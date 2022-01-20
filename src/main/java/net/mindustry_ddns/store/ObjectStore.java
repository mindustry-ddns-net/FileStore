package net.mindustry_ddns.store;


public interface ObjectStore<T>{
    void store(String name, T object);

    <R extends T> R load(String name, Class<R> clazz);
}
