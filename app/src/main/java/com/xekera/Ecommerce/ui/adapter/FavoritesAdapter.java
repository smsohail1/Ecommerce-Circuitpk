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
import com.xekera.Ecommerce.data.rest.response.fetch_favourite_response.Product;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.history.HistoryPresenter;
import retrofit2.http.Query;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Product> productsItems;
    // IShopDetailAdapter iShopDetailAdapter;
    HistoryPresenter historyPresenter;
    IFvaouritesAddToCartAdapter iFvaouritesAddToCartAdapter;

    public FavoritesAdapter(Context context, List<Product> productsItems, IFvaouritesAddToCartAdapter iFvaouritesAddToCartAdapter) {
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
        final Product favourites = productsItems.get(position);
        if (holder instanceof productDetailsDataListViewHolder) {
            final productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;

            productDetailsDataListViewHolder.productNameLabelTextView.setText(favourites.getName());
            productDetailsDataListViewHolder.priceTextView.setText(favourites.getPrice());

            if (favourites.getRegularPrice() != null) {

                productDetailsDataListViewHolder.discountPriceTextView.setText(favourites.getRegularPrice());
                if (favourites.getRegularPrice().equalsIgnoreCase("0")) {
                    productDetailsDataListViewHolder.discountLinearParent.setVisibility(View.GONE);
                }

            } else {
                productDetailsDataListViewHolder.discountPriceTextView.setText("");
                productDetailsDataListViewHolder.discountLinearParent.setVisibility(View.GONE);

            }

            // productDetailsDataListViewHolder.availabilitStockTextView.setText();
            //  productDetailsDataListViewHolder.counterTextview.setText(favourites.getItemQuantity() + "");

            try {

                Glide.with(context)
                        .load(favourites.getImageJson().get(0))
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
            itemView.findViewById(R.id.cardViewParent).setOnClickListener(this);

            // itemView.findViewById(R.id.messenger_send_button).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cardViewParent:
                    try {

                        //  productsItems.get(getLayoutPosition()).setItemQuantity(counterTextview.getText().toString());

                        iFvaouritesAddToCartAdapter.onCardClick(
                                productsItems.get(getLayoutPosition()));
                    } catch (Exception e) {

                    }
                    break;

                case R.id.btnAddToCart:
                    Product productDetail = productsItems.get(getLayoutPosition());
                    iFvaouritesAddToCartAdapter.addToCartServer(productDetail.getId(), counterTextview.getText().toString(),
                            productDetail.getPrice(),
                            "", productDetail.getRegularPrice(), imgProductCopy, getLayoutPosition(),
                            productDetail.getNameSku());
                    // iFvaouritesAddToCartAdapter.addToCartFavourites(productsItems.get(getLayoutPosition()), getLayoutPosition(),
                    //       imgProductCopy);
                    break;


                case R.id.imgRemove:
                    iFvaouritesAddToCartAdapter.removeFavourites(productsItems.get(getLayoutPosition()), getLayoutPosition());
                    break;

                case R.id.decrementImageButton:

//                    long dec = 0;
                    long clickCounts = Long.valueOf(counterTextview.getText().toString());

                    if (clickCounts == 1) {
                        counterTextview.setText("1");
                    } else {
                        counterTextview.setText(String.valueOf(clickCounts - 1));
                        ;
                    }
//                    if (clickCounts == 0) {
//                        dec = 0;
//
////                        productsItems.get(getLayoutPosition()).setItemQuantity(String.valueOf(dec));
////                        counterTextview.setText(dec + "");
//
////                        long productPriceDec = Long.valueOf(productsItems.get(getLayoutPosition()).getItemIndividualPrice());
////                        long itemQuantityDec = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
////                        iFvaouritesAddToCartAdapter.onDecrementButtonClick(Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity()),
////                                String.valueOf(productPriceDec),
////                                String.valueOf(productPriceDec * itemQuantityDec),
////                                productsItems.get(getLayoutPosition()).getItemName(),
////                                Long.valueOf(productsItems.get(getLayoutPosition()).getItemCutPrice()),
////                                productsItems.get(getLayoutPosition()).getItemImage(), imgProductCopy,
////                                productsItems.get(getLayoutPosition()).getImage(),
////                                productsItems.get(getLayoutPosition()).getProduct_id(), "0", productsItems.get(getLayoutPosition()).getProductDescFav(),
////                                productsItems.get(getLayoutPosition()).getImgArrListFav(),
////                                productsItems.get(getLayoutPosition()).getNameSku());
//
//                    } else {
////                        dec = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity()) - 1;
////                        productsItems.get(getLayoutPosition()).setItemQuantity(String.valueOf(dec));
////                        counterTextview.setText(dec + "");
////
////                        long productPriceDec = Long.valueOf(productsItems.get(getLayoutPosition()).getItemIndividualPrice());
////                        long itemQuantityDec = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
////                        iFvaouritesAddToCartAdapter.onDecrementButtonClick(Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity()),
////                                String.valueOf(productPriceDec),
////                                String.valueOf(productPriceDec * itemQuantityDec),
////                                productsItems.get(getLayoutPosition()).getItemName(),
////                                Long.valueOf(productsItems.get(getLayoutPosition()).getItemCutPrice()),
////                                productsItems.get(getLayoutPosition()).getItemImage(), imgProductCopy,
////                                productsItems.get(getLayoutPosition()).getImage(),
////                                productsItems.get(getLayoutPosition()).getProduct_id(), "0", productsItems.get(getLayoutPosition()).getProductDescFav(),
////                                productsItems.get(getLayoutPosition()).getImgArrListFav(),
////                                productsItems.get(getLayoutPosition()).getNameSku());
//
//                    }


                    break;
                case R.id.incrementImageButton:
                    long clickIncrementCounts = Long.valueOf(counterTextview.getText().toString());


                    counterTextview.setText(String.valueOf(clickIncrementCounts + 1));


//                    long inc = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
//                    long incrementLong = inc + 1;
//                    productsItems.get(getLayoutPosition()).setItemQuantity(String.valueOf(incrementLong));
//                    counterTextview.setText(incrementLong + "");
//                    long productPrice = Long.valueOf(productsItems.get(getLayoutPosition()).getItemIndividualPrice());
//                    long itemQuantity = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
//
//                    Bitmap bitmapAdd = null;
//
//
//                    iFvaouritesAddToCartAdapter.onIncrementButtonClick(Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity()),
//                            String.valueOf(productPrice),
//                            String.valueOf(productPrice * itemQuantity),
//                            productsItems.get(getLayoutPosition()).getItemName(),
//                            Long.valueOf(productsItems.get(getLayoutPosition()).getItemCutPrice()),
//                            productsItems.get(getLayoutPosition()).getItemImage(), imgProductCopy, bitmapAdd,
//                            productsItems.get(getLayoutPosition()).getImage(),
//                            productsItems.get(getLayoutPosition()).getProduct_id(), "0",
//                            productsItems.get(getLayoutPosition()).getProductDescFav(),
//                            productsItems.get(getLayoutPosition()).getImgArrListFav(),
//                            productsItems.get(getLayoutPosition()).getNameSku());
//

                    break;


                case R.id.imgShareProductDetails:

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


    public void addAll(List<Product> addToCarts) {
        int currentListSize = this.productsItems.size();
        this.productsItems.addAll(addToCarts);
        notifyItemRangeInserted(currentListSize, addToCarts.size());
    }


    public interface IFvaouritesAddToCartAdapter {
        void onCardClick(Product favourites);

        void addToCartFavourites(Favourites favourites, int position, ImageView img);

        void removeFavourites(Product favourites, int position);

        void onIncrementButtonClick(long quantity, String price, String totalPrice, String productName,
                                    long cutPrice, byte[] byteImage, ImageView imgProductCopy, Bitmap bitmap, String imgUrl,
                                    String productID, String isEmailFav, String productDesc, String imgArrList, String nameSku);

        void onDecrementButtonClick(long quantity, String price, String totalPrice, String productName,
                                    long cutPrice, byte[] byteImage, ImageView imgProductCopy, String imgUrl,
                                    String productID, String isEmailFav, String productDesc, String imgArrList, String nameSku);

        void removeItemFromCart(Favourites favourites);

        void onClickButtonMessenger();

        void onClickShareButton(Product favourites);

        void addToCartServer(String product_id,
                             String itemQuantity,
                             String itemPrice,
                             String last_id, String discountPrice, ImageView imgProductCopy, int position,
                             String nameSku);

    }
}


