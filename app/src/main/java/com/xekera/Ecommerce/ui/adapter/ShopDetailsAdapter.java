package com.xekera.Ecommerce.ui.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.varunest.sparkbutton.SparkButton;
import com.wang.avi.AVLoadingIndicatorView;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.rest.response.Product;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedFragment;
import com.xekera.Ecommerce.util.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class ShopDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Product> productsItems;
    List<Product> productsItemsSearch;
    IShopDetailAdapter iShopDetailAdapter;
//    private ProductItemActionListener actionListener;

    public ShopDetailsAdapter() {


    }

    public ShopDetailsAdapter(Context context, List<Product> productsItems, IShopDetailAdapter iShopDetailAdapter) {
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
        final Product shoppingDetailModel = productsItems.get(position);

        if (holder instanceof productDetailsDataListViewHolder) {
            final productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;

            productDetailsDataListViewHolder.productNameLabelTextView.setText(shoppingDetailModel.getName());
            productDetailsDataListViewHolder.priceTextView.setText(shoppingDetailModel.getPrice());
            productDetailsDataListViewHolder.discountPriceTextView.setText(shoppingDetailModel.getRegularPrice());


            try {
                if (shoppingDetailModel.getImageJson() != null &&
                        shoppingDetailModel.getImageJson().size() > 0) {
//                    String[] separated = new String[0];
//                    if (shoppingDetailModel.getImageJson().contains(",")) {
//                        separated = shoppingDetailModel.getImageJson().split(",");
//                    } else {
//                        separated[0] = shoppingDetailModel.getImageJson();
//                    }

                    Glide.with(context)
                            .load(shoppingDetailModel.getImageJson().get(0))
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
                                    productDetailsDataListViewHolder.imgProductCopy.setImageBitmap(resource);

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
                                    productDetailsDataListViewHolder.imgProductCopy.setImageBitmap(resource);

                                }

                                @Override
                                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                    super.onLoadFailed(e, errorDrawable);
                                    productDetailsDataListViewHolder.avloadingIndicatorView.setVisibility(View.GONE);
                                    productDetailsDataListViewHolder.imgProduct.setVisibility(View.VISIBLE);
                                }
                            });

                }

//                if (position == 0) {
//                    productDetailsDataListViewHolder.imgProduct.setImageResource(R.drawable.arduino_detail);
//                    productDetailsDataListViewHolder.imgProductCopy.setImageResource(R.drawable.arduino_detail);
//                }
//
//                if (position == 1) {
//                    productDetailsDataListViewHolder.imgProduct.setImageResource(R.drawable.detail_sensor_module);
//                    productDetailsDataListViewHolder.imgProductCopy.setImageResource(R.drawable.detail_sensor_module);
//                }
//                if (position == 2) {
//                    productDetailsDataListViewHolder.imgProduct.setImageResource(R.drawable.detail_wire);
//                    productDetailsDataListViewHolder.imgProductCopy.setImageResource(R.drawable.detail_wire);
//                }
//                if (position == 3) {
//                    productDetailsDataListViewHolder.imgProduct.setImageResource(R.drawable.details_battery);
//                    productDetailsDataListViewHolder.imgProductCopy.setImageResource(R.drawable.details_battery);
//                }
//                if (position == 4) {
//                    productDetailsDataListViewHolder.imgProduct.setImageResource(R.drawable.details_rectifier);
//                    productDetailsDataListViewHolder.imgProductCopy.setImageResource(R.drawable.details_rectifier);
//                }


            } catch (Exception e) {

            }

//            if (productsItems.get(position).isFavourite()) {
//                productDetailsDataListViewHolder.favouriteButton.setChecked(true);
//            } else {
//                productDetailsDataListViewHolder.favouriteButton.setChecked(false);
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
        @BindView(R.id.avloadingIndicatorView)
        public AVLoadingIndicatorView avloadingIndicatorView;
        @BindView(R.id.discountPriceTextView)
        public TextView discountPriceTextView;

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
//                     BitmapDrawable bitmapDrawable = (BitmapDrawable) imgProduct.getDrawable();
//                    Bitmap bitmapImg = bitmapDrawable.getBitmap();


