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

package io.github.thierrysquirrel.json.serialize.pojo.field;

import io.github.thierrysquirrel.json.tag.JsonTag;
import io.github.thierrysquirrel.json.type.JsonType;
import io.github.thierrysquirrel.json.util.JsonUtil;

/**
 * Classname: JsonSerializePojoField
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonSerializePojoField {
    private JsonSerializePojoField() {
    }

    public static void fieldNameAndTagToJson(StringBuilder jsonBuilder, String fieldName) {
        fieldToJson(jsonBuilder, fieldName);
        jsonBuilder.append(JsonTag.COLON);
    }

    public static void fieldValueToJson(StringBuilder jsonBuilder, String fieldTypeName, Object fieldValue) {
        switch (fieldTypeName) {
            case JsonType.BYTE, JsonType.BYTE_WRAP,
                 JsonType.SHORT, JsonType.SHORT_WRAP,
                 JsonType.INT, JsonType.INTEGER_WRAP,
                 JsonType.LONG, JsonType.LONG_WRAP,
                 JsonType.FLOAT, JsonType.FLOAT_WRAP,
                 JsonType.DOUBLE, JsonType.DOUBLE_WRAP,
                 JsonType.BOOLEAN, JsonType.BOOLEAN_WRAP -> jsonBuilder.append(fieldValue);

            case JsonType.CHAR, JsonType.CHARACTER_WRAP,
                 JsonType.STRING -> fieldToJson(jsonBuilder, fieldValue);

            case JsonType.OBJECT -> fieldValueToJson(jsonBuilder, fieldValue.getClass().getTypeName(), fieldValue);
            default -> fieldEnumAndPojoToJson(jsonBuilder, fieldValue);
        }
    }

    private static void fieldToJson(StringBuilder jsonBuilder, Object field) {
        jsonBuilder.append(JsonTag.DOUBLE_QUOTATION_MARKS).
                append(field).
                append(JsonTag.DOUBLE_QUOTATION_MARKS);
    }

    private static void fieldEnumAndPojoToJson(StringBuilder jsonBuilder, Object fieldValue) {
        Class<?> thisClass = fieldValue.getClass();
        if (thisClass.isEnum()) {
            fieldEnumToJson(jsonBuilder, fieldValue);
        } else {
            fieldPojoToJson(jsonBuilder, fieldValue);
        }
    }

    private static void fieldEnumToJson(StringBuilder jsonBuilder, Object field) {
        fieldValueToJson(jsonBuilder, String.class.getTypeName(), field);
    }

    private static void fieldPojoToJson(StringBuilder jsonBuilder, Object field) {
        String pojoJson = JsonUtil.toJson(field);
        jsonBuilder.append(pojoJson);
    }


}
