package com.bestapps.carwallet.maintenance;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    public RelativeLayout background, foreground;

    public MyViewHolder(FrameLayout v) {
        super(v);
        title = v.findViewById(R.id.recycler_view_item_title);
        description = v.findViewById(R.id.recycler_view_item_description);
        price = v.findViewById(R.id.recycler_view_item_price);
        mileage = v.findViewById(R.id.recycler_view_item_mileage);
        background = v.findViewById(R.id.view_background);
        foreground = v.findViewById(R.id.view_foreground);
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
        String mileageText = "Mileage: " + maintenanceList.get(i).getMileage() + " km";
        String priceText = "" + maintenanceList.get(i).getMileage() + "$";
        myViewHolder.title.setText(maintenanceList.get(i).getTitle());
        myViewHolder.description.setText(maintenanceList.get(i).getDescription());
        myViewHolder.mileage.setText(mileageText);
        myViewHolder.price.setText(priceText);
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