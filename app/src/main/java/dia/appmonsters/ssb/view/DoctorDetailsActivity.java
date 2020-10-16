package dia.appmonsters.ssb.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import dia.appmonsters.ssb.R;
import dia.appmonsters.ssb.model.DoctorInfo;

public class DoctorDetailsActivity extends AppCompatActivity {
    private Button btnRequestForAppo;
    private TextView tvName, tvDegrees, tvDepartment, tvDesignation, tvId, tvOffday, tvPhone, tvEmail;
    private CircleImageView civImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        //initializations
        initComp();

        DoctorInfo data = (DoctorInfo) getIntent().getSerializableExtra("data");
        if(data != null){
            tvName.setText(data.getName());
            tvDegrees.setText(data.getDegress());
            tvDepartment.setText(data.getDepartment());
            tvDesignation.setText(data.getDesignation());
            tvId.setText(String.valueOf(data.getId()));
            tvOffday.setText(data.getOffday());
            tvPhone.setText(data.getPhone());
            tvEmail.setText(data.getEmail());
            civImage.setImageBitmap(BitmapFactory.decodeByteArray(data.getImage(), 0, data.getImage().length));
        }

        btnRequestForAppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorDetailsActivity.this, AppointmentActivityUser.class);
                intent.putExtra("doctor_name", tvName.getText().toString().trim());
                intent.putExtra("doctor_id", tvId.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    private void initComp() {
        btnRequestForAppo = findViewById(R.id.doctor_detais_btn_req_app);
        tvName = findViewById(R.id.doctor_details_name);
        tvDegrees = findViewById(R.id.doctor_details_degrees);
        tvDepartment = findViewById(R.id.doctor_details_department);
        tvDesignation = findViewById(R.id.doctor_details_designation);
        tvId = findViewById(R.id.doctor_details_id);
        tvOffday = findViewById(R.id.doctor_details_offday);
        tvPhone = findViewById(R.id.doctor_details_phone);
        tvEmail = findViewById(R.id.doctor_details_email);
        civImage = findViewById(R.id.doctor_details_image);
    }
}