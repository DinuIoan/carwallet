package com.bestapps.carwallet.maintenance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.model.Maintenance;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MaintenanceRecyclerView extends RecyclerView.Adapter<MaintenanceRecyclerView.MyViewHolder> {
private List<Maintenance> maintenanceList;


public static class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView description;
    public TextView price;
    public TextView mileage;
    public TextView date;
    private ImageView alarm;
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
        alarm = v.findViewById(R.id.alarm_on_off);
    }
}

    public MaintenanceRecyclerView(List<Maintenance> maintenanceList) {
        this.maintenanceList = maintenanceList;
    }

    @Override
    public MaintenanceRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.maintenance_recycler_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        String mileageText = "";
        if (maintenanceList.get(i).getMileage() != 0) {
           mileageText = "" + maintenanceList.get(i).getMileage() + " km";
        }
        if (maintenanceList.get(i).isNotificationActive() == 1) {
            myViewHolder.alarm.setImageResource(R.drawable.ic_alarm_on);
        } else {
            myViewHolder.alarm.setImageResource(R.drawable.ic_alarm_off);
        }
        String priceText = "" + maintenanceList.get(i).getPrice() + "$";
        myViewHolder.title.setText(maintenanceList.get(i).getTitle());
        if (maintenanceList.get(i).getDescription().isEmpty()) {
            myViewHolder.description.setVisibility(View.GONE);
        } else {
            myViewHolder.description.setVisibility(View.VISIBLE);
            myViewHolder.description.setText(maintenanceList.get(i).getDescription());
        }
        myViewHolder.mileage.setText(mileageText);
        myViewHolder.price.setText(priceText);
        myViewHolder.date.setText(maintenanceList.get(i).getDate());
    }

    public void removeItem(int position) {
        maintenanceList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Maintenance item, int position) {
        maintenanceList.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return maintenanceList.size();
    }
}