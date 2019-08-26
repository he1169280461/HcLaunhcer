package com.androidplugin.hclaunhcer;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class AppInfo {
    private Drawable appicon;
    private String appname;
    private String apppackagename;

    public AppInfo(Drawable appicon, String appname, String apppackagename) {
        this.appicon = appicon;
        this.appname = appname;
        this.apppackagename = apppackagename;
    }

    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getApppackagename() {
        return apppackagename;
    }

    public void setApppackagename(String apppackagename) {
        this.apppackagename = apppackagename;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appicon=" + appicon +
                ", appname='" + appname + '\'' +
                ", apppackagename='" + apppackagename + '\'' +
                '}';
    }
}
