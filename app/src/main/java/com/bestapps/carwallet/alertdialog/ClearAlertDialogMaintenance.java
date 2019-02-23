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
import com.bestapps.carwallet.maintenance.MaintenanceFragment;
import com.bestapps.carwallet.model.Maintenance;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ClearAlertDialogMaintenance extends Fragment {
    private FragmentManager fragmentManager;
    private Maintenance maintenance;
    private DatabaseHandler databaseHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.maintenance = (Maintenance) getArguments().getSerializable("maintenance");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_dialog, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        handleOnBackPressed(view);
        databaseHandler = new DatabaseHandler(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(buildMessage(maintenance))
                .setTitle(R.string.attention);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                databaseHandler.deleteMaintenance(maintenance.getId());
                changeFragment(new MaintenanceFragment());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                changeFragment(new MaintenanceFragment());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    changeFragment(new MaintenanceFragment());
                    dialog.dismiss();
                }
                return true;
            }
        });
        dialog.show();
        return view;
    }

    private String buildMessage(Maintenance maintenance) {
        return "Are you sure you want to delete '"
                + maintenance.getTitle()
                + "' from '"
                + maintenance.getDate()
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
                    changeFragment(new MaintenanceFragment());
                    return true;
                }
                return false;
            }
        });
    }
}
