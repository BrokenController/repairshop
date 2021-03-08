package com.repairshop.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * ConfigFileParser class
 * Parses config files (crdb.properties and PathConfigFile.xml)
 */
public class ConfigFileParser {
    private static Logger log = LoggerFactory.getLogger(ConfigFileParser.class);

    /**
     * Parses PathConfigFile.xml and returns Map of strings with file paths
     *
     * @param configFilePath
     * @return
     * @throws ConfigFileParserException
     */
    public Map<String, String> xmlToPath(String configFilePath) throws ConfigFileParserException {
        try {
            Map<String, String> pathMap = new HashMap<>();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            InputStream input;
            Document doc;
            if (configFilePath == null) {
                String filename = "PathConfigFile.xml";
                input = ArgumentsParser.class.getClassLoader().getResourceAsStream(filename);
                doc = db.parse(input);
            } else {
                doc = db.parse(configFilePath);
            }

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xp = xpf.newXPath();

            XPathExpression xPathExpression = xp.compile("/crdb/config/directories/directory");
            NodeList directoryNodeList = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < directoryNodeList.getLength(); i++) {
                Node n = directoryNodeList.item(i);
                Element e = (Element) n;

                String path = e.getFirstChild().getTextContent();
                pathMap.put(e.getAttribute("type"), path);
            }

            return pathMap;

        } catch (ParserConfigurationException | XPathExpressionException | SAXException | IOException e) {
            throw new ConfigFileParserException("Error while parsing config file." + e);
        }
    }

    /**
     * Parses crdb.database file and returns array of strings with database connection data. If file contains incomplete login data the method throws an exception
     *
     * @param crdbPropertiesPath
     * @return
     * @throws ConfigFileParserException
     * @throws IOException
     */
    public String[] readDatabasePropertiesFile(String crdbPropertiesPath) throws ConfigFileParserException, IOException {
        Properties prop = new Properties();

        String connUrl = "";
        String user = "";
        String password = "";

        InputStream input = null;

        try {
            if (crdbPropertiesPath == null) {
                String filename = "crdb.properties";
                input = ArgumentsParser.class.getClassLoader().getResourceAsStream(filename);
            } else {
                input = new FileInputStream(crdbPropertiesPath);
            }
            prop.load(input);
            connUrl = prop.getProperty("connectionString");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (connUrl.isEmpty() || user.isEmpty() || password.isEmpty()) {
            throw new ConfigFileParserException("crdb.properties file contains invalid data. Aborting.");
        }

        String[] connectionData = {connUrl, user, password};
        return connectionData;
    }
}
