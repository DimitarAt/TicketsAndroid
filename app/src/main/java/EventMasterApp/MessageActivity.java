package EventMasterApp;

import EventMaster.App.Buisness.EventDTO;
import EventMaster.App.Buisness.MessageDTO;
import EventMaster.App.Buisness.TicketsAppBackbone;
import android.content.Intent;
import android.telephony.SmsMessage;
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
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketsandroid.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    String usernameContact;
    TextView usernameTV,messageTV;
    EditText messageEdit;

    ActivityResultLauncher activityResultLauncherGetMessages =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                    , new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            usernameContact=result.getData().getStringExtra("choice");
                            MessageDTO messageDTO=TicketsAppBackbone.getMessageDTOS()
                                    .get(Integer. parseInt(usernameContact));
                            usernameTV.setText(messageDTO.getSender());
                            messageTV.setText(messageDTO.getMessage());
                        }
                    });

    ActivityResultLauncher activityResultLauncherGetUsers =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                    , new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                           ///////////
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        usernameTV=findViewById(R.id.message_user_name);
        messageTV=findViewById(R.id.message_last_message);
        messageEdit=findViewById(R.id.message_edit);
    }

    public void OnButtonGetMessagesClick(View view){
        Intent intent=new Intent(this,ChoiceActivity.class);
        intent.putExtra("url","/tickets/customer/messages");
        intent.putExtra("message","message");
        activityResultLauncherGetMessages.launch(intent);
    }

    public void OnButtonGetUsersClick(View view){

    }
    public void OnButtonExitClick(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void OnButtonSendMessageClick(View view){
        MessageDTO messageToSend=new MessageDTO();
        messageToSend.setMessage(messageEdit.getText().toString());
        messageToSend.setRecipient(usernameTV.getText().toString());
        String url="/tickets/customer/messages";
        SendMessage(messageToSend,url);
        OnButtonExitClick(view);
        }

        private void SendMessage (MessageDTO message,String url) {

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            Gson gson=new Gson();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    TicketsAppBackbone.getUrl()+
                            url,
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
                    params.put("messageDTO",gson.toJson(message));
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

