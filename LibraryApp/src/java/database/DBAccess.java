/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Beans.Book;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Für Zugriff auf Datenbank
 *
 * @author Julia
 */
public class DBAccess {

    // class implementet as Singleton
    private static DBAccess theInstance = null;
    private DBConnectionPool conPool = null;

    public static DBAccess getInstance() throws ClassNotFoundException {
        if (theInstance == null) {
            theInstance = new DBAccess();
        }
        return theInstance;
    }

    private DBAccess() throws ClassNotFoundException {
        conPool = DBConnectionPool.getInstance();
    }


    // 0    ; 1     ; 2      ; 3         ; 4           ; 5       ; 6        ; 7    ; 8       ; 9      ; 10     
    // ISBN ; TITEL ; AUTHOR ; PUBLISHER ; PUBLICATION ; SUMMARY ; LANGUAGE ; LINK ; PICTURE ; AMOUNT ; AVAILABLE
    public LinkedList<Book> getBookFromList(String pfad) throws FileNotFoundException, IOException, Exception {
        LinkedList<Book> buecherListe = new LinkedList<>();
        FileReader fr = new FileReader(pfad);
        BufferedReader br = new BufferedReader(fr);
        String line = "";

        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(";");

            String isbn = parts[0];
            String titel = parts[1];
            String author = parts[2];
            String publisher = parts[3];

            Date publication;
            LocalDate d = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            publication = new Date(d.toEpochDay());
            String summary = parts[5];
            String language = parts[6];
            String linkToAmazon = parts[7];
            String picture = "NULL";
            int amount = Integer.parseInt(parts[9]);
            int available = Integer.parseInt(parts[10]);      
            
            Book b = new Book(isbn, titel, author, publisher, publication, summary, language, linkToAmazon, picture, amount, available);
            buecherListe.add(b);
//           insertBook(b);
        }

        br.close();
        fr.close();

        return buecherListe;
    }
  
    // ISBN ; TITEL ; AUTHOR ; PUBLISHER ; PUBLICATION ; SUMMARY ; LANGUAGE ; LINK ; PICTURE ; AMOUNT ; AVAILABLE
    
    // Prepeard-Statements for Inserting new Values
    private final HashMap<Connection, PreparedStatement> insertBookStmts = new HashMap<>();
    private final String insertBookSqlStr = "INSERT INTO Book(isbn, titel, author, "
            + "publication, summary, language, linkToAmazon, "
            + "amount, available) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    
    public void insertBook(Book book) throws Exception {
        Connection conn = conPool.getConnection();
        PreparedStatement insert = insertBookStmts.get(conn);
        if (insert == null) {
            insert = conn.prepareStatement(insertBookSqlStr);
            insertBookStmts.put(conn, insert);
        }
        insert.setString(1, book.getIsbn());
        insert.setString(2, book.getTitle());
        insert.setString(3, book.getAuthor());
        insert.setDate(4, (Date) book.getPublication());
        insert.setString(5, book.getSummary());
        insert.setString(6, book.getLanguage());
      //  insert.setBlob(7, null);
        insert.setString(7, book.getAmazonlink());
      //  insert.setInt(9, null);
        insert.setInt(8, book.getAmount());
        insert.setInt(9, book.getAvailable());
        insert.executeUpdate();

        conPool.releaseConnection(conn);
    }

    public static void main(String[] args) {

        try {
            DBAccess dba = DBAccess.getInstance();
//            Book bNew = new Book("Hi", "01234", 12.3, );
//            dba.insertBook(bNew);

            LinkedList<Book> list = dba.getBookFromList("");

            for (Book book : list) {
                System.out.println(book);
                dba.insertBook(book);
            }

//            LinkedList<Book> liste = dba.getAllBooks();
//            for (Book book : liste) {
//                System.out.println(book);
//            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

}
