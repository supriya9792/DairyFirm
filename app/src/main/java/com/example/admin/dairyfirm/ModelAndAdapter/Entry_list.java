package com.example.admin.dairyfirm.ModelAndAdapter;

/**
 * Created by admin on 2/20/2018.
 */

public class Entry_list {

    private  String cust_id;
    private   String custname;
    private  String phono;
    private String add;
    private String quanity;
    private String pickupdate;
    private  String prod_cat_id;
    private String Prod_rate;
    private String area_ref_id;
    private String entryId;

    public Entry_list(){};

    public Entry_list( String custid,String custname,String phone,String add,String qty,
                          String pickupdate,  String prod_cat_id,  String prod_rate, String area_ref_id,String entryId){
        this.cust_id=custid;
        this.custname=custname;
        this.phono=phone;
        this.add=add;
        this.quanity=qty;
        this.pickupdate=pickupdate;
        this.prod_cat_id=prod_cat_id;
        this.Prod_rate=prod_rate;
        this.area_ref_id=area_ref_id;
        this.entryId=entryId;

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

    public String getPickupdate() {
        return pickupdate;
    }

    public void setPickupdate(String pickupdate) {
        this.pickupdate = pickupdate;
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

    public String getArea_ref_id() {
        return area_ref_id;
    }

    public void setArea_ref_id(String area_ref_id) {
        this.area_ref_id = area_ref_id;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }
}

