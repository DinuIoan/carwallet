package com.bestapps.carwallet.statistics;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.ServiceEntry;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class StatisticsByMonthFragment extends Fragment {
    private DatabaseHandler databaseHandler;
    private FragmentManager fragmentManager;
    private Car activeCar;
    private Spinner inputYear;
    private ScatterChart januaryChart;
    private ScatterChart februaryChart;
    private ScatterChart marchChart;
    private ScatterChart aprilChart;
    private ScatterChart mayChart;
    private ScatterChart juneChart;
    private ScatterChart julyChart;
    private ScatterChart augustChart;
    private ScatterChart septemberChart;
    private ScatterChart octoberChart;
    private ScatterChart novemberChart;
    private ScatterChart decemberChart;
    private TextView januaryNoData;
    private TextView februaryNoData;
    private TextView marchNoData;
    private TextView aprilNoData;
    private TextView mayNoData;
    private TextView juneNoData;
    private TextView julyNoData;
    private TextView augustNoData;
    private TextView septemberNoData;
    private TextView octoberNoData;
    private TextView novemberNoData;
    private TextView decemberNoData;
    private TextView noDataAvailableTextView;
    private TextView selectYearTextView;
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
        handleOnClickListeners();
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        activeCar = databaseHandler.getActiveCar();

        List<ServiceEntry> serviceEntryList = databaseHandler.findAllServiceEntriesByCarId(activeCar.getId());

        if (serviceEntryList.isEmpty()) {
            noDataAvailableTextView.setVisibility(View.VISIBLE);
            selectYearTextView.setVisibility(View.GONE);
            inputYear.setVisibility(View.GONE);
        } else {
            noDataAvailableTextView.setVisibility(View.GONE);
            selectYearTextView.setVisibility(View.VISIBLE);
            inputYear.setVisibility(View.VISIBLE);
            Set<Integer> years = getYears(serviceEntryList);
            List<Integer> yearsList = new ArrayList<>(years);
            Collections.reverse(yearsList);
            ArrayAdapter<Object> adapter = new ArrayAdapter<>(getContext(),
                    R.layout.select_year_spinner_item, yearsList.toArray());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            inputYear.setAdapter(adapter);
            inputYear.setSelection(0);
            loadChartData((Integer) inputYear.getSelectedItem());
        }

        return view;
    }

    private void handleOnClickListeners() {
        inputYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadChartData((Integer) inputYear.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadChartData(Integer selectedItem) {
        List<ServiceEntry> serviceEntries =
                databaseHandler.findAllServiceEntriesByYearAndCarId(activeCar.getId(), selectedItem);
        List<ServiceEntry> januaryServiceEntry = new ArrayList<>();
        List<ServiceEntry> februaryServiceEntry = new ArrayList<>();
        List<ServiceEntry> marchServiceEntry = new ArrayList<>();
        List<ServiceEntry> aprilServiceEntry = new ArrayList<>();
        List<ServiceEntry> mayServiceEntry = new ArrayList<>();
        List<ServiceEntry> juneServiceEntry = new ArrayList<>();
        List<ServiceEntry> julyServiceEntry = new ArrayList<>();
        List<ServiceEntry> augustServiceEntry = new ArrayList<>();
        List<ServiceEntry> septemberServiceEntry = new ArrayList<>();
        List<ServiceEntry> octoberServiceEntry = new ArrayList<>();
        List<ServiceEntry> novemberServiceEntry = new ArrayList<>();
        List<ServiceEntry> decemberServiceEntry = new ArrayList<>();

        for (ServiceEntry serviceEntry: serviceEntries) {
            switch (serviceEntry.getMonth()) {
                case 0:
                    januaryServiceEntry.add(serviceEntry);
                    break;
                case 1:
                    februaryServiceEntry.add(serviceEntry);
                    break;
                case 2:
                    marchServiceEntry.add(serviceEntry);
                    break;
                case 3:
                    aprilServiceEntry.add(serviceEntry);
                    break;
                case 4:
                    mayServiceEntry.add(serviceEntry);
                    break;
                case 5:
                    juneServiceEntry.add(serviceEntry);
                    break;
                case 6:
                    julyServiceEntry.add(serviceEntry);
                    break;
                case 7:
                    augustServiceEntry.add(serviceEntry);
                    break;
                case 8:
                    septemberServiceEntry.add(serviceEntry);
                    break;
                case 9:
                    octoberServiceEntry.add(serviceEntry);
                    break;
                case 10:
                    novemberServiceEntry.add(serviceEntry);
                    break;
                case 11:
                    decemberServiceEntry.add(serviceEntry);
                    break;
                default:
                    break;
            }
        }
        if (januaryServiceEntry.isEmpty()) {
            januaryChart.setVisibility(View.GONE);
            januaryNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(januaryChart, januaryServiceEntry);
            januaryChart.setVisibility(View.VISIBLE);
            januaryNoData.setVisibility(View.GONE);
        }

        if (februaryServiceEntry.isEmpty()) {
            februaryChart.setVisibility(View.GONE);
            februaryNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(februaryChart, februaryServiceEntry);
            februaryChart.setVisibility(View.VISIBLE);
            februaryNoData.setVisibility(View.GONE);
        }
        if (marchServiceEntry.isEmpty()) {
            marchChart.setVisibility(View.GONE);
            marchNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(marchChart, marchServiceEntry);
            marchChart.setVisibility(View.VISIBLE);
            marchNoData.setVisibility(View.GONE);
        }
        if (aprilServiceEntry.isEmpty()) {
            aprilChart.setVisibility(View.GONE);
            aprilNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(aprilChart, aprilServiceEntry);
            aprilChart.setVisibility(View.VISIBLE);
            aprilNoData.setVisibility(View.GONE);
        }

        if (mayServiceEntry.isEmpty()) {
            mayChart.setVisibility(View.GONE);
            mayNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(mayChart, mayServiceEntry);
            mayChart.setVisibility(View.VISIBLE);
            mayNoData.setVisibility(View.GONE);
        }
        if (juneServiceEntry.isEmpty()) {
            juneChart.setVisibility(View.GONE);
            juneNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(juneChart, juneServiceEntry);
            juneChart.setVisibility(View.VISIBLE);
            juneNoData.setVisibility(View.GONE);
        }

        if (julyServiceEntry.isEmpty()) {
            julyChart.setVisibility(View.GONE);
            julyNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(julyChart, julyServiceEntry);
            julyChart.setVisibility(View.VISIBLE);
            julyNoData.setVisibility(View.GONE);
        }

        if (augustServiceEntry.isEmpty()) {
            augustChart.setVisibility(View.GONE);
            augustNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(augustChart, augustServiceEntry);
            augustChart.setVisibility(View.VISIBLE);
            augustNoData.setVisibility(View.GONE);
        }

        if (septemberServiceEntry.isEmpty()) {
            septemberChart.setVisibility(View.GONE);
            septemberNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(septemberChart, septemberServiceEntry);
            septemberChart.setVisibility(View.VISIBLE);
            septemberNoData.setVisibility(View.GONE);
        }

        if (octoberServiceEntry.isEmpty()) {
            octoberChart.setVisibility(View.GONE);
            octoberNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(octoberChart, octoberServiceEntry);
            octoberChart.setVisibility(View.VISIBLE);
            octoberNoData.setVisibility(View.GONE);
        }

        if (novemberServiceEntry.isEmpty()) {
            novemberChart.setVisibility(View.GONE);
            novemberNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(novemberChart, novemberServiceEntry);
            novemberChart.setVisibility(View.VISIBLE);
            novemberNoData.setVisibility(View.GONE);
        }

        if (decemberServiceEntry.isEmpty()) {
            decemberChart.setVisibility(View.GONE);
            decemberNoData.setVisibility(View.VISIBLE);
        } else {
            createChart(decemberChart, decemberServiceEntry);
            decemberChart.setVisibility(View.VISIBLE);
            decemberNoData.setVisibility(View.GONE);
        }
    }

    private Set<Integer> getYears(List<ServiceEntry> serviceEntryList) {
        Set<Integer> years = new TreeSet<>();
        for (ServiceEntry serviceEntry: serviceEntryList) {
            years.add(serviceEntry.getYear());
        }

        return years;
    }

    private void initializeViews(View view) {
        januaryChart = view.findViewById(R.id.january_chart);
        februaryChart = view.findViewById(R.id.february_chart);
        marchChart = view.findViewById(R.id.march_chart);
        aprilChart = view.findViewById(R.id.april_chart);
        mayChart = view.findViewById(R.id.may_chart);
        juneChart = view.findViewById(R.id.june_chart);
        julyChart = view.findViewById(R.id.july_chart);
        augustChart = view.findViewById(R.id.august_chart);
        septemberChart = view.findViewById(R.id.september_chart);
        octoberChart = view.findViewById(R.id.october_chart);
        novemberChart = view.findViewById(R.id.november_chart);
        decemberChart = view.findViewById(R.id.december_chart);

        januaryNoData = view.findViewById(R.id.january_chart_text_view);
        februaryNoData = view.findViewById(R.id.february_chart_text_view);
        marchNoData = view.findViewById(R.id.march_chart_text_view);
        aprilNoData = view.findViewById(R.id.april_chart_text_view);
        mayNoData = view.findViewById(R.id.may_chart_text_view);
        juneNoData = view.findViewById(R.id.june_chart_text_view);
        julyNoData = view.findViewById(R.id.july_chart_text_view);
        augustNoData = view.findViewById(R.id.august_chart_text_view);
        septemberNoData = view.findViewById(R.id.september_chart_text_view);
        octoberNoData= view.findViewById(R.id.october_chart_text_view);
        novemberNoData = view.findViewById(R.id.november_chart_text_view);
        decemberNoData = view.findViewById(R.id.december_chart_text_view);

        noDataAvailableTextView = view.findViewById(R.id.no_data_available_text_view);
        selectYearTextView = view.findViewById(R.id.input_title);
        inputYear = view.findViewById(R.id.input_year);
    }

    private void createChart(ScatterChart chart, List<ServiceEntry> serviceEntries) {
        List<Entry> entries = new ArrayList<Entry>();

        for (ServiceEntry serviceEntry: serviceEntries) {
            String[] splittedDate = serviceEntry.getDate().split("-");
            int day = Integer.parseInt(splittedDate[2]);

            entries.add(new BarEntry(day, Float.parseFloat("" + serviceEntry.getPrice())));
        }
        ScatterDataSet dataSet = new ScatterDataSet(entries, "Price ($)"); // add entries to dataset
        dataSet.setColor(R.color.colorSecondaryReplyOrange);

        ScatterData scatterData = new ScatterData(dataSet);
        chart.setData(scatterData);
        chart.getXAxis().setDrawGridLines(false);
        Description description = new Description();
        
        description.setEnabled(false);
        chart.setDescription(description);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        chart.getXAxis().setAxisMinimum(1L);
        chart.getXAxis().setAxisMaximum(31L);
        chart.getXAxis().setGranularity(1L);
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
