package com.example.eindopdrachtcsdlarsrookenjaspervanes.recyclerViewLogic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;

import java.util.*;

public class Adapter extends RecyclerView.Adapter<Holder> {

    private List<Route> routes;
    private OnItemClickListener clickListener;

    public Adapter(List<Route> endPoints, OnItemClickListener listener) {
        this.routes = endPoints;
        this.clickListener = listener;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        Holder holder = new Holder(itemView, this.clickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Route pointSelected = this.routes.get(position);
        holder.title.setText(pointSelected.getName());
        System.out.println(holder.title.getText());
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }


    public void listChanged(List<Route> allRoutes){
        this.routes = allRoutes;
        notifyDataSetChanged();
    }
}
