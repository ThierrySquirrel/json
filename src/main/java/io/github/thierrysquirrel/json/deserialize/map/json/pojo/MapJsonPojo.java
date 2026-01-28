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

package io.github.thierrysquirrel.json.deserialize.map.json.pojo;

/**
 * Classname: MapJsonPojo
 * Description:
 * Date:2025/1/28
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class MapJsonPojo {
    private String value;
    private String newJson;

    public MapJsonPojo(String value, String newJson) {
        this.value = value;
        this.newJson = newJson;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNewJson() {
        return newJson;
    }

    public void setNewJson(String newJson) {
        this.newJson = newJson;
    }

    @Override
    public String toString() {
        return "MapJsonPojo{" +
                "value='" + value + '\'' +
                ", newJson='" + newJson + '\'' +
                '}';
    }
}
