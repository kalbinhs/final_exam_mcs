package com.example.final_exam_mcs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Post> posts;

    public PostAdapter(Context context, ArrayList<Post> data){
        this.context = context;
        this.posts = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post currentPost = posts.get(position);
        holder.tvId.setText(String.valueOf(currentPost.getId()));
        holder.tvUserId.setText(String.valueOf(currentPost.getUserID()));
        holder.tvTitle.setText(currentPost.getTitle());
        holder.tvBody.setText(currentPost.getBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvId, tvUserId, tvTitle, tvBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvIdMainPage);
            tvUserId = itemView.findViewById(R.id.tvUserIdMainPage);
            tvTitle = itemView.findViewById(R.id.tvTitleMainPage);
            tvBody = itemView.findViewById(R.id.tvBodyMainPage);
        }
    }

}
