package com.example.hotelkavkaz;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelkavkaz.Model.Hotel;
import com.example.hotelkavkaz.Model.Rate;
import com.example.hotelkavkaz.View.HotelView;
import com.example.hotelkavkaz.View.RateView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MyRatesPage extends AppCompatActivity {
    private ImageButton ReturnButton;

    DatabaseReference RatesRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rates_page);
        RatesRef = FirebaseDatabase.getInstance().getReference().child("Rating").child(Prevalent.CurrentHotelID);
        ReturnButton = (ImageButton) findViewById(R.id.returnB);


        ReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfileIntent = new Intent(MyRatesPage.this, HotelPage.class);
                ProfileIntent.putExtra("hotelTitle", Prevalent.CurrentHotelTitle);
                ProfileIntent.putExtra("hotelText", Prevalent.CurrentHotelText);
                ProfileIntent.putExtra("hotelPrice", Prevalent.CurrentHotelPrice);
                startActivity(ProfileIntent);
            }
        });


        recyclerView = findViewById(R.id.ratesRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Rate> options = new FirebaseRecyclerOptions.Builder<Rate>().setQuery(RatesRef, Rate.class).build();
        FirebaseRecyclerAdapter<Rate, RateView> adapter = new FirebaseRecyclerAdapter<Rate, RateView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RateView holder, int i, @NonNull Rate rate) {
                holder.hotelTitle.setText(rate.getUsername());
                holder.rateText.setText(rate.getStars());
                holder.personalRate.setText(rate.getTextRate());
            }

            @NonNull
            @Override
            public RateView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rate_item, parent, false);
                RateView holder = new RateView(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}