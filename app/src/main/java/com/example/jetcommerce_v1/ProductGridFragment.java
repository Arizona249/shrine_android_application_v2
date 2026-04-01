package com.example.jetcommerce_v1;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jetcommerce_v1.network.ProductEntry;
import com.example.jetcommerce_v1.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter;

public class ProductGridFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.jet_product_grid_fragment, container, false);

        setUpToolbar(view);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 == 2 ? 2 : 1;
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);

        StaggeredProductCardRecyclerViewAdapter adapter =
                new StaggeredProductCardRecyclerViewAdapter(
                        ProductEntry.initProductEntryList(getResources()));

        recyclerView.setAdapter(adapter);

        int largePadding = getResources()
                .getDimensionPixelSize(R.dimen.jet_staggered_product_grid_spacing_large);

        int smallPadding = getResources()
                .getDimensionPixelSize(R.dimen.jet_staggered_product_grid_spacing_small);

        recyclerView.addItemDecoration(
                new ProductGridItemDecoration(largePadding, smallPadding)
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.findViewById(R.id.product_grid)
                    .setBackgroundResource(R.drawable.jet_product_grid_background_shape);
        }

        return view;
    }

    private void setUpToolbar(View view) {

        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }

        toolbar.setNavigationOnClickListener(
                new NavigationIconClickListener(
                        requireContext(),
                        view.findViewById(R.id.product_grid),
                        new AccelerateDecelerateInterpolator(),
                        ContextCompat.getDrawable(requireContext(), R.drawable.jet_branded_menu),
                        ContextCompat.getDrawable(requireContext(), R.drawable.jet_close_menu)
                )
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.jet_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }
}