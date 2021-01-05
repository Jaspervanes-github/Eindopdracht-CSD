package com.example.eindopdrachtcsdlarsrookenjaspervanes.recyclerViewLogic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.models.EndPoint;

import java.util.*;

public class Adapter extends RecyclerView.Adapter<Holder> {

    private List<EndPoint> endPoints;
    private OnItemClickListener clickListener;

    public Adapter(List<EndPoint> endPoints, OnItemClickListener listener) {
        this.endPoints = endPoints;
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
        EndPoint pointSelected = this.endPoints.get(position);
        holder.title.setText(pointSelected.getName());
    }

    @Override
    public int getItemCount() {
        return endPoints.size();
    }
}
