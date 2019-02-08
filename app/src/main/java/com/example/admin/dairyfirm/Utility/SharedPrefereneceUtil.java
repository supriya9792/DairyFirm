package com.example.admin.dairyfirm.Utility;

/**
 * Created by admin on 7/11/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class SharedPrefereneceUtil {

    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;




    public static void setAreaid(Activity activity,String areaid) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("area_id", areaid);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }
    public static String getAreaid(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("area_id", null);
        Log.v("SharedPreferenece:: ","Stored ::");
        return id;
    }


    public static void setadminId(Activity activity,String adminid) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("admin_id", adminid);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }

    public static String getadminId(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("admin_id", null);
        Log.v("SharedPreferenece:: ","admin_id ::");
        return id;
    }
    public static String getDate(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String date= pref.getString("Date", null);
        Log.v("SharedPreferenece:: ","Date ::");
        return date;
    }
    public static void setStream(Activity activity,String stream) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("stream", stream);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }
    public static String getStream(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("stream", null);
        Log.v("SharedPreferenece:: ","Stored ::");
        return id;
    }


    public static void setA_empid(Activity activity,String imgurl) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("emp_id", imgurl);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }
    public static String getA_empid(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("emp_id", null);
        Log.v("SharedPreferenece:: ","Stored ::");
        return id;
    }

    public static String getUserNm(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("user_name", null);
        Log.v("SharedPreferenece:: ","Stored ::");
        return id;
    }



    public static void setUserNm(Activity activity,String userId) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_name", userId);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }

    public static String getUserId(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("userId", null);
        Log.v("SharedPreferenece:: ","Stored ::");
        return id;
    }



    public static void setUserId(Activity activity,String userId) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userId", userId);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }

    public static String getEmail(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("email", null);
        Log.v("SharedPreferenece:: ","Stored ::");
        return id;
    }



    public static void setEmail(Activity activity,String userId) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email", userId);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }



    public static void setRsvpId(Activity activity,String clubdid) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("rsvp_id", clubdid);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }
    public static String getRsvpId(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("rsvp_id", null);
        Log.v("SharedPreferenece:: ","Stored ::");
        return id;
    }


    public static void setTokan(Activity activity,String userId) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", userId);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }
    public static void LogOut(Activity activity){

        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.remove("rsvp_id");
        editor.remove("emp_id");
        editor.remove("club_id");
        editor.remove("id");
        editor.remove("imageurl");
        editor.clear();
        editor.commit();

        Log.v("SharedPreferenece:: ", "LogoutFragment ::");
    }
}
