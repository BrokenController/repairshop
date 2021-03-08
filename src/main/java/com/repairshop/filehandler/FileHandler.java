package com.repairshop.filehandler;


import com.repairshop.entity.InputFileData;
import com.repairshop.entity.InputFileTriplet;
import com.repairshop.entity.Repair;
import com.repairshop.entity.Vehicle;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * FileHandler class - serves for manipulation of files.
 */
public class FileHandler {

    private static Logger log = LoggerFactory.getLogger(FileHandler.class);

    public FileHandler() {
    }

    private Map<String, String> filePaths = new HashMap<>();

    public FileHandler(Map<String, String> filePaths) {
        this.filePaths = filePaths;
    }

    public Map<String, String> getFilePaths() {
        return filePaths;
    }

    /**
     * Method gets list of strings of filepaths in input folder, parses it's names and puts data into list of InputFileData entities for further use.
     *
     * @param listOfFiles
     * @return
     * @throws FileHandlerException
     */
    public List<InputFileData> getFilesAsEntities(File[] listOfFiles) throws FileHandlerEmptyInputException {
        FileHandlerValidator fileHandlerValidator = new FileHandlerValidator();
        List<InputFileData> inputFileDataList = new ArrayList<>();

        if (listOfFiles == null || listOfFiles.length == 0) {
            throw new FileHandlerEmptyInputException();
            //log.info("empty input folder");
        }

        log.info("Validating filenames in input folder...");

        int greenFilesCount = 0;
        int redFilesCount = 0;

        for (File file : listOfFiles) {
            InputFileData inputFileData = new InputFileData();
            String fileName = file.getName();

            String[] pathSplit = fileName.split("_|\\.");
            if (!fileHandlerValidator.validateFileName(fileName, pathSplit, this.getFilePaths())) {
                log.info("File " + fileName + " has invalid name. Moving file to BAD directory.");
                try {
                    file.renameTo(new File(filePaths.get("bad") + "/" + file.getName()));
                } catch (Exception e) {
                    log.info("Couldn't move invalid file to BAD directory.");
                }
                redFilesCount++;
                continue;
            }

            inputFileData.setInputFile(file);
            inputFileData.setFileName(fileName);
            inputFileData.setRepairShopNumber(Integer.parseInt(pathSplit[1]));
            inputFileData.setInputFileType(pathSplit[2]);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd"); //datum muze byt spatne, exception
            inputFileData.setInputFileDate(LocalDate.parse(pathSplit[3], dtf));

            inputFileDataList.add(inputFileData);
            greenFilesCount++;


        }
        log.info("Valid files: " + greenFilesCount + ", invalid files: " + redFilesCount);
        return inputFileDataList;
    }

    /**
     * Filters only Customers from InputFileData list and returns list of Customers
     *
     * @param inputFileData
     * @return
     */
    public List<InputFileData> getAllCustomerFiles(List<InputFileData> inputFileData) {
        List<InputFileData> inputCustomersList = new ArrayList<>();

        for (var inputFile : inputFileData) {
            if (inputFile.getInputFileType().equalsIgnoreCase("customer")) {
                inputCustomersList.add(inputFile);
            }
        }
        return inputCustomersList;
    }

    /**
     * Creates list of triplets containing Customer,Vehicle,RepairItem (one of each) with same year and month
     *
     * @return
     * @throws IOException
     */
    public List<InputFileTriplet> getInputFileTriplet() throws IOException, FileHandlerEmptyInputException {
        FileHandlerValidator fileHandlerValidator = new FileHandlerValidator();
        List<InputFileTriplet> inputFileTripletList = new ArrayList<>();

        List<InputFileData> inputFileDataList = this.getFilesAsEntities(getInputFolderFiles());
        List<InputFileData> inputCustomersList = getAllCustomerFiles(inputFileDataList);

        Collections.sort(inputCustomersList, Comparator.comparing(InputFileData::getInputFileDate));

        for (var customerFile : inputCustomersList) {
            InputFileTriplet inputFileTriplet = new InputFileTriplet();
            for (var inputFile : inputFileDataList) {
                if (inputFile.getInputFileDate().getYear() == customerFile.getInputFileDate().getYear()
                        && inputFile.getInputFileDate().getMonth().equals(customerFile.getInputFileDate().getMonth())
                        && customerFile.getRepairShopNumber() == inputFile.getRepairShopNumber()) {
                    if (inputFile.getInputFileType().equalsIgnoreCase("customer")) {
                        inputFileTriplet.setCustomer(inputFile);
                    }
                    if (inputFile.getInputFileType().equalsIgnoreCase("vehicle")) {
                        inputFileTriplet.setVehicle(inputFile);
                    }
                    if (inputFile.getInputFileType().equalsIgnoreCase("repairItem")) {
                        inputFileTriplet.setRepairItem(inputFile);
                    }
                }
            }
            if (!inputFileTriplet.containsNull()) {
                inputFileTripletList.add(inputFileTriplet);
            } else {
                log.info("Triplet incomplete: " + inputFileTriplet.getCustomer().getFileName());
            }
        }
        return inputFileTripletList;
    }

