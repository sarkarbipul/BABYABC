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
import android.widget.TextView;

public class Learn_Letter_Activity extends AppCompatActivity implements SettingInterface {
    private Button letter_btn, letter_btn1, letter_pre, letter_next, sound_btn, keyboard_btn, letter_home_btn;
    private LinearLayout back_layout_letter;
    private TextView tvlowerCase;

    private MediaPlayer mediaPlayer;
    private String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int[] image ={
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f,
            R.drawable.g,
            R.drawable.h,
            R.drawable.i,
            R.drawable.j,
            R.drawable.k,
            R.drawable.l,
            R.drawable.m,
            R.drawable.n,
            R.drawable.o,
            R.drawable.p,
            R.drawable.q,
            R.drawable.r,
            R.drawable.s,
            R.drawable.t,
            R.drawable.u,
            R.drawable.v,
            R.drawable.w,
            R.drawable.x,
            R.drawable.y,
            R.drawable.z,
                        };
    private Sessions sessions;
    private Handler handler;
    private RecyclerView recyclerView;
    int keyboard = 0;
    boolean check = true;
    float x1, x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn__letter_);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /********************* Calling Sessions Class ************************/
        sessions = new Sessions(Learn_Letter_Activity.this);

        /********************* Initializing Widgets ************************/
        letter_btn = findViewById(R.id.letter_btn);
        letter_btn1 = findViewById(R.id.letter_btn1);
        letter_pre = findViewById(R.id.letter_pre);
        letter_next = findViewById(R.id.letter_next);
        sound_btn = findViewById(R.id.letter_sound_btn);
        keyboard_btn = findViewById(R.id.letter_keyboard_btn);
        letter_home_btn = findViewById(R.id.letter_home_btn);

        tvlowerCase = findViewById(R.id.tvLowerCase);

        back_layout_letter = findViewById(R.id.back_layout_letter);


        recyclerView = findViewById(R.id.letter_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(Learn_Letter_Activity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        /*********************  ************************/
        recyclerView.setVisibility(View.GONE);

        /********************* Calling RecycleView Custom Adapter and Set Adapter on RecycleView ************************/
        CustomAdapter adapter = new CustomAdapter(Learn_Letter_Activity.this, this, letters);
        recyclerView.setAdapter(adapter);

        /******************* Initializing Media Player ************************/
        mediaPlayer = MediaPlayer.create(this, R.raw.a);


        /********************* Sound state ways Sound Button Setup ************************/
        if(sessions.getSoundState()==0){
            sound_btn.setBackgroundResource(R.drawable.volume_off);
        }else {
            sound_btn.setBackgroundResource(R.drawable.volume_on);
        }

        setWord(sessions.getPositionl());


        /********************* When Clicked on Number Button ************************/
        letter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWord(sessions.getPositionl());
            }
        });

        /********************* When Clicked on Number spelling Button ************************/
        letter_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWord(sessions.getPositionl());
            }
        });

        /********************* When Clicked on Previous Button ************************/
        letter_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWord(sessions.getPositionl()-1);
            }
        });

        /********************* When Clicked on Next Button ************************/
        letter_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWord(sessions.getPositionl()+1);
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
                if(keyboard == 0){
                    recyclerView.setVisibility(View.VISIBLE);
                    keyboard_btn.setBackgroundResource(R.drawable.keyboard_off);
                    keyboard = 1;
                }else {
                    recyclerView.setVisibility(View.GONE);
                    keyboard_btn.setBackgroundResource(R.drawable.keyboard_on);
                    keyboard = 0;
                }
            }
        });

        /************************ When swipe on letter button**************** *****/
        letter_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        if(x1<x2){
                            sessions.setPositionl(sessions.getPositionl()-1);
                            setWord(sessions.getPositionl());
                        }else if(x1>x2){
                            sessions.setPositionl(sessions.getPositionl()+1);
                            setWord(sessions.getPositionl());
                        }
                        break;
                }
                return false;
            }
        });

        /************************ When swipe on image button *********************/
        letter_btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        if(x1<x2){
                            sessions.setPositionl(sessions.getPositionl()-1);
                            setWord(sessions.getPositionl());
                        }else if(x1>x2){
                            sessions.setPositionl(sessions.getPositionl()+1);
                            setWord(sessions.getPositionl());
                        }
                        break;
                }
                return false;
            }
        });

        /********************* Home Button************************/
        letter_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                startActivity(new Intent(Learn_Letter_Activity.this,MainActivity.class));
                finish();
            }
        });
    }

    /************** Set Word *********************/
    public void setWord(int position){
        if(position < 26 && position >= 0){
            sessions.setPositionl(position);
            if(sessions.getPositionl() == 25 ){
                letter_next.setBackgroundResource(R.drawable.refresh);
            }else {
                letter_next.setBackgroundResource(R.drawable.arrow_right);
            }
            if(position == 0){
                letter_pre.setVisibility(View.GONE);
                letter_pre.setBackgroundResource(R.drawable.arrow_right);
            }else{
                letter_pre.setBackgroundResource(R.drawable.arrow_left);
                letter_pre.setVisibility(View.VISIBLE);
            }
        }else {
            sessions.setPositionl(0);
            letter_pre.setVisibility(View.GONE);
            letter_next.setBackgroundResource(R.drawable.arrow_right);
        }
        letter_btn.setText(letters[sessions.getPositionl()]);
        tvlowerCase.setText((letters[sessions.getPositionl()]).toLowerCase());
        letter_btn1.setBackgroundResource(image[sessions.getPositionl()]);
        playSound();
    }

    /********************* Sound Playing Method ************************/
    private void playSound(){
        if(sessions.getSoundState() == 1){
            if(mediaPlayer != null){
                mediaPlayer.release();
            }
            mediaPlayer = setSongPosition(sessions.getPositionl(), Learn_Letter_Activity.this);
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
                            back_layout_letter.setBackgroundResource(R.drawable.girl_mouth_open);
                            check = false;
                            talk(duration-300);
                        }else{
                            back_layout_letter.setBackgroundResource(R.drawable.girl_mouth_close);
                            check = true;
                            talk(duration-300);
                        }
                    }else {
                        back_layout_letter.setBackgroundResource(R.drawable.girl_mouth_close);
                    }
                }
            }, 300);
        }
        if(sessions.getSoundState() == 0){
            back_layout_letter.setBackgroundResource(R.drawable.girl_mouth_close);
        }
    }

    /********************* Creating Media by Number Position and Initialize Media Player ************************/
    public MediaPlayer setSongPosition(int positionl, Context context) {
        switch (positionl) {
            case 0:
                mediaPlayer = MediaPlayer.create(context, R.raw.a);
                break;
            case 1:
                mediaPlayer = MediaPlayer.create(context, R.raw.b);
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(context, R.raw.c);
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(context, R.raw.d);
                break;
            case 4:
                mediaPlayer = MediaPlayer.create(context, R.raw.e);
                break;
            case 5:
                mediaPlayer = MediaPlayer.create(context, R.raw.f);
                break;
            case 6:
                mediaPlayer = MediaPlayer.create(context, R.raw.g);
                break;
            case 7:
                mediaPlayer = MediaPlayer.create(context, R.raw.h);
                break;
            case 8:
                mediaPlayer = MediaPlayer.create(context, R.raw.i);
                break;
            case 9:
                mediaPlayer = MediaPlayer.create(context, R.raw.j);
                break;
            case 10:
                mediaPlayer = MediaPlayer.create(context, R.raw.k);
                break;
            case 11:
                mediaPlayer = MediaPlayer.create(context, R.raw.l);
                break;
            case 12:
                mediaPlayer = MediaPlayer.create(context, R.raw.m);
                break;
            case 13:
                mediaPlayer = MediaPlayer.create(context, R.raw.n);
                break;
            case 14:
                mediaPlayer = MediaPlayer.create(context, R.raw.o);
                break;
            case 15:
                mediaPlayer = MediaPlayer.create(context, R.raw.p);
                break;
            case 16:
                mediaPlayer = MediaPlayer.create(context, R.raw.q);
                break;
            case 17:
                mediaPlayer = MediaPlayer.create(context, R.raw.r);
                break;
            case 18:
                mediaPlayer = MediaPlayer.create(context, R.raw.s);
                break;
            case 19:
                mediaPlayer = MediaPlayer.create(context, R.raw.t);
                break;
            case 20:
                mediaPlayer = MediaPlayer.create(context, R.raw.u);
                break;
            case 21:
                mediaPlayer = MediaPlayer.create(context, R.raw.v);
                break;
            case 22:
                mediaPlayer = MediaPlayer.create(context, R.raw.w);
                break;
            case 23:
                mediaPlayer = MediaPlayer.create(context, R.raw.x);
                break;
            case 24:
                mediaPlayer = MediaPlayer.create(context, R.raw.y);
                break;
            case 25:
                mediaPlayer = MediaPlayer.create(context, R.raw.z);
                break;
//            default:
//                mediaPlayer = MediaPlayer.create(context, R.raw.u);
//                break;
        }
        return mediaPlayer;
    }
    @Override
    protected void onStart() {
        super.onStart();
        setWord(sessions.getPositionl());
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
