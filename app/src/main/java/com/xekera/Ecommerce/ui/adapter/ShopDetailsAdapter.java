package com.xekera.Ecommerce.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ShopDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ShoppingDetailModel> productsItems;
    List<ShoppingDetailModel> productsItemsSearch;
    IShopDetailAdapter iShopDetailAdapter;


    public ShopDetailsAdapter() {

    }

    public ShopDetailsAdapter(Context context, List<ShoppingDetailModel> productsItems, IShopDetailAdapter iShopDetailAdapter) {
        this.productsItems = productsItems;
        this.iShopDetailAdapter = iShopDetailAdapter;
        this.productsItemsSearch = new ArrayList<>();
        this.productsItemsSearch.addAll(productsItems);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_row_shop_details, parent, false);
        productDetailsDataListViewHolder productDetailDataListViewHolder = new productDetailsDataListViewHolder(v);
        return productDetailDataListViewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ShoppingDetailModel shoppingDetailModel = productsItems.get(position);
        if (holder instanceof productDetailsDataListViewHolder) {
            productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;


            productDetailsDataListViewHolder.productNameLabelTextView.setText(shoppingDetailModel.getProductName());
            productDetailsDataListViewHolder.priceTextView.setText(shoppingDetailModel.getProductPrice());

            ImageView imageView = productDetailsDataListViewHolder.imgProduct;
            //   Glide.with(context).load(productsItems.get(position))
            //         .centerCrop() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
            //       .override(500, 500) // resizes the image to these dimensions (in pixel). resize does not respect aspect ratio
            //     .animate(android.R.anim.fade_in)
            // .placeholder(R.drawable.ic_launcher_background).
            //   .into(imageView);

            //  productDetailsDataListViewHolder.imgProduct.setImageResource(R.drawable.ic_mr_button_connected_06_dark);
            //productDetailsDataListViewHolder.statusTextview.setBackgroundColor(ContextCompat.getColor(context, R.color.status_blue));

            //     if (position % 2 == 0) {
            //       productDetailsDataListViewHolder.linearParent.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_light_report_item_bg_dark));
            //  } else {
            //     productDetailsDataListViewHolder.linearParent.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_light_report_item_bg_light));
            //}


        }
    }


    public class productDetailsDataListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.linearParent)
        protected LinearLayout linearParent;
        @BindView(R.id.productNameLabelTextView)
        public TextView productNameLabelTextView;
        @BindView(R.id.priceTextView)
        public TextView priceTextView;
        @BindView(R.id.viewDetailsImageView)
        public Button viewDetailsImageView;
        @BindView(R.id.AddImageView)
        public Button AddImageView;
        @BindView(R.id.imgProduct)
        public ImageView imgProduct;


        public productDetailsDataListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // itemView.setOnClickListener(this);
            itemView.findViewById(R.id.AddImageView).setOnClickListener(this);
            itemView.findViewById(R.id.viewDetailsImageView).setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.AddImageView:
                    iShopDetailAdapter.onAddButtonClick(productsItems.get(getLayoutPosition()));

                    break;
                case R.id.viewDetailsImageView:
                    iShopDetailAdapter.onViewDetailsButtonClick(productsItems.get(getLayoutPosition()));

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


    public interface IShopDetailAdapter {
        void onAddButtonClick(ShoppingDetailModel productItems);

        void onViewDetailsButtonClick(ShoppingDetailModel productItems);
    }


    public void filter(String charText) {
        try {

            charText = charText.toLowerCase(Locale.getDefault());
            productsItems.clear();
            if (charText.length() == 0) {

                productsItems.addAll(productsItemsSearch);
            } else if (charText.length() > 0) {
                for (ShoppingDetailModel wp : productsItemsSearch) {
                    if (wp.getProductName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        productsItems.add(wp);

                    }
                }
            }

            notifyDataSetChanged();
        } catch (Exception ex)

        {
        }
    }

}
