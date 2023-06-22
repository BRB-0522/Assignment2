package com.example.assignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TAdapter extends RecyclerView.Adapter<TAdapter.TVH> {

    Calendar start, end;
    private Context context;
    private List<Tournament> TList;

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos, Tournament T);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public TAdapter(Context context, List<Tournament>TList){
        this.context = context;
        this.TList = TList;
    }

    @NonNull
    @Override
    public TVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v ;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.t_item, parent,false);
        return new TVH(v);
    }

    public int SCheck(String s, String e) throws ParseException {
        int i=0;
        Calendar now = Calendar.getInstance();
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        Date SD = sdf.parse(s);
        Date ED = sdf.parse(e);

        start = Calendar.getInstance();
        end = Calendar.getInstance();

        start.setTime(SD);
        end.setTime(ED);

        if(now.before(end)!=true){
            i = 1;
            //Past
        }else if(now.after(start)!=true){
            i = 2;
            //upcoming
        }

        return i;
    }

    @Override
    public void onBindViewHolder(@NonNull TVH holder, int position){

        int i;
        String status="";

        holder.Tname.setText(TList.get(position).getTname());
        holder.Tcategory.setText(TList.get(position).getCategory());
        holder.Tdifficulty.setText(TList.get(position).getDifficulty());
        holder.Tlike.setText(TList.get(position).getSLike());
        holder.TendD.setText(TList.get(position).getEndDate());

        try {
            i = SCheck(TList.get(position).getStartDate(),TList.get(position).getEndDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        switch (i){
            case 0: status ="Ongoing"; break;
            case 1: status ="Past"; break;
            case 2: status ="Upcoming"; break;
        }

        holder.TStatus.setText(status);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if(pos!= RecyclerView.NO_POSITION){
                    Tournament T = TList.get(pos);
                    if(itemClickListener != null){
                        itemClickListener.onItemClick(v, pos, T);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return TList.size();
    }


    public static class TVH extends RecyclerView.ViewHolder{

        TextView Tname, Tcategory, Tdifficulty,TendD, Tlike, TStatus;

        public TVH(@NonNull View itemView) {
            super(itemView);

            Tname = (TextView) itemView.findViewById(R.id.TournamentN);
            Tcategory = (TextView) itemView.findViewById(R.id.TournamentC);
            Tdifficulty = (TextView) itemView.findViewById(R.id.TournamentD);
            TendD = (TextView) itemView.findViewById(R.id.TendD);
            Tlike = (TextView) itemView.findViewById(R.id.Tlike);
            TStatus = (TextView) itemView.findViewById(R.id.status);


        }
    }

}
