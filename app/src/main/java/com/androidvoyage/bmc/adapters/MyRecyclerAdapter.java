package com.androidvoyage.bmc.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidvoyage.bmc.R;

/**
 * Created by appstartmac2 on 13/10/17.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private Context context;
    private String tempData;
    private int count1 = 0,count2 = 0,count3 = 0;

    public MyRecyclerAdapter(Context context) {
        this.context = context;
        tempData = context.getResources().getString(R.string.str_data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (count1 > tempData.length()) {
            count1 = 0;
        }

        if (count2 > tempData.length()) {
            count2 = 0;
        }

        if (count3 > tempData.length()) {
            count3 = 0;
        }

        holder.tvStart.setText(tempData.substring(count1 == 0 ? count1 : count1 + 1));
        holder.tvEnd.setText(tempData.substring(count2 == 0 ? count2 : count2 + 1));
        holder.tvCenter.setText(tempData.substring(count3 == 0 ? count3 : count3 + 1));

        count1 = count1 + getVisibleCount(holder.tvStart);
        count2 = count2 + getVisibleCount(holder.tvCenter);
        count3 = count3 + getVisibleCount(holder.tvEnd);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FAZAL", "onClick: count1 = "+getVisibleCount(holder.tvStart));
                Log.d("FAZAL", "onClick: count2 = "+getVisibleCount(holder.tvCenter));
                Log.d("FAZAL", "onClick: count3 = "+getVisibleCount(holder.tvEnd));
            }
        });

    }

    private int getVisibleCount(TextView textView) {
        return textView.getOffsetForPosition(textView.getWidth(), textView.getHeight());
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvStart, tvEnd, tvCenter;

        public ViewHolder(View view) {
            super(view);
            tvStart = view.findViewById(R.id.tv_start);
            tvEnd = view.findViewById(R.id.tv_end);
            tvCenter = view.findViewById(R.id.tv_center);
        }
    }

}

