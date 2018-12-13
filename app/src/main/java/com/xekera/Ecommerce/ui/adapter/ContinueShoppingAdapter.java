package com.xekera.Ecommerce.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import com.varunest.sparkbutton.SparkButton;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShoppingObjectModel;
import com.xekera.Ecommerce.util.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContinueShoppingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ContinueShoppingObjectModel> productsItems;
    List<ContinueShoppingObjectModel> productsItemsSearch;
    IShopDetailAdapter iShopDetailAdapter;
    SessionManager sessionManager;
    List<String> favList;

//    private ProductItemActionListener actionListener;

    public ContinueShoppingAdapter() {

    }

    public ContinueShoppingAdapter(Context context, List<ContinueShoppingObjectModel> productsItems,
                                   IShopDetailAdapter iShopDetailAdapter,
                                   SessionManager sessionManager) {
        this.context = context;
        this.productsItems = productsItems;
        this.iShopDetailAdapter = iShopDetailAdapter;
        this.productsItemsSearch = new ArrayList<>();
        this.productsItemsSearch.addAll(productsItems);
        this.sessionManager = sessionManager;

    }


//    public void setActionListener(ProductItemActionListener actionListener) {
//        this.actionListener = actionListener;
//    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_continue_shopping, parent, false);
        productDetailsDataListViewHolder productDetailDataListViewHolder = new productDetailsDataListViewHolder(v);
        return productDetailDataListViewHolder;
    }


    List<String> tasksList;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ContinueShoppingObjectModel ContinueShoppingObjectModel = productsItems.get(position);
//        Set<String> list = sessionManager.getIsFavouriteList();
//        tasksList = new ArrayList<String>(list);

        if (holder instanceof productDetailsDataListViewHolder) {
            productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;

            productDetailsDataListViewHolder.productNameLabelTextView.setText(ContinueShoppingObjectModel.getProductName());
            productDetailsDataListViewHolder.priceTextView.setText(ContinueShoppingObjectModel.getProductPrice());
            productDetailsDataListViewHolder.counterTextview.setText(ContinueShoppingObjectModel.getItemQuantity() + "");
            if (ContinueShoppingObjectModel.getCategoryLabel().equalsIgnoreCase("")) {
                productDetailsDataListViewHolder.productCategoryLabelLayout.setVisibility(View.GONE);
            } else {
                productDetailsDataListViewHolder.productCategoryLabelTextview.setText(ContinueShoppingObjectModel.getCategoryLabel());
                productDetailsDataListViewHolder.productCategoryLabelLayout.setVisibility(View.VISIBLE);

            }
            try {


                if (position == 0) {
                    productDetailsDataListViewHolder.imgProduct.setImageResource(R.drawable.arduino_detail);
                    productDetailsDataListViewHolder.imgProductCopy.setImageResource(R.drawable.arduino_detail);
                }

                if (position == 1) {
                    productDetailsDataListViewHolder.imgProduct.setImageResource(R.drawable.detail_sensor_module);
                    productDetailsDataListViewHolder.imgProductCopy.setImageResource(R.drawable.detail_sensor_module);
                }
                if (position == 2) {

                    productDetailsDataListViewHolder.imgProduct.setImageResource(R.drawable.detail_wire);
                    productDetailsDataListViewHolder.imgProductCopy.setImageResource(R.drawable.detail_wire);
                }
                if (position == 3) {
                    productDetailsDataListViewHolder.imgProduct.setImageResource(R.drawable.details_battery);
                    productDetailsDataListViewHolder.imgProductCopy.setImageResource(R.drawable.details_battery);
                }
                if (position == 4) {
                    productDetailsDataListViewHolder.imgProduct.setImageResource(R.drawable.details_rectifier);
                    productDetailsDataListViewHolder.imgProductCopy.setImageResource(R.drawable.details_rectifier);
                }
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
        @BindView(R.id.viewDetailsImageView)
        public Button viewDetailsImageView;
        @BindView(R.id.AddImageView)
        public Button AddImageView;
        @BindView(R.id.imgProduct)
        public ImageView imgProduct;
        @BindView(R.id.imgProductCopy)
        public ImageView imgProductCopy;
        //@BindView(R.id.favouriteButton)
        // public SparkButton favouriteButton;
        @BindView(R.id.decrementImageButton)
        public ImageView decrementImageButton;
        @BindView(R.id.incrementImageButton)
        public ImageView incrementImageButton;
        @BindView(R.id.counterTextview)
        public TextView counterTextview;
        @BindView(R.id.cardViewParent)
        public CardView cardViewParent;
        @BindView(R.id.productCategoryLabelLayout)
        public LinearLayout productCategoryLabelLayout;
        @BindView(R.id.productCategoryLabelTextview)
        public TextView productCategoryLabelTextview;


        public productDetailsDataListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

            cardViewParent.setClickable(true);

            itemView.findViewById(R.id.decrementImageButton).setOnClickListener(this);
            itemView.findViewById(R.id.incrementImageButton).setOnClickListener(this);
            itemView.findViewById(R.id.cardViewParent).setOnClickListener(this);
            //  itemView.findViewById(R.id.favouriteButton).setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.cardViewParent:
//                     BitmapDrawable bitmapDrawable = (BitmapDrawable) imgProduct.getDrawable();
//                    Bitmap bitmapImg = bitmapDrawable.getBitmap();


                    Bitmap bitmapImg = null;
                    //BitmapFactory.Options options = new BitmapFactory.Options();
                    // options.inJustDecodeBounds = true;
                    //options.inSampleSize = 2;

                    if (getLayoutPosition() == 0) {

                        bitmapImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.arduino_detail);
                    } else if (getLayoutPosition() == 1) {
                        bitmapImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_sensor_module);

                    } else if (getLayoutPosition() == 2) {
                        bitmapImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_battery);

                    } else if (getLayoutPosition() == 3) {
                        bitmapImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_wire);

                    } else if (getLayoutPosition() == 4) {
                        bitmapImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_rectifier);

                    }
                    cardViewParent.setClickable(false);
                    iShopDetailAdapter.onCardClick(productsItems.get(getLayoutPosition()).getProductName(),
                            productsItems.get(getLayoutPosition()).getProductPrice(),
                            productsItems.get(getLayoutPosition()).getCutPrice(),
                            productsItems.get(getLayoutPosition()).getItemQuantity(),
                            productsItems.get(getLayoutPosition()).getImage()
                            , bitmapImg);
                    break;

                case R.id.AddImageView:
                    iShopDetailAdapter.onAddButtonClick(productsItems.get(getLayoutPosition()));

                    break;
                case R.id.viewDetailsImageView:
                    iShopDetailAdapter.onViewDetailsButtonClick(productsItems.get(getLayoutPosition()));
                    break;

