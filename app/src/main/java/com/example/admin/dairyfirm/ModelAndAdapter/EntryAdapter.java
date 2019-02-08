package com.example.admin.dairyfirm.ModelAndAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dairyfirm.Activity.CustomerLeaveActivity;
import com.example.admin.dairyfirm.Activity.EntryUpdateActivity;
import com.example.admin.dairyfirm.Activity.MainActivity;
import com.example.admin.dairyfirm.R;
import com.example.admin.dairyfirm.Utility.ServerClass;
import com.example.admin.dairyfirm.Utility.ServiceUrls;
import com.example.admin.dairyfirm.Utility.SharedPrefereneceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 2/20/2018.
 */

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.MyViewHolder> {
    private Context mContext;
    Entry_list entryList;
    private List<Entry_list> etyList;
    private ArrayList<Entry_list> arraylist;
    String Cust_id,Quantity;
    String test,area_ref_id;
    int mili250,mili500,lt;
    private ProgressDialog pd;
    String Pickupdate,entry_id,output,entryDate,Prod_cat_id,prod_rate,prod_price,entry_action,phonenumber;
    private int pYear;
    private int pMonth;
    private int pDay;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView custname,mob_no,address,quantity,msgtext,pending_amount,todays_date,todays_Qty;
        public ImageButton fab,pay;
        public ImageView milk_cat;
        public Button save,edit;
        Spinner milkcategory;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            custname = (TextView) view.findViewById(R.id.tv_custName);
            mob_no = (TextView) view.findViewById(R.id.tv_phnno);
            address=(TextView)view.findViewById(R.id.tv_add);
            quantity = (TextView) view.findViewById(R.id.tv_qty);
            edit = (Button) view.findViewById(R.id.edit);
            todays_date=(TextView)view.findViewById(R.id.date);
            milk_cat=(ImageView)view.findViewById(R.id.milk_catimage);
            save=(Button)view.findViewById(R.id.save);
            Typeface font = Typeface.createFromAsset(mContext.getAssets(), "akshar.ttf");
            // custname.setTypeface(font);
            custname.setTypeface(font,Typeface.BOLD);
            //Typeface font = Typeface.createFromAsset(mContext.getAssets(), "mangal.ttf");
            address.setTypeface(font);

        }
    }
    public EntryAdapter(Context mContext, ArrayList<Entry_list> albumList) {
        this.mContext = mContext;
        this.etyList = albumList;
        this.arraylist = new ArrayList<Entry_list>();
        this.arraylist.addAll(albumList);

    }
    @Override
    public int getItemCount() {
        return etyList.size();
    }
    public Entry_list getItem(int position) {
        return etyList.get(position);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_update_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final EntryAdapter.MyViewHolder holder, final int position) {
        final Entry_list enq = etyList.get(position);
        holder.custname.setText(enq.getCustname());
        holder.mob_no.setText(enq.getPhono());
        holder.address.setText(enq.getAdd());
        holder.quantity.setText(enq.getQuanity());
        final String custid=enq.getCust_id();
        Cust_id=custid;
        final String prod_cat_id = enq.getProd_cat_id();
        final String pickupdate=enq.getPickupdate();
        if(prod_cat_id.equals("1")){
            holder.milk_cat.setImageDrawable(mContext.getDrawable(R.drawable.buffallo));
        }else if(prod_cat_id.equals("2")){
            holder.milk_cat.setImageDrawable(mContext.getDrawable(R.drawable.zoo));
        }
        final String area_id=enq.getArea_ref_id();
       // Quantity=enq.getQuanity();
        entryDate=enq.getPickupdate();
        String sDate1=entryDate;
        try {
            Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);
            DateFormat dateFormat = new SimpleDateFormat("dd-MMM");
            String strDate = dateFormat.format(date1);
            holder.todays_date.setText(strDate);
            holder.todays_date.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlue));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        prod_rate=enq.getProd_rate();

        if(holder.mob_no.getText().toString().equals("")||holder.mob_no.getText().toString().equals("0")){
            holder.mob_no.setText("फोन नंबर अद्ययावत करा");
            holder.mob_no.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
            holder.mob_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(mContext,holder.mob_no.getText().toString(),Toast.LENGTH_SHORT).show();
                    Cust_id=custid;
                    //  df_bill_id=bill_id;
                    final EditText phone = new EditText(v.getContext());
                    phone.setInputType(InputType.TYPE_CLASS_NUMBER);
                    phone.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
                    phone.setBackground(mContext.getResources().getDrawable(R.drawable.quantitybackground));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    // editText.setHint("dd.MM.yyyy");
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                    // Setting Dialog Title
                    alertDialog.setTitle("फोन नंबर प्रविष्ट करा");
                    alertDialog.setView(phone);

                    alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            //FollowupNextDateClass();

                            phonenumber=phone.getText().toString();
                            Log.v(TAG, String.format("doInBackground ::  phonenumber= %s", phonenumber));
                            // Smv_id=smv_id;
                            Log.v(TAG, String.format("doInBackground ::  Cust_id= %s", Cust_id));
                            updatephone_no();
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
        }else{

            holder.mob_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:0123456789"));
                    mContext.startActivity(intent);
                }
            });
        }

        Cust_id=custid;

        final Calendar myCalendar = Calendar.getInstance();
        pYear = myCalendar.get(Calendar.YEAR);
        pMonth = myCalendar.get(Calendar.MONTH);
        pDay = myCalendar.get(Calendar.DAY_OF_MONTH);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View convertView = li.inflate(R.layout.bag_popup, null, false);
                final String month[] = {"000","250","500","750"};
                final NumberPicker numberpicker = (NumberPicker)convertView.findViewById(R.id.numberPicker1);
                final NumberPicker numberPicker1 = (NumberPicker)convertView.findViewById(R.id.numberPicker2);
                final NumberPicker numberPicker3 = (NumberPicker)convertView.findViewById(R.id.numberPicker3);
                final EditText QtyTotal=(EditText)convertView.findViewById(R.id.total);


                numberpicker.setMinValue(0);
                numberpicker.setMaxValue(15);
                numberpicker.setValue(1);
                numberPicker1.setMinValue(00);
                numberPicker1.setMaxValue(10);
                numberPicker1.setValue(1);
                numberPicker3.setMinValue(00);
                numberPicker3.setMaxValue(10);
                numberPicker3.setValue(1);
                numberpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        lt= newVal;
                        mili250= numberPicker1.getValue();
                        mili500= numberPicker3.getValue();
                        Log.v(TAG, String.format("doInBackground ::  lt= %s", lt));
                        int Liter=lt*1000;
                        Log.v(TAG, String.format("doInBackground ::  Liter= %s", Liter));
                        int MILI250=mili250*250;
                        Log.v(TAG, String.format("doInBackground ::  Mili250= %s", MILI250));
                        int Mili500=mili500*500;
                        Log.v(TAG, String.format("doInBackground ::  mili500= %s", Mili500));
                        Float total= Float.valueOf(((Liter+MILI250+Mili500)));
                        Log.v(TAG, String.format("doInBackground ::  Total= %s", total));
                        Float militolt=total/1000;
                        Log.v(TAG, String.format("doInBackground ::  militolt= %s", militolt));
                        String Total=String.valueOf(militolt);
                        test = String.format("%.03f", militolt);
                        Log.v(TAG, String.format("doInBackground ::  QtyTotal= %s", Total));
                        QtyTotal.setText(test);
                    }
                });
                numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        mili250= newVal;
                        Log.v(TAG, String.format("doInBackground ::  mili250= %s", mili250));
                        int Liter=lt*1000;
                        Log.v(TAG, String.format("doInBackground ::  Liter= %s", Liter));
                        int MILI250=mili250*250;
                        Log.v(TAG, String.format("doInBackground ::  Mili250= %s", MILI250));
                        int Mili500=mili500*500;
                        Log.v(TAG, String.format("doInBackground ::  mili500= %s", Mili500));
                        Float total= Float.valueOf(((Liter+MILI250+Mili500)));
                        Log.v(TAG, String.format("doInBackground ::  Total= %s", total));
                        Float militolt=total/1000;
                        Log.v(TAG, String.format("doInBackground ::  militolt= %s", militolt));
                        String Total=String.valueOf(militolt);
                        test = String.format("%.03f", militolt);
                        Log.v(TAG, String.format("doInBackground ::  QtyTotal= %s", Total));
                        QtyTotal.setText(test);
                    }
                });
                numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        mili500=newVal;
                        int Liter=lt*1000;
                        Log.v(TAG, String.format("doInBackground ::  Liter= %s", Liter));
                        int MILI250=mili250*250;
                        Log.v(TAG, String.format("doInBackground ::  Mili250= %s", MILI250));
                        int Mili500=mili500*500;
                        Log.v(TAG, String.format("doInBackground ::  mili500= %s", Mili500));
                        Float total= Float.valueOf(((Liter+MILI250+Mili500)));
                        Log.v(TAG, String.format("doInBackground ::  Total= %s", total));
                        Float militolt=total/1000;
                        Log.v(TAG, String.format("doInBackground ::  militolt= %s", militolt));
                        String Total=String.valueOf(militolt);
                        test = String.format("%.03f", militolt);
                        Log.v(TAG, String.format("doInBackground ::  QtyTotal= %s", Total));
                        QtyTotal.setText(test);
                        Log.v(TAG, String.format("doInBackground ::  mili500= %s", mili500));
                    }
                });
                lt= numberpicker.getValue();
                mili250= numberPicker1.getValue();
                mili500= numberPicker3.getValue();

                Log.v(TAG, String.format("doInBackground ::  lt= %s", lt));
                int Liter=lt*1000;
                Log.v(TAG, String.format("doInBackground ::  Liter= %s", Liter));
                int MILI250=mili250*250;
                Log.v(TAG, String.format("doInBackground ::  Mili250= %s", MILI250));
                int Mili500=mili500*500;
                Log.v(TAG, String.format("doInBackground ::  mili500= %s", Mili500));
                Float total= Float.valueOf(((Liter+MILI250+Mili500)));
                Log.v(TAG, String.format("doInBackground ::  Total= %s", total));
                Float militolt=total/1000;
                Log.v(TAG, String.format("doInBackground ::  militolt= %s", militolt));
                String Total=String.valueOf(militolt);
                test = String.format("%.03f", militolt);
                Log.v(TAG, String.format("doInBackground ::  QtyTotal= %s", Total));
                QtyTotal.setText(test);
                alertDialog.setView(convertView);
                // Setting Icon to Dialog

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        holder.quantity.setText(test);
                        Quantity=holder.quantity.getText().toString();
                        //Toast.makeText(mContext,holder.quantity.getText().toString(),Toast.LENGTH_SHORT).show();
                       // onBindViewHolder(holder,position);
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });
        //Finally building an AlertDialog
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cust_id=custid;
                Pickupdate=pickupdate;
                Prod_cat_id= prod_cat_id;
                entry_id=enq.getEntryId();
                entry_action="save";
                area_ref_id=area_id;
                Quantity=holder.quantity.getText().toString();
                //String leave_or_extra=  enq.getDf_loc_leave_extra();
                // Quantity=holder.quantity.getText().toString();
                Log.v(TAG, String.format("doInBackground :: Quantity= %s", Quantity));
                prod_price= String.valueOf((Float.parseFloat(Quantity)*Float.parseFloat(prod_rate)));

                if(lt==0){
                    Toast.makeText(mContext,"Please Select Bags First",Toast.LENGTH_LONG).show();
                    return;
                }
                SaveEntryClass();
                //etyList.remove(holder.getAdapterPosition());
                //notifyItemRemoved(holder.getAdapterPosition());
                //notifyItemRangeChanged(holder.getAdapterPosition(),etyList.size());
               // notifyDataSetChanged();

            }

        });
    }
    public void SaveEntryClass(){
        new EntryAdapter.SaveEntrystatus().execute("5");
    }

    class SaveEntrystatus extends AsyncTask<String,Void,String> {
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

            NewCustomerDetails(response);

        }
        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> NewCustomerDetails = new HashMap<String, String>();

            NewCustomerDetails.put("quantity",Quantity);
            Log.v(TAG, String.format("doInBackground :: Quantity= %s", Quantity));
            NewCustomerDetails.put("Pickupdate",entryDate);
            Log.v(TAG, String.format("doInBackground :: Pickupdate= %s", entryDate));
            NewCustomerDetails.put("cust_id",Cust_id);
            Log.v(TAG, String.format("doInBackground :: Cust_id= %s", Cust_id));
            NewCustomerDetails.put("entry_id",entry_id);
            Log.v(TAG, String.format("doInBackground :: entry_id= %s", entry_id));
            NewCustomerDetails.put("status","Complete");
            NewCustomerDetails.put("prod_cat_id",Prod_cat_id);
            Log.v(TAG, String.format("doInBackground :: Prod_cat_id= %s", Prod_cat_id));
            NewCustomerDetails.put("prod_rate",prod_rate);
            Log.v(TAG, String.format("doInBackground :: prod_rate= %s", prod_rate));
            NewCustomerDetails.put("prod_price",prod_price);
            Log.v(TAG, String.format("doInBackground :: prod_price= %s", prod_price));
            NewCustomerDetails.put("area_ref_id",area_ref_id);
            Log.v(TAG, String.format("doInBackground :: Area_ref_id= %s", area_ref_id));
            NewCustomerDetails.put("entry_action",entry_action);
            NewCustomerDetails.put("lt", String.valueOf(lt));
            Log.v(TAG, String.format("doInBackground ::  lt= %s", lt));
            NewCustomerDetails.put("mili250", String.valueOf(mili250));
            Log.v(TAG, String.format("doInBackground ::  mili250= %s", mili250));
            NewCustomerDetails.put("mili500", String.valueOf(mili500));
            Log.v(TAG, String.format("doInBackground ::  mili500= %s", mili500));
            NewCustomerDetails.put("dispatcher_id", SharedPrefereneceUtil.getUserId((Activity) mContext));
            Log.v(TAG, String.format("doInBackground ::  User_id= %s",SharedPrefereneceUtil.getUserId((Activity) mContext) ));
            //NewCustomerDetails.put("ardhalit","500ml");
            // NewCustomerDetails.put("liter", "1lit");
            NewCustomerDetails.put("action", "update_entry_by_cust_id");
            String loginResult2 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, NewCustomerDetails);

            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult2));
            return loginResult2;
        }
    }
    private void NewCustomerDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
