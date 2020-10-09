package com.example.civildoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class Login extends AppCompatActivity {
    static final int REQUEST_CODE = 123;
    private ImageView welcomeLoginIcon;
    private EditText loginEmail,loginPassword;
    private Button loginButton,loginRegButton,mShowAds;
    private TextView forgotPassword;
    private ProgressBar mProgresBar;
    private AppCompatCheckBox loginShowPasswordCheckbox;
    String userID;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcomeLoginIcon = findViewById(R.id.welcome_login_icon);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        loginRegButton = findViewById(R.id.login_reg_button);
        forgotPassword = findViewById(R.id.forgot_password);
        mProgresBar = findViewById(R.id.ProgresBar);
        loginShowPasswordCheckbox = findViewById(R.id.login_show_password_checkbox);
        showOrHidePasswordField();
        AutoPermitiom();

        firebaseAuth = FirebaseAuth.getInstance();
        loginRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));


            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                //AlertDialog passwordResetDilog;
                passwordResetDialog.setMessage("ENTER Your Email ID TO RECEIVED RESET Password LINK");
                passwordResetDialog.setView(resetMail);


                passwordResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset password Link send to your email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error ! password Link Not send + e.getMassage()", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create(). show ();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    CircularProgressBar progressBar = new CircularProgressBar(Login.this);
                    final AlertDialog alertDialog = progressBar.setCircularProgressBar();

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if (alertDialog != null) {
                                if (task.isSuccessful()) {
                                    alertDialog.dismiss();
                                    sendToHomeActivity();
                                } else {
                                    alertDialog.dismiss();
                                    Toast.makeText(Login.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(Login.this, "Empty email and password", Toast.LENGTH_SHORT).show();
                }
            }


        });

    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(Login.this, Home.class));
            sendToHomeActivity();
        }
    }



    private void sendToHomeActivity() {
        userID = firebaseAuth.getCurrentUser().getUid();

        Intent intent = new Intent(this, Home.class);
        intent.putExtra("CarKye",userID);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
    private void showOrHidePasswordField() {
        loginShowPasswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
    }
    public void AutoPermitiom(){

        if (ContextCompat.checkSelfPermission(Login.this, "android.permission.CAMERA") +
                ContextCompat.checkSelfPermission(Login.this, "android.permission.READ_CONTACTS") +
                ContextCompat.checkSelfPermission(Login.this, "android.permission.CALL_PHONE") +
                ContextCompat.checkSelfPermission(Login.this, "android.permission.ACCESS_WIFI_STATE") +
                ContextCompat.checkSelfPermission(Login.this, "android.permission.INTERNET") +
                ContextCompat.checkSelfPermission(Login.this, "android.permission.WRITE_EXTERNAL_STORAGE") +
                ContextCompat.checkSelfPermission(Login.this, "android.permission.READ_EXTERNAL_STORAGE") == 0)
        {
            Toast.makeText(Login.this.getApplicationContext(), "Permission already granted..", Toast.LENGTH_LONG).show();

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this, "android.permission.CAMERA")
                || ActivityCompat.shouldShowRequestPermissionRationale(Login.this, "android.permission.READ_CONTACTS")
                || ActivityCompat.shouldShowRequestPermissionRationale(Login.this, "android.permission.CALL_PHONE")
                || ActivityCompat.shouldShowRequestPermissionRationale(Login.this, "android.permission.ACCESS_WIFI_STATE")
                || ActivityCompat.shouldShowRequestPermissionRationale(Login.this, "android.permission.INTERNET")
                || ActivityCompat.shouldShowRequestPermissionRationale(Login.this, "android.permission.WRITE_EXTERNAL_STORAGE")
                || ActivityCompat.shouldShowRequestPermissionRationale(Login.this, "android.permission.READ_EXTERNAL_STORAGE"))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            builder.setTitle( "Grant those Permission");
            builder.setMessage( "Camera, Read Contact, Write and Read EXTERNAL Storage");
            builder.setPositiveButton( "OK",  new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(Login.this,

                            new String[]
                                    {
                                            "android.permission.CAMERA",
                                            "android.permission.WRITE_EXTERNAL_STORAGE",
                                            "android.permission.INTERNET",
                                            "android.permission.READ_CONTACTS",
                                            "android.permission.CALL_PHONE",
                                            "android.permission.ACCESS_WIFI_STATE",
                                            "android.permission.READ_EXTERNAL_STORAGE"},
                            Login.REQUEST_CODE);
                }
            });
            builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AutoPermitiom();

                }
            });
            builder.create().show();
        } else {
            ActivityCompat.requestPermissions(Login.this,
                    new String[]
                            {
                                    "android.permission.CAMERA",
                                    "android.permission.WRITE_EXTERNAL_STORAGE",
                                    "android.permission.INTERNET",
                                    "android.permission.READ_CONTACTS",
                                    "android.permission.CALL_PHONE",
                                    "android.permission.ACCESS_WIFI_STATE",
                                    "android.permission.READ_EXTERNAL_STORAGE"},
                    Login.REQUEST_CODE);
        }
    }
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            return;
        }
        if ((grantResults.length < 0)
                && (grantResults[0]
                + grantResults[1]
                + grantResults[2]
                + grantResults[3]
                + grantResults[4]
                + grantResults[5]
                == PackageManager.PERMISSION_GRANTED))
        {
            Toast.makeText(getApplicationContext(), "Permission Granted...", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Permission Denied...", Toast.LENGTH_LONG).show();
        }
    }

}