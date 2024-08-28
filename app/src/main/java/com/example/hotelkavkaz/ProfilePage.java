package com.example.hotelkavkaz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelkavkaz.Model.Booking;
import com.example.hotelkavkaz.Model.Rate;
import com.example.hotelkavkaz.View.BookingView;
import com.example.hotelkavkaz.View.RateView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class ProfilePage extends AppCompatActivity {
    private ImageButton HomeButton, RateButton;
    private Button Exit, MyBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        HomeButton = (ImageButton) findViewById(R.id.home);
        RateButton = (ImageButton) findViewById(R.id.rate);
        Exit = (Button) findViewById(R.id.exit);
        MyBooks = (Button) findViewById(R.id.mybookings);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfileIntent = new Intent(ProfilePage.this, HomePage.class);
                startActivity(ProfileIntent);
            }
        });
        RateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RateIntent = new Intent(ProfilePage.this, InfoPage.class);
                startActivity(RateIntent);
            }
        });
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Intent CartIntent = new Intent(ProfilePage.this, GreetingPage.class);
                startActivity(CartIntent);
            }
        });
        MyBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyRatesIntent = new Intent(ProfilePage.this, MyBooksPage.class);
                startActivity(MyRatesIntent);
            }
        });
    }
}