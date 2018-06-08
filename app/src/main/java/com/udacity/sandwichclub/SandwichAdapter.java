package com.udacity.sandwichclub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.List;

public class SandwichAdapter extends ArrayAdapter<String> {
    public SandwichAdapter(@NonNull Context context, String[] sandwiches) {
        super(context, 0, sandwiches);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sandwichName = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.sandwich_name_tv);
        tvName.setText(sandwichName);

        return convertView;
    }
}
