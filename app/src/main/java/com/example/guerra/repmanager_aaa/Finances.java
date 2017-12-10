package com.example.guerra.repmanager_aaa;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Finances extends AppCompatActivity {


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

    public int sizeFinanceList;
    public List<String> contaNome = new ArrayList<String>();
    public List<String> contaValor = new ArrayList<String>();
    public List<String> contaVencimento = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances);
        ListView financeView = (ListView)findViewById(R.id.financesView);
        try{
            JSONObject obj = new JSONObject(loadJSONFromAsset("finances.json"));
            JSONArray membersList = obj.getJSONArray("finance");

            sizeFinanceList = membersList.length();
            for(int i = 0; i < sizeFinanceList; i++){
                JSONObject jsonObj = membersList.getJSONObject(i);
                contaNome.add(jsonObj.getString("name"));
                contaValor.add(jsonObj.getString("valor"));
                contaVencimento.add(jsonObj.getString("vencimento"));

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        financesCustomAdapter financesCustomAdapter = new financesCustomAdapter();
        financeView.setAdapter(financesCustomAdapter);


    }

    class financesCustomAdapter extends BaseAdapter {

        @Override
        public int getCount(){
            return sizeFinanceList;
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
            view = getLayoutInflater().inflate(R.layout.custom_finance_layout, null);

            ImageView imageView = (ImageView)view.findViewById(R.id.financeIcon);
            TextView financeName = (TextView)view.findViewById(R.id.nomeConta);
            TextView financeValue= (TextView)view.findViewById(R.id.valorConta);
            TextView financeDate = (TextView)view.findViewById(R.id.vencimentoConta);

            financeName.setText(contaNome.get(i));
            financeValue.setText(contaValor.get(i));
            financeDate.setText(contaVencimento.get(i));

            return view;
        }

    }
}
