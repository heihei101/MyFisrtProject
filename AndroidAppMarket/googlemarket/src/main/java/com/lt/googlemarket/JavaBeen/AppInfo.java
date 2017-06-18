package com.lt.googlemarket.JavaBeen;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public class AppInfo {
    public int id;
    public String name;
    public String packageName;
    public String iconUrl;
    public float stars;
    public int size;
    public String downloadUrl;
    public String des;
    public String downloadNum;
    public String version;
    public String date;
    public String author;

    public List<String> screen;
    public List<Safe> safe;

    public class Safe{

        public String safeUrl;
        public String safeDesUrl;
        public String safeDes;
        public int safeDesColor;
    }
}
