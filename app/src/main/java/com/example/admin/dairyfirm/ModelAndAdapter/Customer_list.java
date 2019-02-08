package com.example.admin.dairyfirm.ModelAndAdapter;

/**
 * Created by admin on 1/4/2018.
 */

public class Customer_list {
    String cust_id;
    String custname;
    String phono;
    String add;
    String quanity;
    String category;
    String pickupdate;
    String msgtext;
    String prod_cat_id;
    String Prod_rate;
    String remaining_amount;
    String area_ref_id;
    String df_loc_customer_ref;
    String df_loc_leave_extra;
    String df_loc_extra_qty;
    public Customer_list(){};
    public Customer_list( String custid,String custname,String phone,String add,String qty,String cat,String msg_text,
                          String pickupdate,  String prod_cat_id,  String prod_rate, String remaining_amount,String area_ref_id
                          , String df_loc_customer_ref, String df_loc_leave_extra,String df_loc_extra_qty){
        this.cust_id=custid;
        this.custname=custname;
        this.phono=phone;
        this.add=add;
        this.quanity=qty;
        this.category=cat;
        this.pickupdate=pickupdate;
        this.msgtext=msgtext;
        this.prod_cat_id=prod_cat_id;
        this.Prod_rate=prod_rate;
        this.remaining_amount=remaining_amount;
        this.df_loc_customer_ref=df_loc_customer_ref;
        this.df_loc_leave_extra=df_loc_leave_extra;
        this.df_loc_extra_qty=df_loc_extra_qty;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getPhono() {
        return phono;
    }

    public void setPhono(String phono) {
        this.phono = phono;
    }

    public String getQuanity() {
        return quanity;
    }

    public void setQuanity(String quanity) {
        this.quanity = quanity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPickupdate() {
        return pickupdate;
    }

    public void setPickupdate(String pickupdate) {
        this.pickupdate = pickupdate;
    }

    public String getMsgtext() {
        return msgtext;
    }

    public void setMsgtext(String msgtext) {
        this.msgtext = msgtext;
    }

    public String getProd_cat_id() {
        return prod_cat_id;
    }

    public void setProd_cat_id(String prod_cat_id) {
        this.prod_cat_id = prod_cat_id;
    }

    public String getProd_rate() {
        return Prod_rate;
    }

    public void setProd_rate(String prod_rate) {
        Prod_rate = prod_rate;
    }

    public String getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(String remaining_amount) {
        this.remaining_amount = remaining_amount;
    }

    public String getArea_ref_id() {
        return area_ref_id;
    }

    public void setArea_ref_id(String area_ref_id) {
        this.area_ref_id = area_ref_id;
    }

    public String getDf_loc_customer_ref() {
        return df_loc_customer_ref;
    }

    public void setDf_loc_customer_ref(String df_loc_customer_ref) {
        this.df_loc_customer_ref = df_loc_customer_ref;
    }

    public String getDf_loc_leave_extra() {
        return df_loc_leave_extra;
    }

    public void setDf_loc_leave_extra(String df_loc_leave_extra) {
        this.df_loc_leave_extra = df_loc_leave_extra;
    }

    public String getDf_loc_extra_qty() {
        return df_loc_extra_qty;
    }

    public void setDf_loc_extra_qty(String df_loc_extra_qty) {
        this.df_loc_extra_qty = df_loc_extra_qty;
    }

}
