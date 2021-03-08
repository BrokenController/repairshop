package com.repairshop.dao;

import com.repairshop.entity.RepairShop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RepairShopDao {
    public boolean update(Connection con, RepairShop repairShop)throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "update repair_shop set rs_name = ?, rs_manager = ? where rs_id = ?");

        ps.setString(1,repairShop.getName());
        ps.setString(2,repairShop.getManagerName());
        ps.setLong(3,repairShop.getRepairShopId());

        return ps.executeUpdate() == 1;
    }

    public boolean insert(Connection con,RepairShop repairShop)throws SQLException{
        PreparedStatement ps = con.prepareStatement(
                "insert into repair_shop values(?,?,?)"
        );

        ps.setLong(1,repairShop.getRepairShopId());
        ps.setString(2,repairShop.getName());
        ps.setString(3,repairShop.getManagerName());

        return ps.executeUpdate() == 1;
    }

    public void upsert(Connection con,RepairShop repairShop)throws SQLException{
        if(!update(con,repairShop)){
            insert(con,repairShop);
        }
    }
}
