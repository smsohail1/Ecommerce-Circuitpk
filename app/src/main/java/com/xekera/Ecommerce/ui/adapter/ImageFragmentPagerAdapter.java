package com.xekera.Ecommerce.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.stripe.android.RequestOptions;
import com.wang.avi.AVLoadingIndicatorView;
import com.xekera.Ecommerce.R;

import java.util.List;

public class ImageFragmentPagerAdapter extends PagerAdapter {

    private List<String> imgList;
    private Context context;

    public ImageFragmentPagerAdapter(Context context, List<String> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.swipe_fragment, null);

        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        final AVLoadingIndicatorView avloadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avloadingIndicatorView);


        try {
            Glide.with(context).load(imgList.get(position))
                    .asBitmap()
                    .override(300, 300)
                    .placeholder(R.drawable.placeholder_error)
                    .error(R.drawable.placeholder_error)
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


        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
