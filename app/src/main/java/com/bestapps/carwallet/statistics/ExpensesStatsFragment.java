package com.bestapps.carwallet.statistics;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.Maintenance;
import com.bestapps.carwallet.model.ServiceEntry;
import com.bestapps.carwallet.service.ServiceFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ExpensesStatsFragment extends Fragment {
    private TextView mostExpensiveServiceHistoryTitle;
    private TextView mostExpensiveServiceHistoryDate;
    private TextView mostExpensiveServiceHistoryPrice;
    private TextView mostExpensiveCar;
    private TextView mostExpensiveCarLicenseNo;
    private TextView mostExpensiveCarAmountSpent;
    private TextView mostExpensiveCarMaintenanceAmountSpent;
    private TextView mostExpensiveMaintenanceCar;
    private TextView mostExpensiveCarMaintenanceLicenseNo;
    private TextView mostExpensiveServiceHistoryCar;

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
        View view = inflater.inflate(R.layout.fragment_expenses_stats, container, false);
        MainActivity.backCount = 0;
        initializeViews(view);
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        handleOnBackPressed(view);

        calculateMostExpensiveServiceHistory();
        calculateMostExpensiveCar();
        calculateMostExpensiveMaintenanceCar();

        return view;
    }

    private void calculateMostExpensiveMaintenanceCar() {
        List<Car> cars = databaseHandler.findAllCars();
        if (cars.isEmpty()) {
            mostExpensiveMaintenanceCar.setText("-");
            mostExpensiveCarMaintenanceLicenseNo.setText("-");
            mostExpensiveCarMaintenanceAmountSpent.setText("-");
        } else {
            List<Double> amounts = new ArrayList<>();
            for (Car car : cars) {
                List<Maintenance> maintenanceList =
                        databaseHandler.findAllMaintenance(car.getId());
                double amountSpent = 0.0;
                for (Maintenance maintenance : maintenanceList) {
                    amountSpent += maintenance.getPrice();
                }
                amounts.add(amountSpent);
            }
            double max = 0.0;
            int maxPos = 0;
            for (int i = 0; i < amounts.size(); i++) {
                if (amounts.get(i) > max) {
                    max = amounts.get(i);
                    maxPos = i;
                }
            }
            mostExpensiveMaintenanceCar.setText(cars.get(maxPos).getManufacturer() + " " + cars.get(maxPos).getModel());
            mostExpensiveCarMaintenanceLicenseNo.setText(cars.get(maxPos).getLicenseNo());
            mostExpensiveCarMaintenanceAmountSpent.setText("Amount spent: " + amounts.get(maxPos) + "$");
        }
    }

    private void calculateMostExpensiveCar() {
        List<Car> cars = databaseHandler.findAllCars();
        if (cars.isEmpty()) {
            mostExpensiveCar.setText("-");
            mostExpensiveCarLicenseNo.setText("-");
            mostExpensiveCarAmountSpent.setText("-");
        } else {
            List<Double> amounts = new ArrayList<>();
            for (Car car : cars) {
                List<ServiceEntry> serviceEntries =
                        databaseHandler.findAllServiceEntriesByCarId(car.getId());
                double amountSpent = 0.0;
                for (ServiceEntry serviceEntry : serviceEntries) {
                    amountSpent += serviceEntry.getPrice();
                }
                amounts.add(amountSpent);
            }
            double max = 0.0;
            int maxPos = 0;
            for (int i = 0; i < amounts.size(); i++) {
                if (amounts.get(i) > max) {
                    max = amounts.get(i);
                    maxPos = i;
                }
            }
            mostExpensiveCar.setText(cars.get(maxPos).getManufacturer() + " " + cars.get(maxPos).getModel());
            mostExpensiveCarLicenseNo.setText(cars.get(maxPos).getLicenseNo());
            mostExpensiveCarAmountSpent.setText("Amount spent: " + amounts.get(maxPos) + "$");
        }
    }

    private void calculateMostExpensiveServiceHistory() {
        List<ServiceEntry> serviceEntries = databaseHandler.findAllServiceEntries();
        if (serviceEntries.isEmpty()) {
            mostExpensiveServiceHistoryCar.setText("-");
            mostExpensiveServiceHistoryTitle.setText("-");
            mostExpensiveServiceHistoryDate.setText("-");
            mostExpensiveServiceHistoryPrice.setText("-");
        } else {
            ServiceEntry mostExpensiveServiceEntry = new ServiceEntry();
            double max = 0.0;
            for (ServiceEntry serviceEntry : serviceEntries) {
                if (serviceEntry.getPrice() > max) {
                    max = serviceEntry.getPrice();
                    mostExpensiveServiceEntry = serviceEntry;
                }
            }
            Car car = databaseHandler.findCarById(mostExpensiveServiceEntry.getCarId());
            mostExpensiveServiceHistoryCar.setText(car.getManufacturer() + " " + car.getModel());
            mostExpensiveServiceHistoryTitle.setText(mostExpensiveServiceEntry.getTitle());
            mostExpensiveServiceHistoryDate.setText(mostExpensiveServiceEntry.getDate());
            mostExpensiveServiceHistoryPrice.setText("Price: " + mostExpensiveServiceEntry.getPrice() + "$");
        }
    }

    private void initializeViews(View view) {
        mostExpensiveServiceHistoryTitle = view.findViewById(R.id.most_expensive_service_history_title);
        mostExpensiveServiceHistoryPrice = view.findViewById(R.id.most_expensive_service_history_price);
        mostExpensiveServiceHistoryDate = view.findViewById(R.id.most_expensive_service_history_date);
        mostExpensiveCar = view.findViewById(R.id.most_expensive_car);
        mostExpensiveCarLicenseNo = view.findViewById(R.id.most_expensive_license_no);
        mostExpensiveCarAmountSpent = view.findViewById(R.id.most_expensive_amount_spent);
        mostExpensiveCarMaintenanceAmountSpent = view.findViewById(R.id.most_expensive_maintenance_amount_spent);
        mostExpensiveMaintenanceCar = view.findViewById(R.id.most_expensive_maintenance_car);
        mostExpensiveCarMaintenanceLicenseNo = view.findViewById(R.id.most_expensive_maintenance_license_no);
        mostExpensiveServiceHistoryCar = view.findViewById(R.id.most_expensive_service_history_car);
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
                    changeFragment(new StatisticsFragment());
                    return true;
                }
                return false;
            }
        });

    }
}
