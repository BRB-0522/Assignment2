package com.example.assignment2;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateT extends Fragment {

    private Calendar startC = Calendar.getInstance();
    private Calendar endC = Calendar.getInstance();

    private ListT LT;

    boolean Exist=false;

    Tournament T = new Tournament();
    Tournament Tt = new Tournament();

    private Date SD,ED;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    String myFormat = "dd/MM/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

    public boolean NameCheck(String n){

        Query query = db.collection("Tournament").whereEqualTo("Tname",n);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot doc : value){
                    Tt = doc.toObject(Tournament.class);
                }
            }
        });
        if(Tt.getTname()!=""){
            return true;
        }
        return false;
    }


    public UpdateT() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static UpdateT newInstance(String param1, String param2) {
        UpdateT fragment = new UpdateT();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String updateLabel(Calendar date){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        String s = sdf.format(date.getTime());

        return s;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_update_t, container, false);


        if (getArguments() != null) {

            T = (Tournament) getArguments().getSerializable("Tournament");
        }

        EditText tname = v.findViewById(R.id.TName);
        TextView Sdate = v.findViewById(R.id.startD);
        TextView Edate = v.findViewById(R.id.endD);
        ImageView Sic = v.findViewById(R.id.startDIc);
        ImageView Eic = v.findViewById(R.id.endDIc);

        Sdate.setText(T.getStartDate());
        Edate.setText(T.getEndDate());
        tname.setText(T.getTname());

        Button update = v.findViewById(R.id.updateT);
        Button delete = v.findViewById(R.id.deleteT);

        tname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                NameCheck(tname.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                NameCheck(tname.getText().toString());
            }
        });

        try {
             SD = sdf.parse(T.getStartDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
             ED = sdf.parse(T.getEndDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        startC.setTime(SD);
        endC.setTime(ED);


        DatePickerDialog.OnDateSetListener startP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startC.set(Calendar.YEAR, year);
                startC.set(Calendar.MONTH, month);
                startC.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Sdate.setText(updateLabel(startC));
            }
        };

        DatePickerDialog.OnDateSetListener endP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endC.set(Calendar.YEAR, year);
                endC.set(Calendar.MONTH, month);
                endC.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Edate.setText(updateLabel(endC));
            }
        };

        //show date pickers
        Sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateT.this.getContext(), startP, startC.get(Calendar.YEAR), startC.get(Calendar.MONTH), startC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Sic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateT.this.getContext(), startP, startC.get(Calendar.YEAR), startC.get(Calendar.MONTH), startC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateT.this.getContext(), endP, endC.get(Calendar.YEAR), endC.get(Calendar.MONTH), endC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Eic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateT.this.getContext(), endP, endC.get(Calendar.YEAR), endC.get(Calendar.MONTH), endC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Sdate.getText().toString()==""||Edate.getText().toString()==""||tname.getText().toString()==""){
                    Toast.makeText(getContext(), "Fill all the information", Toast.LENGTH_LONG);
                }else if(startC.before(endC.getTime())==true){
                    Toast.makeText(getContext(), "The end date must be later than start date", Toast.LENGTH_LONG);
                }else if(Exist==true){
                    Toast.makeText(getContext(),"Tournament name must be unique.",Toast.LENGTH_LONG);
                }
                else {
                    Tsave(tname.getText().toString(), T.getCategory(), T.getDifficulty(), T.getQuizs(), Sdate.getText().toString(), Edate.getText().toString());
                    Toast.makeText(getContext(),"Added successfully",Toast.LENGTH_LONG);
                    getParentFragmentManager().beginTransaction().remove(UpdateT.this);
                    LT = new ListT();
                    getParentFragmentManager().beginTransaction().replace(R.id.frame,LT).commitAllowingStateLoss();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Tournament").document(T.getTname()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(),"Deleted successfully",Toast.LENGTH_LONG);

                        getParentFragmentManager().beginTransaction().remove(UpdateT.this);

                        LT = new ListT();
                        getParentFragmentManager().beginTransaction().replace(R.id.frame,LT).commitAllowingStateLoss();
                    }
                });
            }
        });


        return v;
    }

    public void Tsave(String Tname, String category, String difficulty, ArrayList<Quiz> Quizs, String startD, String endD){
        Tournament T = new Tournament(Tname, category, difficulty, Quizs, startD,endD,0);

        db.collection("Tournament").document(Tname).set(T);
    }
}