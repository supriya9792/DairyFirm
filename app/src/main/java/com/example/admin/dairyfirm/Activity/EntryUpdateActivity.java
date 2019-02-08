package com.example.admin.dairyfirm.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dairyfirm.ModelAndAdapter.AreaAdapter;
import com.example.admin.dairyfirm.ModelAndAdapter.Area_list;
import com.example.admin.dairyfirm.ModelAndAdapter.CustomerAdapter;
import com.example.admin.dairyfirm.ModelAndAdapter.Customer_list;
import com.example.admin.dairyfirm.ModelAndAdapter.EntryAdapter;
import com.example.admin.dairyfirm.ModelAndAdapter.Entry_list;
import com.example.admin.dairyfirm.R;
import com.example.admin.dairyfirm.Utility.ServerClass;
import com.example.admin.dairyfirm.Utility.ServiceUrls;
import com.example.admin.dairyfirm.Utility.SharedPrefereneceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class EntryUpdateActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EntryAdapter adapter;
    Entry_list entryList;
    private RecyclerView.LayoutManager layoutManager;
    private List<Entry_list> entry_lists;
    ArrayList<Entry_list> entryArrayList = new ArrayList<Entry_list>();
    public static String TAG = MainActivity.class.getName();
    private ProgressDialog pd;
    private EditText inputsearch,pickupdate;
    private int pYear;
    private int pMonth;
    private int pDay;
    String entryDate,output;
    public Spinner area_ref;
    String selectedArea_ref;
    String area_id;
    String cust_id;
    Area_list areaModel;
    Area_list mSelected;
    String month_year;
    private List<Area_list> area_lists;
    ArrayList<Area_list> spinArrayList = new ArrayList<Area_list>();
    public AreaAdapter areaadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_update);

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        /*final ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        inputsearch=(EditText)findViewById(R.id.inputsearchid);
        pickupdate=(EditText)findViewById(R.id.date);
        Typeface font = Typeface.createFromAsset(getAssets(), "akshar.ttf");
        inputsearch.setTypeface(font);
        //inputsearch.setText("तुलसा");
        Button filter=(Button)findViewById(R.id.filter);
        //MobileAds.initialize(this, "ca-app-pub-5338690682332092~2628421550");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final Calendar myCalendar = Calendar.getInstance();
        pYear = myCalendar.get(Calendar.YEAR);
        pMonth = myCalendar.get(Calendar.MONTH);
        pDay = myCalendar.get(Calendar.DAY_OF_MONTH);


        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat datemonth = new SimpleDateFormat("yyyy-MM");
        month_year=String.valueOf(datemonth.format(c.getTime()));

        final  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                //Calendar calendar = Calendar.getInstance();
                myCalendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                output= formatter.format(myCalendar.getTime());

                updateDisplay();
                // TODO Auto-generated method stub

            }

        };

        //dpDialog.show();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        pickupdate.setText(dateFormat.format(new Date()));
        entryDate=pickupdate.getText().toString();

        final  DatePickerDialog dpDialog = new DatePickerDialog(this, date, pYear, pMonth, pDay);
        dpDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
        //dpDialog.show();
        pickupdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dpDialog.show();
            }
        });

        pickupdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (EntryUpdateActivity.this.adapter == null){
                    // some print statement saying it is null
                   entryArrayList.clear();
                    Toast toast = Toast.makeText(EntryUpdateActivity.this,"no record found", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
                else
                {
                    EntryUpdateActivity.this.adapter.filter(String.valueOf(arg0));

                    customerClass();

                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub


            }
        });
        area_ref=(Spinner) findViewById(R.id.areaspinner);
        showAreaRef_idClass();
       /* ArrayAdapter countryAdapter = new ArrayAdapter<Area_list>(this, android.R.layout.simple_spinner_dropdown_item, spinArrayList);
        area_ref.setPrompt("--Select Country--");
        area_ref.setAdapter(countryAdapter);*/
        final AreaAdapter spinnerArrayAdapter = new AreaAdapter(
                this,spinArrayList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    //area_id="1";
                    mSelected = (spinArrayList).get(position);
                    // area_id=mSelected.getArea_id();
                    //Log.i("area id:", area_id);
                    return false;
                }
                else
                {
                    return true;
                }
            }

        };
        area_ref.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        //area_ref.setSelection(position);
                        //String city = "The city is " + parent.getItemAtPosition(position).toString();
                        //selectedArea_ref = parent.getItemAtPosition(position).toString();
                        //selectedArea_ref = area_ref.getSelectedItem().toString();
                        ArrayList<Area_list> spinArrayList = new ArrayList<Area_list>();
                        mSelected = (Area_list) parent.getItemAtPosition(position);
                        Log.i("Name:", mSelected.getAreaname());
                        Log.i("Id:", mSelected.getArea_id());
                        area_id=mSelected.getArea_id();
                        Log.i("area_id:",area_id);


                        customerClass();

                        int custsize=  entryArrayList.size();
                        if( custsize==0){
                            entryArrayList.clear();

                        }else {
                            entryArrayList.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                });

        inputsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (EntryUpdateActivity.this.adapter == null){
                    // some print statement saying it is null
                    Toast toast = Toast.makeText(EntryUpdateActivity.this,"no record found", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    EntryUpdateActivity.this.adapter.filter(String.valueOf(arg0));

                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub


            }
        });

    }
    private void updateDisplay() {
        pickupdate.setText(output);
        entryDate=pickupdate.getText().toString();

    }
    private void customerClass() {
        EntryUpdateActivity.CustTrackClass ru = new EntryUpdateActivity.CustTrackClass();
        ru.execute("5");
    }
    public void  showAreaRef_idClass() {
        EntryUpdateActivity.AreaTrackClass ru = new EntryUpdateActivity.AreaTrackClass();
        ru.execute("5");
    }


    class CustTrackClass extends AsyncTask<String, Void, String> {

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
            //dismissProgressDialog();
            //Toast.makeText(Employee.this, response, Toast.LENGTH_LONG).show();
            CustometrDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> CustometrDetails = new HashMap<String, String>();
            CustometrDetails.put("area_id",area_id);
            Log.v(TAG, String.format("doInBackground :: area_id= %s", area_id));
            CustometrDetails.put("todaysdate", entryDate);
            Log.v(TAG, String.format("doInBackground :: EntryDate= %s", entryDate));
            CustometrDetails.put("user_id", SharedPrefereneceUtil.getUserId(EntryUpdateActivity.this));
            Log.v(TAG, String.format("doInBackground :: Userid= %s", SharedPrefereneceUtil.getUserId(EntryUpdateActivity.this)));
            CustometrDetails.put("action", "show_entry_list_by_area");
            // CustometrDetails.put("area_id", area_id);
            //EmployeeDetails.put("admin_id", SharedPrefereneceUtil.getadminId(Employee.this));
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, CustometrDetails);
            Log.v(TAG, String.format("doInBackground :: custresult= %s", loginResult));
            return loginResult;
        }


    }
    private void CustometrDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
