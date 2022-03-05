package com.aucki.acbrowse.ifs;

import android.content.Context;

import com.aucki.acbrowse.customClass.CusUrl;

import java.util.List;

public interface CusUrlList {
    List<CusUrl> getUrlList(Context context);

    String getJsonList(Context context);

    int getLength(Context context);

    void initial(Context context);
    void addItem(Context context, CusUrl cusUrl);
    void deleteItem(Context context, CusUrl cusUrl);
    void deleteAll(Context context);
}
