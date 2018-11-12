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

import java.util.List;

public class ShopDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<String> productsItems;


    public ShopDetailsAdapter(Context context, List<String> productsItems) {
        this.productsItems = productsItems;

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
        final String String = productsItems.get(position);
        if (holder instanceof productDetailsDataListViewHolder) {
            productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;


            //    productDetailsDataListViewHolder.productNameLabelTextView.setText(productsItems.get(position));
            //  productDetailsDataListViewHolder.priceTextView.setText("Not attempted");

            ImageView imageView = productDetailsDataListViewHolder.imgProduct;
            Glide.with(context).load(productsItems.get(position))
                    .centerCrop() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                    .override(500, 500) // resizes the image to these dimensions (in pixel). resize does not respect aspect ratio
                    .animate(android.R.anim.fade_in)
                    // .placeholder(R.drawable.ic_launcher_background).
                    .into(imageView);

            // productDetailsDataListViewHolder.imgProduct.setImageResource();
            //productDetailsDataListViewHolder.statusTextview.setBackgroundColor(ContextCompat.getColor(context, R.color.status_blue));

            if (position % 2 == 0) {
                productDetailsDataListViewHolder.linearParent.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_light_report_item_bg_dark));
            } else {
                productDetailsDataListViewHolder.linearParent.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_light_report_item_bg_light));
            }


        }
    }

    public void addAll(List<String> bookingList) {
        int currentListSize = this.productsItems.size();
        this.productsItems.addAll(bookingList);
        notifyItemRangeInserted(currentListSize, bookingList.size());
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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
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
}
