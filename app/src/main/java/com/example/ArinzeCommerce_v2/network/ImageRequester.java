package com.example.ArinzeCommerce_v2.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.ArinzeCommerce_v2.application.ArneApplication;

/**
 * Singleton class that handles image requests using Volley safely.
 */
public class ImageRequester {

    private static ImageRequester instance;

    private final Context appContext;
    private final RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    private final int maxByteSize;

    // Private constructor
    private ImageRequester(Context context) {
        // Use application context to avoid memory leaks
        this.appContext = context.getApplicationContext();

        // Initialize Volley RequestQueue
        this.requestQueue = Volley.newRequestQueue(appContext);
        this.requestQueue.start();

        // Calculate max memory for LruCache
        this.maxByteSize = calculateMaxByteSize();

        // Initialize ImageLoader with LruCache
        this.imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(maxByteSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount();
                }
            };

            @Override
            public synchronized Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }

            @Override
            public synchronized void putBitmap(String url, Bitmap bitmap) {
                lruCache.put(url, bitmap);
            }
        });
    }

    /**
     * Returns the singleton instance of ImageRequester.
     * Safe to call anytime after application start.
     */
    public static synchronized ImageRequester getInstance() {
        if (instance == null) {
            Context context = ArneApplication.getAppContext();
            if (context == null) {
                throw new IllegalStateException("JetBreedApplication context not initialized yet!");
            }
            instance = new ImageRequester(context);
        }
        return instance;
    }

    /**
     * Sets image from URL into a NetworkImageView with optional placeholder and error images.
     */
    public void setImageFromUrl(NetworkImageView networkImageView, String url, int placeholderRes, int errorRes) {
        networkImageView.setDefaultImageResId(placeholderRes);
        networkImageView.setErrorImageResId(errorRes);
        networkImageView.setImageUrl(url, imageLoader);
    }

    /**
     * Overload without placeholders (uses default behavior)
     */
    public void setImageFromUrl(NetworkImageView networkImageView, String url) {
        networkImageView.setImageUrl(url, imageLoader);
    }

    /**
     * Calculate max memory size for image cache.
     */
    private int calculateMaxByteSize() {
        DisplayMetrics displayMetrics = appContext.getResources().getDisplayMetrics();
        int screenBytes = displayMetrics.widthPixels * displayMetrics.heightPixels * 4;
        return screenBytes * 3; // 3 screens worth of images
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}