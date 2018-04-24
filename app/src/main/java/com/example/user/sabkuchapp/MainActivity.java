package com.example.user.sabkuchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    Fragment fragment;

    SQLiteHelper myDB;
   // a = b.getInt("id");
   int amount,Price,Kg,Fruit_id;
String Name,Image;
    String Fruit_name,Fruit_Image,Fruit_weight;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                //    mTextMessage.setText(R.string.title_home);
                    fragment= new VegitableShop();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_dashboard:
                    Intent intent = getIntent();
                    Bundle b = intent.getExtras();
                    amount = b.getInt("key");

                    Fruit_Image=b.getString("image");
                    Fruit_weight = b.getString("id");
                   Fruit_name = b.getString("Name");
                  //  UpdataData();

                    fragment= new BillActivity();
                    loadFragment(fragment);

                   // mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    fragment= new AccountActivity();
                    loadFragment(fragment);
                   // mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
myDB=new SQLiteHelper(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
       // layoutParams.setBehavior(new BottomNavigationBehavior());
        //loadFragment(new StoreFragment());
       // toolbar.setTitle("Shop");
        loadFragment(new VegitableShop());
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public  void UpdataData() {


        myDB.update(
                Fruit_name, amount,Fruit_id,Fruit_Image,Fruit_weight
        );

        Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();




    }

}
