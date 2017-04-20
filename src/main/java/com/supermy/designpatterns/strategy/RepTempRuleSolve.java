package com.supermy.designpatterns.strategy;

/**
 * Created by moyong on 17/2/22.
 * 算法或者规则切换
 */
public class RepTempRuleSolve {
    private RepTempRule strategy;

    public RepTempRuleSolve(RepTempRule rule){
        this.strategy=rule;
        }

    public String getNewContext(String site,String oldString) throws Exception {
        strategy.setOldString(oldString);
        strategy.replace();
        return strategy.getNewString();
    }

    public void changeAlgorithm(RepTempRule newAlgorithm) {
        strategy = newAlgorithm;
    }
}