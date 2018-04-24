package com.example.user.sabkuchapp;

/**
 * Created by user on 2/23/2018.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;


public  class VegitableShop extends Fragment {
        //hi
        private static final String TAG = VegitableShop.class.getSimpleName();
        private static final String URL = "http://sabkuch.co.in/sabkuckapp/fruits.php";
        int amount = 0;
        private RecyclerView recyclerView;
        private List<Vegitable> movieList;
        private StoreAdapter mAdapter;
        Intent intent;
    String Fruit_name,Fruit_Image,Fruit_weight;
    int Fruit_id;
    SQLiteHelper myDB;
    Button btn_intent;
        public VegitableShop() {
            // Required empty public constructor
        }

        public static VegitableShop newInstance(String param1, String param2) {
            VegitableShop fragment = new VegitableShop();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_store, container, false);

            recyclerView = view.findViewById(R.id.recycler_view);
            movieList = new ArrayList<>();
            mAdapter = new StoreAdapter(getActivity(), movieList);
            myDB = new SQLiteHelper(getActivity());
            btn_intent=view.findViewById(R.id.btn);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);

            fetchStoreItems();
            btn_intent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return view;
        }

        private void fetchStoreItems() {
            JsonArrayRequest request = new JsonArrayRequest(URL,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response == null) {
                                Toast.makeText(getActivity(), "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                                return;
                            }

                            List<Vegitable> items = new Gson().fromJson(response.toString(), new TypeToken<List<Vegitable>>() {
                            }.getType());

                            movieList.clear();
                            movieList.addAll(items);

                            // refreshing recycler view
                            mAdapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error in getting json
                    Log.e(TAG, "Error: " + error.getMessage());
                    Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            MyApplication.getInstance().addToRequestQueue(request);
        }

        public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

            private int spanCount;
            private int spacing;
            private boolean includeEdge;

            public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
                this.spanCount = spanCount;
                this.spacing = spacing;
                this.includeEdge = includeEdge;
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int column = position % spanCount; // item column

                if (includeEdge) {
                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                    outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                    if (position >= spanCount) {
                        outRect.top = spacing; // item top
                    }
                }
            }
        }

        /**
         * Converting dp to pixel
         */
        private int dpToPx(int dp) {
            Resources r = getResources();
            return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
        }

        class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
            private Context context;
            ImageLoader imageLoader;
            private List<Vegitable> movieList;
            final int[] count = {0};


            public class MyViewHolder extends RecyclerView.ViewHolder {
                CheckBox c1;
                public TextView name, price, txtCount, id;
                public ImageView thumbnail;
                double amount = 0;
                public Button buttonInc, buttonDec, orderNow;
                Spinner mSpinner;
                Intent intent=new Intent(getActivity(),BillActivity.class);

                public MyViewHolder(View view) {
                    super(view);
                    name = view.findViewById(R.id.textView);
                    price = view.findViewById(R.id.price);
                    id = view.findViewById(R.id.id);
                    mSpinner = (Spinner) view.findViewById(R.id.cb1);
                    thumbnail = view.findViewById(R.id.image);

                   intent = new Intent(context, BillActivity.class);


                    List<String> categories = new ArrayList<String>();
                    categories.add("ADD");
                    for(int i=0;i<=100;i++){
                        categories.add(""+i);
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categories);

                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    mSpinner.setAdapter(dataAdapter);


                }
            }


            public StoreAdapter(Context context, List<Vegitable> movieList) {
                this.context = context;
                this.movieList = movieList;
            }

            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout, parent, false);

                return new MyViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, final int position) {
                final Vegitable movie = movieList.get(position);
                Glide.with(getActivity()).load(movie.getImage())
                        .into(holder.thumbnail);
                holder.name.setText(movie.getName());
                holder.price.setText(String.valueOf(movie.getPrice()));
                holder.id.setText(String.valueOf(movie.getid()));
               //  imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
                //  imageLoader.get(movie.getImage(), ImageLoader.getImageListener(holder.thumbnail, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
                 // imageLoader.get(movie.getImage(), ImageLoader.getImageListener(holder.circularImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));


                holder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // On selecting a spinner ite
                        String item = parent.getItemAtPosition(position).toString();
                        if (!"ADD".equals(item)) {
                            amount = amount + movie.getPrice();

                            Fruit_name = movie.getName();
                            Fruit_Image=movie.getImage();
                             Fruit_id = movie.getid();
                            Fruit_weight=item;
                            AddData();
                            Intent intent = getActivity().getIntent();
                            intent.putExtra("key", amount);
                           intent.putExtra("image",  Fruit_Image);
                           intent.putExtra("Name", Fruit_name );
                           intent.putExtra("id",Fruit_id );
                           intent.putExtra("kg",Fruit_weight );     //android studio se apne phone ko connect krde...usb k thro
                            Toast.makeText(getActivity(), ""+Fruit_weight, Toast.LENGTH_SHORT).show();
                          startActivity(intent);

                            // Showing selected spinner item
                            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        amount = amount - movie.getPrice();
                    }
                });


            }
            public boolean AddData() {


            boolean inserted=  myDB.insertData(
                        Fruit_name, amount,Fruit_id,Fruit_Image,Fruit_weight
                );
if(inserted==true) {
    Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_LONG).show();
}else{
    Toast.makeText(getActivity(), " not Data Inserted", Toast.LENGTH_LONG).show();
}
return true;

            }
            @Override
            public int getItemCount() {
                return movieList.size();
            }
        }
   }