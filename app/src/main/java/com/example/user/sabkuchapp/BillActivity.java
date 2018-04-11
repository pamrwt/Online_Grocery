package com.example.user.sabkuchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BillActivity extends AppCompatActivity {
TextView tv3,tv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        tv3= (TextView) findViewById(R.id.tv_amt);
        tv4= (TextView) findViewById(R.id.tv_name);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        int a = b.getInt("key");
        String item1 = b.getString("momos");
        tv3.setText("Bill amt:  "+String.valueOf(a));
        tv4.setText("Bill amt:  "+item1);
    }
}
