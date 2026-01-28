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

package io.github.thierrysquirrel.json.type;

/**
 * Classname: JsonType
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonType {
    private JsonType() {
    }

    public static final String BYTE = "byte";
    public static final String BYTE_WRAP = "java.lang.Byte";

    public static final String SHORT = "short";
    public static final String SHORT_WRAP = "java.lang.Short";

    public static final String INT = "int";
    public static final String INTEGER_WRAP = "java.lang.Integer";

    public static final String LONG = "long";
    public static final String LONG_WRAP = "java.lang.Long";

    public static final String FLOAT = "float";
    public static final String FLOAT_WRAP = "java.lang.Float";

    public static final String DOUBLE = "double";
    public static final String DOUBLE_WRAP = "java.lang.Double";

    public static final String BOOLEAN = "boolean";
    public static final String BOOLEAN_WRAP = "java.lang.Boolean";

    public static final String CHAR = "char";
    public static final String CHARACTER_WRAP = "java.lang.Character";

    public static final String STRING = "java.lang.String";
    public static final String OBJECT = "java.lang.Object";

    public static final String LIST = "java.util.List";
    public static final String ARRAY_LIST = "java.util.ArrayList";
    public static final String LINKED_LIST = "java.util.LinkedList";

    public static final String MAP = "java.util.Map";
    public static final String CONCURRENT_MAP = "java.util.concurrent.ConcurrentMap";
    public static final String HASH_MAP = "java.util.HashMap";
    public static final String CONCURRENT_HASH_MAP = "java.util.concurrent.ConcurrentHashMap";
}
