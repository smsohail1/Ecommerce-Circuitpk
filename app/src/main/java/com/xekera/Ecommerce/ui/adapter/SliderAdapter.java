package com.xekera.Ecommerce.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.fido.fido2.api.common.RequestOptions;
import com.xekera.Ecommerce.R;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> color;
    private List<String> colorName;
    private List<String> img;

    public SliderAdapter(Context context, List<Integer> color, List<String> colorName, List<String> img) {
        this.context = context;
        this.color = color;
        this.colorName = colorName;
        this.img = img;
    }

    @Override
    public int getCount() {
        return color.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider, null);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);

       // textView.setText(colorName.get(position));
        // linearLayout.setBackgroundColor(color.get(position));
        //  "http://lorempixel.com/400/200/sports/0" + "/"
        ImageView imageView = view.findViewById(R.id.imageView);
        Glide.with(context).load(img.get(position))
                .fitCenter()
                .placeholder(R.drawable.placeholder).
                into(imageView);


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
