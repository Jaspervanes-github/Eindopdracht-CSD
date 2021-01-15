package com.example.eindopdrachtcsdlarsrookenjaspervanes.recyclerViewLogic;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;

public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView title;
    Button removeButton;
    private OnItemClickListener clickListener;

    public Holder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.textView_title_listItem);
        removeButton = itemView.findViewById(R.id.button_ListItem);
        clickListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("ClickInHOLDER",  "GOT A CLICK");
        clickListener.onItemClick(getAdapterPosition());
    }
}
