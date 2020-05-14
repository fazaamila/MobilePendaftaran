package com.example.modul6;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;


public class read extends AppCompatActivity  implements ListView.OnItemClickListener{

    private ListView listView;
    private String JSON_STRING;

    private void showMahasiswa(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(konﬁgurasi.TAG_ID);
                String nama = jo.getString(konﬁgurasi.TAG_NAMA);
                HashMap<String,String> mahasiswa = new HashMap<>();
                mahasiswa.put(konﬁgurasi.TAG_ID,id);
                mahasiswa.put(konﬁgurasi.TAG_NAMA,nama);
                list.add(mahasiswa);
            }
    }catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                read.this, list, R.layout.list_item,
                new String[]{konﬁgurasi.TAG_ID,konﬁgurasi.TAG_NAMA},
                new int[]{R.id.id, R.id.nama});

        listView.setAdapter(adapter);
    } private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                JSON_STRING = s;
                showMahasiswa();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konﬁgurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int posion, long id) {
        Intent intent = new Intent(this, select.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(posion);
        String mhsId = map.get(konﬁgurasi.TAG_ID).toString();
        intent.putExtra(konﬁgurasi.MHS_ID,mhsId);
        startActivity(intent);
    }
}

