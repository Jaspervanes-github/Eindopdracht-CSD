package com.example.eindopdrachtcsdlarsrookenjaspervanes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments.DetailFragment;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments.EditFragment;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments.MapFragment;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments.QuickRouteFragment;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments.RouteListFragment;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.models.EndPoint;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.ViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private Data data = Data.getInstance();
//    private FragmentManager fragmentManager;
//    private MapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        navView.setOnNavigationItemSelectedListener(navListener);

        ViewModel viewModel = new ViewModelProvider(this).get(ViewModel.class);
        ArrayList<EndPoint> testValues = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            testValues.add(new EndPoint("Point " + i, null));
        }
        viewModel.setAllEndPoints(testValues);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, MapFragment.newInstance())
//                    .commitNow();
//        }

//        fragmentManager = getSupportFragmentManager();
//        if (fragmentManager.findFragmentById(R.id.main_fragment) == null) {
//            mapFragment = new MapFragment();
//            fragmentManager.beginTransaction().add(R.id.main_fragment, mapFragment).commit();
//        } else {
//            mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.main_fragment);
//        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_quickRoute: {
                            navigateToFragment("quickRoute");
                            break;
                        }
                        case R.id.navigation_Map: {
                            navigateToFragment("map");
                            break;
                        }
                        case R.id.navigation_Destinations: {
                            navigateToFragment("destinations");
                            break;
                        }
                    }
                    return true;
                }
            };


    private void navigateToFragment(String direction) {
        Fragment f = data.getCurrentFragment();
        if(f instanceof MapFragment) {
            if (direction.equals("quickRoute"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_mapFragment_to_quickRouteFragment);
            if (direction.equals("destinations"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_mapFragment_to_routeListFragment);
        }
        else if(f instanceof RouteListFragment) {
            if (direction.equals("map"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_routeListFragment_to_mapFragment);
            if (direction.equals("quickRoute"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_routeListFragment_to_quickRouteFragment);
            if (direction.equals("detail"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_routeListFragment_to_detailFragment);
            if (direction.equals("edit"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_routeListFragment_to_editFragment);
        }
        else if(f instanceof DetailFragment){
            if (direction.equals("map"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_detailFragment_to_mapFragment);
            if (direction.equals("quickRoute"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_detailFragment_to_quickRouteFragment);
            if (direction.equals("destinations"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_detailFragment_to_routeListFragment);
            if (direction.equals("edit"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_detailFragment_to_editFragment);
        }
        else if (f instanceof EditFragment) {
            if (direction.equals("quickRoute"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_editFragment_to_quickRouteFragment);
            if (direction.equals("destinations"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_editFragment_to_routeListFragment);
            if (direction.equals("map"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_editFragment_to_mapFragment);

        } else if (f instanceof QuickRouteFragment) {
            if (direction.equals("map"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_quickRouteFragment_to_mapFragment);
            if (direction.equals("destinations"))
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.action_quickRouteFragment_to_routeListFragment);
        }
    }

}