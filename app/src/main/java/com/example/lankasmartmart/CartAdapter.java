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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface CartUpdateListener {
        void onCartUpdated();
    }

    private Context context;
    private List<CartItem> cartItems;
    private CartUpdateListener listener;
    private DatabaseHelper dbHelper;

    public CartAdapter(Context context, List<CartItem> cartItems,
                       CartUpdateListener listener, DatabaseHelper dbHelper) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
        this.dbHelper = dbHelper;
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
        holder.productBrand.setText(item.getProductBrand());
        holder.productPrice.setText("LKR " + String.format("%.2f", item.getProductPrice()));
        holder.quantityText.setText(String.valueOf(item.getQuantity()));
        holder.subtotalText.setText("LKR " + String.format("%.2f", item.getSubtotal()));

        // Decrease quantity
        holder.decreaseBtn.setOnClickListener(v -> {
            int newQty = item.getQuantity() - 1;
            dbHelper.updateCartQuantity(item.getId(), newQty);
            listener.onCartUpdated();
        });

        // Increase quantity
        holder.increaseBtn.setOnClickListener(v -> {
            int newQty = item.getQuantity() + 1;
            dbHelper.updateCartQuantity(item.getId(), newQty);
            listener.onCartUpdated();
        });

        // Delete item
        holder.deleteBtn.setOnClickListener(v -> {
            dbHelper.removeFromCart(item.getId());
            listener.onCartUpdated();
        });
    }

    @Override
    public int getItemCount() { return cartItems.size(); }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productBrand, productPrice, quantityText, subtotalText;
        View decreaseBtn, increaseBtn, deleteBtn;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName  = itemView.findViewById(R.id.cartProductName);
            productBrand = itemView.findViewById(R.id.cartProductBrand);
            productPrice = itemView.findViewById(R.id.cartProductPrice);
            quantityText = itemView.findViewById(R.id.quantityText);
            subtotalText = itemView.findViewById(R.id.cartSubtotal);
            decreaseBtn  = itemView.findViewById(R.id.decreaseBtn);
            increaseBtn  = itemView.findViewById(R.id.increaseBtn);
            deleteBtn    = itemView.findViewById(R.id.deleteBtn);
        }
    }
}

