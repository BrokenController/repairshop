package com.repairshop.entity;

import java.io.File;
import java.time.LocalDate;

public class InputFileData {
    private String fileName;
    private File inputFile;
    private int repairShopNumber;
    private String inputFileType;
    private LocalDate inputFileDate;

    public InputFileData(){}

    public InputFileData(File inputFile){
        this.inputFile = inputFile;
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public String getFileName() {
        return fileName;
    }

    public int getRepairShopNumber() {
        return repairShopNumber;
    }

    public String getInputFileType() {
        return inputFileType;
    }

    public LocalDate getInputFileDate() {
        return inputFileDate;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setRepairShopNumber(int repairShopNumber) {
        this.repairShopNumber = repairShopNumber;
    }

    public void setInputFileType(String inputFileType) {
        this.inputFileType = inputFileType;
    }

    public void setInputFileDate(LocalDate inputFileDate) {
        this.inputFileDate = inputFileDate;
    }
}
