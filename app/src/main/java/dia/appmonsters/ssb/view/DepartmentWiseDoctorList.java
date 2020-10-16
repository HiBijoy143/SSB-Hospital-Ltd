package dia.appmonsters.ssb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import dia.appmonsters.ssb.R;
import dia.appmonsters.ssb.adapters.AllDoctorListAdapter;
import dia.appmonsters.ssb.adapters.DepartmentListAdapter;
import dia.appmonsters.ssb.database.DbHelper;
import dia.appmonsters.ssb.model.DoctorInfo;

public class DepartmentWiseDoctorList extends AppCompatActivity implements AllDoctorListAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private AllDoctorListAdapter adapter;
    private List<DoctorInfo> doctorList;
    private DbHelper dbHelper;

    private Toolbar toolbar;
    private String dataDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_wise_doctor_list);

        initComps();
        prepareViews();

        dataDept = getIntent().getStringExtra("data_dept");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        doctorList = dbHelper.getDoctorListByDepartment(dataDept);
        adapter = new AllDoctorListAdapter(doctorList, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void initComps() {
        recyclerView = findViewById(R.id.department_wise_recyler_view);
        toolbar = findViewById(R.id.dept_wise_doc_list_toolbar);
        doctorList = new ArrayList<>();
        dbHelper = new DbHelper(DepartmentWiseDoctorList.this);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(DepartmentWiseDoctorList.this, DoctorDetailsActivity.class);
        intent.putExtra("data", doctorList.get(position));
        startActivity(intent);
    }

    private void prepareViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}