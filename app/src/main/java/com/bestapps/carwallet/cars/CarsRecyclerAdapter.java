package com.bestapps.carwallet.cars;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.model.Car;

import java.util.List;

public class CarsRecyclerAdapter
        extends RecyclerView.Adapter<CarsRecyclerAdapter.MyViewHolder>  {
    private List<Car> cars;
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
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CarsRecyclerAdapter(List<Car> cars) {
        this.cars = cars;
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.manufacturer.setText(cars.get(position).getManufacturer());
        holder.model.setText(cars.get(position).getModel());
        holder.shape.setText(cars.get(position).getShape());
        holder.mileage.setText(cars.get(position).getMileage() + " km");
        holder.vin.setText(cars.get(position).getVin());
        holder.year.setText(cars.get(position).getYear());
        holder.fuelType.setText(cars.get(position).getFuelType());
        holder.engine.setText(cars.get(position).getEngine() + " l");
        holder.power.setText(cars.get(position).getPower() + " CP");
        holder.licenseNo.setText(cars.get(position).getLicenseNo());
        holder.image.setImageResource(cars.get(position).getImage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cars.size();
    }
}