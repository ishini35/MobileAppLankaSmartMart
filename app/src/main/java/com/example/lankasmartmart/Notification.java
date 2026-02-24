package com.example.lankasmartmart;

public class Notification {
    private int id;
    private String title;
    private String message;
    private String time;
    private int iconResource;
    private int iconBackgroundColor;

    public Notification(int id, String title, String message, String time,
                        int iconResource, int iconBackgroundColor) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.time = time;
        this.iconResource = iconResource;
        this.iconBackgroundColor = iconBackgroundColor;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getTime() { return time; }
    public int getIconResource() { return iconResource; }
    public int getIconBackgroundColor() { return iconBackgroundColor; }
}
