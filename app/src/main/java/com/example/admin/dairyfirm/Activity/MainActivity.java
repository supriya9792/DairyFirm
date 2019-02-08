package com.example.admin.dairyfirm.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.print.PrintAttributes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dairyfirm.ModelAndAdapter.AreaAdapter;
import com.example.admin.dairyfirm.ModelAndAdapter.Area_list;
import com.example.admin.dairyfirm.ModelAndAdapter.CustomerAdapter;
import com.example.admin.dairyfirm.ModelAndAdapter.Customer_list;
import com.example.admin.dairyfirm.R;
import com.example.admin.dairyfirm.Utility.ServerClass;
import com.example.admin.dairyfirm.Utility.ServiceUrls;
import com.example.admin.dairyfirm.Utility.SharedPrefManager;
import com.example.admin.dairyfirm.Utility.SharedPrefereneceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.ViewAnimationUtils;

import static android.content.ContentValues.TAG;
import static android.view.Gravity.BOTTOM;

public class MainActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    Customer_list custList;
    private RecyclerView.LayoutManager layoutManager;
    private List<Customer_list> customer_lists;
   ArrayList<Customer_list> custArrayList = new ArrayList<Customer_list>();
   ArrayList<Customer_list> custfilterArrayList = new ArrayList<Customer_list>();
    public static String TAG = MainActivity.class.getName();
    private ProgressDialog pd;
    private TextInputLayout inputLayoutsearch;
    private EditText inputsearch,pickupdate;
    private int pYear;
    private int pMonth;
    private int pDay;
    String entryDate,output;
    public Spinner area_ref;
    String selectedArea_ref;
    String area_id;
    String df_bill_id;
    String cust_id;
    String RemaingAmount;
    Area_list areaModel;
    Area_list mSelected;
    String month_year;
    private List<Area_list> area_lists;
    ArrayList<Area_list> spinArrayList = new ArrayList<Area_list>();
    public AreaAdapter areaadapter;
    private PopupWindow pw;
    TextView buffalo,cow,sal_month,sal,tillnow_buff,tillnow_cow;
    String remaining_milk_buffalo;
    String remaining_milk_cow;
    LinearLayout mLinearLayout;
    LinearLayout mLinearLayoutHeader;
    //private static final String LIST_FRAGMENT_TAG = "list_fragment";
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    ImageView arrow;
    BottomSheetBehavior sheetBehavior;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        inputsearch=(EditText)findViewById(R.id.inputsearchid);
        pickupdate=(EditText)findViewById(R.id.date);
        Typeface font = Typeface.createFromAsset(getAssets(), "akshar.ttf");
        inputsearch.setTypeface(font);
        //inputsearch.setText("तुलसा");
        Button filter=(Button)findViewById(R.id.filter);
        //MobileAds.initialize(this, "ca-app-pub-5338690682332092~2628421550");
          token = SharedPrefManager.getInstance(this).getDeviceToken();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView lifting_rem_milk = (TextView) findViewById(R.id.lifting_rem_milk);
        buffalo=(TextView)findViewById(R.id.diffbuffalo);
        cow=(TextView)findViewById(R.id.diffcow);
       sal_month=(TextView)findViewById(R.id.saltext);
       sal=(TextView)findViewById(R.id.sal);
       tillnow_buff=(TextView)findViewById(R.id.buff);
       tillnow_cow=(TextView)findViewById(R.id.cow);
       ImageButton bottomrefresh=(ImageButton)findViewById(R.id.bottom_refresh);
       bottomrefresh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               show_sal_dispatch();
               remaingbuffalomilk();
               remaingcowmilk();
           }
       });

        arrow=(ImageView)findViewById(R.id.uparrow);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        layoutBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBottomSheet();
            }
        });



        final Calendar myCalendar = Calendar.getInstance();
        pYear = myCalendar.get(Calendar.YEAR);
        pMonth = myCalendar.get(Calendar.MONTH);
        pDay = myCalendar.get(Calendar.DAY_OF_MONTH);



      /* final DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
                Log.e("onDateSet()", "arg0 = [" + arg0 + "], year = [" + year + "], monthOfYear = [" + monthOfYear + "], dayOfMonth = [" + dayOfMonth + "]");
            }
        };
*/

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat datemonth = new SimpleDateFormat("yyyy-MM");
        month_year=String.valueOf(datemonth.format(c.getTime()));
       // Toast.makeText(MainActivity.this,month_year,Toast.LENGTH_LONG).show();
        //pickupdate.setText(datemonth.format(c.getTime()));

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
       // Toast.makeText(MainActivity.this,pickupdate.getText().toString(),Toast.LENGTH_SHORT).show();

       /* String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

// editText is the EditText  that should display it
        pickupdate.setText(currentDateTimeString);*/
      final  DatePickerDialog dpDialog = new DatePickerDialog(this, date, pYear, pMonth, pDay);
        dpDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
        //dpDialog.show();
        pickupdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
              /*  new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
                dpDialog.show();
            }
        });

        pickupdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (MainActivity.this.adapter == null){
                    // some print statement saying it is null
                    custArrayList.clear();
                    Toast toast = Toast.makeText(MainActivity.this,"no record found", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
                else
                {
                    MainActivity.this.adapter.filter(String.valueOf(arg0));

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
                        int size=  custfilterArrayList.size();
                        int custsize=  custArrayList.size();
                        if( custsize==0){
                            custArrayList.clear();
                            custfilterArrayList.clear();
                            //ReturnFilesArrayList.clear();
                           // Toast.makeText(MainActivity.this,"No records found"+size,Toast.LENGTH_SHORT).show();
                        }else {
                            custArrayList.clear();
                            custfilterArrayList.clear();
                            adapter.notifyDataSetChanged();
                        }
                        remaingbuffalomilk();
                        remaingcowmilk();
                        //Toast.makeText(MainActivity.this,area_id,Toast.LENGTH_LONG).show();
                       // MainActivity.this.areaadapter.updateList(selectedArea_ref);


                        //Toast.makeText(MainActivity.this,mSelected.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ReturnFilesFilterArrayList.clear();
                FilterClass();

                int size=  custfilterArrayList.size();
                int custsize=  custArrayList.size();
                if( custsize==0){
                    //custArrayList.clear();
                    //custfilterArrayList.clear();
                    //ReturnFilesArrayList.clear();
                    Toast.makeText(MainActivity.this,"No records found"+size,Toast.LENGTH_SHORT).show();
                }else {
                   // custArrayList.clear();
                   // custfilterArrayList.clear();
                    adapter.notifyDataSetChanged();
                }
                  // ReturnfilesListView.setAdapter(adapter);
            }
        });

        inputsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (MainActivity.this.adapter == null){
                    // some print statement saying it is null
                    Toast toast = Toast.makeText(MainActivity.this,"no record found", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    MainActivity.this.adapter.filter(String.valueOf(arg0));

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
        show_sal_dispatch();
        registerDeviceClass();
       // getmonthlybillClass();
       // enquiryClass();
    }
    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            arrow.setImageDrawable(getResources().getDrawable(R.mipmap.uparrow));
            //btnBottomSheet.setText("Close sheet");
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            // btnBottomSheet.setText("Expand sheet");
            arrow.setImageDrawable(getResources().getDrawable(R.mipmap.downarrow));
        }
    }

   /* private void initiatePopupWindow(View v) {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.layout_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout,  700,
                   300, true);

            // display the popup in the center
            pw.showAtLocation(v,Gravity.BOTTOM, 0, 0);
           // pw.update(0,10, 400,ViewGroup.MarginLayoutParams.WRAP_CONTENT);
            //pw.setElevation(5.0f);
            TextView mResultText = (TextView) layout.findViewById(R.id.server_status_text);
            TextView diffbuffalo = (TextView) layout.findViewById(R.id.diffbuffalo);
            diffbuffalo.setText("म्हैस "+remaining_milk_buffalo+" लि");
            TextView diffcow = (TextView) layout.findViewById(R.id.diffcow);
            diffcow.setText("गाय "+remaining_milk_cow+" लि");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        } else {

            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit Application?");
            alertDialogBuilder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    moveTaskToBack(true);
                    //android.os.Process.killProcess(android.os.Process.myPid());
                    //System.exit(1);
                }
            })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            //  super.onBackPressed();
        }


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_navigation, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        int id = item.getItemId();
        if(id ==R.id.myprofile){
            Intent intent=new Intent(this,MyProfileActivity.class);
            startActivity(intent);
            // search action
            return true;
        }else if(id == R.id.refresh){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_clip){
            Intent intent=new Intent(this,EntryUpdateActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.logout){
            Intent intent=new Intent(this,LogoutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    private void updateDisplay() {
        pickupdate.setText(output);
        entryDate=pickupdate.getText().toString();

    }
    private void customerClass() {
        MainActivity.CustTrackClass ru = new MainActivity.CustTrackClass();
        ru.execute("5");
    }
    public void  showAreaRef_idClass() {
        MainActivity.AreaTrackClass ru = new MainActivity.AreaTrackClass();
        ru.execute("5");
    }
    /*private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();
    }

    *//**
     * Dismiss Progress Dialog.
     *//*
    private void dismissProgressDialog() {
        Log.v(TAG, String.format("dismissProgressDialog"));

        pd.cancel();


    }*/



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
            CustometrDetails.put("month", month_year);
            Log.v(TAG, String.format("doInBackground :: month_year= %s", month_year));
            CustometrDetails.put("user_id", SharedPrefereneceUtil.getUserId(MainActivity.this));
            Log.v(TAG, String.format("doInBackground :: Userid= %s", SharedPrefereneceUtil.getUserId(MainActivity.this)));
            CustometrDetails.put("action", "show_cust_list_by_area");
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
                            custList = new Customer_list();
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
                                String remaing_amount = jsonObj.getString("remaing_amount");
                                String category = jsonObj.getString("df_product_name");
                                String prod_cat_id = jsonObj.getString("df_cust_product_cat_ref_id");
                                String prod_rate = jsonObj.getString("df_product_rate");

                                String area_ref_id = jsonObj.getString("df_cust_area_ref_id");
                                //df_bill_id = jsonObj.getString("df_bill_id");
                                String quantity = qty;

                                JSONArray jsonArrayResult1 = jsonObj.getJSONArray("leave_info");

                                if (jsonArrayResult1 != null && jsonArrayResult1.length() > 0) {
                                    for (int j = 0; j < jsonArrayResult1.length(); j++) {
                                        Log.v(TAG, "JsonResponseOpeartion ::");
                                        JSONObject jsonObj1 = jsonArrayResult1.getJSONObject(j);
                                        if (jsonObj1 != null) {
                                            String msgtext = jsonObj1.getString("msgtext");
                                            String df_loc_leave_extra = jsonObj1.getString("df_loc_leave_extra");
                                            String df_loc_extra_qty = jsonObj1.getString("df_loc_extra_qty");
                                           // custList.setDf_loc_customer_ref(df_loc_customer_ref);
                                            custList.setDf_loc_leave_extra(df_loc_leave_extra);
                                            custList.setMsgtext(msgtext);
                                           custList.setDf_loc_extra_qty(df_loc_extra_qty);
                                        }

                                    }
                                }else{
                                    custList.setDf_loc_customer_ref("0");
                                    custList.setDf_loc_leave_extra("null");
                                    custList.setDf_loc_extra_qty("0");
                                }
                                    //int rm_amount=Integer.parseInt(df_bill_ttl_price)-Integer.parseInt(df_bill_deposited_price);
                                    String custname="ग्राहकाचे नाव: "+name;
                                    custList.setCustname(custname);
                                   // String phone="मोबाईल नंबर: "+phn_no;
                                    custList.setPhono(phn_no);
                                String Add="पत्ता: "+add;
                                    custList.setAdd(Add);
                                    custList.setQuanity(quantity);
                                    custList.setCategory(category);
                                    custList.setCust_id(cust_id);
                                    // String entrydate=pickupdate.getText().toString();
                                    custList.setPickupdate(entryDate);
                                    //custList.setBill_id(df_bill_id);
                                    custList.setProd_cat_id(prod_cat_id);
                                    custList.setProd_rate(prod_rate);
                                    custList.setArea_ref_id(mSelected.getArea_id());

                                    // RemaingAmount=String.valueOf(rm_amount);
                                    // pending_amount.setText("Pending Amount: "+RemaingAmount);
                                    //.setText(df_bill_deposited_price);"थकीत बाकी ० रु. "
                                String rm="थकीत बाकी "+remaing_amount+" रु. ";
                                    custList.setRemaining_amount(rm);
                                    //Toast.makeText(MainActivity.this,entryDate,Toast.LENGTH_LONG).show();
                                    custArrayList.add(custList);
                                    adapter = new CustomerAdapter(this, custArrayList);

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
    private void  FilterClass() {
        MainActivity.ReturnFilesFilterClass ru = new MainActivity.ReturnFilesFilterClass();
        ru.execute("5");
    }

    class ReturnFilesFilterClass extends AsyncTask<String, Void, String> {

        ServerClass ruc = new ServerClass();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(TAG, "onPreExecute");
           // showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response1) {
            super.onPostExecute(response1);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response1));
            //dismissProgressDialog();
            // Toast.makeText(ReturnFiles.this, response1, Toast.LENGTH_LONG).show();
            CustomerFilterDetails(response1);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            //HashMap<String, String> ReturnFilesDetails = new HashMap<String, String>();
            HashMap<String, String> CustomerFilterDetails = new HashMap<>();
            CustomerFilterDetails.put("area_id",mSelected.getArea_id());
            CustomerFilterDetails.put("todaysdate", entryDate);
            CustomerFilterDetails.put("month", month_year);
            CustomerFilterDetails.put("user_id", SharedPrefereneceUtil.getUserId(MainActivity.this));
            Log.v(TAG, String.format("doInBackground :: Area_id= %s", mSelected.getArea_id()));
            //ReturnFilesDetails.put("return_type",selectedtax);
            CustomerFilterDetails.put(ServerClass.ACTION,"show_cust_list_by_area");
            String loginResult1 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, CustomerFilterDetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult1));
            return loginResult1;

        }


    }


    private void CustomerFilterDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
