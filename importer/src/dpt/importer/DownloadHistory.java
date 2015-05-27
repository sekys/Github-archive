package dpt.importer;

import dpt.misc.Util;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Seky
 * Date: 19.10.2013
 * Time: 14:28
 */
public class DownloadHistory {
    private static final Logger logger = Logger.getLogger(DownloadHistory.class.getName());

    public static DateFormat URL_FORMAT = new SimpleDateFormat("yyyy-MM-dd-H");

    // Stiahni vsetko v danm rozsahu
    public static void downloadHistory(Date start, Date end, File folder) throws IOException {
        while (start.before(end)) {
            // Pripocitavaj po 1 hodinu kym nieje koniec
            Calendar c = Calendar.getInstance();
            c.setTime(start);
            c.add(Calendar.HOUR, 1);
            start = c.getTime();

            // Naformatuj url adresu, meno suboru
            String fileName = URL_FORMAT.format(start) + ".json";
            String www = "http://data.githubarchive.org/" + fileName + ".gz";
            File outputFile = new File(folder + "/" + fileName);
            if (outputFile.exists()) {
                logger.info("File " + outputFile + " exist, skipping.");
            } else {
                Util.download(www, outputFile, true);
            }
        }
    }

}
