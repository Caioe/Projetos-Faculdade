/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senacpi.hospitaltades.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javax.sql.DataSource;
import senacpi.hospitaltades.model.Remedio;

/**
 *
 * @author Yury Cavalcante
 */
public class RemedioDbUtil {

    private DataSource dataSource;

    public RemedioDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Remedio> getRemedios() throws Exception {

        List<Remedio> remedios = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            myConn = dataSource.getConnection();

            String sql = "select * from remedio where ativo is true";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {

                int idRemedio = myRs.getInt("idRemedio");
                String nome = myRs.getString("nome");
                String quantidade = myRs.getString("quantidade");
                boolean ativo = myRs.getBoolean("ativo");

                Remedio remedio = new Remedio(idRemedio, nome, quantidade, ativo);

                remedios.add(remedio);
            }

            return remedios;

        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void addRemedy(Remedio remedio) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            myConn = dataSource.getConnection();

            // Criando um SQL para inserir no banco
            String sql = "insert into remedio"
                    + "(nome, quantidade, ativo)"
                    + "values (?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // Atribuindo os parametros para o Paciente
            myStmt.setString(1, remedio.getNome());
            myStmt.setString(2, remedio.getQuantidade());
            myStmt.setBoolean(3, remedio.isAtivo());

            // Executando o comando SQL
            myStmt.execute();

        } finally {
            // Limpando os objetos JDBC
            close(myConn, myStmt, null);
        }
    }

    public Remedio getRemedio(String theRemedioId) throws Exception {

        Remedio remedio = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int remedioId;

        try {

            remedioId = Integer.parseInt(theRemedioId);

            myConn = dataSource.getConnection();

            String sql = "select * from remedio where idRemedio=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, remedioId);

            System.out.println(remedioId);

            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                String nome = myRs.getString("nome");
                String quantidade = myRs.getString("quantidade");
                boolean ativo = myRs.getBoolean("ativo");

                remedio = new Remedio(remedioId, nome, quantidade, ativo);

            } else {
                throw new Exception("Não pode achar o ID do médico: " + remedioId);
            }
            return remedio;
        } finally {
            close(myConn, myStmt, myRs);
        }

    }

    public void updateRemedio(Remedio remedio) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "update remedio "
                    + "set nome=?, quantidade=?, ativo=? "
                    + "where idRemedio=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, remedio.getNome());
            myStmt.setString(2, remedio.getQuantidade());
            myStmt.setBoolean(3, remedio.isAtivo());
            myStmt.setInt(4, remedio.getIdRemedio());

            myStmt.execute();

        } finally {

            close(myConn, myStmt, null);

        }

    }
    
    public void deleteRemedy(String theRemedioId) throws Exception {
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        try {
         
            int remedioId = Integer.parseInt(theRemedioId);
            
            myConn = dataSource.getConnection();

            String sql = "update remedio "
                    + "set ativo=false "
                    + "where idRemedio=?";
            
            myStmt = myConn.prepareStatement(sql);
            
            myStmt.setInt(1, remedioId);
            
            myStmt.execute();
        }
        
        finally {
            close(myConn, myStmt, null);
        }
    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
