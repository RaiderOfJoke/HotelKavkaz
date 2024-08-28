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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.net.Uri;
import com.example.hotelkavkaz.Prevalent;
import com.example.hotelkavkaz.View.HotelView;
import com.example.hotelkavkaz.Model.Hotel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class HotelPage extends AppCompatActivity {

    private ImageButton returnButton;
    private Button BookingButton, showRating;
    DatabaseReference ProductsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_page);


        returnButton = (ImageButton) findViewById(R.id.returnB);
        BookingButton = (Button) findViewById(R.id.add_to_cart);
        showRating = (Button) findViewById(R.id.showRates);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HotelPage.this, HomePage.class);
                ActivityOptions options1 = ActivityOptions.makeSceneTransitionAnimation((Activity) HotelPage.this, new Pair<View, String>(v, "hotelImage"), new Pair<View, String>(v, "hotelTitle"));
                startActivity((HomeIntent), options1.toBundle());
            }
        });
        BookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BookingIntent = new Intent(HotelPage.this, BookingPage.class);
                startActivity(BookingIntent);
            }
        });

        showRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BookingIntent = new Intent(HotelPage.this, MyRatesPage.class);
                startActivity(BookingIntent);
            }
        });
        TextView hotelTitle = findViewById(R.id.HotelTitle);
        TextView hotelText = findViewById(R.id.HotelDescription);
        TextView hotelPrice = findViewById(R.id.hotelPrice);
        ImageView hotelImage = findViewById(R.id.hotelImage);


        hotelTitle.setText(getIntent().getStringExtra("hotelTitle"));
        hotelText.setText(getIntent().getStringExtra("hotelText"));
        hotelPrice.setText(getIntent().getStringExtra("hotelPrice"));
        Picasso.get().load(Prevalent.CurrentHotelImage).into(hotelImage);
    }
}
