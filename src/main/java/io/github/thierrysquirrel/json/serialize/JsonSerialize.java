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

package io.github.thierrysquirrel.json.serialize;

import io.github.thierrysquirrel.json.serialize.list.JsonSerializeList;
import io.github.thierrysquirrel.json.serialize.map.JsonSerializeMap;
import io.github.thierrysquirrel.json.serialize.pojo.JsonSerializePojo;
import io.github.thierrysquirrel.json.type.JsonType;

import java.util.List;
import java.util.Map;

/**
 * Classname: JsonSerialize
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonSerialize {
    private JsonSerialize() {
    }

    public static String toJson(Object object) {
        String typeName = object.getClass().getTypeName();
        return switch (typeName) {
            case JsonType.ARRAY_LIST, JsonType.LINKED_LIST, JsonType.LIST ->
                    JsonSerializeList.serializeList((List<?>) object);
            case JsonType.MAP, JsonType.HASH_MAP, JsonType.CONCURRENT_MAP, JsonType.CONCURRENT_HASH_MAP ->
                    JsonSerializeMap.serializeMap((Map<Object, Object>) object);
            default -> JsonSerializePojo.serializePojo(object);
        };
    }
}
