package com.aucki.acbrowse.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aucki.acbrowse.R;
import com.aucki.acbrowse.customClass.CusUrl;
import com.aucki.acbrowse.customClass.Settings;

import org.w3c.dom.Text;

public class SetHomepageActivity extends AppCompatActivity {

    private Settings settings = new Settings(SetHomepageActivity.this);

    private Button mButtonCancel;
    private Button mButtonConfirm;
    private TextView mShowCurrentUrl;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_homepage);

        mButtonCancel = findViewById(R.id.button_cancel_change);
        mButtonConfirm = findViewById(R.id.button_confirm_change);
        mShowCurrentUrl = findViewById(R.id.current_url);
        mEditText = findViewById(R.id.to_url);

        mButtonCancel.setBackgroundColor(Color.TRANSPARENT);
        mButtonConfirm.setBackgroundColor(Color.TRANSPARENT);
        mShowCurrentUrl.setText(settings.getHomepage());
//        getSupportActionBar().hide();

        mButtonCancel.setOnClickListener(view -> {
            onBackPressed();
        });

        mButtonConfirm.setOnClickListener(view ->{
            String targetUrl = new String(String.valueOf(mEditText.getText()));
            CusUrl cusUrl = new CusUrl(targetUrl);
            if (cusUrl.isURL()){
                boolean isChanged = settings.changeHomepage(targetUrl);
                if (isChanged){
                    Toast.makeText(this, "Changed successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Change fail", Toast.LENGTH_SHORT).show();
                }
                onBackPressed();
            }else{
                Toast.makeText(this, "Not a url", Toast.LENGTH_SHORT).show();
            }
        });

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i){
                    case EditorInfo.IME_ACTION_DONE:{
                        String targetUrl = new String(String.valueOf(mEditText.getText()));
                        CusUrl cusUrl = new CusUrl(targetUrl);
                        if (cusUrl.isURL()){
                            boolean isChanged = settings.changeHomepage(targetUrl);
                            if (isChanged){
                                Toast.makeText(SetHomepageActivity.this, "Changed successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SetHomepageActivity.this, "Change fail", Toast.LENGTH_SHORT).show();
                            }
                            onBackPressed();
                        }else{
                            Toast.makeText(SetHomepageActivity.this, "Not a url", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }

                }
                return true;
            }
        });

    }
}