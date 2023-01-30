package com.example.booking;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.TimeZone;

public class MyDatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText editText;
    private int day;
    private int month;
    private int year;
    private Context context;

    public MyDatePicker(){}

    public MyDatePicker(Context context, int editTextId){
        Activity activity = (Activity) context;
        this.editText = (EditText) activity.findViewById(editTextId);
        this.editText.setOnClickListener(this);
        this.context=context;
    }


    @Override
    public void onClick(View v) {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(context,R.style.DialogTheme,this, calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
        updateDisplay();
    }


    private void updateDisplay() {

        editText.setText(new StringBuilder()
        .append(year).append("-").append((month + 1)<10? "0" + (month + 1) : month + 1).append("-").append(day<10? "0" + day : day));

    }
}
