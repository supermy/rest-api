package com.supermy.microcloud;

/**
 * Created by moyong on 17/3/29.
 */
class Entry<K, V> {
    K key;
    V value;
    int hashCode;
    Entry<K, V> nextEntry;

    public Entry(K key, V value, int hashCode) {
        super();
        this.key = key;
        this.value = value;
        this.hashCode = hashCode;
    }

}
