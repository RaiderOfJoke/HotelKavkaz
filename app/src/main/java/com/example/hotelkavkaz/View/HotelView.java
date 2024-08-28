package com.example.hotelkavkaz.View;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelkavkaz.ItemClickListener;
import com.example.hotelkavkaz.R;

public class HotelView extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView titleHotel, priceHotel, textHotel;
    public ImageView imageView;
    public Button moreButton;
    public ItemClickListener listener;

    public HotelView(View itemView)
    {
        super (itemView);

        imageView = itemView.findViewById(R.id.gameImage);
        titleHotel = itemView.findViewById(R.id.hotelTitle);
        priceHotel = itemView.findViewById(R.id.Hotel_Price);
        moreButton = itemView.findViewById(R.id.more);
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
