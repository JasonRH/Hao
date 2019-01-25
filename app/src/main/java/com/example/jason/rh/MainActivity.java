package com.example.jason.rh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rh.bottom.BottomActivity;


/**
 * @author RH
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //调用检查更新接口
        //XiaomiUpdateAgent.update(MyApp.getApplicationContext());

        startActivity(new Intent(this, BottomActivity.class));
        finish();
    }
}
