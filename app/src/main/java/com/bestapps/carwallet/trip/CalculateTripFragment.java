package com.bestapps.carwallet.trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CalculateTripFragment extends Fragment {
    private DatabaseHandler databaseHandler;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculate_trip, container, false);
        databaseHandler = new DatabaseHandler(getContext());
        fragmentManager = getActivity().getSupportFragmentManager();

        return view;
    }
}
