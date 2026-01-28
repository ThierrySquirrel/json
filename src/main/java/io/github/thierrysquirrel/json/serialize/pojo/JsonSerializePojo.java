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

package io.github.thierrysquirrel.json.serialize.pojo;

import io.github.thierrysquirrel.json.reflect.JsonReflect;
import io.github.thierrysquirrel.json.serialize.pojo.field.JsonSerializePojoField;
import io.github.thierrysquirrel.json.tag.JsonTag;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Classname: JsonSerializePojo
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonSerializePojo {
    private JsonSerializePojo() {
    }

    public static String serializePojo(Object pojo) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append(JsonTag.OPEN_CURLY);

        Field[] declaredFields = pojo.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Object fieldValue = JsonReflect.getFieldValue(declaredField, pojo);
            if (Objects.isNull(fieldValue)) {
                continue;
            }
            String fieldName = declaredField.getName();
            JsonSerializePojoField.fieldNameAndTagToJson(jsonBuilder, fieldName);

            String fieldTypeName = declaredField.getType().getTypeName();
            JsonSerializePojoField.fieldValueToJson(jsonBuilder, fieldTypeName, fieldValue);

            jsonBuilder.append(JsonTag.COMMA);
        }
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append(JsonTag.CLOSE_CURLY);
        return jsonBuilder.toString();
    }
}
