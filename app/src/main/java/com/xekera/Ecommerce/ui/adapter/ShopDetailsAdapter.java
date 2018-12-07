package com.xekera.Ecommerce.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.varunest.sparkbutton.SparkButton;
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


//    private ProductItemActionListener actionListener;

    public ShopDetailsAdapter() {

    }

    public ShopDetailsAdapter(Context context, List<ShoppingDetailModel> productsItems, IShopDetailAdapter iShopDetailAdapter) {
        this.context = context;
        this.productsItems = productsItems;
        this.iShopDetailAdapter = iShopDetailAdapter;
        this.productsItemsSearch = new ArrayList<>();
        this.productsItemsSearch.addAll(productsItems);
    }


//    public void setActionListener(ProductItemActionListener actionListener) {
//        this.actionListener = actionListener;
//    }


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
            productDetailsDataListViewHolder.counterTextview.setText(shoppingDetailModel.getItemQuantity() + "");
//            if (shoppingDetailModel.getFavourites().size() > 0) {
////                if (shoppingDetailModel.getFavourites().contains(shoppingDetailModel.getProductName())) {
//                if (productsItems.get(position).getFavourites().contains(shoppingDetailModel.getProductName())) {
//
//                    productDetailsDataListViewHolder.favouriteButton.setChecked(true);
//                } else {
//                    productDetailsDataListViewHolder.favouriteButton.setChecked(false);
//
//                }
//            } else {
//                productDetailsDataListViewHolder.favouriteButton.setChecked(false);
//            }


            if (productsItems.get(position).isFavourite()) {
                productDetailsDataListViewHolder.favouriteButton.setChecked(true);
            } else {
                productDetailsDataListViewHolder.favouriteButton.setChecked(false);

            }


            //            if (shoppingDetailModel.isFavourite()) {
//                productDetailsDataListViewHolder.favouriteButton.setChecked(true);
//
//            } else {
//                productDetailsDataListViewHolder.favouriteButton.setChecked(false);
//            }

            //   ImageView imageView = productDetailsDataListViewHolder.imgProduct;
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
        @BindView(R.id.imgProductCopy)
        public ImageView imgProductCopy;
        @BindView(R.id.favouriteButton)
        public SparkButton favouriteButton;
        @BindView(R.id.decrementImageButton)
        public ImageView decrementImageButton;
        @BindView(R.id.incrementImageButton)
        public ImageView incrementImageButton;
        @BindView(R.id.counterTextview)
        public TextView counterTextview;
        @BindView(R.id.cardViewParent)
        public CardView cardViewParent;
        @BindView(R.id.imgShareProductDetails)
        public ImageView imgShareProductDetails;


        public productDetailsDataListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            // itemView.findViewById(R.id.AddImageView).setOnClickListener(this);
            // itemView.findViewById(R.id.viewDetailsImageView).setOnClickListener(this);

            cardViewParent.setClickable(true);

            itemView.findViewById(R.id.decrementImageButton).setOnClickListener(this);
            itemView.findViewById(R.id.incrementImageButton).setOnClickListener(this);
            itemView.findViewById(R.id.cardViewParent).setOnClickListener(this);
            itemView.findViewById(R.id.imgShareProductDetails).setOnClickListener(this);
            itemView.findViewById(R.id.favouriteButton).setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.cardViewParent:
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imgProduct.getDrawable();
                    Bitmap bitmapImg = bitmapDrawable.getBitmap();

                    cardViewParent.setClickable(false);
                    iShopDetailAdapter.onCardClick(productsItems.get(getLayoutPosition()), bitmapImg);
                    break;

                case R.id.AddImageView:
                    iShopDetailAdapter.onAddButtonClick(productsItems.get(getLayoutPosition()));

                    break;
                case R.id.viewDetailsImageView:
                    iShopDetailAdapter.onViewDetailsButtonClick(productsItems.get(getLayoutPosition()));
                    break;

                case R.id.favouriteButton:
                    if (((SparkButton) v.findViewById(R.id.favouriteButton)).isChecked()) {
                        ((SparkButton) v.findViewById(R.id.favouriteButton)).setChecked(false);
                        productsItems.get(getLayoutPosition()).setFavourite(false);

                    } else {
                        ((SparkButton) v.findViewById(R.id.favouriteButton)).playAnimation();
                        ((SparkButton) v.findViewById(R.id.favouriteButton)).setChecked(true);

                        productsItems.get(getLayoutPosition()).setFavourite(true);
                    }

                    iShopDetailAdapter.onFavouriteButtonClick(productsItems.get(getLayoutPosition()), getLayoutPosition());
                    break;
                case R.id.decrementImageButton:
                    //  decrementCounter = decrementCounter - 1;
                    //productsItems.get(getLayoutPosition()).setItemQuantity(getItemQuantity());

                    // productsItems.get(getLayoutPosition()).setItemQuantity(getItemQuantity());

                    if (productsItems.get(getLayoutPosition()).getItemQuantity() > 1) {

                        long dec = productsItems.get(getLayoutPosition()).getItemQuantity() - 1;
                        productsItems.get(getLayoutPosition()).setItemQuantity(dec);

                        counterTextview.setText(dec + "");

                        long productPrice = Long.valueOf(productsItems.get(getLayoutPosition()).getProductPrice());
                        long itemQuantity = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
                        iShopDetailAdapter.onDecrementButtonClick(productsItems.get(getLayoutPosition()).getItemQuantity(),
                                String.valueOf(productPrice),
                                String.valueOf(productPrice * itemQuantity),
                                productsItems.get(getLayoutPosition()).getProductName(),
                                productsItems.get(getLayoutPosition()).getCutPrice(),
                                productsItems.get(getLayoutPosition()).getByteImage(), imgProductCopy);

                    } else {
                        productsItems.get(getLayoutPosition()).setItemQuantity(0);
                        counterTextview.setText(productsItems.get(getLayoutPosition()).getItemQuantity() + "");

                        iShopDetailAdapter.removeItemFromCart(productsItems.get(getLayoutPosition()));


//                        long productPrice = Long.valueOf(productsItems.get(getLayoutPosition()).getProductPrice());
//                        long itemQuantity = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
//                        iShopDetailAdapter.onDecrementButtonClick(productsItems.get(getLayoutPosition()).getItemQuantity(),
//                                String.valueOf(productPrice),
//                                String.valueOf(productPrice * itemQuantity),
//                                productsItems.get(getLayoutPosition()).getProductName(),
//                                productsItems.get(getLayoutPosition()).getCutPrice(),
//                                productsItems.get(getLayoutPosition()).getByteImage(), imgProductCopy);

                    }
                    break;
                case R.id.incrementImageButton:

                    long inc = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
                    long incrementLong = inc + 1;
                    productsItems.get(getLayoutPosition()).setItemQuantity(incrementLong);
                    counterTextview.setText(incrementLong + "");
                    long productPrice = Long.valueOf(productsItems.get(getLayoutPosition()).getProductPrice());
                    long itemQuantity = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());


                    iShopDetailAdapter.onIncrementButtonClick(productsItems.get(getLayoutPosition()).getItemQuantity(),
                            String.valueOf(productPrice),
                            String.valueOf(productPrice * itemQuantity),
                            productsItems.get(getLayoutPosition()).getProductName(),
                            productsItems.get(getLayoutPosition()).getCutPrice(),
                            productsItems.get(getLayoutPosition()).getByteImage(), imgProductCopy);


