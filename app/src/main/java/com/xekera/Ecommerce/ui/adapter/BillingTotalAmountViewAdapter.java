package com.xekera.Ecommerce.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.billing_total_amount_view.BillingTotalAmountViewPresenter;

import java.util.List;

public class BillingTotalAmountViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<AddToCart> productsItems;
    // IShopDetailAdapter iShopDetailAdapter;
    BillingTotalAmountViewPresenter billingTotalAmountViewPresenter;

    public BillingTotalAmountViewAdapter(List<AddToCart> productsItems, BillingTotalAmountViewPresenter billingTotalAmountViewPresenter) {
        this.productsItems = productsItems;
        this.billingTotalAmountViewPresenter = billingTotalAmountViewPresenter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_billing_total_amount_view, parent, false);
        productDetailsDataListViewHolder productDetailDataListViewHolder = new productDetailsDataListViewHolder(v);
        return productDetailDataListViewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AddToCart addToCart = productsItems.get(position);
        byte[] bytes;
        if (holder instanceof productDetailsDataListViewHolder) {
            productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;

            productDetailsDataListViewHolder.productNameLabelTextView.setText(addToCart.getItemName());
            productDetailsDataListViewHolder.priceTextView.setText(addToCart.getItemPrice());
            productDetailsDataListViewHolder.quantityTextView.setText(addToCart.getItemQuantity());

            try {


                bytes = addToCart.getItemImage();
                // Create a bitmap from the byte array
                Bitmap compressedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                productDetailsDataListViewHolder.imgProduct.setImageBitmap(compressedBitmap);


            } catch (Exception e) {

            }

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