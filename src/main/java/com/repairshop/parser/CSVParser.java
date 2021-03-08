package com.repairshop.parser;

import com.cgi.projectt1.entity.*;
import com.repairshop.entity.*;
import com.repairshop.service.DaoService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVParser class - Parses .csv files and puts them to list of desired entity
 */
public class CSVParser {
    static final int CUSTOMER_FILE_COLUMNS = 6;
    static final int VEHICLE_FILE_COLUMNS = 14;
    static final int REPAIRITEM_FILE_COLUMNS = 5;

    private static Logger log = LoggerFactory.getLogger(DaoService.class);


    /**
     * Parses parses Customer .csv file and puts data into list of Customer entities
     * @param filePath
     * @return
     */
    public List<Customer> parseCustomerFile(String filePath){
        CSVParserValidator csvParserValidator = new CSVParserValidator();
        List<Customer> customerList = new ArrayList<>();
        try(CSVReader csvr = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build()){

            String[] array = csvr.readNext();
            while(array != null){
                Customer customer = new Customer();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                customer.setCustomerOldId(Long.parseLong(array[0]));
                customer.setCustomerName(array[1]);
                customer.setCustomerType(array[2]);
                customer.setCustomerOriginDate(LocalDate.parse(array[3],dtf));
                customer.setRegistrationDate(LocalDate.parse(array[4],dtf));
                customer.setPhoneNumber(Long.parseLong(array[5]));

                customerList.add(customer);

                array = csvr.readNext();
            }
        }catch (IOException e){
            //e.printStackTrace();
            System.out.println("Error reading .csv file" + filePath);
        }

        return customerList;
    }

    /**
     * Parses Vehicle .csv file and puts data into list of Vehicle entities
     * @param filePath
     * @return
     */
    public List<Vehicle> parseVehicleFile(String filePath){
        List<Vehicle> vehicleList = new ArrayList<>();

        try(CSVReader csvr = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build()){

            String[] array = csvr.readNext();
            while(array != null){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Vehicle vehicle = new Vehicle();
                Customer customer = new Customer();
                customer.setCustomerOldId(Long.parseLong(array[1]));
                vehicle.setVehicleOldId(Long.parseLong(array[0]));
                vehicle.setCustomer(customer);
                vehicle.setVin(array[2]);
                vehicle.setRegistrationDate(LocalDate.parse(array[3],dtf));
                vehicle.setRegistrationCountry(array[4]);
                vehicle.setManufacturer(array[5]);
                vehicle.setVehicleType(array[6]);
                vehicle.setVehicleBody(array[7]);
                vehicle.setVehicleEngine(array[8]);
                vehicle.setVehicleRestraint(array[9]);
                vehicle.setModel(array[10]);
                vehicle.setYear(Long.parseLong(array[11])); //v db je to taky cislo
                vehicle.setPlant(array[12]);
                vehicle.setSerialNumber(array[13]);

                vehicleList.add(vehicle);
                array = csvr.readNext();
            }
        }catch (IOException e){
            System.out.println("Error opening .csv file" + filePath);
        }

        return vehicleList;
    }

    /**
     * Parses RepairItem .csv file and puts data into list of Repair entities
     * @param filePath
     * @return
     */
    public List<Repair> parseRepairItemFile(String filePath){
        List<Repair> repairList = new ArrayList<>();
        try(CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build()){
            String[] array = csvReader.readNext();
            while(array != null){
                Repair repair = new Repair();
                Vehicle vehicle = new Vehicle();
                RepairDetail repairDetail = new RepairDetail();
                RepairShop repairShop = new RepairShop();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-dd-MM");

                repair.setRepairOldId(Long.parseLong(array[0]));
                vehicle.setVehicleOldId(Long.parseLong(array[1]));
                repairDetail.setRepairDetailId(Long.parseLong(array[2]));
                repairShop.setRepairShopId(Long.parseLong(array[3]));
                repair.setVehicle(vehicle);
                repair.setRepairDetail(repairDetail);
                repair.setRepairShop(repairShop);
                repair.setBillDate(LocalDate.parse(array[4],dtf));

                repairList.add(repair);

                array = csvReader.readNext();
            }

        }catch (IOException e){
            System.out.println("Error opening .csv file" + filePath);
        }

        return repairList;
    }


    public List<RepairDetail> parseRepairDetailFile(String filePath){
        List<RepairDetail> repairDetailList = new ArrayList<>();

        try(CSVReader csvr = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build()){

            String[] array = csvr.readNext();
            while(array != null){
                RepairDetail repairDetail = new RepairDetail();

                repairDetail.setRepairDetailId(Long.parseLong(array[0]));
                repairDetail.setRepairType(array[1]);
                repairDetail.setPrice(Double.parseDouble(array[2]));

                repairDetailList.add(repairDetail);

                array = csvr.readNext();
            }

        }catch (IOException e){
            System.out.println("Error opening .csv file" + filePath);
        }
        return repairDetailList;
    }


    public List<RepairShop> parseRepairShopFile(String filePath)throws IOException{
        List<RepairShop> repairShopList = new ArrayList<>();

        try(CSVReader csvr = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build()){

            String[] array = csvr.readNext();
            while(array != null){
                RepairShop repairShop = new RepairShop();

                repairShop.setRepairShopId(Long.parseLong(array[0]));
                repairShop.setName(array[1]);
                repairShop.setManagerName(array[2]);

                repairShopList.add(repairShop);

                array = csvr.readNext();
            }
        }catch (IOException e){
            throw new IOException("Error opening .csv file" + filePath);
        }
        return repairShopList;
    }







}
