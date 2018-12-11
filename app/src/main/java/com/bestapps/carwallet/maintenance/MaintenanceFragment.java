package com.bestapps.carwallet.maintenance;

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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.bestapps.carwallet.MainActivity.backCount;

public class MaintenanceFragment extends Fragment {
    private TextView licenseNoTextView;
    private ImageView imageView;
    private FloatingActionButton serviceFab;
    private FragmentManager fragmentManager;
    private DatabaseHandler databaseHandler;

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
        MainActivity.backCount = 0;

        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();

        Car car = databaseHandler.getActiveCar();
        if (car != null) {
            licenseNoTextView.setText(car.getLicenseNo());
            if (getActivity().getResources().getResourceName(car.getImage()) != null) {
                imageView.setImageResource(car.getImage());
            }

        }
        return view;
    }

    private void initializeViews(View view) {
        licenseNoTextView = view.findViewById(R.id.active_car_license_no);
        imageView = view.findViewById(R.id.active_car_image);
        serviceFab = view.findViewById(R.id.service_fab);
    }

    private void changeFragment(Fragment fragment, ServiceEntry serviceEntry) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
