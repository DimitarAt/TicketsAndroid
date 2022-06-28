package EventMasterApp;

import EventMaster.App.Buisness.TicketsAppBackbone;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketsandroid.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    private final String LoginUrl= TicketsAppBackbone.getUrl()+"/login";
    EditText usernameText;
    TextView textViewRes;
    EditText passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RequestQueue queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        usernameText= findViewById(R.id.textViewUsername);
        textViewRes = findViewById(R.id.textViewLoginMessage);
        passwordText = findViewById((R.id.textViewPassword));
    }
public void SubmitLogin(View view) {

    String usernameStr = usernameText.getText().toString();
    String passwordStr = passwordText.getText().toString();
    if (CheckIfInputIsValid(usernameStr, passwordStr)) {
        SendLoginRequest(LoginUrl,usernameStr,passwordStr);
       SendLoginRequest(LoginUrl,usernameStr,passwordStr);       //////
    } else {
        textViewRes.setText(R.string.login_text_box_result_unsuitable_input);
    }
}


    public void GoToRegister(View view){
        textViewRes.setText("registration called");
    Intent intentRegister=new Intent(LogInActivity.this,RegisterActivity.class);
   startActivity(intentRegister);

}

    public boolean CheckIfInputIsValid (String username,String password){
        boolean usernameIsValid =username!=null&&username.trim()!="";
        boolean passwordIsValid =password!=null&&username.trim()!="";
        return usernameIsValid&&passwordIsValid;
    }

    private void SendLoginRequest(String requestUrl,String username,String password){
      RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String unreadMessages=response.split(",")[0];
                        TicketsAppBackbone.setJwtToken(response.split(",")[1]);

                        GoToMainActivity(unreadMessages);
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
                params.put("name", username);
                params.put("password",password);
                return params;
            }};
        queue.add(stringRequest);
    }
    public void GoToMainActivity(String newMessages){
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("newMessages",newMessages);
        startActivity(intent);
    }


}