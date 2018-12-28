package com.bestapps.carwallet;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.bestapps.carwallet.cars.CarsFragment;
import com.bestapps.carwallet.maintenance.MaintenanceFragment;
import com.bestapps.carwallet.service.ServiceFragment;
import com.bestapps.carwallet.statistics.StatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    public static int backCount = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected( MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_service:
                    changeFragment(new ServiceFragment());
                    return true;
                case R.id.navigation_alerts:
                    changeFragment(new MaintenanceFragment());
                    return true;
                case R.id.navigation_cars:
                    changeFragment(new CarsFragment());
                    return true;
                case R.id.navigation_charts:
                    changeFragment(new StatisticsFragment());
                    return true;
                case R.id.navigation_trip:
                    changeFragment(new CarsFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_service);
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        backCount++;

        if (backCount == 2) {
            finish();
        }
    }

}
