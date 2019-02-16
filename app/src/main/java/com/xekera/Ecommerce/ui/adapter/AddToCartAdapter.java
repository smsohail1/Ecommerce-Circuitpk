package com.xekera.Ecommerce.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;

public class AddToCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<AddToCart> productsItems;
    // IShopDetailAdapter iShopDetailAdapter;
    //  AddToCartPresenter addToCartPresenter;
    IShopDetailAdapter iShopDetailAdapter;

    public AddToCartAdapter(List<AddToCart> productsItems, IShopDetailAdapter iShopDetailAdapter) {
        this.productsItems = productsItems;
        this.iShopDetailAdapter = iShopDetailAdapter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_to_cart_fragment, parent, false);
        productDetailsDataListViewHolder productDetailDataListViewHolder = new productDetailsDataListViewHolder(v);
        return productDetailDataListViewHolder;
    }


    Bitmap compressedBitmap;
    BitmapFactory.Options options;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AddToCart addToCart = productsItems.get(position);
        //  byte[] bytes;
        if (holder instanceof productDetailsDataListViewHolder) {
            final productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;

            productDetailsDataListViewHolder.productNameLabelTextView.setText(addToCart.getItemName());
            productDetailsDataListViewHolder.priceTextView.setText(addToCart.getItemIndividualPrice());
            productDetailsDataListViewHolder.counterTextview.setText(addToCart.getItemQuantity());

            if (addToCart.getItemCutPrice() != null) {
                productDetailsDataListViewHolder.discountPriceTextView.setText(addToCart.getItemCutPrice());
                if (addToCart.getItemCutPrice().equalsIgnoreCase("0")) {
                    productDetailsDataListViewHolder.discountLinearParent.setVisibility(View.GONE);
                }
            } else {
                productDetailsDataListViewHolder.discountPriceTextView.setText("");
                productDetailsDataListViewHolder.discountLinearParent.setVisibility(View.GONE);

            }

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
////                options = new BitmapFactory.Options();
////                //  options.inJustDecodeBounds = true;
////                options.inSampleSize = 2;
////
//               // bytes = addToCart.getItemImage();
////                // Create a bitmap from the byte array
////                compressedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
////
////                productDetailsDataListViewHolder.imgProduct.setImageBitmap(compressedBitmap);
//
////                Glide.with(context)
////                        .load(bytes)
////                        .asBitmap()
////                        .placeholder(R.drawable.placeholder)
////                        .into(productDetailsDataListViewHolder.imgProduct);
//
//            } catch (Exception e) {
//
//            }
            // ImageView imageView = productDetailsDataListViewHolder.imgProduct;


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
        @BindView(R.id.imgProduct)
        public ImageView imgProduct;
        @BindView(R.id.decrementImageButton)
        public ImageView decrementImageButton;
        @BindView(R.id.incrementImageButton)
        public ImageView incrementImageButton;
        @BindView(R.id.counterTextview)
        public TextView counterTextview;
        @BindView(R.id.cardViewParent)
        public CardView cardViewParent;
        @BindView(R.id.imgRemoveProduct)
        public ImageView imgRemoveProduct;
        @BindView(R.id.discountPriceTextView)
        public TextView discountPriceTextView;
        @BindView(R.id.avloadingIndicatorView)
        public AVLoadingIndicatorView avloadingIndicatorView;
        @BindView(R.id.discountLinearParent)
        public LinearLayout discountLinearParent;

        public productDetailsDataListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.decrementImageButton).setOnClickListener(this);
            itemView.findViewById(R.id.incrementImageButton).setOnClickListener(this);
            itemView.findViewById(R.id.imgRemoveProduct).setOnClickListener(this);
            itemView.findViewById(R.id.cardViewParent).setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.cardViewParent:
                    try {

                        iShopDetailAdapter.onCardClick(
                                productsItems.get(getLayoutPosition()).getItemName(),
                                productsItems.get(getLayoutPosition()).getItemIndividualPrice(),
                                productsItems.get(getLayoutPosition()).getItemCutPrice(),
                                productsItems.get(getLayoutPosition()).getItemQuantity(),
                                productsItems.get(getLayoutPosition()).getImage(),
                                productsItems.get(getLayoutPosition()).getImgArrList(),
                                productsItems.get(getLayoutPosition()).getProduct_id(),
                                productsItems.get(getLayoutPosition()).getProductDesc());
                    } catch (Exception e) {

                    }
                    break;

                case R.id.decrementImageButton:
                    long dec = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
                    if (dec > 1) {

                        long decrementLong = dec - 1;
                        productsItems.get(getLayoutPosition()).setItemQuantity(String.valueOf(decrementLong));

                        counterTextview.setText(decrementLong + "");

                        long productPrice = Long.valueOf(productsItems.get(getLayoutPosition()).getItemIndividualPrice());
                        long itemQuantity = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());

