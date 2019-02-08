package com.example.admin.dairyfirm.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.admin.dairyfirm.ModelAndAdapter.Spinner_cust_adapter;
import com.example.admin.dairyfirm.ModelAndAdapter.Spinner_cust_list;
import com.example.admin.dairyfirm.R;
import com.example.admin.dairyfirm.Utility.ServerClass;
import com.example.admin.dairyfirm.Utility.ServiceUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class CustomerLeaveActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText inputCustName,inputMilkQuantity, inputFromdate, inputToDate,inputRemark;
    private TextInputLayout inputLayoutCustName,inputLayoutMilkqty, inputLayoutFromDate,inputLayoutTodate,InputLayoutRemark;
    public Spinner customerName;
    private Button submit,close;
    String custname;
    private final String TAG = CustomerLeaveActivity.class.getName();
    private ProgressDialog pd;
    Spinner_cust_list cust_list;
    Spinner_cust_adapter adapter;
    ArrayList<Spinner_cust_list> spinArrayList = new ArrayList<Spinner_cust_list>();
    private int pYear;
    private int pMonth;
    private int pDay;
    String Date,output;
    String cust_id;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    String radiodata;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_leave);

        inputLayoutCustName = (TextInputLayout) findViewById(R.id.input_layout_cust_name);
        inputLayoutMilkqty = (TextInputLayout) findViewById(R.id.input_layout_milk_quantity);
        inputLayoutFromDate = (TextInputLayout) findViewById(R.id.input_layout_from_date);
        inputLayoutTodate = (TextInputLayout) findViewById(R.id.input_layout_to_date);
        InputLayoutRemark = (TextInputLayout) findViewById(R.id.input_layout_remark);

        inputCustName = (EditText) findViewById(R.id.input_cust_name);
        inputMilkQuantity = (EditText) findViewById(R.id.input_milk_quantity);
        inputFromdate = (EditText) findViewById(R.id.input_from_date);
        inputToDate = (EditText) findViewById(R.id.input_to_date);
        inputRemark = (EditText) findViewById(R.id.input_remark);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.input_milk_quantity, RegexTemplate.NOT_EMPTY,R.string.err_msg_password);
        awesomeValidation.addValidation(this,R.id.input_from_date, RegexTemplate.NOT_EMPTY,R.string.err_msg_password);
        awesomeValidation.addValidation(this,R.id.input_to_date, RegexTemplate.NOT_EMPTY,R.string.err_msg_password);

        String name = getIntent().getStringExtra("custname");
        inputCustName.setText(name);
         cust_id = getIntent().getStringExtra("cust_id");
        /*customerName=(Spinner)findViewById(R.id.custspinner);
        showCust_listClass();
        final Spinner_cust_adapter spinnerArrayAdapter = new Spinner_cust_adapter(
                this,spinArrayList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

        };*/
        submit=(Button)findViewById(R.id.btn_submit);
        submit.setOnClickListener(this);
        close=(Button)findViewById(R.id.btn_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* customerName.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        custname=String.valueOf(customerName.getSelectedItem());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                });*/

        final Calendar myCalendar = Calendar.getInstance();
        pYear = myCalendar.get(Calendar.YEAR);
        pMonth = myCalendar.get(Calendar.MONTH);
        pDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        final  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
              output= formatter.format(calendar.getTime());

                updateFromdate();
               // updateTodate();
                // TODO Auto-generated method stub

            }

        };
        final  DatePickerDialog.OnDateSetListener todate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                output= formatter.format(calendar.getTime());

                //updateFromdate();
                updateTodate();
                // TODO Auto-generated method stub

            }

        };
        inputFromdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CustomerLeaveActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        inputToDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CustomerLeaveActivity.this, todate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    radiodata=rb.getText().toString();
                    if(radiodata.equals("Leave")){
                        inputMilkQuantity.setText("0");
                        inputMilkQuantity.setEnabled(false);
                        //inputMilkQuantity.setCursorVisible(false);
                    }else{
                        inputMilkQuantity.getText().clear();
                        inputMilkQuantity.setEnabled(true);
                    }

                    //Toast.makeText(CustomerLeaveActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void  updateFromdate(){
        inputFromdate.setText(output);
        Date=inputFromdate.getText().toString();
    }
    public void  updateTodate(){
        inputToDate.setText(output);
        Date=inputToDate.getText().toString();
    }
    public void  newLeaveClass() {
       CustomerLeaveActivity.NewLeaveTrackClass ru = new CustomerLeaveActivity.NewLeaveTrackClass();
        ru.execute("5");
    }
    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        pd = new ProgressDialog(CustomerLeaveActivity.this);
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



    class NewLeaveTrackClass extends AsyncTask<String, Void, String> {

        ServerClass ruc = new ServerClass();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(TAG, "onPreExecute");
            // showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response));
            //dismissProgressDialog();
            //Toast.makeText(CandiateListView.this, response, Toast.LENGTH_LONG).show();
            //Toast.makeText(NewProductActivity.this, response, Toast.LENGTH_LONG).show();
            NewInquiryDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> NewInquiryDetails = new HashMap<String, String>();
            NewInquiryDetails.put("qty",inputMilkQuantity.getText().toString());
            Log.v(TAG, String.format("doInBackground :: qty= %s", inputMilkQuantity.getText().toString()));
            NewInquiryDetails.put("from_date",inputFromdate.getText().toString());
            Log.v(TAG, String.format("doInBackground :: from_date= %s", inputFromdate.getText().toString()));
            NewInquiryDetails.put("to_date",inputToDate.getText().toString());
            Log.v(TAG, String.format("doInBackground :: to_date= %s", inputToDate.getText().toString()));
            NewInquiryDetails.put("remark",inputRemark.getText().toString());
            Log.v(TAG, String.format("doInBackground :: remark= %s", inputRemark.getText().toString()));
            NewInquiryDetails.put("cust_id",cust_id);
            Log.v(TAG, String.format("doInBackground :: cust_id= %s", cust_id));
            NewInquiryDetails.put("loc_action","new");
            NewInquiryDetails.put("loc_id","-1");
            NewInquiryDetails.put("radiodata",radiodata);
            Log.v(TAG, String.format("doInBackground :: radiodata= %s", radiodata));

            NewInquiryDetails.put("action", "update_loc_by_id");
            String loginResult2 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, NewInquiryDetails);

            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult2));
            return loginResult2;
        }


    }


    private void NewInquiryDetails(String jsonResponse) {

        Log.v(TAG, String.format("loginServerResponse :: response = %s", jsonResponse));

        JSONObject jsonObjLoginResponse = null;
        try {
            jsonObjLoginResponse = new JSONObject(jsonResponse);
            String success = jsonObjLoginResponse.getString(getResources().getString(R.string.success));

            if (success.equalsIgnoreCase(getResources().getString(R.string.two))) {
                Toast.makeText(CustomerLeaveActivity.this,"Customer added succesfully",Toast.LENGTH_SHORT).show();
                inputMilkQuantity.getText().clear();
                inputFromdate.getText().clear();
                inputToDate.getText().clear();
                inputRemark.getText().clear();
                radioGroup.clearCheck();

                Intent intent=new Intent(CustomerLeaveActivity.this,MainActivity.class);
                startActivity(intent);
                //inputEmail, inputPhone,inputAdd,inputReq,inputFollowupdate;
            }


            else if (success.equalsIgnoreCase(getResources().getString(R.string.zero)))
            {
                Toast.makeText(CustomerLeaveActivity.this,"Failed to add",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void  showCust_listClass() {
        CustomerLeaveActivity.CustlistTrackClass ru = new CustomerLeaveActivity.CustlistTrackClass();
        ru.execute("5");
    }
    class CustlistTrackClass extends AsyncTask<String, Void, String> {

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
            //Toast.makeText(Employee.this, response, Toast.LENGTH_LONG).show();
            EmpDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> EmployeeDetails = new HashMap<String, String>();

            EmployeeDetails.put("action", "show_customer_list");
            //EmployeeDetails.put("admin_id", SharedPrefereneceUtil.getadminId(Employee.this));
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, EmployeeDetails);
            Log.v(TAG, String.format("doInBackground :: Customer_listFor spinner= %s", loginResult));
            return loginResult;
        }


    }


    private void EmpDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
//        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayoutPrabhagDetails);
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);
                if (object != null) {
                    JSONArray jsonArrayResult = object.getJSONArray("result");

                    if (jsonArrayResult != null && jsonArrayResult.length() > 0){
                        for (int i = 0; i < jsonArrayResult.length(); i++) {
                            cust_list = new Spinner_cust_list();
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                String fname     = jsonObj.getString("df_cust_fname");
                                String lname     = jsonObj.getString("df_cust_lname");
                                String name = fname + " " + lname;
                               // String email = jsonObj.getString("email");
                               // String phn_no = jsonObj.getString("mobile");
                                cust_id=jsonObj.getString("df_cust_id");

                                cust_list.setCustname(name);
                                spinArrayList.add(cust_list);
                                adapter = new Spinner_cust_adapter(CustomerLeaveActivity.this, spinArrayList);
                                customerName.setAdapter(adapter);


                            }
                        }}else if(jsonArrayResult.length()==0){
                        System.out.println("No records found");
                    }
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        submitForm();
        // custname.getText().clear();
        // emailid.getText().clear();
    }
    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
                //Toast.makeText(this, "Validation Succussfull", Toast.LENGTH_LONG).show();
            newLeaveClass();

        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
