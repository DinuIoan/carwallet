package com.bestapps.carwallet.trip;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Currency;
import com.bestapps.carwallet.model.TripData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CalculateTripFragment extends Fragment {
    private TextInputEditText fromEdt;
    private TextInputEditText toEdt;
    private TextInputEditText avarageConsumptionEdt;
    private TextInputEditText fuelPriceEdt;
    private TextInputEditText distanceEdt;
    private TextInputEditText totalPriceEdt;
    private TextInputEditText totalLitersEdt;
    private Button calculateButton;
    private Button saveButton;

    private TextInputLayout fromLayout;
    private TextInputLayout toLayout;
    private TextInputLayout avarageConsumptionLayout;
    private TextInputLayout fuelPriceLayout;
    private TextInputLayout distanceLayout;
    private TextInputLayout totalPriceLayout;

    private String from;
    private String to;
    private Double avarageConsumption;
    private Double fuelPrice;
    private Double distance;
    private Double totalPrice;
    private Double totalLiters;

    private DatabaseHandler databaseHandler;
    private FragmentManager fragmentManager;
    private Currency currency;

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
        handleOnBackPressed(view);
        handleClickListeners();
        currency = databaseHandler.findCurrency();

        return view;
    }

    private void handleClickListeners() {
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate("calculate")) {
                    calculateTotalPrice();
                    calculateTotalLiters();
                    totalPriceEdt.setText(String.format(Locale.US, "%.2f", totalPrice) + currency.getCurrency());
                    totalLitersEdt.setText(String.format(Locale.US, "%.2f", totalLiters) + currency.getCurrency());
                }
                hideKeyboard();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate("save")) {
                    TripData tripData = new TripData();
                    tripData.setAvarageConsumption(avarageConsumption);
                    tripData.setDistance(distance);
                    tripData.setFromLocation(from);
                    tripData.setToLocation(to);
                    tripData.setTotalPrice(totalPrice);
                    tripData.setFuelPrice(fuelPrice);
                    tripData.setTotalLiters(totalLiters);
                    databaseHandler.addTrip(tripData);
                    changeFragment(new TripFragment());
                }
                hideKeyboard();
            }
        });
    }

    private void calculateTotalLiters() {
        totalLiters = (distance / 100) * avarageConsumption;
        totalLiters = Double.parseDouble(String.format(Locale.US, "%.2f", totalLiters));
    }

    private void calculateTotalPrice() {
        totalPrice = (distance / 100) * avarageConsumption * fuelPrice;
        totalPrice = Double.parseDouble(String.format(Locale.US, "%.2f", totalPrice));
    }

    private boolean validate(String type) {
        boolean isValid = true;

        if(type.equals("save")) {
            if (toEdt.getText().toString().isEmpty()) {
                isValid = false;
                toLayout.setError(" ");
            } else {
                toLayout.setErrorEnabled(false);
                to = toEdt.getText().toString();
            }

            if (fromEdt.getText().toString().isEmpty()) {
                isValid = false;
                fromLayout.setError(" ");
            } else {
                fromLayout.setErrorEnabled(false);
                from = fromEdt.getText().toString();
            }

            if (totalPriceEdt.getText().toString().isEmpty()) {
                isValid = false;
                totalPriceLayout.setError(" ");
            } else {
                totalPriceLayout.setErrorEnabled(false);
            }
        }

        if (fuelPriceEdt.getText().toString().isEmpty()) {
            isValid = false;
            fuelPriceLayout.setError(" ");
        } else {
            fuelPriceLayout.setErrorEnabled(false);
            fuelPrice = Double.parseDouble(fuelPriceEdt.getText().toString());
        }

        if (distanceEdt.getText().toString().isEmpty()) {
            isValid = false;
            distanceLayout.setError(" ");
        } else {
            distanceLayout.setErrorEnabled(false);
            distance = Double.parseDouble(distanceEdt.getText().toString());
        }

        if (avarageConsumptionEdt.getText().toString().isEmpty()) {
            isValid = false;
            avarageConsumptionLayout.setError(" ");
        } else {
            avarageConsumptionLayout.setErrorEnabled(false);
            avarageConsumption = Double.parseDouble(avarageConsumptionEdt.getText().toString());
        }

        return isValid;
    }

    private void initiateViews(View view) {
        fromEdt = view.findViewById(R.id.input_from);
        toEdt = view.findViewById(R.id.input_to);
        avarageConsumptionEdt = view.findViewById(R.id.input_avarage_consumption);
        distanceEdt = view.findViewById(R.id.input_distance);
        fuelPriceEdt = view.findViewById(R.id.input_fuel_price);
        totalPriceEdt = view.findViewById(R.id.input_total_price);
        totalLitersEdt = view.findViewById(R.id.input_total_liters);
        calculateButton = view.findViewById(R.id.btn_calculate);
        saveButton = view.findViewById(R.id.btn_save_trip);

        toLayout = view.findViewById(R.id.input_layout_to);
        fromLayout = view.findViewById(R.id.input_layout_from);
        avarageConsumptionLayout = view.findViewById(R.id.input_layout_avarage_consumption);
        distanceLayout = view.findViewById(R.id.input_layout_distance);
        fuelPriceLayout = view.findViewById(R.id.input_layout_fuel_price);
        totalPriceLayout = view.findViewById(R.id.input_layout_total_price);
    }

    private void handleOnBackPressed(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new TripFragment());
                    return true;
                }
                return false;
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
