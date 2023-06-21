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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrevT#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrevT extends Fragment {


    RecyclerView RV;
    TAdapter TA;
    ArrayList<String> TL=new ArrayList<>();
    List<Tournament> TTL;

    User u = new User();;
    Tournament tt = new Tournament();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        RV = v.findViewById(R.id.RVtournament);
        TL = new ArrayList<String>();



        String user = getActivity().getIntent().getStringExtra("user");
        Query query = db.collection("User").whereEqualTo("email",user);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot doc : value){
                    u = doc.toObject(User.class);
                }
                TL = u.getPlayed();

                for (String t: TL) {
                    Query q2 = db.collection("Tournament").whereEqualTo("Tname",t);
                    q2.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for (DocumentSnapshot doc : value){
                                tt = doc.toObject(Tournament.class);
                                TTL.add(tt);
                            }
                            TA=new TAdapter(getContext(),TTL);
                            RV.setAdapter(TA);
                            RV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

                        }
                    });

                }


            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}