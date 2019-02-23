package com.bestapps.carwallet.service;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.ServiceEntry;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AddServiceEntryFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private DatabaseHandler databaseHandler;
    private FragmentManager fragmentManager;
    private TextInputEditText titleEdt;
    private TextInputEditText descriptionEdt;
    private TextInputEditText serviceNameEdt;
    private TextInputEditText mileageEdt;
    private TextInputEditText priceEdt;
    private TextInputEditText dateEdt;
    private ImageView calendarImage;
    private DatePickerDialog dpd;
    private Button btnAdd;

    private TextInputLayout titleLayout;
    private TextInputLayout mileageLayout;
    private TextInputLayout priceLayout;

    private String title;
    private String description;
    private String serviceName;
    private int mileage;
    private double price;
    private String date;
    private int year;
    private int month;
    private int day;
    private boolean isEdit = false;
    private ServiceEntry serviceEntryEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String message;
        message = (String) getArguments().getSerializable("type");
        if (message.equals("edit")) {
            serviceEntryEdit = (ServiceEntry) getArguments().getSerializable("serviceEntry");
            isEdit = true;
        } else {
            isEdit = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_service_entry, container, false);
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        MainActivity.backCount = 22;
        createCalendar();
        initializeViews(view);
        handleOnBackPressed(view);
        handleClickListeners();
        if (isEdit) {
            titleEdt.setText(serviceEntryEdit.getTitle());
            descriptionEdt.setText(serviceEntryEdit.getDescription());
            serviceNameEdt.setText(serviceEntryEdit.getServiceName());
            dateEdt.setText(serviceEntryEdit.getDate());
            mileageEdt.setText("" + serviceEntryEdit.getMileage());
            priceEdt.setText("" + serviceEntryEdit.getPrice());
        }


        return view;
    }

    private void handleClickListeners() {
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
                    serviceEntry.setCarId(car.getId());
                    serviceEntry.setTitle(title);
                    serviceEntry.setDescription(description);
                    serviceEntry.setServiceName(serviceName);
                    serviceEntry.setMileage(mileage);
                    serviceEntry.setPrice(price);
                    serviceEntry.setDate(dateEdt.getText().toString());
                    serviceEntry.setYear(year);
                    serviceEntry.setMonth(month);
                    serviceEntry.setDay(day);
                    if (isEdit) {
                        serviceEntry.setId(serviceEntryEdit.getId());
                        databaseHandler.updateServiceEntry(serviceEntry);
                    } else {
                        databaseHandler.addServiceEntry(serviceEntry);
                    }
                    changeFragment(new ServiceFragment());
                }
                hideKeyboard();
            }
        });

    }

    private boolean validate() {
        title = titleEdt.getText().toString();
        description = descriptionEdt.getText().toString();
        serviceName = serviceNameEdt.getText().toString();
        boolean isValid = true;

        if (title.isEmpty()) {
            isValid = false;
            titleLayout.setError(" ");
        }

        if (mileageEdt.getText().toString().isEmpty() ||
                Integer.parseInt(mileageEdt.getText().toString()) <= 0 ) {
            isValid = false;
            mileageLayout.setError(" ");
        } else {
            mileage = Integer.parseInt(mileageEdt.getText().toString());
        }

        if (priceEdt.getText().toString().isEmpty()) {
            isValid = false;
            priceLayout.setError(" ");
        } else {
            price = Double.parseDouble(priceEdt.getText().toString());
        }

        return isValid;
    }

    private void clickListenerDate() {
        Calendar tomorrow = Calendar.getInstance();
        dpd.setMaxDate(tomorrow);
        dpd.show(fragmentManager, "Datepickerdialog");
    }

    private void initializeViews(View view) {
        titleEdt = view.findViewById(R.id.input_title);
        descriptionEdt = view.findViewById(R.id.input_description);
        serviceNameEdt = view.findViewById(R.id.input_service_name);
        mileageEdt = view.findViewById(R.id.input_service_mileage);
        priceEdt = view.findViewById(R.id.input_price);
        dateEdt = view.findViewById(R.id.input_date);
        dateEdt.setText(buildNowDate());
        calendarImage = view.findViewById(R.id.calendar_image);
        btnAdd = view.findViewById(R.id.btn_add_service_entry);
        titleLayout = view.findViewById(R.id.input_layout_title);
        mileageLayout = view.findViewById(R.id.input_layout_service_mileage);
        priceLayout = view.findViewById(R.id.input_layout_service_price);

        if (isEdit) {
            btnAdd.setText("Edit");
        } else {
            btnAdd.setText("Add");
        }
    }

    private String buildNowDate() {

        return Calendar.getInstance().get(Calendar.YEAR) + "-" +
                (1 + Calendar.getInstance().get(Calendar.MONTH)) + "-" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    private String buildDate(int year, int monthOfYear, int dayOfMonth) {
        return year + "-" + (1 + monthOfYear) + "-" + dayOfMonth;
    }

    private void createCalendar() {
        Calendar now = Calendar.getInstance();
        if (isEdit) {
            String[] splittedDate = serviceEntryEdit.getDate().split("-");
            int year = Integer.parseInt(splittedDate[0]);
            int month = Integer.parseInt(splittedDate[1]);
            int day = Integer.parseInt(splittedDate[2]);
            dpd = DatePickerDialog.newInstance(AddServiceEntryFragment.this,
                    year,
                    month,
                    day
                    );
        } else {
            year = now.get(Calendar.YEAR);
            month = now.get(Calendar.MONTH);
            day = now.get(Calendar.DAY_OF_MONTH);
            dpd = DatePickerDialog.newInstance(AddServiceEntryFragment.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
        }
        // If you're calling this from a support Fragment

    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
        dateEdt.setText(buildDate(year, monthOfYear, dayOfMonth));
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
        titleEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new ServiceFragment());
                    return true;
                }
                return false;
            }
        });
        descriptionEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new ServiceFragment());
                    return true;
                }
                return false;
            }
        });
        serviceNameEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new ServiceFragment());
                    return true;
                }
                return false;
            }
        });
        mileageEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new ServiceFragment());
                    return true;
                }
                return false;
            }
        });
        priceEdt.setOnKeyListener(new View.OnKeyListener() {
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

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
