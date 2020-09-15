package com.atcibasi.new_atcibasi.post;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.atcibasi.new_atcibasi.R;
import com.atcibasi.new_atcibasi.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class PostActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST=1;
    private Button mButtonChoseImage;
    private Button mButtonUpload;
    private TextView mKonuk1Post;
    private TextView mKonuk2Post;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;

    private StorageReference mStoregeRef;
    private DatabaseReference mDatabeseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mButtonChoseImage=findViewById(R.id.button_choose_image);
        mButtonUpload=findViewById(R.id.button_upload);
        mEditTextFileName=findViewById(R.id.edit_text_name);
        mProgressBar=findViewById(R.id.progres_bar);
        mImageView=findViewById(R.id.image_view);
        mKonuk1Post=findViewById(R.id.konuk1_post);
        mKonuk2Post=findViewById(R.id.konuk2_post);

        mStoregeRef= FirebaseStorage.getInstance().getReference("uploads");
        mDatabeseRef= FirebaseDatabase.getInstance().getReference("uploads");


        mButtonChoseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUploadTask!=null && mUploadTask.isInProgress())
                {
                    Toast.makeText(PostActivity.this,"Yükleme Yapılıyor", Toast.LENGTH_SHORT).show();

                }else
                {
                    uploadFile();


                }



            }
        });

        mKonuk1Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PostActivity.this, KonukTahmin1Paylasim.class);
                startActivity(i);
            }
        });
        mKonuk2Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PostActivity.this,KonukTahmin2Paylasim.class);
                startActivity(i);
            }
        });


    }
    private  void openFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            mImageUri=data.getData();
            Picasso.with(this).load(mImageUri).into(mImageView);

        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if(mImageUri!=null)
        {
            final StorageReference fileRefrance=mStoregeRef.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));
            mUploadTask=fileRefrance.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler =new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            },500);
                            fileRefrance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Upload upload=new Upload(mEditTextFileName.getText().toString().trim(),uri.toString());
                                    String uploadId=mDatabeseRef.push().getKey();
                                    mDatabeseRef.child(uploadId).setValue(upload);
                                    Toast.makeText(PostActivity.this,"Paylaşım Başarılı", Toast.LENGTH_SHORT).show();
                                    mEditTextFileName.setText("");
                                    //notification();

                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PostActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }else{
            Toast.makeText(this,"Dosya seçilmedi", Toast.LENGTH_SHORT).show();
        }

    }
    private void notification(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"n")
                .setContentTitle("ATÇI BAŞI")
                .setSmallIcon(R.drawable.ic_natification)
                .setAutoCancel(true)
                .setContentText("Atçı Başından Yeni Gönderi var!!");
        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());

    }

}
