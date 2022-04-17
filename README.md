# FileStore

[![Xpdustry latest](https://repo.xpdustry.fr/api/badge/latest/releases/net/mindustry_ddns/file-store?color=00FFFF&name=FileStore&prefix=v)](https://github.com/mindustry-ddns-net/FileStore/releases)

## Description

A very simple object persistence library.

To use in your project, add this in your `build.gradle` :

```gradle
repositories {
    maven { url = uri("https://repo.xpdustry.fr/releases") }
}

dependencies {
    implementation("net.mindustry_ddns:file-store:2.1.0")
}
```

## Usage

The library provides the `Store` and `FileStore` classes to load and save your objects, with the format handled by a `Serializer`.

Here are the default formats provided by the library (make sure the implementations are available in the classpath) :

- Json : Use this for your settings, the stuff that can change at runtime.

  - [Gson](https://github.com/google/gson) (`Serializers.gson()`).

  - [Jackson](https://github.com/FasterXML/jackson-databind) (`Serializers.jackson()`).

- Properties : I recommend using this for configurations, stuff that don't change during runtime, generally used at the application startup.

  - [Owner](https://github.com/matteobaccan/owner) (`Serializers.config()`).
  
    /!\ I **greatly** recommend reading the [docs](http://owner.aeonbits.org/docs/welcome/) of this back-end before using it.

  - [Java](https://docs.oracle.com/javase/7/docs/api/java/util/Properties.html) (`Serializers.properties()`).

- Other formats :

  - Jackson : You can use one of the bindings like [xml](https://github.com/FasterXML/jackson-dataformat-xml) by passing the mapper with `Serializers.jackson(ObjectMapper)`.

### Examples

For example, let's say you have this `PersonSettings` POJO class :

```java
public class PersonSettings {
    private String name;
    private int age;

    public PersonSettings(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

You'll just have to do :

```java
FileStore<PersonSettings> store = FileStore.of("./person.json", Serializers.gson(), new TypeToken<>(){});
```

Then you can freely call the `load()` or `save()` methods whenever you mutate the `PersonSettings` object stored in the store with `set()` or `get()`.

## Tips

- Interfaces with generic parameters aren't supported by the `config` (Owner) serializer.
