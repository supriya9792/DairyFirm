package com.example.admin.dairyfirm.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dairyfirm.R;
import com.example.admin.dairyfirm.Utility.ServerClass;
import com.example.admin.dairyfirm.Utility.ServiceUrls;
import com.example.admin.dairyfirm.Utility.SharedPrefereneceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class BillActivity extends AppCompatActivity  implements View.OnClickListener{
    TextView total_quantity,Total_price,Paid_amount,Remaining_amount;
    TextView custname,month;
    Button pay;
    String month_name;
    String monthyear;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 1;
    public static String TAG = BillActivity.class.getName();
    ProgressDialog pd;
    String cust_id;
    String amount;
    String df_bill_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        total_quantity=(TextView)findViewById(R.id.ttl_quantity);
        Total_price=(TextView)findViewById(R.id.ttl_price);
        Paid_amount=(TextView)findViewById(R.id.paid_amount);
        Remaining_amount=(TextView)findViewById(R.id.rm_amount);
        custname=(TextView)findViewById(R.id.custname);
        month=(TextView)findViewById(R.id.month);
        pay=(Button)findViewById(R.id.pay);

        month.setOnClickListener(this);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);

         cust_id = getIntent().getStringExtra("cust_id");
        String name = getIntent().getStringExtra("name");
        custname.setText(name);
       pay.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            final EditText editText = new EditText(v.getContext());
             editText.setBackground(getResources().getDrawable(R.drawable.quantitybackground));
             LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                     LinearLayout.LayoutParams.WRAP_CONTENT,
                     LinearLayout.LayoutParams.WRAP_CONTENT);
             int left = getPixelValue(v.getContext(), 10);
             int top = getPixelValue(v.getContext(), 10);
             int right = getPixelValue(v.getContext(), 10);
             int bottom = getPixelValue(v.getContext(),10);
             layoutParams.setMargins(left,top,right,bottom);
             editText.setLayoutParams(layoutParams);
                // editText.setHint("dd.MM.yyyy");
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                // Setting Dialog Title
                alertDialog.setTitle("Please Enter Amount");
                // Setting Dialog Message
                //alertDialog.setMessage(" You want to cancel followup? ");
                alertDialog.setView(editText);

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                //FollowupNextDateClass();

                amount=editText.getText().toString();
                Log.v(TAG, String.format("doInBackground ::  date1= %s", amount));
              /*  Smv_id=smv_id;
                Log.v(TAG, String.format("doInBackground ::  Smv_id= %s", Smv_id));
                FollowupNextDateClass();*/
                        adddeposite();
                    /*    Intent intent=new Intent(BillActivity.this, BillActivity.class);
                startActivity(intent);*/
                       /* Intent intent =new Intent(v.getContext(),FollowupActivity.class);
                        v.getContext().startActivity(intent);*/
                // Write your code here to invoke YES event
                //Toast.makeText(mContext, "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                //Toast.makeText(mContext, "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
});

    }
    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }
    @Override
    public void onClick(View view) {
        if(view == month) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH-1);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            //final String[] MONTH = {"01", "Febuary", "March", "April", "May", "June", "Jule", "August", "September", "Octomber", "November", "December"};
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            SimpleDateFormat month_date = new SimpleDateFormat("MM");
                            month_name = month_date.format(calendar.getTime());
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                            monthyear= formatter.format(calendar.getTime());
                            month.setText(month_name+"-"+monthyear);
                            monthlybillClass();
                            getmonthlybillClass();
                            //adapter.notifyDataSetChanged();
//Toast.makeText(SalaryActivity.this,month_name,Toast.LENGTH_SHORT).show();
                        }
                    }, mYear, mMonth, mDay);
            ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
            datePickerDialog.show();

        }
    }

    private void monthlybillClass() {
        MonthlyBillTrackClass ru = new MonthlyBillTrackClass();
        ru.execute("5");

    }

    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        pd = new ProgressDialog(BillActivity.this);
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



    class MonthlyBillTrackClass extends AsyncTask<String, Void, String> {

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
            BillDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> BillDetails = new HashMap<String, String>();
            BillDetails.put("cust_id", cust_id);
            BillDetails.put("month", month_name);
            BillDetails.put("year", monthyear);
            BillDetails.put("action", "generate_bill_by_cust_id");
           // BillDetails.put("action", "get_bill_details_by_cust_id");
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, BillDetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;
        }


    }


    private void BillDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);
                String totalQauntity = object.getString("totalQuantity");
                String totalprice = object.getString("totalprice");
                total_quantity.setText(totalQauntity);
                Total_price.setText(totalprice);

            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }

    }
    private void getmonthlybillClass() {
        GetMonthlyBillTrackClass ru = new GetMonthlyBillTrackClass();
        ru.execute("5");
    }
    class GetMonthlyBillTrackClass extends AsyncTask<String, Void, String> {

        ServerClass ruc = new ServerClass();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(TAG, "onPreExecute");
            //showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response));
            dismissProgressDialog();
            //Toast.makeText(Admin_account.this, response, Toast.LENGTH_LONG).show();
            GetBillDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> GetBillDetails = new HashMap<String, String>();
            GetBillDetails.put("cust_id", cust_id);
            GetBillDetails.put("month", month.getText().toString());
            Log.v(TAG, String.format("doInBackground :: Month= %s", month.getText().toString()));
            GetBillDetails.put("action", "get_bill_details_by_cust_id");
            // BillDetails.put("action", "get_bill_details_by_cust_id");
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, GetBillDetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;
        }


    }


    private void GetBillDetails(String jsonResponse) {


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

                                String df_bill_ttl_price = jsonObj.getString("df_bill_ttl_price");
                                String df_bill_ttl_quantity = jsonObj.getString("df_bill_ttl_quantity");
                                String df_bill_deposited_price = jsonObj.getString("df_bill_deposited_price");
                                 df_bill_id = jsonObj.getString("df_bill_id");
                                int rm_amount=Integer.parseInt(df_bill_ttl_price)-Integer.parseInt(df_bill_deposited_price);
                                String RemaingAmount=String.valueOf(rm_amount);

                                Paid_amount.setText(df_bill_deposited_price);
                                Remaining_amount.setText(RemaingAmount);

                            }
                        }
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }

    }
    private void adddeposite() {
        AddDespositeAmountTrackClass ru = new AddDespositeAmountTrackClass();
        ru.execute("5");
    }
    class AddDespositeAmountTrackClass extends AsyncTask<String, Void, String> {

        ServerClass ruc = new ServerClass();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(TAG, "onPreExecute");
            //showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response));
            dismissProgressDialog();
            //Toast.makeText(Admin_account.this, response, Toast.LENGTH_LONG).show();
           AddDespositeDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> AddDespositeDetails = new HashMap<String, String>();
            AddDespositeDetails.put("bill_id", df_bill_id);
            AddDespositeDetails.put("amount", amount);
            Log.v(TAG, String.format("doInBackground :: Amount= %s", amount));
            AddDespositeDetails.put("action", "update_deposited_amt");
            // BillDetails.put("action", "get_bill_details_by_cust_id");
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, AddDespositeDetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;
        }


    }


    private void AddDespositeDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);
                String success=object.getString("success");
                if(success.equals("1")){
                    Toast.makeText(BillActivity.this,"Deposited Successfully",Toast.LENGTH_LONG).show();
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
