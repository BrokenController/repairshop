package com.repairshop.service;

import com.cgi.projectt1.dao.*;
import com.cgi.projectt1.entity.*;

import com.repairshop.entity.*;
import com.repairshop.filehandler.FileHandler;
import com.repairshop.parser.CSVParser;
import com.repairshop.parser.CSVParserException;
import com.repairshop.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class DaoService {
   private static Logger log = LoggerFactory.getLogger(DaoService.class);

    public void InsertTriplet(List<InputFileTriplet> fileTripletList, Connection con, Map<String,String> filePaths)throws IOException, CSVParserException,SQLException {
        CSVParser csvParser = new CSVParser();
        //ConnectionFactory.init();

            CustomerDao customerDao = new CustomerDao();
            VehicleDao vehicleDao = new VehicleDao();
            RepairDao repairItemDao = new RepairDao();
        try{
            for (var triplet : fileTripletList) {
                List<Customer> customerList = csvParser.parseCustomerFile(triplet.getCustomer().getInputFile().getPath());
                List<Vehicle> vehicleList = csvParser.parseVehicleFile(triplet.getVehicle().getInputFile().getPath());
                List<Repair> repairItemList = csvParser.parseRepairItemFile(triplet.getRepairItem().getInputFile().getPath());
                FileHandler fileHandler = new FileHandler();

                log.info("Processing files from " + triplet.getCustomer().getInputFileDate().getMonth().name() + " " + triplet.getCustomer().getInputFileDate().getYear());
                log.info("Inserting customers to database from file: " + triplet.getCustomer().getFileName());
                for (var customer : customerList) {
                    customerDao.upsert(con, customer);
                    con.commit();
                    customer.setCustomerId(customerDao.getId(con, customer));
                }
                log.info("Inserting vehicles to database from file: " + triplet.getVehicle().getFileName());
                for (var vehicle : vehicleList) {
                    for (var customer : customerList) {
                        if (vehicle.getCustomer().getCustomerOldId().equals(customer.getCustomerOldId())) {
                            vehicle.setCustomer(customer);
                            vehicleDao.upsert(con, vehicle);
                            con.commit();
                            var vehicleNewId = vehicleDao.getId(con, vehicle);
                            vehicle.setVehicleId(vehicleNewId);
                        }
                    }
                }
                log.info("Setting vehicle IDs for repairs... ");
                for (var repairItem : repairItemList) {
                    for (var vehicle : vehicleList) {
                        if (repairItem.getVehicle().getVehicleOldId().equals(vehicle.getVehicleOldId())) {
                            repairItem.setVehicle(vehicle);
                            //repairItem.getVehicle().setVehicleId(vehicle.getVehicleId());
                        }
                    }
                }



                log.info("Inserting repairs to database from: " + triplet.getRepairItem().getFileName());
                repairItemDao.mergeAll(con,repairItemList);
                con.commit();

                log.info("Inserting successful, moving triplet to processed folder...");
                fileHandler.moveFileToDirectory(1,triplet.getCustomer().getInputFile(),filePaths.get("processed"));
                fileHandler.moveFileToDirectory(1,triplet.getRepairItem().getInputFile(),filePaths.get("processed"));
                fileHandler.moveFileToDirectory(1,triplet.getVehicle().getInputFile(),filePaths.get("processed"));

                //triplet.getCustomer().getInputFile().renameTo(new File(filePaths.get("processed") + "/" + triplet.getCustomer().getInputFile().getName()));
                //triplet.getVehicle().getInputFile().renameTo(new File(filePaths.get("processed") + "/" + triplet.getVehicle().getInputFile().getName()));
                //triplet.getRepairItem().getInputFile().renameTo(new File(filePaths.get("processed") + "/" + triplet.getRepairItem().getInputFile().getName()));

                log.info("Triplet files from " + triplet.getCustomer().getInputFileDate().getMonth().name() + " " + triplet.getCustomer().getInputFileDate().getYear() + " moved to processed folder\n");
            }
                } catch (SQLException e) {
                    System.out.println("Error inserting triplet, rolling back");
                    e.printStackTrace();
                    try {
                        System.out.println("Rolling back");
                        con.rollback();
                    } catch (SQLException e1) {
                        System.out.println("Rollback unsuccessful");
                        e.printStackTrace();
                    }
                }
            }


    public void insertShopsAndDetails(Connection con,Map<String,String> filePaths)throws SQLException,IOException{
        CSVParser csvParser = new CSVParser();

        List<RepairShop> repairShopList = csvParser.parseRepairShopFile(filePaths.get("shoptables") + "/RepairShop.csv");
        List<RepairDetail> repairDetailList = csvParser.parseRepairDetailFile(filePaths.get("shoptables") + "/RepairDetail.csv");
        RepairDetailDao repairDetailDao = new RepairDetailDao();
        RepairShopDao repairShopDao = new RepairShopDao();

        RepairDetail  repairDetailFixer = new RepairDetail();
        repairDetailFixer.setRepairDetailId(Long.valueOf(0));
        repairDetailFixer.setRepairType("Žádná oprava");
        repairDetailFixer.setPrice(Double.valueOf(0));

        repairDetailDao.upsert(con,repairDetailFixer);
        for(var repairDetail: repairDetailList){
            repairDetailDao.merge(con, repairDetail);
        }
        for (var repairShop: repairShopList){
            repairShopDao.upsert(con, repairShop);
        }
    }
}
