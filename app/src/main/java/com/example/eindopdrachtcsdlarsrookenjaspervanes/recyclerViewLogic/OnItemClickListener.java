package com.example.eindopdrachtcsdlarsrookenjaspervanes.recyclerViewLogic;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;

public interface OnItemClickListener {
    void onItemClick(int clickPosition);
    void removeRoute(Route route);
}