//                    long inc = productsItems.get(getLayoutPosition()).getItemQuantity() + 1;
//                    productsItems.get(getLayoutPosition()).setItemQuantity(inc);
//                    counterTextview.setText(inc + "");


//                    if (actionListener != null)
//                        actionListener.onItemTap(imgProductCopy);

                    break;

                case R.id.imgShareProductDetails:

                    BitmapDrawable bitmapDrawableImg = (BitmapDrawable) imgProduct.getDrawable();
                    Bitmap bitmapImage = bitmapDrawableImg.getBitmap();

                    iShopDetailAdapter.shareItemsDetails(productsItems.get(getLayoutPosition()), bitmapImage);
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

    public void removeAll(boolean status, int position) {
        productsItems.get(position).setFavourite(status);
        notifyDataSetChanged();
    }

    public void refreshProduct() {
        notifyDataSetChanged();

    }

    public interface IShopDetailAdapter {
        void onAddButtonClick(ShoppingDetailModel productItems);

        void onViewDetailsButtonClick(ShoppingDetailModel productItems);

        void onFavouriteButtonClick(ShoppingDetailModel productItems, int position);

        void onIncrementButtonClick(long quantity, String price, String totalPrice, String productName,
                                    long cutPrice, byte[] byteImage, ImageView imgProductCopy);

        void onDecrementButtonClick(long quantity, String price, String totalPrice, String productName,
                                    long cutPrice, byte[] byteImage, ImageView imgProductCopy);


        void onCardClick(ShoppingDetailModel productItems, Bitmap bitmapImg);

        void shareItemsDetails(ShoppingDetailModel productItems, Bitmap bitmapImg);

        void removeItemFromCart(ShoppingDetailModel shoppingDetailModel);
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

//
//    public interface ProductItemActionListener {
//        void onItemTap(ImageView imageView);
//    }

}
