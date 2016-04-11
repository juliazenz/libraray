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
public class Book {
    private String picture;
    private String title;
    private String author; 
    private Date publication;
    private String language;
    private String summary;
    private String amazonlink;
    private String isbn;

    public Book() {
       
    }

    public Book(String picture, String title, String author, Date publication, String language, String summary, String amazonlink, String isbn) {
        this.picture = picture;
        this.title = title;
        this.author = author;
        this.publication = publication;
        this.language = language;
        this.summary = summary;
        this.amazonlink = amazonlink;
        this.isbn = isbn;
    }

    public Book(String title, Date publication, String language, String isbn) {
        this.title = title;
        this.publication = publication;
        this.language = language;
        this.isbn = isbn;
    }

    public Book(String picture, String title, Date publication, String language, String summary, String amazonlink, String isbn) {
        this.picture = picture;
        this.title = title;
        this.publication = publication;
        this.language = language;
        this.summary = summary;
        this.amazonlink = amazonlink;
        this.isbn = isbn;
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

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAmazonlink() {
        return amazonlink;
    }

    public void setAmazonlink(String amazonlink) {
        this.amazonlink = amazonlink;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
}
