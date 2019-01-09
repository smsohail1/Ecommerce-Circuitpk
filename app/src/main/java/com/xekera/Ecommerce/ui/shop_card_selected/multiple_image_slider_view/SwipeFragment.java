package com.xekera.Ecommerce.ui.shop_card_selected.multiple_image_slider_view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wang.avi.AVLoadingIndicatorView;
import com.xekera.Ecommerce.R;

import java.util.ArrayList;
import java.util.List;

public class SwipeFragment extends Fragment {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.avloadingIndicatorView)
    AVLoadingIndicatorView avloadingIndicatorView;

    List<String> imgList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View swipeView = inflater.inflate(R.layout.swipe_fragment, container, false);
        initializeViews(swipeView);

        return swipeView;
    }

    private void initializeViews(View v) {
        ButterKnife.bind(this, v);

        Bundle bundle = getArguments();
        imgList = bundle.getStringArrayList("img_viewpager");
        int position = bundle.getInt("position");
        // String imageFileName = IMAGE_NAME[position];
        //int imgResId = getResources().getIdentifier(imageFileName, "drawable", "com.javapapers.android.swipeimageslider");
        //  imageView.setImageResource(imgList);
        try {
            Glide.with(getActivity()).load(imgList.get(position))
                    .asBitmap()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            avloadingIndicatorView.setVisibility(View.GONE);
                            imageView.setImageBitmap(resource);
                            imageView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            avloadingIndicatorView.setVisibility(View.GONE);
                            imageView.setVisibility(View.GONE);

                        }
                    });


        } catch (Exception e) {

        }
    }

    public static SwipeFragment newInstance(int position, List<String> imgList) {
        SwipeFragment swipeFragment = new SwipeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putStringArrayList("img_viewpager", (ArrayList<String>) imgList);
        swipeFragment.setArguments(bundle);
        return swipeFragment;
    }
}
