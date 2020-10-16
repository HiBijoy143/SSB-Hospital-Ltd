package dia.appmonsters.ssb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import dia.appmonsters.ssb.R;
import dia.appmonsters.ssb.database.DbHelper;
import dia.appmonsters.ssb.model.AppointmentInfo;
import dia.appmonsters.ssb.model.DoctorInfo;

public class AppointmentActivityUser extends AppCompatActivity {
    private EditText etPatientName, etPatientEmail, etPatientPhone, etPatientDoctorName, etMessage;
    private TextView tvAppoDate;
    private Button btnSendAppoReq;

    private Toolbar toolbar;

    private DbHelper dbHelper;

    private int doctorId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_user);

        //initializations
        initComps();
        prepareViews();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            etPatientDoctorName.setText(bundle.getString("doctor_name"));
            etPatientDoctorName.setKeyListener(null);
            doctorId = bundle.getInt("doctor_id");
        }

        tvAppoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = new DatePicker(AppointmentActivityUser.this);

                DatePickerDialog dialog = new DatePickerDialog(AppointmentActivityUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvAppoDate.setText(i+"-"+(i1+1)+"-"+i2);
                    }
                }, datePicker.getYear(), datePicker.getMonth(),datePicker.getDayOfMonth());

                dialog.show();
            }
        });

        btnSendAppoReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patName = etPatientName.getText().toString().trim();
                String patEmail = etPatientEmail.getText().toString().trim();
                String patPhone = etPatientPhone.getText().toString().trim();
                String patDoctorName = etPatientDoctorName.getText().toString().trim();
                String appoDate = tvAppoDate.getText().toString().trim();
                String appoMessage = etMessage.getText().toString().trim();


                boolean error = false;

                if (patName.isEmpty()) {
                    etPatientName.setError("");
                    error = true;
                }

                if (patEmail.isEmpty()) {
                    etPatientEmail.setError("");
                    error = true;
                }
                if (patPhone.isEmpty()) {
                    etPatientPhone.setError("");
                    error = true;
                }
                if (patDoctorName.isEmpty()) {
                    etPatientDoctorName.setError("");
                    error = true;
                }
                if (appoDate.isEmpty() || appoDate.equalsIgnoreCase("Choose a date")) {
                    Toast.makeText(getApplicationContext(), "Select Date", Toast.LENGTH_SHORT).show();
                    error = true;
                }

                if (error) {
                    Toast.makeText(getApplicationContext(), "Fill All Field Properly", Toast.LENGTH_SHORT).show();
                } else {
                    //save into database
                    AppointmentInfo appoModel = new AppointmentInfo(patName, patEmail, patPhone, patDoctorName, doctorId, appoDate, appoMessage);
                    long value = dbHelper.insertAppointmentInfo(appoModel);
                    if (value != -1) {
                        Toast.makeText(AppointmentActivityUser.this, "Appointment Request Sent!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

    }

    private void prepareViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComps() {
        etPatientName = findViewById(R.id.appo_user_patient_name);
        etPatientEmail = findViewById(R.id.appo_user_patient_email);
        etPatientPhone = findViewById(R.id.appo_user_patient_phone);
        etPatientDoctorName = findViewById(R.id.appo_user_doctor_name);
        tvAppoDate = findViewById(R.id.appo_user_appo_date);
        etMessage = findViewById(R.id.appo_user_message);
        btnSendAppoReq = findViewById(R.id.appo_user_btn_send);

        toolbar = findViewById(R.id.appo_user_toolbar);

        dbHelper = new DbHelper(this);
    }
}