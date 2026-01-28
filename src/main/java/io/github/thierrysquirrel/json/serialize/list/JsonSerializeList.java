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

package io.github.thierrysquirrel.json.serialize.list;

import io.github.thierrysquirrel.json.serialize.pojo.field.JsonSerializePojoField;
import io.github.thierrysquirrel.json.tag.JsonTag;

import java.util.List;

/**
 * Classname: JsonSerializeList
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonSerializeList {
    private JsonSerializeList() {
    }

    public static String serializeList(List<?> list) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append(JsonTag.OPEN_BRACKET);
        for (Object object : list) {
            JsonSerializePojoField.fieldValueToJson(jsonBuilder, object.getClass().getTypeName(), object);
            jsonBuilder.append(JsonTag.COMMA);
        }

        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append(JsonTag.CLOSE_BRACKET);
        return jsonBuilder.toString();
    }
}
