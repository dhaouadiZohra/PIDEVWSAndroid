package com.example.zahra.pidevwsandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zahra.pidevwsandroid.model.Comment;
import com.example.zahra.pidevwsandroid.remote.APIUtils;
import com.example.zahra.pidevwsandroid.remote.CommentService;
import com.google.zxing.Result;


import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler
        {


    Button btnAddComment,sendMail;
    Button btnGetCommentList;
    ListView listView;
    CommentService commentService;
    List<Comment> list = new ArrayList<Comment>();
    ImageView imageView;
    TextView textname,textemail;
    Button btnLogout;
    Button button,scan;
  //  private LoginButton loginButton;
  private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     // setSupportActionBar(toolbar);


//
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.example.haroun.pidevwsandroid",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }

        btnAddComment = (Button) findViewById(R.id.btnAddComment);
        scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScannerView = new ZXingScannerView(MainActivity.this);
                setContentView(mScannerView);
                mScannerView.setResultHandler(MainActivity.this);
                mScannerView.startCamera();
            }
        });
        btnGetCommentList=(Button) findViewById(R.id.btnGetCommentList);
        listView = (ListView) findViewById(R.id.listView);

        imageView=(ImageView)findViewById(R.id.imageView2);
        textname=(TextView)findViewById(R.id.textView3);
        textemail=(TextView)findViewById(R.id.textView4);
        button=(Button)findViewById(R.id.button2);

        sendMail = (Button)findViewById(R.id.SendEmail);
      //  final String firstname=getIntent().getExtras().getString("firstname");
        //final String lastname=getIntent().getExtras().getString("lastname");
    //    final  String image=getIntent().getExtras().getString("image");

        // String url_pic="https://graph.facebook.com/1958749884364942/picture?width=100&height=100";

        // System.out.println("PIC_HAROUN = "+image);

//        if (firstname!=null&&lastname!=null&&email!=null&&image!=null)
//        {
      //  textname.setText("Welcome to IRMC ");
    //   Glide.with(MainActivity.this).load(image).into(imageView);

        //textemail.setText(email);
        //System.out.println("EEEEE :"+email);
        //System.out.println("EEEEE :"+firstname);
        //System.out.println("EEEEE :"+lastname);
        // textemail.setText(email);
     //    Glide.with(MainActivity.this).load(image).into(imageView);
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(),"Data Not Found",Toast.LENGTH_SHORT).show();
//        }

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                LoginManager.getInstance().logOut();
//
//                startActivity(login);
//                finish();
//
//            }
//        });
        commentService = APIUtils.getCommentService();

        getCommentsList();

        //btnLogout.setOnClickListener(new View.OnClickListener() {
           // @Override
          //  public void onClick(View view) {
            //    AlertDialog.Builder altdial = new AlertDialog.Builder(MainActivity.this);
            //    altdial.setMessage("Do you really want to Logout ?").setCancelable(false)
              //          .setPositiveButton("Disconnect", new DialogInterface.OnClickListener() {
                          //  @Override
                          //  public void onClick(DialogInterface dialogInterface, int i) {
                           //    LoginManager.getInstance().logOut();

                 //               startActivity(login);
                 //               finish();
                  //          }
                  //      })
                    //    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                         //   @Override
                          //  public void onClick(DialogInterface dialogInterface, int i) {
                     //           dialogInterface.cancel();
                     //       }
                   //     });
               /// AlertDialog alert = altdial.create();
             //   alert.setTitle("Logout Facebook confirmation");
               // alert.show();
          //  }
      //  });

        btnGetCommentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get comments list
                Log.d("aa", "onClick: ");
                getCommentsList();
            }
        });

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommentActivity.class);
                intent.putExtra("comment", "");
                startActivity(intent);

            }
        });
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, SendMailActivity.class);
                startActivity(mIntent);
            }
        });
    }


            @Override
            public void handleResult(Result result) {

                String res = result.getText();
                Log.d("zhra" , res ) ;

                mScannerView.resumeCameraPreview(MainActivity.this);

            }



    public void getCommentsList(){
        Call<List<Comment>> call = commentService.getComments();
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    Log.d("list", "onResponse: "+list);
                    listView.setAdapter(new CommentAdapter(MainActivity.this,R.layout.list_comment, list));
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("ERROR: ",t.getCause().getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
