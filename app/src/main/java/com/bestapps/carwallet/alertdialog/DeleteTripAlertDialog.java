package com.bestapps.carwallet.alertdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.TripData;
import com.bestapps.carwallet.trip.TripFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DeleteTripAlertDialog extends Fragment {
    private FragmentManager fragmentManager;
    private TripData tripData;
    private DatabaseHandler databaseHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.tripData = (TripData) getArguments().getSerializable("tripData");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_dialog, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        handleOnBackPressed(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        databaseHandler = new DatabaseHandler(getContext());

        builder.setMessage(buildMessage(tripData))
                .setTitle(R.string.changeActiveCar);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                databaseHandler.deleteTripData(tripData.getId());
                changeFragment(new TripFragment());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                changeFragment(new TripFragment());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    changeFragment(new TripFragment());
                    dialog.dismiss();
                }
                return true;
            }
        });
        dialog.show();
        return view;

    }

    private String buildMessage(TripData tripData) {
        return "Are you sure that you want to delete this trip from '"
                + tripData.getFromLocation() + "' to '"
                + tripData.getToLocation() + "' with total price '"
                + tripData.getTotalPrice()
                + "' ?";
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
                    changeFragment(new TripFragment());
                    return true;
                }
                return false;
            }
        });
    }

}