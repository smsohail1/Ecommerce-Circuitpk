package com.xekera.Ecommerce.ui.dashboard_shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.dashboard_shopping.adapter.DashboardAdapter;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements ShopFragmentMVP.View {
    @BindView(R.id.recyclerViewHome)
    protected RecyclerView recyclerViewHome;


    @Inject
    protected ShopFragmentMVP.Presenter presenter;
    @Inject
    protected Utils utils;
    @Inject
    protected ToastUtil toastUtil;
    @Inject
    protected SessionManager sessionManager;


    private ProgressCustomDialogController progressDialogControllerPleaseWait;


    public ShopFragment() {
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
        presenter.setView(this);

        try {
            setTitle();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTitle() {
        ((BaseActivity) getActivity()).setTitle(getString(R.string.shop_dashboard));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop, container, false);

        initializeViews(v);


        return v;
    }

    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);

        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

        recyclerViewHome.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewHome.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));

        presenter.setDashboardItems();

    }


    @Override
    public void setHomeRecyclerViewAdapter(DashboardAdapter homeAdapter) {
        recyclerViewHome.setAdapter(homeAdapter);
    }


    @Override
    public void showProgressDialogPleaseWait() {
        progressDialogControllerPleaseWait.showDialog();
    }

    @Override
    public void hideProgressDialogPleaseWait() {
        progressDialogControllerPleaseWait.hideDialog();
    }

    @Override
    public void showToastShortTime(String message) {
        toastUtil.showToastShortTime(message);
    }

    @Override
    public void showToastLongTime(String message) {
        toastUtil.showToastLongTime(message);
    }


}
