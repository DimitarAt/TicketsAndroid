package EventMasterApp;

import EventMaster.App.Buisness.TicketsAppBackbone;
import EventMaster.App.Buisness.recyclerViewAdminData;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketsandroid.R;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity implements RecyclerInterface {

    ArrayList<recyclerViewAdminData> dataList=new ArrayList<recyclerViewAdminData>();
    private final String url=TicketsAppBackbone.getUrl()+"/tickets/admin/users";
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TicketsAppBackbone.setAdminD(new ArrayList<recyclerViewAdminData>());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView=findViewById(R.id.admin_recycler);

        SetUpData();

        AdminRecyclerViewAdapter adapter=new AdminRecyclerViewAdapter(this,TicketsAppBackbone.getAdminD(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    private void SetUpData(){


            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            Gson gson=new Gson();

                            recyclerViewAdminData data;
                            ArrayList<String> list=new ArrayList<>();
                            int i=0;
                            while(true) {
                                try {
                                    if (response.isNull(i)) break;
                                    TicketsAppBackbone.getAdminD().add(gson.fromJson(response.getString(i),recyclerViewAdminData.class));
//                                    RecyclerView re= findViewById(R.id.admin_recycler);

                                    i++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }}
                            recyclerView.getAdapter().notifyDataSetChanged();

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
    @Override
    public void OnClicRow(int position) {
        String str=String.valueOf(position);
        Intent intent =new Intent(getApplicationContext(),AdminActivityRoles.class);
        intent.putExtra("customer",str);
        startActivity(intent);
    }


}