package dia.appmonsters.ssb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dia.appmonsters.ssb.R;
import dia.appmonsters.ssb.utils.SharedPrefs;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText etUsername, etPassword;
    private TextView tvNeedHelp;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init views
        initComps();
        prepareViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString();
                if(username.equalsIgnoreCase("ADMIN") && password.equals("ssb789")){
                    new SharedPrefs(getApplicationContext()).saveLoginInfo(username, password, true);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "INVALID USERNAME OR PASSWORD\nCLICK NEED HELP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvNeedHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "USERNAME: ADMIN\nPASSWORD: ssb789", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareViews() {
        //add back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initComps() {
        btnLogin = findViewById(R.id.login_btn_login);
        etUsername = findViewById(R.id.login_et_username);
        etPassword = findViewById(R.id.login_et_password);
        tvNeedHelp = findViewById(R.id.login_tv_needhelp);
        toolbar = findViewById(R.id.login_toolbar);
    }
}