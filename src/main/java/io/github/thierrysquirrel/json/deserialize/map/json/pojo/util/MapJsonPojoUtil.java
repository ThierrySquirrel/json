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

package io.github.thierrysquirrel.json.deserialize.map.json.pojo.util;

import io.github.thierrysquirrel.json.deserialize.map.json.pojo.MapJsonPojo;
import io.github.thierrysquirrel.json.deserialize.util.JsonDeSerializeUtil;
import io.github.thierrysquirrel.json.tag.JsonTag;

/**
 * Classname: MapJsonPojoUtil
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class MapJsonPojoUtil {
    private MapJsonPojoUtil() {
    }

    public static MapJsonPojo getJsonKeyValue(String json) {
        int beginIndex = json.indexOf(JsonTag.DOUBLE_QUOTATION_MARKS);
        int endIndex = json.indexOf(JsonTag.DOUBLE_QUOTATION_MARKS, beginIndex + 1);

        String value = json.substring(beginIndex + 1, endIndex);
        json = json.substring(endIndex + 1);

        return new MapJsonPojo(value, json);
    }

    public static MapJsonPojo getJsonKeyValueAndDelete(String json) {
        int beginIndex = json.indexOf(JsonTag.DOUBLE_QUOTATION_MARKS);
        int endIndex = json.indexOf(JsonTag.DOUBLE_QUOTATION_MARKS, beginIndex + 1);

        String value = json.substring(beginIndex + 1, endIndex);

        String valueAndTag = json.substring(beginIndex, endIndex + 1);
        json = JsonDeSerializeUtil.deleteFindString(json, valueAndTag);
        return new MapJsonPojo(value, json);
    }

    public static MapJsonPojo getJsonValue(String json) {
        int beginIndex = json.indexOf(JsonTag.COLON);

        int endIndex = json.indexOf(JsonTag.COMMA, beginIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf(JsonTag.CLOSE_CURLY, beginIndex);
        }

        String value = json.substring(beginIndex + 1, endIndex);
        value = JsonDeSerializeUtil.replaceEmpty(value, JsonTag.DOUBLE_QUOTATION_MARKS);
        json = json.substring(endIndex + 1);
        return new MapJsonPojo(value, json);
    }

}
