/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Beans.Book;
import database.DBAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Julia
 */
@WebServlet(name = "AddBookServlet", urlPatterns = {"/AddBookServlet"})
public class AddBookServlet extends HttpServlet {

    private DBAccess dba;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/addBook.jsp").forward(request, response);
        processRequest(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            dba = DBAccess.getInstance();
            if(request != null){
                if(request.getParameter("publication").equals("DD.MM.YYYY")){
                    request.setAttribute("error", "Insert publication date in the right form (DD.MM.YYYY)!");
                    request.getRequestDispatcher("/jsp/addBook.jsp").forward(request, response);
                }
                String title = request.getParameter("title");
                String isbn = request.getParameter("isbn");
                String author = request.getParameter("author");
                LocalDate publication = LocalDate.now();
                String publisher = request.getParameter("publisher");
                String language = request.getParameter("language");
                String amazonlink = request.getParameter("amazonlink");
                String summary = request.getParameter("summary");
                String picture = request.getParameter("picture");
                int anzBooks = Integer.parseInt(request.getParameter("exemplare"));
                
                if(title.equals("") || title == null){
                    request.setAttribute("error", "Insert title!");
                    request.getRequestDispatcher("/jsp/addBook.jsp").forward(request, response);
                }
                if(isbn.equals("") || isbn == null){
                    request.setAttribute("error", "Insert ISBN!");
                    request.getRequestDispatcher("/jsp/addBook.jsp").forward(request, response);
                }
                if(author.equals("") || author == null){
                    request.setAttribute("error", "Insert author!");
                    request.getRequestDispatcher("/jsp/addBook.jsp").forward(request, response);
                }
                if(publication == null){
                    request.setAttribute("error", "Insert publication date in the right form (DD.MM.YYYY)!");
                    request.getRequestDispatcher("/jsp/addBook.jsp").forward(request, response);
                }
                if(publisher.equals("") || publisher == null){
                    request.setAttribute("error", "Insert publisher!");
                    request.getRequestDispatcher("/jsp/addBook.jsp").forward(request, response);
                }
                if(language.equals("") || language == null){
                    request.setAttribute("error", "Insert language!");
                    request.getRequestDispatcher("/jsp/addBook.jsp").forward(request, response);
                }
                if(anzBooks <= 0){
                    request.setAttribute("error", "Insert amount of Books!");
                    request.getRequestDispatcher("/jsp/addBook.jsp").forward(request, response);
                }
                
                Book b = new Book(isbn, title, author, publisher, new java.sql.Date(publication.toEpochDay()), summary, language, amazonlink, picture, anzBooks, anzBooks);
                dba.insertBook(b);
                
                 request.getRequestDispatcher("/jsp/overview.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddBookServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
