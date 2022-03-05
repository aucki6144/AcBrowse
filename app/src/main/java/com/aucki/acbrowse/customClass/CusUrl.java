package com.aucki.acbrowse.customClass;

import android.util.Log;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CusUrl {

    private String url;
    private String title;

    public CusUrl(String url){
        this.url = url;
    }

    public CusUrl(String url, String title){
        this.url = url;
        this.title = title;
    }

    public boolean isURL(){
        String cusUrl = this.url;

//        isPath(cusUrl);

        boolean is_url = false;

        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";

        Pattern pat = Pattern.compile(regex.trim());//比对
        Matcher mat = pat.matcher(cusUrl.trim());
        is_url = mat.matches();
        return is_url;
    }


    public String urlString(){
        return this.url;
    }

    public String urlTitle(){
        return this.title;
    }

}
