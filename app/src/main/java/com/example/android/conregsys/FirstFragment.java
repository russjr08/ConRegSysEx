package com.example.android.conregsys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private TextView txtTotalCost;

    // Instantiate a model that will hold our selection choices from the UI
    private SelectionModel model = new SelectionModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Connect UI Fields from XML to Java
        txtTotalCost = view.findViewById(R.id.text_total);
        Spinner spinnerRegType = view.findViewById(R.id.registration_type_selector);
        CheckBox optOpeningNight = view.findViewById(R.id.opt_opening_night);
        CheckBox optIntroEcommerce = view.findViewById(R.id.opt_intro_ecommerce);
        CheckBox optFutureOfWeb = view.findViewById(R.id.opt_future_of_web);
        CheckBox optAdvancedJava = view.findViewById(R.id.opt_advanced_java);
        CheckBox optNetSec = view.findViewById(R.id.opt_net_sec);

        // Register UI fields to listen for changes

        spinnerRegType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Positions: 0 = Student, 1 = Non-Student
                switch(position) {
                    case 0:
                        model.isStudent = true;
                        break;
                    case 1:
                        model.isStudent = false;
                        break;
                    default:
                        throw new IllegalArgumentException("An invalid item has been selected");
                }
                calculateTotal();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txtTotalCost.setText("Invalid options selected (??)");
            }
        });

        /*
          All of the CheckBoxes utilize the same checked change listener
          @see FirstFragment#onCheckedChanged(CompoundButton, boolean)
         */
        optOpeningNight.setOnCheckedChangeListener(this);
        optIntroEcommerce.setOnCheckedChangeListener(this);
        optFutureOfWeb.setOnCheckedChangeListener(this);
        optAdvancedJava.setOnCheckedChangeListener(this);
        optNetSec.setOnCheckedChangeListener(this);

    }

    /**
     * References the {@link SelectionModel} of the fragment to calculate the total cost of
     * admission, and then writes that value to the Total Cost TextView
     */
    @SuppressLint("DefaultLocale")
    private void calculateTotal() {
        double total = 0.00;
        if(model.isStudent)
            total += 495;
        else
            total += 895;

        if(model.openingNight) total += 30;
        if(model.introEcommerce) total += 295;
        if(model.futureWeb) total += 295;
        if(model.advancedJava) total += 395;
        if(model.netSecurity) total += 395;

        txtTotalCost.setText(String.format("$%.2f", total));
    }

    /**
     * This method is utilized to listen for when any of the checkboxes' state on the UI changes
     * @param buttonView The button (CheckBox) that was toggled / changed
     * @param isChecked Whether the CheckBox is checked or not
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()) {
            case R.id.opt_opening_night:
                model.openingNight = isChecked;
                break;
            case R.id.opt_intro_ecommerce:
                model.introEcommerce = isChecked;
                break;
            case R.id.opt_future_of_web:
                model.futureWeb = isChecked;
                break;
            case R.id.opt_advanced_java:
                model.advancedJava = isChecked;
                break;
            case R.id.opt_net_sec:
                model.netSecurity = isChecked;
                break;
            default:
                throw new IllegalArgumentException("Unsure of what to do with the button you clicked...");
        }

        calculateTotal();
    }
}