package EventMasterApp;

import EventMaster.App.Buisness.TicketsAppBackbone;
import EventMaster.App.Buisness.recyclerViewAdminData;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.ticketsandroid.R;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminActivityRoles extends AppCompatActivity {

    TextView nameView,roleView,userNameView,resultView;
    Button addButton, undoButton;
    EditText inputRole;
    int position;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       String pos=getIntent().getStringExtra("customer");
        position=Integer.parseInt(pos);
        setContentView(R.layout.activity_admin_roles);
        nameView =findViewById(R.id.text_roles_name);
        nameView.setText( TicketsAppBackbone.getAdminD().get(position).getName());
        roleView=findViewById(R.id.text_roles_roles);
        roleView.setText( TicketsAppBackbone.getAdminD().get(position).getRoles());
      userNameView=findViewById(R.id.text_roles_username);
        userNameView.setText( TicketsAppBackbone.getAdminD().get(position).getUsername());
        addButton=findViewById(R.id.add_role_buuton);
        undoButton=findViewById(R.id.undo_role_button);
        inputRole=findViewById(R.id.text_edit_roles);
        resultView=findViewById(R.id.text_roles_result);
    }

    public void onDeleteButtonListener(View vie){
        String customer=userNameView.getText().toString();
        ManageRole(null,customer,Request.Method.DELETE);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void onClickAddButtonListener(View view){
         String role=inputRole.getText().toString();
        String customer=userNameView.getText().toString();
        if(IsRoleValid(role)){
            if(!IsRoleAcquired(role)){
                ManageRole(role,customer,Request.Method.POST);

            }
            else {resultView.setText("The role is already acquired!");}

        }

    }
    public  void onClickUndoButtonListener(View view){
        String role=inputRole.getText().toString();
        String customer=userNameView.getText().toString();
        if(IsRoleValid(role)){
            if(IsRoleAcquired(role)){
              ManageRole(role,customer,Request.Method.GET);
              roleView.setText(roleView.getText().toString().replace(","+role,""));
            }else { resultView.setText("There isn't such role to remove");}
        }
    }

    private boolean IsRoleValid(String role){
        if (role.equals("ADMIN")||role.equals("ORGANIZER")||role.equals("DISTRIBUTOR"))return true;
        else {resultView.setText("The role is not valid. Please retype it!");
        return false;}

    }
    private boolean IsRoleAcquired (String role) {
        return roleView.getText().toString().contains(role);
    }
    private void ManageRole(String roleM,String customer, int m){
        int method =m;
        String role=roleM;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(method,
                TicketsAppBackbone.getUrl()+"/tickets/admin/manageRole",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if( response.startsWith("1")){
                            String roleV=roleView.getText().toString();
                            switch (method){
                                case Request.Method.GET:{roleV.replace(","+role,""); break;}
                                case Request.Method.POST:{roleV+=(","+role);break;}
                            }
                            roleView.setText(roleV);

                        }
                        resultView.setText(response.substring(1).toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resultView.setText(error.toString());}
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", TicketsAppBackbone.getJwtToken());
                params.put("customer",customer);
                params.put("role",role);
                return params;
            }};
        queue.add(stringRequest);
    }

}