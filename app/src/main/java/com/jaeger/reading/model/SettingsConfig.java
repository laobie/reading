package com.jaeger.reading.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsConfig {
    private boolean loadCoverMode;
    private String loadCoverQuality;
    private int loadNum;
    private boolean receiveNoticeMode;
    private String receiveNoticeTime;

    public SettingsConfig(Context context) {
        SharedPreferences config = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        this.loadCoverMode = config.getBoolean("loadCoverMode", false);
        this.loadCoverQuality = config.getString("loadCoverQuality", "一般");
        this.loadNum = config.getInt("loadNum", 3);
        this.receiveNoticeMode = config.getBoolean("receiveNoticeMode", false);
        this.receiveNoticeTime = config.getString("receiveNoticeTime", "20:00");
    }

    public boolean isLoadCoverMode() {
        return loadCoverMode;
    }

    public void setLoadCoverMode(boolean loadCoverMode) {
        this.loadCoverMode = loadCoverMode;
    }

    public String getLoadCoverQuality() {
        return loadCoverQuality;
    }

    public void setLoadCoverQuality(String loadCoverQuality) {
        this.loadCoverQuality = loadCoverQuality;
    }

    public int getLoadNum() {
        return loadNum;
    }

    public void setLoadNum(int loadNum) {
        this.loadNum = loadNum;
    }

    public boolean isReceiveNoticeMode() {
        return receiveNoticeMode;
    }

    public void setReceiveNoticeMode(boolean receiveNoticeMode) {
        this.receiveNoticeMode = receiveNoticeMode;
    }

    public String getReceiveNoticeTime() {
        return receiveNoticeTime;
    }

    public void setReceiveNoticeTime(String receiveNoticeTime) {
        this.receiveNoticeTime = receiveNoticeTime;
    }
}
