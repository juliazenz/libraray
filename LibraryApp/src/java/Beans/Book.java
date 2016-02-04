/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author pruta_000
 */
public class Book {
    private String picture;
    private String title;
    private String author; 
    private boolean available;
    private int yearOfPublication;
    private String language;
    private String summary;
    private String amazonlink;
    private String bookID = java.util.UUID.randomUUID().toString();

    public Book() {
       
    }
    
    public Book(String picture, String title, String author, boolean available, 
            String language, String summary, int yearOfPublication, String amazon) {
        this.picture = picture;
        this.title = title;
        this.author = author;
        this.available = available;
        this.language = language;
        this.summary = summary;
        this.yearOfPublication = yearOfPublication;
        this.amazonlink = amazon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }
   
    public String getAmazonlink() {
        return amazonlink;
    }

    public void setAmazonlink(String amazonlink) {
        this.amazonlink = amazonlink;
    }
    
}
