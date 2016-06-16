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
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Julia
 */
@WebServlet(name = "DetailServlet", urlPatterns = {"/DetailServlet"})
public class DetailServlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            if (request.getParameter("action") == null) {
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/detail.jsp");
                rd.forward(request, response);
            }
            else if(request.getParameter("action").equals("reserve")){
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/reserveBook.jsp");
                rd.forward(request, response);
            }
             else if(request.getParameter("action").equals("lend out")){
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/lendBook.jsp");
                rd.forward(request, response);
            }
           // LinkedList<Book> bookList = (LinkedList<Book>) request.getServletContext().getAttribute("bookList");
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
        if(request.getParameter("dateOfLend") != null){
            try {
                String isbn = request.getParameter("book");
                String nameEMP = request.getParameter("nameEMP");
                DBAccess dba = DBAccess.getInstance();
                dba.lendBook(isbn, nameEMP);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DetailServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(request.getParameter("dateOfReservation") != null){
            String isbn = request.getParameter("book");
                String nameEMP = request.getParameter("nameEMP");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/jsp/overview.jsp");
                rd.forward(request, response);
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
