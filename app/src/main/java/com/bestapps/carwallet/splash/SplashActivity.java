package com.bestapps.carwallet.splash;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.data.StaticData;
import com.bestapps.carwallet.model.CarType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static Resources resources;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        resources = getApplicationContext().getResources();
        initializeCarTypes();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
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
