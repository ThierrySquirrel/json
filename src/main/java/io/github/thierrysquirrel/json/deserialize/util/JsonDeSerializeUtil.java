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

package io.github.thierrysquirrel.json.deserialize.util;

import io.github.thierrysquirrel.json.tag.JsonTag;
import io.github.thierrysquirrel.json.type.JsonType;

/**
 * Classname: JsonDeSerializeUtil
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonDeSerializeUtil {
    private JsonDeSerializeUtil() {
    }

    public static int getEndIndex(String json, int beginIndex, String beginTag, String lastTag) {
        int endIndex = json.indexOf(beginTag, beginIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf(lastTag);
        }
        return endIndex;
    }

    public static String replaceEmpty(String string, String regex) {
        return string.replaceAll(regex, JsonTag.EMPTY_CHARACTER);
    }

    public static String getTagJson(String json, int beginIndex, String beginTag, String endTag) {
        beginIndex = json.indexOf(beginTag, beginIndex);
        int endIndex = beginIndex;
        int lastBeginIndex = beginIndex;
        boolean whileFind = Boolean.TRUE;
        while (whileFind) {
            if (endIndex == -1) {
                return json;
            }
            endIndex = json.indexOf(endTag, endIndex + 1);
            lastBeginIndex = json.indexOf(beginTag, lastBeginIndex + 1, endIndex);
            if (lastBeginIndex == -1) {
                whileFind = Boolean.FALSE;
            }
        }
        return json.substring(beginIndex, endIndex + 1);
    }


    public static Object getWarpValue(String value, String valueType) {
        return switch (valueType) {
            case JsonType.BYTE, JsonType.BYTE_WRAP -> Byte.parseByte(value);
            case JsonType.SHORT, JsonType.SHORT_WRAP -> Short.parseShort(value);
            case JsonType.INT, JsonType.INTEGER_WRAP -> Integer.parseInt(value);
            case JsonType.LONG, JsonType.LONG_WRAP -> Long.parseLong(value);
            case JsonType.FLOAT, JsonType.FLOAT_WRAP -> Float.parseFloat(value);
            case JsonType.DOUBLE, JsonType.DOUBLE_WRAP -> Double.parseDouble(value);
            case JsonType.BOOLEAN, JsonType.BOOLEAN_WRAP -> Boolean.parseBoolean(value);
            case JsonType.CHAR, JsonType.CHARACTER_WRAP -> value.charAt(0);
            case JsonType.STRING -> value;
            default -> value;
        };
    }

    public static String deleteFindString(String allString, String findString) {
        StringBuilder all = new StringBuilder();
        int findStringIndex = allString.indexOf(findString);

        String heard = allString.substring(0, findStringIndex);
        all.append(heard);

        String body = allString.substring(findStringIndex + findString.length());
        all.append(body);
        return all.toString();
    }
}
