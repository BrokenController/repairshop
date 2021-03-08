package com.repairshop.dao;

import com.repairshop.entity.Customer;

import java.sql.*;

public class CustomerDao {

    public void merge(Connection con, Customer customer)throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "merge into customer c using (select ? cu_old_id, ? cu_type, ? cu_origin_date, ? cu_reg_date, ? cu_phone_number, ? cu_name from dual) d " +
                        "ON (c.cu_origin_date = d.cu_origin_date AND c.cu_phone_number = d.cu_phone_number) " +
                        "when matched then update set c.cu_old_id = d.cu_old_id, c.cu_type = d.cu_type, c.cu_reg_date = d.cu_reg_date " +
                        "when not matched then insert values(customer_seq.nextval,d.cu_old_id,d.cu_type,d.cu_origin_date,d.cu_reg_date,d.cu_phone_number,d.cu_name)"
        );

//        merge into repair_detail c using (select ? rd_id, ? rd_type, ? rd_price from dual) d " +
//        "on (c.rd_id = d.rd_id) " +
//                "when matched then update set c.rd_type = d.rd_type, c.rd_price = d.rd_price " +
//                "when not matched then insert (c.rd_id, c.rd_type, c.rd_price) values (d.rd_id,d.rd_type,d.rd_price)"

        ps.setLong(1, customer.getCustomerOldId());
        ps.setString(2, customer.getCustomerType());
        ps.setDate(3, Date.valueOf(customer.getCustomerOriginDate()));
        ps.setDate(4, Date.valueOf(customer.getRegistrationDate()));
        ps.setLong(5, customer.getPhoneNumber());
        ps.setString(6,customer.getCustomerName());

        ps.executeUpdate();
    }

    public boolean update(Connection con, Customer customer)throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "update customer set cu_old_id=?,cu_type=? " +
                        "where cu_name like ? and cu_origin_date = ? and cu_phone_number = ?");

        ps.setLong(1,customer.getCustomerOldId());
        ps.setString(2,customer.getCustomerType());
        ps.setString(3,customer.getCustomerName());
        ps.setDate(4,Date.valueOf(customer.getCustomerOriginDate()));
        ps.setLong(5,customer.getPhoneNumber());

        int updateResult = ps.executeUpdate();
        ps.close();

        if(updateResult == 1) {
            return true;
        }else{
            return false;
        }

    }

    public boolean insert(Connection con, Customer customer)throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "insert into customer values(customer_seq.nextval,?,?,?,?,?,?)"
        );
            ps.setLong(1, customer.getCustomerOldId());
            ps.setString(2, customer.getCustomerType());
            ps.setDate(3, Date.valueOf(customer.getCustomerOriginDate()));
            ps.setDate(4, Date.valueOf(customer.getRegistrationDate()));
            ps.setLong(5, customer.getPhoneNumber());
        ps.setString(6,customer.getCustomerName());

        int updateResult = ps.executeUpdate();
        ps.close();

        if(updateResult == 1) {
            return true;
        }else{
            return false;
        }
    }

    public void upsert(Connection con, Customer customer)throws SQLException{
        if(!this.update(con,customer)){
            this.insert(con,customer);
        }
    }

    public Long getId(Connection con, Customer customer)throws SQLException{
        PreparedStatement ps = con.prepareStatement(
                "select cu_id,cu_name,cu_origin_date,cu_phone_number from customer where cu_name like ? and cu_origin_date = ? and cu_phone_number = ?");

        ps.setString(1,customer.getCustomerName());
        ps.setDate(2,Date.valueOf(customer.getCustomerOriginDate()));
        ps.setLong(3,customer.getPhoneNumber());

        try (ResultSet rs = ps.executeQuery()) {
           if(rs.next()) {
                customer.setCustomerId(rs.getLong("cu_id"));
            }
        }
        ps.close();
        return customer.getCustomerId();
    }
}