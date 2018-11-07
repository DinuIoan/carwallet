package com.bestapps.carwallet.cars;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bestapps.carwallet.R;

public class AddCarFragment extends Fragment {
    private EditText manufacturerEdt;
    private EditText modelEdt;
    private Spinner shapeEdt;
    private EditText yearEdt;
    private EditText mileageEdt;
    private EditText vinEdt;
    private EditText licenseNoEdt;
    private EditText engineEdt;
    private EditText powerEdt;
    private EditText fuelTypeEdt;
    private Button addCar;
    private TextInputLayout layoutManufacturer;
    private TextInputLayout layoutModel;
    private TextInputLayout layoutShape;
    private TextInputLayout layoutYear;
    private TextInputLayout layoutMileage;
    private TextInputLayout layoutVin;
    private TextInputLayout layoutLicenseNo;
    private TextInputLayout layoutEngine;
    private TextInputLayout layoutPower;
    private TextInputLayout layoutFuelType;

    private String vin;
    private String manufacturer;
    private String model;
    private int year;
    private int mileage;
    private String engine;
    private int power;
    private String licenseNo;
    private String fuelType;
    private String shape;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_car, container, false);
        initializeViews(view);
        setClickListeners();

        return view;
    }

    private void setClickListeners() {
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    
                } else {
                    
                }
            }
        });
    }

    private boolean validate() {
        boolean isValid = true;
        String manufacturer = manufacturerEdt.getText().toString();
        vin = vinEdt.getText().toString();
        engine = engineEdt.getText().toString();
        fuelType = fuelTypeEdt.getText().toString();

        if (manufacturer.isEmpty()) {
            isValid = setError(layoutManufacturer);
        } else {
            layoutManufacturer.setErrorEnabled(false);
        }
        if (modelEdt.getText().toString().isEmpty()) {
            isValid = setError(layoutModel);
        } else {
            layoutModel.setErrorEnabled(false);
            model = modelEdt.getText().toString();
        }
        if (licenseNoEdt.getText().toString().isEmpty()) {
            isValid = setError(layoutLicenseNo);
        } else {
            layoutLicenseNo.setErrorEnabled(false);
            licenseNo = licenseNoEdt.getText().toString();
        }
        if (shapeEdt.isSelected()) {
            layoutShape.setErrorEnabled(false);
            shape = shapeEdt.getSelectedItem().toString();
        } else {
            isValid = setError(layoutShape);
        }
        if (!powerEdt.getText().toString().isEmpty()) {
            power = Integer.parseInt(powerEdt.getText().toString());
        }
        if (yearEdt.getText().toString().isEmpty()) {
            isValid = setError(layoutYear);
        } else {
            layoutYear.setErrorEnabled(false);
            year = Integer.parseInt(yearEdt.getText().toString());
        }
        if (mileageEdt.getText().toString().isEmpty()) {
            isValid = setError(layoutMileage);
        } else {
            layoutMileage.setErrorEnabled(false);
            mileage = Integer.parseInt(mileageEdt.getText().toString());
        }
        return isValid;
    }

    private void initializeViews(View view) {
        manufacturerEdt = view.findViewById(R.id.input_manufacturer);
        modelEdt = view.findViewById(R.id.input_model);
        shapeEdt = view.findViewById(R.id.input_shape);
        yearEdt = view.findViewById(R.id.input_year);
        mileageEdt = view.findViewById(R.id.input_mileage);
        vinEdt = view.findViewById(R.id.input_vin);
        licenseNoEdt = view.findViewById(R.id.input_license);
        engineEdt = view.findViewById(R.id.input_engine);
        powerEdt = view.findViewById(R.id.input_power);
        fuelTypeEdt = view.findViewById(R.id.input_fuel_type);
        addCar = view.findViewById(R.id.btn_add_car);

        layoutEngine = view.findViewById(R.id.input_layout_engine);
        layoutPower = view.findViewById(R.id.input_layout_power);
        layoutFuelType = view.findViewById(R.id.input_layout_fuel_type);
        layoutLicenseNo = view.findViewById(R.id.input_layout_license);
        layoutManufacturer = view.findViewById(R.id.input_layout_manufacturer);
        layoutModel = view.findViewById(R.id.input_layout_model);
        layoutMileage = view.findViewById(R.id.input_layout_mileage);
        layoutShape = view.findViewById(R.id.input_layout_shape);
        layoutVin = view.findViewById(R.id.input_layout_vin);
        layoutYear = view.findViewById(R.id.input_layout_year);
    }

    private boolean setError(TextInputLayout textInputLayout) {
        textInputLayout.setErrorEnabled(true);
        return false;
    }
}
