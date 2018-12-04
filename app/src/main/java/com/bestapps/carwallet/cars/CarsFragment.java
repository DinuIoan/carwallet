package com.bestapps.carwallet.cars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.data.StaticData;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CarsFragment extends Fragment {
    private FloatingActionButton serviceFab;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fragmentManager ;
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
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        MainActivity.backCount = 0;
        StaticData.setCars(databaseHandler.findAllCars());
        setClickListeners(view);

        mRecyclerView = view.findViewById(R.id.cars_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CarsRecyclerAdapter(StaticData.getCars(), databaseHandler, fragmentManager);
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
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
