package com.xekera.Ecommerce.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.history.HistoryPresenter;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Favourites> productsItems;
    // IShopDetailAdapter iShopDetailAdapter;
    HistoryPresenter historyPresenter;
    IFvaouritesAddToCartAdapter iFvaouritesAddToCartAdapter;

    public FavoritesAdapter(Context context, List<Favourites> productsItems, IFvaouritesAddToCartAdapter iFvaouritesAddToCartAdapter) {
        this.context = context;
        this.productsItems = productsItems;
        this.iFvaouritesAddToCartAdapter = iFvaouritesAddToCartAdapter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favourites_fragment, parent, false);
        productDetailsDataListViewHolder productDetailDataListViewHolder = new productDetailsDataListViewHolder(v);
        return productDetailDataListViewHolder;
    }

    Bitmap compressedBitmap;
    // byte[] bytes;
    BitmapFactory.Options options;


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Favourites favourites = productsItems.get(position);
        if (holder instanceof productDetailsDataListViewHolder) {
            final productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;

            productDetailsDataListViewHolder.productNameLabelTextView.setText(favourites.getItemName());
            productDetailsDataListViewHolder.priceTextView.setText(favourites.getItemIndividualPrice());

            if (favourites.getItemCutPrice() != null) {

                productDetailsDataListViewHolder.discountPriceTextView.setText(favourites.getItemCutPrice());
                if (favourites.getItemCutPrice().equalsIgnoreCase("0")) {
                    productDetailsDataListViewHolder.discountLinearParent.setVisibility(View.GONE);
                }

            } else {
                productDetailsDataListViewHolder.discountPriceTextView.setText("");
                productDetailsDataListViewHolder.discountLinearParent.setVisibility(View.GONE);

            }

            productDetailsDataListViewHolder.availabilitStockTextView.setText(favourites.getItemStockStatus());
            productDetailsDataListViewHolder.counterTextview.setText(favourites.getItemQuantity() + "");

            try {

                Glide.with(context)
                        .load(favourites.getImage())
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
                                productDetailsDataListViewHolder.imgProductCopy.setImageBitmap(resource);
                                productDetailsDataListViewHolder.imgProduct.setVisibility(View.VISIBLE);
                                productDetailsDataListViewHolder.imgProductCopy.setVisibility(View.VISIBLE);


                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                productDetailsDataListViewHolder.avloadingIndicatorView.setVisibility(View.GONE);
                                productDetailsDataListViewHolder.imgProduct.setVisibility(View.VISIBLE);
                                productDetailsDataListViewHolder.imgProductCopy.setVisibility(View.VISIBLE);


                            }
                        });

            } catch (Exception e) {

            }

