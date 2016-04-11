/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Beans.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
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

    // Prepeard-Statements for Inserting new Values
    private final HashMap<Connection, PreparedStatement> insertBookStmts = new HashMap<>();
    private final String insertBookSqlStr = "INSERT INTO Book(title, "
            + "publication, summary, language, picture, linkToAmazon) "
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
        String sqlString = "SELECT title, publication, summary, language, "
                + "picture, linkToAmazon, b.isbn, a.firstname, a.lastname "
                + "FROM BookAuthor ba INNER JOIN Book b ON(b.isbn = ba.isbn)"
                + "INNER JOIN Author a ON(ba.author_id = a.author_id);";
        ResultSet rs = stat.executeQuery(sqlString);
        while (rs.next()) {
            String titel = rs.getString("title");
            String isbn = rs.getString("isbn");
            Date publication = rs.getDate("publication");
            String summary = rs.getString("summary");
            String language = rs.getString("language");
            String picture = rs.getString("picture");
            String linkToAmazon = rs.getString("linkToAmazon");
            String author = rs.getString("firstname") + " " + rs.getString("lastname");
            buecherListe.add(new Book(picture, titel, author, publication,
                    language, summary, linkToAmazon, isbn));
        }
        conPool.releaseConnection(con);
        return buecherListe;
    }

    public LinkedList<Book> getBookFromList() throws FileNotFoundException, IOException {
        LinkedList<Book> buecherListe = new LinkedList<>();
        String pfadListe = "C:\\Users\\Nico\\Google Drive\\4. Jahrgang\\POS\\library\\LibraryApp\\web\\res\\Book_list_20160311.csv";

        FileReader fr = new FileReader(pfadListe);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(";");
            
            String titel = parts[0];
            String isbn = "";
            Date publication = Date.valueOf(LocalDate.now());
                  
            String summary = parts[4];
            String language = parts[5];
            String picture = parts[6];
            String linkToAmazon = parts[7];
            String author = parts[2];
            buecherListe.add(new Book(picture, titel, author, publication,
                    language, summary, linkToAmazon, isbn));

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
        //insert.setString(2, book.getIsbn());
        insert.setDate(2, (Date) book.getPublication());
        insert.setString(3, book.getSummary());
        insert.setString(4, book.getLanguage());
        insert.setString(5, book.getPicture());
        insert.setString(6, book.getAmazonlink());
        insert.executeUpdate();

//        PreparedStatement insertBA = insertBookAuthorStmts.get(conn);
//        if (insertBA == null) {
//            insertBA = conn.prepareStatement(insertBookAuthorSqlStr);
//            insertBookAuthorStmts.put(conn, insertBA);
//        }
//        // insertBA.setInt(1, ????)
//        insertBA.setString(1, book.getIsbn());
//        insertBA.executeUpdate();

        PreparedStatement insertAuthor = insertAuthorStmts.get(conn);
        if (insertAuthor == null) {
            insertAuthor = conn.prepareStatement(insertAuthorSqlStr);
            insertAuthorStmts.put(conn, insertAuthor);
        }
        // insertBA.setInt(1, ????)
        String[] parts = book.getAuthor().split(" ");
        insertAuthor.setString(1, parts[0]);
        insertAuthor.setString(2, parts[1]);
        insertAuthor.executeUpdate();
        conPool.releaseConnection(conn);
    }

    public static void main(String[] args) {

        try {
            DBAccess dba = DBAccess.getInstance();
//            Book bNew = new Book("Hi", "01234", 12.3, );
//            dba.insertBook(bNew);

            LinkedList<Book> list = dba.getBookFromList();
            
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
