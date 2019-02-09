package com.bestapps.carwallet.trip;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.model.ParametersSettings;
import com.bestapps.carwallet.model.TripData;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TripRecyclerView extends RecyclerView.Adapter<TripRecyclerView.MyViewHolder> {
    private List<TripData> tripDataList;
    private String currency;
    private String distance;
    private String volume;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout background, foreground;
        public TextView from;
        public TextView to;
        public TextView avarageConsumption;
        public TextView distance;
        public TextView fuelPrice;
        public TextView totalPrice;
        public TextView totalLiters;
        public MyViewHolder(FrameLayout v) {
            super(v);
            background = v.findViewById(R.id.view_background);
            foreground = v.findViewById(R.id.view_foreground);
            from = v.findViewById(R.id.from);
            to = v.findViewById(R.id.to);
            avarageConsumption= v.findViewById(R.id.avarage_consumption);
            distance = v.findViewById(R.id.distance);
            fuelPrice = v.findViewById(R.id.fuel_price);
            totalPrice = v.findViewById(R.id.total_price);
            totalLiters = v.findViewById(R.id.total_liters);
        }

    }
    public TripRecyclerView(List<TripData> tripDataList, ParametersSettings parametersSettings) {
        this.tripDataList = tripDataList;
        this.currency = parametersSettings.getCurrency();
        this.distance = parametersSettings.getDistance();
        this.volume = parametersSettings.getVolume();
    }

    @Override
    public TripRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_recycler_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.from.setText("From: " + tripDataList.get(i).getFromLocation());
        myViewHolder.to.setText("To: " + tripDataList.get(i).getToLocation());
        myViewHolder.avarageConsumption.setText(
                "Avarage consumption: " + tripDataList.get(i).getAvarageConsumption());
        myViewHolder.distance.setText("Distance: " + tripDataList.get(i).getDistance() + distance);
        myViewHolder.fuelPrice.setText("Fuel price: " + tripDataList.get(i).getFuelPrice() + currency);
        myViewHolder.totalPrice.setText("Total price: " + tripDataList.get(i).getTotalPrice() + currency);
        myViewHolder.totalLiters.setText("Total liters: " + tripDataList.get(i).getTotalLiters() + volume);
    }

    public void removeItem(int position) {
        tripDataList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return tripDataList.size();
    }
}
