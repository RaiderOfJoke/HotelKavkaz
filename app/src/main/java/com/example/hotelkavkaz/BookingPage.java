package com.example.hotelkavkaz;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import io.paperdb.Paper;

public class BookingPage extends AppCompatActivity {
    private Button orderButton;
    private ImageButton returnButton;
    private EditText dateInInput, dateOutInput, cardNumber, dateLimit, cvcNumber;
    private Spinner guestNumberInput, typeInput;
    private CheckBox SPA, pool;
    private ProgressDialog loading;
    private String parentDnName = "Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

        orderButton = (Button) findViewById(R.id.orderButton);
        returnButton = (ImageButton) findViewById(R.id.returnB);
        dateInInput = (EditText) findViewById(R.id.dateIn);
        dateOutInput = (EditText) findViewById(R.id.dateOut);
        cardNumber = (EditText) findViewById(R.id.cardNumber);
        dateLimit = (EditText) findViewById(R.id.dateLimit);
        cvcNumber = (EditText) findViewById(R.id.CVC);
        typeInput = (Spinner) findViewById(R.id.type);
        guestNumberInput = (Spinner) findViewById(R.id.guestNumber);
        SPA = (CheckBox) findViewById(R.id.SPA);
        pool = (CheckBox) findViewById(R.id.Pool);

        loading = new ProgressDialog(this);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat format1 = new SimpleDateFormat("MM-yyyy", Locale.getDefault());
        setDatePickerDialog(this, dateInInput, format);
        setDatePickerDialog(this, dateOutInput, format);
        setDatePickerDialog(this,dateLimit,format1);


        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(BookingPage.this, HotelPage.class);
                HomeIntent.putExtra("hotelTitle", Prevalent.CurrentHotelTitle);
                HomeIntent.putExtra("hotelText", Prevalent.CurrentHotelText);
                HomeIntent.putExtra("hotelPrice", Prevalent.CurrentHotelPrice);
                startActivity(HomeIntent);
            }
        });
    }

    private void orderList()
    {
        String dateIn = dateInInput.getText().toString();
        String dateOut = dateOutInput.getText().toString();
        String card = cardNumber.getText().toString();
        String dateL = dateLimit.getText().toString();
        String cvc = cvcNumber.getText().toString();
        String poolText = "Нет";
        String spaText = "Нет";

        Random rn = new Random();
        int ProductId = rn.nextInt(10000-1+1) + 1;
        String ProductRandomId = Integer.toString(ProductId);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat format1 = new SimpleDateFormat("MM-yyyy", Locale.getDefault());
        Date dateInCorrect;
        Date dateOutCorrect;
        Date dateLimitCorrect;
        LocalDate currentDate = LocalDate.now();
        try {
            dateInCorrect = format.parse(dateIn);
            dateOutCorrect = format.parse(dateOut);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Пожалуйста, введите дату в формате dd-MM-yyyy", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            dateLimitCorrect = format1.parse(dateL);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Пожалуйста, введите дату в формате MM-yyyy", Toast.LENGTH_LONG).show();
            return;
        }
        LocalDate selectedDateIn = dateInCorrect.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate selectedDateOut = dateOutCorrect.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate selectedDateLimit = dateLimitCorrect.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        String type = typeInput.getSelectedItem().toString();
        String guestNumber = guestNumberInput.getSelectedItem().toString();

        if (pool.isChecked())
        {
            poolText = "Да";
        }
        if (SPA.isChecked())
        {
            spaText = "Да";
        }
        if(TextUtils.isEmpty(dateIn))
        {
            Toast.makeText(this, "Вы не ввели дату въезда", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(dateOut))
        {
            Toast.makeText(this, "Вы не ввели дату съезда", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(card))
        {
            Toast.makeText(this, "Вы не ввели номер карты", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(dateL))
        {
            Toast.makeText(this, "Вы не ввели дату срока карты", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cvc))
        {
            Toast.makeText(this, "Вы не ввели CVC код", Toast.LENGTH_SHORT).show();
        }
        else if((currentDate.isAfter(selectedDateIn))||(currentDate.isAfter(selectedDateOut))){
            Toast.makeText(this, "Вы ввели прошедшую дату", Toast.LENGTH_SHORT).show();
        }
        else if (selectedDateOut.isBefore(selectedDateIn)){
            Toast.makeText(this, "Дата въезда не может быть позже даты съезда", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loading.setTitle("Создание заказа");
            loading.setMessage("Пожалуйста, подождите");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            setOrder(type, guestNumber, dateIn, dateOut, poolText, spaText, ProductRandomId);
        }
    }

    private void setOrder(String type, String guestNumber, String dateIn,String dateOut, String poolText, String spaText, String ProductRandomId)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> userDataMap = new HashMap<>();
                userDataMap.put("type", type);
                userDataMap.put("guestNumber", guestNumber);
                userDataMap.put("dateIn", dateIn);
                userDataMap.put("dateOut", dateOut);
                userDataMap.put("pool", poolText);
                userDataMap.put("spa", spaText);
                userDataMap.put("hotelTitle", Prevalent.CurrentHotelTitle);
                userDataMap.put("hotelID", Prevalent.CurrentHotelID);
                userDataMap.put("Price", Prevalent.CurrentHotelPrice);
                userDataMap.put("hotelImage", Prevalent.CurrentHotelImage);

                RootRef.child("Users").child(Prevalent.UserLogin).child("Booking").child(ProductRandomId).updateChildren(userDataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    loading.dismiss();
                                    Toast.makeText(BookingPage.this,"Номер забронирован",Toast.LENGTH_SHORT).show();
                                    Intent loginIntent = new Intent(BookingPage.this,ProfilePage.class);
                                    startActivity(loginIntent);
                                }
                                else {
                                    loading.dismiss();
                                    Toast.makeText(BookingPage.this,"Ошибка :(",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setDatePickerDialog(Context context, EditText editText, SimpleDateFormat format) {
        editText.setFocusable(false);
        editText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    (datePicker, year, month, day) -> {
                        calendar.set(year, month, day);
                        String date = format.format(calendar.getTime());
                        editText.setText(date);
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }
}