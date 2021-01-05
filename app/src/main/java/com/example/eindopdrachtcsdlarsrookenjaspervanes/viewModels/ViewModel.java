package com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.models.EndPoint;

import java.util.ArrayList;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private MutableLiveData<ArrayList<EndPoint>> allEndPoints;
    private MutableLiveData<EndPoint> selectedEndPoint;


    public ViewModel() {
        this.allEndPoints = new MutableLiveData<>();
        this.allEndPoints.setValue(new ArrayList<>());
        this.selectedEndPoint = new MutableLiveData<>();
    }

    public LiveData<ArrayList<EndPoint>> getAllEndPoints() {
        return allEndPoints;
    }

    public void setAllEndPoints(ArrayList<EndPoint> allEndPoints) {
        this.allEndPoints.setValue(allEndPoints);
    }

    public LiveData<EndPoint> getSelectedEndPoint() {
        return selectedEndPoint;
    }

    public void setSelectedEndPoint(EndPoint selectedEndPoint) {
        this.selectedEndPoint.setValue(selectedEndPoint);
    }
}
