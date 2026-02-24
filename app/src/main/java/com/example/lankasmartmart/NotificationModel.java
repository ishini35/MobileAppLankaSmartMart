package com.example.lankasmartmart;

public class NotificationModel {
    private String title;
    private String message;
    private String time;
    private int iconResId;
    private boolean isRead;
    private String type; // "promo", "order", "flash_sale", etc.

    public NotificationModel(String title, String message, String time, int iconResId, boolean isRead, String type) {
        this.title = title;
        this.message = message;
        this.time = time;
        this.iconResId = iconResId;
        this.isRead = isRead;
        this.type = type;
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getTime() { return time; }
    public int getIconResId() { return iconResId; }
    public boolean isRead() { return isRead; }
    public String getType() { return type; }
    public void setRead(boolean read) { isRead = read; }
}