package com.convertor.util;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * Converting html file to Pdf file
 * With parallel Output and error stream for the process
 * 
 * @author Ankur
 *
 */
public class StreamBasedPdfGenerator {
  public void makeAPdfWithStreams() throws InterruptedException, IOException {
        Process wkhtml; // Create uninitialized process

        // Command -> Path_To_Wkhtml Path_To_input_html Path_to_output_pdf
        String command = "C:/programFiles/wkhtmltopdf/bin/wkhtmltopdf /Users/Shared/pdfPrintExample.html /Users/Shared/output.pdf"; // Desired command

        wkhtml = Runtime.getRuntime().exec(command); // Start process

        Thread errThread = new Thread(() -> {
            try {
                IOUtils.copy(wkhtml.getErrorStream(), System.err);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Thread htmlReadThread = new Thread(() -> {
            try {
            	IOUtils.copy(wkhtml.getErrorStream(), System.out);
                wkhtml.getOutputStream().flush();
                wkhtml.getOutputStream().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Do NOT use Run... it should be clear why, you want them to all be going at the same time.
        errThread.start();
        htmlReadThread.start();

        wkhtml.waitFor(); // Allow process to run
    }
}