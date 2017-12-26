package com.example.ahmed.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.preference.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter recyclerAdapter;
    private ApiInterface apiInterface;
    private static final String baseURL="http://api.openweathermap.org/data/2.5/";
    private Retrofit retrofit;
    private SharedPreferences preferences;
    private String location;
    private String unit;
    private int flag=0;
    private static final String APPID = "138b71014a38a31c8c0085b22593ee3d";


   @Override
    protected void onStart() {
        super.onStart();
        getLocation();
        getUnit();
        getData();
    }


    private void getUnit() {

        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        unit = preferences.getString(getString(R.string.pref_units_key), getString(R.string.pref_units_celsius));

    }

    private void getLocation() {
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        location = preferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id =item.getItemId();
        if(id == R.id.setting){
            Intent intent = new Intent(this, Setting.class);
            startActivity(intent);
        }

        else if(id == R.id.week){
            flag=0;
            onStart();
        }
        else if(id == R.id.current){
            flag=1;
            onStart();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void getData() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit=new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        apiInterface = retrofit.create(ApiInterface.class);


        String loc = "?q="+ location;

        Log.v("a7a3", loc);

        apiInterface.getData(loc,APPID).enqueue(callback);

    }

    Callback<ListWrapper<WeeklyModel>> callback = new Callback<ListWrapper<WeeklyModel>>() {
        @Override
        public void onResponse(Call<ListWrapper<WeeklyModel>> call, Response<ListWrapper<WeeklyModel>> response) {
            Log.v("asd", response.toString());
            ListWrapper<WeeklyModel> data  = response.body();
            recyclerAdapter = new RecyclerAdapter(MainActivity.this ,data.list, unit, flag);
            recyclerView.setAdapter(recyclerAdapter);

        }

        @Override
        public void onFailure(Call<ListWrapper<WeeklyModel>> call, Throwable t) {
            Log.v("asd", "fail");
        }
    };
}
