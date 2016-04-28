package com.arte.quicknotes.models;

import java.io.Serializable;

/**
 * Created by Sergio on 27/4/16.
 */
public class Note implements Serializable {

    private static final int EXCERPT_LENGTH = 100;

    private int id;
    private String title;
    private String content;

    public Note() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        if (content == null) {
            return "";
        }

        if (content.length() < EXCERPT_LENGTH) {
            return content;
        }

        return content.substring(0, EXCERPT_LENGTH);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
