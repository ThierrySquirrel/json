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

package io.github.thierrysquirrel.json.deserialize.map;

import io.github.thierrysquirrel.json.deserialize.JsonDeSerialize;
import io.github.thierrysquirrel.json.deserialize.list.JsonDeSerializeList;
import io.github.thierrysquirrel.json.deserialize.map.json.pojo.MapJsonPojo;
import io.github.thierrysquirrel.json.deserialize.map.json.pojo.util.MapJsonPojoUtil;
import io.github.thierrysquirrel.json.deserialize.util.JsonDeSerializeUtil;
import io.github.thierrysquirrel.json.reflect.JsonReflect;
import io.github.thierrysquirrel.json.tag.JsonTag;
import io.github.thierrysquirrel.json.type.JsonType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classname: JsonDeSerializeMap
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonDeSerializeMap {
    private JsonDeSerializeMap() {
    }

    public static Map<Object, Object> getMap(String json, String mapTypeName, Map<Object, Object> thisMap) {
        String mapValueType = getMapValueType(mapTypeName);
        String mapKeyType = getMapKeyType(mapTypeName);

        int listOrMapIndex = mapValueType.indexOf(JsonTag.LESS_THAN_SIGN);
        if (listOrMapIndex == -1) {
            genericTypeAll(json, mapKeyType, mapValueType, thisMap);
        } else {
            String genericNameAndTag = getMapValueTypeAndTag(mapTypeName);
            String originalName = mapValueType.substring(0, listOrMapIndex);

            genericTypeAddListOrMap(json, genericNameAndTag, originalName, mapKeyType, thisMap);
        }

        return thisMap;
    }

    private static void genericTypeAddListOrMap(String json, String genericNameAndTag, String originalName, String mapKeyType, Map<Object, Object> thisMap) {
        boolean getJson = Boolean.TRUE;
        while (getJson) {

            MapJsonPojo mapKeyPojo = MapJsonPojoUtil.getJsonKeyValueAndDelete(json);
            String mapKeyValue = mapKeyPojo.getValue();
            json = mapKeyPojo.getNewJson();

            Object mapKeyObject = JsonDeSerializeUtil.getWarpValue(mapKeyValue, mapKeyType);

            json = typeAddListOrMapFind(json, genericNameAndTag, originalName, mapKeyObject, thisMap);

            int nextBeginIndex = json.indexOf(JsonTag.DOUBLE_QUOTATION_MARKS);
            if (nextBeginIndex == -1) {
                getJson = false;
            }
        }

    }

    private static String typeAddListOrMapFind(String json, String genericNameAndTag, String originalName, Object mapKey, Map<Object, Object> thisMap) {
        return switch (originalName) {
            case JsonType.MAP, JsonType.HASH_MAP ->
                    typeAddMap(json, genericNameAndTag, mapKey, new HashMap<>(), thisMap);
            case JsonType.CONCURRENT_MAP, JsonType.CONCURRENT_HASH_MAP ->
                    typeAddMap(json, genericNameAndTag, mapKey, new ConcurrentHashMap<>(), thisMap);
            case JsonType.ARRAY_LIST -> typeAddList(json, genericNameAndTag, mapKey, new ArrayList<>(), thisMap);
            case JsonType.LINKED_LIST -> typeAddList(json, genericNameAndTag, mapKey, new LinkedList<>(), thisMap);
            case JsonType.LIST -> typeAddList(json, genericNameAndTag, mapKey, new ArrayList<>(), thisMap);
            default -> JsonTag.EMPTY_CHARACTER;
        };
    }

    private static String typeAddMap(String json, String genericNameAndTag, Object mapKey, Map<Object, Object> map, Map<Object, Object> thisMap) {
        thisMap.put(mapKey, map);

        int beginIndex = json.indexOf(JsonTag.OPEN_CURLY);
        String tagJson = JsonDeSerializeUtil.getTagJson(json, beginIndex + 1, JsonTag.OPEN_CURLY, JsonTag.CLOSE_CURLY);

        json = JsonDeSerializeUtil.deleteFindString(json, tagJson);
        getMap(tagJson, genericNameAndTag, map);
        return json;
    }

    private static String typeAddList(String json, String genericNameAndTag, Object mapKey, List<Object> list, Map<Object, Object> thisMap) {
        thisMap.put(mapKey, list);

        String tagJson = JsonDeSerializeUtil.getTagJson(json, 0, JsonTag.OPEN_BRACKET, JsonTag.CLOSE_BRACKET);
        json = JsonDeSerializeUtil.deleteFindString(json, tagJson);
        JsonDeSerializeList.getList(tagJson, genericNameAndTag, list);
        return json;
    }


    private static void genericTypeAll(String json, String mapKeyType, String mapValueType, Map<Object, Object> thisMap) {
        boolean getJson = Boolean.TRUE;
        while (getJson) {

            MapJsonPojo keyJsonValue = MapJsonPojoUtil.getJsonKeyValue(json);
            String mapKey = keyJsonValue.getValue();
            json = keyJsonValue.getNewJson();

            Object mapKeyObject = JsonDeSerializeUtil.getWarpValue(mapKey, mapKeyType);
            json = genericType(json, mapKeyObject, mapValueType, thisMap);

            int nextBeginIndex = json.indexOf(JsonTag.DOUBLE_QUOTATION_MARKS);
            if (nextBeginIndex == -1) {
                getJson = Boolean.FALSE;
            }
        }
    }

    private static String genericType(String json, Object mapKey, String mapValueType, Map<Object, Object> thisMap) {
        return switch (mapValueType) {
            case JsonType.BYTE, JsonType.BYTE_WRAP, JsonType.SHORT, JsonType.SHORT_WRAP, JsonType.INT,
                 JsonType.INTEGER_WRAP, JsonType.LONG, JsonType.LONG_WRAP, JsonType.FLOAT, JsonType.FLOAT_WRAP,
                 JsonType.DOUBLE, JsonType.DOUBLE_WRAP, JsonType.BOOLEAN, JsonType.BOOLEAN_WRAP, JsonType.CHAR,
                 JsonType.CHARACTER_WRAP, JsonType.STRING -> genericTypeAddValue(json, mapKey, mapValueType, thisMap);
            default -> genericTypeAddEnumAndPojo(json, mapKey, mapValueType, thisMap);
        };
    }

    private static String genericTypeAddValue(String json, Object mapKey, String valueTypeName, Map<Object, Object> thisMap) {
        MapJsonPojo mapJsonPojo = MapJsonPojoUtil.getJsonValue(json);
        String value = mapJsonPojo.getValue();
        json = mapJsonPojo.getNewJson();

        Object warpValue = JsonDeSerializeUtil.getWarpValue(value, valueTypeName);
        thisMap.put(mapKey, warpValue);
        return json;
    }

    private static String genericTypeAddEnumAndPojo(String json, Object mapKey, String valueTypeName, Map<Object, Object> thisMap) {
        Class<?> thisClass = JsonReflect.classForName(valueTypeName);
        if (thisClass.isEnum()) {
            return genericTypeAddEnum(json, mapKey, (Class<? extends Enum>) thisClass, thisMap);
        } else {
            return genericTypeAddPojo(json, mapKey, thisClass, thisMap);
        }
    }

    private static String genericTypeAddPojo(String json, Object mapKey, Class<?> pojoClass, Map<Object, Object> thisMap) {
        String tagJson = JsonDeSerializeUtil.getTagJson(json, 0, JsonTag.OPEN_CURLY, JsonTag.CLOSE_CURLY);
        json = JsonDeSerializeUtil.deleteFindString(json, tagJson);
        Object object = JsonDeSerialize.deSerialize(tagJson, pojoClass);
        thisMap.put(mapKey, object);
        return json;
    }

    private static String genericTypeAddEnum(String json, Object mapKey, Class<? extends Enum> enumClass, Map<Object, Object> thisMap) {
        MapJsonPojo mapJsonPojo = MapJsonPojoUtil.getJsonValue(json);
        String value = mapJsonPojo.getValue();
        json = mapJsonPojo.getNewJson();

        Enum<?> enumValue = Enum.valueOf((Class<? extends Enum>) enumClass, value);
        thisMap.put(mapKey, enumValue);
        return json;
    }

    private static String getMapValueType(String mapTypeName) {
        int beginIndex = mapTypeName.indexOf(JsonTag.COMMA);
        int endIndex = mapTypeName.indexOf(JsonTag.GREATER_THAN_SIGN);
        return mapTypeName.substring(beginIndex + 1, endIndex).trim();
    }

    private static String getMapValueTypeAndTag(String mapTypeName) {
        int beginIndex = mapTypeName.indexOf(JsonTag.COMMA);
        int endIndex = mapTypeName.indexOf(JsonTag.GREATER_THAN_SIGN);
        return mapTypeName.substring(beginIndex + 1, endIndex + 1).trim();
    }

    private static String getMapKeyType(String mapTypeName) {
        int beginIndex = mapTypeName.indexOf(JsonTag.LESS_THAN_SIGN);
        int endIndex = mapTypeName.indexOf(JsonTag.COMMA);
        return mapTypeName.substring(beginIndex + 1, endIndex);
    }
}
