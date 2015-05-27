package dpt.importer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import dpt.entities.events.Events;
import dpt.entities.events.FileEvents;
import dpt.entities.issues.Repository;
import dpt.misc.DBException;
import dpt.misc.DBService;
import dpt.misc.QueryBuilder;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seky
 * Date: 23.10.2013
 * Time: 15:34
 */
public class ParseEvents {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy/MM/dd HH:mm:ss Z").serializeNulls().create();
    private static final Logger logger = Logger.getLogger(ParseEvents.class.getName());

    private static List<Events> parseEvents(File file, FileEvents fe) throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(file));
        reader.setLenient(true);
        JsonParser jp = new JsonParser();

        JsonElement je = null;
        List<Events> zoznam = new ArrayList<Events>();
        try {
            while ((je = jp.parse(reader)) != null) {
                Events event = gson.fromJson(je, Events.class);
                event.setFileEvents(fe);
                zoznam.add(event);
            }
        } catch (IllegalArgumentException e) {
            logger.info(file + " error at parsing.");
        }

        logger.info("File " + file + ", loaded events " + zoznam.size());
        parseRepositories(zoznam);
        return zoznam;
    }

    private static void parseRepositories(List<Events> events) {
        for (Events event : events) {
            Repository rep = event.getRepository();
            if (rep != null) {
                rep = DBService.getOrCreate("Repository", rep.getUrl(), Repository.class, rep);
                event.setRepository(rep);
            }
        }
    }

    private static void parseFile(File item) {
        // Zisti ci sa uz taky subor parsoval
        String fileName = item.getName();
        QueryBuilder sql = new QueryBuilder();
        sql.select("SELECT * FROM PDT.FileEvents WHERE fileName = ?");
        sql.add(fileName);
        boolean exist = DBService.existRow(sql);
        if (exist) {
            logger.info(item + " subor sa uz parsoval");
            return;
        }

        // Odparsuj vsetky eventy
        List<Events> eventy = null;
        FileEvents fe = null;
        try {
            fe = new FileEvents();
            fe.setFileName(item.getName());
            eventy = parseEvents(item, fe);
        } catch (FileNotFoundException e) {
            logger.info(item + " subor nenajdeny");
            return;
        }

        // Uloz vsetky entity
        try {
            DBService.insertOne(fe);
            DBService.insertMany(eventy);
        } catch (DBException e) {
            e.printStackTrace();
            return;
        }
    }

    public static void parseFiles(File inFolder) {
        for (File item : inFolder.listFiles()) {
            parseFile(item);
        }
    }

}
