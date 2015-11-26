<%-- 
    Document   : detail
    Created on : 19.11.2015, 14:44:33
    Author     : Julia
--%>

<%@page import="java.util.LinkedList"%>
<%@page import="Beans.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/infonovaStyle.css" rel="stylesheet" type="text/css"/>
        <title>Detail</title>
    </head>
    <body>
        <%! Book book = new Book(); %>
        <% String bookID = request.getParameter("bookID");
           LinkedList<Book> booklist = (LinkedList<Book>) request.getServletContext().getAttribute("booklist");
           for(Book b : booklist){
               if(b.getBookID().equals(bookID)){
                   book = b;
               }
           }
        %>
        <table border="0">
            <tbody>
                <tr>
                    <td>Title:</td>
                    <td><%=book.getTitle()%></td>
                </tr>
                <tr>
                    <td>Author:</td>
                    <td><%=book.getAuthor()%></td>
                </tr>
                 <tr>
                    <td>Language:</td>
                    <td><%=book.getLanguage()%></td>
                </tr>
                 <tr>
                    <td>Summary:</td>
                    <td><%=book.getSummary()%></td>
                </tr>
            </tbody>
        </table>

    </body>
</html>
