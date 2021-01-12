package com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.lifecycle.LifecycleOwner;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Data;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.Method;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.ViewModel;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QuickRouteFragment extends Fragment implements LifecycleOwner {

    private Context fragmentContext;

    private TextView textViewInputCoordinates;
    private TextView textViewMethod;
    private EditText editTextInputCoordinates;
    private Spinner spinnerMethod;
    private ListView listViewPoints;
    private Button buttonRemovePoint;
    private Button buttonStart;
    private ImageButton imageButtonMoveUp;
    private ImageButton imageButtonMoveDown;
    private CheckBox checkBoxStartLocation;

    private ArrayAdapter<GeoPoint> listViewAdapter;
    private ArrayList<GeoPoint> listItems;
    private GeoPoint selectedItem;

    private ArrayAdapter<Method> spinnerAdapter;
    private ArrayList<Method> methods;

    private ViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentContext = getActivity().getBaseContext();
        Configuration.getInstance().load(fragmentContext, PreferenceManager.getDefaultSharedPreferences(fragmentContext));
        Data.getInstance().setCurrentFragment(this);
        View view = inflater.inflate(R.layout.quick_route_fragment, container, false);

        mViewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        textViewInputCoordinates = view.findViewById(R.id.textViewInputCoordinates);
//        textViewInputCoordinates.setText("Coordinate (Example: 51.100,4.678):");
        textViewMethod = view.findViewById(R.id.textViewMethod);
//        textViewMethod.setText("Method:");

        editTextInputCoordinates = view.findViewById(R.id.editTextInputCoordinates);

        spinnerMethod = view.findViewById(R.id.spinnerMethod);
        methods = new ArrayList<>();
        methods.add(Method.DRIVING_CAR);
        methods.add(Method.FOOT_WALKING);

        spinnerAdapter = new ArrayAdapter<Method>(fragmentContext, R.layout.simple_list_item, methods);
        spinnerMethod.setAdapter(spinnerAdapter);

        listViewPoints = view.findViewById(R.id.listViewPoints);
        listItems = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<GeoPoint>(fragmentContext, R.layout.simple_list_item, listItems);
        listViewPoints.setAdapter(listViewAdapter);

        buttonRemovePoint = view.findViewById(R.id.buttonRemovePoint);
        buttonStart = view.findViewById(R.id.buttonStart);
        imageButtonMoveUp = view.findViewById(R.id.imageButtonMoveUp);
        imageButtonMoveDown = view.findViewById(R.id.imageButtonMoveDown);
        checkBoxStartLocation = view.findViewById(R.id.checkBoxStartLocation);

        setListeners();
        return view;
    }

    private void setListeners() {
        buttonRemovePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItem != null && listItems.contains(selectedItem)) {
                    listItems.remove(selectedItem);
                    listViewAdapter.notifyDataSetChanged();
                }
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean startFromMyLocation = checkBoxStartLocation.isChecked();

                if (startFromMyLocation) {
                    if (listItems.size() >= 1) {
                        listItems.add(0, mViewModel.getCurrentLocation().getValue());

                        String method = spinnerMethod.getSelectedItem().toString().replaceAll("_", "-").toLowerCase();

                        mViewModel.setCurrentRoute(listItems.toArray(new GeoPoint[listItems.size()]));
                        mViewModel.setMethod(method);
                        mViewModel.setIsFollowingRoute(true);

                        Navigation.findNavController(mViewModel.getMainActivity().getValue(), R.id.fragmentContainer)
                                .navigate(R.id.action_quickRouteFragment_to_mapFragment);
                    }else{
                        Toast.makeText(mViewModel.getMainActivity().getValue().getApplicationContext(),
                                "You don't have enough points in your list!",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (listItems.size() >= 2) {
                        String method = spinnerMethod.getSelectedItem().toString().replaceAll("_", "-").toLowerCase();

                        mViewModel.setCurrentRoute(listItems.toArray(new GeoPoint[listItems.size()]));
                        mViewModel.setMethod(method);
                        mViewModel.setIsFollowingRoute(true);

                        Navigation.findNavController(mViewModel.getMainActivity().getValue(), R.id.fragmentContainer)
                                .navigate(R.id.action_quickRouteFragment_to_mapFragment);
                    }else{
                        Toast.makeText(mViewModel.getMainActivity().getValue().getApplicationContext(),
                                "You don't have enough points in your list!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        imageButtonMoveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItem != null && listItems.contains(selectedItem)) {
                    int selectedItemIndex = listItems.indexOf(selectedItem);

                    if (selectedItemIndex > 0) {
                        listItems.remove(selectedItem);
                        listItems.add(selectedItemIndex - 1, selectedItem);
                        listViewAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        imageButtonMoveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItem != null && listItems.contains(selectedItem)) {
                    int selectedItemIndex = listItems.indexOf(selectedItem);

                    if (selectedItemIndex < listItems.size() - 1) {
                        listItems.remove(selectedItem);
                        listItems.add(selectedItemIndex + 1, selectedItem);
                        listViewAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        listViewPoints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = listViewAdapter.getItem(position);
            }
        });

        editTextInputCoordinates.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String text = editTextInputCoordinates.getText().toString().replaceAll(" ", "");

                if (keyCode == KeyEvent.KEYCODE_ENTER && text.matches("\\d+.{0,1}\\d*,{1}\\d+.{0,1}\\d*")) {
                    Pattern pattern = Pattern.compile("[^,]*,");
                    Matcher matcher = pattern.matcher(text);
                    int comma = 0;
                    while (matcher.find()) {
                        comma++;
                    }

                    if (comma == 1) {
                        double lat = Double.parseDouble(text.substring(0, text.indexOf(",")));
                        double lng = Double.parseDouble(text.substring(text.indexOf(",") + 1));

                        GeoPoint geoPoint = new GeoPoint(lat, lng);

                        listItems.add(geoPoint);
                        listViewAdapter.notifyDataSetChanged();
                    }

                }
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    editTextInputCoordinates.setText("");
                }
                return false;
            }
        });
    }
}
