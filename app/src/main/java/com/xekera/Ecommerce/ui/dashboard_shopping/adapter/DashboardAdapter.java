package com.xekera.Ecommerce.ui.dashboard_shopping.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.dashboard_shopping.ShopFragmentPresenter;
import com.xekera.Ecommerce.ui.dashboard_shopping.model.DashboardItem;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<DashboardItem> items;
    private IDashboardAdapter iDashboardAdapter;

    public DashboardAdapter(List<DashboardItem> items, IDashboardAdapter iDashboardAdapter){
        this.items = items;
        this.iDashboardAdapter = iDashboardAdapter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item,parent,false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(v);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DashboardItem homeItem = items.get(position);
        if(holder instanceof HomeViewHolder){
            HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
            homeViewHolder.imgHomeItem.setImageResource(homeItem.getImgResId());
            homeViewHolder.txtHomeItem.setText(homeItem.getNameResId());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.imgHomeItem)
        protected ImageView imgHomeItem;
        @BindView(R.id.txtHomeItem)
        protected TextView txtHomeItem;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iDashboardAdapter.onHomeItemClick(items.get(getLayoutPosition()));
        }
    }

    public interface IDashboardAdapter{
        void onHomeItemClick(DashboardItem homeItem);
    }
}














