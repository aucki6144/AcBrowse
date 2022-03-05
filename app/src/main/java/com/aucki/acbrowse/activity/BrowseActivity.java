package com.aucki.acbrowse.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aucki.acbrowse.R;
import com.aucki.acbrowse.customClass.CusUrl;
import com.aucki.acbrowse.customClass.Settings;
import com.aucki.acbrowse.fragment.BrowseFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

import java.util.Collections;
import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    private Button mButton1;
    private FrameLayout div_browse_container;
    private ImageButton mButtonDraw;
    private ImageButton mButtonDiv;
    private ImageButton mButtonGoHome;
    private ImageButton mButtonBookMark;
    private ImageButton mButtonPages;

    private View mDivider;
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerNav;
    private final Settings settings = new Settings(BrowseActivity.this);
//    private MaterialCardView mCard;


    private CusUrl callBackUrl;

    private int REQUEST_CODE_URL = 3;

    private String homepageUrl;

    private class UrlAdapter extends RecyclerView.Adapter<BrowseActivity.UrlAdapter.ViewHolder>{

        private List<CusUrl> mCusUrlList;

        public UrlAdapter(List<CusUrl> list) {
            Collections.reverse(list);
            this.mCusUrlList = list;
        }


        @Override
        public BrowseActivity.UrlAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.url_list_item, parent, false);
            BrowseActivity.UrlAdapter.ViewHolder viewHolder = new BrowseActivity.UrlAdapter.ViewHolder(v);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    CusUrl url = mCusUrlList.get(position);
                    callBackUrl = url;
                    onBackPressed();
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(BrowseActivity.UrlAdapter.ViewHolder holder, int position) {
            // 绑定数据
            CusUrl cusUrl = mCusUrlList.get(position);
            holder.mTextView.setText(cusUrl.urlString());
            holder.mTitle.setText(cusUrl.urlTitle());
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
        setContentView(R.layout.activity_browse);
        homepageUrl = new Settings(BrowseActivity.this).getHomepage();
        //initial page
        loadFragment(homepageUrl);
        settings.initial();

        //initial view instants
        div_browse_container = (FrameLayout) findViewById(R.id.div_fragment_container);
        mDivider = (View) findViewById(R.id.div_browse_divider);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mButtonDraw = (ImageButton) findViewById(R.id.button_side_draw);
        mButtonDiv = (ImageButton) findViewById(R.id.button_div);
        mButtonGoHome = (ImageButton) findViewById(R.id.button_go_Home);
        mButtonBookMark = (ImageButton) findViewById(R.id.button_book_mark);
        mDrawerNav = (NavigationView) findViewById(R.id.drawer_nav);
//        mCard = (MaterialCardView) findViewById(R.id.bookmarks_container);
//        mButtonPages = (ImageButton) findViewById(R.id.button_pages);

        //Set up UI
        div_browse_container.setVisibility(View.GONE);
        mDivider.setVisibility(View.GONE);
//        mCard.setVisibility(View.GONE);
        mButtonDraw.setBackgroundColor(Color.TRANSPARENT);
        mButtonDiv.setBackgroundColor(Color.TRANSPARENT);
        mButtonGoHome.setBackgroundColor(Color.TRANSPARENT);
        mButtonBookMark.setBackgroundColor(Color.TRANSPARENT);
//        mButtonPages.setBackgroundColor(Color.TRANSPARENT);

        //button listeners
        //button draw
        mButtonDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mDrawerNav.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Log.d("debug_nav", "onNavigationItemSelected: "+String.valueOf(item));
                        switch (String.valueOf(item)){
                            case "Setting":{
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent = new Intent(BrowseActivity.this, SettingsActivity.class);
                                startActivity(intent);
//                                Log.d("debug_nav", "Setting chosen: ");
                                break;
                            }
                            case "Github":{
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                //All fragment killed when loading github,fix after "pages" func added
                                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                                }
                                loadFragment("https://github.com/aucki6144/AcBrowse");
//                                Log.d("debug_nav", "Github chosen: ");
                                break;
                            }
                            case "About":{
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                //All fragment killed when loading github,fix after "pages" func added
                                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                                }
                                loadFragment("file:///android_asset/web_page/about.html");
                                break;
                            }
                                /*
                                REQUEST_CODE:
                                0 for bookmark list
                                1 for favor list
                                2 for history list
                                3 for error
                                 */
                            case "Bookmark":{
                                mDrawerLayout.closeDrawer(GravityCompat.START,true);
                                Intent intent = new Intent(BrowseActivity.this,UrlListActivity.class);
                                intent.putExtra("REQUEST_CODE",0);
                                startActivityForResult(intent, REQUEST_CODE_URL);
                                break;
                            }
                            case "Favor":{
                                mDrawerLayout.closeDrawer(GravityCompat.START,true);
                                Intent intent = new Intent(BrowseActivity.this,UrlListActivity.class);
                                intent.putExtra("REQUEST_CODE",1);
                                startActivityForResult(intent, REQUEST_CODE_URL);
                                break;
                            }
                            case "History":{
                                mDrawerLayout.closeDrawer(GravityCompat.START,true);
                                Intent intent = new Intent(BrowseActivity.this,UrlListActivity.class);
                                intent.putExtra("REQUEST_CODE",2);
                                startActivityForResult(intent, REQUEST_CODE_URL);
                                break;
                            }
                            default:{
//                                Log.d("debug_nav", "chosen error");
                            }
                        }

                        return true;
                    }
                }
        );

        //button div
        mButtonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (div_browse_container.getVisibility()==View.VISIBLE){
                    div_browse_container.setVisibility(View.GONE);
                    mDivider.setVisibility(View.GONE);
                }else{
                    div_browse_container.setVisibility(View.VISIBLE);
                    mDivider.setVisibility(View.VISIBLE);
                    loadDivFragment(homepageUrl);
                }
            }
        });

        //lock gesture for drawer layout
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,GravityCompat.START);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,GravityCompat.START);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,GravityCompat.START);
            }

            @Override
            public void onDrawerStateChanged(int newState) {}
        });

        //button go home
        mButtonGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kill fragment first
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
                loadFragment(homepageUrl);
            }
        });

        //button book mark
        mButtonBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START,true);
                Intent intent = new Intent(BrowseActivity.this,UrlListActivity.class);
                intent.putExtra("REQUEST_CODE",0);
                startActivityForResult(intent, REQUEST_CODE_URL);
