package com.example.rh.daily.bing;


import java.io.Serializable;

/**
 * @author RH
 * @date 2017/12/13
 */

public class BingDailyBean implements Serializable {
    public String date;
    private String url;
    private String copyright;

    public BingDailyBean() {
    }

    public BingDailyBean(String date, String url) {
        this.date = date;
        this.url = url;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return "https://www.bing.com" + url;
    }

    public String getCopyright() {
        return copyright;
    }
}
