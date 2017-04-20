package com.supermy.microcloud;

/**
 * Created by moyong on 17/3/29.
 */
public class HashMap<K, V> {

    private static final int CAPACTITY = 16;

    transient Entry<K, V>[] table = null;

    @SuppressWarnings("unchecked")
    public HashMap() {
        super();
        table = new Entry[CAPACTITY];
    }

    /* 哈希算法 */
    private final int toHashCode(Object obj) {
        int h = 0;
        if (obj instanceof String) {
            return StringHash.toHashCode((String) obj);
        }
        h ^= obj.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
    /*放入hashMap*/
    public void put(K key, V value) {
        int hashCode = this.toHashCode(key);
        int index = hashCode % CAPACTITY;
        if (table[index] == null) {
            table[index] = new Entry<K, V>(key, value, hashCode);
        } else {
            for (Entry<K, V> entry = table[index]; entry != null; entry = entry.nextEntry) {
                if (entry.hashCode == hashCode && (entry.key == key || key.equals(entry.key))) {
                    entry.value = value;
                    return;
                }
            }
            Entry<K, V> entry2 = table[index];
            Entry<K, V> entry3 = new Entry<K, V>(key, value, hashCode);
            entry3.nextEntry = entry2;
            table[index] = entry3;

        }
    }
    /*获取值*/
    public V get(K key) {
        int hashCode = this.toHashCode(key);
        int index = hashCode % CAPACTITY;
        if (table[index] == null) {
            return null;
        } else {
            for (Entry<K, V> entry = table[index]; entry != null; entry = entry.nextEntry) {
                if (entry.hashCode == hashCode && (entry.key == key || key.equals(entry.key))) {
                    return entry.value;
                }
            }
            return null;

        }
    }
    /*删除*/
    public void remove(K key){
        int hashCode = this.toHashCode(key);
        int index = hashCode % CAPACTITY;
        if (table[index] == null) {
            return ;
        } else {
            Entry<K, V> parent=null;
            int i=0;
            for (Entry<K, V> entry = table[index]; entry != null; entry = entry.nextEntry) {
                if (entry.hashCode == hashCode && (entry.key == key || key.equals(entry.key))) {
                    if(i==0){
                        table[index]=null;
                    }
                    if(parent!=null){
                        parent.nextEntry=entry.nextEntry;
                    }
                    entry=null;
                    return ;
                }
                i++;
                parent=entry;
            }
        }
    }
    public static void main(String[] args) {
        HashMap<String,String> map=new HashMap<String,String>();
        for(int i=0;i<10000;i++){
            map.put(Integer.toString(i), Integer.toString(i));
        }
        map.put("1", "2");
        map.put("3", "哈哈哈");
        System.out.println(map.get("1"));
        System.out.println(map.get("3"));
        map.remove("1");
        System.out.println(map.get("1"));
        for(int i=0;i<10000;i++){
            String s=    map.get(Integer.toString(i));
            if(s==null){
                System.out.println(i);
            }
        }
    }

}
