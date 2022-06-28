package EventMaster.App.Buisness;

import EventMasterApp.AdminActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestService  {
    private HashMap<String ,String > map;

    public RequestService() {
       map= new HashMap<String,String>();
    }

    public RequestService(HashMap<String, String> map) {
        this.map = map;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

    public HashMap<String,String> GetMapFromUrl(Context context, String url, HashMap<String,String> addParams){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                TicketsAppBackbone.getUrl()+url,
                new Response.Listener<String>()  {

                    @Override
                    public void onResponse(String response) {
                       setMap((HashMap<String, String>) Arrays.stream(response.split(","))
                                .map(entry ->  entry.split("="))
                                .collect(Collectors.toMap(entry -> entry[0]
                                        , entry -> entry[1])));
                        TicketsAppBackbone.setChoiceData(getMap());


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
        return map;
    }
    public void GetObjectFromUrl(Context context, String url
            ,HashMap<String,String> addParams,Object obj){

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET,
                TicketsAppBackbone.getUrl()+url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();

                        ArrayList<Object> list=new ArrayList<>();
                        int i=0;
                        while(true) {
                            try {
                                if (response.isNull(i)) break;
                               list.add(gson.fromJson(response.getString(i),obj.getClass()));
                                i++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }}
                        TicketsAppBackbone.setObjectList(list);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                        }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", TicketsAppBackbone.getJwtToken());
                if(addParams!=null){
                    for (String key:addParams.keySet()
                    ) {
                        params.put(key,addParams.get(key));
                    }

                }
                return params;
            }};
        queue.add(stringRequest);
    }

}
