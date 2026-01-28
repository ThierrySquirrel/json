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

package io.github.thierrysquirrel.json.deserialize;

import io.github.thierrysquirrel.json.deserialize.pojo.JsonDeSerializePojo;
import io.github.thierrysquirrel.json.reflect.JsonReflect;
import io.github.thierrysquirrel.json.tag.JsonTag;

import java.lang.reflect.Field;

/**
 * Classname: JsonDeSerialize
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonDeSerialize {
    private JsonDeSerialize() {
    }

    public static <T> T deSerialize(String json, Class<T> pojoClass) {
        json = json.replaceAll(JsonTag.REGEX_WHITESPACE_CHARACTERS, JsonTag.EMPTY_CHARACTER);
        T pojo = JsonReflect.newInstance(pojoClass);
        Field[] declaredFields = pojoClass.getDeclaredFields();

        for (Field field : declaredFields) {
            int jsonFieldIndex = json.indexOf(field.getName());

            String fieldValueJson = JsonDeSerializePojo.getFieldValueJson(json, jsonFieldIndex, field);
            Object fieldValue = JsonDeSerializePojo.getFieldValue(fieldValueJson, field.getType().getTypeName(), field);

            JsonReflect.fieldSetValue(field, pojo, fieldValue);
        }

        return pojo;
    }
}
