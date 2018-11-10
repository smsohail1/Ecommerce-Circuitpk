package com.xekera.Ecommerce.ui.dashboard.dashboard_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;

public class HistoryFragment extends Fragment {

    public HistoryFragment () {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        //   requestPermissionsFromUser();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            setTitle();
        } catch ( Exception ex) {
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
        ((BaseActivity) getActivity()).setTitle(getString(R.string.history_dashboard));
    }



    private void initializeViews(View v) {
        ButterKnife.bind(this, v);


    }
}
