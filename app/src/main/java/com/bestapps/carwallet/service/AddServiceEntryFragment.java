package com.bestapps.carwallet.service;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.ServiceEntry;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AddServiceEntryFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private DatabaseHandler databaseHandler;
    private FragmentManager fragmentManager;
    private EditText titleEdt;
    private EditText descriptionEdt;
    private EditText mileageEdt;
    private EditText priceEdt;
    private EditText dateEdt;
    private ImageView calendarImage;
    private DatePickerDialog dpd;
    private Button btnAdd;

    private String title;
    private String description;
    private int mileage;
    private double price;
    private String date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_service_entry, container, false);
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        MainActivity.backCount = 0;
        handleOnBackPressed(view);
        createCalendar();
        initializeViews(view);
        handleClikcListeners();


        return view;
    }

    private void handleClikcListeners() {
        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListenerDate();
            }
        });
        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListenerDate();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Car car = databaseHandler.getActiveCar();
                    ServiceEntry serviceEntry = new ServiceEntry();
                    serviceEntry.setTitle(title);
                    serviceEntry.setDescription(description);
                    serviceEntry.setMileage(mileage);
                    serviceEntry.setPrice(price);
                    serviceEntry.setDate(dateEdt.getText().toString());
                    serviceEntry.setCarId(car.getId());
                    databaseHandler.addServiceEntry(serviceEntry);
                    changeFragment(new ServiceFragment());
                }
            }
        });

    }

    private boolean validate() {
        title = titleEdt.getText().toString();
        description = descriptionEdt.getText().toString();
        boolean isValid = true;

        if (title.isEmpty()) {
            isValid = false;
            titleEdt.setError("");
        }

        if (mileageEdt.getText().toString().isEmpty()) {
            isValid = false;
            mileageEdt.setError("");
        } else {
            mileage = Integer.parseInt(mileageEdt.getText().toString());
        }

        if (priceEdt.getText().toString().isEmpty()) {
            isValid = false;
            priceEdt.setError("");
        } else {
            price = Double.parseDouble(priceEdt.getText().toString());
        }

        return isValid;
    }

    private void clickListenerDate() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.set(Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.DAY_OF_MONTH);
        dpd.setMaxDate(tomorrow);
        dpd.show(fragmentManager, "Datepickerdialog");
    }

    private void initializeViews(View view) {
        titleEdt = view.findViewById(R.id.input_title);
        descriptionEdt = view.findViewById(R.id.input_description);
        mileageEdt = view.findViewById(R.id.input_service_mileage);
        priceEdt = view.findViewById(R.id.input_price);
        dateEdt = view.findViewById(R.id.input_date);
        dateEdt.setText(buildNowDate());
        calendarImage = view.findViewById(R.id.calendar_image);
        btnAdd = view.findViewById(R.id.btn_add_service_entry);
    }

    private String buildNowDate() {
        return Calendar.getInstance().get(Calendar.YEAR) + "/" +
                (1 + Calendar.getInstance().get(Calendar.MONTH)) + "/" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    private String buildDate(int year, int monthOfYear, int dayOfMonth) {
        return year + "/" + (1 + monthOfYear) + "/" + dayOfMonth;
    }

    private void createCalendar() {
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(AddServiceEntryFragment.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        // If you're calling this from a support Fragment

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
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new ServiceFragment());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateEdt.setText(buildDate(year, monthOfYear, dayOfMonth));
    }
}