//        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayoutPrabhagDetails);
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);

                if (object != null) {
                    JSONArray jsonArrayResult = object.getJSONArray("result");

                    if (jsonArrayResult != null && jsonArrayResult.length() > 0)
                        for (int i = 0; i < jsonArrayResult.length(); i++) {
                            custList = new Customer_list();
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
                                String remaing_amount = jsonObj.getString("remaing_amount");
                                String category = jsonObj.getString("df_product_name");
                                String prod_cat_id = jsonObj.getString("df_cust_product_cat_ref_id");
                                String prod_rate = jsonObj.getString("df_product_rate");

                                String area_ref_id = jsonObj.getString("df_cust_area_ref_id");
                                //df_bill_id = jsonObj.getString("df_bill_id");
                                String quantity = qty;

                                JSONArray jsonArrayResult1 = jsonObj.getJSONArray("leave_info");

                                if (jsonArrayResult1 != null && jsonArrayResult1.length() > 0) {
                                    for (int j = 0; j < jsonArrayResult1.length(); j++) {
                                        Log.v(TAG, "JsonResponseOpeartion ::");
                                        JSONObject jsonObj1 = jsonArrayResult1.getJSONObject(j);
                                        if (jsonObj1 != null) {
                                            String msgtext = jsonObj1.getString("msgtext");
                                            String df_loc_leave_extra = jsonObj1.getString("df_loc_leave_extra");
                                            String df_loc_extra_qty = jsonObj1.getString("df_loc_extra_qty");
                                            // custList.setDf_loc_customer_ref(df_loc_customer_ref);
                                            custList.setDf_loc_leave_extra(df_loc_leave_extra);
                                            custList.setMsgtext(msgtext);
                                            custList.setDf_loc_extra_qty(df_loc_extra_qty);
                                        }

                                    }
                                }else{
                                    custList.setDf_loc_customer_ref("0");
                                    custList.setDf_loc_leave_extra("null");
                                    custList.setDf_loc_extra_qty("0");
                                }
                                //int rm_amount=Integer.parseInt(df_bill_ttl_price)-Integer.parseInt(df_bill_deposited_price);
                                String custname="ग्राहकाचे नाव: "+name;
                                custList.setCustname(custname);
                                // String phone="मोबाईल नंबर: "+phn_no;
                                custList.setPhono(phn_no);
                                String Add="पत्ता: "+add;
                                custList.setAdd(Add);
                                custList.setQuanity(quantity);
                                custList.setCategory(category);
                                custList.setCust_id(cust_id);
                                // String entrydate=pickupdate.getText().toString();
                                custList.setPickupdate(entryDate);
                                //custList.setBill_id(df_bill_id);
                                custList.setProd_cat_id(prod_cat_id);
                                custList.setProd_rate(prod_rate);
                                custList.setArea_ref_id(mSelected.getArea_id());

                                // RemaingAmount=String.valueOf(rm_amount);
                                // pending_amount.setText("Pending Amount: "+RemaingAmount);
                                //.setText(df_bill_deposited_price);"थकीत बाकी ० रु. "
                                String rm="थकीत बाकी "+remaing_amount+" रु. ";
                                custList.setRemaining_amount(rm);
                                //Toast.makeText(MainActivity.this,entryDate,Toast.LENGTH_LONG).show();
                                custArrayList.add(custList);
                                adapter = new CustomerAdapter(this, custArrayList);

                                recyclerView.setAdapter(adapter);
//                                Log.v("party", party);



                            }
                        }
                }else {
                    Toast.makeText(MainActivity.this, "No records ", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
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
            AreaDetails.put("user_id", SharedPrefereneceUtil.getUserId(MainActivity.this));
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

                                areaadapter = new AreaAdapter(MainActivity.this, spinArrayList);

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
    private void remaingbuffalomilk() {
        MainActivity.BuffMilkTrackClass ru = new MainActivity.BuffMilkTrackClass();
        ru.execute("5");
    }
    class BuffMilkTrackClass extends AsyncTask<String, Void, String> {

        ServerClass ruc = new ServerClass();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(TAG, "onPreExecute");
           // showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response1) {
            super.onPostExecute(response1);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response1));
            //dismissProgressDialog();
            // Toast.makeText(ReturnFiles.this, response1, Toast.LENGTH_LONG).show();
            BuffMilkDetails(response1);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            //HashMap<String, String> ReturnFilesDetails = new HashMap<String, String>();
            HashMap<String, String> BuffMilkDetails = new HashMap<>();
            BuffMilkDetails.put("area_id",  area_id);
            BuffMilkDetails.put("pro_id", "1");
            BuffMilkDetails.put("today", entryDate);
            Log.v(TAG, String.format("doInBackground :: Area_id= %s",area_id));
            //ReturnFilesDetails.put("return_type",selectedtax);
            BuffMilkDetails.put(ServerClass.ACTION,"show_remainig_milk_list_by_qty");
            String loginResult1 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, BuffMilkDetails);
            Log.v(TAG, String.format("doInBackground :: remainigMilkBuff= %s", loginResult1));
            return loginResult1;

        }


    }


    private void BuffMilkDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
