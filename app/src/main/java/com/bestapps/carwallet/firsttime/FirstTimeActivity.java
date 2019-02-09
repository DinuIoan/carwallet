package com.bestapps.carwallet.firsttime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.ParametersSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class FirstTimeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Button startNowButton;
    private Button nextButton;
    private TextView addCarTextViewMessage;
    private TextView selectCurrencyViewMessage;
    private TextView currencyTextView;
    private TextView volumeMeasurementTextView;
    private TextView distanceMeasurementTextView;
    private TextView changeHint;
    private DatabaseHandler databaseHandler;
    private Spinner currencySpinner;
    private Spinner distanceSpinner;
    private Spinner volumeSpinner;
    private ParametersSettings parametersSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        databaseHandler = new DatabaseHandler(this);
        fragmentManager = getSupportFragmentManager();
        startNowButton = findViewById(R.id.start_now_button);
        nextButton = findViewById(R.id.next_button);
        addCarTextViewMessage = findViewById(R.id.add_car_first_message);
        selectCurrencyViewMessage = findViewById(R.id.welcome_message);
        currencySpinner = findViewById(R.id.input_currency);
        distanceSpinner = findViewById(R.id.input_distance_measurement);
        volumeSpinner = findViewById(R.id.input_volume_measurement);
        currencyTextView = findViewById(R.id.currency_message);
        volumeMeasurementTextView = findViewById(R.id.volume_measurement);
        distanceMeasurementTextView = findViewById(R.id.distance_measurement);
        changeHint = findViewById(R.id.change_hint);

        setCurrencySpinnerAdapter();
        setDistanceSpinnerAdapter();
        setVolumeSpinnerAdapter();
        parametersSettings = databaseHandler.findSettings();
        nextButton.setVisibility(View.GONE);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParametersSettings parametersSettings = new ParametersSettings();
                parametersSettings.setId(0);
                parametersSettings.setCurrency(currencySpinner.getSelectedItem().toString());
                parametersSettings.setDistance(distanceSpinner.getSelectedItem().toString());
                parametersSettings.setVolume(volumeSpinner.getSelectedItem().toString());
                databaseHandler.addSettings(parametersSettings);
                hideCurrency();
            }
        });
        startNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("firstTime", true);
                startActivity(intent);
                finish();
            }
        });

        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (    i != 0 &&
                        distanceSpinner.getSelectedItemPosition() != 0 &&
                        volumeSpinner.getSelectedItemPosition() != 0) {

                            nextButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (    i != 0 &&
                        currencySpinner.getSelectedItemPosition() != 0 &&
                        volumeSpinner.getSelectedItemPosition() != 0) {

                    nextButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        volumeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (    i != 0 &&
                        currencySpinner.getSelectedItemPosition() != 0  &&
                        distanceSpinner.getSelectedItemPosition() != 0) {

                    nextButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (parametersSettings == null) {
            hideAddCarMessage();
        } else {
            hideCurrency();
        }
    }

    private void hideAddCarMessage() {
        startNowButton.setVisibility(View.GONE);
        addCarTextViewMessage.setVisibility(View.GONE);
        selectCurrencyViewMessage.setVisibility(View.VISIBLE);
        currencySpinner.setVisibility(View.VISIBLE);
        distanceSpinner.setVisibility(View.VISIBLE);
        volumeSpinner.setVisibility(View.VISIBLE);
        currencyTextView.setVisibility(View.VISIBLE);
        distanceMeasurementTextView.setVisibility(View.VISIBLE);
        volumeMeasurementTextView.setVisibility(View.VISIBLE);
        changeHint.setVisibility(View.VISIBLE);
    }

    private void hideCurrency() {
        startNowButton.setVisibility(View.VISIBLE);
        addCarTextViewMessage.setVisibility(View.VISIBLE);
        selectCurrencyViewMessage.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        currencySpinner.setVisibility(View.GONE);
        distanceSpinner.setVisibility(View.GONE);
        volumeSpinner.setVisibility(View.GONE);
        currencyTextView.setVisibility(View.GONE);
        distanceMeasurementTextView.setVisibility(View.GONE);
        volumeMeasurementTextView.setVisibility(View.GONE);
        changeHint.setVisibility(View.GONE);
    }

    private void setCurrencySpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currecies, R.layout.simple_item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
        currencySpinner.setSelection(0);
    }

    private void setDistanceSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.distance_measurement_units, R.layout.simple_item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(adapter);
        distanceSpinner.setSelection(0);
    }

    private void setVolumeSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.volume_measurement_units, R.layout.simple_item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        volumeSpinner.setAdapter(adapter);
        volumeSpinner.setSelection(0);
    }
}
