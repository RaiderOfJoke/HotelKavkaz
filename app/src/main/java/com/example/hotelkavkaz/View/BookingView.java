package com.example.hotelkavkaz.View;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelkavkaz.ItemClickListener;
import com.example.hotelkavkaz.R;

public class BookingView extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView hotelTitle, dateIn, dateOut;
    public ImageView hotelImage;
    public Button infoButton, rateButton, deleteButton;
    public ItemClickListener listener;

    public BookingView(View itemView)
    {
        super (itemView);

        hotelTitle = itemView.findViewById(R.id.hotelTitle);
        dateIn = itemView.findViewById(R.id.dateIn);
        hotelImage = itemView.findViewById(R.id.hotelImage);
        infoButton = itemView.findViewById(R.id.info);
        rateButton = itemView.findViewById(R.id.rate);
        deleteButton = itemView.findViewById(R.id.delete);
        dateOut = itemView.findViewById(R.id.dateOut);
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
