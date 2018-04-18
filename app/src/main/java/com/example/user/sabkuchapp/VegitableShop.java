package com.example.user.sabkuchapp;

/**
 * Created by user on 2/23/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class VegitableShop extends Fragment {
//hi
    private static final String TAG = VegitableShop.class.getSimpleName();
    private static final String URL = "http://sabkuch.co.in/sabkuckapp/fruits.php";
int amount=0;
    private RecyclerView recyclerView;
    private List<Vegitable> movieList;
    private StoreAdapter mAdapter;
    Intent intent;

    public VegitableShop() {
        // Required empty public constructor
    }

    public static  VegitableShop newInstance(String param1, String param2) {
        VegitableShop fragment = new VegitableShop ();
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

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        fetchStoreItems();

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
        final int[]  count = {0};
        public class MyViewHolder extends RecyclerView.ViewHolder {
            CheckBox c1;
            public TextView name, price,txtCount,id;
            public ImageView thumbnail;
            int amount=0;
public Button buttonInc,buttonDec,orderNow;
            public MyViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.textView);
                price = view.findViewById(R.id.price);
                id = view.findViewById(R.id.id);
                c1= (CheckBox)view. findViewById(R.id.cb1);
                thumbnail = view.findViewById(R.id.image);

                intent =new Intent(context,BillActivity.class);
                final TextView txtCount =(TextView)view. findViewById(R.id.txt);
                Button buttonInc= (Button) view.findViewById(R.id.button1);
                Button buttonDec= (Button) view.findViewById(R.id.button2);
                 orderNow= (Button) view.findViewById(R.id.order);

                buttonInc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count[0]++;
                        txtCount.setText(String.valueOf(count[0]));

                    }
                });

                buttonDec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count[0]--;
                        txtCount.setText(String.valueOf(count[0]));

                    }
                });
                orderNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  Toast.makeText(context, ""+movieList.getid, Toast.LENGTH_SHORT).show();
                    }
                });
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
            holder.name.setText(movie.getName());
           holder.price.setText(String.valueOf(movie.getPrice()));
            holder.id.setText(String.valueOf(movie.getid()));
         //   imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
         //   imageLoader.get(movie.getImage(), ImageLoader.getImageListener(holder.thumbnail, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
         //   imageLoader.get(rc.getProfileImage(), ImageLoader.getImageListener(holder.circularImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
            Glide.with(context)
                  .load(movie.getImage())
                   .into(holder.thumbnail);
           holder.c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        amount=amount+30*count[0];
                        intent.putExtra("key",amount);
                        intent.putExtra("momos",movie.getName());
                        startActivity(intent);

                    }
                    else{ amount=amount-movie.getPrice();
                      //  intent.putExtra("key",amount);
                       // startActivity(intent);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }
    }
}
