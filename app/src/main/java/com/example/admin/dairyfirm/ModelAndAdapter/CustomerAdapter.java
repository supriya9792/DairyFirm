package com.example.admin.dairyfirm.ModelAndAdapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.admin.dairyfirm.Activity.BillActivity;
import com.example.admin.dairyfirm.Activity.CustomerLeaveActivity;
import com.example.admin.dairyfirm.Activity.MainActivity;
import com.example.admin.dairyfirm.R;
import com.example.admin.dairyfirm.Utility.ServerClass;
import com.example.admin.dairyfirm.Utility.ServiceUrls;
import com.example.admin.dairyfirm.Utility.SharedPrefereneceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 1/4/2018.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private Context mContext;
    Customer_list custList;
    private List<Customer_list> engList;
    private ArrayList<Customer_list> arraylist;
    String Cust_id,Quantity;
    String last_insert_id;
    String Pickupdate,entry_id,output,entryDate,Prod_cat_id,prod_rate,prod_price,entry_action;
    private int pYear;
    private int pMonth;
    private int pDay;
    String amount,phonenumber;
    String df_bill_id,area_ref_id;
    String RemaingAmount;
    String test;
    int mili250,mili500,lt;
    String df_loc_customer_ref;
    String df_loc_remark;
    String df_loc_leave_extra;
    String df_loc_extra_qty;
    private ProgressDialog pd;
    private LinearLayout mRevealView;
    private boolean hidden = true;
    private ImageButton gallery_btn, photo_btn;
    FrameLayout frameview;
    String year_month;
    private AwesomeValidation awesomeValidation;
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
            fab = (ImageButton) view.findViewById(R.id.fab);
            msgtext=(TextView)view.findViewById(R.id.msgtext);
            todays_date=(TextView)view.findViewById(R.id.date);
            todays_Qty=(TextView)view.findViewById(R.id.today_qty);
           // milkcategory=(Spinner)view.findViewById(R.id.spinner1);
            milk_cat=(ImageView)view.findViewById(R.id.milk_catimage);
            pending_amount=(TextView)view.findViewById(R.id.pending_amount);
            save=(Button)view.findViewById(R.id.save);
            pay=(ImageButton) view.findViewById(R.id.pay);
            linearLayout=(LinearLayout)view.findViewById(R.id.linearlayout);
            Typeface font = Typeface.createFromAsset(mContext.getAssets(), "akshar.ttf");
           // custname.setTypeface(font);
            custname.setTypeface(font,Typeface.BOLD);
            //Typeface font = Typeface.createFromAsset(mContext.getAssets(), "mangal.ttf");
            address.setTypeface(font);
            View itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.attach_menu, null, false);
            mRevealView = (LinearLayout)itemView.findViewById(R.id.reveal_items);
            //mRevealView.setVisibility(View.INVISIBLE);
            awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        }
    }


    public CustomerAdapter(Context mContext, ArrayList<Customer_list> albumList) {
        this.mContext = mContext;
        this.engList = albumList;
        this.arraylist = new ArrayList<Customer_list>();
        this.arraylist.addAll(albumList);

    }
    @Override
    public int getItemCount() {
        return engList.size();
    }
    public Customer_list getItem(int position) {
        return engList.get(position);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cust_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Customer_list enq = engList.get(position);
        holder.custname.setText(enq.getCustname());

        holder.mob_no.setText(enq.getPhono());
        holder.address.setText(enq.getAdd());
        holder.quantity.setText(enq.getQuanity());

       // holder.category.setText(enq.getCategory());
       // holder.pending_amount.setText(enq.getRemaining_amount());
        final String custid=enq.getCust_id();
        Cust_id=custid;


        //getmonthlybillClass() ;

        final String prod_cat_id = enq.getProd_cat_id();

        final String pickupdate=enq.getPickupdate();
       /* String[] milk = { "म्हैस", "गाय"  };
        String[] milk1 = {  "गाय","म्हैस"  };*/
        if(prod_cat_id.equals("1")){
            holder.milk_cat.setImageDrawable(mContext.getDrawable(R.drawable.buffallo));
        }else if(prod_cat_id.equals("2")){
           holder.milk_cat.setImageDrawable(mContext.getDrawable(R.drawable.zoo));
        }

      final String area_id=enq.getArea_ref_id();
        Quantity=enq.getQuanity();
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
        RemaingAmount=enq.getRemaining_amount();
        //Toast.makeText(mContext,RemaingAmount,Toast.LENGTH_LONG).show();
        holder.pending_amount.setText(RemaingAmount);

        if(holder.mob_no.getText().toString().equals("")||holder.mob_no.getText().toString().equals("0")){
            holder.mob_no.setText("फोन नंबर अद्ययावत करा");
            holder.mob_no.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
            holder.mob_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(mContext,holder.mob_no.getText().toString(),Toast.LENGTH_SHORT).show();
                    Cust_id=custid;
                    //  df_bill_id=bill_id;
                    final EditText phon = new EditText(v.getContext());
                    phon.setInputType(InputType.TYPE_CLASS_NUMBER);
                    phon.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
                    phon.setBackground(mContext.getResources().getDrawable(R.drawable.quantitybackground));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    // editText.setHint("dd.MM.yyyy");
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                    // Setting Dialog Title
                    alertDialog.setTitle("फोन नंबर प्रविष्ट करा");
                    // Setting Dialog Message
                    //alertDialog.setMessage(" You want to cancel followup? ");
                    alertDialog.setView(phon);

                    alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            //FollowupNextDateClass();

                            phonenumber=phon.getText().toString();
                            Log.v(TAG, String.format("doInBackground ::  phonenumber= %s", phonenumber));
                            // Smv_id=smv_id;
                            Log.v(TAG, String.format("doInBackground ::  Cust_id= %s", Cust_id));
                            //Log.v(TAG, String.format("doInBackground ::  Status= %s", "Pending"));
                            // FollowupNextDateClass();
                            //adddeposite();
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
            //holder.mob_no.setTextSize(14);
        holder.mob_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                mContext.startActivity(intent);
            }
        });
        }
        //leave_or_extra();
        Cust_id=custid;
        //final String df_loc_customer_ref=enq.getDf_loc_customer_ref();
        final String df_loc_leave_extra=enq.getMsgtext();
        final String df_loc_extra_qty=enq.getDf_loc_extra_qty();
       /* if(df_loc_leave_extra.equals("null")){
            holder.msgtext.setText("");
            //holder.quantity.setText(df_loc_extra_qty);
        }else{*/
            holder.msgtext.setText(enq.getMsgtext());
            holder.quantity.setText(df_loc_extra_qty);
       // }




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
            numberpicker.setValue(0);
            numberPicker1.setMinValue(00);
            numberPicker1.setMaxValue(10);
            numberPicker1.setValue(0);
            numberPicker3.setMinValue(00);
            numberPicker3.setMaxValue(10);
            numberPicker3.setValue(0);
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

                    //String lt= String.valueOf(numberpicker.getValue());
                    //int pos = numberPicker1.getValue();
                    //String mililt= String.valueOf(numberPicker1.getValue());
                    holder.todays_Qty.setText(test);
                   onBindViewHolder(holder,position);
                   // holder.quantity.setText(lt+"."+mililt);
                   // Quantity=holder.todays_Qty.getText().toString();
                   // notifyDataSetChanged();
                    //notifyItemChanged(holder.getAdapterPosition());
                    //Toast.makeText(mContext,value,Toast.LENGTH_LONG).show();
                    // Write your code here to invoke YES event

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
            //Finally building an AlertDialog
     holder.save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Cust_id=custid;
        Pickupdate=pickupdate;
        Prod_cat_id= prod_cat_id;
        entry_id="-1";
        entry_action="save";
        area_ref_id=area_id;
        Quantity=holder.todays_Qty.getText().toString();
      String leave_or_extra=  enq.getDf_loc_leave_extra();
       // Quantity=holder.quantity.getText().toString();
        Log.v(TAG, String.format("doInBackground :: Quantity= %s", Quantity));
        prod_price= String.valueOf((Float.parseFloat(Quantity)*Float.parseFloat(prod_rate)));

        if((holder.quantity.getText().toString().equals("0"))&&(leave_or_extra.equals("Leave"))){
            lt=0;
            mili250=0;
            mili500=0;
            SaveEntryClass();

         final   Snackbar snackbar = Snackbar
                    .make(holder.linearLayout, "Record Saved Successfully", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Cust_id = custid;
                            Pickupdate = pickupdate;
                            entry_id = last_insert_id;
                            entry_action = "delete";
                            Quantity=holder.quantity.getText().toString();
                            Log.v(TAG, String.format("doInBackground :: prod_rate= %s", prod_rate));
                            prod_price = String.valueOf((Float.parseFloat(Quantity) * Float.parseFloat(prod_rate)));
                            SaveEntryClass();
                            // Snackbar snackbar1 = Snackbar.make(holder.linearLayout, "Record not saved!", Snackbar.LENGTH_LONG);
                            //  snackbar1.show();
                       /* engList.add((Customer_list) engList);
                        notifyDataSetChanged();*/
                        }
                    });
            /*snackbar.setAction("dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });*/
            snackbar.show();
            engList.remove(holder.getPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            notifyItemRangeChanged(holder.getAdapterPosition(),engList.size());
            //notifyDataSetChanged();
           // Log.v(TAG, String.format("doInBackground ::custId= %s", Cust_id));
        }
        if(Quantity.equals("0.000")){
            Toast.makeText(mContext,"Please Select Bags First",Toast.LENGTH_LONG).show();
            return;
        }
           SaveEntryClass();

           Snackbar snackbar = Snackbar
                   .make(holder.linearLayout, "Record Saved Successfully", Snackbar.LENGTH_LONG)
                   .setAction("UNDO", new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Cust_id = custid;
                           Pickupdate = pickupdate;
                           entry_id = last_insert_id;
                           entry_action = "delete";
                           Log.v(TAG, String.format("doInBackground :: prod_rate= %s", prod_rate));
                           prod_price = String.valueOf((Float.parseFloat(Quantity) * Float.parseFloat(prod_rate)));
                           SaveEntryClass();
                           // Snackbar snackbar1 = Snackbar.make(holder.linearLayout, "Record not saved!", Snackbar.LENGTH_LONG);
                           //  snackbar1.show();
                       /* engList.add((Customer_list) engList);
                        notifyDataSetChanged();*/
                       }
                   });

           snackbar.show();
           engList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
           notifyItemRangeChanged(holder.getAdapterPosition(),engList.size());
            // notifyItemRangeChanged(getPosition(),mDataSet.size());
           /* Intent intent =new Intent(mContext,MainActivity.class);
            mContext.startActivity(intent);*/
          // notifyDataSetChanged();

    }

   });
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat datemonth = new SimpleDateFormat("yyyy-MM");
        year_month=String.valueOf(datemonth.format(c.getTime()));
     holder.pay.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Cust_id=custid;
           //  df_bill_id=bill_id;
             final EditText editText = new EditText(v.getContext());
             editText.setInputType(InputType.TYPE_CLASS_NUMBER);
             editText.setBackground(mContext.getResources().getDrawable(R.drawable.quantitybackground));
             LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                     LinearLayout.LayoutParams.WRAP_CONTENT,
                     LinearLayout.LayoutParams.WRAP_CONTENT);
             // editText.setHint("dd.MM.yyyy");
             AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
             // Setting Dialog Title
             alertDialog.setTitle("Please Enter Amount");
             // Setting Dialog Message
             //alertDialog.setMessage(" You want to cancel followup? ");
             alertDialog.setView(editText);

             alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog,int which) {
                     //FollowupNextDateClass();

                     amount=editText.getText().toString();

                    adddeposite();
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
     holder.fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Cust_id=custid;
             Intent intent=new Intent(mContext,CustomerLeaveActivity.class);
             intent.putExtra("custname",holder.custname.getText().toString());
             intent.putExtra("cust_id",Cust_id);
             mContext.startActivity(intent);
         }
     });

    }


    public void SaveEntryClass(){
        new SaveEntrystatus().execute("5");
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
            //dismissProgressDialog();
            //Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();
            // Toast.makeText(, response, Toast.LENGTH_LONG).show();
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
             last_insert_id = object.getString("last_insert_id");
                String success=object.getString("success");
                if (success.equalsIgnoreCase("2"))
                {
                    Intent intent =new Intent(mContext,MainActivity.class);
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
           // dismissProgressDialog();
            //Toast.makeText(Admin_account.this, response, Toast.LENGTH_LONG).show();
            AddDespositeDetails(response);
        }
        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> AddDespositeDetails = new HashMap<String, String>();
            AddDespositeDetails.put("cust_id", Cust_id);
            Log.v(TAG, String.format("doInBackground :: cust_id= %s", Cust_id));
            AddDespositeDetails.put("asso_ref_id",SharedPrefereneceUtil.getUserId((Activity) mContext));
            Log.v(TAG, String.format("doInBackground :: Asso_ref_id= %s", SharedPrefereneceUtil.getUserId((Activity) mContext)));
            AddDespositeDetails.put("req_status", "Pending");
            AddDespositeDetails.put("month", year_month);
            Log.v(TAG, String.format("doInBackground :: month= %s", year_month));
            AddDespositeDetails.put("amount", amount);
            Log.v(TAG, String.format("doInBackground :: Amount= %s", amount));
            AddDespositeDetails.put("action", "send_payment_request");
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
                if(success.equals("2")){
                    Toast.makeText(mContext,"Deposited Successfully",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }

    }
    private void updatephone_no() {
        UpdatePhoneNOTrackClass ru = new UpdatePhoneNOTrackClass();
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
                    Intent intent =new Intent(mContext,MainActivity.class);
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
        engList.clear();
        if (charText.length() == 0) {
            engList.addAll(arraylist);
        } else {
            for (Customer_list wp : arraylist) {
                if (wp.getCustname().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    engList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}

