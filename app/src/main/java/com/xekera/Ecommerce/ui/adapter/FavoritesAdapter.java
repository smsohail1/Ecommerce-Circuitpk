package com.xekera.Ecommerce.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.Favourites;
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


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Favourites favourites = productsItems.get(position);
        byte[] bytes;
        if (holder instanceof productDetailsDataListViewHolder) {
            productDetailsDataListViewHolder productDetailsDataListViewHolder = (productDetailsDataListViewHolder) holder;

            productDetailsDataListViewHolder.productNameLabelTextView.setText(favourites.getItemName());
            productDetailsDataListViewHolder.priceTextView.setText(favourites.getItemIndividualPrice());
            productDetailsDataListViewHolder.discountPriceTextView.setText(favourites.getItemCutPrice());
            productDetailsDataListViewHolder.availabilitStockTextView.setText(favourites.getItemStockStatus());

            try {

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;


                bytes = favourites.getItemImage();
                // Create a bitmap from the byte array
                Bitmap compressedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

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





        public productDetailsDataListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.btnAddToCart).setOnClickListener(this);
            itemView.findViewById(R.id.imgRemove).setOnClickListener(this);

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
        notifyDataSetChanged();
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
    }
}


