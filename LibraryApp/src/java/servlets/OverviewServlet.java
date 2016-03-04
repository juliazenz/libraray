/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Beans.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Julia
 */
@WebServlet(name = "OverviewServlet", urlPatterns = {"/OverviewServlet"})
public class OverviewServlet extends HttpServlet {

    private final LinkedList<Book> bookList = new LinkedList<>();
    private final LinkedList<String> empList = new LinkedList<>();

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
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/overview.jsp");
            rd.include(request, response);
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

        if (request.getSession().getAttribute("filterList") == null) {
            LinkedList<Book> filterList = (LinkedList<Book>) bookList.clone();
            request.getSession().setAttribute("filterList", filterList);
        }
        processRequest(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); //To change body of generated methods, choose Tools | Templates.
        try {
            loadData();

        } catch (IOException ex) {
            Logger.getLogger(OverviewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        String filterStr = request.getParameter("search");
        LinkedList<Book> filterList = (LinkedList<Book>) request.getSession().getAttribute("filterList");

        if (!filterStr.equals("") || filterStr != null) {
            filterList.clear();
            for (Book b : bookList) {
                if (b.getTitle().toLowerCase().contains(filterStr.toLowerCase()) || b.getAuthor().toLowerCase().contains(filterStr.toLowerCase())) {
                    filterList.add(b);
                }
            }
            request.getSession().setAttribute("search", filterStr);
        }
        request.getSession().setAttribute("filterList", filterList);
        processRequest(request, response);
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

    public void loadData() throws FileNotFoundException, IOException {
        String path = this.getServletContext().getRealPath("/res" + File.separator + "buchdaten.csv");
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String feld[] = null;
        boolean available;
        while ((line = br.readLine()) != null) {
            feld = line.split(";");
            //String picture, String title, String author, boolean available
            available = false;
            if (feld[3].equals("true")) {
                available = true;
            }

            Book b = new Book(feld[0], feld[1], feld[2], available, feld[4], feld[5], Integer.parseInt(feld[6]), feld[7]);
            bookList.add(b);
        }
        br.close();

        // Employees einlesen
        path = this.getServletContext().getRealPath("/res" + File.separator + "Mitarbeiter_Liste_20151119.txt");
        fr = new FileReader(path);
        br = new BufferedReader(fr);
        while ((line = br.readLine()) != null) {
            empList.add(line);
        }
        this.getServletContext().setAttribute("bookList", bookList);
        this.getServletContext().setAttribute("empList", empList);

    }
}
