package com.example.braintrainer;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton,o1,o2,o3,o4,playggain;
    ConstraintLayout constraintLayout;
    TextView score,timer,question,info,result;
    GridLayout choices;
    int right,wrong,a,b,pos;
    ArrayList<Integer> answers;
    Random rand=new Random();
    public void start(View view){
        info.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        constraintLayout.setBackgroundColor(Color.WHITE);
        score.setAlpha(1);
        timer.setAlpha(1);
        question.setAlpha(1);
        choices.setAlpha(1);
        result.setAlpha(1);
        o1.setEnabled(true);
        o2.setEnabled(true);
        o3.setEnabled(true);
        o4.setEnabled(true);
        starttimer();
    }
    public void starttimer(){
        new CountDownTimer(31000,500){
            public void onTick(long timeLeft){
                int time=(int)timeLeft/1000;
                String timeString=Integer.toString(time);
                timeString+=" s";
                if(time<10)
                    timeString="0"+timeString;
                timer.setText(timeString);
            }
            @Override
            public void onFinish(){
                int points=right*4-wrong;
                result.setText("BOOM! YOU scored "+Integer.toString(points)+" points.");
                playggain.setAlpha(1);
                o1.setEnabled(false);
                o2.setEnabled(false);
                o3.setEnabled(false);
                o4.setEnabled(false);
                playggain.setEnabled(true);
                MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.fire);
                mp.start();
            }
        }.start();
    }
    public void chooseAnswer(View view){
        int i=Integer.parseInt(view.getTag().toString());
        int answer=answers.get(i-1);
        if(answer==a+b){
            right++;
            result.setText("Correct:)");
        }
        else{
            wrong++;
            result.setText("Wrong:(");
        }
        score.setText(Integer.toString(right)+'/'+Integer.toString(wrong));
        generateQuestion();
        generateOptions();
    }
    public void generateQuestion(){
        a=rand.nextInt(1000);
        b=rand.nextInt(1000);
        question.setText(Integer.toString(a)+" + "+Integer.toString(b));
    }
    public void generateOptions(){
        answers=new ArrayList<Integer>();
        pos=rand.nextInt(4);
        for(int i=0;i<4;i++) {
            if (i == pos){
                answers.add(a + b);
                continue;
            }
            else answers.add(rand.nextInt(a+b+10));
            if(answers.get(i)==a+b) answers.set(i,a+b-1);
        }
        o1.setText(Integer.toString(answers.get(0)));
        o2.setText(Integer.toString(answers.get(1)));
        o3.setText(Integer.toString(answers.get(2)));
        o4.setText(Integer.toString(answers.get(3)));
    }
    public void playAgain(View view){
        score.setText("0/0");
        right=0;
        wrong=0;
        o1.setEnabled(true);
        o2.setEnabled(true);
        o3.setEnabled(true);
        o4.setEnabled(true);
        playggain.setEnabled(false);
        generateQuestion();
        generateOptions();
        timer.setText("30 s");
        playggain.setAlpha(0);
        result.setText(" ");
        starttimer();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton=findViewById(R.id.startbutton);
        score=findViewById(R.id.score);
        timer=findViewById(R.id.timer);
        question=findViewById(R.id.question);
        choices=findViewById(R.id.choices);
        info=findViewById(R.id.info);
        result=findViewById(R.id.result);
        constraintLayout=findViewById(R.id.layout);
        o1=findViewById(R.id.option1);
        o2=findViewById(R.id.option2);
        o3=findViewById(R.id.option3);
        o4=findViewById(R.id.option4);
        playggain=findViewById(R.id.playagain);
        generateQuestion();
        generateOptions();
    }
}
