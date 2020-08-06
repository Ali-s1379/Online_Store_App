package com.example.onlinestoreapp.Model;

import android.view.View;

public class MenuItem {

    private String name;
    private int drawableId;
    private View.OnClickListener listener;

    public MenuItem(String name, int drawableId, View.OnClickListener listener) {
        this.name = name;
        this.drawableId = drawableId;
        this.listener = listener;
    }

    public String getName() {
        return name;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

}
