# FileStore

## Description

File management library.

To use in your project, add it in your `build.gradle`:

```gradle
repositories {
    maven { url "https://repo.xpdustry.fr/releases" }
}

dependencies {
    implementation "net.mindustry_ddns:file-store:1.3.0"
}
```

## Usage

This library is very easy to use. It provides a [property](https://github.com/matteobaccan/owner)
and [json](https://github.com/google/gson) based file stores by default.

For example, let's say you have this `PersonConfig` config interface (uses property based file store):

```java
public interface PersonConfig extends Accessible {
    @DefaultValue("John")
    @key("person.name")
    String getName();

    @DefaultValue("24")
    @key("person.age")
    int getAge();
};
```

You'll just have to do:

```java
FileStore<PersonConfig> store = new ConfigFileStore("./john.properties", PersonConfig.class);
```

to create and load the config.

## Tips

If you are using the `JsonFileStore`, keep in mind that if the object hold by the store uses generics, you must use
gson's `TypeToken` for the type such as:

```java
FileStore<List<Integer>> store = new JsonFileStore("./int-list.json", TypeToken.getParameterized(List.class, Integer.class).getType(), ArrayList::new);
```

As you can see, I don't use the raw `List.class` for the `Type`.

> Not doing this may result in `ClassCastException` exceptions and other funny bugs.
