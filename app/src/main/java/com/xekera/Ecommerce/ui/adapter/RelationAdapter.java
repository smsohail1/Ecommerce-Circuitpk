package com.xekera.Ecommerce.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.xekera.Ecommerce.R;

import java.util.List;

/**
 * Created by shahrukh.malik on 17, July, 2018
 */
public class RelationAdapter extends BaseAdapter {
    private List<String> names;

    public RelationAdapter(List<String> names) {
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String name = names.get(i);
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_item_relation, null);
        TextView txtItem = view.findViewById(R.id.txtItem);
        txtItem.setText(name);
        return view;
    }
}
