package com.xekera.Ecommerce.ui.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.HistoryAdapter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.SnackUtil;
import com.xekera.Ecommerce.util.ToastUtil;
import com.xekera.Ecommerce.util.Utils;

import javax.inject.Inject;

public class HistoryFragment extends Fragment implements HistoryMVP.View {


    @BindView(R.id.recyclerViewAddToCartDetails)
    protected RecyclerView recyclerViewAddToCartDetails;
    @BindView(R.id.linearParent)
    protected LinearLayout linearParent;
    // @BindView(R.id.subTotalValueTextView)
    //protected TextView subTotalValueTextView;
    //  @BindView(R.id.shippingValueTextView)
    //protected TextView shippingValueTextView;
    @BindView(R.id.totalValueTextView)
    protected TextView totalValueTextView;
    @BindView(R.id.txtNoCartItemFound)
    protected TextView txtNoCartItemFound;

    @Inject
    protected HistoryMVP.Presenter presenter;
    @Inject
    protected Utils utils;
    @Inject
    protected ToastUtil toastUtil;
    @Inject
    protected SnackUtil snackUtil;
    @Inject
    protected SessionManager sessionManager;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            //setTitle();
            ((BaseActivity) getActivity()).showBottomNavigation();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        initializeViews(v);

        return v;
    }

    public void setTitle() {
        // ((BaseActivity) getActivity()).setTitle(getString(R.string.history_dashboard));
    }


    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);
        ((BaseActivity) getActivity()).showBottomNavigation();

        recyclerViewAddToCartDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

        presenter.fetchOrderDetails();
    }


    @Override
    public void showToastShortTime(String message) {
        toastUtil.showToastShortTime(message);
    }

    @Override
    public void showToastLongTime(String message) {
        toastUtil.showToastLongTime(message);
    }


    public void showSnackBarShortTime(String message) {
        snackUtil.showSnackBarShortTime(getView(), message);
    }

    @Override
    public void showRecylerViewProductsDetail(HistoryAdapter historyAdapter) {
        recyclerViewAddToCartDetails.setAdapter(historyAdapter);

    }

    @Override
    public void showRecyclerView() {
        linearParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView() {
        linearParent.setVisibility(View.GONE);
    }

    @Override
    public void setCartCounts(long counts) {

        ((BaseActivity) getActivity()).setCartsCounts(counts);

    }

    @Override
    public void setCartCounterTextview(int counts) {
        ((BaseActivity) getActivity()).showTotalCartsCount(counts);

    }

    @Override
    public void setParentFields() {
        //  subTotalValueTextView.setText("0");
        //   shippingValueTextView.setText("0");
        totalValueTextView.setText("0");
    }

    @Override
    public void txtNoCartItemFound() {
        txtNoCartItemFound.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideNoCartItemFound() {
        txtNoCartItemFound.setVisibility(View.GONE);
    }

    @Override
    public void setSubTotal(String setSubToal) {
        if (!utils.isTextNullOrEmpty(setSubToal)) {
//            subTotalValueTextView.setText(setSubToal);
            //  String flatShippingRateStr = shippingValueTextView.getText().toString();
            // long flatShippingRateLong = 0;
            //   flatShippingRateLong = Long.valueOf(setSubToal) + Long.valueOf(flatShippingRateStr);
            totalValueTextView.setText(String.valueOf(setSubToal));

        } else {
            totalValueTextView.setText("0");

            //          subTotalValueTextView.setText("0");
        }


    }

    @Override
    public void showSnackBarLongTime(String message, View view) {
        snackUtil.showSnackBarLongTime(view, message);
    }

    @Override
    public void showSnackBarShortTime(String message, View view) {
        snackUtil.showSnackBarShortTime(view, message);
    }

}
