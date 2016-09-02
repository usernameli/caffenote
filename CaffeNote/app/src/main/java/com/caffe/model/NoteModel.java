package com.caffe.model;

import com.caffe.view.DynamicHeightImageView;

/**
 * Created by brill on 2016/6/22.
 */
public class NoteModel {
    public void setTime(String time) {
        Time = time;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getTime() {

        return Time;
    }

    public String getSummary() {
        return Summary;
    }

    private String NoteTitle;
    private String Time;
    private String Summary;

    public void setNoteTitle(String noteTitle) {
        NoteTitle = noteTitle;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public void setDynamicHeightImageView(DynamicHeightImageView dynamicHeightImageView) {
        this.dynamicHeightImageView = dynamicHeightImageView;
    }

    public void setDynamicHeightImageView(int id) {
        this.dynamicHeightImageView.setImageResource(id);
    }

    public void setGeneralize(String generalize) {
        Generalize = generalize;
    }

    public String getNoteTitle() {

        return NoteTitle;
    }

    public String getGeneralize() {
        return Generalize;
    }

    public DynamicHeightImageView getDynamicHeightImageView() {
        return dynamicHeightImageView;
    }

    public String getImagePath() {
        return ImagePath;
    }

    private String Generalize;
    private DynamicHeightImageView dynamicHeightImageView;
    private String ImagePath=null;
}
