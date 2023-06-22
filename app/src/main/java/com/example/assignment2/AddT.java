package com.example.assignment2;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;


public class AddT extends Fragment {

    RequestQueue queue;
    private ArrayList<Quiz> Q;

    Tournament T;
    Tournament TM = new Tournament();
    boolean Exist=false;

    private Calendar startC = Calendar.getInstance();
    private Calendar endC = Calendar.getInstance();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //calendar add

    private String updateLabel(Calendar date){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        String s = sdf.format(date.getTime());

        return s;
    }

    public AddT() {
        // Required empty public constructor
    }

    public boolean NameCheck(String n){

        Query query = db.collection("Tournament").whereEqualTo("Tname",n);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot doc : value){
                    TM = doc.toObject(Tournament.class);
                }
            }
        });
        if(TM.getTname()!=""){
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TM.setTname("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Q=new ArrayList<Quiz>();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_addt,container,false);

        TextView Sdate = (TextView) v.findViewById(R.id.startD);
        TextView Edate = (TextView) v.findViewById(R.id.endD);

        TextView TName = (TextView) v.findViewById(R.id.TName);

        ImageView SD = (ImageView) v.findViewById(R.id.startDIc);
        ImageView ED = (ImageView) v.findViewById(R.id.endDIc);

        //Spinner binding
        Spinner category = (Spinner) v.findViewById(R.id.category);
        Spinner difficulty = (Spinner) v.findViewById(R.id.difficulty);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.category_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.difficulty_array, android.R.layout.simple_spinner_item);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(categoryAdapter);
        difficulty.setAdapter(difficultyAdapter);

        TName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                NameCheck(TName.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                NameCheck(TName.getText().toString());
            }
        });

        //calendar add
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
                new DatePickerDialog(AddT.this.getContext(), startP, startC.get(Calendar.YEAR), startC.get(Calendar.MONTH), startC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        SD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddT.this.getContext(), startP, startC.get(Calendar.YEAR), startC.get(Calendar.MONTH), startC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddT.this.getContext(), endP, endC.get(Calendar.YEAR), endC.get(Calendar.MONTH), endC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddT.this.getContext(), endP, endC.get(Calendar.YEAR), endC.get(Calendar.MONTH), endC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Sdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Q.size()==10){
                    Q.clear();
                }
                String C =category.getSelectedItem().toString();
                String D=difficulty.getSelectedItem().toString();

                Q = AddApi(C,D);
            }
        });

        Edate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(Q.size()==10){
                    Q.clear();
                }
                String C =category.getSelectedItem().toString();
                String D=difficulty.getSelectedItem().toString();

                Q = AddApi(C,D);
            }
        });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Q.clear();
                String C =category.getSelectedItem().toString();
                String D=difficulty.getSelectedItem().toString();

                Q = AddApi(C,D);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Q.clear();
                String C =category.getSelectedItem().toString();
                String D=difficulty.getSelectedItem().toString();

                Q = AddApi(C,D);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button submit = (Button) v.findViewById(R.id.submitT);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String C =category.getSelectedItem().toString();
                String D=difficulty.getSelectedItem().toString();

                if(Sdate.getText().toString()==""||Edate.getText().toString()==""||TName.getText().toString()==""){
                    Toast.makeText(v.getContext(), "Fill all the information", Toast.LENGTH_LONG);
                }else if(startC.before(endC.getTime())==true){
                    Toast.makeText(v.getContext(), "The end date must be later than start date", Toast.LENGTH_LONG);
                }else if(Exist==true){
                    Toast.makeText(v.getContext(),"Tournament name must be unique.",Toast.LENGTH_LONG);
                }
                else{
                    Tsave(TName.getText().toString(), C,D,Q,Sdate.getText().toString(),Edate.getText().toString());
                    Toast.makeText(v.getContext(),"Added successfully",Toast.LENGTH_LONG);
                }
            }
        });



        return v;
    }

    public void Tsave( String Tname, String category, String difficulty, ArrayList<Quiz> Quizs, String startD, String endD){
        T = new Tournament(Tname, category, difficulty, Quizs, startD,endD,0);

        db.collection("Tournament").document(Tname).set(T);
    }


    public ArrayList<Quiz> AddApi(String category, String difficulty){

        String D = "&difficulty=";
        String C = "&category=";

        switch (difficulty){
            case "Any Difficulty":
                D="";
                break;
            case "Easy":
                D = D+"easy";
                break;

            case "Medium":
                D = D+"medium";
                break;

            case "Hard":
                D = D+"hard";
                break;
        }

        switch (category){
            case "Any Category":
                C="";
                break;
            case "General Knowledge":
                C = C+"9";
                break;
            case "Entertainment: Books":
                C=C+"10";
                break;
            case "Entertainment: Film":
                C=C+"11";
                break;

            case "Entertainment: Music":
                C =C+"12";
                break;
            case "Entertainment: Musicals & Theatres":
                C =C+"13";
                break;

            case "Entertainment: Telecision":
                C =C+"14";
                break;

            case "Entertainment: Video Games":
                C =C+"15";
                break;

            case "Entertainment: Board Games":
                C =C+"16";
                break;

            case "Science & Nature":
                C =C+"17";
                break;

            case "Science: Computers":
                C =C+"18";
                break;

            case "Science: Mathematics":
                C =C+"19";
                break;

            case "Mythology":
                C =C+"20";
                break;

            case "Sports":
                C =C+"21";
                break;

            case "Geography":
                C =C+"22";
                break;

            case "History":
                C =C+"23";
                break;

            case "Politics":
                C =C+"24";
                break;

            case "Art":
                C =C+"25";
                break;

            case "Celebrities":
                C =C+"26";
                break;

            case "Animals":
                C =C+"27";
                break;

            case "Vehicles":
                C =C+"28";
                break;

            case "Entertainment: Comics":
                C =C+"29";
                break;

            case "Science: Gadgets":
                C =C+"30";
                break;

            case "Entertainment: Japanese Anime & Manga":
                C =C+"31";
                break;

            case "Entertainment: Cartoon & Animations":
                C =C+"32";
                break;
        }

        String url = "https://opentdb.com/api.php?amount=10"+C+D+"&type=multiple";

        if(queue==null){
            queue = Volley.newRequestQueue(this.getContext());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("results");
                    for (int i=0; i<data.length(); i++){
                        JSONObject TQ = data.getJSONObject(i);

                        String c = TQ.getString("category");
                        String t = TQ.getString("type");
                        String d = TQ.getString("difficulty");
                        String q = TQ.getString("question");
                        String ca = TQ.getString("correct_answer");
                        JSONArray wa = TQ.getJSONArray("incorrect_answers");
                        ArrayList<String> wrongA = new ArrayList<>();
                        for (int j=0; j<wa.length(); j++){
                            wrongA.add(wa.getString(j));
                        }
                        ArrayList<String> answers = wrongA;
                        answers.add(ca);

                        Quiz quiz = new Quiz(c,t,d,q,ca,wrongA,answers);
                        Q.add(quiz);

                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        },null);
        queue.add(request);

        return Q;
    }
}