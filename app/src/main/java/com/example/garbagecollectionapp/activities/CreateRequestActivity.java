package com.example.garbagecollectionapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.models.Area;
import com.example.garbagecollectionapp.models.SpecialRequest;
import com.example.garbagecollectionapp.utils.DateUtils;
import com.example.garbagecollectionapp.utils.SharedPrefManager; 

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateRequestActivity extends AppCompatActivity {

    private Spinner spinnerArea;
    private TextView tvDate;
    private EditText etDescription;
    private Button btnSubmit;
    private List<Area> areaList = new ArrayList<>();
    private String selectedAreaId;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        spinnerArea = findViewById(R.id.spinner_area);
        tvDate = findViewById(R.id.tv_date);
        etDescription = findViewById(R.id.et_description);
        btnSubmit = findViewById(R.id.btn_submit);

        setupAreaSpinner();
        setupDatePicker();
        setupSubmitButton();
    }

    private void setupAreaSpinner() {
        // TODO: Replace with actual API call to get areas
        // For now, we'll use dummy data
        for (int i = 0; i < 5; i++) {
            Area area = new Area();
            area.setId("area_" + i);
            area.setName("Area " + (i + 1));
            area.setZone("Zone " + (i % 3 + 1));
            areaList.add(area);
        }

        List<String> areaNames = new ArrayList<>();
        for (Area area : areaList) {
            areaNames.add(area.getName() + " (" + area.getZone() + ")");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, areaNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(adapter);

        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAreaId = areaList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedAreaId = null;
            }
        });
    }

    private void setupDatePicker() {
        selectedDate = Calendar.getInstance();
        updateDateText();

        tvDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate.set(year, month, dayOfMonth);
                    updateDateText();
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            );
            
            // Set minimum date to today
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
    }

    private void updateDateText() {
        tvDate.setText(DateUtils.formatDate(selectedDate.getTime(), DateUtils.DISPLAY_DATE_FORMAT));
    }

    private void setupSubmitButton() {
        btnSubmit.setOnClickListener(v -> {
            String description = etDescription.getText().toString().trim();

            if (selectedAreaId == null) {
                Toast.makeText(this, "Please select an area", Toast.LENGTH_SHORT).show();
                return;
            }

            if (description.isEmpty()) {
                etDescription.setError("Description is required");
                etDescription.requestFocus();
                return;
            }

            // Create the request
            SpecialRequest request = new SpecialRequest();
            request.setAreaId(selectedAreaId);
            request.setRequestDate(selectedDate.getTime());
            request.setDescription(description);
            request.setStatus("PENDING");

            // TODO: Call API to create request
            Toast.makeText(this, "Request submitted successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}