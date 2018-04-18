package com.example.user.sabkuchapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AddProducts extends AppCompatActivity {
    Button btnAddProduct;
    EditText edName , edPrice ,edImage;
    boolean connected = false;
    InputStream inpst=null;
    String line ,result  , Name ,Image , Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        new AsyncTaskRunner().execute((Void[])null);
    }

    public boolean Connetion()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;
    }
    class AsyncTaskRunner extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            //spinner=(Spinner)findViewById(R.id.spinner1);
            edName=(EditText) findViewById(R.id.edName);
            edImage = (EditText) findViewById(R.id.edImage);
            edPrice = (EditText) findViewById(R.id.edPrice);
            btnAddProduct=(Button) findViewById(R.id.btnAddProduct);
        }



        @Override
        protected Void doInBackground(Void... params) {
         /*   spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinnerstring = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            */


            btnAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connected = Connetion();
                    if (connected==true) {
                        Name =edName.getText().toString();
                        Image = edImage.getText().toString();
                        Price = edPrice.getText().toString();



                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("Name", Name));
                        nameValuePairs.add(new BasicNameValuePair("Price",Price));
                        //nameValuePairs.add(new BasicNameValuePair("type", "register"));
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://sabkuch.co.in/sabkuckapp/uploadImage.php");

                        try {
                            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpClient.execute(httpPost);
                            HttpEntity entity = response.getEntity();
                            inpst = entity.getContent();
                            BufferedReader br = new BufferedReader(new InputStreamReader(inpst));
                            StringBuilder sb = new StringBuilder();
                            //test=Mobile;

                            while ((line = br.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            inpst.close();
                            result = sb.toString();
                            JSONObject jsonObject = new JSONObject(result);
                            String res = jsonObject.getString("status");
                            Log.d("value of res is ",res);
                            int r=Integer.parseInt(res);
                            //String msg = jsonObject.getString("status_messsage");
                            if (r==1) {
                                Intent intent = new Intent(AddProducts.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                // finish();
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else
                    {
                        Toast.makeText(getApplicationContext(), "NO INTERNET CONNECTION! TRY AGAIN...", Toast.LENGTH_LONG).show();
                    }


                }
            });


            return null;
        }
    }

}
