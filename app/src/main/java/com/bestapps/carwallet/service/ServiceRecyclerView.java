package com.bestapps.carwallet.service;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.model.ServiceEntry;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


class ServiceRecyclerView extends RecyclerView.Adapter<ServiceRecyclerView.MyViewHolder> {
    List<ServiceEntry> serviceEntries;

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(LinearLayout v) {
            super(v);
        }
    }

    public ServiceRecyclerView(List<ServiceEntry> serviceEntries) {
        this.serviceEntries = serviceEntries;
    }

    @Override
    public ServiceRecyclerView.MyViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_recycler_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return serviceEntries.size();
    }
}
