package com.example.assignment2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PrevT extends Fragment {


    RecyclerView RV;
    TAdapter TA;
    User u=new User();
    List<Tournament> TTL = new ArrayList<>();


    public PrevT() {
        // Required empty public constructor
    }


    public static PrevT newInstance() {
        PrevT fragment = new PrevT();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_prev_t, container, false);

        List<Tournament> Played = new ArrayList<>();
        TTL = (ArrayList<Tournament>) getActivity().getIntent().getSerializableExtra("T");
        u = (User) getActivity().getIntent().getSerializableExtra("user");
        for(Tournament t : TTL){
            for (String s : u.getPlayed()){
                if(t.getTname().equals(s)){
                    Played.add(t);
                }
            }
        }

        RV = v.findViewById(R.id.RVtournament);

        TA=new TAdapter(getContext(),Played);
        RV.setAdapter(TA);
        RV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        // Inflate the layout for this fragment
        return v;
    }

}