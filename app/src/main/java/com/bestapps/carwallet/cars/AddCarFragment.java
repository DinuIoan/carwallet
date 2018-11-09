package com.bestapps.carwallet.cars;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.data.StaticData;
import com.bestapps.carwallet.model.CarType;

import java.util.ArrayList;
import java.util.List;

public class AddCarFragment extends Fragment {
    private Spinner manufacturerSpinner;
    private Spinner modelSpinner;
    private Spinner shapeEdt;
    private EditText manufacturerEdt;
    private EditText modelEdt;
    private EditText yearEdt;
    private EditText mileageEdt;
    private EditText vinEdt;
    private EditText licenseNoEdt;
    private EditText engineEdt;
    private EditText powerEdt;
    private EditText fuelTypeEdt;
    private Button addCar;
    private TextInputLayout layoutManufacturer;
    private TextInputLayout edtLayoutManufacturer;
    private TextInputLayout layoutModel;
    private TextInputLayout edtLayoutModel;
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
    private boolean isOther = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_car, container, false);
        initializeViews(view);
        modelSpinner.setEnabled(false);
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

        manufacturerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                manufacturer = adapterView.getItemAtPosition(i).toString();
                modelSpinner.setEnabled(true);
                for (CarType carType: StaticData.getCarTypes()) {
                    if (carType.getManufacturer().equals(manufacturer)) {
                        Object[] stringArray = carType.getModels().toArray();
                        ArrayAdapter<Object> arrayAdapter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_spinner_item, stringArray);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        modelSpinner.setAdapter(arrayAdapter);
                    }
                }
                if (manufacturer.equals("Other...")) {
                    isOther = true;
                    manufacturerSpinner.setVisibility(View.VISIBLE);
                    modelSpinner.setVisibility(View.GONE);
                    edtLayoutManufacturer.setVisibility(View.VISIBLE);
                    edtLayoutModel.setVisibility(View.VISIBLE);
                } else {
                    isOther = false;
                    manufacturerEdt.setText("");
                    modelEdt.setText("");
                    edtLayoutManufacturer.setVisibility(View.GONE);
                    edtLayoutModel.setVisibility(View.GONE);
                    modelSpinner.setVisibility(View.VISIBLE);
                    manufacturerSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(0);
            }
        });

        modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                model = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(0);
            }
        });
    }

    private boolean validate() {
        boolean isValid = true;
        vin = vinEdt.getText().toString();
        engine = engineEdt.getText().toString();
        fuelType = fuelTypeEdt.getText().toString();

        if (manufacturerSpinner.getSelectedItem().toString().equals("Select manufacturer...")) {
            TextView errorText = (TextView)manufacturerSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select manufacturer...");
            isValid = setError(layoutManufacturer);
        } else {
            layoutManufacturer.setErrorEnabled(false);
            manufacturer = manufacturerSpinner.getSelectedItem().toString();
        }

        if (modelSpinner.getSelectedItem().toString().equals("Select model...")) {
            isValid = setError(layoutManufacturer);
            TextView errorText = (TextView)modelSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select model...");
        } else {
            layoutManufacturer.setErrorEnabled(false);
            model = modelSpinner.getSelectedItem().toString();
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
        manufacturerSpinner = view.findViewById(R.id.input_manufacturer);
        setManufacturerSpinnerAdapter(manufacturerSpinner);
        modelSpinner = view.findViewById(R.id.input_model);
        shapeEdt = view.findViewById(R.id.input_shape);
        yearEdt = view.findViewById(R.id.input_year);
        mileageEdt = view.findViewById(R.id.input_mileage);
        vinEdt = view.findViewById(R.id.input_vin);
        licenseNoEdt = view.findViewById(R.id.input_license);
        engineEdt = view.findViewById(R.id.input_engine);
        powerEdt = view.findViewById(R.id.input_power);
        fuelTypeEdt = view.findViewById(R.id.input_fuel_type);
        addCar = view.findViewById(R.id.btn_add_car);

        manufacturerEdt = view.findViewById(R.id.input_edt_manufacturer);
        modelEdt = view.findViewById(R.id.input_edt_model);

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
        edtLayoutManufacturer = view.findViewById(R.id.input_edt_layout_manufacturer);
        edtLayoutModel = view.findViewById(R.id.input_edt_layout_model);
        edtLayoutManufacturer.setVisibility(View.GONE);
        edtLayoutModel.setVisibility(View.GONE);
    }

    private void setManufacturerSpinnerAdapter(Spinner spinner) {
        ArrayAdapter<Object> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, createManufacturersList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }

    private Object[] createManufacturersList() {
        List<String> manufacturers = new ArrayList<>();
        for (CarType carType: StaticData.getCarTypes()) {
            manufacturers.add(carType.getManufacturer());
        }
        return manufacturers.toArray();
    }

    private boolean setError(TextInputLayout textInputLayout) {
        textInputLayout.setErrorEnabled(true);
        return false;
    }
}
