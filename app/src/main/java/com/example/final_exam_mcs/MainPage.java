package com.example.final_exam_mcs;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    private ArrayList<Post> posts;
    private RecyclerView rvPosts;
    String url = "https://jsonplaceholder.typicode.com/posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        Button attendanceBtn = findViewById(R.id.attendanceBtnMainPage);
        Button notificationBtn = findViewById(R.id.notificationBtnMainPage);

        initializeVariables();
        setValues();

        attendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new AttendanceFragment());

            }
        });

        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new NotificationFragment());

            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("attendance")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Attendance has been recorded!";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }

                    }
                });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    Log.d(TAG, "Token: " + token);
                });

    }

    private void initializeVariables(){
        posts = new ArrayList<>();
        rvPosts = findViewById(R.id.rvMainPage);
    }

    private void setValues(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        int id, userID;
                        String title, body;

                        for(int i = 0; i <response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                id = jsonObject.getInt("id");
                                userID = jsonObject.getInt("userId");
                                title = jsonObject.getString("title");
                                body = jsonObject.getString("body");

                                Post post = new Post(userID, id, title, body);
                                posts.add(post);

                                Log.i("ASD", post.getTitle());

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        setRecyclerView();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ASD", "Can't fetch data");
                Toast.makeText(MainPage.this, "Can't fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }

    private void setRecyclerView(){
        PostAdapter adapter = new PostAdapter(this, posts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
    }

    private void replaceFragment(Fragment fragment) {

        RecyclerView rvMainPage = findViewById(R.id.rvMainPage);
        rvMainPage.setVisibility(View.GONE); // Hide the RecyclerView

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMainPage, fragment);
        fragmentTransaction.commit();

    }

}