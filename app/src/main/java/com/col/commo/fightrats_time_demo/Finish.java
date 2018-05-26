package com.col.commo.fightrats_time_demo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by commo on 2017/5/27.
 */

public class Finish extends Activity {

    private DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.finish);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        TextView view = (TextView) findViewById(R.id.textView1);
        String jf = bundle.getInt("num")+"0";
        view.setText("你的积分："+jf);

        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);

        dbhelper = new DatabaseHelper(this,"Fightrats.db",null,1);

         Cursor cu = dbhelper.getReadableDatabase().rawQuery("select count(*) from singleRanking",null);
        cu.moveToFirst();
        int ifexists = cu.getInt(0);
        cu.close();

        if(ifexists == 0){
            dbhelper.getReadableDatabase().execSQL("insert into singleRanking values(0,0,0)");
        }else if(ifexists == 1){
            dbhelper.getReadableDatabase().execSQL("update singleRanking set easy = ?",new String[]{jf});
        }


        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent inten = new Intent(Finish.this,MainActivity.class);
                startActivity(inten);
                finish();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
