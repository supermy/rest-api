package com.supermy.microcloud;

/**
 * Created by moyong on 17/3/30.
 */
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedFile {

    // 文件名
    private String fileName;

    // 文件所在目录路径
    private String fileDirPath;

    // 文件对象
    private File file;

    private MappedByteBuffer mappedByteBuffer;
    private FileChannel fileChannel;
    private boolean boundSuccess = false;

    // 文件最大只能为50MB   5G
    private final static long MAX_FILE_SIZE = 1024 * 1024 * 2000;

    // 最大的脏数据量512KB,系统必须触发一次强制刷
    private long MAX_FLUSH_DATA_SIZE = 1024 * 512 * 8;

    // 最大的刷间隔,系统必须触发一次强制刷
    private long MAX_FLUSH_TIME_GAP = 1000;

    // 文件写入位置
    private long writePosition = 0;

    // 最后一次刷数据的时候
    private long lastFlushTime;

    // 上一次刷的文件位置
    private long lastFlushFilePosition = 0;

    public MappedFile(String fileName, String fileDirPath) {
        super();
        this.fileName = fileName;
        this.fileDirPath = fileDirPath;
        this.file = new File(fileDirPath + "/" + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * 内存映照文件绑定
     * @return
     */
    public synchronized boolean boundChannelToByteBuffer() {
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            this.fileChannel = raf.getChannel();
        } catch (Exception e) {
            e.printStackTrace();
            this.boundSuccess = false;
            return false;
        }

        try {
            this.mappedByteBuffer = this.fileChannel
                    .map(FileChannel.MapMode.READ_WRITE, 0, MAX_FILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
            this.boundSuccess = false;
            return false;
        }

        this.boundSuccess = true;
        return true;
    }

    /**
     * 写数据：先将之前的文件删除然后重新
     * @param data
     * @return
     */
    public synchronized boolean writeData(byte[] data) {

        return false;
    }

    /**
     * 在文件末尾追加数据
     * @param data
     * @return
     * @throws Exception
     */
    public synchronized boolean appendData(byte[] data) throws Exception {
        if (!boundSuccess) {
            boundChannelToByteBuffer();
        }

        writePosition = writePosition + data.length;
        if (writePosition >= MAX_FILE_SIZE) {   // 如果写入data会超出文件大小限制，不写入
            flush();
            writePosition = writePosition - data.length;
            System.out.println("File="
                    + file.toURI().toString()
                    + " is written full.");
            System.out.println("already write data length:"
                    + writePosition
                    + ", max file size=" + MAX_FILE_SIZE);
            return false;
        }

        this.mappedByteBuffer.put(data);

        // 检查是否需要把内存缓冲刷到磁盘
        if ( (writePosition - lastFlushFilePosition > this.MAX_FLUSH_DATA_SIZE)
                ||
                (System.currentTimeMillis() - lastFlushTime > this.MAX_FLUSH_TIME_GAP
                        && writePosition > lastFlushFilePosition) ) {
            flush();   // 刷到磁盘
        }

        return true;
    }

    public synchronized void flush() {
        this.mappedByteBuffer.force();
        this.lastFlushTime = System.currentTimeMillis();
        this.lastFlushFilePosition = writePosition;
    }

    public long getLastFlushTime() {
        return lastFlushTime;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileDirPath() {
        return fileDirPath;
    }

    public boolean isBundSuccess() {
        return boundSuccess;
    }

    public File getFile() {
        return file;
    }

    public static long getMaxFileSize() {
        return MAX_FILE_SIZE;
    }

    public long getWritePosition() {
        return writePosition;
    }

    public long getLastFlushFilePosition() {
        return lastFlushFilePosition;
    }

    public long getMAX_FLUSH_DATA_SIZE() {
        return MAX_FLUSH_DATA_SIZE;
    }

    public long getMAX_FLUSH_TIME_GAP() {
        return MAX_FLUSH_TIME_GAP;
    }

}