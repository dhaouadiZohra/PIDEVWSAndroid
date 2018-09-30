package com.example.zahra.pidevwsandroid;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.zahra.pidevwsandroid.model.Comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;



public class CommentAdapter extends ArrayAdapter<Comment> {

    private Context context;
    private List<Comment> comments;


    public CommentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Comment> objects) {
        super(context, resource, objects);
        this.context=context;
        this.comments=objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_comment,parent,false);

        TextView txtCommentId=(TextView) rowView.findViewById(R.id.txtCommentId);
        TextView txtContent=(TextView) rowView.findViewById(R.id.txtContent);
       // TextView txtDateComment=(TextView) rowView.findViewById(com.example.haroun.pidevwsandroid.R.id.txtDateComment);

      //  DateFormat df= new SimpleDateFormat("MM/dd/yyyy");
        //  DateFormat df2= new SimpleDateFormat("hh:mm");



        //    txtCommentId.setText("User : "+comments.get(pos).getId() );
       // txtCommentId.setText("Dhaouadi" );

        txtContent.setText(""+comments.get(pos).getDescription());
        // txtDateComment.setText("Date : "+comments.get(pos).getDate().toString());
       // txtDateComment.setText(df.format(comments.get(pos).getPostdate()));




        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start Activity comment Form
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("comment_id", String.valueOf(comments.get(pos).getId()));
                intent.putExtra("idc", comments.get(pos).getId());
                intent.putExtra("content ",comments.get(pos).getDescription());
                context.startActivity(intent);
            }
        });


        return rowView;


    }
}
