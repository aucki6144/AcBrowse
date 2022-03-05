package com.aucki.acbrowse.customClass;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    private String homepage;
    private String jsonData;
    private Context context;

    private final String KEY_HOMEPAGE = "home_page_url";

    public Settings (Context c){
        context = c;
    }

    public void initial() {
        SharedPreferences.Editor editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        jsonData = this.getJson();
        if (jsonData == "NONE"){
            editor.putString(KEY_HOMEPAGE,"www.bing.com");
        }
        editor.apply();
    }

    public String getHomepage(){
        jsonData = this.getJson();
        Gson gson = new Gson();
        String homepageUrl = gson.fromJson(jsonData,String.class);
        return homepageUrl;
    }

    public boolean changeHomepage(String target_url){
        SharedPreferences.Editor editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        jsonData = this.getJson();
        CusUrl cusUrl = new CusUrl(target_url);
        if (cusUrl.isURL()){
            editor.putString(KEY_HOMEPAGE,target_url).apply();
            return true;
        }else{
            return false;
        }
    }

    public String getJson() {
        SharedPreferences prefs = context.getSharedPreferences(
                "data", Context.MODE_PRIVATE
        );
        String jsonData = prefs.getString(KEY_HOMEPAGE, "NONE");
        return jsonData;
    }

}
