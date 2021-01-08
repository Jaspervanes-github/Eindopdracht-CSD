package com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.eindopdrachtcsdlarsrookenjaspervanes.models.EndPoint;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.recyclerViewLogic.Adapter;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.recyclerViewLogic.OnItemClickListener;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.MapViewModel;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.RouteListViewModel;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.ViewModel;

import java.util.ArrayList;

public class RouteListFragment extends Fragment implements OnItemClickListener, LifecycleOwner {

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
        adapter = new Adapter(mViewModel.getAllEndPoints().getValue(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(RouteListViewModel.class);
//        // TODO: Use the ViewModel
//    }

    @Override
    public void onItemClick(int clickPosition) {
        mViewModel.setSelectedEndPoint(mViewModel.getAllEndPoints().getValue().get(clickPosition));
        Navigation.findNavController(getActivity(), R.id.fragmentContainer).navigate(R.id.action_routeListFragment_to_detailFragment);
    }

    private Observer<ArrayList<EndPoint>> fragmentObserver(){
        Observer<ArrayList<EndPoint>> observer = new Observer<ArrayList<EndPoint>>() {
            @Override
            public void onChanged(ArrayList<EndPoint> endPoints) {
                recyclerView = view.findViewById(R.id.recyclerView);
                adapter = new Adapter(mViewModel.getAllEndPoints().getValue(), fragment);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }
        };
        return observer;
    }
}
