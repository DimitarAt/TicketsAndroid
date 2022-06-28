package EventMasterApp;

import EventMaster.App.Buisness.TicketsAppBackbone;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.ticketsandroid.R;

public class MainActivity extends AppCompatActivity {
    private final String url=TicketsAppBackbone.getUrl()+"/tickets/admin/users";
    TextView messagesTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messagesTV=findViewById(R.id.main_text_new_messages);
        String newMessages=getIntent().getStringExtra("newMessages");
        messagesTV.setText("You have "+newMessages+" new messages");
    }
    public void SendMessage(View view){
        Intent intent = new Intent(this, DistributorActivity.class);
        startActivity(intent);

    }
    public void LogIn(View view){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
    public void OnButtonLogOutClick(View view){
        TicketsAppBackbone.setJwtToken("");
        Toast.makeText(this,"You just logged out!",Toast.LENGTH_LONG).show();
    }

    public void GoToAdminActivity(View view){
        Intent intent=new Intent(this, AdminActivity.class);
        startActivity(intent);
    }
    public void onButtonDistributorClick(View view){
        Intent intent=new Intent(this,DistributorActivity.class);
        startActivity(intent);
    }
    public void OnButtonOrganizerClick(View view){
        Intent intent=new Intent(getApplicationContext(),OrganizerActivity.class);
        startActivity(intent);
    }
    public void OnButtonMessagesClick(View view){
        Intent intent=new Intent(this, MessageActivity.class);
        intent.putExtra("message","message");
        startActivity(intent);
    }
}