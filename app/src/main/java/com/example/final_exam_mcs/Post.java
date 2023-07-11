package com.example.final_exam_mcs;

public class Post {
    private int id, userID;
    private String title, body;

   public Post(int id, int userID, String title, String Body){
       this.id = id;
       this.userID = userID;
       this.title = title;
       this.body = Body;
   }

    public int getId() {
        return id;
    }

    public int getUserID() {
       return userID;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
