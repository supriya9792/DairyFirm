package com.example.admin.dairyfirm.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.admin.dairyfirm.R;
import com.example.admin.dairyfirm.Utility.ServerClass;
import com.example.admin.dairyfirm.Utility.ServiceUrls;
import com.example.admin.dairyfirm.Utility.SharedPrefereneceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MyProfileActivity extends AppCompatActivity {
    private TextView txtid;
    private TextView txtnm;
    private TextView txtphn;
    private TextView txtemail;
    private TextView txtadd;
    private TextView txtbday;
    private TextView usrrole;
    public static String TAG = MyProfileActivity.class.getName();
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        txtnm = (TextView)findViewById( R.id.txtadminname );

        txtphn = (TextView)findViewById( R.id.txt_adminphn );
        txtemail = (TextView)findViewById( R.id.txt_adminemail );
        txtadd = (TextView)findViewById( R.id.txt_add );
        txtbday = (TextView)findViewById( R.id.txt_bday );
        usrrole = (TextView)findViewById( R.id.usrrole );

        empClass();
    }
    private void empClass() {
        EmpAccountClass ru = new EmpAccountClass();
        ru.execute("5");
    }

    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        pd = new ProgressDialog(MyProfileActivity.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();
    }

    /**
     * Dismiss Progress Dialog.
     */
    private void dismissProgressDialog() {
        Log.v(TAG, String.format("dismissProgressDialog"));

        pd.cancel();


    }



    class EmpAccountClass extends AsyncTask<String, Void, String> {

        ServerClass ruc = new ServerClass();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(TAG, "onPreExecute");
            showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response));
            dismissProgressDialog();
            //Toast.makeText(Admin_account.this, response, Toast.LENGTH_LONG).show();
            EmpDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> EmpDetails = new HashMap<String, String>();
            EmpDetails.put("action", "get_emp_details_by_id");
            //EmpDetails.put("emp_id", "5");
            EmpDetails.put("emp_id", SharedPrefereneceUtil.getUserId(MyProfileActivity.this));
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, EmpDetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;
        }


    }


    private void EmpDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);
                if (object != null) {
                    JSONArray jsonArrayResult = object.getJSONArray("result");

                    if (jsonArrayResult != null && jsonArrayResult.length() > 0)
                        for (int i = 0; i < jsonArrayResult.length(); i++) {

                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                String emp_id = jsonObj.getString("emp_id");
                                String fname = jsonObj.getString("fname");
                                String lname = jsonObj.getString("lname");
                                String phn_no = jsonObj.getString("mobile");
                                String email = jsonObj.getString("email");
                                String addr = jsonObj.getString("addr");
                                String bday = jsonObj.getString("bday");
                                String role = jsonObj.getString("role");
                                String name=fname+" "+lname;
                                String phne_no=" "+phn_no;
                                String emal="  "+email;
                                String add=" "+addr;
                                String bthday=" "+bday;
                                txtnm.setText(name);
                                txtphn.setText(phne_no);
                                txtemail.setText(emal);
                                txtadd.setText(add);
                                txtbday.setText(bthday);
                                usrrole.setText(role);

                            }
                        }
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
