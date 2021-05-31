package com.example.serotoninapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.serotoninapp.model.Feed;
import com.example.serotoninapp.model.entry.Entry;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String BASE_URL = "https://www.reddit.com/r/";

    private static final String CHANNEL_ID = "id";

    private static List<String> URL_LIST =  new ArrayList<String>();

    public static final String EXTRA_MESSAGE = "com.example.serotoninapp.MESSAGE";

    public static boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigationView.getMenu().getItem(0).setChecked(true);

        if (!flag) {
            createNotificationChannel();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

        FeedAPI feedAPI = retrofit.create(FeedAPI.class);

        Call<Feed> call = feedAPI.getFeed();

        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(@NotNull Call<Feed> call, @NotNull Response<Feed> response) {
                Log.d(TAG, "onResponse: feed: " + response.body().toString());
                Log.d(TAG, "onResponse: Server Response: " + response.toString());

                List<Entry> entries = response.body().getEntrys();

                for (Entry entry : entries) {

                    String content = entry.getContent();

                    String contentSubstring = StringUtils.substringBetween(content, "<span><a href=", ">[link]");

                    //String noQuotes = contentSubstring.replace("\"", "");

                    URL_LIST.add(contentSubstring);
                }
                flag = true;
                URL_LIST.remove(0);
                Log.d(TAG, "Num Elements: " + URL_LIST.size());
                Log.d(TAG, "URL ARRAY CONTENTS:" + URL_LIST.toString());

            }

            @Override
            public void onFailure(@NotNull Call<Feed> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS:" + t.getMessage());

            }
        });
    }
    }

    public void getLink(View view) {
        Button button = (Button) findViewById(R.id.button);
        button.setVisibility(View.GONE);

        int index = (int)(Math.random() * URL_LIST.size());
        String link2 = URL_LIST.get(index);
        String noQuote = link2.substring(1, link2.length() - 1);
        Log.d(TAG,link2.toString());
        Log.d(TAG,noQuote.toString());

        Intent intent = new Intent(this, DisplayLinkActivity.class);
        String link = "https://wsvn.com/entertainment/rappers-young-thug-gunna-pay-bail-for-low-level-offenders-in-atlanta/";
        Log.d(TAG,link.toString());
        intent.putExtra(EXTRA_MESSAGE,noQuote);
        startActivity(intent);



    }

    public void setHome(MenuItem menuItem) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void setAlarm(MenuItem menuItem){
        Intent intent = new Intent(this,DisplayAlarmActivity.class);
        startActivity(intent);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}