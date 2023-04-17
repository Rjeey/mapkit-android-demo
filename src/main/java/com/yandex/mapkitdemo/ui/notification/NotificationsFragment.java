package com.yandex.mapkitdemo.ui.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import com.yandex.mapkitdemo.R;
import com.yandex.mapkitdemo.utils.GlobalStorage;

public class NotificationsFragment extends Fragment {

    private View root;
    private ListView listView;
    final String CHANNEL_ID = "rjeey";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        listView = root.findViewById(R.id.notification_listview);
        ListAdapter notificationListAdapter = new ArrayAdapter<>(root.getContext(),android.R.layout.simple_list_item_1, GlobalStorage.notifications);
        listView.setAdapter(notificationListAdapter);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Rjeey", "Rjeey", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = root.getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        showNotification();


        return  root;

    }
    private void showNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(root.getContext(), "Rjeey");
        builder.setContentTitle("Информация о транспорте");
        if(!GlobalStorage.notifications.isEmpty()) {
            builder.setContentText(GlobalStorage.notifications.get(0));
            builder.setSmallIcon(R.drawable.ic_map);
            builder.setAutoCancel(true);
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(root.getContext());
            managerCompat.notify(1, builder.build());
        }
    }
}
