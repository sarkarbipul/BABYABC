package com.logicaltriangle.babyabc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class Learn_Number_Activity extends AppCompatActivity implements SettingInterface{

/*****************************   Variable Declaration ******************************/
    private Button num_btn, num_btn1, num_pre, num_next, home_btn, sound_btn, keyboard_btn;
    private LinearLayout back_layout_num;
    private RecyclerView recyclerView;

    private MediaPlayer mediaPlayer;
    private Sessions sessions;
    private Handler handler;

    private String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private String[] spelling = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    int keyb = 0;
    boolean check = true;
    int cc = 0;
    float x1, x2;


    /******************* onCreate Method Start ***************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn__number_);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /********************* Calling Sessions Class ************************/
        sessions = new Sessions(Learn_Number_Activity.this);

        /********************* Initializing Widgets ************************/
        num_btn = findViewById(R.id.num_btn);
        num_btn1 = findViewById(R.id.num_btn1);
        num_pre = findViewById(R.id.num_pre);
        num_next = findViewById(R.id.num_next);
        home_btn = findViewById(R.id.num_home_btn);
        sound_btn = findViewById(R.id.num_sound_btn);
        keyboard_btn = findViewById(R.id.num_keyboard_btn);
        back_layout_num = findViewById(R.id.back_layout_num);
        recyclerView = findViewById(R.id.letter_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(Learn_Number_Activity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        /*********************  ************************/
        recyclerView.setVisibility(View.GONE);

        /********************* Calling RecycleView Custom Adapter and Set Adapter on RecycleView ************************/
        CustomAdapter adapter = new CustomAdapter(Learn_Number_Activity.this, this, numbers);
        recyclerView.setAdapter(adapter);

        /******************* Initializing Media Player ************************/
        mediaPlayer = MediaPlayer.create(this, R.raw._1);



        /********************* Sound state ways Sound Button Setup ************************/
        if(sessions.getSoundState()==0){
            sound_btn.setBackgroundResource(R.drawable.volume_off);
        }else {
            sound_btn.setBackgroundResource(R.drawable.volume_on);
        }

        /********************* When Clicked on Number Button ************************/
        num_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
            }
        });

        /********************* When Clicked on Number spelling Button ************************/
        num_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
            }
        });

        /********************* When Clicked on Previous Button ************************/
        num_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setWord(sessions.getPosition()-1);
            }
        });

        /********************* When Clicked on Next Button ************************/
        num_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWord(sessions.getPosition()+1);
            }
        });

        /************************ When swipe on number button**************** *****/
        num_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        if(x1<x2){
                            sessions.setPosition(sessions.getPosition()-1);
                            setWord(sessions.getPosition());
                        }else if(x1>x2){
                            sessions.setPosition(sessions.getPosition()+1);
                            setWord(sessions.getPosition());
                        }
                        break;
                }
                return false;
            }
        });

        /************************ When swipe on Spelling button *********************/
        num_btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        if(x1<x2){
                            sessions.setPosition(sessions.getPosition()-1);
                            setWord(sessions.getPosition());
                        }else if(x1>x2){
                            sessions.setPosition(sessions.getPosition()+1);
                            setWord(sessions.getPosition());
                        }
                        break;
                }
                return false;
            }
        });

        /********************* When Clicked on Home Button ************************/
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                startActivity(new Intent(Learn_Number_Activity.this, MainActivity.class));
                finish();
            }
        });

        /********************* When Clicked on Sound Button ************************/
        sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessions.getSoundState()==0){
                    sessions.setSoundState(1);
                    sound_btn.setBackgroundResource(R.drawable.volume_on);
                }else {
                    if(mediaPlayer != null){
                        mediaPlayer.release();
                    }
                    sessions.setSoundState(0);
                    sound_btn.setBackgroundResource(R.drawable.volume_off);
                }
            }
        });

        /********************* When Clicked on KeyBoard Button ************************/
        keyboard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(keyb == 0){
                    recyclerView.setVisibility(View.VISIBLE);
                    keyboard_btn.setBackgroundResource(R.drawable.keyboard_off);
                    keyb = 1;
                }else {
                    recyclerView.setVisibility(View.GONE);
                    keyb = 0;
                    keyboard_btn.setBackgroundResource(R.drawable.keyboard_on);
                }
            }
        });
    }

    /************** Set Word *********************/
    public void setWord(int position){
        if(position < 10 && position >= 0){
            sessions.setPosition(position);
            if(sessions.getPosition() == 9 ){
                num_next.setBackgroundResource(R.drawable.refresh);
            }else {
                num_next.setBackgroundResource(R.drawable.arrow_right);
            }
            if(position == 0){
                num_pre.setVisibility(View.GONE);
                num_pre.setBackgroundResource(R.drawable.arrow_right);
            }else{
                num_pre.setBackgroundResource(R.drawable.arrow_left);
                num_pre.setVisibility(View.VISIBLE);
            }
        }else {
            sessions.setPosition(0);
            num_pre.setVisibility(View.GONE);
            num_next.setBackgroundResource(R.drawable.arrow_right);
        }
        num_btn.setText(numbers[sessions.getPosition()]);
        num_btn1.setText(spelling[sessions.getPosition()]);
        playSound();
    }

    /********************* Sound Playing Method ************************/
    private void playSound(){
        if(sessions.getSoundState() == 1){
            if(mediaPlayer != null){
                mediaPlayer.release();
            }
            mediaPlayer = setSongPosition(sessions.getPosition(), Learn_Number_Activity.this);
            mediaPlayer.start();
            handler = null;
            talk(mediaPlayer.getDuration());
        }
    }

    /********************* Animation Methods ************************/
    private void talk(final int duration){
       if(sessions.getSoundState() == 1){
           handler = new Handler();
           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   if(duration>300){
                       if(check){
                           back_layout_num.setBackgroundResource(R.drawable.boy_mouth_open);
                           check = false;
                           talk(duration-300);
                       }else{
                           back_layout_num.setBackgroundResource(R.drawable.boy_mouth_close);
                           check = true;
                           talk(duration-300);
                       }
                   }else {
                       back_layout_num.setBackgroundResource(R.drawable.boy_mouth_close);
                   }
               }
           }, 300);
       }
        if(sessions.getSoundState() == 0){
            back_layout_num.setBackgroundResource(R.drawable.boy_mouth_close);
        }
    }

    /********************* Creating Media by Number Position and Initialize Media Player ************************/
    private MediaPlayer setSongPosition(int position, Context context){
        switch (position){
            case 0:
                mediaPlayer = MediaPlayer.create(context,R.raw._1);
                break;
            case 1:
                mediaPlayer = MediaPlayer.create(context,R.raw._2);
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(context,R.raw._3);
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(context,R.raw._4);
                break;
            case 4:
                mediaPlayer = MediaPlayer.create(context,R.raw._5);
                break;
            case 5:
                mediaPlayer = MediaPlayer.create(context,R.raw._6);
                break;
            case 6:
                mediaPlayer = MediaPlayer.create(context,R.raw._7);
                break;
            case 7:
                mediaPlayer = MediaPlayer.create(context,R.raw._8);
                break;
            case 8:
                mediaPlayer = MediaPlayer.create(context,R.raw._9);
                break;
            case 9:
                mediaPlayer = MediaPlayer.create(context,R.raw._10);
                break;
//            default:
//                mediaPlayer = MediaPlayer.create(context,R.raw._7);
//                break;
        }
        return mediaPlayer;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setWord(sessions.getPosition());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mediaPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
    }
}
