package com.col.commo.fightrats_time_demo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by commo on 2017/6/2.
 */

public class Hello extends AppCompatActivity {
    private TextView easy;
    private  DatabaseHelper dbhelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.hello);

        easy = (TextView) findViewById(R.id.textView7);

        dbhelper = new DatabaseHelper(this,"Fightrats.db",null,1);

        Cursor cu = dbhelper.getReadableDatabase().rawQuery("select easy from singleRanking",null);
        cu.moveToFirst();
        String jf = cu.getString(0);
        cu.close();

        easy.setText(jf);


    }
}
