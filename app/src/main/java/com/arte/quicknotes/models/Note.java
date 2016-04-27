package com.arte.quicknotes.models;

/**
 * Created by Sergio on 27/4/16.
 */
public class Note {

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

        if (content.length() < 100) {
            return content;
        }

        return content.substring(0, 100);
    }
}
