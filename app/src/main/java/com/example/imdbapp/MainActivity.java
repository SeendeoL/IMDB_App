package com.example.imdbapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String JSON_URL="https://api.themoviedb.org/3/movie/550?api_key=b7905d994d254e56281bb7a4f5cd220b";
    List<MovieModelClass> movieList;
    RecyclerView recyclerView;
    ImageView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        header =  findViewById(R.id.imageView2);
        Glide.with(activity: this)
            .load(R.drawable.header)
            .into(header);

        GetData getData=new GetData();
        getData.execute();
    }
    public class GetData extends AsyncTask<String , String, String>{
    @Override
        protected String doInBackground(String...String){
        String current= "";
        try {
            URL url;
            HttpURLConnection urlConnection= null;

            try {
                url= new URL(JSON_URL);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream is = URLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                int data = isr.read();
                while (data != -1){
                    current+= (char) data;
                    data= isr.read();
                }
                return current;
            } catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(URLConnection!=null){
                    urlConnection.disconnect();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    return current;
    }
    @Override
        protected void onPostExecute(String s){

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray =jsonObject.getJSONArray(name:"results");

            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonObject.getJSONObject(i);

                MovieModelClass model = new MovieModelClass();
                model.setId(jsonObject1.getString(name:"vote_average"));
                model.setName(jsonObject1.getString(name:"title"));
                model.setImg(jsonObject1.getString(name:"poster_path"));

                movieList.add(model);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        putDataIntoRecyclerView(movieList);
    }
    }

    private void putDataIntoRecyclerView(List<MovieModelClass> movieList){

        Adaptery adaptery = new Adaptery(mContext:this, movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context:this));
        recyclerView.setAdapter(adaptery);
    }
}