package com.example.jetcommerce_v1.staggeredgridlayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jetcommerce_v1.R;
import com.example.jetcommerce_v1.network.ImageRequester;
import com.example.jetcommerce_v1.network.ProductEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter used to show an asymmetric grid of products, with 2 items in the first column, and 1
 * item in the second column, and so on.
 */
public class StaggeredProductCardRecyclerViewAdapter extends RecyclerView.Adapter<com.example.jetcommerce_v1.staggeredgridlayout.StaggeredProductCardViewHolder> {

    private List<ProductEntry> productList;
    private ImageRequester imageRequester;

    public StaggeredProductCardRecyclerViewAdapter(List<ProductEntry> productList) {
        this.productList = productList;
        imageRequester = ImageRequester.getInstance();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @NonNull
    @Override
    public com.example.jetcommerce_v1.staggeredgridlayout.StaggeredProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.jet_staggered_product_card_first;
        if (viewType == 1) {
            layoutId = R.layout.jet_staggered_product_card_second;
        } else if (viewType == 2) {
            layoutId = R.layout.jet_staggered_product_card_third;
        }

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new com.example.jetcommerce_v1.staggeredgridlayout.StaggeredProductCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.jetcommerce_v1.staggeredgridlayout.StaggeredProductCardViewHolder holder, int position) {
        if (productList != null && position < productList.size()) {
            ProductEntry product = productList.get(position);
            holder.productTitle.setText(product.title);
            holder.productPrice.setText(product.price);
            imageRequester.setImageFromUrl(holder.productImage, product.url);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
