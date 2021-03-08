package com.repairshop.filehandler;

import com.repairshop.entity.InputFileData;
import com.repairshop.entity.InputFileTriplet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Class uses methods for validating given files. It validates if files are csv and if filename contains correct values.
 */
public class FileHandlerValidator {
    private static Logger log = LoggerFactory.getLogger(FileHandlerValidator.class);

    public void validateIfFileIsCsv(File inputFile, Map<String, String> filePaths) throws IOException {
        FileHandler fileHandler = new FileHandler();

        if (inputFile.isDirectory()) {
            FileUtils.moveDirectory(inputFile, new File(filePaths.get("bad/") + "/" + inputFile.getName()));
            log.info("Directory: " + inputFile.getName() + " is not allowed in /input directory and was moved to /bad directory"); //to zni retardovane

        }
        if (!FilenameUtils.getExtension(inputFile.getName()).equals("csv")) {
            fileHandler.moveFileToDirectory(inputFile, filePaths.get("bad"));
            log.info("File: " + inputFile.getName() + " is not .csv file and was moved to /bad directory");
        }
    }

    public void moveFilesWithNoTriplet(List<InputFileData> inputFileDataList, List<InputFileTriplet> inputFileTripletList, Map<String, String> filePaths) {
        for (var inputFile : inputFileDataList) {
            boolean noTriplet = true;
            for (var triplet : inputFileTripletList) {
                if ((triplet.getVehicle().getInputFile() == inputFile.getInputFile() || triplet.getCustomer().getInputFile() == inputFile.getInputFile() || triplet.getRepairItem().getInputFile() == inputFile.getInputFile())) {
                    noTriplet = false;
                }
            }
            if (noTriplet == true) {
                inputFile.getInputFile().renameTo(new File(filePaths.get("bad") + "/" + inputFile.getFileName()));
                log.info("File: " + inputFile.getInputFile().getName() + " is not member of any triplet and is moved to bad folder");
            }
        }
    }

    public boolean validateFileName(String filename, String[] pathSplit, Map<String, String> filePaths) {
        if (!pathSplit[1].matches("[0-9]+")) {
            log.info("File: " + filename + " has invalid shop number (only numbers are allowed)");
            return false;
        }

        if (!pathSplit[0].equalsIgnoreCase("RS")) {
            log.info("File" + filename + " has invalid format");
            return false;
        }

        if (!(pathSplit[2].equalsIgnoreCase("vehicle") || pathSplit[2].equalsIgnoreCase("customer") || pathSplit[2].equalsIgnoreCase("repairitem"))) {
            log.info("File" + filename + " has invalid format");
            return false;
        }

        if (pathSplit.length != 5) {
            log.info("File" + filename + " has invalid format");
            return false;
        }

        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate.parse(pathSplit[3], dtf);
        } catch (DateTimeException e) {
            log.info("File " + filename + " has invalid date.");
            return false;
        }

        if (pathSplit[3].length() != 8) {
            log.info("date invalid, returning false");
            return false;
        }

        return true;
    }


}
