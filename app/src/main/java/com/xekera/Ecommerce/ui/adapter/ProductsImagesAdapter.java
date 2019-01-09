package com.xekera.Ecommerce.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.wang.avi.AVLoadingIndicatorView;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.shop_card_selected.model.MultipleImagesItem;

import javax.sql.DataSource;
import java.util.List;

public class ProductsImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MultipleImagesItem> productsItems;
    IMultipleImageAdapter iMultipleImageAdapter;

    public ProductsImagesAdapter(List<MultipleImagesItem> productsItems, IMultipleImageAdapter iMultipleImageAdapter, Context context) {
        this.productsItems = productsItems;
        this.iMultipleImageAdapter = iMultipleImageAdapter;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_images, parent, false);
        productDetailsDataListViewHolder productDetailDataListViewHolder = new productDetailsDataListViewHolder(v);
        return productDetailDataListViewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MultipleImagesItem items = productsItems.get(position);
        //  byte[] bytes;
        if (holder instanceof productDetailsDataListViewHolder) {
            final productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;

            try {

                Glide.with(context).load(items.getImage())
                        .asBitmap()
                        .fitCenter()
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
//                    .listener(new RequestListener<String, GlideDrawable>() {
//                        @Override
//                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                            productDetailsDataListViewHolder.avloadingIndicatorView.setVisibility(View.GONE);
//                            productDetailsDataListViewHolder.imageViewMultipleProduct.setVisibility(View.VISIBLE);
//
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            productDetailsDataListViewHolder.avloadingIndicatorView.setVisibility(View.GONE);
//                            productDetailsDataListViewHolder.imageViewMultipleProduct.setVisibility(View.VISIBLE);
//                            return false;
//                        }
//                    })
//                    .into(productDetailsDataListViewHolder.imageViewMultipleProduct);

                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                productDetailsDataListViewHolder.avloadingIndicatorView.setVisibility(View.GONE);
                                productDetailsDataListViewHolder.imageViewMultipleProduct.setImageBitmap(resource);
                                productDetailsDataListViewHolder.imageViewMultipleProduct.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                productDetailsDataListViewHolder.avloadingIndicatorView.setVisibility(View.GONE);
                                productDetailsDataListViewHolder.imageViewMultipleProduct.setVisibility(View.VISIBLE);

                            }
                        });


            } catch (Exception e) {

            }
//            try {
//
//
//                bytes = addToCart.getItemImage();
//                // Create a bitmap from the byte array
//                Bitmap compressedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//
//                productDetailsDataListViewHolder.imageViewMultipleProduct.setImageBitmap(compressedBitmap);
//
//
//            } catch (Exception e) {
//
//            }


        }
    }


    public class productDetailsDataListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageViewMultipleProduct)
        public ImageView imageViewMultipleProduct;
        @BindView(R.id.avloadingIndicatorView)
        public AVLoadingIndicatorView avloadingIndicatorView;

        public productDetailsDataListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            imageViewMultipleProduct.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageViewMultipleProduct:

                    String clickedUrl = productsItems.get(getLayoutPosition()).getImage();

//            Glide.with(context).load(clicked_url)
//                    .fitCenter()
//                    .placeholder(R.drawable.placeholder)
//                    .error(R.drawable.placeholder)
//                    .into(productDetailsDataListViewHolder.imageViewMultipleProduct);

//            BitmapDrawable bitmapDrawable = imageViewMultipleProduct.getDrawable();
//            Bitmap bitmapImg = bitmapDrawable.getBitmap();
//            imageViewMultipleProduct.bi
                    iMultipleImageAdapter.onImageClick(clickedUrl);
                    break;
            }

        }

    }

    @Override
    public int getItemCount() {
        return productsItems.size();
    }


    public void removeAll() {
        this.productsItems.clear();
        notifyDataSetChanged();
    }

    public void refreshProduct() {
        notifyDataSetChanged();

    }


    public void addAll(List<MultipleImagesItem> addToCarts) {
        int currentListSize = this.productsItems.size();
        this.productsItems.addAll(addToCarts);
        notifyItemRangeInserted(currentListSize, addToCarts.size());
    }

    public interface IShopDetailAdapter {


    }


    public interface IMultipleImageAdapter {
        void onImageClick(String clickedUrl);
    }
}


