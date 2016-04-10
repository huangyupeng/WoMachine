package com.example.peng.graduationproject.model;

/**
 * Created by peng on 2016/4/9.
 */
public class UserInfoManager {

    private static String id;

    private static User currentUser;

    private static boolean changed = true;



    public static void setCurrentUser(String id){
        UserInfoManager.id = id;

    }


    public static User getCurrentUser(){
        if (id == null){
            return null;
        }
        if(changed || currentUser == null){
            //TODO get user info from database

        }

        return currentUser;
    }

    public static void infoChange (){
        changed = true;
    }
}
