package com.bestapps.carwallet.alertdialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.ServiceEntry;
import com.bestapps.carwallet.service.ServiceFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DeleteServiceEntryDialog extends Fragment {
    private FragmentManager fragmentManager;
    private ServiceEntry serviceEntry;
    private DatabaseHandler databaseHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.serviceEntry = (ServiceEntry) getArguments().getSerializable("serviceEntry");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_dialog, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        handleOnBackPressed(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        databaseHandler = new DatabaseHandler(getContext());

        builder.setMessage(buildMessage(serviceEntry))
                .setTitle(R.string.changeActiveCar);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                databaseHandler.deleteServiceEntry(serviceEntry.getId());
                changeFragment(new ServiceFragment());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                changeFragment(new ServiceFragment());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return view;

    }

    private String buildMessage(ServiceEntry serviceEntry) {
        return "Are you sure that you want to delete this service history: '"
                + serviceEntry.getTitle()
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
                    changeFragment(new ServiceFragment());
                    return true;
                }
                return false;
            }
        });
    }

}
