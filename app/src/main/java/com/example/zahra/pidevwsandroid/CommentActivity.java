package com.example.zahra.pidevwsandroid;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zahra.pidevwsandroid.model.Comment;
import com.example.zahra.pidevwsandroid.remote.APIUtils;
import com.example.zahra.pidevwsandroid.remote.CommentService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {

    CommentService commentService;
    EditText editCId;
    EditText editComment;
    Button btnSave;
    Button bntDel;
    //    Button btnBack;
    TextView txtCId;
    //  TextView txtContetnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.zahra.pidevwsandroid.R.layout.activity_comment);

        if(getSupportActionBar()!=null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        editCId =(EditText) findViewById(com.example.zahra.pidevwsandroid.R.id.editCId);
        editComment =(EditText) findViewById(com.example.zahra.pidevwsandroid.R.id.editComment);
        btnSave =(Button) findViewById(com.example.zahra.pidevwsandroid.R.id.btnSave);
        bntDel =(Button) findViewById(com.example.zahra.pidevwsandroid.R.id.bntDel);
        txtCId =(TextView) findViewById(com.example.zahra.pidevwsandroid.R.id.txtCId);

        commentService = APIUtils.getCommentService();
        Bundle extras = getIntent().getExtras();
       final String commentId = extras.getString("comment_id");
        final String content = extras.getString("content");
        final int idc=extras.getInt("idc");



        editCId.setText(commentId);
        editComment.setText(content);

        if(commentId != null && commentId.trim().length() > 0 ){
            editCId.setFocusable(false);
        }else{
            txtCId.setVisibility(View.INVISIBLE);
            editCId.setVisibility(View.INVISIBLE);
            bntDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment c = new Comment();
                c.setDescription(editComment.getText().toString());

                if(commentId != null && commentId.trim().length() > 0){

                    c.setId(idc);
                    updateComment(c);
                    Toast.makeText(CommentActivity.this, "Comment updated successfully!",Toast.LENGTH_SHORT).show();
                    //System.out.println("EDITTTT");
                }else{

                    if (editComment.getText().toString().equals("")) {
                        Toast.makeText(CommentActivity.this, "Field comment required.",Toast.LENGTH_SHORT).show();

                    }else {
                        addComment(c);
                        //System.out.println("Add");
                        Toast.makeText(CommentActivity.this, "Comment inserted successfully!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        bntDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder altdial = new AlertDialog.Builder(CommentActivity.this);
                altdial.setMessage("Do you want to delete this comment ?").setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                deleteComment(Integer.parseInt(commentId));

                                Toast.makeText(CommentActivity.this, "Comment deleted successfully!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = altdial.create();
                alert.setTitle("Delete confirmation");
                alert.show();
            }
        });
    }

    public void addComment(Comment c){
        Call<Comment> call = commentService.addComment(c);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(response.isSuccessful()){
                    //System.out.println("Add success !!!!!!!!!!!!!");
                    Toast.makeText(CommentActivity.this, "Comment inserted successfully!",Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        Log.v("Error code 400",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("ERROR: ",t.getMessage());
            }
        });
    }


    public void updateComment(Comment c){
        Call<Comment> call = commentService.updateComment(c);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(response.isSuccessful()){

                    Toast.makeText(CommentActivity.this, "Comment updated successfully!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("ERROR: ",t.getMessage());
            }
        });
    }

    public void deleteComment(Integer id){
        Call<Comment> call = commentService.deleteComment(id);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {

                if(response.isSuccessful()){
                    Toast.makeText(CommentActivity.this, "Comment deleted successfully!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("ERROR: ",t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
