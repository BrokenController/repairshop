package com.repairshop.parser;



public class ArgumentsParser {

    /**
     * ArgumentsParser class - parses user given parameters. Sets default paths to config files if no arguments are given.
     * @param args
     * @return
     * @throws ArgumentsParserException
     */
    public String[] parseArguments(String[] args) throws ArgumentsParserException{
        String configFilePath = "";
        String crdbPropertiesPath = "";

        if(args == null || args.length == 0){
            //set defaults
            configFilePath = null;
            crdbPropertiesPath = null;

        }else if(args.length == 2){
            //parse arguments method
            if(args[0].contains("PathConfigFile.xml") && args[1].contains("crdb.properties")){
                configFilePath = args[0];
                crdbPropertiesPath = args[1];
            }
            else {
                throw new ArgumentsParserException("Error parsing arguments.");
            }
        }

        String[] filePathArguments = {configFilePath,crdbPropertiesPath};
        return filePathArguments;
    }
}
