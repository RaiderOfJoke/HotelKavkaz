package com.example.hotelkavkaz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hotelkavkaz.Model.users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginPage extends AppCompatActivity {

    private Button loginButton, registerReturnButton;
    private ImageView Joker;
    private EditText loginInput, passwordInput;
    private ProgressDialog loading;
    private String parentDnName = "Users";
    private CheckBox rememberCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        loginButton = (Button)findViewById(R.id.main_login_btn);
        loginInput = (EditText)findViewById(R.id.login_input);
        passwordInput = (EditText)findViewById(R.id.password_input);
        registerReturnButton = (Button)findViewById(R.id.regist_return);
        Joker = (ImageView)findViewById(R.id.joker);
        loading = new ProgressDialog(this);
        rememberCheck = (CheckBox) findViewById(R.id.rememberMe);

        Paper.init(this);

        registerReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnLoginIntent = new Intent(LoginPage.this,RegisterPage.class);
                startActivity(returnLoginIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String login = loginInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(TextUtils.isEmpty(login))
        {
            Toast.makeText(this, "Вы не ввели логин", Toast.LENGTH_SHORT).show();
        }
        else if (login.equals("joker"))
        {
            Joker.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.INVISIBLE);
            //exitProgram();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Вы не ввели пароль", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loading.setTitle("Вход в аккаунт");
            loading.setMessage("Пожалуйста, подождите");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            ValidationUser(login, password);
        }
    }

    private void exitProgram() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(1);
    }

    private void ValidationUser(String login, String password) {

        if (rememberCheck.isChecked()){
            Paper.book().write(Prevalent.UserLogin, login);
            Paper.book().write(Prevalent.Password, password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDnName).child(login).exists())
                {
                    users usersData = dataSnapshot.child(parentDnName).child(login).getValue(users.class);

                    if (usersData.getLogin().equals(login))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            loading.dismiss();
                            Toast.makeText(LoginPage.this,"Вход выполнен",Toast.LENGTH_SHORT).show();
                            Prevalent.UserLogin = login;
                            Intent shopIntent = new Intent(LoginPage.this,HomePage.class);
                            startActivity(shopIntent);
                        }
                        else {
                            loading.dismiss();
                            Toast.makeText(LoginPage.this,"Неверный пароль",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    loading.dismiss();
                    Toast.makeText(LoginPage.this, "Такого логина не существует", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}