//            try {
//
////                options = new BitmapFactory.Options();
////                //  options.inJustDecodeBounds = true;
////                options.inSampleSize = 2;
//
//               // bytes = favourites.getItemImage();
////                // Create a bitmap from the byte array
////                compressedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
////
////                productDetailsDataListViewHolder.imgProduct.setImageBitmap(compressedBitmap);
////                productDetailsDataListViewHolder.imgProductCopy.setImageBitmap(compressedBitmap);
//
//
//                Glide.with(context)
//                        .load(bytes)
//                        .asBitmap()
//                        .placeholder(R.drawable.placeholder)
//                        .into(productDetailsDataListViewHolder.imgProduct);
//
//                Glide.with(context)
//                        .load(bytes)
//                        .asBitmap()
//                        .placeholder(R.drawable.placeholder)
//                        .into(productDetailsDataListViewHolder.imgProductCopy);
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
        @BindView(R.id.imgProductCopy)
        public ImageView imgProductCopy;
        @BindView(R.id.cardViewParent)
        public CardView cardViewParent;
        @BindView(R.id.availabilitStockTextView)
        public TextView availabilitStockTextView;
        @BindView(R.id.discountPriceTextView)
        public TextView discountPriceTextView;
        @BindView(R.id.btnAddToCart)
        public ImageView btnAddToCart;
        @BindView(R.id.imgRemove)
        public ImageView imgRemove;
        @BindView(R.id.decrementImageButton)
        public ImageView decrementImageButton;
        @BindView(R.id.incrementImageButton)
        public ImageView incrementImageButton;
        @BindView(R.id.counterTextview)
        public TextView counterTextview;
        @BindView(R.id.imgShareProductDetails)
        public ImageView imgShareProductDetails;
        @BindView(R.id.avloadingIndicatorView)
        public AVLoadingIndicatorView avloadingIndicatorView;
        @BindView(R.id.discountLinearParent)
        public LinearLayout discountLinearParent;


        public productDetailsDataListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.btnAddToCart).setOnClickListener(this);
            itemView.findViewById(R.id.imgRemove).setOnClickListener(this);
            itemView.findViewById(R.id.decrementImageButton).setOnClickListener(this);
            itemView.findViewById(R.id.incrementImageButton).setOnClickListener(this);
            itemView.findViewById(R.id.imgShareProductDetails).setOnClickListener(this);

            // itemView.findViewById(R.id.messenger_send_button).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAddToCart:
                    iFvaouritesAddToCartAdapter.addToCartFavourites(productsItems.get(getLayoutPosition()), getLayoutPosition(),
                            imgProductCopy);
                    break;


                case R.id.imgRemove:
                    iFvaouritesAddToCartAdapter.removeFavourites(productsItems.get(getLayoutPosition()), getLayoutPosition());
                    break;

                case R.id.decrementImageButton:
                    //  decrementCounter = decrementCounter - 1;
                    //productsItems.get(getLayoutPosition()).setItemQuantity(getItemQuantity());

                    // productsItems.get(getLayoutPosition()).setItemQuantity(getItemQuantity());

                    long dec = 0;
                    if (Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity()) == 0) {
                        dec = 0;

                        productsItems.get(getLayoutPosition()).setItemQuantity(String.valueOf(dec));
                        counterTextview.setText(dec + "");

                        long productPriceDec = Long.valueOf(productsItems.get(getLayoutPosition()).getItemIndividualPrice());
                        long itemQuantityDec = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
                        iFvaouritesAddToCartAdapter.onDecrementButtonClick(Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity()),
                                String.valueOf(productPriceDec),
                                String.valueOf(productPriceDec * itemQuantityDec),
                                productsItems.get(getLayoutPosition()).getItemName(),
                                Long.valueOf(productsItems.get(getLayoutPosition()).getItemCutPrice()),
                                productsItems.get(getLayoutPosition()).getItemImage(), imgProductCopy,
                                productsItems.get(getLayoutPosition()).getImage(),
                                productsItems.get(getLayoutPosition()).getProduct_id(), "0", productsItems.get(getLayoutPosition()).getProductDescFav(),
                                productsItems.get(getLayoutPosition()).getImgArrListFav(),
                                productsItems.get(getLayoutPosition()).getNameSku());

                    } else {
                        dec = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity()) - 1;
                        productsItems.get(getLayoutPosition()).setItemQuantity(String.valueOf(dec));
                        counterTextview.setText(dec + "");

                        long productPriceDec = Long.valueOf(productsItems.get(getLayoutPosition()).getItemIndividualPrice());
                        long itemQuantityDec = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
                        iFvaouritesAddToCartAdapter.onDecrementButtonClick(Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity()),
                                String.valueOf(productPriceDec),
                                String.valueOf(productPriceDec * itemQuantityDec),
                                productsItems.get(getLayoutPosition()).getItemName(),
                                Long.valueOf(productsItems.get(getLayoutPosition()).getItemCutPrice()),
                                productsItems.get(getLayoutPosition()).getItemImage(), imgProductCopy,
                                productsItems.get(getLayoutPosition()).getImage(),
                                productsItems.get(getLayoutPosition()).getProduct_id(), "0", productsItems.get(getLayoutPosition()).getProductDescFav(),
                                productsItems.get(getLayoutPosition()).getImgArrListFav(),
                                productsItems.get(getLayoutPosition()).getNameSku());

                    }


                    // else {
                    // productsItems.get(getLayoutPosition()).setItemQuantity("0");
                    //   counterTextview.setText(productsItems.get(getLayoutPosition()).getItemQuantity() + "");

                    // iFvaouritesAddToCartAdapter.removeItemFromCart(productsItems.get(getLayoutPosition()));


