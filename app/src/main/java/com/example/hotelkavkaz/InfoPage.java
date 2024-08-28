package com.example.hotelkavkaz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InfoPage extends AppCompatActivity {
    private ImageButton ProfileButton, HomeButton;
    private Button InfoButton, appInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        ProfileButton = (ImageButton) findViewById(R.id.profile);
        HomeButton = (ImageButton) findViewById(R.id.home);
        InfoButton = (Button)findViewById(R.id.showDevelopInfo);
        appInfoButton = (Button)findViewById(R.id.showAppInfo);

        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfileIntent = new Intent(InfoPage.this, ProfilePage.class);
                startActivity(ProfileIntent);
            }
        });
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CartIntent = new Intent(InfoPage.this, HomePage.class);
                startActivity(CartIntent);
            }
        });

        InfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoPage.this);
                builder.setTitle("Разработчик")
                        .setMessage("Милованова Татьяна Витальевна")
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

        appInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoPage.this);
                builder.setTitle("О приложении")
                        .setMessage("Приложение “Бронирование мест в гостинице «Кавказ»” предоставляет возможность пользователям забронировать номер и услугу, связаться с персоналом и оставить отзыв. \nВерсия: 1.0 ")
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
    }
}