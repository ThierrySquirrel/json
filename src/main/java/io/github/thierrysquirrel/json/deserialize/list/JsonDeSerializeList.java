/**
 * Copyright 2025/1/28 ThierrySquirrel
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io.github.thierrysquirrel.json.deserialize.list;

import io.github.thierrysquirrel.json.deserialize.JsonDeSerialize;
import io.github.thierrysquirrel.json.deserialize.map.JsonDeSerializeMap;
import io.github.thierrysquirrel.json.deserialize.util.JsonDeSerializeUtil;
import io.github.thierrysquirrel.json.reflect.JsonReflect;
import io.github.thierrysquirrel.json.tag.JsonTag;
import io.github.thierrysquirrel.json.type.JsonType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classname: JsonDeSerializeList
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonDeSerializeList {
    private JsonDeSerializeList() {
    }

    public static List<Object> getList(String json, String listTypeName, List<Object> thisList) {
        String genericName = getListGenericName(listTypeName);
        int listOrMapIndex = genericName.indexOf(JsonTag.LESS_THAN_SIGN);

        if (listOrMapIndex == -1) {
            genericType(json, genericName, thisList);
        } else {
            String originalName = genericName.substring(0, listOrMapIndex);
            String genericNameAndTag = getListGenericNameAndTag(listTypeName);
            genericTypeAddListOrMap(json, genericNameAndTag, originalName, thisList);
        }
        return thisList;
    }

    private static void genericType(String json, String genericName, List<Object> thisList) {
        json = json.replaceAll(JsonTag.REGEX_BRACKET, JsonTag.EMPTY_CHARACTER);
        switch (genericName) {
            case JsonType.BYTE, JsonType.BYTE_WRAP,
                 JsonType.SHORT, JsonType.SHORT_WRAP,
                 JsonType.INT, JsonType.INTEGER_WRAP,
                 JsonType.LONG, JsonType.LONG_WRAP,
                 JsonType.FLOAT, JsonType.FLOAT_WRAP,
                 JsonType.DOUBLE, JsonType.DOUBLE_WRAP,
                 JsonType.BOOLEAN, JsonType.BOOLEAN_WRAP,
                 JsonType.CHAR, JsonType.CHARACTER_WRAP,
                 JsonType.STRING -> genericTypeAddValue(json, genericName, thisList);

            default -> genericTypeAddEnumAndPojo(json, genericName, thisList);

        }
    }

    private static void genericTypeAddListOrMap(String json, String genericNameAndTag, String originalName, List<Object> thisList) {
        boolean getJson = Boolean.TRUE;
        while (getJson) {
            json = typeAddListOrMapFind(json, genericNameAndTag, originalName, thisList);

            String jsonIsNull = JsonDeSerializeUtil.replaceEmpty(json, JsonTag.REGEX_LIST_BRACKET);
            if (jsonIsNull.isEmpty()) {
                getJson = Boolean.FALSE;
            }

        }

    }

    private static String typeAddListOrMapFind(String json, String genericNameAndTag, String originalName, List<Object> thisList) {
        return switch (originalName) {
            case JsonType.ARRAY_LIST -> typeAddList(json, genericNameAndTag, new ArrayList<>(), thisList);
            case JsonType.LINKED_LIST -> typeAddList(json, genericNameAndTag, new LinkedList<>(), thisList);
            case JsonType.LIST -> typeAddList(json, genericNameAndTag, new ArrayList<>(), thisList);
            case JsonType.MAP, JsonType.HASH_MAP -> typeAddMap(json, genericNameAndTag, new HashMap<>(), thisList);
            case JsonType.CONCURRENT_MAP, JsonType.CONCURRENT_HASH_MAP ->
                    typeAddMap(json, genericNameAndTag, new ConcurrentHashMap<>(), thisList);
            default -> JsonTag.EMPTY_CHARACTER;
        };
    }

    private static String typeAddList(String json, String genericNameAndTag, List<Object> list, List<Object> thisList) {
        thisList.add(list);
        int tagBeginIndex = json.indexOf(JsonTag.OPEN_BRACKET);

        String tagJson = JsonDeSerializeUtil.getTagJson(json, tagBeginIndex + 1, JsonTag.OPEN_BRACKET, JsonTag.CLOSE_BRACKET);
        json = JsonDeSerializeUtil.deleteFindString(json, tagJson);

        String isNull = JsonDeSerializeUtil.replaceEmpty(tagJson, JsonTag.REGEX_LIST_BRACKET);
        if (!isNull.isEmpty()) {
            getList(tagJson, genericNameAndTag, list);
        }
        return json;
    }

    private static String typeAddMap(String json, String genericNameAndTag, Map<Object, Object> map, List<Object> thisList) {
        boolean getJson = Boolean.TRUE;
        while (getJson) {
            thisList.add(map);

            String tagJson = JsonDeSerializeUtil.getTagJson(json, 0, JsonTag.OPEN_CURLY, JsonTag.CLOSE_CURLY);
            json = JsonDeSerializeUtil.deleteFindString(json, tagJson);
            JsonDeSerializeMap.getMap(tagJson, genericNameAndTag, map);
            int nextBeginIndex = json.indexOf(JsonTag.DOUBLE_QUOTATION_MARKS);
            if (nextBeginIndex == -1) {
                getJson = Boolean.FALSE;
            }
        }
        return json;
    }


    private static void genericTypeAddValue(String json, String typeName, List<Object> thisList) {
        for (String value : json.split(JsonTag.COMMA)) {
            value = JsonDeSerializeUtil.replaceEmpty(value, JsonTag.DOUBLE_QUOTATION_MARKS);
            Object listValue = JsonDeSerializeUtil.getWarpValue(value, typeName);
            thisList.add(listValue);
        }

    }

    private static void genericTypeAddEnumAndPojo(String json, String typeName, List<Object> thisList) {
        Class<?> thisClass = JsonReflect.classForName(typeName);
        if (thisClass.isEnum()) {
            genericTypeAddEnum(json, (Class<? extends Enum>) thisClass, thisList);
        } else {
            genericTypeAddPojo(json, thisClass, thisList);
        }
    }

    private static void genericTypeAddPojo(String json, Class<?> pojoClass, List<Object> thisList) {
        boolean getTagJson = Boolean.TRUE;
        while (getTagJson) {
            int beginIndex = json.indexOf(JsonTag.OPEN_CURLY);
            String tagJson = JsonDeSerializeUtil.getTagJson(json, beginIndex, JsonTag.OPEN_CURLY, JsonTag.CLOSE_CURLY);
            json = JsonDeSerializeUtil.deleteFindString(json, tagJson);

            Object object = JsonDeSerialize.deSerialize(tagJson, pojoClass);
            thisList.add(object);

            int nextBeginIndex = json.indexOf(JsonTag.OPEN_CURLY);
            if (nextBeginIndex == -1) {
                getTagJson = Boolean.FALSE;
            }
        }
    }

    private static void genericTypeAddEnum(String json, Class<? extends Enum> enumClass, List<Object> thisList) {
        for (String value : json.split(JsonTag.COMMA)) {
            value = JsonDeSerializeUtil.replaceEmpty(value, JsonTag.DOUBLE_QUOTATION_MARKS);
            Enum<?> enumValue = Enum.valueOf(enumClass, value);
            thisList.add(enumValue);
        }

    }

    private static String getListGenericName(String listTypeName) {
        int beginIndex = listTypeName.indexOf(JsonTag.LESS_THAN_SIGN);
        int endIndex = listTypeName.indexOf(JsonTag.GREATER_THAN_SIGN, beginIndex);
        return listTypeName.substring(beginIndex + 1, endIndex);
    }

    private static String getListGenericNameAndTag(String listTypeName) {
        int beginIndex = listTypeName.indexOf(JsonTag.LESS_THAN_SIGN);
        int endIndex = listTypeName.indexOf(JsonTag.GREATER_THAN_SIGN, beginIndex);
        return listTypeName.substring(beginIndex + 1, endIndex + 1);
    }
}
