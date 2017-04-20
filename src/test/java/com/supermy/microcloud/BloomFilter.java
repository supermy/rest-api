package com.supermy.microcloud;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * BloomFilter算法
 *
 * Created by moyong on 17/3/29.
 *
 */
public class BloomFilter {
    /*哈希函数*/
    private List<IHashFunction> hashFuctionList;
    /*构造方法*/
    public BloomFilter() {
        this.hashFuctionList = new ArrayList<IHashFunction>();
    }
    /*添加哈希函数类*/
    public void addHashFunction(IHashFunction hashFunction) {
        this.hashFuctionList.add(hashFunction);
    }
    /*删除hash函数*/
    public void removeHashFunction(IHashFunction hashFunction) {
        this.hashFuctionList.remove(hashFunction);
    }
    /*判断是否被包含*/
    public boolean contain(BitSet bitSet, String str) {
        for (IHashFunction hash : hashFuctionList) {
            int hashCode = hash.toHashCode(str);
            if(hashCode<0){
                hashCode=-hashCode;
            }
            if (bitSet.get(hashCode) == false) {
                return false;
            }
        }
        return true;
    }
    /*添加到bitSet*/
    public void toBitSet(BitSet bitSet, String str) {
        for (IHashFunction hash : hashFuctionList) {
            int hashCode = hash.toHashCode(str);
            if(hashCode<0){
                hashCode=-hashCode;
            }
            bitSet.set(hashCode, true);
        }
    }

    public static void main(String[] args) {
        BloomFilter bloomFilter=new BloomFilter();
		/*添加3个哈希函数*/
        bloomFilter.addHashFunction(new JavaHash());
        bloomFilter.addHashFunction(new RSHash());
        bloomFilter.addHashFunction(new SDBMHash());
		/*长度为2的24次方*/
        BitSet bitSet=new BitSet(1<<25);
		/*判断test1很test2重复的字符串*/
        String[] test1=new String[]{"哈哈","我","大家","逗比","有钱人性","小米","Iphone","helloWorld"};
        for (String str1 : test1) {
            bloomFilter.toBitSet(bitSet, str1);
        }
        String[] test2=new String[]{"哈哈","我的","大家","逗比","有钱的人性","小米","Iphone6s","helloWorld"};
        for (String str2 : test2) {
            if(bloomFilter.contain(bitSet, str2)){
                System.out.println("'"+str2+"'是重复的");
            }
        }

    }
}
/*哈希函数接口*/
interface IHashFunction {
    int toHashCode(String str);
}

class JavaHash implements IHashFunction {

    @Override
    public int toHashCode(String str) {
        return str.hashCode();
    }

}

class RSHash implements IHashFunction {

    @Override
    public int toHashCode(String str) {
        int b = 378551;
        int a = 63689;
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = hash * a + str.charAt(i);
            a = a * b;
        }
        return hash;
    }

}

class SDBMHash implements IHashFunction {

    @Override
    public int toHashCode(String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++)
            hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
        return hash;
    }

}



