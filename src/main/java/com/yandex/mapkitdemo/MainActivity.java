package com.yandex.mapkitdemo;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkitdemo.utils.GlobalStorage;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(!GlobalStorage.mapState) {
            MapKitFactory.setApiKey("a305ff24-d0df-4871-9a52-0ae434368133");
            MapKitFactory.initialize(this);
            GlobalStorage.mapState = true;
        }
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.main_activity);
        BottomNavigationView navView =findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_map, R.id.navigation_schedule, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


}
