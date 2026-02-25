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
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context context;
    private final List<CartItem> cartItems;
    private final CartUpdateListener listener;
    private final DatabaseHelper databaseHelper;
    private final int userId;

    public interface CartUpdateListener {
        void onCartUpdated();
    }

    public CartAdapter(Context context, List<CartItem> cartItems, CartUpdateListener listener,
                       DatabaseHelper databaseHelper, int userId) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
        this.databaseHelper = databaseHelper;
        this.userId = userId;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.productName.setText(item.getProductName());
        holder.productPrice.setText(String.format(Locale.getDefault(), "LKR %.2f", item.getPrice()));
        holder.quantity.setText(String.valueOf(item.getQuantity()));
        holder.totalPrice.setText(String.format(Locale.getDefault(), "LKR %.2f", item.getPrice() * item.getQuantity()));

        // Plus button
        holder.btnPlus.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() + 1;
            databaseHelper.updateCartQuantity(userId, item.getProductId(), newQuantity);
            if (listener != null) listener.onCartUpdated();
        });

        // Minus button
        holder.btnMinus.setOnClickListener(v -> {
            int newQuantity = Math.max(item.getQuantity() - 1, 0);
            if (newQuantity > 0) {
                databaseHelper.updateCartQuantity(userId, item.getProductId(), newQuantity);
            } else {
                databaseHelper.updateCartQuantity(userId, item.getProductId(), 0);
            }
            if (listener != null) listener.onCartUpdated();
        });

        // Delete button
        holder.btnDelete.setOnClickListener(v -> {
            databaseHelper.updateCartQuantity(userId, item.getProductId(), 0);
            if (listener != null) listener.onCartUpdated();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, quantity, totalPrice;
        ImageView btnPlus, btnMinus, btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            quantity = itemView.findViewById(R.id.quantity);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}