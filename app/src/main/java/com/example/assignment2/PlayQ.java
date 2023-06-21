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
import android.widget.Button;
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

    RecyclerView RV;
    QAdapter QA;
    List<Quiz> QL;
    Tournament T;

    User u = new User();

    ListT listT;
    PlayQ qp;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_play_q, container, false);


        TextView score = (TextView)v.findViewById(R.id.score);

        Button  like = (Button)v.findViewById(R.id.like);
        Button finish = (Button)v.findViewById(R.id.finish);

        RV = v.findViewById(R.id.RVQuiz);
        QL = new List<Quiz>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Quiz> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(Quiz quiz) {
                return false;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Quiz> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends Quiz> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Quiz get(int index) {
                return null;
            }

            @Override
            public Quiz set(int index, Quiz element) {
                return null;
            }

            @Override
            public void add(int index, Quiz element) {

            }

            @Override
            public Quiz remove(int index) {
                return null;
            }

            @Override
            public int indexOf(@Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Quiz> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Quiz> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<Quiz> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        T = (Tournament) getArguments().getSerializable("Tournament");
        DocumentReference documentReference = db.collection("Tournament").document(T.getTname());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Tournament t = documentSnapshot.toObject(Tournament.class);
                QL = t.getQuizs();
                QA = new QAdapter(getContext(), QL);
                RV.setAdapter(QA);
                RV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            }
        });

        QA.setOnItemClickListener(new QAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                score.setText(String.valueOf(QA.getCorrect()));
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });



        // Inflate the layout for this fragment
        return v;
    }
}