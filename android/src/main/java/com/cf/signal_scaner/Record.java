package com.cf.signal_scaner;

/**
 * 信号记录
 */
public class Record {
    /**
     * 时间
     */
    private String Time;

    public int[] getTotalState() {
        return TotalState;
    }

    public void setTotalState(int[] totalState) {
        TotalState = totalState;
    }

    /**
     * 状态
     */
    private int[] TotalState = new int[8];

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