//        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayoutPrabhagDetails);
        if (jsonResponse != null) {
            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);
                //JSONArray jsonArrayResult = object.getJSONArray("last_insert_id");
               // last_insert_id = object.getString("last_insert_id");
                String success=object.getString("success");
                if (success.equalsIgnoreCase("1"))
                {
                    Intent intent =new Intent(mContext,EntryUpdateActivity.class);
                    mContext.startActivity(intent);
                }/*else{
                    Intent intent =new Intent(mContext,MainActivity.class);
                    mContext.startActivity(intent);
                }*/

            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }
    }

    private void updatephone_no() {
        EntryAdapter.UpdatePhoneNOTrackClass ru = new EntryAdapter.UpdatePhoneNOTrackClass();
        ru.execute("5");
    }
    class UpdatePhoneNOTrackClass extends AsyncTask<String, Void, String> {

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
            //Toast.makeText(Admin_account.this, response, Toast.LENGTH_LONG).show();
            UpdatePhoneNODetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> UpdatePhoneNODetails = new HashMap<String, String>();
            UpdatePhoneNODetails.put("cust_id", Cust_id);
            Log.v(TAG, String.format("doInBackground :: cust_id= %s", Cust_id));
            // UpdatePhoneNODetails.put("req_status", "Pending");
            //Log.v(TAG, String.format("doInBackground :: req_status= %s", req_status));
            UpdatePhoneNODetails.put("phone", phonenumber);
            Log.v(TAG, String.format("doInBackground :: phone= %s", phonenumber));
            UpdatePhoneNODetails.put("action", "update_phone_number");
            // BillDetails.put("action", "get_bill_details_by_cust_id");
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, UpdatePhoneNODetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;
        }


    }


    private void UpdatePhoneNODetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);
                String success=object.getString("success");
                if(success.equals("1")){
                    Toast.makeText(mContext,"Updated Successfully",Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(mContext,EntryUpdateActivity.class);
                    mContext.startActivity(intent);

                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }

    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        etyList.clear();
        if (charText.length() == 0) {
            etyList.addAll(arraylist);
        } else {
            for (Entry_list wp : arraylist) {
                if (wp.getCustname().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    etyList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
