package dia.appmonsters.ssb.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dia.appmonsters.ssb.R;
import dia.appmonsters.ssb.adapters.AllDoctorListAdapter;
import dia.appmonsters.ssb.database.DbHelper;
import dia.appmonsters.ssb.model.DoctorInfo;
import dia.appmonsters.ssb.view.DoctorDetailsActivity;

public class AllDoctorListFragment extends Fragment implements AllDoctorListAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<DoctorInfo> allDoctorList = new ArrayList<>();
    private AllDoctorListAdapter adapter;
    private DbHelper dbHelper;


   public AllDoctorListFragment() {
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
       View view =  inflater.inflate(R.layout.fragment_all_doctor_list, container, false);

       recyclerView = view.findViewById(R.id.all_doctor_recyler_view);
       searchView = view.findViewById(R.id.all_doctor_searchview);

       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       recyclerView.hasFixedSize();

       dbHelper = new DbHelper(getContext());
       allDoctorList = dbHelper.getAllDoctorsList();

       adapter = new AllDoctorListAdapter(allDoctorList, this);
       recyclerView.setAdapter(adapter);
       adapter.notifyDataSetChanged();

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String s) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String s) {
               adapter.getFilter().filter(s);
               return false;
           }
       });


       return view;
   }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), DoctorDetailsActivity.class);
        intent.putExtra("data", allDoctorList.get(position));
        startActivity(intent);
    }
}