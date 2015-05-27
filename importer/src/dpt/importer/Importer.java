package dpt.importer;

import dpt.entities.issues.Issues;
import dpt.misc.Constants;
import dpt.misc.DBService;
import dpt.misc.QueryBuilder;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * dpt.entities.Repository: Seky
 * Date: 2.10.2013
 * Time: 15:57
 */
public class Importer {
    private static final Logger logger = Logger.getLogger(Importer.class.getName());
    public static String ACCESS_TOKEN = "2d2c3a7f1b381e6bfe9b3efbfc3522380e980c1d";

    public static void main(String[] args) throws FileNotFoundException {
        try {
            Date start = DownloadHistory.URL_FORMAT.parse("2012-04-11-0");
            Date end = DownloadHistory.URL_FORMAT.parse("2012-04-11-23");
            File downloadFolder = new File("download/");

            // Faza 1 - stiahni subory podla datumu
            //DownloadHistory.downloadHistory(start, end, downloadFolder);
            // Faza 2 - vyparsuj eventy a repositorie a uloz ich
            ParseEvents.parseFiles(downloadFolder);
            // Faza 3 - vyparsuj issues a uloz ich
            //ParseIssues.parseIssues();
            //ParseIssues.parseRepositoryIssues();
            //ParseIssues.parseRepositoryMilestones();
            ParseIssues.parseOrgs();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*public static List<Issues> getRepositoryAll(String name) {
        QueryBuilder sql = new QueryBuilder();
        sql.select("SELECT ID FROM PDT.REPOSITOY WHERE NAME = ?");
        sql.add(name);
        return DBService.nativeQuery(sql, Issues.class);
    } */
}
