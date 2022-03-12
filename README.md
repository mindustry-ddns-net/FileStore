# FileStore

## Description

File management library.

To use in your project, add it in your `build.gradle`:

```gradle
repositories {
    maven { url "https://repo.xpdustry.fr/releases" }
}

dependencies {
    implementation "net.mindustry_ddns:file-store:1.4.0"
}
```

## Usage

This library is very easy to use. It provides a [property](https://github.com/matteobaccan/owner) and [json](https://github.com/google/gson) based file stores by default.

For example, let's say you have this `PersonConfig` config interface (uses the property based file store) :

```java
public interface PersonConfig extends Accessible {
    @DefaultValue("John")
    @key("person.name")
    String getName();

    @DefaultValue("24")
    @key("person.age")
    int getAge();
}
```

You'll just have to do :

```java
FileStore<PersonConfig> store = new ConfigFileStore("./john.properties", PersonConfig.class);
```

to create the config file store.

If you call the `load()` method, it will try to load the file if it exists, otherwise, it will create it.

## Tips

If you are using the `JsonFileStore`, keep in mind that if the object hold by the store uses generics, you must use gson's `TypeToken` for the type such as :

```java
Type type = TypeToken.getParameterized(List.class, Integer.class).getType();
FileStore<List<Integer>> store = new JsonFileStore("./int-list.json", type, ArrayList::new);
```

As you can see, I don't use the raw `List.class` for the `type`.

> Not doing this may result in `ClassCastException` exceptions and other funny bugs.
