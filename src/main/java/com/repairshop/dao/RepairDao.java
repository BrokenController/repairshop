package com.repairshop.dao;

import com.repairshop.entity.Repair;
import com.repairshop.service.DaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * RepairDao - This database access object updates or inserts repairItem data into database
 */
public class RepairDao {
    private static Logger log = LoggerFactory.getLogger(DaoService.class);

    public void mergeAll(Connection con, List<Repair> repairItemList) throws SQLException{
        PreparedStatement ps = con.prepareStatement(
                "merge into repair r using (select ? re_old_id, ? re_veh_id, ? re_veh_old_id, ? re_det_id, ? re_shop_id, ? re_bill_date from dual) d " +
                        "on (r.re_shop_id = d.re_shop_id AND r.re_old_id = d.re_old_id) " +
                        "when matched then update set r.re_veh_id = d.re_veh_id, r.re_veh_old_id = d.re_veh_old_id, r.re_det_id = d.re_det_id, r.re_bill_date = d.re_bill_date " +
                        "when not matched then insert values (repair_seq.nextval,d.re_old_id,d.re_veh_id,d.re_veh_old_id,d.re_det_id,d.re_shop_id,d.re_bill_date)"
        );

        long start = System.currentTimeMillis();
        int counter = 0;
        for(var repairItem :repairItemList){
            ps.setLong(1,repairItem.getRepairOldId());
            ps.setLong(2,repairItem.getVehicle().getVehicleId());
            ps.setLong(3,repairItem.getVehicle().getVehicleOldId());
            ps.setLong(4,repairItem.getRepairDetail().getRepairDetailId());
            ps.setLong(5,repairItem.getRepairShop().getRepairShopId());
            ps.setDate(6,Date.valueOf(repairItem.getBillDate()));

            counter ++;
            ps.addBatch();
            if(counter >=10000) {
                ps.executeBatch();
                counter=0;
            }
        }
        if (counter>0){
            ps.executeBatch();
        }
        long end = System.currentTimeMillis();
        log.info("Time taken to insert repair data = " + (end - start) + " ms");
        ps.close();
    }

}