//        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayoutPrabhagDetails);
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);

                if (object != null) {
                    JSONArray jsonArrayResult = object.getJSONArray("result");

                    if (jsonArrayResult != null && jsonArrayResult.length() > 0)
                        for (int i = 0; i < jsonArrayResult.length(); i++) {
                            custList = new Customer_list();
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                remaining_milk_buffalo     = jsonObj.getString("remaining_milk");
                                buffalo.setText("म्हैस "+remaining_milk_buffalo+" लि ");
                            }
                        }
                }else {
                    Toast.makeText(MainActivity.this, "No records ", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }
    }
    private void remaingcowmilk() {
        MainActivity.CowMilkTrackClass ru = new MainActivity.CowMilkTrackClass();
        ru.execute("5");
    }
    class CowMilkTrackClass extends AsyncTask<String, Void, String> {

        ServerClass ruc = new ServerClass();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(TAG, "onPreExecute");
           // showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response1) {
            super.onPostExecute(response1);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response1));
            //dismissProgressDialog();
            // Toast.makeText(ReturnFiles.this, response1, Toast.LENGTH_LONG).show();
            CowMilkDetails(response1);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            //HashMap<String, String> ReturnFilesDetails = new HashMap<String, String>();
            HashMap<String, String> CowMilkDetails = new HashMap<>();
            CowMilkDetails.put("area_id",area_id);
            CowMilkDetails.put("pro_id", "2");
            CowMilkDetails.put("today", entryDate);
            Log.v(TAG, String.format("doInBackground :: Area_id= %s",area_id));
            //ReturnFilesDetails.put("return_type",selectedtax);
            CowMilkDetails.put(ServerClass.ACTION,"show_remainig_milk_list_by_qty");
            String loginResult1 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, CowMilkDetails);
            Log.v(TAG, String.format("doInBackground :: remainigMilkcow= %s", loginResult1));
            return loginResult1;

        }


    }


    private void CowMilkDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
