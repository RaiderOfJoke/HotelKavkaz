package com.example.hotelkavkaz.View;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelkavkaz.ItemClickListener;
import com.example.hotelkavkaz.R;

public class RateView extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView hotelTitle, rateText, personalRate;
    public Button infoButton;
    public ItemClickListener listener;

    public RateView(View itemView)
    {
        super (itemView);

        hotelTitle = itemView.findViewById(R.id.hotelTitle);
        rateText = itemView.findViewById(R.id.personalRate);
        infoButton = itemView.findViewById(R.id.info);
        personalRate = itemView.findViewById(R.id.textRate);
    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
