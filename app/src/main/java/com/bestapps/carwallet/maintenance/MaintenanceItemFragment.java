package com.bestapps.carwallet.maintenance;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.ParametersSettings;
import com.bestapps.carwallet.model.Maintenance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MaintenanceItemFragment extends Fragment {
    private Maintenance maintenance;
    private FragmentManager fragmentManager;
    private TextView title;
    private TextView description;
    private TextView date;
//    private TextView hour;
    private TextView mileage;
    private TextView price;
    private FloatingActionButton fab;
    private DatabaseHandler databaseHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.maintenance = (Maintenance) getArguments().getSerializable("maintenance");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maintenance_item, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        databaseHandler = new DatabaseHandler(getContext());
        initializeViews(view);
        handleOnBackPressed(view);
        handleOnClickListeners();
        ParametersSettings parametersSettings = databaseHandler.findSettings();

        if (maintenance != null) {
            title.setText(maintenance.getTitle());
            description.setText(maintenance.getDescription());
            date.setText(maintenance.getDate());
//            hour.setText("" + maintenance.getHour() + ":" + maintenance.getMin());
            price.setText("Price: " + maintenance.getPrice() + " " + parametersSettings.getCurrency());
            mileage.setText("At mileage: " + maintenance.getMileage() + parametersSettings.getDistance());
        }
        return view;
    }

    private void initializeViews(View view) {
        title = view.findViewById(R.id.maintenance_item_title);
        description = view.findViewById(R.id.maintenance_item_description);
        date = view.findViewById(R.id.maintenance_item_date);
        price = view.findViewById(R.id.maintenance_item_price);
        mileage = view.findViewById(R.id.maintenance_item_mileage);
//        hour = view.findViewById(R.id.maintenance_item_hour);
        fab = view.findViewById(R.id.maintenance_item_fab_edit);
    }

    private void handleOnClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new AddMaintenanceFragment());
            }
        });
    }

    private void handleOnBackPressed(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    changeFragment(new MaintenanceFragment());
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
        bundle.putSerializable("maintenance" , maintenance);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
