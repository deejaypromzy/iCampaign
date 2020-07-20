package com.campaign.sey;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;
import com.campaign.sey.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Model> ModelEdu;
    private HomeAdapter eduAdapter;
    private FirebaseAuth auth;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mref;
    private FirebaseUser fireuser;
    private ProgressBar progressBar;

    private int[] images = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5};
    private BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CarouselView carouselView =view. findViewById(R.id.carouselView);

        carouselView.setSize(images.length);
        carouselView.setResource(R.layout.center_carousel_item);
        carouselView.setAutoPlay(true);
        carouselView.setIndicatorAnimationType(IndicatorAnimationType.THIN_WORM);
        carouselView.setCarouselOffset(OffsetType.CENTER);
        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                ImageView imageView = view.findViewById(R.id.imageView);
                imageView.setImageDrawable(getResources().getDrawable(images[position]));
            }
        });
        // After you finish setting up, show the CarouselView
        carouselView.show();

         bottomNavigationView=getActivity().findViewById(R.id.navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        bottomNavigationView.startAnimation(animation);
        bottomNavigationView.setVisibility(View.VISIBLE);


        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        progressBar= view.findViewById(R.id.progressbar);
        auth= FirebaseAuth.getInstance();
        fireuser=auth.getCurrentUser();
        mfirebaseDatabase = FirebaseDatabase.getInstance();

        mref = mfirebaseDatabase.getReference().child(Utils.PERSON).child(Utils.HOME_INFO);
        mref.keepSynced(true);


        final RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);

//Set the Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//Initialize the adapter and set it ot the RecyclerView

//Initialize the ArrayList that will contain the data
        ModelEdu = new ArrayList<>();
        eduAdapter = new HomeAdapter(getActivity(), ModelEdu);
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
            userDatabase.setYoutube((ds.getValue(Model.class)).getYoutube());

           ModelEdu.add(new Model(userDatabase.getId(),userDatabase.getName(),userDatabase.getTitle(),userDatabase.getYoutube()));

        }
        //Notify the adapter of the change
        eduAdapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

    }
    }
