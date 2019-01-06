package com.bestapps.carwallet.service;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.model.ServiceEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ServiceEntryFragment extends Fragment {
    private FragmentManager fragmentManager;
    private ServiceEntry serviceEntry;
    private TextView title;
    private TextView price;
    private TextView mileage;
    private TextView date;
    private TextView description;
    private TextView serviceName;
    private FloatingActionButton floatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.serviceEntry = (ServiceEntry) getArguments().getSerializable("serviceEntry");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_entry, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        handleOnBackPressed(view);
        initializeViews(view);
        handleOnClickListeners();

        if (serviceEntry != null) {
            title.setText(serviceEntry.getTitle());
            price.setText("Price: " + serviceEntry.getPrice());
            mileage.setText("Mileage: " + serviceEntry.getMileage() + " km");
            date.setText("Date: " + serviceEntry.getDate());
            description.setText(serviceEntry.getDescription());
            serviceName.setText("Service name: " + serviceEntry.getServiceName());
        }
        return view;
    }

    private void handleOnClickListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new AddServiceEntryFragment());
            }
        });
    }

    private void initializeViews(View view) {
        title = view.findViewById(R.id.service_entry_title);
        price = view.findViewById(R.id.service_entry_price);
        mileage = view.findViewById(R.id.service_entry_mileage);
        date = view.findViewById(R.id.service_entry_date);
        description = view.findViewById(R.id.service_entry_description);
        serviceName = view.findViewById(R.id.service_entry_service_name);
        floatingActionButton = view.findViewById(R.id.service_entry_fab_edit);
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

    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", "edit");
        bundle.putSerializable("serviceEntry", serviceEntry);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
