package com.aucki.acbrowse.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.aucki.acbrowse.R;
import com.aucki.acbrowse.activity.SettingsActivity;
import com.aucki.acbrowse.customClass.BookMarks;
import com.aucki.acbrowse.customClass.CusUrl;
import com.aucki.acbrowse.customClass.Favors;
import com.aucki.acbrowse.customClass.Histories;
import com.aucki.acbrowse.customClass.Settings;


public class BrowseFragment extends Fragment {

    /**
     * load view instants here
     */
    private Button mButtonUrlShow;
    private Button mButtonCancel;
    private Button mButtonCancelFavor;
    private Button mButtonBookMark;
    private Button mButtonConfirmFavor;
    private ImageButton mButtonRefresh;
    private ImageButton mButtonGoBack;
    private ImageButton mButtonGoForward;
    private ImageButton mButtonFavor;

    private WebView mWebView;
    private LinearLayout mBrowseBarNormal;
    private LinearLayout mBrowseBarType;
    private LinearLayout mFavorPage;
    private EditText editText;
    private InputMethodManager im;
    private ProgressBar mProgressBar;
    private TextView mFavorShowUrl;
    private TextView mFavorShowTitle;

    private String currentUrl;
    private String currentTitle;
    private String urlFromActivity;

    private final BookMarks bookmarks = new BookMarks();
    private final Favors favors = new Favors();
    private final Histories histories = new Histories();

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_browse, container, false);
        context = this.getActivity();

        //initial Storage
        bookmarks.initial(context);
        favors.initial(context);
        histories.initial(context);

        //Get message from Activity
        Bundle bundle = getArguments();
        urlFromActivity = bundle.getString("pass_url");
        CusUrl toLoadUrl = new CusUrl(urlFromActivity);

        //initial View instants
        mBrowseBarNormal = (LinearLayout) v.findViewById(R.id.browse_bar_normal);
        mBrowseBarType = (LinearLayout) v.findViewById(R.id.browse_bar_type);
        editText = (EditText) v.findViewById(R.id.edit_url);
        mButtonCancel = (Button) v.findViewById(R.id.button_cancel);
        mButtonUrlShow = (Button) v.findViewById(R.id.button_show_url);
        mButtonRefresh = (ImageButton) v.findViewById(R.id.button_side_refresh);
        mButtonGoBack = (ImageButton) v.findViewById(R.id.button_go_back);
        mButtonGoForward = (ImageButton) v.findViewById(R.id.button_go_forward);
        mButtonFavor = (ImageButton) v.findViewById(R.id.button_favor);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar_browse);
        mButtonCancelFavor = (Button) v.findViewById(R.id.button_cancel_favor);
        mButtonBookMark = (Button) v.findViewById(R.id.button_confirm_favor_bookmark);
        mButtonConfirmFavor = (Button) v.findViewById(R.id.button_confirm_favor);
        mFavorPage = (LinearLayout) v.findViewById(R.id.draw_layout_favor);
        mFavorShowUrl = (TextView) v.findViewById(R.id.text_view_favor_url);
        mFavorShowTitle = (TextView) v.findViewById(R.id.text_view_favor_title);

        //Set up UI
        mBrowseBarType.setVisibility(View.GONE);
        mFavorPage.setVisibility(View.GONE);

        mButtonCancel.setBackgroundColor(Color.TRANSPARENT);
        mButtonUrlShow.setBackgroundColor(Color.TRANSPARENT);
        mButtonRefresh.setBackgroundColor(Color.TRANSPARENT);
        mButtonGoBack.setBackgroundColor(Color.TRANSPARENT);
        mButtonGoForward.setBackgroundColor(Color.TRANSPARENT);
        mButtonFavor.setBackgroundColor(Color.TRANSPARENT);
        mButtonCancelFavor.setBackgroundColor(Color.TRANSPARENT);
        mButtonBookMark.setBackgroundColor(Color.TRANSPARENT);
        mButtonConfirmFavor.setBackgroundColor(Color.TRANSPARENT);

        //Set Listeners
        //button cancel
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBrowseBarType.setVisibility(View.GONE);
                mBrowseBarNormal.setVisibility(View.VISIBLE);
                im = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });

        //editText action Listener
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i){
                    case EditorInfo.IME_ACTION_GO:
                        String typeContent = String.valueOf(editText.getText());
                        mWebView.loadUrl(typeContent);
                        mBrowseBarType.setVisibility(View.GONE);
                        mBrowseBarNormal.setVisibility(View.VISIBLE);
                        im = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        im.hideSoftInputFromWindow(v.getWindowToken(),0);
                        break;
                }
                return true;
            }
        });

        //button url show
        mButtonUrlShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBrowseBarType.setVisibility(View.VISIBLE);

                mBrowseBarNormal.setVisibility(View.GONE);
                im = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                editText.requestFocus();
                im.showSoftInput(editText,0);
            }
        });

        //button refresh
        mButtonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.loadUrl(currentUrl);
            }
        });

        //button go back
        mButtonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebView.canGoBack()){
                    mWebView.goBack();
                }else{
                    Log.d("TAG", "onClick: can not go back");
                }
            }
        });

        //button go forward
        mButtonGoForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mWebView.canGoForward()){
                    mWebView.goForward();
                }else{
                    Log.d("TAG", "onClick: can not go forward");
                }
            }
        });

        //button favor
        mButtonFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFavorPage.getVisibility() == View.GONE){
                    showFavorPage();
                }else{
                    hideFavorPage();
                }
            }
        });

        mButtonCancelFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideFavorPage();
            }
        });

        mButtonConfirmFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favors.addItem(context,new CusUrl(currentUrl,currentTitle));
                hideFavorPage();
                Toast.makeText(context, "Favor Added", Toast.LENGTH_SHORT).show();
            }
        });

        mButtonBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarks.addItem(context,new CusUrl(currentUrl,currentTitle));
                hideFavorPage();
                Toast.makeText(context, "Bookmark Added", Toast.LENGTH_SHORT).show();
            }
        });

        //Set up web view and progressbar
        //Progress bar
        mProgressBar.setMax(100);

        //web view
        mWebView = (WebView) v.findViewById(R.id.web_view_browse);
        mWebView.clearCache(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setAppCacheMaxSize(1000);
        mWebView.getSettings().getAllowContentAccess();
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {

//            let webView load urls without http or https
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null || url.startsWith("http://") || url.startsWith("https://")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                } catch (Exception e) {
                    Log.i("TAG", "shouldOverrideUrlLoading Exception:" + e);
                    return true;
                }
            }

