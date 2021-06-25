package com.example.BOOKED;

import com.budiyev.android.codescanner.CodeScanner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.ProgressBar;
import com.example.BOOKED.data.Result;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.CodeScannerView;
import java.util.Calendar;

import bolts.Capture;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.os.Handler;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import android.widget.ListView;
import android.widget.Toast;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;

//import com.google.firebase.database.DocumentReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.widget.Toast.LENGTH_SHORT;


public class EnterInfo extends AppCompatActivity {

    private static final String TAG = " ";
    Button submit;
    ImageButton pdf,scan;
    //View scanner_view;

    private TextView rdate,rtime,txt,docu;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    sun.bob.mcalendarview.MCalendarView calendarView;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mpdfStorageReference;
    private CodeScanner mCodeScanner;
    private static final int RC_PDF_PICKER = 2;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    EditText remail, rphone, rorg, rpurp, rhall;
    String date,tim,phone;
    String ani,email,pop,msg;
    private Handler handler = new Handler();
    int flag=0;
    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);



        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);






        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference("Bookings");



        mAuth = FirebaseAuth.getInstance();


        docu = (TextView) findViewById(R.id.textView3);
        txt = (TextView) findViewById(R.id.textView2);
        docu.setVisibility(View.INVISIBLE);

        pdf = (ImageButton) findViewById(R.id.imageButton);
        submit = (Button) findViewById(R.id.button2);


        rorg = (EditText) findViewById(R.id.editTextTextPersonName5);
        rpurp = (EditText) findViewById(R.id.editTextTextPersonName7);
        rhall = (EditText) findViewById(R.id.editTextTextPersonName8);
        rdate = (TextView) findViewById(R.id.textView);
        rtime = (TextView) findViewById(R.id.editTextTime);


        int i = 1;




        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseStorage = FirebaseStorage.getInstance();
                mpdfStorageReference = mFirebaseStorage.getReference().child("doc");

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PDF_PICKER);



            }
        });


        rdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EnterInfo.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);


                date = month + "-" + day + "-" + year;
                rdate.setText(date);
            }
        };

        rtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calendar  = Calendar.getInstance();
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(EnterInfo.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        rtime.setText( selectedHour + ":" + selectedMinute);
                        tim = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                 email = user.getEmail();
                 phone = user.getPhoneNumber();

                // String email = remail.getText().toString();
                //  String phone = rphone.getText().toString();
                String org = rorg.getText().toString();
                String purp = rpurp.getText().toString();
                String hall = rhall.getText().toString();
                //String date = rdate.getText().toString();
                //String time = rtime.getText().toString();

                String approv = "Not Approved❌";
                String logo = mMessagesDatabaseReference.push().getKey();
                ani = logo;
                Info dota = new Info(email, " ", org, purp, hall, date, tim, approv,pop);
                mMessagesDatabaseReference.child(logo).setValue(dota);

                Toast.makeText(getApplicationContext(),
                        "Response Submitted",
                        Toast.LENGTH_LONG)
                        .show();


                remail.setText("");
                rphone.setText("");
                rorg.setText("");
                rhall.setText("");  
                rpurp.setText("");
                rdate.setText("");
                rtime.setText("");



                Toast.makeText(getApplicationContext(),
                        "Response Submitted",
                        Toast.LENGTH_LONG)
                        .show();


            }
        });




    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode,data );

        if (requestCode == RC_PDF_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            // Get a reference to store file at chat_photos/<FILENAME>
            StorageReference photoRef = mpdfStorageReference.child(selectedImageUri.getLastPathSegment());
            progressBar.setVisibility(View.VISIBLE);
            txt.setVisibility(View.INVISIBLE);
            docu.setVisibility(View.VISIBLE);

            // Upload file to Firebase Storage
            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final Uri downloadUrl = uri;
                                    //Info q = new ;
                                    pop =  downloadUrl.toString();
                                    docu.setVisibility(View.INVISIBLE);
                                    showToast("PDF Added");



                                }
                            });

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    System.out.println("Upload is " + progress + "% done");
                    int currentprogress = (int) progress;
                    progressBar.setProgress(currentprogress);
                }
            });


        }
    }
}