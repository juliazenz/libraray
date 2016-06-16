<%-- 
    Document   : reserveBook
    Created on : 02.02.2016, 10:25:13
    Author     : Julia
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.LinkedList"%>
<%@page import="Beans.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/infonovaStyleDetail.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="css/cssmenu/styles.css">
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <script src="css/cssmenu/script.js"></script>
        <script type="text/javascript" src="javascript/extern.js"></script>
        <title>Reserve Book</title>
    </head>
    <body>  
        <%! Book book = new Book();
            LinkedList<String> empList = new LinkedList<>();%>      

        <jsp:include page="/css/cssmenu/index.html"></jsp:include>

        <% String isbn = request.getParameter("isbn");
            empList = (LinkedList<String>) application.getAttribute("empList");
            LinkedList<Book> booklist = (LinkedList<Book>) application.getAttribute("bookList");
            if (booklist.size() > 0) {
                for (Book b : booklist) {
                    if (b.getIsbn().equals(isbn)) {
                        book = b;
                    }
                }
            }
        %>
    <center>
        <form action="DetailServlet" method="POST" onsubmit="showReserve(this)">
        <div id="container">
                <h1 style='font-size:16pt;'>Reserve Book "<%=book.getTitle()%>"</h1>
                <input type="hidden" name="book" value="<%=book.getIsbn()%>"/>
                <table border="0">
                    <tbody>
                        <tr>
                            <td><h3>from <%=book.getAuthor()%> (Author)</h3></td>
                        </tr>
                        <tr><td><b>Date of Reservation: </b>
                                <input type="text" name="dateOfReservation" value="<%=LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)%>" /></td></tr>
                        <tr>
                            <td></td>
                        </tr>
                        <tr>
                            <td><b>Select your Name: </b> <select name="nameEMP">
                                    <% if (empList != null && !empList.isEmpty()) {
                                            for (String emp : empList) {%>
                                    <option><%=emp%></option>
                                    <%}
                                        }%>
                                </select></td>
                        </tr>
                    </tbody>
                </table><br><br>
                <input type='submit' value="reserve">
        </div>
        </form>
    </center>
</body>
</html>
