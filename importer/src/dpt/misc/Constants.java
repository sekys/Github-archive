package dpt.misc;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
public class Constants implements Serializable {
    private static final Logger logger = Logger.getLogger(Constants.class.getName());

    public static String LOGGING_PROPERTIES_FILE = "log4j.properties";
    private static JAXBContext context = null;
    private static Constants instance = null;
    private static String FILENAME = "konfiguracia.xml";
    private static String CONFIG_DIR;

    // Attributy
    private boolean configSaved;
    private Map<String, String> dbInfo = new HashMap<String, String>();

    static {
        // Load conf dir
        CONFIG_DIR = System.getProperty("config.dir", System.getProperty("user.dir"));

        // Prepare log4j
        String log4jLoggingPropFile = new File(CONFIG_DIR,
                Constants.LOGGING_PROPERTIES_FILE).getAbsolutePath();
        PropertyConfigurator.configureAndWatch(
                log4jLoggingPropFile,
                30000);

        try {
            context = JAXBContext.newInstance(Constants.class);
        } catch (JAXBException ex) {
            logger.log(Level.ERROR, null, ex);
        }
    }

    private Constants() {

    }

    public static Constants getInstance() {
        if (instance == null) {
            instance = read();
            if (instance == null) {
                File file = new File(CONFIG_DIR, FILENAME);
                throw new RuntimeException("Konfiguracny subor '" + file.getAbsolutePath() + "' nenajdeny.");
            }
        }
        return instance;
    }

    public void write() {
        FileOutputStream writer = null;
        XMLEncoder encoder = null;
        try {
            File file = new File(CONFIG_DIR, FILENAME);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);
            marshaller.marshal(this, file);
        } catch (Throwable ex) {
            logger.log(Level.ERROR, null, ex);
        }
    }

    public static Constants read() {
        Constants konstanten = null;

        try {
            File file = new File(CONFIG_DIR, FILENAME);
            logger.log(Level.INFO, "Konfiguracia nacitana z: " + file.getAbsolutePath());
            konstanten = (Constants) context.createUnmarshaller().unmarshal(file);
            konstanten.setConfigSaved(true);
        } catch (Throwable ex) {
            logger.warn("Nemozem spracovat konfiguracny subor.");
        }
        return konstanten;
    }

    public Map<String, String> getDbInfo() {
        return dbInfo;
    }

    public void setDbInfo(Map<String, String> dbInfo) {
        this.dbInfo = dbInfo;
    }

    @Override
    public String toString() {
        String result = null;
        try {
            StringWriter writer = new StringWriter();
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);
            marshaller.marshal(this, writer);
            result = writer.getBuffer().toString();
        } catch (JAXBException ex) {
            Logger.getLogger(Constants.class.getName()).log(Level.ERROR, null, ex);
        }
        if (result == null) {
            result = ToStringBuilder.reflectionToString(this);
        }
        return result;
    }

    public boolean getConfigSaved() {
        return configSaved;
    }

    public void setConfigSaved(boolean ConfigDateiGefunden) {
        this.configSaved = ConfigDateiGefunden;
    }

}
