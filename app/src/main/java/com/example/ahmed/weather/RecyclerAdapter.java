package com.example.ahmed.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<WeeklyModel> weekData;
    private Context context;
    private double max, min;
    private String tMax, tMin, url, icon, unit;
    private int flag;
    public RecyclerAdapter (Context context , List<WeeklyModel> weekData, String unit, int flag){
        this.unit = unit;
        this.context = context;
        this.weekData = weekData;
        this.flag=flag;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raws, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        WeeklyModel weeklyModel = weekData.get(position);

        Time dayTime = new Time();
        dayTime.setToNow();
        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);
        dayTime = new Time();
        String day;
        long dateTime;
        dateTime = dayTime.setJulianDay(julianStartDay + position);

        if(position==0)
            day="Today";
        else if(position==1)
            day="Tomorrow";
        else
            day = getReadableDateString(dateTime);


        if(unit.equals("Celsius")){
            max = weeklyModel.getTempMax() -  273.15;
            min = weeklyModel.getTempMin() -  273.15;

            tMax = String.valueOf((int)max) + "°C";
            tMin = String.valueOf((int)min) + "°C";



        }
        else {
            max = (weeklyModel.getTempMax() - 273.15)* 1.8 + 32.;
            min = (weeklyModel.getTempMin() - 273.15)* 1.8 + 32.;

            tMax = String.valueOf((int)max) + "°F";
            tMin = String.valueOf((int)min) + "°F";

        }

        holder.day.setText(day);
        holder.max.setText(tMax);
        holder.min.setText(tMin);
        holder.state.setText(weeklyModel.getMain().toString());

        icon = weeklyModel.getIcon();
        url = "http://openweathermap.org/img/w/" + icon +".png";

        Picasso.with(context).load(url).into(holder.icon);


    }

    private String getReadableDateString(long time){
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
        return shortenedDateFormat.format(time);
    }

    @Override
    public int getItemCount() {
        if(flag==0)
            return weekData.size();
        else
            return 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView day, state, max, min;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            day = (TextView) itemView.findViewById(R.id.day);
            state = (TextView) itemView.findViewById(R.id.state);
            max = (TextView) itemView.findViewById(R.id.max);
            min = (TextView) itemView.findViewById(R.id.min);
            icon = (ImageView) itemView.findViewById(R.id.icon);

        }
    }
}
