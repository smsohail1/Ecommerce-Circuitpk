package com.xekera.Ecommerce.ui.shop_card_selected.multiple_image_slider_view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.adapter.ImageFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MultipeImageSliderViewFragment extends Fragment {

    @BindView(R.id.pager)
    ViewPager viewPager;

    List<String> imgList;
    ImageFragmentPagerAdapter imageFragmentPagerAdapter;


    public MultipeImageSliderViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.multiple_image_slider_view_fragment, container, false);

        initializeViews(v);


        return v;
    }

    private void initializeViews(View v) {
        ButterKnife.bind(this, v);

        imgList = new ArrayList<>();
        Bundle bundle = getArguments();
        imgList = bundle.getStringArrayList("list_viewpager");
        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getActivity(), imgList);
        viewPager.setAdapter(imageFragmentPagerAdapter);

    }

    public MultipeImageSliderViewFragment newInstance(List<String> imgList) {
        MultipeImageSliderViewFragment multipeImageSliderViewFragment = new MultipeImageSliderViewFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list_viewpager", (ArrayList<String>) imgList);
        multipeImageSliderViewFragment.setArguments(bundle);
        return multipeImageSliderViewFragment;
    }
}
