package EventMasterApp;

import EventMaster.App.Buisness.RequestService;
import EventMaster.App.Buisness.TicketsAppBackbone;
import EventMaster.App.Buisness.recyclerViewAdminData;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketsandroid.R;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DistributorActivity extends AppCompatActivity {


    Map<String,String> mapData;
    RequestService requestService;
    String choice;
    TextView tv;

    ActivityResultLauncher activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                    , new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            choice=result.getData().getStringExtra("choice");
            requestService =new RequestService();
            requestService.GetMapFromUrl(getApplicationContext() ,"/tickets/distributor/events/tickets",null);

        }
    });

    public Map<String, String> getMapData() {
        return mapData;
    }

    public void setMapData(Map<String, String> mapData) {
        this.mapData = mapData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);
        tv=findViewById(R.id.distributor_text_view);

        requestService =new RequestService();
        requestService.GetMapFromUrl(this ,"/tickets/distributor/events",null);


    }

    public void OnButtonDistributorClick(View view){

    }

    public  void OnButtonDistributorChoiceClick(View view){
        Intent intent = new Intent(this, ChoiceActivity.class);
        activityResultLauncher.launch(intent);


    }



}