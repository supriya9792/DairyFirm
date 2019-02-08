package com.example.admin.dairyfirm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dairyfirm.R;
import com.example.admin.dairyfirm.Utility.InternetConnection;
import com.example.admin.dairyfirm.Utility.ServerClass;
import com.example.admin.dairyfirm.Utility.ServiceUrls;
import com.example.admin.dairyfirm.Utility.SharedPrefereneceUtil;
import com.example.admin.dairyfirm.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.admin.dairyfirm.Utility.ServiceUrls.LOGGEDIN_SHARED_PREF;
import static com.example.admin.dairyfirm.Utility.ServiceUrls.LOGIN_URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextInputLayout inputLayoutPassword,inputLayoutusername;
    private Button button;

    String Susernm,Spassword;
    /**
     * TAG string used for log.
     */
    final static int RQS_1 = 1;
    private final String TAG = LoginActivity.class.getName();
    private String admin_role;
    private String employee_role;
    private String error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        // populateAutoComplete();
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_password);
        inputLayoutusername = (TextInputLayout) findViewById(R.id.input_username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    //attemptLogin();
                    return true;
                }
                return false;
            }
        });

        button = (Button) findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(this);


        mProgressView = findViewById(R.id.login_progress);

    }

    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        Utility.showProgressDialog(this);

    }

    /**
     * Dismiss the Progress dialog.
     */

    private void dismissProgressDialog() {
        Log.v(TAG, String.format("dismissProgressDialog"));
        Utility.hideProgressBar();

    }

    /**
     * Get the user input.
     */

    private void login() {
        Log.v(TAG, String.format("login"));
        Susernm =mEmailView.getText().toString().trim();
        Spassword = mPasswordView.getText().toString().trim();

        userLogin(Susernm, Spassword);
    }


    /**
     * Email and password send to userLoginClass.
     *
     * @param name of user send to server.
     * @param password of user send to server.
     */
    private void userLogin(final String name, final String password) {
        Log.v(TAG, String.format("userLogin :: email,password = %s,%s", name, password));

        if(InternetConnection.checkConnection(LoginActivity.this)) {
            UserLoginClass ulc = new UserLoginClass();
            ulc.execute(name, password);
        }
        else
        {

            Toast.makeText(LoginActivity.this,"Please Connect to Internet",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Perform the asyncTask sends data to server and gets response.
     */

    class UserLoginClass extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            Log.v(TAG,"onPreExecute");
            super.onPreExecute();
            //showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response));
            //dismissProgressDialog();
//            showToastMessage(response);
            //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
            loginServerResponse(response);
        }

        /**
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {
//            Log.v(TAG, String.format("doInBackground ::  params= %s", params));

            HashMap<String, String> loginData = new HashMap<>();
            loginData.put("username",mEmailView.getText().toString());
            loginData.put("password",mPasswordView.getText().toString());
            SharedPrefereneceUtil.setUserNm(LoginActivity.this, Susernm);
            loginData.put(ServerClass.ACTION,"login");
            ServerClass ruc = new ServerClass();
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, loginData);

            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;

        }
    }



    /**
     * Server response Operations.
     */

    private void loginServerResponse(String response) {
        Log.v(TAG, String.format("loginServerResponse :: response = %s", response));

        JSONObject jsonObjLoginResponse = null;
        try {
            jsonObjLoginResponse = new JSONObject(response);
            String success = jsonObjLoginResponse.getString(getResources().getString(R.string.success));

            if(success.equalsIgnoreCase(getResources().getString(R.string.one))){
                String userId = jsonObjLoginResponse.getString("user_id");
                String email = jsonObjLoginResponse.getString("user_email");
                SharedPrefereneceUtil.setUserId(LoginActivity.this, userId);
                SharedPrefereneceUtil.setEmail(LoginActivity.this,email);
                saveLoginCredentials();
                nextEmpActivity();

            }else if (success.equalsIgnoreCase(getResources().getString(R.string.two)))
            {
                showToastMessage("This is Employees Login Your Not Employee");
            }else if (success.equalsIgnoreCase(getResources().getString(R.string.zero)))
            {
                showToastMessage(getResources().getString(R.string.inavlidlogin));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }




    private void saveLoginCredentials() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
        editor.putString("username",Susernm );  // Saving string
        editor.putString("password", Spassword);
        editor.commit(); // commit changes
Toast.makeText(LoginActivity.this,"save login credentials ",Toast.LENGTH_SHORT).show();
    }
    private void loginActivity(){
        Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    private void nextEmpActivity() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showToastMessage(String message) {
        Log.v(TAG, String.format("showToastMessage :: message = %s", message));
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {

        if (v == button) {


            login();
        }
    }
}
