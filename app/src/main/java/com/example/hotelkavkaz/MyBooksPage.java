package com.example.hotelkavkaz;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.hotelkavkaz.Model.Hotel;
import com.example.hotelkavkaz.View.BookingView;
import com.example.hotelkavkaz.View.HotelView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MyBooksPage extends AppCompatActivity {
    private ImageButton ReturnButton;

    DatabaseReference Bookings;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books_page);
        Bookings = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.UserLogin).child("Booking");
        ReturnButton = (ImageButton) findViewById(R.id.returnB);


        ReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfileIntent = new Intent(MyBooksPage.this, ProfilePage.class);
                startActivity(ProfileIntent);
            }
        });

        recyclerView = findViewById(R.id.myBooksRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Booking> options = new FirebaseRecyclerOptions.Builder<Booking>().setQuery(Bookings, Booking.class).build();
        FirebaseRecyclerAdapter<Booking, BookingView> adapter = new FirebaseRecyclerAdapter<Booking, BookingView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BookingView holder, int i, @NonNull Booking Booking) {
                holder.hotelTitle.setText(Booking.getHotelTitle());
                holder.dateIn.setText(Booking.getDateIn());
                holder.dateOut.setText(Booking.getDateOut());
                Picasso.get().load(Booking.getHotelImage()).into(holder.hotelImage);

                holder.infoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyBooksPage.this);
                        builder.setTitle("Информация о брони")
                                .setMessage("Отель: "+ Booking.getHotelTitle()+"\nТип номера: "+Booking.getType()+"\nДата въезда: "+ Booking.getDateIn()+ "\nДата съезда:"+ Booking.getDateOut()+"\nБассейн: "+Booking.getPool()+"\nСПА: "+Booking.getSpa()+"\nЦена: "+Booking.getPrice()+" рублей")
                                .setCancelable(false)
                                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        builder.create().show();
                    }
                });

                holder.rateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent hotelIntent = new Intent(MyBooksPage.this, RateWritePage.class);
                        hotelIntent.putExtra("hotelTitle", Booking.getHotelTitle());
                        Prevalent.CurrentHotelTitle = Booking.getHotelTitle();
                        Prevalent.CurrentHotelID = Booking.getHotelID();
                        Prevalent.CurrentHotelImage = Booking.getHotelImage();

                        ActivityOptions options1 = ActivityOptions.makeSceneTransitionAnimation((Activity) MyBooksPage.this, new Pair<View, String>(holder.hotelImage, "hotelImage"), new Pair<View, String>(holder.hotelTitle, "hotelTitle"));

                        startActivity(hotelIntent, options1.toBundle());
                    }
                });

                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyBooksPage.this);
                        builder.setTitle("Удаление брони")
                                .setMessage("Вы уверены, что хотите удалить бронь?")
                                .setCancelable(false)
                                .setNegativeButton("Да", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DatabaseReference itemRef = getRef(holder.getAdapterPosition());
                                        itemRef.removeValue();
                                    }
                                })
                                .setPositiveButton("Нет", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        builder.create().show();
                    }
                });

            }

            @NonNull
            @Override
            public BookingView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
                BookingView holder = new BookingView(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}