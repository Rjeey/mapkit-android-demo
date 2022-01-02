package com.yandex.mapkitdemo;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import androidx.appcompat.app.AppCompatActivity;
import com.yandex.mapkit.MapKitFactory;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey("a305ff24-d0df-4871-9a52-0ae434368133");
        MapKitFactory.initialize(this);
        super.onCreate(savedInstanceState);



    }
}
