package com.bestapps.carwallet.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.cars.CarsRecyclerAdapter;
import com.bestapps.carwallet.data.StaticData;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.ServiceEntry;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceFragment extends Fragment {
    private DatabaseHandler databaseHandler;
    private TextView licenseNoTextView;
    private ImageView imageView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        initializeViews(view);
        databaseHandler = new DatabaseHandler(getContext());
        Car car = databaseHandler.getActiveCar();
        if (car != null ) {
            licenseNoTextView.setText(car.getLicenseNo());
            imageView.setImageResource(car.getImage());
            List<ServiceEntry> serviceEntries = databaseHandler.findAllServiceEntriesByCarId(car.getId());

            mRecyclerView = view.findViewById(R.id.cars_recycler_view);

            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            mAdapter = new ServiceRecyclerView(serviceEntries);
            mRecyclerView.setAdapter(mAdapter);
        }
        return view;
    }

    private void initializeViews(View view) {
        licenseNoTextView = view.findViewById(R.id.active_car_license_no);
        imageView = view.findViewById(R.id.active_car_image);
    }
}
