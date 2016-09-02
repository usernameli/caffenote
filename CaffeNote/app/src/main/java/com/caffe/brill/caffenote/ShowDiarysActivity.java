package com.caffe.brill.caffenote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

public class ShowDiarysActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_diarys);
        final Bundle bundle = getIntent().getExtras();
        final String title = bundle.getString("title");
        TextView diaryTitle = (TextView) findViewById(R.id.diarytitle);
        diaryTitle.setText(title);
        TextView edit = (TextView) findViewById(R.id.editDiary);
        String diarysText = bundle.getString("diarys");
        final String time = bundle.getString("time");
        final String finalDiarysText = diarysText;
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ShowDiarysActivity.this, EditActivity.class);
                Bundle bundle1 = new Bundle();
                bundle.putString("title", title);
                bundle.putString("diary", finalDiarysText);
                bundle.putString("time", time);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            }
        });
        //  diarysText.replace("src=\"","src=\"file:/");
        diarysText = "<html>"
                + "<body>" + diarysText + "</body>" + "</html>";
        System.out.println(diarysText);
        String imageUrl = "file:/storage/emulated/0/DCIM/P60623-161740.jpg";
        //     String data = "<HTML><IMG src=\""+imageUrl+"\""+"/>";
        // webview.loadDataWithBaseURL(imageUrl, data, "text/html", "utf-8", null);
        WebView webView = (WebView) findViewById(R.id.showdiarys);
        webView.loadDataWithBaseURL(imageUrl, diarysText, "text/html", "utf-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

    }
}
