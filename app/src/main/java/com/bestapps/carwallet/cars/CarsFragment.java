package com.bestapps.carwallet.cars;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.data.StaticData;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.listener.ClickListener;

public class CarsFragment extends Fragment {
    private FloatingActionButton serviceFab;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    private DatabaseHandler databaseHandler;
    private Button setActiveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cars, container, false);
        serviceFab = view.findViewById(R.id.service_fab);
        setActiveButton = view.findViewById(R.id.set_active_button);
        databaseHandler = new DatabaseHandler(getContext());
        StaticData.setCars(databaseHandler.findAllCars());
        setClickListeners(view);

        mRecyclerView = view.findViewById(R.id.cars_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CarsRecyclerAdapter(StaticData.getCars(), databaseHandler);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void setClickListeners(View view) {
        serviceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new AddCarFragment());
            }
        });
        setActiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
