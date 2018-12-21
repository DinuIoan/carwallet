package com.bestapps.carwallet.service;

import android.graphics.Canvas;
import android.view.View;

import com.bestapps.carwallet.maintenance.MaintenanceRecyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemTouchHelper  extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;


    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (listener != null) {
            listener.onSwipe(viewHolder, direction, viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foregroundView = null;
        if (viewHolder instanceof ServiceRecyclerView.MyViewHolder) {
            foregroundView = ((ServiceRecyclerView.MyViewHolder) viewHolder).foreground;
        } else if (viewHolder instanceof MaintenanceRecyclerView.MyViewHolder) {
            foregroundView = ((MaintenanceRecyclerView.MyViewHolder) viewHolder).foreground;
        }
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView = null;
        if (viewHolder instanceof ServiceRecyclerView.MyViewHolder) {
            foregroundView = ((ServiceRecyclerView.MyViewHolder) viewHolder).foreground;
        } else if (viewHolder instanceof MaintenanceRecyclerView.MyViewHolder) {
            foregroundView = ((MaintenanceRecyclerView.MyViewHolder) viewHolder).foreground;
        }
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView = null;
        if (viewHolder instanceof ServiceRecyclerView.MyViewHolder) {
            foregroundView = ((ServiceRecyclerView.MyViewHolder) viewHolder).foreground;
        } else if (viewHolder instanceof MaintenanceRecyclerView.MyViewHolder) {
            foregroundView = ((MaintenanceRecyclerView.MyViewHolder) viewHolder).foreground;
        }
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null) {
            View foregroundView = null;
            if (viewHolder instanceof ServiceRecyclerView.MyViewHolder) {
                foregroundView = ((ServiceRecyclerView.MyViewHolder) viewHolder).foreground;
            } else if (viewHolder instanceof MaintenanceRecyclerView.MyViewHolder) {
                foregroundView = ((MaintenanceRecyclerView.MyViewHolder) viewHolder).foreground;
            }
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}