package dia.appmonsters.ssb.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import dia.appmonsters.ssb.R;
import dia.appmonsters.ssb.adapters.DoctorListFragmentAdpter;

public class ListDoctorFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;


   public ListDoctorFragment() {
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
       View view =  inflater.inflate(R.layout.fragment_list_doctor, container, false);
       viewPager = view.findViewById(R.id.list_doctor_viewpager);
       tabLayout = view.findViewById(R.id.list_doctor_tab_layout);

       return view;
   }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupAdapter();
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupAdapter() {
        DoctorListFragmentAdpter adpter = new DoctorListFragmentAdpter(getChildFragmentManager());

        adpter.addFragment(new AllDoctorListFragment(), "ALL");
        adpter.addFragment(new DepartmentFragment(), "DEPARTMENTS");

        viewPager.setAdapter(adpter);
    }
}