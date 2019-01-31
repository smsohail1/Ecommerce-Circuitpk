package com.xekera.Ecommerce.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.xekera.Ecommerce.data.rest.response.Fulldetail;
import com.xekera.Ecommerce.data.rest.response.HistoryOrderDescriptionAddressResponse.Address;
import com.xekera.Ecommerce.data.rest.response.OrderList;
import com.xekera.Ecommerce.data.rest.response.ProList;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.history.HistoryPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryDesciptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ProList> productsItems;
    List<ProList> productsItemsSearch;
    Address address;

    // IShopDetailAdapter iShopDetailAdapter;
    HistoryDesciptionAdapter.IHistoryCancelOrderAdapter iHistoryCancelOrderAdapter;
    HistoryDesciptionAdapter.ISearchOrderAmount iSearchOrderAmount;

    public HistoryDesciptionAdapter(Context context, List<ProList> productsItems,
                                    Address address,
                                    HistoryDesciptionAdapter.IHistoryCancelOrderAdapter iHistoryCancelOrderAdapter, HistoryDesciptionAdapter.ISearchOrderAmount iSearchOrderAmount) {
        this.context = context;
        this.productsItems = productsItems;
        this.iHistoryCancelOrderAdapter = iHistoryCancelOrderAdapter;
        this.iSearchOrderAmount = iSearchOrderAmount;
        this.productsItemsSearch = new ArrayList<>();
        this.productsItemsSearch.addAll(productsItems);
        this.address = address;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history_desciption, parent, false);
        HistoryDesciptionAdapter.productDetailsDataListViewHolder productDetailDataListViewHolder = new HistoryDesciptionAdapter.productDetailsDataListViewHolder(v);
        return productDetailDataListViewHolder;
    }

    // byte[] bytes;
    BitmapFactory.Options options;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProList booking = productsItems.get(position);
        if (holder instanceof HistoryDesciptionAdapter.productDetailsDataListViewHolder) {
            final HistoryDesciptionAdapter.productDetailsDataListViewHolder productDetailsDataListViewHolder = (HistoryDesciptionAdapter.productDetailsDataListViewHolder) holder;

            if (booking.getName() != null) {
                productDetailsDataListViewHolder.productNameLabelTextView.setText(booking.getName());
            }
            if (booking.getPrice() != null) {
                long totalAmount = Long.valueOf(booking.getPrice()) * Long.valueOf(booking.getItemQuantity());
                productDetailsDataListViewHolder.priceTextView.setText(String.valueOf(totalAmount));
            }

            if (booking.getItemQuantity() != null) {
                productDetailsDataListViewHolder.quantityTextView.setText(booking.getItemQuantity());
            }
            if (address.getCustName() != null) {
                productDetailsDataListViewHolder.nameValueTextView.setText(address.getCustName());
            }
            if (address.getAddress() != null) {
                productDetailsDataListViewHolder.orderIdValueTextView.setText(address.getOrderId());

            }
            if (address.getPhoneNumber() != null) {
                productDetailsDataListViewHolder.PhoneNoValueTextView.setText(address.getPhoneNumber());

            }
            if (address.getEmailAddress() != null) {
                productDetailsDataListViewHolder.emailValueTextView.setText(address.getEmailAddress());
            }

            try {
                if (booking.getImageJson() == null) {
                    Glide.with(context)
                            .load(R.drawable.placeholder)
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
                } else {
                    Glide.with(context)
                            .load(booking.getImageJson())
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
                }
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
        @BindView(R.id.nameValueTextView)
        public TextView nameValueTextView;
        @BindView(R.id.orderIdValueTextView)
        public TextView orderIdValueTextView;
        @BindView(R.id.PhoneNoValueTextView)
        public TextView PhoneNoValueTextView;
        @BindView(R.id.emailValueTextView)
        public TextView emailValueTextView;
        @BindView(R.id.quantityTextView)
        public TextView quantityTextView;

        @BindView(R.id.avloadingIndicatorView)
        public AVLoadingIndicatorView avloadingIndicatorView;


        public productDetailsDataListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

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


    public void addAll(List<List<ProList>> addToCarts) {
        int currentListSize = this.productsItems.size();
        //  this.productsItems.addAll(addToCarts);
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

    //    public void filter(String charText) {
//        try {
//            charText = charText.toLowerCase(Locale.getDefault());
//            productsItems.clear();
//            if (charText.length() == 0) {
//                productsItems.addAll(productsItemsSearch);
//                totalAmount = 0;
//                for (Booking wp : productsItemsSearch) {
//                    totalAmount = totalAmount + (Long.valueOf(wp.getItemPrice()) + Long.valueOf(wp.getFlatCharges()));
//                }
//                if (productsItemsSearch != null && productsItemsSearch.size() > 0) {
//                    iSearchOrderAmount.showTotalAmount(String.valueOf(totalAmount));
//                } else {
//                    iSearchOrderAmount.showTotalAmount("0");
//                }
//
//            } else if (charText.length() > 0) {
//                totalAmount = 0;
//                for (Booking wp : productsItemsSearch) {
//                    if (wp.getItemName().toLowerCase(Locale.getDefault()).trim()
//                            .contains(charText)) {
//                        productsItems.add(wp);
//                        totalAmount = totalAmount + (Long.valueOf(wp.getItemPrice()) + Long.valueOf(wp.getFlatCharges()));
//                    }
//                }
//                if (productsItemsSearch != null && productsItemsSearch.size() > 0) {
//                    iSearchOrderAmount.showTotalAmount(String.valueOf(totalAmount));
//                } else {
//                    iSearchOrderAmount.showTotalAmount("0");
//                }
//            }
//            notifyDataSetChanged();
//
//
//        } catch (Exception ex) {
//        }
//    }
//    public void filter(String charText) {
//        try {
//
//            charText = charText.toLowerCase(Locale.getDefault());
//            productsItems.clear();
//            if (charText.length() == 0) {
//
//                productsItems.addAll(productsItemsSearch);
//            } else if (charText.length() > 0) {
//                for (Fulldetail wp : productsItemsSearch) {
//                    if (wp.getProducts().getProList().getName().toLowerCase(Locale.getDefault()).trim()
//                            .contains(charText)) {
//                        productsItems.add(wp);
//
//                    }
//                }
//            }
//
//            notifyDataSetChanged();
//        } catch (Exception ex) {
//        }
//    }

}


