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

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Data;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.ViewModel;

public class EditFragment extends Fragment implements LifecycleOwner {


    private ViewModel mViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Data.getInstance().setCurrentFragment(this);
        mViewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        View view = inflater.inflate(R.layout.edit_fragment, container, false);

        Button saveButton = view.findViewById(R.id.editFragment_buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                //Route newRoute =  new Route();
               // mViewModel.addRoute(newRoute);
            }
        });


        return view;
    }

    private Observer<Fragment> fragmentObserver(){
        Observer<Fragment> observer = new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {

            }
        };
        return observer;
    }
}

