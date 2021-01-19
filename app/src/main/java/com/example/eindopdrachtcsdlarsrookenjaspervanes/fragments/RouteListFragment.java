package com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Data;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.models.EndPoint;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.recyclerViewLogic.Adapter;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.recyclerViewLogic.OnItemClickListener;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.recyclerViewLogic.RecyclerViewUpdate;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.ViewModel;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class RouteListFragment extends Fragment implements OnItemClickListener, RecyclerViewUpdate, LifecycleOwner {

    private ViewModel mViewModel;
    private RecyclerView recyclerView;
    private Adapter adapter;

    private View view;
    private RouteListFragment fragment;


    public static RouteListFragment newInstance() {
        return new RouteListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.route_list_fragment, container, false);
        Data.getInstance().setCurrentFragment(this);
        this.view = view;
        this.fragment = this;
        mViewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new Adapter(mViewModel.getAllSavedRoutes(), this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        Button addNewButtonTest = view.findViewById(R.id.button_Add_New_Endpoint);
        addNewButtonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.fragmentContainer).navigate(R.id.action_routeListFragment_to_editFragment);

            }
        });

        return view;
    }

    @Override
    public void onItemClick(int clickPosition) {
        //mViewModel.setSelectedEndPoint(mViewModel.getAllSavedRoutes().get(clickPosition));
        Bundle bundle = new Bundle();
        bundle.putInt("routeID", mViewModel.getAllSavedRoutes().get(clickPosition).getUid());
        Navigation.findNavController(getActivity(), R.id.fragmentContainer).navigate(R.id.action_routeListFragment_to_detailFragment, bundle);
    }

    @Override
    public void removeRoute(Route route) {
        mViewModel.deleteRoute(route);
    }

    @Override
    public void update() {
        adapter = new Adapter(mViewModel.getAllSavedRoutes(), this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}
