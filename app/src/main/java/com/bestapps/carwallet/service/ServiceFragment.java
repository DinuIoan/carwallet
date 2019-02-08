package com.bestapps.carwallet.service;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.alertdialog.DeleteServiceEntryDialog;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.ServiceEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceFragment extends Fragment implements RecyclerItemTouchHelperListener {
    private TextView activeCarLicenseNoTextView;
    private TextView activeCarName;
    private TextView activeCarVinTextView;
    private TextView addCarFirstMessage;
    private FloatingActionButton serviceFab;

    private DatabaseHandler databaseHandler;
    private RecyclerView mRecyclerView;
    private ServiceRecyclerView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    private int backCount = 0;
    private Paint p = new Paint();
    private List<ServiceEntry> serviceEntries = new ArrayList<>();
    private List<ServiceEntry> serviceEntriesOrdered = new ArrayList<>();
    private FrameLayout rootLayout;

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
            serviceFab.setEnabled(true);
            addCarFirstMessage.setVisibility(View.GONE);

            activeCarLicenseNoTextView.setText("License no: " + car.getLicenseNo());
            activeCarName.setText(car.getManufacturer() + " " + car.getModel());
            activeCarVinTextView.setText("VIN: " + car.getVin());
            serviceEntries = databaseHandler.findAllServiceEntriesByCarId(car.getId());
            serviceEntriesOrdered = orderByDate(serviceEntries);

            mRecyclerView = view.findViewById(R.id.service_recycler_view);

            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));

            // specify an adapter (see also next example)
            mAdapter = new ServiceRecyclerView(serviceEntriesOrdered, fragmentManager);
            mRecyclerView.setAdapter(mAdapter);

            ItemTouchHelper.SimpleCallback item = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);

            new ItemTouchHelper(item).attachToRecyclerView(mRecyclerView);
        } else {
            serviceFab.setEnabled(false);
            addCarFirstMessage.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private List<ServiceEntry> orderByDate(List<ServiceEntry> serviceEntries) {
        SortedMap<Long, ServiceEntry> serviceEntrySortedMap = new TreeMap<>();
        for(int i = 0; i < serviceEntries.size(); i++) {
            ServiceEntry serviceEntry = serviceEntries.get(i);
            serviceEntrySortedMap.put(serviceEntry.getTimestamp(), serviceEntry);
        }
        List<ServiceEntry> serviceEntriesOrdered = new ArrayList<>(serviceEntrySortedMap.values());
        Collections.reverse(serviceEntriesOrdered);
        return serviceEntriesOrdered;
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ServiceRecyclerView.MyViewHolder) {
            ServiceEntry deletedServiceEntry = serviceEntriesOrdered.get(viewHolder.getAdapterPosition());
            int deleteIndex = viewHolder.getAdapterPosition();

            mAdapter.removeItem(deleteIndex);
            changeFragment(new DeleteServiceEntryDialog(), deletedServiceEntry);
        }
    }



    private void initializeOnClickListeners() {
        serviceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new AddServiceEntryFragment(), null);
            }
        });
    }

    private void initializeViews(View view) {
        addCarFirstMessage = view.findViewById(R.id.add_car_first_message);
        activeCarLicenseNoTextView = view.findViewById(R.id.active_car_license_no);
        activeCarName = view.findViewById(R.id.active_car_name);
        activeCarVinTextView = view.findViewById(R.id.active_car_vin);
        serviceFab = view.findViewById(R.id.service_fab);
        rootLayout = view.findViewById(R.id.root_layout);
    }

    private void changeFragment(Fragment fragment, ServiceEntry serviceEntry) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("serviceEntry", serviceEntry);
        bundle.putSerializable("type", "add");
        fragment.setArguments(bundle);
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