//                    Bitmap bitmapImg = null;
//                    //BitmapFactory.Options options = new BitmapFactory.Options();
//                    // options.inJustDecodeBounds = true;
//                    //options.inSampleSize = 2;
//
//                    if (getLayoutPosition() == 0) {
//
//                        bitmapImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.arduino_detail);
//                    } else if (getLayoutPosition() == 1) {
//                        bitmapImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_sensor_module);
//
//                    } else if (getLayoutPosition() == 2) {
//                        bitmapImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_battery);
//
//                    } else if (getLayoutPosition() == 3) {
//                        bitmapImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_wire);
//
//                    } else if (getLayoutPosition() == 4) {
//                        bitmapImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_rectifier);
//
//                    }
//
                    cardViewParent.setClickable(false);

                    // String[] separated = new String[0];
                    Bitmap bitmap = null;

                    try {

//                        if (!productsItems.get(getLayoutPosition()).getImageJson().equalsIgnoreCase("")) {
//                            if (productsItems.get(getLayoutPosition()).getImageJson().contains(",")) {
//                                separated = productsItems.get(getLayoutPosition()).getImageJson().split(",");
//                            } else {
//                                separated[0] = productsItems.get(getLayoutPosition()).getImageJson();
//                            }
//                        }

                        //   URL url = new URL(separated[0]);
                        // bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
                        // bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    } catch (Exception e) {
                    }

                    try {
                        iShopDetailAdapter.onCardClick(productsItems.get(getLayoutPosition()).getName(),
                                productsItems.get(getLayoutPosition()).getPrice(),
                                Long.valueOf(productsItems.get(getLayoutPosition()).getRegularPrice()),
                                Long.valueOf(counterTextview.getText().toString()),
                                productsItems.get(getLayoutPosition()).getImageJson()
                                , bitmap, productsItems.get(getLayoutPosition()).getAboutProduct(),
                                productsItems.get(getLayoutPosition()).getProductSku(),
                                productsItems.get(getLayoutPosition()).getId());

                    } catch (Exception e) {

                    }

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
                        // productsItems.get(getLayoutPosition()).setFavourite(false);

                    } else {
                        ((SparkButton) v.findViewById(R.id.favouriteButton)).playAnimation();
                        ((SparkButton) v.findViewById(R.id.favouriteButton)).setChecked(true);

                        // productsItems.get(getLayoutPosition()).setFavourite(true);
                    }

//                    Bitmap bitmapFavourite = null;
//
//                    if (getLayoutPosition() == 0) {
//                        bitmapFavourite = BitmapFactory.decodeResource(context.getResources(), R.drawable.arduino_detail);
//                    } else if (getLayoutPosition() == 1) {
//                        bitmapFavourite = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_sensor_module);
//
//                    } else if (getLayoutPosition() == 2) {
//                        bitmapFavourite = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_battery);
//
//                    } else if (getLayoutPosition() == 3) {
//                        bitmapFavourite = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_wire);
//
//                    } else if (getLayoutPosition() == 4) {
//                        bitmapFavourite = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_rectifier);
//
//                    }

                    Bitmap bitmapFavourite = null;

                    //  try {
                    //    String[] separatedList = new String[0];

