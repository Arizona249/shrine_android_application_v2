package com.example.jetcommerce_v1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.jetcommerce_v1.network.ProductEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter used to show a simple grid of products.
 */
public class ProductCardRecyclerViewAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {

    private List<ProductEntry> productList;

    ProductCardRecyclerViewAdapter(List<ProductEntry> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jet_product_card, parent, false);

        return new ProductCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {

        if (productList != null && position < productList.size()) {

            ProductEntry product = productList.get(position);

            holder.productTitle.setText(product.title);
            holder.productPrice.setText(product.price);

            Glide.with(holder.itemView.getContext())
                    .load(product.url)
//                    .placeholder(R.drawable.placeholder)
//                    .error(R.drawable.error)
                    .into(holder.productImage);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}