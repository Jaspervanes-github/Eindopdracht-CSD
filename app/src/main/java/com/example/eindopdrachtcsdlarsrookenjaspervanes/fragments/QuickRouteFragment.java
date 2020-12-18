package com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Data;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;

import org.osmdroid.config.Configuration;



public class QuickRouteFragment extends Fragment {

    private Context fragmentContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentContext = getActivity().getBaseContext();
        Configuration.getInstance().load(fragmentContext, PreferenceManager.getDefaultSharedPreferences(fragmentContext));
        Data.getInstance().setCurrentFragment(this);
        View view = inflater.inflate(R.layout.quick_route_fragment, container, false);




        return view;
    }
}
