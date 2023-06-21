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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
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
    List<Tournament> TL;

    UpdateT updateT = new UpdateT();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ListT() {
        // Required empty public constructor
    }

    public static ListT newInstance(String param1, String param2) {
        ListT fragment = new ListT();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_listt, container, false);

        RV = v.findViewById(R.id.RVtournament);
        TL = new List<Tournament>() {
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
            public Iterator<Tournament> iterator() {
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
            public boolean add(Tournament tournament) {
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
            public boolean addAll(@NonNull Collection<? extends Tournament> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends Tournament> c) {
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
            public Tournament get(int index) {
                return null;
            }

            @Override
            public Tournament set(int index, Tournament element) {
                return null;
            }

            @Override
            public void add(int index, Tournament element) {

            }

            @Override
            public Tournament remove(int index) {
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
            public ListIterator<Tournament> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Tournament> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<Tournament> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        CollectionReference collectionReference = db.collection("Tournament");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                TL = queryDocumentSnapshots.toObjects(Tournament.class);

                TA = new TAdapter(getContext(),TL);
                RV.setAdapter(TA);
                RV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

                TA.setOnItemClickListener(new TAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos, Tournament T) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Tournament", (Serializable) T);
                        updateT.setArguments(bundle);

                        getParentFragmentManager().beginTransaction().remove(ListT.this);
                        getParentFragmentManager().beginTransaction().replace(R.id.frame,updateT).commitAllowingStateLoss();
                    }
                });
            }
        });




        return v;
    }
}