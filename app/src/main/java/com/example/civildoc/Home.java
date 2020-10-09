package com.example.civildoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class Home extends AppCompatActivity {

    private Button mPdfView,btnconpro,btnReset,btnAllCalcu,mShareBtn,ReqBTn,
            BtnReqution,BtnMobilization,BtnMobilizationfinal;
    static final int REQUEST_CODE = 123;
    RewardedAd rewardedAd;
    Button videoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AutoPermitiom();
        videoButton = findViewById(R.id.advideobutton);

        rewardedAd = new RewardedAd(this, "ca-app-pub-5084042290697877/4036762131");

        rewardedAd.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
                videoButton.setVisibility(View.VISIBLE);
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAd();
            }
        });

        mPdfView= (Button) findViewById(R.id.PdfView);
        btnconpro= (Button) findViewById(R.id.BasicCalculationBTN);
        btnReset= (Button) findViewById(R.id.onResetBtn);
        btnAllCalcu= (Button) findViewById(R.id.allcalBTN);
        mShareBtn= (Button) findViewById(R.id.ShareBtn);
        ReqBTn= (Button) findViewById(R.id.Req);
        BtnReqution= (Button) findViewById(R.id.Reqution);
        BtnMobilization= (Button) findViewById(R.id.Mobilization);
        BtnMobilizationfinal= (Button) findViewById(R.id.Mobilizationfinal);

        BtnMobilizationfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        ReqBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        BtnReqution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        BtnMobilization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btnAllCalcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        btnconpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        mPdfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //Toast.makeText(getApplicationContext(), "Coming Soon.", Toast.LENGTH_SHORT).show();
            }

        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming Soon.", Toast.LENGTH_SHORT).show();

            }
        });

        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Civil ABC APP Download Link: https://drive.google.com/file/d/11heaXDM_kYlYZtSFfz8zTE6ZJ6kdQ1Km/view?usp=sharing";
                String shareSubject = "Civil ABC App Download Link";
                sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,shareSubject);

                startActivity(Intent.createChooser(sharingIntent, "Share Using"));

            }
        });


    }
    public void displayAd() {
        rewardedAd.show(this, new RewardedAdCallback() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                videoButton.setVisibility(View.INVISIBLE);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Civil ABC APP Download Link: https://drive.google.com/file/d/11heaXDM_kYlYZtSFfz8zTE6ZJ6kdQ1Km/view?usp=sharing";
                String shareSubject = "Civil ABC App Download Link";
                sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);

                startActivity(Intent.createChooser(sharingIntent, "Share Using"));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    // simple method to show toast message
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }
    public void AutoPermitiom(){

        if (ContextCompat.checkSelfPermission(Home.this, "android.permission.CAMERA") +
                ContextCompat.checkSelfPermission(Home.this, "android.permission.READ_CONTACTS") +
                ContextCompat.checkSelfPermission(Home.this, "android.permission.INTERNET") +
                ContextCompat.checkSelfPermission(Home.this, "android.permission.WRITE_EXTERNAL_STORAGE") +
                ContextCompat.checkSelfPermission(Home.this, "android.permission.READ_EXTERNAL_STORAGE") == 0)
        {
            //Toast.makeText(MainActivity.this.getApplicationContext(), "Permission already granted..", Toast.LENGTH_LONG).show();

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this, "android.permission.CAMERA")
                || ActivityCompat.shouldShowRequestPermissionRationale(Home.this, "android.permission.READ_CONTACTS")
                || ActivityCompat.shouldShowRequestPermissionRationale(Home.this, "android.permission.INTERNET")
                || ActivityCompat.shouldShowRequestPermissionRationale(Home.this, "android.permission.WRITE_EXTERNAL_STORAGE")
                || ActivityCompat.shouldShowRequestPermissionRationale(Home.this, "android.permission.READ_EXTERNAL_STORAGE"))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setTitle( "Grant those Permission");
            builder.setMessage( "Camera, Read Contact and Read Storage");
            builder.setPositiveButton( "OK",  new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(Home.this,

                            new String[]
                                    {
                                            "android.permission.CAMERA",
                                            "android.permission.WRITE_EXTERNAL_STORAGE",
                                            "android.permission.INTERNET",
                                            "android.permission.READ_CONTACTS",
                                            "android.permission.READ_EXTERNAL_STORAGE"},
                            Home.REQUEST_CODE);
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
            ActivityCompat.requestPermissions(Home.this,
                    new String[]
                            {
                                    "android.permission.CAMERA",
                                    "android.permission.WRITE_EXTERNAL_STORAGE",
                                    "android.permission.INTERNET",
                                    "android.permission.READ_CONTACTS",
                                    "android.permission.READ_EXTERNAL_STORAGE"},
                    Home.REQUEST_CODE);
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
                == PackageManager.PERMISSION_GRANTED))
        {
            //Toast.makeText(getApplicationContext(), "Permission Granted...", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Permission Denied...", Toast.LENGTH_LONG).show();
        }
    }

}