//        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayoutPrabhagDetails);
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);

                if (object != null) {
                    JSONArray jsonArrayResult = object.getJSONArray("result");

                    if (jsonArrayResult != null && jsonArrayResult.length() > 0)

                        for (int i = 0; i < jsonArrayResult.length(); i++) {
                            custList = new Customer_list();
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                remaining_milk_cow     = jsonObj.getString("remaining_milk");
                                cow.setText("गाय "+remaining_milk_cow+" लि ");

                            }
                        }

                }else {
                    Toast.makeText(MainActivity.this, "No records ", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }
    }
    private void show_sal_dispatch() {
        MainActivity.SalaryDispatchTrackClass ru = new MainActivity.SalaryDispatchTrackClass();
        ru.execute("5");
    }
    class SalaryDispatchTrackClass extends AsyncTask<String, Void, String> {

        ServerClass ruc = new ServerClass();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(TAG, "onPreExecute");
            // showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response1) {
            super.onPostExecute(response1);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response1));
            //dismissProgressDialog();
            // Toast.makeText(ReturnFiles.this, response1, Toast.LENGTH_LONG).show();
            SalaryDispatchDetails(response1);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            //HashMap<String, String> ReturnFilesDetails = new HashMap<String, String>();
            HashMap<String, String> SalaryDispatchDetails = new HashMap<>();
            SalaryDispatchDetails.put("dispatcher_id",SharedPrefereneceUtil.getUserId(MainActivity.this));
            Log.v(TAG, String.format("doInBackground :: Dispatcher_id= %s",SharedPrefereneceUtil.getUserId(MainActivity.this)));
            SalaryDispatchDetails.put("month", month_year);
            Log.v(TAG, String.format("doInBackground :: Month= %s",month_year));
            //ReturnFilesDetails.put("return_type",selectedtax);
            SalaryDispatchDetails.put(ServerClass.ACTION,"show_sal_and_dispatch_by_id");

            String loginResult1 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, SalaryDispatchDetails);
            Log.v(TAG, String.format("doInBackground :: salDispatchResult= %s", loginResult1));
            return loginResult1;

        }
    }
    private void SalaryDispatchDetails(String jsonResponse) {
        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
//        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayoutPrabhagDetails);
        if (jsonResponse != null) {
            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);
                if (object != null) {
                    JSONArray jsonArrayResult = object.getJSONArray("result");
                    if (jsonArrayResult != null && jsonArrayResult.length() > 0)
                        for (int i = 0; i < jsonArrayResult.length(); i++) {
                          //  custList = new Customer_list();
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                               String ttl_sal   = object.getString("ttl_sal");
                               String ttl_dispatch_buff   = object.getString("ttl_dispatch_till_now_buff");
                               String ttl_dispatch_cow   = object.getString("ttl_dispatch_till_now_cow");
                                sal.setText(ttl_sal +" रु.");
                                tillnow_buff.setText("म्हैस "+ttl_dispatch_buff+" लि");
                                tillnow_cow.setText("गाय "+ttl_dispatch_cow+" लि");

                            }
                        }
                }else {
                    Toast.makeText(MainActivity.this, "No records ", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }
    }
    public void  registerDeviceClass() {
        MainActivity.registerDeviceTrackClass ru = new MainActivity.registerDeviceTrackClass();
        ru.execute("5");
    }


    class registerDeviceTrackClass extends AsyncTask<String, Void, String> {

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
            // Toast.makeText(NavigationDrawer.this, response, Toast.LENGTH_LONG).show();
            //registerDeviceDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> registerDeviceDetails = new HashMap<String, String>();
            registerDeviceDetails.put("token",token);
            Log.v(TAG, String.format("doInBackground :: Token= %s", token));
            registerDeviceDetails.put("email",SharedPrefereneceUtil.getEmail(MainActivity.this));
            Log.v(TAG, String.format("doInBackground :: Email= %s", SharedPrefereneceUtil.getEmail(MainActivity.this)));
            registerDeviceDetails.put("action", "register_device_token");
            String loginResult2 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, registerDeviceDetails);
            Log.v(TAG, String.format("doInBackground :: Register Device= %s", loginResult2));
            return loginResult2;
        }
    }

}
