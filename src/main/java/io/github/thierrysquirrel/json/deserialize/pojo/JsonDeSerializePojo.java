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

package io.github.thierrysquirrel.json.deserialize.pojo;

import io.github.thierrysquirrel.json.deserialize.JsonDeSerialize;
import io.github.thierrysquirrel.json.deserialize.list.JsonDeSerializeList;
import io.github.thierrysquirrel.json.deserialize.map.JsonDeSerializeMap;
import io.github.thierrysquirrel.json.deserialize.util.JsonDeSerializeUtil;
import io.github.thierrysquirrel.json.tag.JsonTag;
import io.github.thierrysquirrel.json.type.JsonType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classname: JsonDeSerializePojo
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonDeSerializePojo {
    private JsonDeSerializePojo() {
    }

    public static String getFieldValueJson(String json, int beginIndex, Field field) {
        String typeName = field.getType().getTypeName();

        beginIndex = json.indexOf(JsonTag.COLON, beginIndex);
        int endIndex = JsonDeSerializeUtil.getEndIndex(json, beginIndex, JsonTag.COMMA, JsonTag.CLOSE_CURLY);

        return switch (typeName) {
            case JsonType.BYTE, JsonType.BYTE_WRAP,
                 JsonType.SHORT, JsonType.SHORT_WRAP,
                 JsonType.INT, JsonType.INTEGER_WRAP,
                 JsonType.LONG, JsonType.LONG_WRAP,
                 JsonType.FLOAT, JsonType.FLOAT_WRAP,
                 JsonType.DOUBLE, JsonType.DOUBLE_WRAP,
                 JsonType.BOOLEAN, JsonType.BOOLEAN_WRAP -> json.substring(beginIndex + 1, endIndex);

            case JsonType.CHAR, JsonType.CHARACTER_WRAP,
                 JsonType.STRING -> json.substring(beginIndex + 2, endIndex - 1);
            case JsonType.OBJECT ->
                    JsonDeSerializeUtil.replaceEmpty(json.substring(beginIndex + 1, endIndex), JsonTag.DOUBLE_QUOTATION_MARKS);
            case JsonType.ARRAY_LIST, JsonType.LINKED_LIST,
                 JsonType.LIST ->
                    JsonDeSerializeUtil.getTagJson(json, beginIndex, JsonTag.OPEN_BRACKET, JsonTag.CLOSE_BRACKET);
            case JsonType.HASH_MAP, JsonType.CONCURRENT_HASH_MAP,
                 JsonType.MAP, JsonType.CONCURRENT_MAP ->
                    JsonDeSerializeUtil.getTagJson(json, beginIndex, JsonTag.OPEN_CURLY, JsonTag.CLOSE_CURLY);
            default -> getFieldEnumAndPojoJson(json, beginIndex, endIndex, field);
        };
    }

    public static Object getFieldValue(String jsonFieldValue, String fieldTypeName, Field field) {
        return switch (fieldTypeName) {
            case JsonType.BYTE, JsonType.BYTE_WRAP, JsonType.SHORT, JsonType.SHORT_WRAP, JsonType.INT,
                 JsonType.INTEGER_WRAP, JsonType.LONG, JsonType.LONG_WRAP, JsonType.FLOAT, JsonType.FLOAT_WRAP,
                 JsonType.DOUBLE, JsonType.DOUBLE_WRAP, JsonType.BOOLEAN, JsonType.BOOLEAN_WRAP, JsonType.CHAR,
                 JsonType.CHARACTER_WRAP, JsonType.STRING ->
                    JsonDeSerializeUtil.getWarpValue(jsonFieldValue, fieldTypeName);
            case JsonType.OBJECT -> jsonFieldValue;
            case JsonType.ARRAY_LIST ->
                    JsonDeSerializeList.getList(jsonFieldValue, field.getGenericType().getTypeName(), new ArrayList<>());
            case JsonType.LINKED_LIST ->
                    JsonDeSerializeList.getList(jsonFieldValue, field.getGenericType().getTypeName(), new LinkedList<>());
            case JsonType.LIST ->
                    JsonDeSerializeList.getList(jsonFieldValue, field.getGenericType().getTypeName(), new ArrayList<>());
            case JsonType.MAP, JsonType.HASH_MAP ->
                    JsonDeSerializeMap.getMap(jsonFieldValue, field.getGenericType().getTypeName(), new HashMap<>());
            case JsonType.CONCURRENT_MAP, JsonType.CONCURRENT_HASH_MAP ->
                    JsonDeSerializeMap.getMap(jsonFieldValue, field.getGenericType().getTypeName(), new ConcurrentHashMap<>());
            default -> getFieldEnumAndPojoValue(jsonFieldValue, field);
        };
    }

    private static Object getFieldEnumAndPojoValue(String jsonFieldValue, Field field) {
        Class<?> thisClass = field.getType();
        if (thisClass.isEnum()) {
            return fieldGetEnumValue(jsonFieldValue, (Class<? extends Enum>) thisClass);
        } else {
            return fieldGetPojoValue(jsonFieldValue, thisClass);
        }
    }

    private static String getFieldEnumAndPojoJson(String json, int beginIndex, int endIndex, Field field) {
        Class<?> thisClass = field.getType();
        if (thisClass.isEnum()) {
            return getFieldEnumJson(json, beginIndex, endIndex);
        } else {
            return getFieldPojoJson(json, beginIndex);
        }
    }

    private static String getFieldEnumJson(String json, int beginIndex, int endIndex) {
        return json.substring(beginIndex + 2, endIndex - 1);
    }

    private static String getFieldPojoJson(String json, int beginIndex) {
        return JsonDeSerializeUtil.getTagJson(json, beginIndex, JsonTag.OPEN_CURLY, JsonTag.CLOSE_CURLY);
    }

    private static Object fieldGetEnumValue(String jsonFieldValue, Class<? extends Enum> enumClass) {
        return Enum.valueOf(enumClass, jsonFieldValue);
    }

    private static Object fieldGetPojoValue(String jsonFieldValue, Class<?> pojoClass) {
        return JsonDeSerialize.deSerialize(jsonFieldValue, pojoClass);
    }

}
