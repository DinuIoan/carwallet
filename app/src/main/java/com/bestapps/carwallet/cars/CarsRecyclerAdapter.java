package com.bestapps.carwallet.cars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.alertdialog.ClearAlertDialog;
import com.bestapps.carwallet.alertdialog.SetActiveAlertDialogFragment;
import com.bestapps.carwallet.database.DatabaseHandler;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.ParametersSettings;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class CarsRecyclerAdapter
        extends RecyclerView.Adapter<CarsRecyclerAdapter.MyViewHolder>  {
    private List<Car> cars;
    private DatabaseHandler databaseHandler;
    private FragmentManager fragmentManager;
    public String distance;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView manufacturer;
        public TextView model;
        public TextView shape;
        public TextView engine;
        public TextView power;
        public TextView year;
        public TextView vin;
        public TextView mileage;
        public TextView fuelType;
        public TextView licenseNo;
        public ImageView image;

        public Button editButton;
        public Button setActiveButton;
        public Button clearButton;



        public MyViewHolder(LinearLayout v) {
            super(v);
            manufacturer = v.findViewById(R.id.manufacturer);
            model = v.findViewById(R.id.model);
            shape = v.findViewById(R.id.shape);
            engine = v.findViewById(R.id.engine);
            power = v.findViewById(R.id.power);
            year = v.findViewById(R.id.year);
            vin = v.findViewById(R.id.vin);
            mileage = v.findViewById(R.id.mileage);
            fuelType = v.findViewById(R.id.fuel_type);
            licenseNo = v.findViewById(R.id.license_no);
            image = v.findViewById(R.id.image);
            editButton = v.findViewById(R.id.edit_button);
            setActiveButton = v.findViewById(R.id.set_active_button);
            clearButton = v.findViewById(R.id.clear);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CarsRecyclerAdapter(List<Car> cars,
                               DatabaseHandler databaseHandler,
                               FragmentManager fragmentManager,
                               ParametersSettings parametersSettings) {
        this.cars = cars;
        this.databaseHandler = databaseHandler;
        this.fragmentManager = fragmentManager;
        this.distance = parametersSettings.getDistance();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CarsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cars_recycler_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String shapeText = holder.shape.getText() +
                cars.get(position).getShape();
        String mileageText = holder.mileage.getText() + "" +
                cars.get(position).getMileage() + " " + distance;
        String vinText = holder.vin.getText() +
                cars.get(position).getVin();
        String yearText = holder.year.getText() + "" +
                cars.get(position).getYear();
        String fuelTypeText = holder.fuelType.getText() +
                cars.get(position).getFuelType();
        String engineText = holder.engine.getText() +
                cars.get(position).getEngine() + " cmc";
        String powerText = holder.power.getText() + "" +
                cars.get(position).getPower() + " CP";
        String licenseNoText = holder.licenseNo.getText() +
                cars.get(position).getLicenseNo();
        holder.manufacturer.setText(cars.get(position).getManufacturer());
        holder.model.setText(cars.get(position).getModel());
        holder.shape.setText(shapeText);
        holder.mileage.setText(mileageText);
        holder.vin.setText(vinText);
        holder.year.setText(yearText);
        holder.fuelType.setText(fuelTypeText);
        holder.engine.setText(engineText);
        holder.power.setText(powerText);
        holder.licenseNo.setText(licenseNoText);
        holder.image.setImageResource(cars.get(position).getImage());
        if (cars.get(position).getActive() == 1) {
            holder.setActiveButton.setBackgroundColor(R.drawable.button_car_card_set_active);
            holder.setActiveButton.setEnabled(false);
            holder.setActiveButton.setText(R.string.active);
        } else {
            holder.setActiveButton.setText(R.string.set_active);
        }
        holder.setActiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new SetActiveAlertDialogFragment(), cars.get(position));

            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new AddCarFragment(), cars.get(position));
            }
        });

        holder.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ClearAlertDialog(), cars.get(position));
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cars.size();
    }

    private void changeFragment(Fragment fragment, Car car) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("car", car);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}