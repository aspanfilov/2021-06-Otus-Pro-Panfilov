package ru.otus.cachehw;


import java.lang.ref.WeakReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, V> cache;
    private final List<HwListener<K, V>> listeners;

    public MyCache () {
        this.cache = new WeakHashMap<>();
        this.listeners = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        this.cache.put(key, value);
        notifyListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        this.cache.remove(key);
        notifyListeners(key, null, "remove");
    }

    @Override
    public V get(K key) {
        return this.cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        this.listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, String action) {
        this.listeners.forEach(listener -> listener.notify(key, value, action));
    }
}
