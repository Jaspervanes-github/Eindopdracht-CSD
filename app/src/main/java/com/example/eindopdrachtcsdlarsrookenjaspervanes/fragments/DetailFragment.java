package com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Data;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.models.EndPoint;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.DetailFragmentViewModel;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.MapViewModel;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.ViewModel;

import org.osmdroid.util.GeoPoint;

import java.sql.SQLOutput;

public class DetailFragment extends Fragment implements LifecycleOwner {
    private ViewModel mViewModel;
    private int selectedRouteID;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedRouteID = requireArguments().getInt("routeID");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        Data.getInstance().setCurrentFragment(this);
        mViewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        TextView textView_name = view.findViewById(R.id.detailFragment_textView_name_value);
        TextView textView_endpoint = view.findViewById(R.id.detailFragment_textView_endpoint_value);
        ListView listView_pointInBetween = view.findViewById(R.id.detailFragment_listView_pointsInBetween);

        Route selectedPoint = mViewModel.getRouteByID(selectedRouteID);
        System.out.println(selectedPoint.name);
        System.out.println(selectedPoint.uid);
        textView_name.setText(selectedPoint.getName());
        if(selectedPoint.getWaypoints().size() > 0){
            textView_endpoint.setText("Latitude : " + selectedPoint.getWaypoints().get(selectedPoint.getWaypoints().size()-1).getLatitude() + ", " + "Longitude: " +  selectedPoint.getWaypoints().get(selectedPoint.getWaypoints().size()-1).getLongitude());
        }
        ArrayAdapter<GeoPoint> arrayAdapter = new ArrayAdapter<GeoPoint>(this.getContext(), android.R.layout.simple_list_item_1);
        arrayAdapter.addAll(selectedPoint.getWaypoints());
        listView_pointInBetween.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


        Button start_button = view.findViewById(R.id.detailFragment_button_start);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.setActiveRoute(selectedRouteID);
                Navigation.findNavController(requireActivity(), R.id.fragmentContainer).navigate(R.id.action_detailFragment_to_mapFragment);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(DetailFragmentViewModel.class);
        // TODO: Use the ViewModel
    }


    private Observer<Fragment> fragmentObserver(){
        Observer<Fragment> observer = new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {

            }
        };
        return observer;
    }

    @Override
    public void onDestroy() {
        System.out.println("ON DESTROY");
        super.onDestroy();
    }
}
