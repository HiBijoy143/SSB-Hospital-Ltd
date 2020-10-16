package dia.appmonsters.ssb.view.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import dia.appmonsters.ssb.R;
import dia.appmonsters.ssb.database.DbHelper;
import dia.appmonsters.ssb.model.DoctorInfo;
import dia.appmonsters.ssb.utils.SharedPrefs;
import dia.appmonsters.ssb.view.LoginActivity;

import static android.app.Activity.RESULT_OK;

public class AddDoctorFragment extends Fragment {
    private CircleImageView civImage;
    private EditText etName, etDegrees, etDesignation, etPhone, etEmail;
    private AppCompatSpinner spDepartment, spOffday;
    private Button btnAddDoctor;

    private Toolbar toolbar;

    private static final int RC_CAMERA = 100;
    private static final int RC_GALLERY = 101;

    private DbHelper dbHelper;


    public AddDoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;

        boolean isLoggedIn = new SharedPrefs(getContext()).isLoggedIn();

        if(isLoggedIn) {
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.fragment_add_doctor, container, false);

            initComps(v);
            prepareViews();

            civImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectImage();
                }
            });

            btnAddDoctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addDoctor();

                }
            });
            return v;
        } else {
            v = inflater.inflate(R.layout.layout_admin_permission, container, false);
            Button buttonGoToLogin = v.findViewById(R.id.ad_perm_btn);
            buttonGoToLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            });
            return v;
        }
    }


    private void addDoctor() {
        byte[] imageData = convertImageToByte(civImage);
        String name = etName.getText().toString().trim();
        String degrees = etDegrees.getText().toString().trim();
        String department = spDepartment.getSelectedItem().toString().trim();
        String designation = etDesignation.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String offday = spOffday.getSelectedItem().toString();

        boolean error = false;

        if (name.isEmpty()) {
            etName.setError("");
            error = true;
        }
        if (degrees.isEmpty()) {
            etDegrees.setError("");
            error = true;
        }
        if (department.isEmpty() || department.equalsIgnoreCase("DEPARTMENT")) {
            error = true;
        }
        if (designation.isEmpty()) {
            etDesignation.setError("");
            error = true;
        }
        if (phone.isEmpty()) {
            etPhone.setError("");
            error = true;
        }
        if (email.isEmpty()) {
            etEmail.setError("");
            error = true;
        }

        if (offday.isEmpty() || offday.equalsIgnoreCase("WEEKLY OFF DAY")) {
            offday = "-";
        }

        if (error) {
            Toast.makeText(getContext(), "Fill All Field Properly", Toast.LENGTH_SHORT).show();
        } else {
            //save into database
            DoctorInfo docModel = new DoctorInfo(name, degrees, department, designation, phone, email, offday, imageData);
            long value = dbHelper.insertDoctorInfo(docModel);
            if (value != -1) {
                Toast.makeText(getContext(), "Doctor Info Inserted!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private byte[] convertImageToByte(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmapMain = bitmapDrawable.getBitmap();
            Bitmap bitmap = Bitmap.createScaledBitmap(bitmapMain, 512, 512, true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
            byte[] byteData = stream.toByteArray();
            return byteData;
        }
        return null;
    }

    private void selectImage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, RC_GALLERY)) {
                uploadImage();
            }
        } else {
            uploadImage();
        }


    }

    private void uploadImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(getContext(), this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                civImage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public boolean checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getContext(), permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{permission},
                    requestCode);
        }

        return ContextCompat.checkSelfPermission(getContext(), permission)
                == PackageManager.PERMISSION_GRANTED ? true : false;
    }

    private void prepareViews() {
        spDepartment.setAdapter(getCustomAdapter(R.array.name_of_dept));
        spOffday.setAdapter(getCustomAdapter(R.array.name_of_days));
    }

    private ArrayAdapter<String> getCustomAdapter(int arrayId) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(arrayId)) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0)
                    tv.setTextColor(Color.GRAY);
                else
                    tv.setTextColor(Color.BLACK);
                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0)
                    tv.setTextColor(Color.GRAY);
                return view;
            }
        };

        return arrayAdapter;
    }

    private void initComps(View v) {
        civImage = v.findViewById(R.id.add_doc_civ_image);
        etName = v.findViewById(R.id.add_doc_et_name);
        etDegrees = v.findViewById(R.id.add_doc_et_degrees);
        etDesignation = v.findViewById(R.id.add_doc_et_designation);
        etPhone = v.findViewById(R.id.add_doc_et_phone);
        etEmail = v.findViewById(R.id.add_doc_et_email);
        spDepartment = v.findViewById(R.id.add_doc_sp_dept);
        spOffday = v.findViewById(R.id.add_doc_sp_off_day);
        btnAddDoctor = v.findViewById(R.id.add_doc_btn_add);

        dbHelper = new DbHelper(getContext());
    }
}