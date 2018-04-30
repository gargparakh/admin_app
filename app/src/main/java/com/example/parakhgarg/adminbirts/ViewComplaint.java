package com.example.parakhgarg.adminbirts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

public class ViewComplaint extends AppCompatActivity {

    Bundle b;
    String accessToken,secretKey,name,email;
    String URL_FOR_COMPLAINT_BY_ID = Constants.SERVER+"/complaint/show_complaint_by_id";
    String URL_FOR_ALERT = Constants.SERVER+"/complaint/create_alert";
    ProgressDialog progressDialog;
    ImageView tv_image;
    Button save_button,alert;
    Context context;
    TextView tv_subject,tv_description,tv_city,tv_userid,tv_status;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);
        Intent i = getIntent();
        b = i.getExtras();
        secretKey = b.getString("secretKey");
        accessToken = b.getString("accessToken");
        name = b.getString("name");
        email = b.getString("email");
        context = getApplicationContext();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        tv_subject = (TextView)findViewById(R.id.textView16);
        tv_description = (TextView)findViewById(R.id.textView17);
        tv_city = (TextView)findViewById(R.id.textView20);
        tv_userid = (TextView)findViewById(R.id.textView25);
        tv_status = (TextView)findViewById(R.id.textView26);
        tv_image = (ImageView)findViewById(R.id.imageView4);
        tv_subject.setText("Subject : "+b.getString("subject"));
        tv_description.setText("Description : "+b.getString("description"));
        tv_city.setText("Created At : "+b.getString("created_at"));
        tv_userid.setText("User ID : "+b.getString("user_id"));
        tv_status.setText("Status : "+b.getString("status"));
        tv_subject.setShadowLayer(30, 0, 0, Color.DKGRAY);
        tv_description.setShadowLayer(30, 0, 0, Color.DKGRAY);
        tv_city.setShadowLayer(30, 0, 0, Color.DKGRAY);
        tv_userid.setShadowLayer(30, 0, 0, Color.DKGRAY);
        tv_status.setShadowLayer(30, 0, 0, Color.DKGRAY);

        save_button = (Button)findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewComplaint.this,HomePage.class);
                Bundle b2 = new Bundle();
                b2.putString("accessToken",accessToken);
                b2.putString("secretKey",secretKey);
                b2.putString("name",name);
                b2.putString("email",email);
                i.putExtras(b);
                startActivity(i);
            }
        });

        Button button = (Button) findViewById(R.id.chat_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });

    }

}
