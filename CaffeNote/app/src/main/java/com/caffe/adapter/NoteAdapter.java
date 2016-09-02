package com.caffe.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caffe.brill.caffenote.R;
import com.caffe.brill.caffenote.ShowDiarysActivity;
import com.caffe.brill.sqlite.DataBaseHelper;
import com.caffe.model.NoteModel;
import com.caffe.utill.LruUtil;
import com.caffe.view.DynamicHeightImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.transform.Source;

/**
 * Created by brill on 2016/6/22.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteView> {
    private final int width;
    private final int height;
    private List<NoteModel> noteList;
    private Context context;
    private LruUtil lruutil;
    private RecyclerView mRecyclerView;
    private TextView emptyText;
    private ImageView emptyImage;


    public NoteAdapter(List<NoteModel> noteList, Context context, RecyclerView mRecyclerView, ImageView emptyImageView, TextView emptyTextView) {
        this.context = context;
        this.noteList = noteList;
        this.mRecyclerView = mRecyclerView;
        this.emptyImage = emptyImageView;
        this.emptyText = emptyTextView;

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth() / 2;
        height = wm.getDefaultDisplay().getHeight() / 2;
    }

    //    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
//        if (mLruCache.get(key) == null) {
//            System.out.println("add cache");
//            mLruCache.put(key, bitmap);
//        }
//    }
//
//    public Bitmap getBitmapFromMemCache(String key) {
//        if (mLruCache.get(key) == null) {
//            System.out.println("get sd");
//            return getLoacalBitmap(key);
//        } else {
//            System.out.println("get cache");
//            return mLruCache.get(key);
//        }
//    }

    @Override
    public NoteView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);

        return new NoteView(view);
    }


    public void onBindViewHolder(NoteView noteViewr, final int position) {
        noteViewr.Time.setText(noteList.get(position).getTime());
        noteViewr.summary.setText(noteList.get(position).getSummary());
        // System.out.println(noteList.get(position).getImagePath());

        if (noteList.get(position).getImagePath() != null) {
            // noteViewr.dynamicHeightImageView.setImageBitmap(getBitmapFromMemCache(noteList.get(position).getImagePath()));
            //   System.out.println("lru get" + mLruCache.get(noteList.get(position).getImagePath()));
            lruutil = new LruUtil();
            if (lruutil.getBitmapFromMemCache(noteList.get(position).getImagePath()) == null) {
                noteViewr.dynamicHeightImageView.setImageBitmap(getLoacalBitmap(noteList.get(position).getImagePath()));
                System.out.println("frome disk");
            } else {
                System.out.println("frome lru");
                noteViewr.dynamicHeightImageView.setImageBitmap(lruutil.getBitmapFromMemCache(noteList.get(position).getImagePath()));
            }
        } else {
            noteViewr.dynamicHeightImageView.setImageBitmap(null);
        }
        //  noteViewr.dynamicHeightImageView.setImageBitmap(null);
        noteViewr.noteTitle.setText(noteList.get(position).getNoteTitle());
        noteViewr.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("title", noteList.get(position).getNoteTitle());
                bundle.putString("diarys", noteList.get(position).getSummary());
                bundle.putString("time", noteList.get(position).getTime());
                intent.putExtras(bundle);
                intent.setClass(context, ShowDiarysActivity.class);
                context.startActivity(intent);
            }
        });
        noteViewr.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(mRecyclerView, "是否删除", Snackbar.LENGTH_SHORT)
                        .setAction("是", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //   SaveDate(viewGroup);
                                //   finish();
                                DeleteDiarys(position);
                            }
                        }).setActionTextColor(context.getResources().getColor(android.R.color.white))
                        .show();
                return true;
            }
        });
        //   noteViewr.itemView.setLayoutParams(getParames(position));
    }

    private void DeleteDiarys(int position) {
        DataBaseHelper dbHelper = new DataBaseHelper(context, "diary_db");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //  SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete("diarys", "diarytime " + "=?", new String[]{noteList.get(position).getTime()});
        noteList.remove(position);
        notifyItemRemoved(position);
    }

    private ViewGroup.LayoutParams getParames(int posion) {
//        if (posion == 1) {
        return new RelativeLayout.LayoutParams(
                (int) (width / 2), (int) (Math.random() * (9 - 6) + 6) * height / 10);
//        } else {
//            return new RelativeLayout.LayoutParams(
//                    width, height / 3 * 2);
//        }
        //  return params;
    }

    @Override
    public int getItemCount() {
        checkView();
        return noteList.size();
    }

    public static class NoteView extends RecyclerView.ViewHolder {
        TextView noteTitle;
        View itemView;
        DynamicHeightImageView dynamicHeightImageView;
        TextView summary;
        TextView Time;

        public NoteView(View itemView) {
            super(itemView);
            this.itemView = itemView;
            noteTitle = (TextView) itemView.findViewById(R.id.note_title);
            dynamicHeightImageView = (DynamicHeightImageView) itemView.findViewById(R.id.note_image);
            summary = (TextView) itemView.findViewById(R.id.note_summary);
            Time = (TextView) itemView.findViewById(R.id.note_time);
        }

    }


    public Bitmap getLoacalBitmap(String url) {
        if (url == null || url.equals("")) {
            return null;
        }
        FileInputStream fileInputStream = null;
        Bitmap bitmap = null;
        try {
            //       imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            //FileOutputStream out = new FileOutputStream(new File(url));
            fileInputStream = new FileInputStream(new File(url));
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            System.out.println(bitmap + "bt");
            return small(bitmap, url);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("exception");
            return null;
        }
    }

    private Bitmap small(Bitmap bitmap, String url) {
        System.out.println(bitmap);
        Matrix matrix = new Matrix();
        matrix.postScale(0.3f, 0.3f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        System.out.println(bitmap.getByteCount() + "size " + resizeBmp.getByteCount());
        // bitmap.recycle();
        lruutil.addBitmapToMemoryCache(url, resizeBmp);
        return resizeBmp;
    }

    public void checkView() {
        if (noteList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.VISIBLE);
            System.out.println("gone");

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            emptyImage.setVisibility(View.GONE);
            //   emptyImageView.setVisibility(View.GONE);
            //  emptyTextView.setVisibility(View.GONE);
            System.out.println("visible");
        }
    }
}
