package com.example.eindopdrachtcsdlarsrookenjaspervanes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments.MapFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, MapFragment.newInstance())
//                    .commitNow();
//        }

        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.main_fragment) == null) {
            mapFragment = new MapFragment();
            fragmentManager.beginTransaction().add(R.id.main_fragment, mapFragment).commit();
        } else {
            mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.main_fragment);
        }
    }
}