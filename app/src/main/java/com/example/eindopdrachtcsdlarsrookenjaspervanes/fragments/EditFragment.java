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

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Data;

public class EditFragment extends Fragment implements LifecycleOwner {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Data.getInstance().setCurrentFragment(this);
        return super.onCreateView(inflater, container, savedInstanceState);
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