//            url callback
            @Override
            public void onLoadResource(WebView view, String url) {
                currentUrl = view.getUrl();
                currentTitle =view.getTitle();
                mButtonUrlShow.setText(currentTitle);
                CusUrl cusUrl = new CusUrl(currentUrl,currentTitle);
                histories.addItem(context, cusUrl);
                super.onLoadResource(view, url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int newProgress){
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                }else{
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });

        if (toLoadUrl.isURL() || toLoadUrl.urlString().equals("https://github.com/aucki6144/AcBrowser") || toLoadUrl.urlString().equals("file:///android_asset/web_page/about.html")){
            mWebView.loadUrl(toLoadUrl.urlString());
        }else{
            mWebView.loadUrl("www.bing.com");
        }

        Log.d("debug_shared",String.valueOf(favors.getLength(context)));


        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("debug_fragment","destroyed");
        mWebView.clearCache(true);
    }

    /**
     * Show Favor $ Bookmark page with an animation effect
     * move from up to down
     * global view elements required
     */
    private void showFavorPage(){
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, -1, TranslateAnimation.RELATIVE_TO_SELF, 0);
        ctrlAnimation.setDuration(200);     //设置动画的过渡时间

        mFavorPage.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFavorPage.startAnimation(ctrlAnimation);
                mFavorPage.setVisibility(View.VISIBLE);
            }
        }, 100);

        mFavorShowUrl.setText(currentUrl);
        mFavorShowTitle.setText(currentTitle);
    }

    /**
     * Hide Favor $ Bookmark page with an animation effect
     * move from down to top
     * global view elements required
     */
    private void hideFavorPage(){
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, -1);
        ctrlAnimation.setDuration(200);     //设置动画的过渡时间

        mFavorPage.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFavorPage.startAnimation(ctrlAnimation);
                mFavorPage.setVisibility(View.GONE);
            }
        }, 100);

        mFavorShowUrl.setText(currentUrl);
        mFavorShowTitle.setText(currentTitle);
    }

}