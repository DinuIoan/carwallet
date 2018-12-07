package com.bestapps.carwallet.service;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.ServiceEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceFragment extends Fragment {
    private TextView licenseNoTextView;
    private ImageView imageView;
    private FloatingActionButton serviceFab;

    private DatabaseHandler databaseHandler;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    private int backCount = 0;

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
        initializeOnClickListeners();
        MainActivity.backCount = 0;

        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        Car car = databaseHandler.getActiveCar();

        if (car != null) {
            licenseNoTextView.setText(car.getLicenseNo());
            if (getActivity().getResources().getResourceName(car.getImage()) != null) {
                imageView.setImageResource(car.getImage());
            }
            List<ServiceEntry> serviceEntries = databaseHandler.findAllServiceEntriesByCarId(car.getId());

            mRecyclerView = view.findViewById(R.id.service_recycler_view);

            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            mAdapter = new ServiceRecyclerView(serviceEntries);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
        }
        return view;
    }

    private void initializeOnClickListeners() {
        serviceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new AddServiceEntryFragment());
            }
        });
    }

    private void initializeViews(View view) {
        licenseNoTextView = view.findViewById(R.id.active_car_license_no);
        imageView = view.findViewById(R.id.active_car_image);
        serviceFab = view.findViewById(R.id.service_fab);
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
                    backCount++;
                }
                if (backCount == 2) {
                    getActivity().finish();
                }
                return false;
            }
        });
    }
}
