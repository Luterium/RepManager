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

public class marketList extends AppCompatActivity {

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


    public int sizeMarketList;
    public List<String> produtoNome = new ArrayList<String>();
    public List<String> produtoQuantidade = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_list);
        ListView marketList = (ListView)findViewById(R.id.marketList);
        try{
            JSONObject obj = new JSONObject(loadJSONFromAsset("marketList.json"));
            JSONArray membersList = obj.getJSONArray("marketList");
            sizeMarketList = membersList.length();
            for(int i = 0; i < sizeMarketList; i++){
                JSONObject jsonObj = membersList.getJSONObject(i);
                produtoNome.add(jsonObj.getString("name"));
                produtoQuantidade.add(jsonObj.getString("quantidade"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        marketCustomAdapter  marketCustomAdapter  = new marketCustomAdapter();
        marketList.setAdapter(marketCustomAdapter);


    }

    class marketCustomAdapter extends BaseAdapter{
        @Override
        public int getCount(){
            return sizeMarketList;
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
            view = getLayoutInflater().inflate(R.layout.custom_market_screen_layout, null);

            ImageView imageView = (ImageView)view.findViewById(R.id.marketItem);
            TextView productName = (TextView)view.findViewById(R.id.nomeProduto);
            TextView productQuantity = (TextView)view.findViewById(R.id.quantidadeProduto);


            productName.setText(produtoNome.get(i));
            productQuantity.setText(produtoQuantidade.get(i));

            return view;
        }
    }

}



