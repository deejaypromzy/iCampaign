package com.campaign.sey;

import android.app.Notification;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.campaign.sey.recylcerchat.ChatData;
import com.campaign.sey.recylcerchat.ConversationRecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CommentsFragment extends Fragment {
    public static final String ME = "2";
    public static final String YOU = "1";
    private static final String CHANNEL_ID = "2";
    private FirebaseAuth auth;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mrefRead;
    private FirebaseUser fireuser;
    private ArrayList<Model> DataModel;

    private RecyclerView mRecyclerView;
    private EditText text;
    private ImageView send;
    private ConversationRecyclerView mAdapter;
    private DatabaseReference mref;
    private SimpleDateFormat df;
    private Date date;
    private String type=ME;
    private PrefManager prefManager;
    private TextView title;
    private CircleImageView user_photo;
    private static final String TAG = "CommentsFragment";
    private Notification notification;
    private Bitmap pFixBitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefManager = new PrefManager(getActivity());

        if (!prefManager.isRegistered()) {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.registration);
        }

        Toolbar toolbar =getActivity(). findViewById(R.id.toolbar);
        Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.item_animation_fall_down);
        toolbar.startAnimation(animation2);
        toolbar.setVisibility(View.VISIBLE);
        title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText(prefManager.getUserName());

        user_photo = (CircleImageView) toolbar.findViewById(R.id.user_photo);

        if (Objects.equals(prefManager.getImageUrl(), ""))
        user_photo.setImageDrawable(getResources().getDrawable(R.drawable.no_user));
        else
            Glide.with(getActivity()).load(prefManager.getImageUrl()).into(user_photo);

        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.navigation);
        //Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
       // bottomNavigationView.startAnimation(animation);
        bottomNavigationView.setVisibility(View.GONE);

        auth= FirebaseAuth.getInstance();
        fireuser=auth.getCurrentUser();
        mfirebaseDatabase = FirebaseDatabase.getInstance();

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();

        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mref = mfirebaseDatabase.getReference();


        mrefRead = mfirebaseDatabase.getReference().child(Utils.PERSON).child(Utils.CHAT);
        mrefRead.keepSynced(true);


        mRecyclerView = view. findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




        DataModel = new ArrayList<>();
        mAdapter = new ConversationRecyclerView(getActivity(),DataModel);

        new CountDownTimer(200,200)
        {
            public void onTick(long ms)
            {

                // progressBar.setVisibility(View.VISIBLE);
            }
            public void onFinish() {
                if (mrefRead!=null){
                    mrefRead.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            showData(dataSnapshot);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        }.start();


        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRecyclerView.getAdapter().getItemCount()>0)
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
            }
        }, 1000);

        text = view. findViewById(R.id.et_message);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //if (mRecyclerView.getAdapter().getItemCount()>0)
                        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                    }
                }, 500);
            }
        });
        send = view. findViewById(R.id.bt_send);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                prefManager = new PrefManager(getActivity());
                if (!text.getText().toString().trim().equals("")){
                    final String key = mref.push().getKey();
                    Model items = new Model(
                            prefManager.getUserPhone(),
                            df.format(date),
                            text.getText().toString(),
                            date,
                            prefManager.getImageUrl()
                           );

                  //  Toast.makeText(getActivity(), items.toString(), Toast.LENGTH_SHORT).show();

                   mref.child(Utils.PERSON).child(Utils.CHAT).child(key).setValue(items);

                  //  Toast.makeText(getActivity(), items.toString(), Toast.LENGTH_SHORT).show();

//                   List<Model> data = new ArrayList<>();
//                    Model item = new Model();
//                    item.setTime(date);
//                    item.setType("2");
//                    item.setText(text.getText().toString());
//                    data.add(item);
//                    mAdapter.addItem(data);

                   // if (mRecyclerView.getAdapter().getItemCount()>0)
                    try {
                        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() -1);
                    }catch (Exception ignored){

                    }
                    text.setText("");
                }
            }
        });

    }


    private void showData(DataSnapshot dataSnapshot)  {
        //Create the ArrayList of Sports objects with the titles, images
        // and information about each sport
        DataModel.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            Model userDatabase = new Model();
            userDatabase.setId(ds.getValue(Model.class).getId());
            userDatabase.setText(ds.getValue(Model.class).getText());
            userDatabase.setTime(ds.getValue(Model.class).getTime());
            userDatabase.setDate(ds.getValue(Model.class).getType());

            if(Objects.equals(ds.getValue(Model.class).getId(), prefManager.getUserPhone())){
                userDatabase.setType(ME);
            }else {
                userDatabase.setType(YOU);
            }


            //  userDatabase.setType(ds.getValue(Model.class).getType());


            userDatabase.setImageResource(ds.getValue(Model.class).getImageResource());


         //   Toast.makeText(getActivity(), ds.getValue(Model.class).getType().toString(), Toast.LENGTH_SHORT).show();
           // DataModel.add(new Model(userDatabase.getType(),userDatabase.getText(),userDatabase.getTime()));

            DataModel.add(userDatabase);

        }


        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView.getAdapter().getItemCount()>0)
        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() -1);

    }




/*
    public List<ChatData> setData(){
        List<ChatData> data = new ArrayList<>();

        String text[] = {"15 September","Hi, Julia! How are you?", "Hi, Joe, looks great! :) ", "I'm fine. Wanna go out somewhere?", "Yes! Coffe maybe?", "Great idea! You can come 9:00 pm? :)))", "Ok!", "Ow my good, this Kit is totally awesome", "Can you provide other kit?", "I don't have much time, :`("};
        String time[] = {"", "5:30pm", "5:35pm", "5:36pm", "5:40pm", "5:41pm", "5:42pm", "5:40pm", "5:41pm", "5:42pm"};
        String type[] = {"0", "2", "1", "1", "2", "1", "2", "2", "2", "1"};

        for (int i=0; i<text.length; i++){
            ChatData item = new ChatData();
            item.setType(type[i]);
            item.setText(text[i]);
            item.setTime(time[i]);
            data.add(item);
        }

        return data;
    }
*/
public static String formateddate(String date) {
    DateTime dateTime = DateTimeFormat.forPattern("dd-MMM-yyyy").parseDateTime(date);
    DateTime today = new DateTime();
    DateTime yesterday = today.minusDays(1);
    DateTime twodaysago = today.minusDays(2);
    DateTime tomorrow= today.minusDays(-1);

    if (dateTime.toLocalDate().equals(today.toLocalDate())) {
        return "Today ";
    } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {
        return "Yesterday ";
    } else if (dateTime.toLocalDate().equals(twodaysago.toLocalDate())) {
        return "2 days ago ";
    } else if (dateTime.toLocalDate().equals(tomorrow.toLocalDate())) {
        return "Tomorrow ";
    } else {
        return date;
    }
}
}
