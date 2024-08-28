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
import com.example.hotelkavkaz.View.HotelView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomePage extends AppCompatActivity {
    private ImageButton ProfileButton, RateButton;

    DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Hotels");

        ProfileButton = (ImageButton) findViewById(R.id.profile);
        RateButton = (ImageButton) findViewById(R.id.rate);

        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfileIntent = new Intent(HomePage.this, ProfilePage.class);
                startActivity(ProfileIntent);
            }
        });
        RateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CartIntent = new Intent(HomePage.this, InfoPage.class);
                startActivity(CartIntent);
            }
        });

        recyclerView = findViewById(R.id.hotelRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Hotel> options = new FirebaseRecyclerOptions.Builder<Hotel>().setQuery(ProductsRef, Hotel.class).build();

        FirebaseRecyclerAdapter<Hotel, HotelView> adapter = new FirebaseRecyclerAdapter<Hotel, HotelView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HotelView holder, int i, @NonNull Hotel hotel) {
                holder.titleHotel.setText(hotel.getTitle());
                holder.priceHotel.setText(hotel.getPrice());
                Picasso.get().load(hotel.getImage()).into(holder.imageView);
                holder.moreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent hotelIntent = new Intent(HomePage.this, HotelPage.class);
                        hotelIntent.putExtra("hotelTitle", hotel.getTitle());
                        hotelIntent.putExtra("hotelText", hotel.getText());
                        hotelIntent.putExtra("hotelPrice", hotel.getPrice());
                        Prevalent.CurrentHotelTitle = hotel.getTitle();
                        Prevalent.CurrentHotelID = hotel.getId();
                        Prevalent.CurrentHotelPrice = hotel.getPrice();
                        Prevalent.CurrentHotelImage = hotel.getImage();
                        Prevalent.CurrentHotelText = hotel.getText();

                        ActivityOptions options1 = ActivityOptions.makeSceneTransitionAnimation((Activity) HomePage.this, new Pair<View, String>(holder.imageView, "hotelImage"), new Pair<View, String>(holder.titleHotel, "hotelTitle"));

                        startActivity(hotelIntent, options1.toBundle());
                    }
                });
            }

            @NonNull
            @Override
            public HotelView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item, parent, false);
                HotelView holder = new HotelView(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}