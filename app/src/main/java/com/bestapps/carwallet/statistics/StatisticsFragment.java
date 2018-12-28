package com.bestapps.carwallet.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.ServiceEntry;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class StatisticsFragment extends Fragment {
    private TextView activeCarLicenseNoTextView;
    private TextView activeCarManufacturerTextView;
    private TextView activeCarModelTextView;
    private TextView activeCarVinTextView;
    private LinearLayout byMonthLinearLayout;
    private DatabaseHandler databaseHandler;
    private FragmentManager fragmentManager;
    private TextView mostExpensiveServiceHistoryTitle;
    private TextView mostExpensiveServiceHistoryDate;
    private TextView mostExpensiveServiceHistoryPrice;
    private TextView mostExpensiveCarManufacturer;
    private TextView mostExpensiveCarModel;
    private TextView mostExpensiveCarLicenseNo;
    private TextView mostExpensiveCarAmountSpent;

    private Car activeCar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        initializeViews(view);
        handleOnClickListeners();
        MainActivity.backCount = 0;
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        activeCar = databaseHandler.getActiveCar();

        if (activeCar != null) {
            activeCarLicenseNoTextView.setText("License no: " + activeCar.getLicenseNo());
            activeCarModelTextView.setText(activeCar.getModel());
            activeCarManufacturerTextView.setText(activeCar.getManufacturer());
            activeCarVinTextView.setText("VIN: " + activeCar.getVin());
        }
        calculateMostExpensiveServiceHistory();
        calculateMostExpensiveCar();

        return view;
    }

    private void calculateMostExpensiveCar() {
        List<Car> cars = databaseHandler.findAllCars();
        List<Double> amounts = new ArrayList<>();
        for (Car car: cars) {
            List<ServiceEntry> serviceEntries =
                    databaseHandler.findAllServiceEntriesByCarId(car.getId());
            double amountSpent = 0.0;
            for (ServiceEntry serviceEntry: serviceEntries) {
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
        mostExpensiveCarManufacturer.setText(cars.get(maxPos).getManufacturer());
        mostExpensiveCarModel.setText(cars.get(maxPos).getModel());
        mostExpensiveCarLicenseNo.setText(cars.get(maxPos).getLicenseNo());
        mostExpensiveCarAmountSpent.setText("Amount spent: " + amounts.get(maxPos) + "$");
    }

    private void calculateMostExpensiveServiceHistory() {
        List<ServiceEntry> serviceEntries = databaseHandler.findAllServiceEntries();
        ServiceEntry mostExpensiveServiceEntry = new ServiceEntry();
        double max = 0.0;
        for (ServiceEntry serviceEntry: serviceEntries) {
            if (serviceEntry.getPrice() > max) {
                max = serviceEntry.getPrice();
                mostExpensiveServiceEntry = serviceEntry;
            }
        }
        mostExpensiveServiceHistoryTitle.setText(mostExpensiveServiceEntry.getTitle());
        mostExpensiveServiceHistoryDate.setText(mostExpensiveServiceEntry.getDate());
        mostExpensiveServiceHistoryPrice.setText("" + mostExpensiveServiceEntry.getPrice() + "$");
    }

    private void handleOnClickListeners() {
        byMonthLinearLayout.setClickable(true);
        byMonthLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new StatisticsByMonthFragment());
            }
        });
    }

    private void initializeViews(View view) {
        activeCarLicenseNoTextView = view.findViewById(R.id.active_car_license_no);
        activeCarManufacturerTextView = view.findViewById(R.id.active_car_manufacturer);
        activeCarModelTextView = view.findViewById(R.id.active_car_model);
        activeCarVinTextView = view.findViewById(R.id.active_car_vin);
        mostExpensiveServiceHistoryTitle = view.findViewById(R.id.most_expensive_service_history_title);
        mostExpensiveServiceHistoryPrice = view.findViewById(R.id.most_expensive_service_history_price);
        mostExpensiveServiceHistoryDate = view.findViewById(R.id.most_expensive_service_history_date);
        mostExpensiveCarManufacturer = view.findViewById(R.id.most_expensive_manufacturer);
        mostExpensiveCarModel = view.findViewById(R.id.most_expensive_model);
        mostExpensiveCarLicenseNo = view.findViewById(R.id.most_expensive_license_no);
        byMonthLinearLayout = view.findViewById(R.id.by_month_linear_layout);
        mostExpensiveCarAmountSpent = view.findViewById(R.id.most_expensive_amount_spent);
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
