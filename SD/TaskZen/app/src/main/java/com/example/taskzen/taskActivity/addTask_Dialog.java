package com.example.taskzen.taskActivity;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskzen.MainActivity;
import com.example.taskzen.Model.taskModel;
import com.example.taskzen.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class addTask_Dialog extends AppCompatActivity {

    int mYear, mMonth, mDay, mWeek;
    int mHour, mMinute;
    TextView day;
    TextView mon;
    TextView date;

    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    EditText taskDate;
    EditText taskTime;
    EditText taskTitle;
    FirebaseFirestore db;
    String TAG = "TaskZen";
    Button addTaskbtn;
    MainActivity activity;
    AlarmManager alarmManager;
    Handler handler;
    public static int count =0;
    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_dialog);

        db = FirebaseFirestore.getInstance();
        day = findViewById(R.id.day);
        date = findViewById(R.id.date);
        mon = findViewById(R.id.month);
        handler = new Handler();

        taskTitle = findViewById(R.id.addTask_Title);
        addTaskbtn = findViewById(R.id.add_Task);
        taskDate = findViewById(R.id.Task_Date);
        taskTime = findViewById(R.id.taskTime);





        taskDate.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mWeek = c.get(Calendar.DAY_OF_WEEK);

                DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, monthOfYear, dayOfMonth) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, monthOfYear, dayOfMonth);
                    int selectedWeekDay = selectedCalendar.get(Calendar.DAY_OF_WEEK);

                    String weekDayName = "";

                    switch (selectedWeekDay) {

                        case 1:
                            weekDayName = "Sun";
                            break;
                        case 2:
                            weekDayName = "Mon";
                            break;
                        case 3:
                            weekDayName = "Tue";
                            break;
                        case 4:
                            weekDayName = "Wed";
                            break;
                        case 5:
                            weekDayName = "Thu";
                            break;
                        case 6:
                            weekDayName = "Fri";
                            break;
                        case 7:
                            weekDayName = "Sat";
                            break;
                    }


                    taskDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year + "-" + weekDayName);
                };

                datePickerDialog = new DatePickerDialog(addTask_Dialog.this,
                        dateSetListener, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });




        taskTime.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(addTask_Dialog.this,
                        (view12, hourOfDay, minute) -> {
                            taskTime.setText(hourOfDay + ":" + minute);
                            timePickerDialog.dismiss();

                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
            return true;
        });

        addTaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String task_Name = taskTitle.getText().toString();
                String task_Date =  taskDate.getText().toString();
                String task_Time = taskTime.getText().toString();

                if (task_Name!=null){
                    //Toast.makeText(addTask_Dialog.this,task_Name+" "+task_Date+" "+task_Time,Toast.LENGTH_SHORT).show();
                    taskModel taskModel = new taskModel("",task_Name,"PENDING",task_Date,task_Time, FirebaseAuth.getInstance().getUid());
                    db.collection("tasks").add(taskModel) .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    findViewById(R.id.successAdd).setVisibility(View.VISIBLE);

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {


                                            finish();
                                        }
                                    },2000);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                }
            }
        });


    }



}