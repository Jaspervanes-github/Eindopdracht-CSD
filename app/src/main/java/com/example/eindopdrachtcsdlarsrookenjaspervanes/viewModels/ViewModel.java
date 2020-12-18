package com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.models.EndPoint;

import java.util.ArrayList;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private LiveData<ArrayList<EndPoint>> allEndPoints;
    private LiveData<EndPoint> selectedEndPoint;


    public LiveData<ArrayList<EndPoint>> getAllEndPoints() {
        return allEndPoints;
    }

    public void setAllEndPoints(LiveData<ArrayList<EndPoint>> allEndPoints) {
        this.allEndPoints = allEndPoints;
    }

    public LiveData<EndPoint> getSelectedEndPoint() {
        return selectedEndPoint;
    }

    public void setSelectedEndPoint(LiveData<EndPoint> selectedEndPoint) {
        this.selectedEndPoint = selectedEndPoint;
    }
}
