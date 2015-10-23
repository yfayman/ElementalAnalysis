/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.eletablebuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author yan
 */
public class App {

    public static void main(String[] args) throws IOException {
        List<EleInfo> list = new ArrayList<>();
        Document jsoup = Jsoup.connect("http://www.lenntech.com/periodic/mass/atomic-mass.htm")
                .get();

        Elements symbols = jsoup.body().select("tr td:nth-child(4)");
        Elements symbols2 = jsoup.body().select("tr td:nth-child(3)");

        Elements atomicWeights = jsoup.body().select("tr td:nth-child(2)");
        Elements atomicWeights2 = jsoup.body().select("tr td:nth-child(1)");
        System.out.println("test");
        for (int i = 1; i < 17; i++) {
            String symbol = symbols.get(i).text();
            String atWeight = atomicWeights.get(i).text();
            String atNum = symbols.get(i).nextElementSibling().text();
            list.add(new EleInfo(Integer.valueOf(atNum), symbol, atWeight));
        }

        for (int i = 17; i < 56; i++) {
            String symbol = symbols2.get(i).text();
            String atWeight = atomicWeights2.get(i).text();
            if (i == 35) {
                symbol = "Br";
                atWeight = "79.904";
            }
            String atNum = symbols2.get(i).nextElementSibling().text();
            list.add(new EleInfo(i + 1, symbol, atWeight));
        }

        for (int i = 56; i < symbols.size(); i++) {
            String symbol = symbols.get(i).text();
            String atWeight = atomicWeights.get(i).text();
            String atNum = symbols.get(i).nextElementSibling().text();

            list.add(new EleInfo(Integer.valueOf(atNum), symbol, atWeight));
        }
        System.out.println("Done");

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:eleanalysis.sqlite");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            
            int i = 0;
            String sql = "CREATE TABLE elements "
                    + "(ID BIGINT PRIMARY KEY     NOT NULL,"
                    + " SYMBOL         VARCHAR(6)    NOT NULL, "
                    + " CONC            DOUBLE     NOT NULL); ";
            stmt.executeUpdate(sql);

            for (EleInfo el : list) {

                if (el.getAtomicWeight().equals("")) {
                    continue;
                }

                System.out.println(el.getAtomicNumber() + "/" + el.getAtomicWeight());
                sql = "INSERT INTO elements VALUES(" + i++ + ",'" + el.getSymbol() + "', " + el.getAtomicWeight() + ");";
                stmt.executeUpdate(sql);

            }
//            String sql = "SELECT * FROM elements";
//            ResultSet rs = stmt.executeQuery(sql);                 // WORKS!
//            String el = rs.getString("ELEMENT");
//            
//           c.commit();
            stmt.close();
            c.commit();
            c.close();
            System.out.println("Connection closed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
