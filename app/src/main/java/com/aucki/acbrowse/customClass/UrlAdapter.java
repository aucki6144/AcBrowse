//package com.aucki.acbrowse.customClass;
//
//import android.icu.text.Transliterator;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import com.aucki.acbrowse.R;
//public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.ViewHolder>{
//
//    private List<CusUrl> mCusUrlList;
//
//    public UrlAdapter(List<CusUrl> list) {
//        this.mCusUrlList = list;
//    }
//
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.url_list_item, parent, false);
//        ViewHolder viewHolder = new ViewHolder(v);
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = viewHolder.getAdapterPosition();
//                CusUrl url = mCusUrlList.get(position);
////                Log.d("debug_recycle", "onClick:"+url.urlString());
//            }
//        });
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        // 绑定数据
//        CusUrl cusUrl = mCusUrlList.get(position);
//        holder.mTextView.setText(cusUrl.urlString());
//        holder.mTitle.setText(cusUrl.urlTitle());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mCusUrlList == null ? 0 : mCusUrlList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView mTextView;
//        TextView mTitle;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            mTextView = (TextView) itemView.findViewById(R.id.item_tv);
//            mTitle = (TextView) itemView.findViewById(R.id.item_title);
//        }
//    }
//}