//        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayoutPrabhagDetails);
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);
                if (object != null) {
                    JSONArray jsonArrayResult = object.getJSONArray("result");

                    if (jsonArrayResult != null && jsonArrayResult.length() > 0) {
                        for (int i = 0; i < jsonArrayResult.length(); i++) {
                            entryList = new Entry_list();
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                String fname = jsonObj.getString("df_cust_fname");
                                String lname = jsonObj.getString("df_cust_lname");
                                String name = fname + " " + lname;
                                String phn_no = jsonObj.getString("df_cust_mob");
                                String add = jsonObj.getString("df_cust_addr");
                                String qty = jsonObj.getString("df_cust_daily_req");
                                // String date = jsonObj.getString("date");
                                cust_id = jsonObj.getString("df_cust_id");
                                Log.v(TAG, String.format("doInBackground ::custId= %s", cust_id));
                               // String remaing_amount = jsonObj.getString("remaing_amount");
                                String category = jsonObj.getString("df_product_name");
                                String prod_cat_id = jsonObj.getString("df_cust_product_cat_ref_id");
                                String prod_rate = jsonObj.getString("df_product_rate");
                                String entry_id=jsonObj.getString("df_entry_id");
                                String area_ref_id = jsonObj.getString("df_cust_area_ref_id");
                                //df_bill_id = jsonObj.getString("df_bill_id");
                                String quantity = qty;

                                //int rm_amount=Integer.parseInt(df_bill_ttl_price)-Integer.parseInt(df_bill_deposited_price);
                                String custname="ग्राहकाचे नाव: "+name;
                                entryList.setCustname(custname);
                                // String phone="मोबाईल नंबर: "+phn_no;
                                entryList.setPhono(phn_no);
                                String Add="पत्ता: "+add;
                                entryList.setAdd(Add);
                                entryList.setQuanity(quantity);
                                entryList.setCust_id(cust_id);
                                // String entrydate=pickupdate.getText().toString();
                                entryList.setPickupdate(entryDate);
                                //custList.setBill_id(df_bill_id);
                                entryList.setProd_cat_id(prod_cat_id);
                                entryList.setProd_rate(prod_rate);
                                entryList.setArea_ref_id(mSelected.getArea_id());
                                entryList.setEntryId(entry_id);
                              entryArrayList.add(entryList);
                                adapter = new EntryAdapter(this, entryArrayList);

                                recyclerView.setAdapter(adapter);
//                                Log.v("party", party);



                            }
                        }
                    }else if (jsonArrayResult.length() == 0) {
                        System.out.println("No records found");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class AreaTrackClass extends AsyncTask<String, Void, String> {

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
            // dismissProgressDialog();
            //Toast.makeText(Employee.this, response, Toast.LENGTH_LONG).show();
            AreaDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> AreaDetails = new HashMap<String, String>();
            AreaDetails.put("user_id", SharedPrefereneceUtil.getUserId(EntryUpdateActivity.this));
            AreaDetails.put("action", "show_area_list_by_user_id");
            //EmployeeDetails.put("admin_id", SharedPrefereneceUtil.getadminId(Employee.this));
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, AreaDetails);
            Log.v(TAG, String.format("doInBackground :: areaResult= %s", loginResult));
            return loginResult;
        }


    }


    private void AreaDetails(String jsonResponse) {


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
                            areaModel = new Area_list();
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                String areaname     = jsonObj.getString("df_area_name");
                                String   area_id=jsonObj.getString("df_area_id");

                                areaModel.setAreaname(areaname);
                                areaModel.setArea_id(area_id);
                                //Toast.makeText(MainActivity.this,area_id,Toast.LENGTH_LONG).show();
                                spinArrayList.add(areaModel);

                                areaadapter = new AreaAdapter(EntryUpdateActivity.this, spinArrayList);

                                area_ref.setAdapter(areaadapter);


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
}
