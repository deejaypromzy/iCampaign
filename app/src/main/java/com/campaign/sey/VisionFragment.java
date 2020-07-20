package com.campaign.sey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class VisionFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private Bundle bundle;
    private BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vision, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(this);
        bottomNavigationView=getActivity().findViewById(R.id.navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_vision).setChecked(true);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        bottomNavigationView.startAnimation(animation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        EducationAcheivementFragment  educationAcheivementFragment = new EducationAcheivementFragment();
        bundle = new Bundle();
        bundle.putString("service", Utils.EDUCATIONAL_VISION);
        educationAcheivementFragment.setArguments(bundle);
        setFragment(educationAcheivementFragment);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {


        switch (tab.getPosition()){

            case 0:
                EducationAcheivementFragment  educationAcheivementFragment = new EducationAcheivementFragment();
                bundle.putString("service", Utils.EDUCATIONAL_VISION);
                educationAcheivementFragment.setArguments(bundle);
                setFragment(educationAcheivementFragment);
                break;
            case 1:
                HealthAchievementFragment  healthAchievementFragment = new HealthAchievementFragment();
                bundle.putString("service", Utils.HEALTH_VISION);
                healthAchievementFragment.setArguments(bundle);
                setFragment(healthAchievementFragment);
                break;
            case 2:
                JobsAchievementFragment  jobsAchievementFragment = new JobsAchievementFragment();
                bundle.putString("service", Utils.JOB_VISION);
                jobsAchievementFragment.setArguments(bundle);
                setFragment(jobsAchievementFragment);
                break;
            case 3:
                infrastructureAchievementFragment  infrastructureAchievementFragment = new infrastructureAchievementFragment();
                bundle.putString("service", Utils.INFRASTRUCTURE_VISION);
                infrastructureAchievementFragment.setArguments(bundle);
                setFragment(infrastructureAchievementFragment);
                break;
        }


    }
    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView.getMenu().findItem(R.id.nav_vision).setChecked(true);

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
