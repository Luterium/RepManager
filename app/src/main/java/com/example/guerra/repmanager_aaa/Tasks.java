package com.example.guerra.repmanager_aaa;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tasks extends AppCompatActivity {

    public String loadJSONFromAsset(String file) {
        String json = null;
        try {

            InputStream is = getAssets().open(file);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public int sizeTaskList;
    public List<String> responsibles = new ArrayList<String>();
    public List<String> tasks = new ArrayList<String>();
    public List<String> dates = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ListView listView = (ListView)findViewById(R.id.listView);

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("taskList.json"));
            JSONArray taskList = obj.getJSONArray("tasks");
            sizeTaskList = taskList.length();
            for (int i = 0; i < taskList.length(); i++){

                JSONObject jsonObj = taskList.getJSONObject(i);
                responsibles.add(jsonObj.getString("responsible"));
                tasks.add(jsonObj.getString("task"));
                dates.add(jsonObj.getString("date"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount(){
            return sizeTaskList;
        }

        @Override
        public Object getItem(int i){
            return null;
        }

        @Override
        public long getItemId(int i){
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup){

            view = getLayoutInflater().inflate(R.layout.custom_task_layout, null);
//            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView taskView = (TextView)view.findViewById(R.id.taskView);
            TextView responsibleViewContent = (TextView)view.findViewById(R.id.responsibleViewContent);
            TextView dataViewContent = (TextView)view.findViewById(R.id.dataViewContent);

            responsibleViewContent.setText(responsibles.get(i));
            taskView.setText(tasks.get(i));
            dataViewContent.setText(dates.get(i));


            return view;
        }

    }
}

