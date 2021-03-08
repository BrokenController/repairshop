package com.repairshop.dao;

import com.repairshop.entity.Vehicle;


import java.sql.*;

public class VehicleDao {
    public void merge(Connection con, Vehicle vehicle)throws SQLException{
        PreparedStatement ps = con.prepareStatement(
                "merge into vehicle v using (select ? veh_old_id, ? veh_cu_id_new, ? veh_cu_id, ? veh_vin, ? veh_reg_date, ? veh_reg_country, ? veh_manufacturer, " +
                        "? veh_type, ? veh_body, ? veh_engine, ? veh_restraint, ? veh_model, ? veh_year, ? veh_plant, ? veh_serial_number  from dual) d " +
                        "ON (v.veh_vin = d.veh_vin) " +
                        "when matched then update SET " +
                            "v.veh_old_id = d.veh_old_id, " +
                            "v.veh_cu_id_new=d.veh_cu_id_new, " +
                            "v.veh_cu_id=d.veh_cu_id, " +
                            "v.veh_reg_date=d.veh_reg_date, " +
                            "v.veh_reg_country=d.veh_reg_country, " +
                            "v.veh_manufacturer=d.veh_manufacturer, " +
                            "v.veh_type=d.veh_type, " +
                            "v.veh_body=d.veh_body, " +
                            "v.veh_engine=d.veh_engine, " +
                            "v.veh_restraint=d.veh_restraint, " +
                            "v.veh_model=d.veh_model, " +
                            "v.veh_year=d.veh_year, " +
                            "v.veh_plant=d.veh_plant, " +
                            "v.veh_serial_number=d.veh_serial_number " +
                        "when not matched then insert values(vehicle_seq.nextval, d.veh_old_id, d.veh_cu_id_new, d.veh_cu_id," +
                        " d.veh_vin, d.veh_reg_date, d.veh_reg_country, d.veh_manufacturer, d.veh_type, d.veh_body, d.veh_engine, d.veh_restraint, d.veh_model, d.veh_year, d.veh_plant, d.veh_serial_number)"
        );

            ps.setLong(1,vehicle.getVehicleOldId());
            ps.setLong(2,vehicle.getCustomer().getCustomerId());
            ps.setLong(3,vehicle.getCustomer().getCustomerOldId());
            ps.setString(4,vehicle.getVin());
            ps.setDate(5,Date.valueOf(vehicle.getRegistrationDate()));
            ps.setString(6,vehicle.getRegistrationCountry());
            ps.setString(7,vehicle.getManufacturer());
            ps.setString(8,vehicle.getVehicleType());
            ps.setString(9,vehicle.getVehicleBody());
            ps.setString(10,vehicle.getVehicleEngine());
            ps.setString(11,vehicle.getVehicleRestraint());
            ps.setString(12,vehicle.getModel());
            ps.setLong(13,vehicle.getYear());
            ps.setString(14,vehicle.getPlant());
            ps.setString(15,vehicle.getSerialNumber());

        ps.executeUpdate();
        ps.close();
    }
    public boolean update(Connection con, Vehicle vehicle)throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "update vehicle set veh_old_id=?,veh_cu_id_new=?,veh_cu_id=?,veh_reg_date=?,veh_reg_country=?," +
                        "veh_manufacturer=?,veh_type=?,veh_body=?,veh_engine=?,veh_restraint=?,veh_model=?,veh_year=?,veh_plant=?,veh_serial_number=? where veh_vin=?");

        ps.setLong(1,vehicle.getVehicleOldId());
        ps.setLong(2,vehicle.getCustomer().getCustomerId());
        ps.setLong(3,vehicle.getCustomer().getCustomerOldId());
        ps.setDate(4,Date.valueOf(vehicle.getRegistrationDate()));
        ps.setString(5,vehicle.getRegistrationCountry());
        ps.setString(6,vehicle.getManufacturer());
        ps.setString(7,vehicle.getVehicleType());
        ps.setString(8,vehicle.getVehicleBody());
        ps.setString(9,vehicle.getVehicleEngine());
        ps.setString(10,vehicle.getVehicleRestraint());
        ps.setString(11,vehicle.getModel());
        ps.setLong(12,vehicle.getYear());
        ps.setString(13,vehicle.getPlant());
        ps.setString(14,vehicle.getSerialNumber());
        ps.setString(15,vehicle.getVin());

        int updateResult = ps.executeUpdate();
        ps.close();

        if(updateResult == 1) {
            return true;
        }else{
            return false;
        }
    }

    public boolean insert(Connection con, Vehicle vehicle)throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "insert into vehicle values (vehicle_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
        );

        ps.setLong(1,vehicle.getVehicleOldId());
        ps.setLong(2,vehicle.getCustomer().getCustomerId());
        ps.setLong(3,vehicle.getCustomer().getCustomerOldId());
        ps.setString(4,vehicle.getVin());
        ps.setDate(5,Date.valueOf(vehicle.getRegistrationDate()));
        ps.setString(6,vehicle.getRegistrationCountry());
        ps.setString(7,vehicle.getManufacturer());
        ps.setString(8,vehicle.getVehicleType());
        ps.setString(9,vehicle.getVehicleBody());
        ps.setString(10,vehicle.getVehicleEngine());
        ps.setString(11,vehicle.getVehicleRestraint());
        ps.setString(12,vehicle.getModel());
        ps.setLong(13,vehicle.getYear());
        ps.setString(14,vehicle.getPlant());
        ps.setString(15,vehicle.getSerialNumber());

        int updateResult = ps.executeUpdate();
        ps.close();

        if(updateResult == 1) {
            return true;
        }else{
            return false;
        }
    }

    public void upsert(Connection con, Vehicle vehicle)throws SQLException{
        if(!this.update(con,vehicle)){
            this.insert(con,vehicle);
        }
    }

    public Long getId(Connection con, Vehicle vehicle)throws SQLException{
        PreparedStatement ps = con.prepareStatement("select veh_id, veh_vin from vehicle where veh_vin = ?");

        ps.setString(1,vehicle.getVin());

        try(ResultSet rs = ps.executeQuery()){
            if(rs.next())vehicle.setVehicleId(rs.getLong("veh_id"));
        }
        ps.close();
        return vehicle.getVehicleId();
    }
}
