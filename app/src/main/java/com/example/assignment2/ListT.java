package com.example.assignment2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListT#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListT extends Fragment {

    RecyclerView RV;
    TAdapter TA;
    ArrayList<Tournament> TL = new ArrayList<>();
    Tournament T = new Tournament();

    UpdateT updateT = new UpdateT();
    PlayQ qp;

    FirebaseFirestore db= FirebaseFirestore.getInstance();


    public ListT() {
        // Required empty public constructor
    }

    public static ListT newInstance(String param1, String param2) {
        ListT fragment = new ListT();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public void getT(){
        TL.clear();
        Query query = db.collection("Tournament");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot doc:value){
                    T=doc.toObject(Tournament.class);
                    TL.add(T);
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getT();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_listt, container, false);

        RV = v.findViewById(R.id.RVtournament);
        TL = (ArrayList<Tournament>) getActivity().getIntent().getSerializableExtra("T");
        ArrayList<Tournament> NP = new ArrayList<>();
        if(getActivity().getIntent().getStringExtra("type").equals("player")){
            User u;
            u = (User) getActivity().getIntent().getSerializableExtra("user");
            for(Tournament t : TL){
                for (String s : u.getPlayed()){
                    if(t.getTname().equals(s)!=true){
                        NP.add(t);
                    }
                }
            }


        }

        if(getActivity().getIntent().getStringExtra("type").equals("admin")){

            TA = new TAdapter(getContext(),TL);
        }else{

            TA = new TAdapter(getContext(),NP);
        }
        RV.setAdapter(TA);
        RV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        TA.setOnItemClickListener(new TAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos, Tournament T) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Tournament", (Serializable) T);

                getParentFragmentManager().beginTransaction().remove(ListT.this);
                if(getActivity().getIntent().getStringExtra("type").equals("admin")){
                    updateT.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.frame,updateT).commitAllowingStateLoss();
                }else{

                    bundle.putSerializable("Quiz", (Serializable) T.getQuizs());

                    qp = new PlayQ();
                    qp.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.frame,qp).commitAllowingStateLoss();
                }

            }
        });


        return v;
    }
}