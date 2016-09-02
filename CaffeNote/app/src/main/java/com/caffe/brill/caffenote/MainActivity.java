package com.caffe.brill.caffenote;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.LruCache;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.caffe.adapter.NoteAdapter;
import com.caffe.brill.sqlite.DataBaseHelper;
import com.caffe.model.NoteModel;
import com.caffe.view.DynamicHeightImageView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView noteListRecyclerView;
    private List<NoteModel> noteModelList;
    private ImageButton addNote;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private final int DEVIDER = 14;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NoteAdapter noteAdapter;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            noteAdapter.notifyDataSetChanged();
            //   swipeRefreshLayout.setRefreshing(false);
            return true;
        }
    });
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  initDataBase();
        InitView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initDataBase() {
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, "diary_db");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("diarys", null, null, null, null, null, null);
        NoteModel noteModel;
        while (cursor.moveToNext()) {
            noteModel = new NoteModel();
           /* if (i % 2 == 0) {
                DynamicHeightImageView dynamicHeightImageView = new DynamicHeightImageView(MainActivity.this);
                dynamicHeightImageView.setBackgroundResource(R.drawable.hela);
                noteModel.setDynamicHeightImageView(dynamicHeightImageView);
            } else*/
//            {
//                DynamicHeightImageView dynamicHeightImageView = new DynamicHeightImageView(MainActivity.this);
//                dynamicHeightImageView.setImageBitmap(BitmapFactory.decodeFile(cursor.getString(0)));
//
//           //     dynamicHeightImageView.setBackgroundResource(R.drawable.helan);
//                noteModel.setDynamicHeightImageView(dynamicHeightImageView);
//            }
            noteModel.setImagePath(cursor.getString(0));
            System.out.println(cursor.getString(0) + "+++" + cursor.getString(1) + "+++" + cursor.getString(2) + "+++" + cursor.getString(3));
            noteModel.setSummary(cursor.getString(2));
            noteModel.setNoteTitle(cursor.getString(3));//(cursor.getString(3) == null || cursor.getString(3).equals("")) ?
            noteModel.setTime(cursor.getString(1));
            noteModelList.add(noteModel);
            System.out.println(noteModelList.size()+"size");
        }
    }

    private void InitView() {
        emptyImageView = (ImageView) findViewById(R.id.emptyImgView);
        emptyTextView = (TextView) findViewById(R.id.emptyText);
        noteListRecyclerView = (RecyclerView) findViewById(R.id.note_list);
        addNote = (ImageButton) findViewById(R.id.add_note);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditActivity.class));
            }
        });
        noteListRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        noteListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noteListRecyclerView.addItemDecoration(decoration);
        initSwipe();
        loadData();
        noteAdapter.notifyDataSetChanged();
    }

    private void initSwipe() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {


            @Override
            public void onRefresh() {
                //   tv.setText("正在刷新");
                // TODO Auto-generated method stub

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        noteModelList.clear();
                        initDataBase();
                        swipeRefreshLayout.setRefreshing(false);
                        handler.sendEmptyMessage(0x11);
                    }
                }).start();


            }
        });
    }

    private void loadData() {
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, "diary_db");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("diarys", null, null, null, null, null, null);
        noteModelList = new ArrayList<>();
        NoteModel noteModel;
        while (cursor.moveToNext()) {
            noteModel = new NoteModel();
           /* if (i % 2 == 0) {
                DynamicHeightImageView dynamicHeightImageView = new DynamicHeightImageView(MainActivity.this);
                dynamicHeightImageView.setBackgroundResource(R.drawable.hela);
                noteModel.setDynamicHeightImageView(dynamicHeightImageView);
            } else*/
//            {
//                DynamicHeightImageView dynamicHeightImageView = new DynamicHeightImageView(MainActivity.this);
//                dynamicHeightImageView.setImageBitmap(BitmapFactory.decodeFile(cursor.getString(0)));
//
//           //     dynamicHeightImageView.setBackgroundResource(R.drawable.helan);
//                noteModel.setDynamicHeightImageView(dynamicHeightImageView);
//            }
            noteModel.setImagePath(cursor.getString(0));
            System.out.println(cursor.getString(0) + "+++" + cursor.getString(1) + "+++" + cursor.getString(2) + "+++" + cursor.getString(3));
            noteModel.setSummary(cursor.getString(2));
            noteModel.setNoteTitle(cursor.getString(3));//(cursor.getString(3) == null || cursor.getString(3).equals("")) ?
            noteModel.setTime(cursor.getString(1));
            noteModelList.add(noteModel);
        }
        if (noteModelList.size() == 0) {
            noteListRecyclerView.setVisibility(View.GONE);
            emptyImageView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        }
        noteAdapter = new NoteAdapter(noteModelList, MainActivity.this, noteListRecyclerView,emptyImageView,emptyTextView);
        noteListRecyclerView.setAdapter(noteAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.caffe.brill.caffenote/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.caffe.brill.caffenote/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) % 2 == 0) {
                outRect.left = 8;
                outRect.right = 10;
            } else {
                outRect.left = 8;
                outRect.right = 10;
            }

            outRect.bottom = 20;
            outRect.top = 10;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = 25;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteModelList.clear();
                initDataBase();
                swipeRefreshLayout.setRefreshing(false);
                handler.sendEmptyMessage(0x11);
            }
        }).start();
    }
}
