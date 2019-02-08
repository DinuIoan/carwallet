package com.bestapps.carwallet.splash;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.Notifications;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.data.StaticData;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.firsttime.FirstTimeActivity;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.CarType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static com.bestapps.carwallet.Notifications.CHANNEL_ID;

public class SplashActivity extends AppCompatActivity {
    private static Resources resources;
    public DatabaseHandler databaseHandler;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        resources = getApplicationContext().getResources();
        databaseHandler = new DatabaseHandler(this);
        Car car = databaseHandler.getActiveCar();
        initializeCarTypes();
        createNotificationChannel();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (car == null) {
                    Intent intent = new Intent(getApplicationContext(), FirstTimeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1500);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, Notifications.CHANNEL_NAME, importance);
            channel.setDescription(Notifications.CHANNEL_DESCRIPTION);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void initializeCarTypes() {
        InputStream inputStream = resources.openRawResource(R.raw.carsinput);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean isManufacturer = false;
            boolean isModel = false;
            CarType carType = null;
            List<String> models = null;
            while ((line = br.readLine()) != null) {
                if (line.contains("MANUFACTURER")) {
                    if (carType != null ) {
                        carType.setModels(models);
                        StaticData.getCarTypes().add(carType);
                    }
                    carType = new CarType();
                    isManufacturer = true;
                    isModel = false;
                } else if (line.contains("MODEL")) {
                    models = new ArrayList<>();
                    isModel = true;
                    isManufacturer = false;
                } else if (isManufacturer) {
                    carType.setManufacturer(line);
                } else if (isModel) {
                    models.add(line);
                }
            }
            StaticData.getCarTypes().add(carType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Other car type
        CarType carType = new CarType();
        carType.setManufacturer("Other...");
        List<String> otherList = new ArrayList<>();
        otherList.add("Other...");
        carType.setModels(otherList);
        StaticData.getCarTypes().add(carType);
    }
}
