package com.bestapps.carwallet.cars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestapps.carwallet.R;

public class CustomSpinnerAdapter extends ArrayAdapter {

    String[] spinnerTitles;
    Integer[] spinnerImages;
    Context mContext;

    public CustomSpinnerAdapter(Context context, String[] titles, Integer[] images) {
        super(context, R.layout.custom_spinner_row);
        this.spinnerTitles = titles;
        this.spinnerImages = images;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return spinnerTitles.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.custom_spinner_row, parent, false);
            mViewHolder.image = (ImageView) convertView.findViewById(R.id.car_image);
            mViewHolder.carColor = (TextView) convertView.findViewById(R.id.car_color);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.image.setImageResource(spinnerImages[position]);
        mViewHolder.carColor.setText(spinnerTitles[position]);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }


    private static class ViewHolder {
        ImageView image;
        TextView carColor;


    }


}