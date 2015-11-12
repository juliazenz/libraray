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

    private LinkedList<Book> bookList = new LinkedList<>();

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
            RequestDispatcher rd = request.getRequestDispatcher("/html/overview.html");
            rd.include(request, response);

            out.println("<input type=\"text\" value=\"suchen\">");
            out.println("<input type=\"button\" value=\"los\">\n</form>\n</div>");
            
            for (Book b : bookList) {
                 
                            
                
                
                out.println("<div class='row'><div id='pic'>");
                out.println("<img src='res/"+b.getPicture()+"' height='300' width='200'></div>");
                out.println("<div id='book'><p><b>"+b.getTitle()+"</b> ("+b.getAuthor()+")</p>");
                if (b.isAvailable()) {
                    out.println("<p>verf&uuml;gbar</p></div>");
                } else {
                    out.println("<p>nicht verf&uuml;gbar</p></div>");
                }
                out.println("<div id='rating'><p>***</p></div>");
                out.println("<div id='detail'><form><input type='button' value='");
                out.println(b.isAvailable() ? "ausleihen'>" : "reservieren'>");
                out.println("</form></div>");
                out.println("</div>");

            }
            out.println("</div></body></html>");

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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); //To change body of generated methods, choose Tools | Templates.
        try {
            loadData();
        } catch (IOException ex) {
            Logger.getLogger(OverviewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
            Book b = new Book(feld[0], feld[1], feld[2], available);
            bookList.add(b);
        }

        this.getServletContext().setAttribute("bookList", bookList);
        br.close();
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

}
