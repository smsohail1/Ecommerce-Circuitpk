package com.xekera.Ecommerce.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wang.avi.AVLoadingIndicatorView;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.billing_total_amount_view.BillingTotalAmountViewPresenter;

import java.util.List;

public class BillingTotalAmountViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<AddToCart> productsItems;
    // IShopDetailAdapter iShopDetailAdapter;
    //  BillingTotalAmountViewPresenter billingTotalAmountViewPresenter;

    public BillingTotalAmountViewAdapter(List<AddToCart> productsItems) {
        this.productsItems = productsItems;
        //  this.billingTotalAmountViewPresenter = billingTotalAmountViewPresenter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_billing_total_amount_view, parent, false);
        productDetailsDataListViewHolder productDetailDataListViewHolder = new productDetailsDataListViewHolder(v);
        return productDetailDataListViewHolder;
    }

    BitmapFactory.Options options;


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AddToCart addToCart = productsItems.get(position);
        //  byte[] bytes;
        if (holder instanceof productDetailsDataListViewHolder) {
            final productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;

            productDetailsDataListViewHolder.productNameLabelTextView.setText(addToCart.getItemName());
            productDetailsDataListViewHolder.priceTextView.setText(addToCart.getItemPrice());
            productDetailsDataListViewHolder.quantityTextView.setText(addToCart.getItemQuantity());

            try {

                Glide.with(context)
                        .load(addToCart.getImage())
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        // .override(130, 50)
                        .centerCrop()
                        .override(300, 300)

                        // .into(homeViewHolder.imgHomeItem);
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                productDetailsDataListViewHolder.avloadingIndicatorView.setVisibility(View.GONE);
                                productDetailsDataListViewHolder.imgProduct.setImageBitmap(resource);
                                productDetailsDataListViewHolder.imgProduct.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                productDetailsDataListViewHolder.avloadingIndicatorView.setVisibility(View.GONE);
                                productDetailsDataListViewHolder.imgProduct.setVisibility(View.VISIBLE);

                            }
                        });

            } catch (Exception e) {

            }
//            try {
//
//
//                //options = new BitmapFactory.Options();
//                //  options.inJustDecodeBounds = true;
//                // options.inSampleSize = 2;
//
//                bytes = addToCart.getItemImage();
//                // Create a bitmap from the byte array
//                //Bitmap compressedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
//
//                //productDetailsDataListViewHolder.imgProduct.setImageBitmap(compressedBitmap);
//
//                Glide.with(context)
//                        .load(bytes)
//                        .asBitmap()
//                        .placeholder(R.drawable.placeholder)
//                        .into(productDetailsDataListViewHolder.imgProduct);
//
//            } catch (Exception e) {
//
//            }

        }
    }


    public class productDetailsDataListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.linearParent)
        protected LinearLayout linearParent;
        @BindView(R.id.productNameLabelTextView)
        public TextView productNameLabelTextView;
        @BindView(R.id.priceTextView)
        public TextView priceTextView;
        @BindView(R.id.imgProduct)
        public ImageView imgProduct;
        @BindView(R.id.cardViewParent)
        public CardView cardViewParent;
        @BindView(R.id.quantityTextView)
        public TextView quantityTextView;
        @BindView(R.id.avloadingIndicatorView)
        public AVLoadingIndicatorView avloadingIndicatorView;


        public productDetailsDataListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {


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


    public void addAll(List<AddToCart> addToCarts) {
        int currentListSize = this.productsItems.size();
        this.productsItems.addAll(addToCarts);
        notifyItemRangeInserted(currentListSize, addToCarts.size());
    }


}