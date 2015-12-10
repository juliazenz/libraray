<%-- 
    Document   : overview
    Created on : 03.12.2015, 15:07:26
    Author     : Julia
--%>

<%@page import="java.util.TreeMap"%>
<%@page import="Beans.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link href="css/infonovaStyleOverview.css" rel="stylesheet" type="text/css"/>
        <title>Book Overview</title>
    </head>
    <body>
        <%! TreeMap<String, Book> bookmap = new TreeMap<>();
            TreeMap<String, Book> filteredMap = new TreeMap<>();
            String searchString="";
        %>
        
        <div id="container">
            <div id="headLeft">
                <img id="logo" src="res/infonova.png">
            </div>
            <div id="headCenter">
                <h1>Library</h1>
            </div>
            <form action="OverviewServlet" method="post">
                <% searchString = request.getParameter("search") != null ? request.getParameter("search") : "";%>
                <div id="headRight">
                    <input type="text" name="search" value="<%=searchString%>"/>
                    <input type="submit" value="go" method="submit()" />
                </div>
            </form>
            <% bookmap = (TreeMap<String, Book>) request.getServletContext().getAttribute("bookmap");
            if(!searchString.equals("")){
                filteredMap = (TreeMap<String, Book>) session.getAttribute("filterList");
            }
            else{
                filteredMap = (TreeMap<String, Book>) bookmap.clone();
            }
            %>
        </div>
    </body>
</html>




