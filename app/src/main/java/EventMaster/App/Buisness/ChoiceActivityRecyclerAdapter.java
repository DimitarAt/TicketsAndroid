package EventMaster.App.Buisness;

import EventMasterApp.AdminRecyclerViewAdapter;
import EventMasterApp.RecyclerInterface;
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
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ChoiceActivityRecyclerAdapter
        extends RecyclerView.Adapter<ChoiceActivityRecyclerAdapter.ChoiceViewHolder>{
    Context context;
    HashMap<String ,String> choiceData;
    List<String> list;
    RecyclerInterface recyclerInterface;

    public ChoiceActivityRecyclerAdapter(Context context,
                                         HashMap<String, String> choiceData,
                                         RecyclerInterface recyclerInterface) {
        this.context = context;
        this.recyclerInterface = recyclerInterface;
        this.choiceData=choiceData;
        list = new ArrayList<>(choiceData.keySet());

    }

    @NonNull
    @NotNull
    @Override
    public ChoiceActivityRecyclerAdapter.ChoiceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.choice_row,parent,false);

        return new ChoiceActivityRecyclerAdapter.ChoiceViewHolder(view,recyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChoiceActivityRecyclerAdapter.ChoiceViewHolder holder, int position) {

    String s=list.get(position);
    String k=choiceData.get(s);
     holder.choiceText.setText(k);


    }

    @Override
    public int getItemCount() {
        return choiceData.size();
    }

    public static class ChoiceViewHolder extends RecyclerView.ViewHolder {
        TextView choiceText;

        public ChoiceViewHolder(@NonNull @NotNull View itemView, RecyclerInterface recyclerInterface) {
            super(itemView);
            choiceText = itemView.findViewById(R.id.choice_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerInterface != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerInterface.OnClicRow(position);
                        }
                    }
                }
            });

        }

    }


}
