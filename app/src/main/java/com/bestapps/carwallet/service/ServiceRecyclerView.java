package com.bestapps.carwallet.service;


import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.model.ServiceEntry;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


class ServiceRecyclerView extends RecyclerView.Adapter<ServiceRecyclerView.MyViewHolder> {
    private List<ServiceEntry> serviceEntries;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public TextView price;
        public TextView mileage;
        public TextView date;
        public RelativeLayout background, foreground;

        public MyViewHolder(FrameLayout v) {
            super(v);
            title = v.findViewById(R.id.recycler_view_item_title);
            description = v.findViewById(R.id.recycler_view_item_description);
            price = v.findViewById(R.id.recycler_view_item_price);
            mileage = v.findViewById(R.id.recycler_view_item_mileage);
            background = v.findViewById(R.id.view_background);
            foreground = v.findViewById(R.id.view_foreground);
            date = v.findViewById(R.id.date);
        }

    }

    public ServiceRecyclerView(List<ServiceEntry> serviceEntries) {
        this.serviceEntries = serviceEntries;
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
        myViewHolder.description.setText(serviceEntries.get(i).getDescription());
        myViewHolder.mileage.setText(mileageText);
        myViewHolder.price.setText(priceText);
        myViewHolder.date.setText(serviceEntries.get(i).getDate());
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
}
