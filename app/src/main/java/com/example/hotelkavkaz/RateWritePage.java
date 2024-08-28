package com.example.hotelkavkaz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class RateWritePage extends AppCompatActivity {
    private ImageButton returnButton;
    private ImageView HotelImage;
    private TextView HotelTitle;
    private Button SendRate;
    private EditText RateText;
    private Spinner starsChoose;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rate_write_page);
        returnButton = (ImageButton) findViewById(R.id.returnB);
        SendRate = (Button) findViewById(R.id.rateButton);
        loading = new ProgressDialog(this);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(RateWritePage.this, MyBooksPage.class);
                startActivity(HomeIntent);
            }
        });

        SendRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateList();
            }
        });

        HotelTitle = (TextView) findViewById(R.id.HotelTitle);
        HotelImage = (ImageView) findViewById(R.id.hotelImage);
        RateText = (EditText) findViewById(R.id.editRate);
        starsChoose = (Spinner) findViewById(R.id.starsChoose);
        HotelTitle.setText(getIntent().getStringExtra("hotelTitle"));
        Picasso.get().load(Prevalent.CurrentHotelImage).into(HotelImage);

    }
    private void rateList()
    {
        String textRate = RateText.getText().toString();
        Random rn = new Random();
        int RateId = rn.nextInt(10000-1+1) + 1;
        String RateRandomId = Integer.toString(RateId);

        String stars = starsChoose.getSelectedItem().toString();

        if(TextUtils.isEmpty(textRate))
        {
            Toast.makeText(this, "Вы не написали отзыв", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.equals(stars, "-")){
            Toast.makeText(this, "Вы оставили оценку", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loading.setTitle("Отправляем отзыв");
            loading.setMessage("Пожалуйста, подождите");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            setRate(textRate, stars, RateRandomId);
        }
    }
    private void setRate(String textRate,String stars, String RateRandomId)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> userDataMap = new HashMap<>();
                userDataMap.put("textRate", textRate);
                userDataMap.put("stars", stars);
                userDataMap.put("title", Prevalent.CurrentHotelTitle);
                userDataMap.put("username", Prevalent.UserLogin);
                userDataMap.put("bookID", RateRandomId);

                RootRef.child("Rating").child(Prevalent.CurrentHotelID).child(RateRandomId).updateChildren(userDataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    loading.dismiss();
                                    Toast.makeText(RateWritePage.this,"Отзыв оставлен!",Toast.LENGTH_SHORT).show();
                                    Intent loginIntent = new Intent(RateWritePage.this,MyRatesPage.class);
                                    startActivity(loginIntent);
                                }
                                else {
                                    loading.dismiss();
                                    Toast.makeText(RateWritePage.this,"Ошибка :(",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}