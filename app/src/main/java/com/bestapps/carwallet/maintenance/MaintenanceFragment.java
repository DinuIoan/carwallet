package com.bestapps.carwallet.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.alertdialog.DeleteMaintenanceDialog;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.Maintenance;
import com.bestapps.carwallet.service.RecyclerItemTouchHelper;
import com.bestapps.carwallet.service.RecyclerItemTouchHelperListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MaintenanceFragment extends Fragment {
    private TextView activeCarLicenseNoTextView;
    private TextView activeCarManufacturerTextView;
    private TextView activeCarModelTextView;
    private TextView activeCarVinTextView;
    private FloatingActionButton maintenanceFab;
    private FragmentManager fragmentManager;
    private DatabaseHandler databaseHandler;
    private RecyclerView mRecyclerView;
    private MaintenanceRecyclerView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Maintenance> maintenanceList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maintenance, container, false);
        initializeViews(view);
        setOnClickListeners();
        MainActivity.backCount = 0;

        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();

        Car car = databaseHandler.getActiveCar();
        if (car != null) {
            activeCarLicenseNoTextView.setText("License no: " + car.getLicenseNo());
            activeCarModelTextView.setText(car.getModel());
            activeCarManufacturerTextView.setText(car.getManufacturer());
            activeCarVinTextView.setText("VIN: " + car.getVin());

            maintenanceList = databaseHandler.findAllMaintenance(car.getId());
            List<Maintenance> maintenanceListOrdered = orderByDate(maintenanceList);

            mRecyclerView = view.findViewById(R.id.maintenance_recycler_view);

            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            // specify an adapter (see also next example)
            mAdapter = new MaintenanceRecyclerView(maintenanceListOrdered, fragmentManager);
            mRecyclerView.setAdapter(mAdapter);

        }
        return view;
    }

    private void setOnClickListeners() {
        maintenanceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new AddMaintenanceFragment(), null);
            }
        });
    }

    private void initializeViews(View view) {
        activeCarLicenseNoTextView = view.findViewById(R.id.active_car_license_no);
        activeCarManufacturerTextView = view.findViewById(R.id.active_car_manufacturer);
        activeCarModelTextView = view.findViewById(R.id.active_car_model);
        activeCarVinTextView = view.findViewById(R.id.active_car_vin);
        maintenanceFab = view.findViewById(R.id.maintenance_fab);
    }

    private void changeFragment(Fragment fragment, Maintenance maintenance) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", "add");
        bundle.putSerializable("maintenance", maintenance);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }

    private List<Maintenance> orderByDate(List<Maintenance> maintenanceList) {
        SortedMap<Long, Maintenance> maintenanceSortedMap = new TreeMap<>();
        for(int i = 0; i < maintenanceList.size(); i++) {
            Maintenance maintenance = maintenanceList.get(i);

            maintenanceSortedMap.put(maintenance.getTimestamp(), maintenance);
        }
        return new ArrayList<>(maintenanceSortedMap.values());
    }


}
