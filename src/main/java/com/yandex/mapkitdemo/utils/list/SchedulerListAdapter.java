package com.yandex.mapkitdemo.utils.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yandex.mapkitdemo.R;
import com.yandex.mapkitdemo.utils.model.Scheduler;
import com.yandex.mapkitdemo.utils.model.ViewTransportData;
import org.w3c.dom.Text;

import java.util.List;

public class SchedulerListAdapter extends ArrayAdapter<ViewTransportData> {

    private final Activity context;
    private final LayoutInflater inflater;

    public SchedulerListAdapter(@NonNull Activity context, @NonNull List<ViewTransportData> objects) {
        super(context, R.layout.list_item, objects);

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewTransportData viewTransportData = getItem(position);
        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.imageTR);
        TextView transportName = view.findViewById(R.id.transportName);
        TextView location = view.findViewById(R.id.location);

        imageView.setImageResource(viewTransportData.getImgId());
        transportName.setText(String.valueOf(viewTransportData.getNumber()));
        location.setText(viewTransportData.getPath());

        return view;
    }
}
