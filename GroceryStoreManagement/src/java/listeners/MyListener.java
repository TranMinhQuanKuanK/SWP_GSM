/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Tran Minh Quan
 */
public class MyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
      
        ServletContext sc = sce.getServletContext();
        Map<String, String> StoreownerSitemap = new HashMap<>();
        Map<String, String> CashierSitemap = new HashMap<>();

        String realPath = sc.getRealPath("/");
        String cashierFilePath = realPath + "WEB-INF/CashierSitemap.txt";
        String storeownerFilePath = realPath + "WEB-INF/StoreownerSitemap.txt";

        try {
            File file = new File(storeownerFilePath);
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "=");
                String label = tokenizer.nextToken().trim();
                String source = tokenizer.nextToken().trim();   
                StoreownerSitemap.put(label, source);
            }
            sc.setAttribute("StoreownerSitemap", StoreownerSitemap);
            //-----------------------------------------
            file = new File(cashierFilePath);
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "=");
                String label = tokenizer.nextToken().trim();
                String source = tokenizer.nextToken().trim();
                CashierSitemap.put(label, source);
            }
            sc.setAttribute("CashierSitemap", CashierSitemap);
        } catch (FileNotFoundException ex) {
            sc.log("ServletListener FileNotFoundException: " + ex.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
