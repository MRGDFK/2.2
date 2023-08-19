package com.example.taskzen.features;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import android.os.Handler;


import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.taskzen.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pomodoro_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pomodoro_Fragment extends Fragment {

    private ProgressBar progressBar;

    private Runnable runnable;

    private EditText txtTime;
    private TextView txtView;
    private CountDownTimer timer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Pomodoro_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Pomodoro_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Pomodoro_Fragment newInstance(String param1, String param2) {
        Pomodoro_Fragment fragment = new Pomodoro_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pomodoro_, container, false);
        progressBar = rootView.findViewById(R.id.progress_circular);
        txtTime = rootView.findViewById(R.id.txtTime);
        txtView = rootView.findViewById(R.id.txtView);

        txtTime.setText("25:00");
        timer = new CountDownTimer(25 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int totalTime = 25 * 60 * 1000; // Total time in milliseconds (25 minutes)
                int remainingTime = (int) millisUntilFinished;
                int progress = (int) (((float) remainingTime / totalTime) * 100);

                int minutes = remainingTime / 1000 / 60;
                int seconds = (remainingTime / 1000) % 60;

                txtView.setText(String.format("%02d:%02d", minutes, seconds));
                smoothUpdateProgressBar(progress);
                // progressBar.setProgress(progress);


            }

            @Override
            public void onFinish() {
                txtTime.setText("00:00");
                progressBar.setProgress(0);
            }
        };
        Button startButton = rootView.findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
            }
        });

        return rootView;
    }

    private void smoothUpdateProgressBar(int newProgress) {
        ObjectAnimator.ofInt(progressBar, "progress", newProgress)
                .setDuration(300) // Duration in milliseconds
                .start();
    }
}