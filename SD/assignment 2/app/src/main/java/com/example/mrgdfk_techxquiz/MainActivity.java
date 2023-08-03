package com.example.mrgdfk_techxquiz;

import static com.example.mrgdfk_techxquiz.R.id.box_1;
import static com.example.mrgdfk_techxquiz.R.id.box_2;
import static com.example.mrgdfk_techxquiz.R.id.box_3;
import static com.example.mrgdfk_techxquiz.R.id.status_Btn;
import static com.example.mrgdfk_techxquiz.R.id.submit_btn;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView questionText,statusBtn;
    CheckBox ansA, ansB, ansC;
    Button submitBtn;

    String correct = "Correct Answer";
    String wrong = "Wrong Answer!";
    String nothing = "Nothing Selected :(";

    //int currentQuestionIndex = 0;
    int totalQuestion = qna.question.length;
    int score =0;
    int checker=0;
    boolean clicked = false;

    String selectedAnswer = "";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.question);
        ansA = findViewById(R.id.box_1);
        ansB = findViewById(R.id.box_2);
        ansC = findViewById(R.id.box_3);
        submitBtn = findViewById(submit_btn);
        statusBtn = findViewById(status_Btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        statusBtn.setOnClickListener(this);

        loadNewQuestion();


    }

    @Override
    public void onClick(View view) {

        clicked=true;
        ansA.setBackgroundColor(Color.BLACK);
        ansB.setBackgroundColor(Color.BLACK);
        ansC.setBackgroundColor(Color.BLACK);

        List<CheckBox> items = new ArrayList<CheckBox>();
        items.add(ansA);
        items.add(ansB);
        items.add(ansC);
        Button  clickedButton = (Button) view;
        if(clickedButton.getId()== submit_btn){

            if (checker<1){
                Toast.makeText(MainActivity.this,"You Have to Choose at least 1 Option",Toast.LENGTH_SHORT).show();
            }
                if(selectedAnswer.equals(qna.ca[0])){
                    score++;
                    statusBtn.setText(String.valueOf(correct));
                    statusBtn.setTextColor(Color.GREEN);
                    //currentQuestionIndex++;
                    clearCheck();
                    checker = 0;
                    //loadNewQuestion();
                }
                else if (selectedAnswer.equals(qna.ca[2])){

                    statusBtn.setText(String.valueOf(nothing));
                    statusBtn.setTextColor(Color.CYAN);

                }
                else{

                    statusBtn.setText(String.valueOf(wrong));
                    statusBtn.setTextColor(Color.RED);
                    clearCheck();
                    checker=0;
                }

        }
        else{

            for (CheckBox item : items){
                if(item.isChecked())
                    selectedAnswer=item.getText().toString();
            }

            //selectedAnswer = checkedBox.getText().toString();
            //clickedButton.setBackgroundColor(Color.MAGENTA);
            if(clicked){
                checker++;
            }
            if(checker==2){
                Toast.makeText(MainActivity.this,"You Have to Choosee Only 1 Option",Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void clearCheck()
    {
        ansA.setChecked(false);
        ansB.setChecked(false);
        ansC.setChecked(false);

    }
    void loadNewQuestion(){

        if(0 == totalQuestion){
            finishQuiz();
            return;
        }
        questionText.setText(qna.question[0]);
        ansA.setText(qna.choices[0][0]);
        ansB.setText(qna.choices[0][1]);
        ansC.setText(qna.choices[0][2]);


    }
    void finishQuiz(){
        String passStat ="";
        if (score> totalQuestion*0.40){
            passStat = "Passed";
        }
        else{
            passStat = "Failed";
        }
        new AlertDialog.Builder(this)
            .setTitle(passStat)
            .setMessage("Score is "+score+" "+"Out of"+" "+totalQuestion)
                .setPositiveButton("Restart",((dialogInterface, i) -> restartQuiz()))
                .setCancelable(false)
                .show();

    }
    void restartQuiz(){
        score =0;
        checker =0;
        clicked = false;
        //currentQuestionIndex=0;
        loadNewQuestion();
    }

}