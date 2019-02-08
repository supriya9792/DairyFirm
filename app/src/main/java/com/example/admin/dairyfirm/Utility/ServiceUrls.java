package com.example.admin.dairyfirm.Utility;

/**
 * Created by admin on 7/11/2017.
 SHA1: "6b2731c2d44526e3630bf5829d6b9b53448827b7"
 */
public class ServiceUrls {
    //public static String  root_path = "http://www.livemanager.in/dairydemo/interface/api.php";
    public static String  root_path =  "http://192.168.0.123/dailydairy/interface/api.php";
    public static String LOGIN_URL = root_path;
    // public static String  Contact_POST_URL=root_path+"contact_form.php";
    // public static String  Change_Password_URL=root_path+"check_password.php";

   // SHA1: "6b2731c2d44526e3630bf5829d6b9b53448827b7"
    // public static final String LOGIN_URL = "http://192.168.94.1/Android/LoginLogout/login.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedIn";
}
