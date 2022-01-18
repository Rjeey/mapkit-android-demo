package com.yandex.mapkitdemo.ui.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.yandex.mapkitdemo.R;

public class NotificationsFragment extends Fragment {

    private View root;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        textView = root.findViewById(R.id.text_notifications);
        textView.setText("50 Автобус будет через 5 минут");

        return  root;

    }
}
