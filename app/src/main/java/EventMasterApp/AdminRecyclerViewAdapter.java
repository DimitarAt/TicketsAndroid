package EventMasterApp;

import EventMaster.App.Buisness.recyclerViewAdminData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ticketsandroid.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdminRecyclerViewAdapter
        extends RecyclerView.Adapter<AdminRecyclerViewAdapter.AdminViewHolder>{
private ArrayList<recyclerViewAdminData> adminDataList;
private Context context;
private final RecyclerInterface recyclerInterface;

    public AdminRecyclerViewAdapter(Context context
            , ArrayList<recyclerViewAdminData> adminDataList
            , RecyclerInterface recyclerInterface) {
        this.adminDataList = adminDataList;
        this.context = context;
        this.recyclerInterface = recyclerInterface;
    }

    @NonNull
    @NotNull
    @Override
    public AdminRecyclerViewAdapter.AdminViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
       View view=inflater.inflate(R.layout.admin_recycler_row,parent,false);

        return new AdminRecyclerViewAdapter.AdminViewHolder(view, recyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdminRecyclerViewAdapter.AdminViewHolder holder, int position) {

        holder.nameView.setText(adminDataList.get(position).getName());
        holder.userNameView.setText(adminDataList.get(position).getUsername());
        holder.rolesView.setText((adminDataList.get(position).getRoles()));


    }

    @Override
    public int getItemCount() {
        return adminDataList.size();
    }



    public static class AdminViewHolder extends RecyclerView.ViewHolder{

        TextView nameView, userNameView,rolesView;

        public AdminViewHolder(@NonNull @NotNull View itemView,
                               RecyclerInterface recyclerInterface) {
            super(itemView);
            nameView=itemView.findViewById(R.id.admin_recycler_name);
            userNameView=itemView.findViewById(R.id.admin_recycler_username);
            rolesView=itemView.findViewById(R.id.admin_recycler_roles);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerInterface !=null){
                        int position=getBindingAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            recyclerInterface.OnClicRow(position);
                        }
                    }
                }
            });
        }
    }
}
