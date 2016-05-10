/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.Date;

/**
 * 
 * @author pruta_000
 */

 // ISBN ; TITEL ; AUTHOR ; PUBLISHER ; PUBLICATION ; SUMMARY ; LANGUAGE ; LINK ; PICTURE ; AVAILABLE
public class Book {

    private String isbn;
    private String title;
    private String author; 
    private String publisher;
    private Date publication;
    private String summary;
    private String language;
    private String amazonlink;
    private String picture;
    private int amount;
    private int available;
    
    public Book() {
       
    }

    public Book(String isbn, String title, String author, String publisher, Date publication, String summary, String language, String amazonlink, String picture, int amount, int available) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publication = publication;
        this.summary = summary;
        this.language = language;
        this.amazonlink = amazonlink;
        this.picture = picture;
        this.amount = amount;
        this.available = available;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAmazonlink() {
        return amazonlink;
    }

    public void setAmazonlink(String amazonlink) {
        this.amazonlink = amazonlink;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    

}
