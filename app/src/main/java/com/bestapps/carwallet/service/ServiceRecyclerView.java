package com.bestapps.carwallet.service;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.model.ServiceEntry;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


class ServiceRecyclerView extends RecyclerView.Adapter<ServiceRecyclerView.MyViewHolder> {
    private List<ServiceEntry> serviceEntries;
    private FragmentManager fragmentManager;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;
        public RelativeLayout background, foreground;

        public MyViewHolder(FrameLayout v) {
            super(v);
            title = v.findViewById(R.id.recycler_view_item_title);
            background = v.findViewById(R.id.view_background);
            foreground = v.findViewById(R.id.view_foreground);
            date = v.findViewById(R.id.date);
        }

    }

    public ServiceRecyclerView(List<ServiceEntry> serviceEntries, FragmentManager fragmentManager) {
        this.serviceEntries = serviceEntries;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ServiceRecyclerView.MyViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_recycler_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        String mileageText = "" + serviceEntries.get(i).getMileage() + " km";
        String priceText = "" + serviceEntries.get(i).getPrice() + "$";
        myViewHolder.title.setText(serviceEntries.get(i).getTitle());
        myViewHolder.date.setText(serviceEntries.get(i).getDate());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ServiceEntryFragment(), serviceEntries.get(i));
            }
        });
    }

    public void removeItem(int position) {
        serviceEntries.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(ServiceEntry item, int position) {
        serviceEntries.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return serviceEntries.size();
    }

    private void changeFragment(Fragment fragment, ServiceEntry serviceEntry) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("serviceEntry", serviceEntry);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }

}
