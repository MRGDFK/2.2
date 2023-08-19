package com.example.taskzen;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.taskzen.logIn.logIn_activity;
import com.example.taskzen.notes.noteActivity;
import com.example.taskzen.taskActivity.addTask_Dialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity { //implements createTaskBottom.setRefreshListener

    NavigationView navView;
    FloatingActionButton fab,logT;
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
    ClipData.Item logout;


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
        createNotificationChannel();
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        logT = findViewById(R.id.logout);


        taskDate = findViewById(R.id.Task_Date);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        replaceFragment(new HomeFragment());
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.tasks){
                replaceFragment(new HomeFragment());
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
        logT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

               /* Intent intent = new Intent(MainActivity.this,logIn_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();*/
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // After signing out from Google, start the login activity
                        Intent intent = new Intent(MainActivity.this, logIn_activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(MainActivity.this, addTask_Dialog.class));
                showBottomDialog();
            }
        });

        View headerView = navView.getHeaderView(0);
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

    private void createNotificationChannel() {
        CharSequence name = "TaskZen Reminder Chennel";
        String description = "Cannel for alarm & Notification";
        int imp = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("TaskZen",name,imp);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }
    //Outside Oncreate

    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout taskLayout = dialog.findViewById(R.id.layoutTask);
        LinearLayout notesLayout = dialog.findViewById(R.id.layoutNotes);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        taskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Toast.makeText(MainActivity.this,"Upload a Video is clicked",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, addTask_Dialog.class));

            }
        });

        notesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
               // Toast.makeText(MainActivity.this,"Create a short is Clicked",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, noteActivity.class));

            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    public void item_logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this,logIn_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}