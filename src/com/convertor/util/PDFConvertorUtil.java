package com.convertor.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

/**
 * utility class to convert html content to PDF content
 * With Single Output Stream and not reading Error Stream
 *
 * @author Ankur
 */
public class PDFConvertorUtil {

    
    private PDFConvertorUtil(){
        
    }

    /**
     * generate PDF stream using html content
     * Here HTML Content is THE CONTENT OF Entire Html File Read AS a String
     * 
     * @param htmlContent
     * @param command
     * @param encoding
     * @return
     */
    public static ByteArrayOutputStream generatePDF(String htmlContent, String encoding) {

    	// Command -> Path_To_Wkhtml - -
    	// Here "-" specifies Stream I/O
    	String command = "C:/programFiles/wkhtmltopdf/bin/wkhtmltopdf - -"; // Desired command
    	
        Process wkhtml; // Create uninitialized process

        try(ByteArrayOutputStream convertedStream = new ByteArrayOutputStream()) {
            
            // NOTE: Instead of passing html content as string, we can also pass html file and convert it to byte Stream.
            // read html content
            InputStream inputStream = new ByteArrayInputStream(htmlContent.getBytes(Charset.forName(encoding)));

            wkhtml = Runtime.getRuntime().exec(command); // Start process

            // thread to read html content to set it to Process
            IOUtils.copy(inputStream, wkhtml.getOutputStream());

            wkhtml.getOutputStream().flush();
            wkhtml.getOutputStream().close();

            IOUtils.copy(wkhtml.getInputStream(), convertedStream);

            wkhtml.getInputStream().close();
            wkhtml.destroy();
            inputStream.close();

            return convertedStream;

        }
        catch (RuntimeException | IOException ex) {

        }

        return null;
    }
}