    /**
     * Scans for all files in input folder (except directories and non .csv files) and returns list of files
     *
     * @return
     * @throws IOException
     */
    public File[] getInputFolderFiles() throws IOException {
        FileHandlerValidator fileHandlerValidator = new FileHandlerValidator();
        String path = filePaths.get("input");

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (!(listOfFiles == null)) {
            for (var file : listOfFiles) {
                fileHandlerValidator.validateIfFileIsCsv(file, filePaths);
            }
            return listOfFiles;
        } else {
            throw new IOException("Input folder is missing or empty!");
        }
    }

    public void clearOutputFolder() {
        String processedPath = filePaths.get("processed");
        String badPath = filePaths.get("bad");

        File processedFolder = new File(processedPath);
        File badFolder = new File(badPath);

        try {
            ArrayList<File> listOfOutputFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(processedFolder.listFiles())));
            listOfOutputFiles.addAll(Arrays.asList(Objects.requireNonNull(badFolder.listFiles())));

            for (var file : listOfOutputFiles) {
                try {
                    FileUtils.forceDelete(file);
                } catch (IOException e) {
                    log.info("Could not remove file " + file.getName() + ". Make sure it's not open or used by another program.");
                }
            }
            log.info("Deleted files: " + listOfOutputFiles.size() + "\n");
        } catch (NullPointerException e) {
            log.info("Error deleting output files." + e);
        }
    }

    public void moveFilesToBadDirectory(File[] files) throws IOException {
        if (!(files == null || files.length == 0)) {
            for (var file : files) {
                FileUtils.moveFile(file, new File(filePaths.get("bad") + "/" + file.getName()));
            }
        } else {
            log.info("Nothing to process, folder is empty");
        }
    }

    public void moveFileToDirectory(File file, String mapFilePath) throws IOException {
       // String fileName = file.getName().replaceFirst("[.][^.]+$", "");
        String oldFilePath = mapFilePath + "/" + file.getName();
        File oldFile = new File(oldFilePath);
        if (oldFile.exists()) {
            Integer append = 1;
            moveFileToDirectory(append, file, mapFilePath);
        }else{
            if (file.renameTo(oldFile)) {
                log.info("Moving file " + file.getName() + " to BAD");
            } else {
                throw new IOException("Couldn't move " + file.getName() + " to BAD directory");
            }
        }
    }

    public void moveFileToDirectory(Integer append, File file, String mapfilePath) throws IOException {
        String fileName = file.getName().replaceFirst("[.][^.]+$", "");
        String filePath = mapfilePath + "/" + fileName + "(" + append.toString() + ")." + FilenameUtils.getExtension(file.getName());
        String filePathwoAppend = mapfilePath + "/" + fileName;
        File oldFile = new File(filePath);

        if (oldFile.exists()) {
            append++;
            moveFileToDirectory(append, file, mapfilePath);
        } else {
            if (file.renameTo(oldFile)) {
                log.info("Moving file " + file.getName() + " to BAD");
            } else {
                throw new IOException("Couldn't move " + file.getName() + " to BAD directory");
            }
        }
    }

    public void printParsedInfoToFile(List<Repair> repairList) throws IOException {
        //Writer w1 = new File
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("repairData.txt"))) {
            for (var repair : repairList) {
                bw.append(repair.toString() + "vehicle old ID: " + repair.getVehicle().getVehicleOldId() + " + vehicle new ID: " + repair.getVehicle().getVehicleId());
                bw.newLine();
            }
        }
    }

    public void printParsedVehicleToFile(List<Vehicle> vehicleList) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("vehicleData.txt"))) {
            for (var vehicle : vehicleList) {
                bw.append("vehicle old ID: " + vehicle.getVehicleOldId() + " + vehicle new ID: " + vehicle.getVehicleId());
                bw.newLine();
            }
        }
    }
}
