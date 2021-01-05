package com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Data;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.models.EndPoint;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.DetailFragmentViewModel;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.MapViewModel;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.ViewModel;

import org.osmdroid.util.GeoPoint;

import java.sql.SQLOutput;

public class DetailFragment extends Fragment implements LifecycleOwner {
    private ViewModel mViewModel;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        Data.getInstance().setCurrentFragment(this);
        mViewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        TextView textView_name = view.findViewById(R.id.detailFragment_textView_name_value);
        TextView textView_endpoint = view.findViewById(R.id.detailFragment_textView_endpoint_value);
        ListView listView_pointInBetween = view.findViewById(R.id.detailFragment_listView_pointsInBetween);

        EndPoint selectedPoint = mViewModel.getSelectedEndPoint().getValue();
        textView_name.setText(selectedPoint.getName());
        textView_endpoint.setText("Latitude : " + selectedPoint.getEndPoint().getLatitude() + ", " + "Longitude: " + selectedPoint.getEndPoint().getLongitude());

        ArrayAdapter<GeoPoint> arrayAdapter = new ArrayAdapter<GeoPoint>(this.getContext(), android.R.layout.simple_list_item_1);
        arrayAdapter.addAll(selectedPoint.getPointInBetween());
        listView_pointInBetween.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

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