//                        if (!productsItems.get(getLayoutPosition()).getImageJson().equalsIgnoreCase("")) {
//                            if (productsItems.get(getLayoutPosition()).getImageJson().contains(",")) {
//                                separatedList = productsItems.get(getLayoutPosition()).getImageJson().split(",");
//                            } else {
//                                separatedList[0] = productsItems.get(getLayoutPosition()).getImageJson();
//                            }
//                        }

                    //  URL url = new URL(separatedList[0]);
                    //bitmapFavourite = BitmapFactory.decodeStream((InputStream) url.getContent());
                    //bitmapFavourite = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    //} catch(IOException e){
                    //}

                    iShopDetailAdapter.onFavouriteButtonClick(productsItems.get(getLayoutPosition()), getLayoutPosition(),
                            bitmapFavourite, counterTextview.getText().toString(),
                            productsItems.get(getLayoutPosition()).getImageJson().get(0),
                            productsItems.get(getLayoutPosition()).getId(),"0");
                    break;
                case R.id.decrementImageButton:
                    //  decrementCounter = decrementCounter - 1;
                    //productsItems.get(getLayoutPosition()).setItemQuantity(getItemQuantity());

                    // productsItems.get(getLayoutPosition()).setItemQuantity(getItemQuantity());

                    if (Long.valueOf(counterTextview.getText().toString()) > 1) {

                        long dec = Long.valueOf(counterTextview.getText().toString()) - 1;
                        // productsItems.get(getLayoutPosition()).setItemQuantity(dec);

                        counterTextview.setText(dec + "");
                        Bitmap bitmapAdd = null;

//                        try {
//                            String[] separatedItem = new String[0];
//
////                            if (!productsItems.get(getLayoutPosition()).getImageJson().equalsIgnoreCase("")) {
////                                if (productsItems.get(getLayoutPosition()).getImageJson().contains(",")) {
////                                    separatedItem = productsItems.get(getLayoutPosition()).getImageJson().split(",");
////                                } else {
////                                    separatedItem[0] = productsItems.get(getLayoutPosition()).getImageJson();
////                                }
////                            }
//
//                            URL url = new URL(separatedItem[0]);
//                            //bitmapAdd = BitmapFactory.decodeStream((InputStream) url.getContent());
//                            bitmapAdd = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//
//                        } catch (IOException e) {
//                        }

                        long productPrice = Long.valueOf(productsItems.get(getLayoutPosition()).getPrice());
                        long itemQuantity = Long.valueOf(counterTextview.getText().toString());
                        iShopDetailAdapter.onDecrementButtonClick(itemQuantity,
                                String.valueOf(productPrice),
                                String.valueOf(productPrice * itemQuantity),
                                productsItems.get(getLayoutPosition()).getName(),
                                Long.valueOf(productsItems.get(getLayoutPosition()).getRegularPrice()),
                                imgProductCopy, bitmapAdd, productsItems.get(getLayoutPosition()).getImageJson().get(0),
                                productsItems.get(getLayoutPosition()).getId(), "0");

                    } else {
                        // productsItems.get(getLayoutPosition()).setItemQuantity(0);
                        counterTextview.setText("0");

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

                    long inc = Long.valueOf(counterTextview.getText().toString());
                    long incrementLong = inc + 1;
                    // productsItems.get(getLayoutPosition()).setItemQuantity(incrementLong);
                    counterTextview.setText(incrementLong + "");
                    long productPrice = Long.valueOf(productsItems.get(getLayoutPosition()).getPrice());
                    long itemQuantity = Long.valueOf(counterTextview.getText().toString());


//                    Bitmap bitmapAdd = null;
//
//                    if (getLayoutPosition() == 0) {
//                        bitmapAdd = BitmapFactory.decodeResource(context.getResources(), R.drawable.arduino_detail);
//                    } else if (getLayoutPosition() == 1) {
//                        bitmapAdd = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_sensor_module);
//
//                    } else if (getLayoutPosition() == 2) {
//                        bitmapAdd = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_battery);
//
//                    } else if (getLayoutPosition() == 3) {
//                        bitmapAdd = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_wire);
//
//                    } else if (getLayoutPosition() == 4) {
//                        bitmapAdd = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_rectifier);
//
//                    }

                    Bitmap bitmapAdd = null;

//                    try {
//
//                        String[] separatedList = new String[0];
//
////                        if (!productsItems.get(getLayoutPosition()).getImageJson().equalsIgnoreCase("")) {
////                            if (productsItems.get(getLayoutPosition()).getImageJson().contains(",")) {
////                                separatedList = productsItems.get(getLayoutPosition()).getImageJson().split(",");
////                            } else {
////                                separatedList[0] = productsItems.get(getLayoutPosition()).getImageJson();
////                            }
////                        }
//
//                        URL url = new URL(separatedList[0]);
//                        // bitmapAdd = BitmapFactory.decodeStream((InputStream) url.getContent());
//                        bitmapAdd = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//
//                    } catch (IOException e) {
//                    }


                    iShopDetailAdapter.onIncrementButtonClick(Long.valueOf(counterTextview.getText().toString()),
                            String.valueOf(productPrice),
                            String.valueOf(productPrice * itemQuantity),
                            productsItems.get(getLayoutPosition()).getName(),
                            Long.valueOf(productsItems.get(getLayoutPosition()).getRegularPrice()), imgProductCopy,
                            bitmapAdd, productsItems.get(getLayoutPosition()).getImageJson().get(0),
                            productsItems.get(getLayoutPosition()).getId(), "0");


//                    long inc = productsItems.get(getLayoutPosition()).getItemQuantity() + 1;
//                    productsItems.get(getLayoutPosition()).setItemQuantity(inc);
//                    counterTextview.setText(inc + "");


//                    if (actionListener != null)
//                        actionListener.onItemTap(imgProductCopy);

                    break;

                case R.id.imgShareProductDetails:

                    //  BitmapDrawable bitmapDrawableImg = (BitmapDrawable) imgProduct.getDrawable();
                    //Bitmap bitmapImage = bitmapDrawableImg.getBitmap();

                    Bitmap bitmapImage = null;
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
        // productsItems.get(position).setFavourite(status);
        notifyDataSetChanged();
    }

    public void setIsfavourite(boolean status, int position) {
        // productsItems.get(position).setFavourite(status);
        notifyDataSetChanged();
    }

    public void refreshProduct() {
        notifyDataSetChanged();

    }

    public interface IShopDetailAdapter {
        void onAddButtonClick(Product productItems);

        void onViewDetailsButtonClick(Product productItems);

        void onFavouriteButtonClick(Product productItems, int position, Bitmap bitmap, String quantity, String imgUrl, String productID,String isEmailFav);

        void onIncrementButtonClick(long quantity, String price, String totalPrice, String productName,
                                    long cutPrice, ImageView imgProductCopy, Bitmap bitmap, String imgUrl,
                                    String productID, String isEmailSent);

        void onDecrementButtonClick(long quantity, String price, String totalPrice, String productName,
                                    long cutPrice, ImageView imgProductCopy, Bitmap bitmapAdd, String imgUrl, String productID, String isEmailSent);


        //void onCardClick(ShoppingDetailModel productItems, Bitmap bitmapImg);
        void onCardClick(String productName, String price, long cutPrice, long quantity, List<String> imgList,
                         Bitmap bitmapImg, String about, String sku, String productID);


        void shareItemsDetails(Product productItems, Bitmap bitmapImg);

        void removeItemFromCart(Product shoppingDetailModel);

        void getIsFavourites(String productName, int position);

    }


    private byte[] bitmapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
    }


    public void filter(String charText) {
        try {

            charText = charText.toLowerCase(Locale.getDefault());
            productsItems.clear();
            if (charText.length() == 0) {

                productsItems.addAll(productsItemsSearch);
            } else if (charText.length() > 0) {
                for (Product wp : productsItemsSearch) {
                    if (wp.getName().toLowerCase(Locale.getDefault()).trim()
                            .contains(charText)) {
                        productsItems.add(wp);

                    }
                }
            }

            notifyDataSetChanged();
        } catch (Exception ex) {
        }
    }

//
//    public interface ProductItemActionListener {
//        void onItemTap(ImageView imageView);
//    }

}
