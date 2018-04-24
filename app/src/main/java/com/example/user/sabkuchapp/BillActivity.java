package com.example.user.sabkuchapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BillActivity extends Fragment {
TextView tv3,tv4;
    SQLiteHelper myDB;
    Button delete;
  Integer a,Amount,Price,Kg;
    String Name;
    ListView listViewDetail;
   ListViewAdapter adapter;
    Button btn_intent;
    ArrayList<String> ID_ArrayList = new ArrayList<String>();
    ArrayList<String> NAME_ArrayList = new ArrayList<String>();
    ArrayList<String> Amount_ArrayList = new ArrayList<String>();
    ArrayList<String> Image_ArrayList = new ArrayList<String>();
    ArrayList<String> Weight_ArrayList = new ArrayList<String>();
    Context context;


    public BillActivity() {
        // Required empty public constructor
    }
    public static BillActivity newInstance(String param1, String param2) {
      BillActivity fragment = new BillActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_bill, container, false);

       listViewDetail= (ListView)view. findViewById(R.id.list);
        btn_intent= (Button)view. findViewById(R.id.btn_intent);
        delete= (Button)view. findViewById(R.id.btn_delete);
        myDB=new SQLiteHelper(getActivity());


     viewAll();
        DeleteData();
        btn_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return  view;
    }
    public void viewAll() {


                        Cursor res = myDB.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                        //    showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                       if(res.moveToFirst()){ do{
                           ID_ArrayList.add(res.getString(res.getColumnIndex(SQLiteHelper.COL_1)));
                          NAME_ArrayList.add(res.getString(res.getColumnIndex(SQLiteHelper.COL_2)));
                           Amount_ArrayList.add(res.getString(res.getColumnIndex(SQLiteHelper.COL_3)));
                         Image_ArrayList.add(res.getString(res.getColumnIndex(SQLiteHelper.COL_5)));
                         Weight_ArrayList.add(res.getString(res.getColumnIndex(SQLiteHelper.COL_6)));
                           // buffer.append("Marks :"+ res.getString(3)+"\n\n");
                        }while (res.moveToNext());}
   adapter= new  ListViewAdapter (getActivity(), ID_ArrayList,NAME_ArrayList,Amount_ArrayList,Image_ArrayList,Weight_ArrayList);
        Toast.makeText(getActivity(), ""+Image_ArrayList, Toast.LENGTH_SHORT).show();

        listViewDetail.setAdapter(adapter);

res.close();

                        // Show all data
                     //   showMessage("Data",buffer.toString());



    }
    public void DeleteData() {
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertDataOnline();
                     Integer deletedRows = myDB.deleteData(String.valueOf(a));

                        Toast.makeText(getActivity(), ""+deletedRows, Toast.LENGTH_SHORT).show();
                       if(deletedRows > 0)
                          Toast.makeText(getActivity(),"Data Deleted",Toast.LENGTH_LONG).show();
                       else{
                           Toast.makeText(getActivity(),"Data not Deleted",Toast.LENGTH_LONG).show();}

                    }
                }
        );
    }
    private void insertDataOnline() {

      String  insertDataURL=  "http://sabkuch.co.in/sabkuckapp/Bill.php";
        StringRequest request = new StringRequest(Request.Method.POST, insertDataURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                Intent intent = getActivity().getIntent();
                Bundle b = intent.getExtras();
                a = b.getInt("id");
                Amount = b.getInt("key");
                Price = b.getInt("id");
                Kg = b.getInt("id");
                Name = b.getString("Name");

                parameters.put("Name", Name);
                parameters.put("Fruit_id",String.valueOf(a));
                parameters.put("Price",String.valueOf(Amount));
                return parameters;

            }
        };

       MyApplication.getInstance().addToRequestQueue(request);

    }



}
