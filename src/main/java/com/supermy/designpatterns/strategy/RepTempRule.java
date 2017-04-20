package com.supermy.designpatterns.strategy;

/**
 * Created by moyong on 17/2/22.
 * 策略模式
 * <p/>
 * Strategy适合下列场合:
 * 1.以不同的格式保存文件;
 * 2.以不同的算法压缩文件;
 * 3.以不同的算法截获图象;
 * 4.以不同的格式输出同样数据的图形,比如曲线 或框图bar等
 */
public abstract class RepTempRule {

    protected String oldString = "";

    public void setOldString(String oldString) {
        this.oldString = oldString;
    }

    protected String newString = "";

    public String getNewString() {
        return newString;
    }


    public abstract void replace() throws Exception;


}
