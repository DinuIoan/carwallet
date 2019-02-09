package com.bestapps.carwallet.cars;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.Maintenance;
import com.bestapps.carwallet.model.ParametersSettings;
import com.bestapps.carwallet.model.ServiceEntry;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SelectCurrencyFragment extends Fragment {
    private Button saveButton;
    private Button cancelButton;
    private Spinner currencySpinner;
    private Spinner distanceSpinner;
    private Spinner volumeSpinner;
    private FragmentManager fragmentManager;
    private DatabaseHandler databaseHandler;
    private ParametersSettings parametersSettings;
    private String oldDistanceMeasurementUnit;
    private DistanceConvertor distanceConvertor = new DistanceConvertor();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_currency, container, false);
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        saveButton = view.findViewById(R.id.save_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        currencySpinner = view.findViewById(R.id.input_currency);
        distanceSpinner = view.findViewById(R.id.input_distance);
        volumeSpinner = view.findViewById(R.id.input_volume);
        parametersSettings = databaseHandler.findSettings();
        oldDistanceMeasurementUnit = parametersSettings.getDistance();

        handleOnBackPressed(view);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    databaseHandler.updateSettings(
                            currencySpinner.getSelectedItem().toString(),
                            distanceSpinner.getSelectedItem().toString(),
                            volumeSpinner.getSelectedItem().toString());
                    convertDistanceMeasurementUnit(distanceSpinner.getSelectedItem().toString());
                    changeFragment(new CarsFragment());
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new CarsFragment());
            }
        });

        setCurrencySpinnerAdapter();
        setDistanceSpinnerAdapter();
        setVolumeSpinnerAdapter();
        setSettings();
        return view;
    }

    private void convertDistanceMeasurementUnit(String actualDistance) {
        List<Car> carList = databaseHandler.findAllCars();
        List<ServiceEntry> serviceEntries = databaseHandler.findAllServiceEntries();
        List<Maintenance> maintenanceList = databaseHandler.findAllMaintenance();
        if (!oldDistanceMeasurementUnit.equals(actualDistance)) {
            for (Car car : carList) {
                distanceConvertor.convert(oldDistanceMeasurementUnit, actualDistance, car);
                databaseHandler.updateCar(car);
            }
            for (ServiceEntry serviceEntry : serviceEntries) {
                distanceConvertor.convert(oldDistanceMeasurementUnit, actualDistance, serviceEntry);
                databaseHandler.updateServiceEntry(serviceEntry);

            }
            for (Maintenance maintenance : maintenanceList) {
                distanceConvertor.convert(oldDistanceMeasurementUnit, actualDistance, maintenance);
                databaseHandler.updateMaintenance(maintenance);
            }
        }
    }

    private void setSettings() {
        String[] currencyArray = getResources().getStringArray(R.array.currecies);
        String[] distanceArray = getResources().getStringArray(R.array.distance_measurement_units);
        String[] volumeArray = getResources().getStringArray(R.array.volume_measurement_units);
        int i;
        for(i = 0; i < currencyArray.length; i++) {
            if (currencyArray[i].equals(parametersSettings.getCurrency())) {
                currencySpinner.setSelection(i);
                break;
            }
        }
        for(i = 0; i < distanceArray.length; i++) {
            if (distanceArray[i].equals(parametersSettings.getDistance())) {
                distanceSpinner.setSelection(i);
                break;
            }
        }
        for(i = 0; i < volumeArray.length; i++) {
            if (volumeArray[i].equals(parametersSettings.getVolume())) {
                volumeSpinner.setSelection(i);
                break;
            }
        }
    }

    private boolean validate() {
        boolean isValid = true;
        if (currencySpinner.getSelectedItemPosition() == 0||
                distanceSpinner.getSelectedItemPosition() == 0 ||
                volumeSpinner.getSelectedItemPosition() == 0) {
            isValid = false;
        }
        return isValid;
    }

    private void setCurrencySpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.currecies, R.layout.simple_item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
    }

    private void setDistanceSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.distance_measurement_units, R.layout.simple_item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(adapter);
    }

    private void setVolumeSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.volume_measurement_units, R.layout.simple_item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        volumeSpinner.setAdapter(adapter);
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }

    private void handleOnBackPressed(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new CarsFragment());
                    return true;
                }
                return false;
            }
        });
    }
}
