package com.bestapps.carwallet.maintenance;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.Maintenance;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static com.bestapps.carwallet.Notifications.CHANNEL_ID;

public class AddMaintenanceFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
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
    private CheckBox checkBoxNotification;
    private Spinner minSpinner;
    private Spinner hourSpinner;

    private String title;
    private String description;
    private int mileage;
    private double price;
    private String date;
    private boolean isEdit = false;
    private Maintenance maintenanceEdit;
    private List<String> hours = new ArrayList<>();
    private List<String> mins = new ArrayList<>();

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String message;
        message = (String) getArguments().getSerializable("type");
        if (message.equals("edit")) {
            maintenanceEdit = (Maintenance) getArguments().getSerializable("maintenance");
            isEdit = true;
        } else {
            isEdit = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_maintenance, container, false);
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        MainActivity.backCount = 0;
        createCalendar();
        initializeViews(view);
        handleOnBackPressed(view);
        handleClickListeners();
        if (isEdit) {
            titleEdt.setText(maintenanceEdit.getTitle());
            descriptionEdt.setText(maintenanceEdit.getDescription());
            dateEdt.setText(maintenanceEdit.getDate());
            mileageEdt.setText("" + maintenanceEdit.getMileage());
            priceEdt.setText("" + maintenanceEdit.getPrice());
            hourSpinner.setSelection(getPosition(hours, "hour"));
            minSpinner.setSelection(getPosition(mins, "min"));
        }
        return view;
    }

    private int getPosition(List<String> list, String type) {
        int count = 0;
        for (String item: list) {
            if (type.equals("min")) {
                if (item.equals("" + maintenanceEdit.getMin())) {
                    return count;
                }
            } else if (type.equals("hour")) {
                if (item.equals("" + maintenanceEdit.getHour())) {
                    return count;
                }
            }

            count++;
        }
        return 0;
    }

    private boolean validate() {
        title = titleEdt.getText().toString();
        description = descriptionEdt.getText().toString();

        boolean isValid = true;

        if (title.isEmpty()) {
            isValid = false;
            titleEdt.setError("");
        }

        if (!mileageEdt.getText().toString().isEmpty()) {
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
                    changeFragment(new MaintenanceFragment());
                    return true;
                }
                return false;
            }
        });
        titleEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new MaintenanceFragment());
                    return true;
                }
                return false;
            }
        });
        descriptionEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new MaintenanceFragment());
                    return true;
                }
                return false;
            }
        });
        mileageEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new MaintenanceFragment());
                    return true;
                }
                return false;
            }
        });
        priceEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new MaintenanceFragment());
                    return true;
                }
                return false;
            }
        });
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
                    Maintenance maintenance = new Maintenance();
                    maintenance.setTitle(title);
                    maintenance.setDescription(description);
                    maintenance.setMileage(mileage);
                    maintenance.setPrice(price);
                    maintenance.setDate(dateEdt.getText().toString());
                    maintenance.setHour(hourSpinner.getSelectedItem().toString());
                    maintenance.setMin(minSpinner.getSelectedItem().toString());
                    maintenance.setCarId(car.getId());
                    maintenance.setNotificationActive(1);
                    if (isEdit) {
                        maintenance.setId(maintenanceEdit.getId());
                        databaseHandler.updateMaintenance(maintenance);
//                        updateNotification();
                    } else {
                        databaseHandler.addMaintenance(maintenance);
                        addNotification(maintenance);
                    }
                    changeFragment(new MaintenanceFragment());
                }
            }
        });

    }

    private void clickListenerDate() {
        Calendar today = Calendar.getInstance();
        dpd.setMinDate(today);
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
        if (isEdit) {
            btnAdd.setText("Edit");
        } else {
            btnAdd.setText("Add");
        }
        minSpinner = view.findViewById(R.id.input_min);
        hourSpinner = view.findViewById(R.id.input_hour);
        setHourSpinner();
        setMinSpinner();
    }

    private void setHourSpinner() {
        hours = buildTime(0, 25, 1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, hours);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(arrayAdapter);
    }

    private void setMinSpinner() {
        mins = buildTime(0, 60, 5);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, mins);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minSpinner.setAdapter(arrayAdapter);
    }

    private List<String> buildTime(int min, int max, int step) {
        List<String> time = new ArrayList<>();
        for(int i = min; i < max; i += step) {
            if (i >= 0 && i < 10)
                time.add("0" + i);
            else
                time.add("" + i);
        }
        return time;
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
        dpd = DatePickerDialog.newInstance(AddMaintenanceFragment.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        // If you're calling this from a support Fragment

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateEdt.setText(buildDate(year, monthOfYear, dayOfMonth));
    }

    private void addNotification(Maintenance maintenance) {

//        alarmMgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
//        Calendar calendar = Calendar.getInstance();
//        String[] splittedDate = maintenance.getDate().split("-");
//        int year = Integer.parseInt(splittedDate[0]);
//        int month = Integer.parseInt(splittedDate[1]);
//        int day = Integer.parseInt(splittedDate[2]);
//        calendar.set(year, month - 1, day,
//                Integer.parseInt(maintenance.getHour()), Integer.parseInt(maintenance.getMin()));
//        alarmMgr.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,  SystemClock.elapsedRealtime() +
//                60 * 1000, alarmIntent);
    }
}
