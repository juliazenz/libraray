/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Beans.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    // Prepeared-Statemet -> nicht als Instanzvariable, da es sonst für mehrere User nicht funktioniert
    // private PreparedStatement insertBookStmt = null;
    private final HashMap<Connection, PreparedStatement> insertBookStmts = new HashMap<>();
    private final String insertBookSqlStr = "INSERT INTO books(title, isbn, publisher_id, url, price) VALUES(?, ?, ?, ?, ?)";

    public LinkedList<Book> getAllBooks() throws Exception {
        LinkedList<Book> buecherListe = new LinkedList<>();
        Connection con = conPool.getConnection();
        Statement stat = con.createStatement();
        String sqlString = "SELECT * FROM Book;";
        // wie Tabelle
        ResultSet rs = stat.executeQuery(sqlString);
        while (rs.next()) {
            String titel = rs.getString("title"); // besser Spaltenname
            String isbn = rs.getString("isbn");
            String publisher = rs.getString("publisher_id");
            String url = rs.getString("url");
            double preis = rs.getDouble("price");

  //          buecherListe.add(new Book(titel, isbn, publisher, url, preis));
        }
        conPool.releaseConnection(con);
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
//    // prepeared Statements
//    public void insertBook(Book book) throws Exception {
//        Connection con = conPool.getConnection();
//
////        if(insertBookStmt == null){
////            insertBookStmt = con.prepareStatement(insertBookSqlStr);
////        }
//        PreparedStatement insertBookStmt = con.prepareStatement(insertBookSqlStr);
////        insertBookStmt.setString(1, book.getTitle());
////        insertBookStmt.setString(2, book.getIsbn());
////        insertBookStmt.setString(3, book.getPublisher());
////        insertBookStmt.setString(4, book.getUrl());
////        insertBookStmt.setDouble(5, book.getPreis());
//
//        insertBookStmt.executeUpdate();
//
//        conPool.releaseConnection(con);
//    }
//
//    public void insertBool(Book book) throws Exception{
//        Connection conn =conPool.getConnection();
//        PreparedStatement isert = insertBookStmts.get(conn);
//        if(isert == null){
//            isert = conn.prepareStatement(insertBookSqlStr);
//            insertBookStmts.put(conn, isert);
//        }
////        isert.setString(1, book.getTitle());
////        isert.setString(2, book.getIsbn());
////        isert.setString(3, book.getTitle());
////        isert.setString(4, book.getIsbn());
////        isert.setString(5, book.getTitle());
//        isert.executeUpdate();
//        conPool.releaseConnection(conn);
//    }
    
    public static void main(String[] args) {

        try {
            DBAccess dba = DBAccess.getInstance();
//            Book bNew = new Book("Hi", "01234", 12.3, );
//            dba.insertBook(bNew);

            LinkedList<Book> liste = dba.getAllBooks();
            for (Book book : liste) {
                System.out.println(book);
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        
        
    }
    
    
}
