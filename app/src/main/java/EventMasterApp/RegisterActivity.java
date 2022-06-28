package EventMasterApp;


import EventMaster.App.Buisness.TicketsAppBackbone;
//import ServerTickets.DTO.CustomerDTO;
import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketsandroid.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    EditText firstNameT;
    EditText famNameT;
    EditText phoneT;
    EditText eMailT;
    EditText passwordT;
    EditText userNameT;
    EditText passwordReT;
    String firstName,famName,eMail,userName,password,passwordRe,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstNameT=findViewById(R.id.RegisterPersonName);
        famNameT=findViewById(R.id.RegisterPersonFamName);
        phoneT=findViewById(R.id.RegisterPersonPhoneNumber);
        eMailT=findViewById(R.id.RegisterPersonEMail);
        userNameT=findViewById(R.id.RegisterPersonUserName);
        passwordT=findViewById(R.id.RegisterPersonPassword);
        passwordReT=findViewById(R.id.RegisterPersonPasswordRe);
    }

    public void onSubmitButtonClick(View view){
        firstName=firstNameT.getText().toString();
        famName=famNameT.getText().toString();
        phone=phoneT.getText().toString();
        eMail=eMailT.getText().toString();
        userName=userNameT.getText().toString();
        password=passwordT.getText().toString();
        passwordRe=passwordReT.getText().toString();

        if(PasswordIntegrityCheck()){
      Register();
    }
    }


    private boolean PasswordIntegrityCheck(){
        if(password.equals(passwordRe))return true;
        else {
            Toast.makeText(getApplicationContext(),
                    "password doesn't match to the original one"
                    ,Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public  void Register(){

        String requestUrls= "http://192.168.0.149:8080/tickets/register/check_by_name";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String isUserPersisted) {
                        if(isUserPersisted.isEmpty()){ RegisterUser();
                             }
                        else { Toast.makeText(getApplicationContext(),
                                "such user already exists. Try another username!"
                                ,Toast.LENGTH_SHORT).show();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.toString()
                        ,Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", TicketsAppBackbone.getJwtToken());
                params.put("name",userName );
                return params;
            }};
        RequestQueue queue = Volley.newRequestQueue(this);
       queue.add(stringRequest);
    }

    private void RegisterUser(){
                String requestUrls=TicketsAppBackbone.getUrl()+"/tickets/register/persist";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrls,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(),
                                        "You have been successfully registered! Please, log in!"
                                        ,Toast.LENGTH_LONG).show();
                                GoToMainActivity();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                error.toString()
                                ,Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("Authorization", TicketsAppBackbone.getJwtToken());
                        return params;
                    }
                    @Override
                    public Map getParams() {
                        Map params = new HashMap();

                        JSONObject json=new JSONObject();
                        try {
                            json.put("userName",userName );
                            json.put("firstName", firstName);
                            json.put("famName", famName);
                            json.put("phone", phone);
                            json.put("eMail", eMail);
                            json.put("password", password);
                    } catch (JSONException e) {
                    e.printStackTrace();
                }
                        params.put("jsonCustomer",json.toString() );
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(stringRequest);
            }
            public void GoToMainActivity (){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

            }

}