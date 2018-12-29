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
    private LinearLayout expensesLinearLayout;
    private DatabaseHandler databaseHandler;
    private FragmentManager fragmentManager;


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

        return view;
    }



    private void handleOnClickListeners() {
        byMonthLinearLayout.setClickable(true);
        byMonthLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new StatisticsByMonthFragment());
            }
        });

        expensesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ExpensesStatsFragment());
            }
        });
    }

    private void initializeViews(View view) {
        activeCarLicenseNoTextView = view.findViewById(R.id.active_car_license_no);
        activeCarManufacturerTextView = view.findViewById(R.id.active_car_manufacturer);
        activeCarModelTextView = view.findViewById(R.id.active_car_model);
        activeCarVinTextView = view.findViewById(R.id.active_car_vin);
        byMonthLinearLayout = view.findViewById(R.id.by_month_linear_layout);
        expensesLinearLayout = view.findViewById(R.id.expenses_linear_layout);
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
