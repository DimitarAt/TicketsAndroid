package EventMasterApp;

import EventMaster.App.Buisness.*;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketsandroid.R;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ChoiceActivity extends AppCompatActivity implements RecyclerInterface{
    RecyclerView choiceRecycler;
    private HashMap<String ,String > map;
    public HashMap<String, String> getMap() {
        return map;
    }
    boolean toggle=false;

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        choiceRecycler=findViewById(R.id.choice_recycler);

        map=new HashMap<>();
        String url=getIntent().getStringExtra("url");
        if(getIntent().getStringExtra("message")!=null){
            GetMapFromUrlJSON(url);

        }
        else{GetMapFromUrl(this ,url,null);}

    }

    public void CallRecycler(){
        ChoiceActivityRecyclerAdapter adapter=new ChoiceActivityRecyclerAdapter(getApplicationContext(),
                map,this);
        choiceRecycler.setAdapter(adapter);
        choiceRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void OnClicRow(int position) {
        String result=(new ArrayList<String>(map.keySet())).get(position);
        Intent intent=new Intent();
        intent.putExtra("choice",result);
        setResult(RESULT_OK,intent);
        finish();
    }
    public void GetMapFromUrl(Context context, String url, HashMap<String,String> addParams){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                TicketsAppBackbone.getUrl()+url,
                new Response.Listener<String>()  {

                    @Override
                    public void onResponse(String response) {


                            setMap((HashMap<String, String>) Arrays.stream(response.split(","))
                                    .map(entry -> entry.split("="))
                                    .collect(Collectors.toMap(entry -> entry[0]
                                            , entry -> entry[1])));
                            TicketsAppBackbone.setChoiceData(getMap());
                            CallRecycler();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        error.toString(),Toast.LENGTH_LONG).show();}
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", TicketsAppBackbone.getJwtToken());
                if(addParams!=null){
                    for (String key:addParams.keySet()
                    ) {
                        params.put(key,params.get(key));
                    }

                }
                return params;
            }};
        queue.add(stringRequest);
    }
    private void GetMapFromUrlJSON(String url){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET,
                TicketsAppBackbone.getUrl()+url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();

                        recyclerViewAdminData data;
                        ArrayList<MessageDTO> list=new ArrayList<>();
                        HashMap<String,String> mapMessages=new HashMap<>();
                        int i=0;
                        while(true) {
                            try {
                                if (response.isNull(i)) break;
                                list.add(gson.fromJson(response.getString(i),MessageDTO.class));
                                mapMessages.put(String.valueOf(i),list.get(i).getMessage());
                                i++;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }}
                        TicketsAppBackbone.setChoiceData(mapMessages);
                        TicketsAppBackbone.setMessageDTOS(list);
                        map=mapMessages;
                        CallRecycler();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.toString(),Toast.LENGTH_LONG).show();}
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", TicketsAppBackbone.getJwtToken());
                return params;
            }};
        queue.add(stringRequest);
    }


}