//                case R.id.favouriteButton:
//                    if (((SparkButton) v.findViewById(R.id.favouriteButton)).isChecked()) {
//                        ((SparkButton) v.findViewById(R.id.favouriteButton)).setChecked(false);
//                        productsItems.get(getLayoutPosition()).setFavourite(false);
//
//                    } else {
//                        ((SparkButton) v.findViewById(R.id.favouriteButton)).playAnimation();
//                        ((SparkButton) v.findViewById(R.id.favouriteButton)).setChecked(true);
//
//                        productsItems.get(getLayoutPosition()).setFavourite(true);
//                    }
//
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
//                    iShopDetailAdapter.onFavouriteButtonClick(productsItems.get(getLayoutPosition()), getLayoutPosition(), bitmapFavourite);
//                    break;
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

                    Bitmap bitmapAdd = null;

                    if (getLayoutPosition() == 0) {
                        bitmapAdd = BitmapFactory.decodeResource(context.getResources(), R.drawable.arduino_detail);
                    } else if (getLayoutPosition() == 1) {
                        bitmapAdd = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_sensor_module);

                    } else if (getLayoutPosition() == 2) {
                        bitmapAdd = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_battery);

                    } else if (getLayoutPosition() == 3) {
                        bitmapAdd = BitmapFactory.decodeResource(context.getResources(), R.drawable.detail_wire);

                    } else if (getLayoutPosition() == 4) {
                        bitmapAdd = BitmapFactory.decodeResource(context.getResources(), R.drawable.details_rectifier);

                    }

                    iShopDetailAdapter.onIncrementButtonClick(productsItems.get(getLayoutPosition()).getItemQuantity(),
                            String.valueOf(productPrice),
                            String.valueOf(productPrice * itemQuantity),
                            productsItems.get(getLayoutPosition()).getProductName(),
                            productsItems.get(getLayoutPosition()).getCutPrice(),
                            productsItems.get(getLayoutPosition()).getByteImage(), imgProductCopy, bitmapAdd);


//                    long inc = productsItems.get(getLayoutPosition()).getItemQuantity() + 1;
//                    productsItems.get(getLayoutPosition()).setItemQuantity(inc);
//                    counterTextview.setText(inc + "");


//                    if (actionListener != null)
//                        actionListener.onItemTap(imgProductCopy);

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

    public void setIsfavourite(boolean status, int position) {
        productsItems.get(position).setFavourite(status);
        notifyDataSetChanged();
    }

    public void refreshProduct() {
        notifyDataSetChanged();

    }

    public interface IShopDetailAdapter {
        void onAddButtonClick(ContinueShoppingObjectModel productItems);

        void onViewDetailsButtonClick(ContinueShoppingObjectModel productItems);

        //void onFavouriteButtonClick(ContinueShoppingObjectModel productItems, int position, Bitmap bitmap);

        void onIncrementButtonClick(long quantity, String price, String totalPrice, String productName,
                                    long cutPrice, byte[] byteImage, ImageView imgProductCopy, Bitmap bitmap);

        void onDecrementButtonClick(long quantity, String price, String totalPrice, String productName,
                                    long cutPrice, byte[] byteImage, ImageView imgProductCopy);


        void onCardClick(String productName, String price, long cutPrice, long quantity, List<String> imgList, Bitmap bitmapImg);


        void removeItemFromCart(ContinueShoppingObjectModel ContinueShoppingObjectModel);

        void getIsFavourites(String productName, int position);
    }


    public void filter(String charText) {
        try {

            charText = charText.toLowerCase(Locale.getDefault());
            productsItems.clear();
            if (charText.length() == 0) {

                productsItems.addAll(productsItemsSearch);
            } else if (charText.length() > 0) {
                for (ContinueShoppingObjectModel wp : productsItemsSearch) {
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

