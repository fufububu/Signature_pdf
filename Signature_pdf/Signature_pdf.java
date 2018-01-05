/*
 FB 22/07/2013
 Java class to add press mark number sequence on pages group
 To be used on perfect bounf binding
 */
package Signature_pdf;

import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;

public class Signature_pdf {

    public static void main(String[] args) {

        float h_scale;
        float v_scale;
        float mark_scale;
        float h_trans;
        float v_trans;
        String s_input_file = "", s_output_file = "", s_marker_file = "";
        int i_group_num = 0, i_group_num_sheets = 0;

        JTextField working_dir = new JTextField();
        working_dir.setColumns(80);
        JTextField input_file = new JTextField();
        JTextField output_file = new JTextField();
        JTextField marker_file = new JTextField();
        JTextField group_num = new JTextField();
        JTextField group_num_sheets = new JTextField();

        if (args.length == 0) {
            // If no arguments given then use graphical input request (swing)
            // set Jtext field for input data
            Object[] message = {
                "Working dir:", working_dir,
                "Input pdf filename (no .pdf extension)", input_file,
                "Output pdf filename (no .pdf extension)", output_file,
                "How many groups (only complete ones and max 40)?", group_num,
                "Signature sheets (max 50)?", group_num_sheets,
                "Markers filename (no .pdf extension, default \"Marker\")", marker_file
            };

            // Get input data
            int option = JOptionPane.showConfirmDialog(null, message, "Group sequence number imposition", JOptionPane.OK_CANCEL_OPTION, 1);

            if (option == JOptionPane.OK_OPTION) {
                s_input_file = working_dir.getText() + "\\" + input_file.getText() + ".pdf";
                s_output_file = working_dir.getText() + "\\" + output_file.getText() + ".pdf";
                i_group_num = Integer.parseInt(group_num.getText());
                i_group_num_sheets = Integer.parseInt(group_num_sheets.getText());
                s_marker_file = ".\\markers\\" + marker_file.getText();
            } else {
                // Wait 5 seconds
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                System.exit(0);
            }
        } else {
            if (args.length <5) {
                System.out.println("*************************************");
                System.out.println("* Java program to number signatures *");
                System.out.println("*************************************");
                System.out.println("To set numbers on signatures when binding books");
                System.out.println("Proper Usage is:");
                System.out.println("       java -jar Signature_pdf.jar <working directory> <input filename> <otuput filename> <signature number> <signature sheets> <markers filename>");
                System.exit(0);

            } else {
                // Else fill variables with command line arguments
                working_dir.setText(args[0]);
                s_input_file = working_dir.getText() + "\\" + args[1] + ".pdf";
                s_output_file = working_dir.getText() + "\\" + args[2] + ".pdf";
                i_group_num = Integer.valueOf(args[3]);
                i_group_num_sheets = Integer.valueOf(args[4]);
                if (args.length == 6) {
                    s_marker_file = args[5];
                }
            }
        }
        // If marker name not inserted use default
        if (s_marker_file == "") {
            s_marker_file = ".\\markers\\Marker";
        }
        
        // Start output windows log console
        AWTConsole console = new AWTConsole();

        // Check if there are enough markers files
        // 
        for (int ii = 1; ii <= i_group_num + 1; ii = ii + 1) {
            // Create marker file name and check existence
            File file = new File(s_marker_file + "_" + ii + ".pdf");
            if (!file.exists()) {
                System.out.printf("Il file :" + s_marker_file + "_" + ii + ".pdf not esist!%n");
                // Wait 5 seconds
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                System.exit(1);
            }
        }

        try {
            PdfReader[] marker = new PdfReader[50];
            PdfImportedPage[] sfondo_marker = new PdfImportedPage[50];
            Rectangle[] dimensione_pagina_marker = new Rectangle[50];

            int iii;

            // Load pdf input file
            // get numpages
            PdfReader document = new PdfReader(s_input_file);
            int num_pages = document.getNumberOfPages();

            // Check  that "group number of sheets"*"number of groups">totale document pages
            //
            if (num_pages < i_group_num_sheets * i_group_num * 2) {
                System.out.printf("input document pages less than total evaluates pages ( group pages* group number)! %n");
                // Wait 5 seconds
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                return;
            }
            System.out.printf("Program start%n%n");
            System.out.println("PDF version: " + document.getPdfVersion());
            System.out.printf("Document Pages:%d%n", num_pages);
            System.out.format("File size (Kbytes): %d%n", document.getFileLength() / 1000);
            System.out.println("Criptografy ? " + document.isEncrypted());
            System.out.println("Page width (mm): " + document.getPageSize(1).getWidth() / 2.834645669);
            System.out.println("Page height (mm): " + document.getPageSize(1).getHeight() / 2.834645669);

            // Open output document
            PdfStamper writer = new PdfStamper(document, new FileOutputStream(s_output_file));

            // loop on group number
            for (int ii = 1; ii <= i_group_num + 1; ii = ii + 1) {

                // System.err.println(ii );
                // Create marker filename
                String group_name = s_marker_file + "_" + ii + ".pdf";
                //System.err.println(group_name );

                // load marker page
                marker[ii] = new PdfReader(group_name);
                //System.err.println(marker[ii]);

                // Get merker page dimension
                dimensione_pagina_marker[ii] = marker[ii].getPageSize(1);

                // Impose marker on output file 
                sfondo_marker[ii] = writer.getImportedPage(marker[ii], 1);
            }

            iii = 0;
            System.out.printf("Processing signature :");

            // Loop on "group number" * "group pages" *2 step "group pages" *2 ( remember it's duplex, multiply per 2) 
            // 
            for (int ii = 1; ii < i_group_num_sheets * i_group_num * 2; ii = ii + i_group_num_sheets * 2) {
                iii++;
                System.out.printf("%d ", iii);
                // iterate over document's pages, adding mark_page as
                // a layer 'underneath' the page content; scale mark_page
                // and move it so it fits within the document's page;
                // if document's page is cropped, then this scale might
                // not be small enough

                // get input document pagesize
                Rectangle dimensione_pagina_documento = document.getPageSize(ii);
                h_scale = (dimensione_pagina_documento.getWidth() / dimensione_pagina_marker[iii].getWidth());
                v_scale = dimensione_pagina_documento.getHeight() / dimensione_pagina_marker[iii].getHeight();
                mark_scale = (h_scale < v_scale) ? h_scale : v_scale;

                // evaluate scale factor to be used
                h_trans = (float) ((dimensione_pagina_documento.getWidth() - dimensione_pagina_marker[iii].getWidth() * mark_scale) / 2.0);
                v_trans = (float) ((dimensione_pagina_documento.getHeight() - dimensione_pagina_marker[iii].getHeight() * mark_scale) / 2.0);

                // insert scaled marker page on first even page of ech group
                PdfContentByte contentByte = writer.getOverContent(ii);
                contentByte.addTemplate(sfondo_marker[iii],
                        mark_scale, 0, 0,
                        mark_scale, h_trans, v_trans);
            }

            int ii = i_group_num_sheets * i_group_num * 2 + 1;
            if (ii < num_pages) {
                iii++;
                System.out.printf("%d ", iii);

                // get input pagesize
                Rectangle dimensione_pagina_documento = document.getPageSize(ii);
                h_scale = dimensione_pagina_documento.getWidth() / dimensione_pagina_marker[iii].getWidth();
                v_scale = dimensione_pagina_documento.getHeight() / dimensione_pagina_marker[iii].getHeight();
                mark_scale = (h_scale < v_scale) ? h_scale : v_scale;

                // evaluate scale factor to be used
                h_trans = (float) ((dimensione_pagina_documento.getWidth() - dimensione_pagina_marker[iii].getWidth() * mark_scale) / 2.0);
                v_trans = (float) ((dimensione_pagina_documento.getHeight() - dimensione_pagina_marker[iii].getHeight() * mark_scale) / 2.0);

                // insert scaled marker page on first even page of ech group
                PdfContentByte contentByte = writer.getOverContent(ii);
                contentByte.addTemplate(sfondo_marker[iii],
                        mark_scale, 0, 0,
                        mark_scale, h_trans, v_trans);
            }
            // Close output document
            writer.close();
            System.out.printf("%n%n");
            System.out.printf("Program end%n");
            System.out.printf("Wait 5 seconds...%n");

            // Wait 5 seconds
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
}
