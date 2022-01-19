package com.yandex.mapkitdemo.ui.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import com.yandex.mapkitdemo.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationsFragment extends Fragment {

    private View root;
    private TextView textView;
    final String CHANNEL_ID = "rjeey";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        textView = root.findViewById(R.id.text_notifications);
        textView.setText("50 Автобус будет через 5 минут");

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
        builder.setContentText("50 Автобус будет через 5 минут");
        builder.setSmallIcon(R.drawable.ic_map);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(root.getContext());
        managerCompat.notify(1, builder.build());
    }
}
