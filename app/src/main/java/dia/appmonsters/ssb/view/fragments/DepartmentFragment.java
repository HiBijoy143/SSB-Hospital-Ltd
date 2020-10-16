package dia.appmonsters.ssb.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dia.appmonsters.ssb.R;
import dia.appmonsters.ssb.adapters.DepartmentListAdapter;
import dia.appmonsters.ssb.view.DepartmentWiseDoctorList;

public class DepartmentFragment extends Fragment implements DepartmentListAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private DepartmentListAdapter adapter;
    private List<String> deptList = new ArrayList<>();


    public DepartmentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_department, container, false);
        recyclerView = view.findViewById(R.id.dept_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        deptList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.name_of_dept)));
        deptList.remove(0);
        adapter = new DepartmentListAdapter(deptList, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), DepartmentWiseDoctorList.class);
        intent.putExtra("data_dept", deptList.get(position));
        startActivity(intent);
    }
}