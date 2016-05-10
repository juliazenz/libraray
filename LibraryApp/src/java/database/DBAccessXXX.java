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
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Für Zugriff auf Datenbank
 *
 * @author Julia
 */
public class DBAccessXXX {

    // class implementet as Singleton
    private static DBAccessXXX theInstance = null;
    private DBConnectionPool conPool = null;

    public static DBAccessXXX getInstance() throws ClassNotFoundException {
        if (theInstance == null) {
            theInstance = new DBAccessXXX();
        }
        return theInstance;
    }

    private DBAccessXXX() throws ClassNotFoundException {
        conPool = DBConnectionPool.getInstance();
    }

    // Prepeard-Statements for Inserting new Values
    private final HashMap<Connection, PreparedStatement> insertBookStmts = new HashMap<>();
    private final String insertBookSqlStr = "INSERT INTO Book(title, isbn, "
            + "publication, summary, language, picture, linkToAmazon, amount, available) "
            + "VALUES(?,?, ?, ?, ?, ?)";

    private final HashMap<Connection, PreparedStatement> insertBookAuthorStmts = new HashMap<>();
    private final String insertBookAuthorSqlStr = "INSERT INTO BookAuthor(isbn) "
            + "VALUES(?)";

    private final HashMap<Connection, PreparedStatement> insertAuthorStmts = new HashMap<>();
    private final String insertAuthorSqlStr = "INSERT INTO Author(firstname, lastname) "
            + "VALUES(?, ?)";

    public LinkedList<Book> getAllBooks() throws Exception {
        LinkedList<Book> buecherListe = new LinkedList<>();
        Connection con = conPool.getConnection();
        Statement stat = con.createStatement();
        String sqlString = "SELECT title, publication, summary, language, p.p_name"
                + "picture, linkToAmazon, b.isbn, a.firstname, a.lastname "
                + "FROM BookAuthor ba INNER JOIN Book b ON(b.isbn = ba.isbn)"
                + "INNER JOIN Author a ON(ba.author_id = a.author_id)"
                + "INNER JOIN Publisher p ON (b.publisher_id = p.publisher_id);";
        ResultSet rs = stat.executeQuery(sqlString);
        while (rs.next()) {
            String titel = rs.getString("title");
            String isbn = rs.getString("isbn");
            Date publication = rs.getDate("publication");
            String publisher = rs.getString("p_name");
            String summary = rs.getString("summary");
            String language = rs.getString("language");
            Blob picture = rs.getBlob("picture");
            String linkToAmazon = rs.getString("linkToAmazon");
            String author = rs.getString("firstname") + " " + rs.getString("lastname");
            buecherListe.add(new Book());
        }
        conPool.releaseConnection(con);
        return buecherListe;
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
            Date publication = Date.valueOf(parts[4]);

            String summary = parts[5];
            String language = parts[6];
            String linkToAmazon = parts[7];
            String picture = parts[8];
            int amount = Integer.parseInt(parts[9]);
            int available = Integer.parseInt(parts[10]);      
            
            Book b = new Book(isbn, titel, author, publisher, publication, summary, language, linkToAmazon, picture, amount, available);
            buecherListe.add(b);
            insertBook(b);
        }

        br.close();
        fr.close();

        return buecherListe;
    }
//
//    public LinkedList<Book> getSortList(String sort) throws Exception {
//        System.out.println("in sortList");
//        LinkedList<Book> sortList = new LinkedList<>();
//        Connection con = conPool.getConnection();
//        Statement stat = con.createStatement();
//        String sqlString = "SELECT   b.title, a.name, b.price "
//                + "FROM     booksauthors ba INNER JOIN books b ON (ba.isbn = b.isbn) "
//                + "	  INNER JOIN authors a ON (ba.author_id = a.id) ";
//        switch (sort) {
//            case "titel":
//                sqlString += "ORDER BY b.title;";
//                break;
//            case "author":
//                sqlString += "ORDER BY a.name;";
//                break;
//            case "price":
//                sqlString += "ORDER BY b.price;";
//                break;
//            default:
//                sqlString += ";";
//                break;
//        }
//        System.out.println(sqlString);
//        ResultSet rs = stat.executeQuery(sqlString);
//        while (rs.next()) {
//            String titel = rs.getString("title");
//            String autor = rs.getString("name");
//            double preis = rs.getDouble("price");
//
//       //     sortList.add(new Book(titel, preis, autor));
//        }
//        System.out.println(sortList.get(0));
//        conPool.releaseConnection(con);
//        return sortList;
//    }
//
//    public LinkedList<Book> getSearchTitel(String search, String sortStr) throws Exception {
//        LinkedList<Book> alleBuecher = getSortList(sortStr);
//        LinkedList<Book> filter = new LinkedList<>();
//        for (Book book : alleBuecher) {
//            if (book.getTitle().toLowerCase().contains(search.toLowerCase())) {
//                filter.add(book);
//            }
//        }
//        return filter;
//    }
//
//    public LinkedList<Book> getSearchAuthor(String search, String sortStr) throws Exception {
//        LinkedList<Book> alleBuecher = getSortList(sortStr);
//        LinkedList<Book> filter = new LinkedList<>();
//        for (Book book : alleBuecher) {
////            if (book.getAutor().toLowerCase().contains(search.toLowerCase())) {
////                filter.add(book);
////            }
//        }
//        return filter;
//    }
//

    // title, isbn, publication, summary, language, picture, linkToAmazon
    public void insertBook(Book book) throws Exception {
        Connection conn = conPool.getConnection();
        PreparedStatement insert = insertBookStmts.get(conn);
        if (insert == null) {
            insert = conn.prepareStatement(insertBookSqlStr);
            insertBookStmts.put(conn, insert);
        }
        insert.setString(1, book.getTitle());
        insert.setString(2, book.getIsbn());
        insert.setDate(3, (Date) book.getPublication());
        insert.setString(4, book.getSummary());
        insert.setString(5, book.getLanguage());
        Blob blob = conn.createBlob();
        blob.setBytes(1, book.getPicture().getBytes());
        insert.setBlob(6, blob);
        insert.setString(7, book.getAmazonlink());
        insert.setInt(8, book.getAmount());
        insert.setInt(9, book.getAvailable());
        insert.executeUpdate();

        PreparedStatement insertBA = insertBookAuthorStmts.get(conn);
        if (insertBA == null) {
            insertBA = conn.prepareStatement(insertBookAuthorSqlStr);
            insertBookAuthorStmts.put(conn, insertBA);
        }
        insertBA.setString(1, book.getIsbn());
        insertBA.executeUpdate();
        
        PreparedStatement insertAuthor = insertAuthorStmts.get(conn);
        if (insertAuthor == null) {
            insertAuthor = conn.prepareStatement(insertAuthorSqlStr);
            insertAuthorStmts.put(conn, insertAuthor);
        }
        String[] parts = book.getAuthor().split(" ");
        insertAuthor.setString(1, parts[0]);
        insertAuthor.setString(2, parts[1]);
        insertAuthor.executeUpdate();
        conPool.releaseConnection(conn);
    }

    public static void main(String[] args) {

        try {
            DBAccessXXX dba = DBAccessXXX.getInstance();
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
