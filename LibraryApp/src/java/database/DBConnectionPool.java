/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

/**
 * ConnectionPool damit jeder User eigene Connection hat
 * Connections sind entweder in der Liste oder bei Benutzern
 * @author Julia
 */
public class DBConnectionPool implements DBConfig{
    // nur Connections die nicht gebraucht werden
    private LinkedList<Connection> connections = new LinkedList<>();
    private final int MAX_CONNECTIONS = 150; // max 150 Connections zugelassen
    private int num_connections = 0; // aktuelle Anzahl der Verbindungen
    // Singelton
    // muss static sein, damit man kein Objekt der Klasse zum Zugriff braucht
    private static DBConnectionPool theInstance = null;
    
    public static DBConnectionPool getInstance() throws ClassNotFoundException{
        if(theInstance == null){
            theInstance = new DBConnectionPool();
        }
        return theInstance;
    }

    // von Außen kann kein Objekt erstellt werden
    private DBConnectionPool() throws ClassNotFoundException {
        // Driver manuell laden um Verbindung zu Datenbank aufzubauen
        Class.forName(DB_DRIVER);
    }
    
    public Connection getConnection() throws Exception{
        if(connections.isEmpty()){
            if(num_connections >= MAX_CONNECTIONS){ // alle Connections sind schon vergeben
                throw new Exception("Maximum Number of Users reached - try again later");
            }
            Connection con = DriverManager.getConnection(DB_URL+DB_NAME, DB_USER, DB_PASSWD);
            num_connections ++;
            return con;
        }
        // Connection wird zurückgegeben (1.) und löscht sie aus der Liste
        return connections.poll();
    }
    
    public void releaseConnection(Connection con){
        // fügt Element am Ende der Liste ein -> wird zurückgegeben (WICHTIG!)
        connections.offer(con);
        // nicht num_connection herunterzählen, weil man ja nicht noch mehr Connections erzeugen will
    }
}
