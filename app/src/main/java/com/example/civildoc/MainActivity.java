package com.example.civildoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText campoNome;
    private EditText campoDescricao;
    private EditText campoValor;
    private static final int CREATEPDF = 1;

    Spinner Item1Spinner,Item2Spinner;
    Button CreateBtn,SaveBtn1,SaveBtn2;
    EditText REfNo,SiteName,REQUISITIONFor,NotET;
    TextView Remarks1,Remarks2,Remarks3,Remarks4,Remarks5,Remarks6,Remarks7,Remarks8,Remarks9,Remarks10;
    TextView item1,item2,item3,item4,item5,item6,item7,item8,item9,item10;
    EditText qtyEt1,qtyEt2,qtyEt3,qtyEt4,qtyEt5,qtyEt6,qtyEt7,qtyEt8,qtyEt9,qtyEt10;
    TextView CompanyDATA,AdderssDATA,DesignationDATA,EmailDATA,PhoneDATA,NameDATA;

    Bitmap bmp, scaledbmp;
    int PageWidth=1200;
    Date dateobj;
    DateFormat dateFormat;
    float[] Prices = new float[] {0, 200, 300, 400,450,500};
    private DatabaseReference databaseReference;
    private String Company,Adderss,Name,Email,Phone,Designation;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        NameDATA=findViewById(R.id.NameDATA);
        PhoneDATA=findViewById(R.id.PhoneDATA);
        EmailDATA=findViewById(R.id.EmailDATA);
        DesignationDATA=findViewById(R.id.DesignationDATA);
        CompanyDATA=findViewById(R.id.CompanyDATA);
        AdderssDATA=findViewById(R.id.AdderssDATA);

        item1=findViewById(R.id.item1);
        item2=findViewById(R.id.item2);
        item3=findViewById(R.id.item3);
        item4=findViewById(R.id.item4);
        item5=findViewById(R.id.item5);
        item6=findViewById(R.id.item6);
        item7=findViewById(R.id.item7);
        item8=findViewById(R.id.item8);
        item9=findViewById(R.id.item9);
        item10=findViewById(R.id.item10);
        Remarks1=findViewById(R.id.Remarks1);
        Remarks2=findViewById(R.id.Remarks2);
        Remarks3=findViewById(R.id.Remarks3);
        Remarks4=findViewById(R.id.Remarks4);
        Remarks5=findViewById(R.id.Remarks5);
        Remarks6=findViewById(R.id.Remarks6);
        Remarks7=findViewById(R.id.Remarks7);
        Remarks8=findViewById(R.id.Remarks8);
        Remarks9=findViewById(R.id.Remarks9);
        Remarks10=findViewById(R.id.Remarks10);
        qtyEt1=findViewById(R.id.Qty1);
        qtyEt2=findViewById(R.id.Qty2);
        qtyEt3=findViewById(R.id.Qty3);
        qtyEt4=findViewById(R.id.Qty4);
        qtyEt5=findViewById(R.id.Qty5);
        qtyEt6=findViewById(R.id.Qty6);
        qtyEt7=findViewById(R.id.Qty7);
        qtyEt8=findViewById(R.id.Qty8);
        qtyEt9=findViewById(R.id.Qty9);
        qtyEt10=findViewById(R.id.Qty10);
        REfNo=findViewById(R.id.REfNo);
        SiteName=findViewById(R.id.SiteName);
        REQUISITIONFor=findViewById(R.id.REQUISITIONFor);
        NotET=findViewById(R.id.NotET);
        CreateBtn = findViewById(R.id.CreateBtn);
        SaveBtn1 = findViewById(R.id.SaveBtn1);
        SaveBtn2 = findViewById(R.id.SaveBtn2);

        UserDATA();

        FloatingActionButton fabGerar = findViewById(R.id.fab);

        fabGerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePdf("Write your file Name");
            }
        });

        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.image);
        scaledbmp= Bitmap.createScaledBitmap(bmp,1200,350,false);
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
    }

    public void CreatePdf(String title){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, title);
        startActivityForResult(intent, CREATEPDF);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREATEPDF){
            dateobj = new Date();
            if(data.getData()!=null){
                if
                        /*  !(TextUtils.isEmpty(NameDATA.getText()))
                        && !(TextUtils.isEmpty(PhoneDATA.getText()))
                        && !(TextUtils.isEmpty(EmailDATA.getText()))
                        && !(TextUtils.isEmpty(DesignationDATA.getText()))
                        && !(TextUtils.isEmpty(CompanyDATA.getText()))
                        && !(TextUtils.isEmpty(AdderssDATA.getText()))
                        && !(TextUtils.isEmpty(item1.getText()))
                        && !(TextUtils.isEmpty(Remarks1.getText()))
                        && !(TextUtils.isEmpty(qtyEt1.getText()))
                        && !(TextUtils.isEmpty(SiteName.getText()))
                        && !(TextUtils.isEmpty(REQUISITIONFor.getText()))
                        && !(TextUtils.isEmpty(REfNo.getText())))
                        */
                        (NameDATA.getText().toString().length() == 0 ||
                                PhoneDATA.getText().toString().length() == 0 ||
                                EmailDATA.getText().toString().length() == 0 ||
                                DesignationDATA.getText().toString().length() == 0 ||
                                CompanyDATA.getText().toString().length() == 0 ||
                                AdderssDATA.getText().toString().length() == 0 ||
                                item1.getText().toString().length() == 0 ||
                                Remarks1.getText().toString().length() == 0 ||
                                qtyEt1.getText().toString().length() == 0 ||
                                SiteName.getText().toString().length() == 0 ||
                                REQUISITIONFor.getText().toString().length() == 0 ||
                                REfNo.getText().toString().length() == 0)
                {
                    Toast.makeText(MainActivity.this, "Some Field Is Empty", Toast.LENGTH_LONG).show();

                }

                else{
                    Uri MyPDFFile = data.getData();
                    PdfDocument myPdfDocument = new PdfDocument();
                    Paint myPaint = new Paint();
                    Paint titlepaint = new Paint();

                    PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                    final PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                    Canvas canvas = myPage1.getCanvas();

                    canvas.drawBitmap(scaledbmp, 0, 0, myPaint);
                    titlepaint.setTextAlign(Paint.Align.CENTER);
                    titlepaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    titlepaint.setTextSize(90f);
                    titlepaint.setColor(Color.BLUE);
                    titlepaint.setColor(Color.rgb(122, 119, 119));
                    canvas.drawText("" + CompanyDATA.getText(), PageWidth / 2, 450, titlepaint);

                    myPaint.setTextAlign(Paint.Align.CENTER);
                    myPaint.setTextSize(20f);
                    myPaint.setColor(Color.rgb(0, 119, 119));
                    canvas.drawText("Company Adderss :" + AdderssDATA.getText(), PageWidth / 2, 1950, myPaint);
                    canvas.drawText("Abdulla Al Numan CivilChip App, Phone:+8801711433235", PageWidth / 2, 1980, myPaint);

                    titlepaint.setTextAlign(Paint.Align.CENTER);
                    titlepaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    titlepaint.setTextSize(70f);
                    titlepaint.setColor(Color.rgb(122, 119, 119));
                    canvas.drawText("REQUISITION FORM", PageWidth / 2, 550, titlepaint);


                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    myPaint.setTextSize(35f);
                    myPaint.setColor(Color.BLACK);
                    canvas.drawText("Ref No:" + REfNo.getText(), PageWidth - 20, 610, myPaint);
                    dateFormat = new SimpleDateFormat("dd/mm/yy");
                    canvas.drawText("Date:" + dateFormat.format(dateobj), PageWidth - 20, 660, myPaint);
                    dateFormat = new SimpleDateFormat("hh/mm/ss");
                    canvas.drawText("Time:" + dateFormat.format(dateobj), PageWidth - 20, 710, myPaint);
                    canvas.drawText("Site Name Or Code:" + SiteName.getText(), PageWidth - 20, 760, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    myPaint.setColor(Color.BLACK);
                    canvas.drawText("Name :     " + NameDATA.getText(), 20, 610, myPaint);
                    canvas.drawText("Phone :    " + PhoneDATA.getText(), 20, 670, myPaint);
                    canvas.drawText("Email :    " + EmailDATA.getText(), 20, 730, myPaint);
                    canvas.drawText("Designation :  " + DesignationDATA.getText(), 20, 790, myPaint);
                    canvas.drawText("REQUISITION FOR :  " + REQUISITIONFor.getText(), 20, 850, myPaint);


                    myPaint.setStyle(Paint.Style.STROKE);
                    myPaint.setStrokeWidth(2);
                    canvas.drawRect(20, 950, PageWidth - 20, 1030, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setStyle(Paint.Style.FILL);
                    myPaint.setTextSize(40f);
                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    canvas.drawText("SL NO ", 40, 1010, myPaint);
                    canvas.drawText("Item Descripion ", 200, 1010, myPaint);
                    canvas.drawText("Qty ", 700, 1010, myPaint);
                    canvas.drawText("REMARKS ", 900, 1010, myPaint);

                    canvas.drawLine(180, 960, 180, 1020, myPaint);
                    canvas.drawLine(680, 960, 680, 1020, myPaint);
                    canvas.drawLine(880, 960, 880, 1020, myPaint);
                    //canvas.drawLine(1030,830,1030,890,myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(35f);
                    myPaint.setColor(Color.BLACK);
                    canvas.drawText("01. ", 40, 1080, myPaint);

                    canvas.drawText("" + item1.getText(), 200, 1080, myPaint);
                    canvas.drawText("" + qtyEt1.getText(), 700, 1080, myPaint);
                    canvas.drawText("" + Remarks1.getText(), 900, 1080, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(35f);
                    myPaint.setColor(Color.BLACK);
                    canvas.drawText("02. ", 40, 1130, myPaint);
                    canvas.drawText("" + item2.getText(), 200, 1130, myPaint);
                    canvas.drawText("" + qtyEt2.getText(), 700, 1130, myPaint);
                    canvas.drawText("" + Remarks2.getText(), 900, 1130, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(35f);
                    myPaint.setColor(Color.BLACK);
                    canvas.drawText("03. ", 40, 1180, myPaint);
                    canvas.drawText("" + item3.getText(), 200, 1180, myPaint);
                    canvas.drawText("" + qtyEt3.getText(), 700, 1180, myPaint);
                    canvas.drawText("" + Remarks3.getText(), 900, 1180, myPaint);

                    myPdfDocument.finishPage(myPage1);
                    PDFCreated(MyPDFFile, myPdfDocument);
                }
            }
        }
    }

    private void PDFCreated(Uri MyPDFFile, PdfDocument pdfDocument) {
        try{
            BufferedOutputStream stream = new BufferedOutputStream(Objects.requireNonNull(getContentResolver().openOutputStream(MyPDFFile)));
            pdfDocument.writeTo(stream);
            pdfDocument.close();
            stream.flush();
            Toast.makeText(this, "PDF file Create Success", Toast.LENGTH_LONG).show();

        }catch (FileNotFoundException e){
            Toast.makeText(this, "Erro 1", Toast.LENGTH_LONG).show();
        }catch (IOException e){
            Toast.makeText(this, "Erro 2", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(this, "Erro 3"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private void UserDATA() {
        fAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(fAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Name=dataSnapshot.child("name").getValue().toString();
                Email=dataSnapshot.child("email").getValue().toString();
                Phone=dataSnapshot.child("phone").getValue().toString();
                Designation=dataSnapshot.child("designation").getValue().toString();
                Company=dataSnapshot.child("company").getValue().toString();
                Adderss=dataSnapshot.child("adderss").getValue().toString();
                NameDATA.setText(Name);
                EmailDATA.setText(Email);
                PhoneDATA.setText(Phone);
                DesignationDATA.setText(Designation);
                CompanyDATA.setText(Company);
                AdderssDATA.setText(Adderss);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}