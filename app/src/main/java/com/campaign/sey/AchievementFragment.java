package com.campaign.sey;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class AchievementFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private Bundle bundle;
    private BottomNavigationView bottomNavigationView;
    private Animation animation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_achievement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(this);


        bottomNavigationView=getActivity().findViewById(R.id.navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_Achievements).setChecked(true);

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        bottomNavigationView.startAnimation(animation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        EducationAcheivementFragment  educationAcheivementFragment = new EducationAcheivementFragment();
        bundle = new Bundle();
        bundle.putString("service", Utils.EDUCATIONAL_ACHIEVEMENT);
        educationAcheivementFragment.setArguments(bundle);
        setFragment(educationAcheivementFragment);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){

            case 0:
                EducationAcheivementFragment  educationAcheivementFragment = new EducationAcheivementFragment();
                bundle.putString("service", Utils.EDUCATIONAL_ACHIEVEMENT);
                educationAcheivementFragment.setArguments(bundle);
                setFragment(educationAcheivementFragment);
                bottomNavigationView.startAnimation(animation);
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;
            case 1:
                HealthAchievementFragment  healthAchievementFragment = new HealthAchievementFragment();
                bundle.putString("service", Utils.HEALTH_ACHIEVEMENT);
                healthAchievementFragment.setArguments(bundle);
                setFragment(healthAchievementFragment);
                bottomNavigationView.startAnimation(animation);
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;
            case 2:
                JobsAchievementFragment  jobsAchievementFragment = new JobsAchievementFragment();
                bundle.putString("service", Utils.JOB_ACHIEVEMENT);
                jobsAchievementFragment.setArguments(bundle);
                setFragment(jobsAchievementFragment);
                bottomNavigationView.startAnimation(animation);
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;
            case 3:
                infrastructureAchievementFragment  infrastructureAchievementFragment = new infrastructureAchievementFragment();
                bundle.putString("service", Utils.INFRASTRUCTURE_ACHIEVEMENT);
                infrastructureAchievementFragment.setArguments(bundle);
                setFragment(infrastructureAchievementFragment);
                bottomNavigationView.startAnimation(animation);
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView.getMenu().findItem(R.id.nav_Achievements).setChecked(true);

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.container, fragment);
        t.commit();
    }
}
