package com.xekera.Ecommerce.ui.continue_shopping.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.xekera.Ecommerce.data.rest.response.Category;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Category> items;
    private IDashboardAdapter iDashboardAdapter;
    Context context;

    public DashboardAdapter(List<Category> items, Context context, IDashboardAdapter iDashboardAdapter) {
        this.items = items;
        this.iDashboardAdapter = iDashboardAdapter;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item, parent, false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(v);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Category homeItem = items.get(position);
        if (holder instanceof HomeViewHolder) {
            final HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
            //homeViewHolder.imgHomeItem.setImageResource(homeItem.getImgResId());
            homeViewHolder.txtHomeItem.setText(homeItem.getName());

            Glide.with(context)
                    .load(homeItem.getImage())
                    .asBitmap()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    // .override(130, 50)
                    .centerCrop()

                    // .into(homeViewHolder.imgHomeItem);
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            homeViewHolder.avLoadingIndicatorView.setVisibility(View.GONE);
                            homeViewHolder.imgHomeItem.setImageBitmap(resource);
                            homeViewHolder.imgHomeItem.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            homeViewHolder.avLoadingIndicatorView.setVisibility(View.GONE);
                            homeViewHolder.imgHomeItem.setVisibility(View.VISIBLE);

                        }
                    });

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imgHomeItem)
        protected ImageView imgHomeItem;
        @BindView(R.id.txtHomeItem)
        protected TextView txtHomeItem;
        @BindView(R.id.imageProduct)
        protected LinearLayout imageProduct;
        @BindView(R.id.avloadingIndicatorView)
        protected AVLoadingIndicatorView avLoadingIndicatorView;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iDashboardAdapter.onHomeItemClick(items.get(getLayoutPosition()));
        }
    }

    public interface IDashboardAdapter {
        void onHomeItemClick(Category categories);
    }
}














