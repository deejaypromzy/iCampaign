package com.campaign.sey;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link infrastructureAchievementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class infrastructureAchievementFragment extends Fragment {
    private ArrayList<Model> ModelEdu;
    private EduAdapter eduAdapter;
    private FirebaseAuth auth;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mref;
    private FirebaseUser fireuser;
    private ProgressBar progressBar;
    private String service;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_education_acheivement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle bundle = getArguments();
        service = bundle.getString("service");

        progressBar= view.findViewById(R.id.progressbar);
        auth= FirebaseAuth.getInstance();
        fireuser=auth.getCurrentUser();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        switch (service){
            case "ins_ach":
                mref = mfirebaseDatabase.getReference().child(Utils.PERSON).child(Utils.INFRASTRUCTURE_ACHIEVEMENT);
                mref.keepSynced(true);
                break;
            case "ins_vision":
                mref = mfirebaseDatabase.getReference().child(Utils.PERSON).child(Utils.INFRASTRUCTURE_VISION);
                mref.keepSynced(true);
                break;
        }

        final RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);

//Set the Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//Initialize the adapter and set it ot the RecyclerView

//Initialize the ArrayList that will contain the data
        ModelEdu = new ArrayList<>();
        eduAdapter = new EduAdapter(getActivity(), ModelEdu);
        mRecyclerView.setAdapter(eduAdapter);
        new CountDownTimer(200,200)
        {
            public void onTick(long ms)
            {
                progressBar.setVisibility(View.VISIBLE);
            }
            public void onFinish() {
                if (mref!=null){
                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            showData(dataSnapshot);
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        }.start();


    }

    private void showData(DataSnapshot dataSnapshot) {
        //Create the ArrayList of Sports objects with the titles, images
        // and information about each sport
        ModelEdu.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Model userDatabase = new Model();
            userDatabase.setName((ds.getValue(Model.class)).getName());
            userDatabase.setTitle((ds.getValue(Model.class)).getTitle());
            userDatabase.setDesc((ds.getValue(Model.class)).getDesc());
            userDatabase.setImageResource((ds.getValue(Model.class)).getImageResource());
            userDatabase.setDate((ds.getValue(Model.class)).getDate());
            userDatabase.setYoutube((ds.getValue(Model.class)).getYoutube());

            ModelEdu.add(new Model(userDatabase.getId(),userDatabase.getName(),userDatabase.getTitle(),userDatabase.getImageResource(),userDatabase.getDesc(),userDatabase.getDate(),userDatabase.getYoutube()));

        }
        //Notify the adapter of the change
        eduAdapter.notifyDataSetChanged();
    }
}