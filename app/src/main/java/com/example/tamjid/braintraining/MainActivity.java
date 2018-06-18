package com.example.tamjid.braintraining;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView question;
    public ArrayList<Integer> answers = new ArrayList<Integer>();
    int correctAnswerLocation;
    int score = 0;
    int numOfQuestions = 0;
    TextView resultTextView;
    TextView pointsTextView;
    TextView timerTextView;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button playAgainButton;
    boolean gameActive = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //make fullscreen
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //**initialising all views**
        timerTextView = findViewById(R.id.timer);


        button = findViewById(R.id.go);
        button1 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button4);
        button3 = findViewById(R.id.button5);
        button4 = findViewById(R.id.button6);
        playAgainButton = findViewById(R.id.restartButton);
        resultTextView = findViewById(R.id.result);

        pointsTextView = findViewById(R.id.points);
        playAgainButton.setVisibility(View.INVISIBLE);


        question = findViewById(R.id.question);
        //initialise timers, points, textview values
        playAgain(findViewById(R.id.restartButton));
        //generate first question
        generateQuestion();


    }
    //not used atm
    public void start(View view) {
        button.setVisibility(View.INVISIBLE);

    }

    //When an answer is selected check if correct
    public void chooseAnswer(View view) {
        Log.i("Tag", (String) view.getTag());
        if (gameActive) {
            resultTextView.setVisibility(View.VISIBLE);
            numOfQuestions++;

            if (view.getTag().toString().equals(Integer.toString(correctAnswerLocation))) {
                Log.i("Check", "Correct");
                score++;
                resultTextView.setText("Correct!");
            } else resultTextView.setText("False!");
            pointsTextView.setText(score + "/" + numOfQuestions);
            generateQuestion();
        }
    }

    //generate a random question with 3 random answers
    public void generateQuestion() {
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        question.setText(Integer.toString(a) + " + " + Integer.toString(b));
        answers.clear();

        correctAnswerLocation = rand.nextInt(4);

        int incorrectAnswer;


        for (int i = 0; i < 4; i++) {
            if (i == correctAnswerLocation) {
                answers.add(a + b);
            } else {
                incorrectAnswer = rand.nextInt(41);
                while (incorrectAnswer == a + b) {
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

            button1.setText(Integer.toString(answers.get(0)));
            button2.setText(Integer.toString(answers.get(1)));
            button3.setText(Integer.toString(answers.get(2)));
            button4.setText(Integer.toString(answers.get(3)));

    }

    public void playAgain(View view) {
        score = 0;
        numOfQuestions = 0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        pointsTextView.setText(score+"/"+numOfQuestions);
        playAgainButton.setVisibility(View.INVISIBLE);
        gameActive = true;



        new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l/1000)+"s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                resultTextView.setText("Score: "+score+"/"+numOfQuestions);
                resultTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
                gameActive = false;

            }
        }.start();

    }
}
