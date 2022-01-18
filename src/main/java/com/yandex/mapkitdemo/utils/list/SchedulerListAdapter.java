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
import org.w3c.dom.Text;

import java.util.List;

public class SchedulerListAdapter extends ArrayAdapter<Scheduler> {

    private Activity context;
    private LayoutInflater inflater;

    public SchedulerListAdapter(@NonNull Activity context, @NonNull List<Scheduler> objects) {
        super(context, R.layout.list_item, objects);

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Scheduler scheduler = getItem(position);
        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.imageTR);
        TextView transportName = view.findViewById(R.id.transportName);
        TextView location = view.findViewById(R.id.location);
        TextView time = view.findViewById(R.id.msgtime);

        imageView.setImageResource(scheduler.getType());
        transportName.setText(scheduler.getName());
        location.setText(scheduler.getLocation());
        time.setText(scheduler.getTime());

        return view;
    }
}
