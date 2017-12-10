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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class membersScreen extends AppCompatActivity {

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


    public int sizeMemberList;
    public List<String> names = new ArrayList<String>();
    public List<String> ingressDate = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_screen);
        ListView memberList = (ListView)findViewById(R.id.membersList);
        try{
            JSONObject obj = new JSONObject(loadJSONFromAsset("members.json"));
            JSONArray membersList = obj.getJSONArray("members");
            sizeMemberList = membersList.length();
            for(int i = 0; i < sizeMemberList; i++){
                JSONObject jsonObj = membersList.getJSONObject(i);
                names.add(jsonObj.getString("name"));
                ingressDate.add(jsonObj.getString("ingressDate"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        membersCustomAdapter membersCustomAdapter = new membersCustomAdapter();
        memberList.setAdapter(membersCustomAdapter);
        


    }

    class membersCustomAdapter extends BaseAdapter{
        @Override
        public int getCount(){
            return sizeMemberList;
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
            view = getLayoutInflater().inflate(R.layout.custom_members_screen_layout, null);

            ImageView imageView = (ImageView)view.findViewById(R.id.memberPhoto);
            TextView memberName = (TextView)view.findViewById(R.id.memberName);
            TextView joinDate = (TextView)view.findViewById(R.id.joinDate);

            memberName.setText(names.get(i));
            joinDate.setText(ingressDate.get(i));

            return view;
        }
    }

}
