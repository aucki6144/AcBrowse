package com.aucki.acbrowse.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aucki.acbrowse.R;
import com.aucki.acbrowse.customClass.BookMarks;
import com.aucki.acbrowse.customClass.CusUrl;
import com.aucki.acbrowse.customClass.Favors;
import com.aucki.acbrowse.customClass.Histories;
//import com.aucki.acbrowse.customClass.UrlAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UrlListActivity extends AppCompatActivity {

    private ImageButton mButtonCloseUrlPage;

    private TextView mTitle;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<String> titleList = new ArrayList<>();
    private List<CusUrl> urlList;

    private CusUrl callBackUrl;

    private BookMarks bookmarks = new BookMarks();
    private Favors favors = new Favors();
    private Histories histories = new Histories();

    //TODO
    //把Adapter 类封装到外部，用接口进行调用

    private class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.ViewHolder>{

        private List<CusUrl> mCusUrlList;

        public UrlAdapter(List<CusUrl> list) {
            Collections.reverse(list);
            this.mCusUrlList = list;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.url_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    CusUrl url = mCusUrlList.get(position);
                    callBackUrl = url;
                    setCallBackResult(callBackUrl);
                    onBackPressed();
                }
            });
//            viewHolder.mImageButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("debug_temp","hhhh");
//                }
//            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // 绑定数据
            CusUrl cusUrl = mCusUrlList.get(position);
            holder.mTextView.setText(cusUrl.urlString());
            holder.mTitle.setText(cusUrl.urlTitle());
//            holder.mImageButton.setBackgroundColor(Color.TRANSPARENT);
        }

        @Override
        public int getItemCount() {
            return mCusUrlList == null ? 0 : mCusUrlList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView mTextView;
            TextView mTitle;

//            ImageButton mImageButton;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.item_tv);
                mTitle = (TextView) itemView.findViewById(R.id.item_title);
//                mImageButton = (ImageButton) itemView.findViewById(R.id.delete_url_item);
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_list);

        int get_request_code = getIntent().getIntExtra("REQUEST_CODE",4);

        titleList.add("Bookmarks");
        titleList.add("Favor");
        titleList.add("History");
        titleList.add("List");

        mButtonCloseUrlPage = (ImageButton) findViewById(R.id.button_close_url_page);
        mTitle = (TextView) findViewById(R.id.url_list_name);

        mButtonCloseUrlPage.setBackgroundColor(Color.TRANSPARENT);
        mTitle.setText(titleList.get(get_request_code));

        switch (get_request_code){
            case (0):{
                urlList = new BookMarks().getUrlList(UrlListActivity.this);
                break;
            }
            case (1):{
                urlList = new Favors().getUrlList(UrlListActivity.this);
                break;
            }
            case (2):{
                urlList = histories.getUrlList(UrlListActivity.this);
                break;
            }
            default:{
            }
        }

        mButtonCloseUrlPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initData();
        initView();

    }

    private void setCallBackResult(CusUrl url){
        Intent intent = new Intent();
        intent.putExtra("call_back_url",url.urlString());
        UrlListActivity.this.setResult(RESULT_OK,intent);
    }

    //
    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new UrlAdapter(urlList);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
    }

}