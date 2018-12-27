package com.bestapps.carwallet.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestapps.carwallet.R;
import com.bestapps.carwallet.alertdialog.ClearAlertDialog;
import com.bestapps.carwallet.alertdialog.ClearAlertDialogMaintenance;
import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.Maintenance;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class MaintenanceRecyclerView extends RecyclerView.Adapter<MaintenanceRecyclerView.MyViewHolder> {
private List<Maintenance> maintenanceList;
    private FragmentManager fragmentManager;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView date;
    public TextView hour;
    private ImageView alarm;
    private ImageView deleteButton;


    public MyViewHolder(FrameLayout v) {
        super(v);
        title = v.findViewById(R.id.recycler_view_item_title);
        date = v.findViewById(R.id.date);
        hour = v.findViewById(R.id.hour);
        alarm = v.findViewById(R.id.alarm_on_off);
        deleteButton = v.findViewById(R.id.delete_notification);
    }
}

    public MaintenanceRecyclerView(List<Maintenance> maintenanceList, FragmentManager fragmentManager) {
        this.maintenanceList = maintenanceList;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MaintenanceRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.maintenance_recycler_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        if (maintenanceList.get(i).isNotificationActive() == 1) {
            myViewHolder.alarm.setImageResource(R.drawable.ic_alarm_on);
        } else {
            myViewHolder.alarm.setImageResource(R.drawable.ic_alarm_off);
        }
        myViewHolder.title.setText(maintenanceList.get(i).getTitle());
        myViewHolder.date.setText(maintenanceList.get(i).getDate());
        myViewHolder.hour.setText(maintenanceList.get(i).getHour() + ":" + maintenanceList.get(i).getMin());
        myViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ClearAlertDialogMaintenance(), maintenanceList.get(i));
            }
        });
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new MaintenanceItemFragment(), maintenanceList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return maintenanceList.size();
    }


    private void changeFragment(Fragment fragment, Maintenance maintenance) {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("maintenance", maintenance);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }

}