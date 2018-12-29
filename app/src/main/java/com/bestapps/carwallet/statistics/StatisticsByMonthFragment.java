package com.bestapps.carwallet.statistics;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class StatisticsByMonthFragment extends Fragment {
    private BarChart chart;
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
        View view = inflater.inflate(R.layout.fragment_statistics_by_month, container, false);
        initializeViews(view);
        handleOnBackPressed(view);
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        activeCar = databaseHandler.getActiveCar();
        createChart();
        return view;
    }

    private void initializeViews(View view) {
        chart = view.findViewById(R.id.chart);
    }

    private void createChart() {
        List<BarEntry> entries = new ArrayList<BarEntry>();
        List<ServiceEntry> serviceEntries =
                databaseHandler.findAllServiceEntriesByCarId(activeCar.getId());
        for (ServiceEntry serviceEntry: serviceEntries) {
            String[] splittedDate = serviceEntry.getDate().split("-");
            int year = Integer.parseInt(splittedDate[0]);
            int month = Integer.parseInt(splittedDate[1]);
            int day = Integer.parseInt(splittedDate[2]);

            entries.add(new BarEntry(day, Float.parseFloat("" + serviceEntry.getPrice())));
        }
        BarDataSet dataSet = new BarDataSet(entries, "Price ($)"); // add entries to dataset
        dataSet.setColor(R.color.colorSecondaryReplyOrange);

        BarData barData= new BarData(dataSet);
        chart.setData(barData);
        chart.getXAxis().setDrawGridLines(false);
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        chart.getXAxis().setDrawGridLinesBehindData(false);
        chart.getAxisRight().setEnabled(false);
        chart.invalidate();
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

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
