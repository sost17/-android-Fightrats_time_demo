package com.col.commo.fightrats_time_demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {
    private int jf = 0;
    private TextView textView ;
    private ImageView integral,hole,hole1,hole2,hole3,hole4,hole5;
    private ImageButton pause;
    private Chronometer ch ;
    Animation translate;
    private SoundPool pool;
    private static MediaPlayer mp = null;
    private HashMap<Integer, Integer> soundmap = new HashMap<Integer, Integer>();
    int count;

    @SuppressLint("HandlerLeak") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //音乐
        open_music();

        //计时器
        ch = (Chronometer) findViewById(R.id.chronometer1);
        ch.setBase(SystemClock.elapsedRealtime());
        ch.setFormat("已用时间：%s");
        ch.start();

        initView();

        MyThread t = new MyThread();
        t.start();
    }

    public void initView(){
        textView = (TextView) findViewById(R.id.textView1);
        integral=(ImageView) findViewById(R.id.imageView2);
        pause = (ImageButton) findViewById(R.id.pause);
        hole = (ImageView) findViewById(R.id.hole1);
        hole1 = (ImageView) findViewById(R.id.hole2);
        hole2 = (ImageView) findViewById(R.id.hole3);
        hole3 = (ImageView) findViewById(R.id.hole4);
        hole4 = (ImageView) findViewById(R.id.hole5);
        hole5 = (ImageView) findViewById(R.id.hole6);
        pause.setImageResource(R.drawable.pause);
        pause.setOnClickListener(this);
        inithole();
        count = 0;
        integral.setVisibility(View.INVISIBLE);
        translate = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_translate);
        pool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 0);
        soundmap.put(1, pool.load(MainActivity.this, R.raw.dalaoshu,1));
        soundmap.put(2, pool.load(MainActivity.this, R.raw.enter,1));
        pool.play(soundmap.get(1), 1, 1, 0, -1, 1);
    }

    public void inithole(){
        hole.setImageResource(R.drawable.nothave_mouse);
        hole1.setImageResource(R.drawable.nothave_mouse);
        hole2.setImageResource(R.drawable.nothave_mouse);
        hole3.setImageResource(R.drawable.nothave_mouse);
        hole4.setImageResource(R.drawable.nothave_mouse);
        hole5.setImageResource(R.drawable.nothave_mouse);
        hole.setEnabled(false);
        hole1.setEnabled(false);
        hole2.setEnabled(false);
        hole3.setEnabled(false);
        hole4.setEnabled(false);
        hole5.setEnabled(false);
    }

    public void fightmouses(){
        pool.play(soundmap.get(2), 1, 1, 0, 0, 1);
        integral.startAnimation(translate);
        integral.setVisibility(View.INVISIBLE);
        jf++;
        textView.setText("积分："+jf+"0");
    }

    @Override
    protected void onDestroy() {
        if(mp != null){
            mp.stop();
            mp.release();
            mp=null;
        }
        super.onDestroy();
    }

    public void close_music(){
        if(mp != null){
            mp.stop();
            mp.release();
            mp=null;
        }
    }

    public void open_music(){
        if(mp != null){
            mp.release();
        }
        mp = MediaPlayer.create(MainActivity.this, R.raw.dalaoshu);
        mp.start();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.pause){
            final int temp_count = count ;
            final int temp_jf = jf;
            count = -1;
            close_music();
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.pause,null);
            final AlertDialog.Builder bulider = new AlertDialog.Builder(this);
            bulider.setView(view);
            bulider.setCancelable(false);
            bulider.create();
            final AlertDialog dialog = bulider.show();
            ImageButton  play = (ImageButton) view.findViewById(R.id.play);
            ImageButton home = (ImageButton) view.findViewById(R.id.home);
            play.setImageResource(R.drawable.play);
            home.setImageResource(R.drawable.home);
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,temp_count+"++++"+temp_jf,Toast.LENGTH_SHORT).show();
                    count = temp_count;
                    jf = temp_jf;
                    dialog.dismiss();
                    open_music();
                    MyThread t = new MyThread();
                    t.start();
                }
            });

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(MainActivity.this,Hello.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    public class Handle extends Handler{
        public void handleMessage(Message msg) {
            if(msg.what == 0x111){
                int i = (int) msg.obj;
                inithole();

                System.out.println(count);

                switch (i){
                    case 0:
                        count ++;
                        hole.setEnabled(true);
                        hole.setImageResource(R.drawable.have_easymouse);
                        hole.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole.setImageResource(R.drawable.finght_easymouse);
                                fightmouses();
                                hole.setEnabled(false);
                            }
                        });
                        break;
                    case 1:
                        count ++;
                        hole1.setEnabled(true);
                        hole1.setImageResource(R.drawable.have_easymouse);
                        hole1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole1.setImageResource(R.drawable.finght_easymouse);
                                fightmouses();
                                hole1.setEnabled(false);
                            }
                        });
                        break;
                    case 2:
                        count ++;
                        hole2.setEnabled(true);
                        hole2.setImageResource(R.drawable.have_easymouse);
                        hole2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole2.setImageResource(R.drawable.finght_easymouse);
                                fightmouses();
                                hole2.setEnabled(false);
                            }
                        });
                        break;
                    case 3:
                        count ++;
                        hole3.setEnabled(true);
                        hole3.setImageResource(R.drawable.have_easymouse);
                        hole3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole3.setImageResource(R.drawable.finght_easymouse);
                                fightmouses();
                                hole3.setEnabled(false);
                            }
                        });
                        break;
                    case 4:
                        count ++;
                        hole4.setEnabled(true);
                        hole4.setImageResource(R.drawable.have_easymouse);
                        hole4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole4.setImageResource(R.drawable.finght_easymouse);
                                fightmouses();
                                hole4.setEnabled(false);
                            }
                        });
                        break;
                    case 5:
                        count ++;
                        hole5.setEnabled(true);
                        hole5.setImageResource(R.drawable.have_easymouse);
                        hole5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole5.setImageResource(R.drawable.finght_easymouse);
                                fightmouses();
                                hole5.setEnabled(false);
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
            super.handleMessage(msg);
        }
    }

    public class MyThread extends Thread{
        Handle handler = new Handle();
        public void run() {
            int hole_number = -1;
            while(!Thread.currentThread().isInterrupted()){
                hole_number = new Random().nextInt(6);
                System.out.println("Random"+String.valueOf(hole_number));
                Message m = handler.obtainMessage();
                m.what = 0x111;
                m.obj = hole_number;
                handler.sendMessage(m);

                if (count >=4){
                    Intent intent = new Intent(MainActivity.this,Finish.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("num", jf);
                    bundle.putString("grade","easy");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                    break;
                } else if(count == -1){
                    break;
                }

                try {
                    Thread.sleep(1500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
