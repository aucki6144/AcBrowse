package com.aucki.acbrowse.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aucki.acbrowse.R;
import com.aucki.acbrowse.customClass.BookMarks;
import com.aucki.acbrowse.customClass.Favors;
import com.aucki.acbrowse.customClass.Histories;
import com.aucki.acbrowse.customClass.Settings;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton mButtonCloseSetting;
    private Button mButtonChangeHomepage;
    private Button mButtonClearCache;
    private Button mButtonClearHistory;
    private Button mButtonClearBookmark;
    private Button mButtonClearFavor;

    private BookMarks bookmarks = new BookMarks();
    private Favors favors = new Favors();
    private Histories histories = new Histories();
    private Settings settings = new Settings(SettingsActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings.initial();
        Log.d("debug_temp", "received: "+settings.getHomepage());

        mButtonCloseSetting = (ImageButton) findViewById(R.id.button_close_setting);
        mButtonChangeHomepage= (Button) findViewById(R.id.button_change_homepage);
        mButtonClearCache= (Button) findViewById(R.id.button_clear_cache);
        mButtonClearHistory= (Button) findViewById(R.id.button_clear_history);
        mButtonClearBookmark= (Button) findViewById(R.id.button_clear_bookmark);
        mButtonClearFavor= (Button) findViewById(R.id.button_clear_favor);

        mButtonCloseSetting.setBackgroundColor(Color.TRANSPARENT);
        mButtonChangeHomepage.setBackgroundColor(Color.TRANSPARENT);
        mButtonClearCache.setBackgroundColor(Color.TRANSPARENT);
        mButtonClearHistory.setBackgroundColor(Color.TRANSPARENT);
        mButtonClearBookmark.setBackgroundColor(Color.TRANSPARENT);
        mButtonClearFavor.setBackgroundColor(Color.TRANSPARENT);

        mButtonChangeHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,SetHomepageActivity.class);
                startActivity(intent);
            }
        });

        mButtonCloseSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mButtonClearCache.setOnClickListener(view -> {
            Toast.makeText(SettingsActivity.this, "Cache cleared", Toast.LENGTH_SHORT).show();

            //TODO
        });

        mButtonClearBookmark.setOnClickListener(view -> {
            Toast.makeText(SettingsActivity.this, "All bookmarks deleted", Toast.LENGTH_SHORT).show();
            bookmarks.deleteAll(SettingsActivity.this);
        });

        mButtonClearFavor.setOnClickListener(view -> {
            Toast.makeText(SettingsActivity.this, "All favors deleted", Toast.LENGTH_SHORT).show();
            favors.deleteAll(SettingsActivity.this);
        });

        mButtonClearHistory.setOnClickListener(view -> {
            Toast.makeText(SettingsActivity.this, "All histories deleted", Toast.LENGTH_SHORT).show();
            histories.deleteAll(SettingsActivity.this);
        });

    }
}