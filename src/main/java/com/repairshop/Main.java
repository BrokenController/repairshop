package com.repairshop;

import com.repairshop.database.ConnectionFactory;
import com.repairshop.entity.InputFileTriplet;
import com.repairshop.filehandler.FileHandler;
import com.repairshop.filehandler.FileHandlerEmptyInputException;
import com.cgi.projectt1.parser.*;
import com.repairshop.parser.*;
import com.repairshop.service.DaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

public class Main {

    private static Logger log = LoggerFactory.getLogger(DaoService.class);


    public static void main(String[] args) {

        String argumentFilePath1 = "C:\\Users\\miroslav.vlodarcik\\Desktop\\inputData\\PathConfigFile.xml";
        String argumentFilePath2 = "C:\\Users\\miroslav.vlodarcik\\Desktop\\inputData\\crdb.properties";

        String[] args2 = {argumentFilePath1, argumentFilePath2};

        Map<String, String> configDirectoryPaths = new HashMap<>();
        String[] databaseConnectionData = new String[3];
        try {
            ConfigFileParser configFileParser = new ConfigFileParser();
            ArgumentsParser argumentsParser = new ArgumentsParser();
            String[] argumentFilePaths = argumentsParser.parseArguments(args2);

            configDirectoryPaths = configFileParser.xmlToPath(argumentFilePaths[0]);
            databaseConnectionData = configFileParser.readDatabasePropertiesFile(argumentFilePaths[1]);
        } catch (ConfigFileParserException | MissingResourceException | IOException | ArgumentsParserException e) {
            log.info("Error while working with arguments" + e);
            log.debug("Error with arguments" + e);
            e.printStackTrace();
        }

        ConnectionFactory.init();
        try (var con = ConnectionFactory.create(databaseConnectionData[0], databaseConnectionData[1], databaseConnectionData[2])) {
            DaoService daoService = new DaoService();
            FileHandler fileHandler = new FileHandler(configDirectoryPaths);

            //log.info("Clearing output folder...");
            //fileHandler.clearOutputFolder();

            List<InputFileTriplet> inputFileTripletList = fileHandler.getInputFileTriplet();
            
            log.info("Inserting shops and details to database folder \n");
            daoService.insertShopsAndDetails(con, configDirectoryPaths);

            long start = System.currentTimeMillis();
            log.info("Files from input will be now synced to database \n");
            daoService.InsertTriplet(inputFileTripletList, con, fileHandler.getFilePaths());
            long end = System.currentTimeMillis();
            double total = (end - start) / 1000;
            log.info("Time taken to insert all = " + total + " s");

            log.info("Moving rest of unprocessed files from input to bad \n");
            fileHandler.moveFilesToBadDirectory(fileHandler.getInputFolderFiles());

        } catch (SQLException | IOException | CSVParserException e) {
            e.printStackTrace();
        } catch (FileHandlerEmptyInputException e) {
            log.info("There is nothing to process, input folder is empty");
        }
    }
}

