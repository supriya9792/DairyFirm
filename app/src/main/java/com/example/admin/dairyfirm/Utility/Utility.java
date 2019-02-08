package com.example.admin.dairyfirm.Utility;

/**
 * Created by admin on 7/11/2017.
 */


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Utility {


    private static final String TAG = Utility.class.getSimpleName();
    private static ProgressDialog sProgressDialog;

    /**
     * This method will make the navigation bar and status bar transparent only for api 21+
     *
     * @param activity
     */
    public static void makeFullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Set the status bar and navigation bar as transparent
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static boolean isValidEmailAddress(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public static void showProgressDialog(Context context) {
        if (null != sProgressDialog && sProgressDialog.isShowing()) {
            return;
        }
        sProgressDialog = new ProgressDialog(context);

        sProgressDialog.setMessage("loading");
        sProgressDialog.setCancelable(false);

        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing()) {
                sProgressDialog.show();
            }
        } else {
            sProgressDialog.show();
        }
    }

    public static void hideProgressBar() {
        try {
            if (null != sProgressDialog && sProgressDialog.isShowing()) {

                Context context = sProgressDialog.getContext();

                if (context instanceof Activity) {

                    if (!((Activity) context).isFinishing()) {


                        sProgressDialog.dismiss();
                        sProgressDialog = null;
                    }
                } else {


                    sProgressDialog.dismiss();
                    sProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "Simple ignore the exceprion", e);
        }
    }

    //checks if device connected to internet
    public static boolean isConnectedToInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);

                if (networkInfo != null && networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo networkInfo : info) {
                        if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d(TAG,
                                    "NETWORKNAME: " + networkInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        Log.v(TAG, "not connected to internet");
        return false;
    }

    //hides soft keyboard
    public static void hideKeyBordActivity(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /* public static void setSpinnerAdapter(Context mContext, Spinner spinner, ArrayList<String> arrayList) {
         ArrayAdapter spinnerAdapter = new ArrayAdapter(mContext, R.layout.spinner_item, arrayList);
         spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(spinnerAdapter);

     }*/
    public static String getDateFormatAsLastSeen(Calendar date) {

        //   String dayNumberSuffix = getDayNumberSuffix(date.get(Calendar.DAY_OF_MONTH));
        DateFormat dateFormat = new SimpleDateFormat(" d MMM yyyy");
        return dateFormat.format(date.getTime());
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//"yyyy-MM-dd"
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");//"yyyy-MM-dd"
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String getLocation(double latitude, double longitude, Context context) {

        Geocoder gc = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
            StringBuilder sb = new StringBuilder();
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                //for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                //  sb.append(address.getAddressLine(i)).append("\n");
                sb.append(address.getSubLocality()).append("\n");
                //    sb.append(address.getPostalCode()).append("\n");
                //sb.append(address.getCountryName());
                Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
                return sb.toString();
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }


    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
