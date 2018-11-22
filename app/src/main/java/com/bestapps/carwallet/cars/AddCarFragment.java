package com.bestapps.carwallet.cars;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
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
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.CarType;
import com.bestapps.carwallet.service.ServiceFragment;

import java.util.ArrayList;
import java.util.List;

public class AddCarFragment extends Fragment {
    private Spinner manufacturerSpinner;
    private Spinner modelSpinner;
    private Spinner shapeSpinner;
    private Spinner fuelTypeSpinner;
    private EditText manufacturerEdt;
    private EditText modelEdt;
    private EditText yearEdt;
    private EditText mileageEdt;
    private EditText vinEdt;
    private EditText licenseNoEdt;
    private EditText engineEdt;
    private EditText powerEdt;
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
    private DatabaseHandler databaseHandler;
    private FragmentManager fragmentManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_car, container, false);
        initializeViews(view);
        handleOnBackPressed(view);
        modelSpinner.setEnabled(false);
        setClickListeners();
        databaseHandler = new DatabaseHandler(getContext());

        return view;
    }

    private void setClickListeners() {
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    databaseHandler.addCar(buildCar());
                    changeFragment(new CarsFragment());
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

    private Car buildCar() {
        Car car = new Car();
        car.setManufacturer(manufacturer);
        car.setModel(model);
        car.setMileage(mileage);
        car.setLicenseNo(licenseNo);
        car.setFuelType(fuelType);
        car.setVin(vin);
        car.setYear(year);
        car.setEngine(engine);
        car.setShape(shape);
        car.setPower(power);
        return car;
    }

    private boolean validate() {
        boolean isValid = true;
        vin = vinEdt.getText().toString();
        engine = engineEdt.getText().toString();

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
        if (shapeSpinner.getSelectedItem().toString().equals("Select vehicle type...")) {
            isValid = setError(layoutShape);
            TextView errorText = (TextView)shapeSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select vehicle type...");
        } else {
            layoutShape.setErrorEnabled(false);
            shape = shapeSpinner.getSelectedItem().toString();
        }

        if (licenseNoEdt.getText().toString().isEmpty()) {
//            isValid = setError(layoutLicenseNo);
            isValid = false;
            licenseNoEdt.setError("");
        } else {
            layoutLicenseNo.setErrorEnabled(false);
            licenseNo = licenseNoEdt.getText().toString();
        }
        if (!engineEdt.getText().toString().isEmpty()) {
            engine = engineEdt.getText().toString();
        } else {
            isValid = false;
            engineEdt.setError("");
        }

        if (!powerEdt.getText().toString().isEmpty()) {
            power = Integer.parseInt(powerEdt.getText().toString());
        } else {
            isValid = false;
            powerEdt.setError("");
        }
        if (yearEdt.getText().toString().isEmpty()) {
//            isValid = setError(layoutYear);
            isValid = false;
            yearEdt.setError("");
        } else {
            layoutYear.setErrorEnabled(false);
            year = Integer.parseInt(yearEdt.getText().toString());
        }
        if (mileageEdt.getText().toString().isEmpty()) {
//            isValid = setError(layoutMileage);
            isValid = false;
            mileageEdt.setError("");
        } else {
            layoutMileage.setErrorEnabled(false);
            mileage = Integer.parseInt(mileageEdt.getText().toString());
        }

        if (fuelTypeSpinner.getSelectedItem().toString().equals("Select fuel type...")) {
            isValid = setError(layoutFuelType);
            TextView errorText = (TextView)fuelTypeSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select fuel type...");
        } else {
            layoutFuelType.setErrorEnabled(false);
            fuelType = fuelTypeSpinner.getSelectedItem().toString();
        }
        return isValid;
    }

    private void initializeViews(View view) {
        manufacturerSpinner = view.findViewById(R.id.input_manufacturer);
        modelSpinner = view.findViewById(R.id.input_model);
        shapeSpinner = view.findViewById(R.id.input_shape);
        yearEdt = view.findViewById(R.id.input_year);
        mileageEdt = view.findViewById(R.id.input_mileage);
        vinEdt = view.findViewById(R.id.input_vin);
        licenseNoEdt = view.findViewById(R.id.input_license);
        engineEdt = view.findViewById(R.id.input_engine);
        powerEdt = view.findViewById(R.id.input_power);
        fuelTypeSpinner = view.findViewById(R.id.input_fuel_type);
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

        setManufacturerSpinnerAdapter();
        setShapeSpinnerAdapter();
        setFuelTypeSpinnerAdapter();
        
    }

    private void setFuelTypeSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.fuelTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelTypeSpinner.setAdapter(adapter);
        fuelTypeSpinner.setSelection(0);
    }

    private void setManufacturerSpinnerAdapter() {
        ArrayAdapter<Object> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, createManufacturersList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        manufacturerSpinner.setAdapter(adapter);
        manufacturerSpinner.setSelection(0);
    }

    private void setShapeSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.shapes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shapeSpinner.setAdapter(adapter);
        shapeSpinner.setSelection(0);
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

    private void changeFragment(Fragment fragment) {
        fragmentManager = getActivity().getSupportFragmentManager();
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
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new CarsFragment());
                    return true;
                }
                return false;
            }
        });
    }
}
