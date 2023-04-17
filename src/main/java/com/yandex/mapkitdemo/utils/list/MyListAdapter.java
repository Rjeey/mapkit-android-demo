package com.yandex.mapkitdemo.utils.list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.yandex.mapkitdemo.R;
import com.yandex.mapkitdemo.utils.model.ViewTransportData;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<ViewTransportData> {

    private final Activity context;
    private final LayoutInflater inflater;

    public MyListAdapter(Activity context, List<ViewTransportData> list) {
        super(context, R.layout.my_custom_list, list);

        this.context=context;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewTransportData viewTransportData = getItem(position);
        View mapTransportView = view;
        if( view == null) {
            mapTransportView = inflater.inflate(R.layout.my_custom_list, parent, false);
        }

        TextView numberTransp =  mapTransportView.findViewById(R.id.number_transp);
        ImageView imageView = mapTransportView.findViewById(R.id.icon_transp);
        TextView subtitleText = mapTransportView.findViewById(R.id.subtitle);
        TextView textTiem = mapTransportView.findViewById(R.id.text_time);

       numberTransp.setText(String.valueOf(viewTransportData.getNumber()));
       imageView.setImageResource(viewTransportData.getImgId());
       subtitleText.setText(viewTransportData.getPath());
       textTiem.setText("5 минут");

        return mapTransportView;

    };
}
