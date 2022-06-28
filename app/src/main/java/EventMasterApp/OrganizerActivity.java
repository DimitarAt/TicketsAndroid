package EventMasterApp;

import EventMaster.App.Buisness.EventDTO;
import EventMaster.App.Buisness.RequestService;
import EventMaster.App.Buisness.SeatsDTO;
import EventMaster.App.Buisness.TicketsAppBackbone;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketsandroid.R;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrganizerActivity extends AppCompatActivity {
    TextView tv,placeTV;
    EditText nameET,descriptionET,typeET,dateTV;
    String placeId;
    String eventId;
    EventDTO eventDTO;
    ArrayList<SeatsDTO> seatsDTOlist;
    RequestService requestService;
    HashMap<String,String> events;

    ActivityResultLauncher activityResultLauncherPlaces =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                    , new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            placeId=result.getData().getStringExtra("choice");

                            GetPlacebyId(placeId);
                            GetSeatsForPlace(placeId);

                        }
                    });

    ActivityResultLauncher activityResultLauncherGetSeatPrice =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                    , new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            seatsDTOlist=TicketsAppBackbone.getDataSource();

                        }
                    });
    ActivityResultLauncher activityResultLauncherGetEvents =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                    , new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            eventId=result.getData().getStringExtra("choice");
                            eventDTO=new EventDTO();
                            GetEventById("/tickets/organizer/event/id",eventId);
                        }
                    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);
        tv=findViewById(R.id.organizer_text_view);
        placeTV=findViewById(R.id.organizer_show_place);
        nameET=findViewById(R.id.organizer_edit_name);
        descriptionET=findViewById(R.id.organizer_edit_description);
        typeET=findViewById(R.id.organizer_edit_type);
        dateTV=findViewById(R.id.organizer_edit_data);
        requestService=new RequestService();
    }


    public void OnButtonChoosePlaceClick(View view){
//        if(placeId==null) {
            Intent intent = new Intent(this, ChoiceActivity.class);
            intent.putExtra("url", "/tickets/organizer/places");
            activityResultLauncherPlaces.launch(intent);
//        }
//        else {
//            GetSeatsForPlace(placeId);
//        }
    }

    private void GetSeatsForPlace(String id){
        Intent intent=new Intent(this,SetPriceActivity.class);
        intent.putExtra("placeId",placeId);
        intent.putExtra("url","/tickets/organizer/places/seats");
        activityResultLauncherGetSeatPrice.launch(intent);

    }


    private void GetPlacebyId(String id){
        HashMap<String,String> parameters =new HashMap<>();
        parameters.put("placeId",id);
//        requestService.GetObjectFromUrl(getApplicationContext()
//               ,"/tickets/organizer/places/seats"
//               ,parameters,new SeatsDTO());
        placeTV.setText(TicketsAppBackbone.getChoiceData().get(id));
    }

    public void OnButtonChooseEventClick(View view){
        Intent intent = new Intent(this, ChoiceActivity.class);
        intent.putExtra("url","/tickets/organizer/events"); //Just for current user from security context
        activityResultLauncherGetEvents.launch(intent);
        
    }

    public void OnButtonSubmitClick(View view) {
        EventDTO newEvent = new EventDTO();
        newEvent.setName(nameET.getText().toString());
        newEvent.setDescription(descriptionET.getText().toString());
        newEvent.setType(typeET.getText().toString());
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);

        try {
            LocalDateTime date = LocalDateTime.parse(dateTV.getText().toString());
        } catch (Exception e) {
        }
        newEvent.setDate(dateTV.getText().toString());
        newEvent.setOrganizerUsername("Empty");
        newEvent.setSeatPrices(ListSeatsDTOToHashmap());
        SubmitEvent(newEvent,"/tickets/organizer/event/post");
        Intent intentMA = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intentMA);
    }

        private void PopulateEventData() {
        nameET.setText(eventDTO.getName());
        typeET.setText(eventDTO.getType());
        descriptionET.setText(eventDTO.getDescription());
        dateTV.setText(eventDTO.getDate().toString());
        GetPlacebyId(String.valueOf(eventDTO.getPlaceId()));


        }

    public void GetEventById(String url,String eventId
           ){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                TicketsAppBackbone.getUrl()+url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson=new Gson();
                            eventDTO=gson.fromJson(response,EventDTO.class);
                             PopulateEventData();
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
                        params.put("eventId",eventId);
                return params;
            }};
        queue.add(stringRequest);
    }
    private HashMap<Long,Integer> ListSeatsDTOToHashmap(){
        HashMap<Long ,Integer> map=new HashMap<>();
        for (Object obj:seatsDTOlist
        ) {
            map.put(((SeatsDTO)obj).getId()
                    ,((SeatsDTO)obj).getPrice());
        }
        return map;
    }

    private void SubmitEvent(EventDTO event,String url){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        Gson gson=new Gson();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                TicketsAppBackbone.getUrl()+url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("event",gson.toJson(event));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", TicketsAppBackbone.getJwtToken());
                return params;
            }};
        queue.add(stringRequest);

    }



    }





