package com.bestapps.carwallet.trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bestapps.carwallet.MainActivity;
import com.bestapps.carwallet.R;
import com.bestapps.carwallet.alertdialog.DeleteTripAlertDialog;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.ParametersSettings;
import com.bestapps.carwallet.model.TripData;
import com.bestapps.carwallet.service.RecyclerItemTouchHelper;
import com.bestapps.carwallet.service.RecyclerItemTouchHelperListener;

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

public class TripFragment extends Fragment implements RecyclerItemTouchHelperListener {
    private DatabaseHandler databaseHandler;
    private FragmentManager fragmentManager;
    private RecyclerView mRecyclerView;
    private TripRecyclerView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button calculateButton;
    List<TripData> tripDataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip, container, false);

        MainActivity.backCount = 0;
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();
        calculateButton = view.findViewById(R.id.calculate_button);
        ParametersSettings parametersSettings = databaseHandler.findSettings();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new CalculateTripFragment(), null);
            }
        });

        tripDataList = databaseHandler.findAllTrips();
        mRecyclerView = view.findViewById(R.id.trip_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        // specify an adapter (see also next example)
        mAdapter = new TripRecyclerView(tripDataList, parametersSettings);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback item = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);

        new ItemTouchHelper(item).attachToRecyclerView(mRecyclerView);
        return view;
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof TripRecyclerView.MyViewHolder) {
            TripData deletedTripData = tripDataList.get(viewHolder.getAdapterPosition());
            int deleteIndex = viewHolder.getAdapterPosition();

            mAdapter.removeItem(deleteIndex);
            changeFragment(new DeleteTripAlertDialog(), deletedTripData);
        }
    }

    private void changeFragment(Fragment fragment, TripData tripData) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("tripData", tripData);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
