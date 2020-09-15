package com.atcibasi.new_atcibasi.post;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
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

import com.atcibasi.new_atcibasi.R;
import com.atcibasi.new_atcibasi.Uploadk1;
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

public class KonukTahmin2Paylasim extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST=1;
    private Button mButtonChoseImage;
    private Button mButtonUpload;
    private TextView mKonuk2Post;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mk2ImageUri;

    private StorageReference mStoregeRef;
    private DatabaseReference mDatabeseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konuk_tahmin2_paylasim);

        mButtonChoseImage=findViewById(R.id.k2_button_choose_image);
        mButtonUpload=findViewById(R.id.k2_button_upload);
        mEditTextFileName=findViewById(R.id.k2_edit_text_name);
        mProgressBar=findViewById(R.id.progres_bar);
        mImageView=findViewById(R.id.k2_image_view);
        mKonuk2Post=findViewById(R.id.konuk2_post);

        mStoregeRef= FirebaseStorage.getInstance().getReference("uploads_k2");
        mDatabeseRef= FirebaseDatabase.getInstance().getReference("uploads_k2");

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
                    Toast.makeText(KonukTahmin2Paylasim.this,"Yükleme Yapılıyor", Toast.LENGTH_SHORT).show();

                }else
                {
                    uploadFile();

                }
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
            mk2ImageUri=data.getData();
            Picasso.with(this).load(mk2ImageUri).into(mImageView);

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if(mk2ImageUri!=null)
        {
            final StorageReference fileRefrance=mStoregeRef.child(System.currentTimeMillis()+"."+getFileExtension(mk2ImageUri));
            mUploadTask=fileRefrance.putFile(mk2ImageUri)
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
                                    Uploadk1 upload=new Uploadk1(mEditTextFileName.getText().toString().trim(),uri.toString());
                                    String uploadId=mDatabeseRef.push().getKey();
                                    mDatabeseRef.child(uploadId).setValue(upload);
                                    Toast.makeText(KonukTahmin2Paylasim.this,"Paylaşım Başarılı", Toast.LENGTH_SHORT).show();
                                    mEditTextFileName.setText("");

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(KonukTahmin2Paylasim.this,e.getMessage(), Toast.LENGTH_SHORT).show();

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
}
