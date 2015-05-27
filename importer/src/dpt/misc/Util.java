package dpt.misc;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Seky
 * Date: 19.10.2013
 * Time: 15:36
 */
public class Util {
    private static final Logger logger = Logger.getLogger(Util.class.getName());


    // Stiahni subor z adresy a lokalne uloz
    public static void download(String www, File outputFile, boolean unzip) throws IOException {
        URL url = new URL(www);
        InputStream in = new BufferedInputStream(url.openStream());
        if (unzip) in = new GZIPInputStream(in);
        outputFile.createNewFile();
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

        try {
            IOUtils.copy(in, outputStream);
            logger.info("Downloaded " + outputFile);
        } finally {
            outputStream.close();
            in.close();
        }
    }

    public static String download(String www) throws IOException {
        URL url = new URL(www);
        InputStream in = new BufferedInputStream(url.openStream());
        StringWriter writer = new StringWriter();
        IOUtils.copy(in, writer, "UTF-8");
        return writer.toString();
    }
}
