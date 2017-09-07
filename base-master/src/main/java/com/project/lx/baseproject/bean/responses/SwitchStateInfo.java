package com.project.lx.baseproject.bean.responses;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/7/6 14:18
 * @company:
 * @email: lx802315@163.com
 */
public class SwitchStateInfo {
    /**
     * letterMesFlag : 1
     * sysMesFlag : 0
     */

    private int letterMesFlag;
    private int sysMesFlag;

    public int getLetterMesFlag() {
        return letterMesFlag;
    }

    public void setLetterMesFlag(int letterMesFlag) {
        this.letterMesFlag = letterMesFlag;
    }

    public int getSysMesFlag() {
        return sysMesFlag;
    }

    public void setSysMesFlag(int sysMesFlag) {
        this.sysMesFlag = sysMesFlag;
    }
}