//                overridePendingTransition(R.anim.);
            }
        });

        //button pages

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("debug_activity", "onActivityResult: "+String.valueOf(resultCode));
        if (resultCode != Activity.RESULT_OK){
            return;
        }else{
            if (data == null){
                return;
            }
            String str = new String();
            str = data.getStringExtra("call_back_url");
            CusUrl cusUrl= new CusUrl(str);
            if(cusUrl.isURL()) {
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
                loadFragment(cusUrl.urlString());
            }
        }
    }

    private void loadFragment(String passURL){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_fragment_container);
        if (passURL != null){
            fragment = new BrowseFragment();
            Bundle bundle = new Bundle();
            bundle.putString("pass_url", passURL);
            fragment.setArguments(bundle);
            fm.beginTransaction()
                    .add(R.id.main_fragment_container, fragment)
                    .commit();
        }
    }

    private void loadDivFragment(String passURL){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.div_fragment_container);
        if (passURL != null){
            fragment = new BrowseFragment();
            Bundle bundle = new Bundle();
            bundle.putString("pass_url", passURL);
            fragment.setArguments(bundle);
            fm.beginTransaction()
                    .add(R.id.div_fragment_container, fragment)
                    .commit();
        }
    }

//    /**
//     * Show Favor $ Bookmark page with an animation effect
//     * move from up to down
//     * global view elements required
//     */
//    private void showBookmarkPage(){
//        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
//                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
//                TranslateAnimation.RELATIVE_TO_SELF, 1, TranslateAnimation.RELATIVE_TO_SELF, 0);
//        ctrlAnimation.setDuration(200);     //设置动画的过渡时间
//
//        mCard.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCard.startAnimation(ctrlAnimation);
//                mCard.setVisibility(View.VISIBLE);
//            }
//        }, 100);
//    }
//
//    /**
//     * Hide Favor $ Bookmark page with an animation effect
//     * move from down to top
//     * global view elements required
//     */
//    private void hideBookmarkPage(){
//        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
//                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
//                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 1);
//        ctrlAnimation.setDuration(150);     //设置动画的过渡时间
//
//        mCard.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCard.startAnimation(ctrlAnimation);
//                mCard.setVisibility(View.GONE);
//            }
//        }, 100);
//
//    }


}