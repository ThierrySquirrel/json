
# Json

Json

[English](./README.md)

支持功能:
- [x] Json序列化
- [x] Json反序列化

# Json序列化:
数据类型,byte,short,int,long,float,double,char,String  
Enum,Pojo,List,Map  

如果Pojo包含List,则支持的数据类型为byte,short,int,long,float,double,char,String  
Enum,Pojo,List,ArrayList,LinkedList,Map,HashMap,ConcurrentMap,ConcurrentHashMap  

如果Pojo包含Map,则Map Key支持的数据类型为byte,short,int,long,float,double,char,String  
Map Value支持的数据类型包括byte,short,int,long,float,double,char,String  
Enum,Pojo,List,ArrayList,LinkedList,Map,HashMap,ConcurrentMap,ConcurrentHashMap  


# Json反序列化:
支持Pojo类反序列化  

## Quick Start

```xml
<!--Adding dependencies to pom. XML-->
        <dependency>
            <groupId>io.github.thierrysquirrel</groupId>
            <artifactId>json</artifactId>
            <version>1.0.0.0-RELEASE</version>
        </dependency>
```

# Json序列化:
# Json反序列化:
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