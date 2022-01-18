package com.yandex.mapkitdemo.ui.scheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.yandex.mapkitdemo.R;
import com.yandex.mapkitdemo.utils.list.SchedulerListAdapter;
import com.yandex.mapkitdemo.utils.model.Scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchedulerList extends Fragment {

    private View root;
    private ListView listView;

    List<Integer> imageView= Arrays.asList(
            R.drawable.bus,R.drawable.trolleybus,R.drawable.trolleybus,
            R.drawable.trolleybus,R.drawable.bus,R.drawable.bus,
            R.drawable.bus,R.drawable.bus,R.drawable.bus);

    List<String> transportName = Arrays.asList("25", "52", "33", "140", "26", "72", "30c", "138", "25");
    List<String> location = Arrays.asList(
            "мкр-н Сухарево-3", "мкр-н Сухарево-3", "мкр-н Сухарево-3", "мкр-н Сухарево-3", "мкр-н Сухарево-3",
            "мкр-н Сухарево-3", "мкр-н Сухарево-3", "мкр-н Сухарево-3", "мкр-н Сухарево-3");

    List<String> time = Arrays.asList(
            "8:45", "9:00", "7:34", "11:32", "6:30",
            "11:00", "7:34", "12:32", "7:30");

    List<Scheduler> schedulerArrayList = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.scheduler_fragment, container, false);
        listView = root.findViewById(R.id.listview);
        List<Scheduler> schedulers = new ArrayList<>();
        for (int i = 0; i< imageView.size(); i++) {
            schedulerArrayList.add(new Scheduler(imageView.get(i), transportName.get(i), location.get(i), time.get(i)));
        }

        SchedulerListAdapter schedulerListAdapter = new SchedulerListAdapter(this.getActivity(),schedulerArrayList);
        listView.setAdapter( schedulerListAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
