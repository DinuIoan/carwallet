package com.bestapps.carwallet.trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.TripData;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CalculateTripFragment extends Fragment {
    private EditText fromEdt;
    private EditText toEdt;
    private EditText avarageConsumptionEdt;
    private EditText fuelPriceEdt;
    private EditText distanceEdt;
    private EditText totalPriceEdt;
    private Button calculateButton;
    private Button saveButton;

    private String from;
    private String to;
    private Double avarageConsumption;
    private Double fuelPrice;
    private Double distance;
    private Double totalPrice;

    private DatabaseHandler databaseHandler;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculate_trip, container, false);
        initiateViews(view);
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        handleClickListeners();

        return view;
    }

    private void handleClickListeners() {
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    totalPrice = calculateTotalPrice();
                    totalPriceEdt.setText("" + totalPrice);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TripData tripData = new TripData();
                tripData.setAvarageConsumption(avarageConsumption);
                tripData.setDistance(distance);
                tripData.setFromLocation(from);
                tripData.setToLocation(to);
                tripData.setTotalPrice(totalPrice);
                tripData.setFuelPrice(fuelPrice);
                databaseHandler.addTrip(tripData);
            }
        });
    }

    private double calculateTotalPrice() {
        return (distance / 100) * avarageConsumption * fuelPrice;
    }

    private boolean validate() {
        avarageConsumption = Double.parseDouble(avarageConsumptionEdt.getText().toString());
        distance = Double.parseDouble(distanceEdt.getText().toString());
        fuelPrice = Double.parseDouble(fuelPriceEdt.getText().toString());
        from = fromEdt.getText().toString();
        to = toEdt.getText().toString();

        return true;
    }

    private void initiateViews(View view) {
        fromEdt = view.findViewById(R.id.input_from);
        toEdt = view.findViewById(R.id.input_to);
        avarageConsumptionEdt = view.findViewById(R.id.input_avarage_consumption);
        distanceEdt = view.findViewById(R.id.input_distance);
        fuelPriceEdt = view.findViewById(R.id.input_fuel_price);
        totalPriceEdt = view.findViewById(R.id.input_total_price);
        calculateButton = view.findViewById(R.id.btn_calculate);
        saveButton = view.findViewById(R.id.btn_save_trip);
    }
}