//                        long productPrice = Long.valueOf(productsItems.get(getLayoutPosition()).getProductPrice());
//                        long itemQuantity = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
//                        iShopDetailAdapter.onDecrementButtonClick(productsItems.get(getLayoutPosition()).getItemQuantity(),
//                                String.valueOf(productPrice),
//                                String.valueOf(productPrice * itemQuantity),
//                                productsItems.get(getLayoutPosition()).getProductName(),
//                                productsItems.get(getLayoutPosition()).getCutPrice(),
//                                productsItems.get(getLayoutPosition()).getByteImage(), imgProductCopy);

                    //}
                    break;
                case R.id.incrementImageButton:

                    long inc = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
                    long incrementLong = inc + 1;
                    productsItems.get(getLayoutPosition()).setItemQuantity(String.valueOf(incrementLong));
                    counterTextview.setText(incrementLong + "");
                    long productPrice = Long.valueOf(productsItems.get(getLayoutPosition()).getItemIndividualPrice());
                    long itemQuantity = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());

                    Bitmap bitmapAdd = null;

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

                    iFvaouritesAddToCartAdapter.onIncrementButtonClick(Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity()),
                            String.valueOf(productPrice),
                            String.valueOf(productPrice * itemQuantity),
                            productsItems.get(getLayoutPosition()).getItemName(),
                            Long.valueOf(productsItems.get(getLayoutPosition()).getItemCutPrice()),
                            productsItems.get(getLayoutPosition()).getItemImage(), imgProductCopy, bitmapAdd,
                            productsItems.get(getLayoutPosition()).getImage(),
                            productsItems.get(getLayoutPosition()).getProduct_id(), "0",
                            productsItems.get(getLayoutPosition()).getProductDescFav(),
                            productsItems.get(getLayoutPosition()).getImgArrListFav(),
                            productsItems.get(getLayoutPosition()).getNameSku());


//                    long inc = productsItems.get(getLayoutPosition()).getItemQuantity() + 1;
//                    productsItems.get(getLayoutPosition()).setItemQuantity(inc);
//                    counterTextview.setText(inc + "");


//                    if (actionListener != null)
//                        actionListener.onItemTap(imgProductCopy);

                    break;

//                case R.id.messenger_send_button:
//                    iFvaouritesAddToCartAdapter.onClickButtonMessenger();
//                    break;

                case R.id.imgShareProductDetails:
                    // Bitmap bitmapShare = null;

//                    if (getLayoutPosition() == 0) {
//                        bitmapShare = BitmapFactory.decodeResource(context.getResources(), R.drawable.arduino_detail);
//                    } else if (getLayoutPosition() == 1) {
//                        bitmapShare = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_sensor_module);
//
//                    }
// else if (getLayoutPosition() == 2) {
//                        bitmapShare = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_battery);
//
//                    } else if (getLayoutPosition() == 3) {
//                        bitmapShare = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_wire);
//
//                    } else if (getLayoutPosition() == 4) {
//                        bitmapShare = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_rectifier);
//
//                    }
                    iFvaouritesAddToCartAdapter.onClickShareButton(productsItems.get(getLayoutPosition()));
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


    public void removeByPosition(int position) {
        this.productsItems.remove(position);
        notifyItemRemoved(position);
        //notifyDataSetChanged();
    }

    public void refreshProduct() {
        notifyDataSetChanged();
    }


    public void addAll(List<Favourites> addToCarts) {
        int currentListSize = this.productsItems.size();
        this.productsItems.addAll(addToCarts);
        notifyItemRangeInserted(currentListSize, addToCarts.size());
    }


    public interface IFvaouritesAddToCartAdapter {
        void addToCartFavourites(Favourites favourites, int position, ImageView img);

        void removeFavourites(Favourites favourites, int position);

        void onIncrementButtonClick(long quantity, String price, String totalPrice, String productName,
                                    long cutPrice, byte[] byteImage, ImageView imgProductCopy, Bitmap bitmap, String imgUrl,
                                    String productID, String isEmailFav, String productDesc, String imgArrList, String nameSku);

        void onDecrementButtonClick(long quantity, String price, String totalPrice, String productName,
                                    long cutPrice, byte[] byteImage, ImageView imgProductCopy, String imgUrl,
                                    String productID, String isEmailFav, String productDesc, String imgArrList, String nameSku);

        void removeItemFromCart(Favourites favourites);

        void onClickButtonMessenger();

        void onClickShareButton(Favourites favourites);

    }
}


