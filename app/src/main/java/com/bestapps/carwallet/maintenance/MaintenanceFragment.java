package com.bestapps.carwallet.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.Maintenance;
import com.bestapps.carwallet.service.RecyclerItemTouchHelper;
import com.bestapps.carwallet.service.RecyclerItemTouchHelperListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MaintenanceFragment extends Fragment implements RecyclerItemTouchHelperListener {
    private TextView licenseNoTextView;
    private ImageView imageView;
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
            licenseNoTextView.setText(car.getLicenseNo());
            if (getActivity().getResources().getResourceName(car.getImage()) != null) {
                imageView.setImageResource(car.getImage());
            }

            maintenanceList = databaseHandler.findAllMaintenance(car.getId());

            mRecyclerView = view.findViewById(R.id.maintenance_recycler_view);

            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));

            // specify an adapter (see also next example)
            mAdapter = new MaintenanceRecyclerView(maintenanceList);
            mRecyclerView.setAdapter(mAdapter);

            ItemTouchHelper.SimpleCallback item = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);

            new ItemTouchHelper(item).attachToRecyclerView(mRecyclerView);

        }
        return view;
    }

    private void setOnClickListeners() {
        maintenanceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new AddMaintenanceFragment());
            }
        });
    }

    private void initializeViews(View view) {
        licenseNoTextView = view.findViewById(R.id.active_car_license_no);
        imageView = view.findViewById(R.id.active_car_image);
        maintenanceFab = view.findViewById(R.id.maintenance_fab);
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MaintenanceRecyclerView.MyViewHolder) {
            String title = maintenanceList.get(viewHolder.getAdapterPosition()).getTitle();

            Maintenance deletedMaintenance = maintenanceList.get(viewHolder.getAdapterPosition());
            int deleteIndex = viewHolder.getAdapterPosition();

            mAdapter.removeItem(deleteIndex);
//            changeFragment(null, deletedMaintenance);
        }
    }
}
