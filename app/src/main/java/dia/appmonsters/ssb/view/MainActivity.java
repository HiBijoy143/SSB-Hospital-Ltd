package dia.appmonsters.ssb.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dia.appmonsters.ssb.R;
import dia.appmonsters.ssb.utils.SharedPrefs;
import dia.appmonsters.ssb.view.fragments.AddDoctorFragment;
import dia.appmonsters.ssb.view.fragments.HomeFragment;
import dia.appmonsters.ssb.view.fragments.ListDoctorFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private BottomNavigationView btmNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComps();
        prepareViews();

        btmNavView.setOnNavigationItemSelectedListener(navListerer);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListerer =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.menu_nav_home:
                            fragment = new HomeFragment();
                            toolbar.setVisibility(View.VISIBLE);
                            break;

                        case R.id.menu_nav_doc_list:
                            fragment = new ListDoctorFragment();
                            toolbar.setVisibility(View.GONE);
                            break;

                        case R.id.menu_nav_add_doc:
                            fragment = new AddDoctorFragment();
                            toolbar.setVisibility(View.GONE);
                            break;
                    }

                    fragmentTransition(fragment);
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menu_nav, menu);

        //to hide logout button if not logged in
        if(new SharedPrefs(this).isLoggedIn()){
            menu.getItem(2).setVisible(true);
        } else {
            menu.getItem(2).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_top_details_dev:
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                break;

            case R.id.menu_top_about_app:
                startActivity(new Intent(MainActivity.this, AboutAppActivity.class));

                break;

            case R.id.menu_top_logout:
                new SharedPrefs(this).logout();
                finish();
                Intent intent = getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

        return true;
    }

    private void fragmentTransition(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.home_frame_layout, fragment)
                .commit();
    }

    private void initComps() {
        toolbar = findViewById(R.id.home_toolbar);
        frameLayout = findViewById(R.id.home_frame_layout);
        btmNavView = findViewById(R.id.home_bottom_nav);
    }

    private void prepareViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.home_frame_layout, new HomeFragment())
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}