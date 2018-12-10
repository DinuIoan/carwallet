package com.bestapps.carwallet.service;

import androidx.recyclerview.widget.RecyclerView;

interface RecyclerItemTouchHelperListener {
    void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
