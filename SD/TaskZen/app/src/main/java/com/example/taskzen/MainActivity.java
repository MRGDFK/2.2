package com.example.taskzen;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;


import com.example.taskzen.features.Calendar_Fragment;
import com.example.taskzen.features.Habits_Fragment;
import com.example.taskzen.features.HomeFragment;
import com.example.taskzen.features.Pomodoro_Fragment;
import com.example.taskzen.taskActivity.addTask_Dialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity { //implements createTaskBottom.setRefreshListener

    NavigationView navView;
    FloatingActionButton fab;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;

    //addind task properties
    EditText taskDate;
    EditText taskTime ;
    EditText taskTitle;
    Button addTask;

    TextView userName;
    TextView userMail;
    ImageView userPhoto;


    MainActivity activity;

    int mYear, mMonth, mDay;
    int mHour, mMinute;
    int taskId;
    boolean isEdit;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @SuppressLint({"NonConstantResourceId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);

        taskDate = findViewById(R.id.Task_Date);
        drawerLayout = findViewById(R.id.drawer_layout);
         NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        replaceFragment(new HomeFragment());


        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.tasks){
                replaceFragment(new HomeFragment());
               /* Intent i = new Intent(MainActivity.this, task_home_activity.class);
                startActivity(i);*/
            }
            if(id == R.id.calendar){
                replaceFragment(new Calendar_Fragment());
            }
            if(id == R.id.pomodoro){
                replaceFragment(new Pomodoro_Fragment());
            }
            if(id == R.id.habits){
                replaceFragment (new Habits_Fragment());
            }
            return true;
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, addTask_Dialog.class));
            }
        });
        navView = findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        /*
        LayoutInflater inflater = getLayoutInflater();
        View separateLayout = inflater.inflate(R.layout.nav_header, null);*/

        userName = headerView.findViewById(R.id.userName_tv);
        userMail = headerView.findViewById(R.id.userMail_tv);
        userPhoto = headerView.findViewById(R.id.userPhoto_tv);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null){
            userName.setText(currentUser.getDisplayName());
            userMail.setText(currentUser.getEmail());
            Picasso.get().load(currentUser.getPhotoUrl()).into(userPhoto);
        }

    }
    //Outside Oncreate

    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}