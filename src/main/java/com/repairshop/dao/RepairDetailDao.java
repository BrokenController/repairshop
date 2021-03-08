package com.repairshop.dao;

import com.repairshop.entity.RepairDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RepairDetailDao {
    public void merge(Connection con, RepairDetail repairDetail)throws SQLException{

        PreparedStatement ps = con.prepareStatement(
                "merge into repair_detail c using (select ? rd_id, ? rd_type, ? rd_price from dual) d " +
                        "on (c.rd_id = d.rd_id) " +
                        "when matched then update set c.rd_type = d.rd_type, c.rd_price = d.rd_price " +
                        "when not matched then insert (c.rd_id, c.rd_type, c.rd_price) values (d.rd_id,d.rd_type,d.rd_price)"
        );

        ps.setLong(1,repairDetail.getRepairDetailId());
        ps.setString(2,repairDetail.getRepairType());
        ps.setDouble(3,repairDetail.getPrice());

        ps.executeUpdate();
    }

    public boolean update(Connection con, RepairDetail repairDetail)throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "update repair_detail set rd_type = ?, rd_price = ? where rd_id = ?");

        ps.setString(1,repairDetail.getRepairType());
        ps.setDouble(2,repairDetail.getPrice());
        ps.setLong(3,repairDetail.getRepairDetailId());

        int updateResult = ps.executeUpdate();
        ps.close();

        if(updateResult == 1) {
            return true;
        }else{
            return false;
        }
    }

    public boolean insert(Connection con,RepairDetail repairDetail)throws SQLException{
        PreparedStatement ps = con.prepareStatement(
                "insert into repair_detail values(?,?,?)"
        );

        ps.setLong(1,repairDetail.getRepairDetailId());
        ps.setString(2,repairDetail.getRepairType());
        ps.setDouble(3,repairDetail.getPrice());

        int updateResult = ps.executeUpdate();
        ps.close();

        if(updateResult == 1) {
            return true;
        }else{
            return false;
        }
    }

    public void upsert(Connection con,RepairDetail repairDetail)throws SQLException{
        if(!update(con,repairDetail)){
            insert(con,repairDetail);
        }
    }
}
