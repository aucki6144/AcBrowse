package com.aucki.acbrowse.customClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.aucki.acbrowse.ifs.CusUrlList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Histories implements CusUrlList {

    protected String SHARED_PREFERENCE_KEY = "getHistoryStorage";

    @Override
    public void initial(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        String jsonData = this.getJsonList(context);
        if (jsonData == "NONE"){
            List<CusUrl> list = new ArrayList<CusUrl>();
            list.add(new CusUrl("www.bing.com","Bing"));
            String str = translateListToJson(list);
            editor.putString(SHARED_PREFERENCE_KEY,str);
        }
        editor.apply();
    }

    @Override
    public List<CusUrl> getUrlList(Context context) {
        String jsonData = this.getJsonList(context);
        if(jsonData != "NONE"){
            List<CusUrl> list = translateJsonToList(jsonData);
            return list;
        }
        else{
            initial(context);
            return null;
        }
    }

    @Override
    public String getJsonList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                "data", Context.MODE_PRIVATE
        );
        String jsonData = prefs.getString(SHARED_PREFERENCE_KEY, "NONE");
        return jsonData;
    }

    @Override
    public int getLength(Context context) {
        String jsonData = this.getJsonList(context);
        if(jsonData == "NONE"){
            return 0;
        }else{
            List<CusUrl> list = translateJsonToList(jsonData);
            return list.size();
        }
    }

    @Override
    public void addItem(Context context, CusUrl cusUrl) {
        String jsonData = this.getJsonList(context);
        if(jsonData != "NONE" && cusUrl.urlTitle()!=null && !cusUrl.urlTitle().equals("")){
            List<CusUrl> list = translateJsonToList(jsonData);
            if (!list.get(list.size() - 1).urlTitle().equals(cusUrl.urlTitle())){
                deleteItem(context,cusUrl);
                list.add(cusUrl);
            }
            String str = translateListToJson(list);
            SharedPreferences.Editor editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
            editor.putString(SHARED_PREFERENCE_KEY,str);
            editor.apply();
        }else{
            initial(context);
        }
        return;
    }

    @Override
    public void deleteItem(Context context, CusUrl cusUrl) {
        String str = cusUrl.urlTitle();
        String jsonData = this.getJsonList(context);
        if(jsonData != "NONE"){
            List<CusUrl> list = translateJsonToList(jsonData);
            for (int index=0;index<list.size();index++){
                if (list.get(index).urlTitle().equals(str)){
                    list.remove(index);
                    String changed_str = translateListToJson(list);
                    SharedPreferences.Editor editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                    editor.putString(SHARED_PREFERENCE_KEY,changed_str);
                    editor.apply();
                }
            }
        }
        return;
    }

    @Override
    public void deleteAll(Context context) {
        String jsonData = this.getJsonList(context);
        SharedPreferences.Editor editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        if(jsonData != "NONE"){
            List<CusUrl> list = translateJsonToList(jsonData);
            list.clear();
            list.add(new CusUrl("www.bing.com","Bing"));
            String str = translateListToJson(list);
            editor.putString(SHARED_PREFERENCE_KEY,str);
        }
        editor.apply();
        return;
    }

    private List<CusUrl> translateJsonToList(String jsDt){
        Gson gson = new Gson();
        List<CusUrl> list = gson.fromJson(jsDt, new TypeToken<List<CusUrl>>(){}.
                getType());
        return list;
    }

    private String translateListToJson(List<CusUrl> list){
        Gson gson = new Gson();
        String str = gson.toJson(list);
        return str;
    }

}
