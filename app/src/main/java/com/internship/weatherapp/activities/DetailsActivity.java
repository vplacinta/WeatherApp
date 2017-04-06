package com.internship.weatherapp.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.internship.weatherapp.R;
import com.internship.weatherapp.fragments.HorizontalItemFragment;
import com.internship.weatherapp.fragments.VerticalItemFragment;

public class DetailsActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new VerticalItemFragment())
                .commit();

        setContentView(R.layout.main_fragment_layout);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int displaymode = getResources().getConfiguration().orientation;

        if (displaymode == 1) {
//            VerticalItemFragment verticalItemFragment = new VerticalItemFragment();
//            fragmentTransaction.replace(android.R.id.content, verticalItemFragment);

//            VerticalItemFragment dFragment = new VerticalItemFragment();
//            // Show DialogFragment
//            dFragment.show(fragmentManager, "Dialog Fragment");
        } else {
            HorizontalItemFragment horizontalItemFragment = new HorizontalItemFragment();
            fragmentTransaction.replace(android.R.id.content, horizontalItemFragment);
        }

        fragmentTransaction.commit();
    }

}
