package com.repairshop.parser;

public class CSVParserValidator {
    static final int CUSTOMER_FILE_COLUMNS = 6;
    static final int VEHICLE_FILE_COLUMNS = 14;
    static final int REPAIRITEM_FILE_COLUMNS = 5;


    public boolean validateCSVColumns(String[] array,String csvType)throws CSVParserException{
        switch (csvType){
            case "customer":
                if(array.length != CUSTOMER_FILE_COLUMNS) {
                    throw new CSVParserException("customer columns wrong");
                    //return false;
                }
                return true;

            case "vehicle":
                if(array.length != VEHICLE_FILE_COLUMNS) {
                    return false;
                }

            case "repairItem":
                if(array.length != REPAIRITEM_FILE_COLUMNS) {
                    return false;
                }
                return true;
            default: return false;
        }
    }
}
