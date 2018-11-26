package com.bestapps.carwallet.alertdialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.cars.CarsFragment;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;

import java.util.List;

public class AlertDialogFragment extends Fragment {
    private FragmentManager fragmentManager;
    private Car car;
    private DatabaseHandler databaseHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.car = (Car) getArguments().getSerializable("car");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_dialog, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        databaseHandler = new DatabaseHandler(getContext());
        builder.setMessage(buildMessage(car))
                .setTitle(R.string.changeActiveCar);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                List<Car> carList = databaseHandler.findAllCars();
                for (Car dbCar: carList) {
                    if (dbCar.getLicenseNo().equals(car.getLicenseNo())) {
                        if (dbCar.getManufacturer().equals(car.getManufacturer())) {
                            if (dbCar.getModel().equals(car.getModel())) {
                                if (dbCar.getMileage() == car.getMileage()) {
                                    databaseHandler.updateCarSetActive(dbCar.getId(), 1);
                                }
                            }
                        }
                    }
                    if (dbCar.getActive() == 1) {
                        databaseHandler.updateCarSetActive(dbCar.getId(), 0);
                    }
                }
                changeFragment(new CarsFragment());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                changeFragment(new CarsFragment());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return view;
    }

    private String buildMessage(Car car) {
        return "Are you sure that "
                + car.getManufacturer()
                + " "
                + car.getModel()
                + " with license no: "
                + car.getLicenseNo()
                + " should be the active car?";
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}