
# Json

Json

[中文](./README_zh_CN.md)

Support function:
- [x] Json Serialize
- [x] Json DeSerialize

# Json Serialize:
Data type,byte,short,int,long,float,double,char,String  
Enum,Pojo,List,Map

If Pojo includes a List, the supported data types are byte,short,int,long,float,double,char,String  
Enum,Pojo,List,ArrayList,LinkedList,Map,HashMap,ConcurrentMap,ConcurrentHashMap  

If Pojo contains a Map, the data types supported by Map Key are byte,short,int,long,float,double,char,String  
The data types supported by Map Value include byte,short,int,long,float,double,char,String  
Enum,Pojo,List,ArrayList,LinkedList,Map,HashMap,ConcurrentMap,ConcurrentHashMap


# Json DeSerialize:
Support Pojo class deserialization  

## Quick Start

```xml
<!--Adding dependencies to pom. XML-->
        <dependency>
            <groupId>io.github.thierrysquirrel</groupId>
            <artifactId>json</artifactId>
            <version>1.0.0.0-RELEASE</version>
        </dependency>
```

# Json Serialize:
# Json DeSerialize:
```java
public class JsonText {
    private byte byteA;
    private Byte byteB;

    private int intA;
    private Integer intB;

    private char charA;
    private Character charB;

    private String stringA;
    
    //Get Set toString
}
```

```java
public class Main {
    public static void main(String[] args) {
        JsonText jsonText=new  JsonText();
        jsonText.setByteA((byte)1);
        jsonText.setByteB((byte)2);
        jsonText.setIntA(3);
        jsonText.setIntB(4);
        jsonText.setCharA('A');
        jsonText.setCharB('B');
        jsonText.setStringA("hello");
        String json=JsonUtil.toJson(jsonText);
        System.out.println(json);

        JsonText jsonTextA = JsonUtil.deSerialize(json, JsonText.class);
        System.out.println(JsonUtil.toJson(jsonTextA));
    }
}
```