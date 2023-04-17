package com.yandex.mapkitdemo.ui.scheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.yandex.mapkitdemo.R;
import com.yandex.mapkitdemo.utils.GlobalStorage;
import com.yandex.mapkitdemo.utils.list.SchedulerListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SchedulerList extends Fragment {

    private View root;
    private ListView listView;
    private ListView stopsListView;
    private ImageButton stopsClose;
    private AlertDialog stopsDialog;
    private AlertDialog timeArriavedDialog;
    private String itemStopClick;
    private  List<String> time = new ArrayList<>();
    private ArrayAdapter<String> stopsListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.scheduler_fragment, container, false);
        listView = root.findViewById(R.id.listview);


        SchedulerListAdapter schedulerListAdapter = new SchedulerListAdapter(this.getActivity(), GlobalStorage.transportData);
        listView.setAdapter( schedulerListAdapter);
        initDialogStopsList();
        initPathTranspDialog();
        initTimeTranspDialog();

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

    public void initDialogStopsList(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stopsDialog.show();
            }
        });
    }

    private void initPathTranspDialog() {

        List<String> path = GlobalStorage.transportData.stream().filter(i-> i.getNumber() == 19).findFirst().get().getStopsList();
        System.out.println("Лист остановок ");
        path.forEach(System.out::println);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Подробная Информация автобуcа № 19");

        View view = getLayoutInflater().inflate(R.layout.custom_stops_dialog, null);
        builder.setView(view);
        stopsDialog = builder.create();
        stopsListView = view.findViewById(R.id.stops_list);
        ArrayAdapter<String> stopsListAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, path);
        stopsListView.setAdapter(stopsListAdapter);

        stopsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemStopClick = path.get(position);
                System.out.println(itemStopClick);
                GlobalStorage.transportData.stream()
                        .filter(i-> i.getNumber() == 19)
                        .findFirst()
                        .get()
                        .getRoute().get(0).getStopTrans()
                        .stream()
                        .filter(j-> j.getName().equals(itemStopClick))
                        .findFirst().get().getTimeArrival().forEach(i-> time.add(i.toString()));
                stopsListAdapter.addAll(String.valueOf(time));
                stopsListAdapter.notifyDataSetChanged();
                timeArriavedDialog.show();
            }
        });


        stopsClose = view.findViewById(R.id.btn_stops_close);

        stopsClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopsDialog.dismiss();
            }
        });

    }

    private void initTimeTranspDialog() {


        System.out.println(itemStopClick+" Расписание для автобуса № 19 ");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Расписание Транспорта");


        View view = getLayoutInflater().inflate(R.layout.custom_stops_dialog, null);
        builder.setView(view);
        timeArriavedDialog = builder.create();
        stopsListView = view.findViewById(R.id.stops_list);
        stopsListAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, time);
        stopsListView.setAdapter(stopsListAdapter);


        stopsClose = view.findViewById(R.id.btn_stops_close);

        stopsClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeArriavedDialog.dismiss();
            }
        });

    }
}
