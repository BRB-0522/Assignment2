package com.example.assignment2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayQ#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayQ extends Fragment {

    ArrayList<Quiz> QL;
    Quiz Q;
    Tournament T;
    int index=0;
    int s=0;
    int Cindex =0;
    boolean correct, liked=false;

    TextView score, question, category, difficulty;
    Button b1, b2, b3, b4;
    Button next, prev, finish;
    LinearLayout control;

    ArrayList<Boolean> answered= new ArrayList<>(10);

    ListT listT;

    User u = new User();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public PlayQ() {
        // Required empty public constructor
    }

    public static PlayQ newInstance(String param1, String param2) {
        PlayQ fragment = new PlayQ();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public boolean Acheck(String selected,int index){
        boolean b = false;
        if(selected.equals(Q.getCorrect_answer())){
            s+=1;
            score.setText(String.valueOf(s));
            answered.add(true);
            b=true;
        }else{
            answered.add(false);
        }
        return b;
    }

    @SuppressLint("ResourceAsColor")
    public void ABset(){
        if(index<Cindex){

            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
            b4.setEnabled(false);

        }else{

            b1.setEnabled(true);
            b2.setEnabled(true);
            b3.setEnabled(true);
            b4.setEnabled(true);
            b1.setBackgroundColor(Color.WHITE);
            b2.setBackgroundColor(Color.WHITE);
            b3.setBackgroundColor(Color.WHITE);
            b4.setBackgroundColor(Color.WHITE);
        }
    }

    public void QInitial(View v, Quiz Q){

        ArrayList<String> ans = Q.getAnswers();


        question.setText(Q.getQuestion());
        category.setText(Q.getCategory());
        difficulty.setText(Q.getDifficulty());

        b1.setText(ans.get(0));
        b2.setText(ans.get(1));
        b3.setText(ans.get(2));
        b4.setText(ans.get(3));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_play_q, container, false);

        score = (TextView)v.findViewById(R.id.score);

        control = (LinearLayout)v.findViewById(R.id.control);

        prev = (Button)v.findViewById(R.id.prev);
        next = (Button)v.findViewById(R.id.next);

        question = (TextView)v.findViewById(R.id.question);
        category = (TextView) v.findViewById(R.id.category);
        difficulty = (TextView) v.findViewById(R.id.difficulty);

        b1 = (Button)v.findViewById(R.id.B1);
        b2 = (Button)v.findViewById(R.id.B2);
        b3 = (Button)v.findViewById(R.id.B3);
        b4 = (Button)v.findViewById(R.id.B4);

        T = (Tournament) getArguments().getSerializable("Tournament");
        QL = (ArrayList<Quiz>) getArguments().getSerializable("Quiz");

        Q = QL.get(0);
        QInitial(v,Q);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==0){
                    getParentFragmentManager().beginTransaction().remove(PlayQ.this);
                    listT = new ListT();
                    getParentFragmentManager().beginTransaction().replace(R.id.frame,listT).commitAllowingStateLoss();
                }else{
                        index-=1;
                        Q = QL.get(index);
                        QInitial(v,Q);
                }
                ABset();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b1.isEnabled()==false){

                    if(index==QL.size()-2){
                        next.setText("Like");
                        index+=1;
                        Q=QL.get(index);
                        QInitial(v,Q);

                        //last question function
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

                        finish = new Button(v.getContext());
                        finish.setText("Finish");
                        finish.setLayoutParams(params);
                        control.addView(finish);

                        finish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(b1.isEnabled()==false){
                                    if(liked==true){

                                        T.moreLike();
                                        String user = getActivity().getIntent().getStringExtra("user");
                                        Query query = db.collection("User").whereEqualTo("email",user);
                                        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                for (DocumentSnapshot doc : value){
                                                    u = doc.toObject(User.class);
                                                }
                                            }
                                        });

                                        db.collection("User").document(user).update("liked", FieldValue.arrayUnion(T.getTname()));
                                        db.collection("Tournament").document(T.getTname()).update("like", T.getLike());
                                    }

                                    String user = getActivity().getIntent().getStringExtra("user");
                                    Query query = db.collection("User").whereEqualTo("email",user);
                                    query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            for (DocumentSnapshot doc : value){
                                                u = doc.toObject(User.class);
                                            }
                                        }
                                    });

                                    db.collection("User").document(user).update("played", FieldValue.arrayUnion(T.getTname()));

                                    getParentFragmentManager().beginTransaction().remove(PlayQ.this);
                                    listT = new ListT();
                                    getParentFragmentManager().beginTransaction().replace(R.id.frame,listT).commitAllowingStateLoss();

                                }
                            }
                        });



                    }
                    //change into like
                    else if(index==QL.size()-1){
                        liked=true;
                        next.setText("UNLIKE");
                    }else if(next.getText().toString().equals("UNLIKE")){
                        liked=false;
                        next.setText("Like");
                    }
                    else{
                        index+=1;
                        Q=QL.get(index);
                        QInitial(v,Q);

                    }
                    ABset();
                }

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               correct = Acheck(b1.getText().toString(),index);
               if (correct==true){
                   b1.setBackgroundColor(Color.GREEN);
               } else{
                   b1.setBackgroundColor(Color.RED);
                   if(Acheck(b2.getText().toString(),index)==true){
                       b2.setBackgroundColor(Color.GREEN);
                   }else if(Acheck(b3.getText().toString(),index)==true){
                       b3.setBackgroundColor(Color.GREEN);
                   }else{
                       b4.setBackgroundColor(Color.GREEN);
                   }
               }

               Cindex+=1;
               ABset();
           }
       });

        b2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                correct = Acheck(b2.getText().toString(),index);
                if (correct==true){
                    b2.setBackgroundColor(Color.GREEN);
                } else{
                    b2.setBackgroundColor(Color.RED);
                    if(Acheck(b1.getText().toString(),index)==true){
                        b1.setBackgroundColor(Color.GREEN);
                    }else if(Acheck(b3.getText().toString(),index)==true){
                        b3.setBackgroundColor(Color.GREEN);
                    }else{
                        b4.setBackgroundColor(Color.GREEN);
                    }
                }

                Cindex+=1;
                ABset();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                correct = Acheck(b3.getText().toString(),index);
                if (correct==true){
                    b3.setBackgroundColor(Color.GREEN);
                } else{
                    b3.setBackgroundColor(Color.RED);
                    if(Acheck(b2.getText().toString(),index)==true){
                        b2.setBackgroundColor(Color.GREEN);
                    }else if(Acheck(b1.getText().toString(),index)==true){
                        b1.setBackgroundColor(Color.GREEN);
                    }else{
                        b4.setBackgroundColor(Color.GREEN);
                    }
                }

                Cindex+=1;
                ABset();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                correct = Acheck(b4.getText().toString(),index);
                if (correct==true){
                    b4.setBackgroundColor(Color.GREEN);
                } else{
                    b4.setBackgroundColor(Color.RED);
                    if(Acheck(b2.getText().toString(),index)==true){
                        b2.setBackgroundColor(Color.GREEN);
                    }else if(Acheck(b3.getText().toString(),index)==true){
                        b3.setBackgroundColor(Color.GREEN);
                    }else{
                        b1.setBackgroundColor(Color.GREEN);
                    }
                }

                Cindex+=1;
                ABset();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}