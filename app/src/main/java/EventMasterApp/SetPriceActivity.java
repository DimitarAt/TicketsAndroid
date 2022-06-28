package EventMasterApp;

import EventMaster.App.Buisness.SeatsDTO;
import EventMaster.App.Buisness.TicketsAppBackbone;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketsandroid.R;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetPriceActivity extends AppCompatActivity {

    TextView headerText,seatText,seatTopText,seatMiddleText,seatMoneyText;
    EditText inputSeatPrice;
    ArrayList<SeatsDTO> seats;

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_price);
        String placeId=getIntent().getStringExtra("placeId");
        String url=getIntent().getStringExtra("url");
        GetObjectFromUrl(url,placeId);

        headerText=findViewById(R.id.seats_text_header);
        seatText=findViewById(R.id.seats_describe_input);
        inputSeatPrice=findViewById(R.id.seats_edit_text);
        seatTopText=findViewById(R.id.seats_describe_top);
        seatMoneyText=findViewById(R.id.seat_describe_edit_text_top);
        seatMiddleText=findViewById(R.id.seat_describe_middle);



    }

    public void OnButtonNextClicked(View view){
        i++;
        if(i==seats.size())i=0;
        SetUpViews();
    }
    public void OnButtonPreviousClicked(View view){
        i--;
        if(i<0)i=(seats.size()-1);
        SetUpViews();

    }
    private void SetUpViews(){
        seatTopText.setText(seats.get(i).getDescription());
        seatMiddleText.setText("type of seats: "+seats.get(i).getType());
        seatText.setText("available seats: "+seats.get(i).getNumber());
        inputSeatPrice.setText(String.valueOf(seats.get(i).getPrice()));
    }

    public void OnButtonSaveClick(View view){
        String input=inputSeatPrice.getText().toString();
        seats.get(i).setPrice(Integer.parseInt(input));
    }
    public void OnButtonExitClicked(View view){
        TicketsAppBackbone.setDataSource(seats);
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }
    public void PopulateData(){
            seats=new ArrayList<>();
            ArrayList<Object> l=TicketsAppBackbone.getObjectList();
            for (Object obj:l
            ) {
                seats.add((SeatsDTO) obj);
            }

        headerText.setText("THere are  "+seats.size()+" different types of seats.");
        seatMoneyText.setText("price in Lv.");
        SetUpViews();
    }

    public void GetObjectFromUrl(String url, String placeId){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
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
                                list.add(gson.fromJson(response.getString(i),SeatsDTO.class));
                                i++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }}
                        TicketsAppBackbone.setObjectList(list);
                        PopulateData();

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

                        params.put("placeId",placeId);

                return params;
            }};
        queue.add(stringRequest);
    }



}