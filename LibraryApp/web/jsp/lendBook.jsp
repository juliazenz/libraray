<%-- 
    Document   : lendBook
    Created on : 11.02.2016, 14:11:26
    Author     : Celina
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
        <title>Lend out Book</title>
    </head>
    <body>  
        <%! Book book = new Book();
            LinkedList<String> empList = new LinkedList<>();%>      

        <jsp:include page="/css/cssmenu/index.html"></jsp:include>

        <% String bookID = request.getParameter("bookID");
            empList = (LinkedList<String>) application.getAttribute("empList");
            LinkedList<Book> booklist = (LinkedList<Book>) application.getAttribute("bookList");
            if (booklist.size() > 0) {
                for (Book b : booklist) {
                    if (b.getIsbn().equals(bookID)) {
                        book = b;
                    }
                }
            }
        %>

       <!-- <h1><% booklist.size();%></h1> -->
    <center>
        <div id="container">
            <h1 style='font-size:16pt;'>Lend out Book <%=book.getTitle()%></h1>
            <table border="0">
                <tbody>

                    <tr>
                        <td><h3>from <%=book.getAuthor()%> (Author)</h3></td>
                    </tr>
                    <tr><td><b>Date of Reservation: </b>
                            <%=LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)%></td></tr>
                    <tr>
                        <td><b>Deadline: </b>
                            <input type="text" name="deadline" value="<%=LocalDate.now().plusMonths(3).format(DateTimeFormatter.ISO_LOCAL_DATE)%>"/></td>
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
            <input type='button' value="lend out" onclick="showLend(this)">
        </div>
    </center>
</body>
</html>
