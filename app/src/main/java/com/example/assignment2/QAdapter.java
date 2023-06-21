package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QAdapter extends RecyclerView.Adapter<QAdapter.QVH> {

    private Context context;
    private List<Quiz> QList;

    private int Correct =0;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onItemClick();
    }

    public void setOnItemClickListener(QAdapter.OnItemClickListener listener){
        this.itemClickListener = listener;
    }
    public QAdapter(Context context, List<Quiz> QList){
        this.context = context;
        this.QList = QList;
    }

    @NonNull
    @Override
    public QVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v ;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.q_item, parent,false);
        return new QVH(v);
    }

    public void onBindViewHolder(@NonNull QVH holder, @SuppressLint("RecyclerView") int position){

        holder.question.setText(QList.get(position).getQuestion());
        holder.category.setText(QList.get(position).getCategory());
        holder.difficulty.setText(QList.get(position).getDifficulty());
        holder.b1.setText(QList.get(position).getAnswers().get(0));
        holder.b2.setText(QList.get(position).getAnswers().get(1));
        holder.b3.setText(QList.get(position).getAnswers().get(2));
        holder.b4.setText(QList.get(position).getAnswers().get(3));

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(QList.get(position).getAnswers().get(0)==QList.get(position).getCorrect_answer()){
                    holder.b1.setBackgroundColor(R.color.teal_700);
                    Correct+=1;
                }else{holder.b1.setBackgroundColor(R.color.red);
                    holder.b1.setTextColor(R.color.white);}
                holder.b1.setEnabled(false);
            }
        });
        holder.b2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(QList.get(position).getAnswers().get(1)==QList.get(position).getCorrect_answer()){
                    holder.b2.setBackgroundColor(R.color.teal_700);
                    Correct+=1;
                }else{holder.b2.setBackgroundColor(R.color.red);
                    holder.b2.setTextColor(R.color.white);}
                holder.b2.setEnabled(false);
            }
        });
        holder.b3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(QList.get(position).getAnswers().get(2)==QList.get(position).getCorrect_answer()){
                    holder.b3.setBackgroundColor(R.color.teal_700);
                    Correct+=1;
                }else{holder.b3.setBackgroundColor(R.color.red);
                    holder.b3.setTextColor(R.color.white);}
                holder.b3.setEnabled(false);
            }
        });
        holder.b4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(QList.get(position).getAnswers().get(3)==QList.get(position).getCorrect_answer()){
                    holder.b4.setBackgroundColor(R.color.teal_700);
                    Correct+=1;
                }else{holder.b4.setBackgroundColor(R.color.red);
                    holder.b4.setTextColor(R.color.white);}
                holder.b4.setEnabled(false);
            }
        });


    }

    @Override
    public int getItemCount() {
        return QList.size();
    }

    public int getCorrect(){
        return Correct;
    }


    public static class QVH extends RecyclerView.ViewHolder{

        TextView question, category, difficulty;
        Button b1, b2, b3, b4;

        public QVH(@NonNull View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.question);
            category = (TextView) itemView.findViewById(R.id.category);
            difficulty = (TextView) itemView.findViewById(R.id.difficulty);
            b1 = (Button) itemView.findViewById(R.id.B1);
            b2 = (Button) itemView.findViewById(R.id.B2);
            b3 = (Button) itemView.findViewById(R.id.B3);
            b4 = (Button) itemView.findViewById(R.id.B4);


        }
    }

}
