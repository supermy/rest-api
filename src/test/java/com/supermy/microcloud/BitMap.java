package com.supermy.microcloud;


import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.BitSet;
/**
 * 大数据处理算法一，bitmap算法
 * Created by moyong on 17/3/29.
 *
 */
public class BitMap {

//    byte[] tem;
//
//    public BitMap(int length) {
//        this.tem = new byte[length];
//    }
//
//    public void add(int num) {
//        if (num < tem.length) {
//            if (tem[num] != 1) {
//                tem[num] = 1;
//            }
//        }
//    }
//
//    public boolean contain(int num) {
//        if (num < tem.length) {
//            if (tem[num] == 1) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static void main(String[] args) throws Exception {
  /*运行前内存*/
        long beforeMemory = Runtime.getRuntime().totalMemory();
        long start1=System.currentTimeMillis();
        BitSet set = new BitSet(2000000000);
        for (int i = 0; i < 2000000000; i++) {
   /*假设898989这个数不在20亿个数里面*/
            //System.out.println(i%50 == 2);
            if (i%200000 == 0) {
                set.set(i, true);
            }
        }
  /*创建20亿个数后所占内存*/
        long afterMemory = Runtime.getRuntime().totalMemory();
        long end1=System.currentTimeMillis();
        System.out.println("总共内存使用:" + (afterMemory - beforeMemory) / 1024 / 1024 + "MB");
        System.out.println("存入内存耗时:"+(end1-start1)+"毫秒");
        long start2 = System.currentTimeMillis();
        boolean isExit1=set.get(898989);
        boolean isExit2=set.get(900000);
        int count=set.cardinality();

        long end2 = System.currentTimeMillis();
  /*输出在20亿个数中判断898989是否包含在里面*/
        System.out.println(isExit1);
        System.out.println("20个亿中"+(isExit1?"包含":"不包含")+898989);
        System.out.println("20个亿中"+(isExit2?"包含":"不包含")+900000);
        System.out.println("20个亿中为 true数量:"+count);


        System.out.println("查询用时:"+(end2 - start2)+"毫秒");

   /*输出到文件*/
        System.out.println("output ...... ......");
        long beforeMemory8 = Runtime.getRuntime().totalMemory();
        long start8=System.currentTimeMillis();

        //TODO 返回数据到 RocksDB 压缩存储；
        String file = "/Users/moyong/project/env-myopensource/1-spring/12-spring/rest-api/target/bigfile.txt";
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            //20亿数据量太大。改为1个亿
            for (int i = 0; i < set.size(); i++) {
                boolean b = set.get(i);


                if(b){
//                    out.append(i+"\r\n");
                    out.write(i+"\r\n");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        String dir = "/Users/moyong/project/env-myopensource/1-spring/12-spring/rest-api/target/";
//        String file2 = "bigfile.dat";
//
//
//        File file = new File(dir+file2);
//
//        //Delete the file; we will create a new file
//        file.delete();
//
//
//
//        int count1 = 100000000;
//        RandomAccessFile memoryMappedFile = new RandomAccessFile(dir+file2, "rw");
//        MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 10485760*100);
//        for (int i = 0; i < count1; i++) {
//            out.put((i+"\n").getBytes());
//        }
//        memoryMappedFile.close();


//        for (int i = 0; i < count; i++) {
//            System.out.print((char) out.get(i));
//        }

//
//        // Get file channel in readonly mode
//        FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();
//
//        // Get direct byte buffer access using channel.map() operation
//        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096 * 8 * 8);
//
//        //Write the content using put methods
//
//
//
//
//        for (int i = 0; i < 100000000; i++) {
//            boolean b = set.get(i);
//
//            if(b){
//                buffer.put((i+"\r\n").getBytes());
//            }
//        }
//
//        fileChannel.close();

        long beforeMemory9 = Runtime.getRuntime().totalMemory();
        long start9=System.currentTimeMillis();
        System.out.println("总共内存使用:" + (beforeMemory9 - beforeMemory8) / 1024 / 1024 + "MB");
        System.out.println("存入硬盘耗时:"+(start9-start8)/60+"秒");


    }

    public void writeToFileNIOWay2(File file) throws IOException {
        final int numberOfIterations = 1000000;
        final String messageToWrite = "This is a test üüüüüüööööö";
        final byte[] messageBytes = messageToWrite.
                getBytes(Charset.forName("ISO-8859-1"));
        final long appendSize = numberOfIterations * messageBytes.length;
        final RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.seek(raf.length());
        final FileChannel fc = raf.getChannel();

        final MappedByteBuffer mbf = fc.map(FileChannel.MapMode.READ_WRITE, fc.
                position(), appendSize);


        fc.close();
        for (int i = 1; i < numberOfIterations; i++) {
            mbf.put(messageBytes);
        }
    }


    public static void method3(String fileName, String content) {
        try {
// 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
// 文件长度，字节数
            long fileLength = randomFile.length();
// 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content + "\r\n");
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void method2(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent+"\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}