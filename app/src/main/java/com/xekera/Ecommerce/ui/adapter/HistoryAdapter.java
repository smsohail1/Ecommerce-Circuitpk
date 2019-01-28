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
import android.view.Window;
import android.widget.Button;
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
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.history.HistoryPresenter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Booking> productsItems;
    List<Booking> productsItemsSearch;

    // IShopDetailAdapter iShopDetailAdapter;
    HistoryPresenter historyPresenter;
    IHistoryCancelOrderAdapter iHistoryCancelOrderAdapter;
    ISearchOrderAmount iSearchOrderAmount;

    public HistoryAdapter(Context context, List<Booking> productsItems, IHistoryCancelOrderAdapter iHistoryCancelOrderAdapter, ISearchOrderAmount iSearchOrderAmount) {
        this.context = context;
        this.productsItems = productsItems;
        this.iHistoryCancelOrderAdapter = iHistoryCancelOrderAdapter;
        this.iSearchOrderAmount = iSearchOrderAmount;
        this.productsItemsSearch = new ArrayList<>();
        this.productsItemsSearch.addAll(productsItems);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history_fragment, parent, false);
        productDetailsDataListViewHolder productDetailDataListViewHolder = new productDetailsDataListViewHolder(v);
        return productDetailDataListViewHolder;
    }

    // byte[] bytes;
    BitmapFactory.Options options;


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Booking booking = productsItems.get(position);
        if (holder instanceof productDetailsDataListViewHolder) {
            final productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;

            productDetailsDataListViewHolder.productNameLabelTextView.setText(booking.getItemName());
            productDetailsDataListViewHolder.priceTextView.setText(booking.getItemIndividualPrice());
            productDetailsDataListViewHolder.flatRateTextView.setText(booking.getFlatCharges());
            productDetailsDataListViewHolder.quantityTextView.setText(booking.getItemQuantity());
            productDetailsDataListViewHolder.orderDateTextView.setText(booking.getCreatedDate());

            try {

                Glide.with(context)
                        .load(booking.getImgUrl())
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        // .override(130, 50)
                        .centerCrop()

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
//                //   options = new BitmapFactory.Options();
//                //  options.inJustDecodeBounds = true;
//                // options.inSampleSize = 2;
//
//                bytes = booking.getItemImage();
//                // Create a bitmap from the byte array
//                // Bitmap compressedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
//
//                // productDetailsDataListViewHolder.imgProduct.setImageBitmap(compressedBitmap);
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
        @BindView(R.id.orderDateTextView)
        public TextView orderDateTextView;
        @BindView(R.id.flatRateTextView)
        public TextView flatRateTextView;
        @BindView(R.id.quantityTextView)
        public TextView quantityTextView;
        @BindView(R.id.btnCancel)
        public Button btnCancel;
        @BindView(R.id.btnTrackOrder)
        public Button btnTrackOrder;
        @BindView(R.id.avloadingIndicatorView)
        public AVLoadingIndicatorView avloadingIndicatorView;


        public productDetailsDataListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.btnCancel).setOnClickListener(this);
            itemView.findViewById(R.id.btnTrackOrder).setOnClickListener(this);
            itemView.findViewById(R.id.cardViewParent).setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnCancel:
                    iHistoryCancelOrderAdapter.cancelOrder(productsItems.get(getLayoutPosition()).getOrderID());
                    break;
                case R.id.btnTrackOrder:
                    iHistoryCancelOrderAdapter.trackOrder(productsItems.get(getLayoutPosition()).getOrderID());
                    break;
                case R.id.cardViewParent:
                    iHistoryCancelOrderAdapter.hideSoftkeyboard();
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


    public void addAll(List<Booking> addToCarts) {
        int currentListSize = this.productsItems.size();
        this.productsItems.addAll(addToCarts);
        notifyItemRangeInserted(currentListSize, addToCarts.size());
    }


    public interface IHistoryCancelOrderAdapter {
        void cancelOrder(String orderID);

        void trackOrder(String orderID);

        void hideSoftkeyboard();
    }

    public interface ISearchOrderAmount {
        void showTotalAmount(String itemTotalPrice);
    }

    long totalAmount = 0;

    public void filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            productsItems.clear();
            if (charText.length() == 0) {
                productsItems.addAll(productsItemsSearch);
                totalAmount = 0;
                for (Booking wp : productsItemsSearch) {
                    totalAmount = totalAmount + (Long.valueOf(wp.getItemPrice()) + Long.valueOf(wp.getFlatCharges()));
                }
                if (productsItemsSearch != null && productsItemsSearch.size() > 0) {
                    iSearchOrderAmount.showTotalAmount(String.valueOf(totalAmount));
                } else {
                    iSearchOrderAmount.showTotalAmount("0");
                }

            } else if (charText.length() > 0) {
                totalAmount = 0;
                for (Booking wp : productsItemsSearch) {
                    if (wp.getItemName().toLowerCase(Locale.getDefault()).trim()
                            .contains(charText)) {
                        productsItems.add(wp);
                        totalAmount = totalAmount + (Long.valueOf(wp.getItemPrice()) + Long.valueOf(wp.getFlatCharges()));
                    }
                }
                if (productsItemsSearch != null && productsItemsSearch.size() > 0) {
                    iSearchOrderAmount.showTotalAmount(String.valueOf(totalAmount));
                } else {
                    iSearchOrderAmount.showTotalAmount("0");
                }
            }
            notifyDataSetChanged();


        } catch (Exception ex) {
        }
    }

}

