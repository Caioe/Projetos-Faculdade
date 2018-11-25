package senacpi.hospitaltades.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yury Cavalcante
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    
    public static Connection createConnection() {
        String url = "jdbc:mysql://localhost:3306/hospital_tades";
        
        try {
            Class.forName("senacpi.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection (url, "root", "123456");
            System.out.println("Conectado com sucesso");
            return conn;
        }
        catch (SQLException e) {
            System.out.println("Erro: " + e);
        }
        
        catch (ClassNotFoundException e) {
            System.out.println("Erro 2: " + e);
        }
        
        return null;
    }
    
}
