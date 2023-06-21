package com.example.assignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TAdapter extends RecyclerView.Adapter<TAdapter.TVH> {

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

    @Override
    public void onBindViewHolder(@NonNull TVH holder, int position){

        holder.Tname.setText(TList.get(position).getTname());
        holder.Tcategory.setText(TList.get(position).getCategory());
        holder.Tdifficulty.setText(TList.get(position).getDifficulty());
        holder.Tlike.setText(TList.get(position).getSLike());
        holder.TendD.setText(TList.get(position).getEndDate());

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

        TextView Tname, Tcategory, Tdifficulty,TendD, Tlike;

        public TVH(@NonNull View itemView) {
            super(itemView);

            Tname = (TextView) itemView.findViewById(R.id.TournamentN);
            Tcategory = (TextView) itemView.findViewById(R.id.TournamentC);
            Tdifficulty = (TextView) itemView.findViewById(R.id.TournamentD);
            TendD = (TextView) itemView.findViewById(R.id.TendD);
            Tlike = (TextView) itemView.findViewById(R.id.Tlike);


        }
    }

}
