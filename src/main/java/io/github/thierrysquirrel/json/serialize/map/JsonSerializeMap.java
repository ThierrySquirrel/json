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

package io.github.thierrysquirrel.json.serialize.map;

import io.github.thierrysquirrel.json.serialize.pojo.field.JsonSerializePojoField;
import io.github.thierrysquirrel.json.tag.JsonTag;
import io.github.thierrysquirrel.json.type.JsonType;

import java.util.Map;

/**
 * Classname: JsonSerializeMap
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonSerializeMap {
    private JsonSerializeMap() {
    }

    public static String serializeMap(Map<Object, Object> map) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append(JsonTag.OPEN_CURLY);

        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object key = entry.getKey();
            String mapKeyType = getMapKeyType(key);
            JsonSerializePojoField.fieldValueToJson(jsonBuilder, mapKeyType, key);
            jsonBuilder.append(JsonTag.COLON);

            Object value = entry.getValue();
            JsonSerializePojoField.fieldValueToJson(jsonBuilder, value.getClass().getTypeName(), value);

            jsonBuilder.append(JsonTag.COMMA);
        }
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);

        jsonBuilder.append(JsonTag.CLOSE_CURLY);
        return jsonBuilder.toString();
    }

    private static String getMapKeyType(Object mapKey) {
        String typeName = mapKey.getClass().getTypeName();
        typeName = switch (typeName) {
            case JsonType.BYTE, JsonType.BYTE_WRAP,
                 JsonType.SHORT, JsonType.SHORT_WRAP,
                 JsonType.INT, JsonType.INTEGER_WRAP,
                 JsonType.LONG, JsonType.LONG_WRAP,
                 JsonType.FLOAT, JsonType.FLOAT_WRAP,
                 JsonType.DOUBLE, JsonType.DOUBLE_WRAP,
                 JsonType.BOOLEAN, JsonType.BOOLEAN_WRAP -> String.class.getTypeName();
            default -> typeName;
        };
        return typeName;
    }
}
