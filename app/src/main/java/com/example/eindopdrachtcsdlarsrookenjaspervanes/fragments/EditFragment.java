package com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments;

import android.content.Context;
import android.os.Bundle;
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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Data;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.Method;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.ViewModel;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditFragment extends Fragment implements LifecycleOwner {

    private Context fragmentContext;

    private EditText editTextInputName;
    private EditText editTextInputCoordinates;
    private Spinner spinner;
    private ListView listView;
    private Button buttonRemovePoint;
    private Button buttonSave;
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
        Data.getInstance().setCurrentFragment(this);
        mViewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        View view = inflater.inflate(R.layout.edit_fragment, container, false);

        this.editTextInputName = view.findViewById(R.id.editfragment_editText_nameInput);
        this.editTextInputCoordinates = view.findViewById(R.id.editFragment_editTextInputCoordinates);
        this.spinner = view.findViewById(R.id.editFragment_spinnerMethod);
        this.listView = view.findViewById(R.id.editFragment_listViewPoints);
        this.buttonRemovePoint = view.findViewById(R.id.editFragment_buttonRemovePoint);
        this.buttonSave = view.findViewById(R.id.editFragment_buttonSave);
        this.imageButtonMoveUp = view.findViewById(R.id.editFragment_imageButtonMoveUp);
        this.imageButtonMoveDown = view.findViewById(R.id.editFragment_imageButtonMoveDown);
        this.checkBoxStartLocation = view.findViewById(R.id.editFragment_checkBoxStartLocation);


        methods = new ArrayList<>();
        methods.add(Method.DRIVING_CAR);
        methods.add(Method.FOOT_WALKING);

        spinnerAdapter = new ArrayAdapter<Method>(fragmentContext, R.layout.simple_list_item, methods);
        spinner.setAdapter(spinnerAdapter);

        listItems = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<GeoPoint>(fragmentContext, R.layout.simple_list_item, listItems);
        listView.setAdapter(listViewAdapter);

        setListeners();

        return view;
    }


    private void setListeners(){

        buttonRemovePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItem != null && listItems.contains(selectedItem)) {
                    listItems.remove(selectedItem);
                    listViewAdapter.notifyDataSetChanged();
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "";
                if(editTextInputName.getText().toString() != null){
                    name = editTextInputName.getText().toString();
                }
                String method = spinner.getSelectedItem().toString().replaceAll("_", "-").toLowerCase();

                boolean startFromMyLocation = checkBoxStartLocation.isChecked();
                if(startFromMyLocation){
                    if(listItems.size() >= 1){
                        listItems.add(0, mViewModel.getCurrentLocation().getValue());
                    } else {
                        Toast.makeText(requireActivity().getApplicationContext(), R.string.quickRoute_error, Toast.LENGTH_SHORT).show();
                    }
                } else{
                    if(!(listItems.size() >= 2)){
                        Toast.makeText(requireActivity().getApplicationContext(), R.string.quickRoute_error, Toast.LENGTH_SHORT).show();
                    }
                }

                Route newRoute = new Route(name, listItems, method, checkBoxStartLocation.isChecked());
                mViewModel.addRoute(newRoute);

                Navigation.findNavController(requireActivity(), R.id.fragmentContainer).navigate(R.id.action_editFragment_to_routeListFragment);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

