package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    AddT addT;
    ListT listT;
    PrevT prevT;

    User u = new User();

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public String typeCheck(String n){

        Query query = db.collection("User").whereEqualTo("email",n);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot doc : value){
                    u = doc.toObject(User.class);
                }
            }
        });

        return u.getType();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        String user = intent.getStringExtra("user");

        String type = typeCheck(user);

        //add Fragments
        addT = new AddT();
        listT = new ListT();
        prevT = new PrevT();

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        if(type=="admin"){
            ft.add(R.id.frame,addT);
            ft.commit();

            TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int position = tab.getPosition();
                    Fragment selected = null;
                    FragmentManager fm1 = getSupportFragmentManager();
                    FragmentTransaction ft1 = fm1.beginTransaction();
                    switch (position){
                        case 0: selected = addT;
                            break;

                        case 1: selected = listT;
                            break;
                    }
                    ft1.replace(R.id.frame, selected);
                    ft1.commit();

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }else{
            ft.add(R.id.frame,prevT);
            ft.commit();
            TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
            tabs.getTabAt(0).setText("Previous Tournament");
            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int position = tab.getPosition();
                    Fragment selected = null;
                    FragmentManager fm1 = getSupportFragmentManager();
                    FragmentTransaction ft1 = fm1.beginTransaction();
                    switch (position){
                        case 0: selected = prevT;
                            break;

                        case 1: selected = listT;
                            break;
                    }
                    ft1.replace(R.id.frame, selected);
                    ft1.commit();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }



    }

}