package com.example.lankasmartmart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private List<NotificationModel> notifications;

    public NotificationAdapter(Context context, List<NotificationModel> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel notification = notifications.get(position);

        holder.title.setText(notification.getTitle());
        holder.message.setText(notification.getMessage());
        holder.time.setText(notification.getTime());
        holder.icon.setImageResource(notification.getIconResId());

        // Show/hide unread dot
        holder.unreadDot.setVisibility(notification.isRead() ? View.INVISIBLE : View.VISIBLE);

        // Change background for unread notifications
        if (!notification.isRead()) {
            holder.itemView.setBackgroundResource(R.drawable.notification_item_unread_bg);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.notification_item_bg);
        }

        // Mark as read on click
        holder.itemView.setOnClickListener(v -> {
            notification.setRead(true);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    // Mark all as read
    public void markAllAsRead() {
        for (NotificationModel n : notifications) {
            n.setRead(true);
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, message, time;
        View unreadDot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.notificationIcon);
            title = itemView.findViewById(R.id.notificationTitle);
            message = itemView.findViewById(R.id.notificationMessage);
            time = itemView.findViewById(R.id.notificationTime);
            unreadDot = itemView.findViewById(R.id.unreadDot);
        }
    }
}