//                        addToCartPresenter.updateItemCountInDB(productsItems.get(getLayoutPosition()).getItemQuantity(),
//                                String.valueOf(productPrice * itemQuantity),
//                                productsItems.get(getLayoutPosition()).getItemName(),
//                                productsItems.get(getLayoutPosition()).getItemCutPrice());

                        iShopDetailAdapter.incrementDecrement(productsItems.get(getLayoutPosition()).getItemQuantity(),
                                productPrice,
                                String.valueOf(productPrice * itemQuantity),
                                productsItems.get(getLayoutPosition()).getItemName(),
                                productsItems.get(getLayoutPosition()).getItemCutPrice(),
                                productsItems.get(getLayoutPosition()).getItemImage(),
                                productsItems.get(getLayoutPosition()).getImage(),
                                productsItems.get(getLayoutPosition()).getProduct_id(), "0",
                                productsItems.get(getLayoutPosition()).getProductDesc(),
                                productsItems.get(getLayoutPosition()).getImgArrList());

                    } else {
                        productsItems.get(getLayoutPosition()).setItemQuantity("1");

                        counterTextview.setText(productsItems.get(getLayoutPosition()).getItemQuantity() + "");

                        long productPrice = Long.valueOf(productsItems.get(getLayoutPosition()).getItemIndividualPrice());
                        long itemQuantity = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());


                        iShopDetailAdapter.incrementDecrement(productsItems.get(getLayoutPosition()).getItemQuantity(),
                                productPrice,
                                String.valueOf(productPrice * itemQuantity),
                                productsItems.get(getLayoutPosition()).getItemName(),
                                productsItems.get(getLayoutPosition()).getItemCutPrice(),
                                productsItems.get(getLayoutPosition()).getItemImage(),
                                productsItems.get(getLayoutPosition()).getImage(),
                                productsItems.get(getLayoutPosition()).getProduct_id(), "0",
                                productsItems.get(getLayoutPosition()).getProductDesc(),
                                productsItems.get(getLayoutPosition()).getImgArrList()
                        );

//                        addToCartPresenter.updateItemCountInDB(productsItems.get(getLayoutPosition()).getItemQuantity(),
//                                String.valueOf(productPrice * itemQuantity),
//                                productsItems.get(getLayoutPosition()).getItemName(),
//                                productsItems.get(getLayoutPosition()).getItemCutPrice());

                    }
                    break;
                case R.id.incrementImageButton:
                    long inc = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());
                    long incrementLong = inc + 1;
                    productsItems.get(getLayoutPosition()).setItemQuantity(String.valueOf(incrementLong));
                    counterTextview.setText(incrementLong + "");

                    long productPrice = Long.valueOf(productsItems.get(getLayoutPosition()).getItemIndividualPrice());
                    long itemQuantity = Long.valueOf(productsItems.get(getLayoutPosition()).getItemQuantity());


                    iShopDetailAdapter.incrementDecrement(productsItems.get(getLayoutPosition()).getItemQuantity(),
                            productPrice,
                            String.valueOf(productPrice * itemQuantity),
                            productsItems.get(getLayoutPosition()).getItemName(),
                            productsItems.get(getLayoutPosition()).getItemCutPrice(),
                            productsItems.get(getLayoutPosition()).getItemImage(),
                            productsItems.get(getLayoutPosition()).getImage(),
                            productsItems.get(getLayoutPosition()).getProduct_id(), "0",
                            productsItems.get(getLayoutPosition()).getProductDesc(),
                            productsItems.get(getLayoutPosition()).getImgArrList());

//                    addToCartPresenter.updateItemCountInDB(productsItems.get(getLayoutPosition()).getItemQuantity(),
//                            String.valueOf(productPrice * itemQuantity),
//                            productsItems.get(getLayoutPosition()).getItemName(),
//                            productsItems.get(getLayoutPosition()).getItemCutPrice());

                    break;
                case R.id.imgRemoveProduct:
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.fragment_cancel_dialog);

                    Button no = dialog.findViewById(R.id.cancel);
                    Button yes = dialog.findViewById(R.id.submit);
                    TextView txtMessage = dialog.findViewById(R.id.txtMessage);
                    TextView txtTitle = dialog.findViewById(R.id.txtTitle);

                    no.setText("No");
                    yes.setText("Yes");
                    txtTitle.setText("Remove Item");
                    txtMessage.setText("Are you sure you want to remove item from cart?");
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            // productsItems.remove(getLayoutPosition());
                            // addToCartPresenter.removeItemFromCart(productsItems.get(getLayoutPosition()));
                            iShopDetailAdapter.removeItemFromCart(productsItems.get(getLayoutPosition()), getLayoutPosition());
                            //productsItems.remove(getLayoutPosition());
                            // notifyDataSetChanged();


                            // iShopDetailAdapter.removeItemFromCart();
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

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

    public void removeItem(int position) {
        this.productsItems.remove(position);
        notifyItemRemoved(position);
    }

    public void refreshProduct() {
        notifyDataSetChanged();

    }


    public void addAll(List<AddToCart> addToCarts) {
        int currentListSize = this.productsItems.size();
        this.productsItems.addAll(addToCarts);
        notifyItemRangeInserted(currentListSize, addToCarts.size());
    }

    public interface IShopDetailAdapter {

        void onIncrementButtonClick(AddToCart productItems);

        void onDecrementButtonClick(AddToCart productItems);

        void incrementDecrement(String quantity, long individualPrice, String itemPrice, String productName,
                                String cutPrice, byte[] bytes, String imgUrl, String prodcutID, String isEmailSent,
                                String productDesc, String imgArrList);

        void removeItemFromCart(AddToCart productItems, int position);

        void onCardClick(String productName, String price, String cutPrice, String quantity, String img, String imgList,
                         String productID, String about);
    }

}

