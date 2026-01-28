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

package io.github.thierrysquirrel.json.reflect;


import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classname: JsonReflect
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class JsonReflect {
    private JsonReflect() {
    }

    private static final Logger logger = Logger.getLogger(JsonReflect.class.getName());

    public static Object getFieldValue(Field field, Object object) {
        field.setAccessible(true);
        Object fieldValue = null;
        try {
            fieldValue = field.get(object);
        } catch (IllegalAccessException e) {
            String logMsg = "getFieldValue Error";
            logger.log(Level.WARNING, logMsg, e);

        }
        return fieldValue;
    }

    public static <T> T newInstance(Class<T> pojoClass) {
        T pojo = null;
        try {
            pojo = pojoClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            String logMsg = "newInstance Error";
            logger.log(Level.WARNING, logMsg, e);
        }
        return pojo;
    }

    public static void fieldSetValue(Field field, Object pojo, Object value) {
        field.setAccessible(true);
        try {
            field.set(pojo, value);
        } catch (IllegalAccessException e) {
            String logMsg = "fieldSetValue Error";
            logger.log(Level.WARNING, logMsg, e);
        }
    }

    public static Class<?> classForName(String className) {
        Class<?> thisClass = null;
        try {
            thisClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            String logMsg = "classForName Error";
            logger.log(Level.WARNING, logMsg, e);
        }
        return thisClass;
    }
}
