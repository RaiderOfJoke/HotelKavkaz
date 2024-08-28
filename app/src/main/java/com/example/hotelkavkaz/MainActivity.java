package com.example.hotelkavkaz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelkavkaz.Model.users;
import com.example.hotelkavkaz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        String Username = Paper.book().read(Prevalent.UserLogin);
        String Password = Paper.book().read(Prevalent.Password);

        if (Username != "" && Password != "")
        {
            if (!TextUtils.isEmpty(Username) && !TextUtils.isEmpty(Password))
            {
                ValidateUser(Username, Password);
            }
            else {
                startActivity(new Intent(this, GreetingPage.class));
            }
        }
    }
    private void ValidateUser(final String login, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(login).exists()) {
                    users usersData = dataSnapshot.child("Users").child(login).getValue(users.class);

                    if (usersData.getLogin().equals(login)) {
                        if (usersData.getPassword().equals(password)) {
                            Prevalent.UserLogin = login;
                            Intent shopIntent = new Intent(MainActivity.this, HomePage.class);
                            startActivity(shopIntent);
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Такого логина не существует", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}