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
import senacpi.hospitaltades.model.Atendente;

/**
 *
 * @author Yury Cavalcante
 */
public class AtendenteDbUtil {

    private DataSource dataSource;

    public AtendenteDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Atendente> getAtendentes() throws Exception {

        List<Atendente> atendentes = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            myConn = dataSource.getConnection();

            String sql = "select * from atendente where ativo is true";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {

                int idAtendente = myRs.getInt("idAtendente");
                String nome = myRs.getString("nome");
                String sobrenome = myRs.getString("sobrenome");
                String cpf = myRs.getString("cpf");
                String sexo = myRs.getString("sexo");
                boolean ativo = myRs.getBoolean("ativo");

                Atendente atendente = new Atendente(idAtendente, nome, sobrenome, cpf, sexo, ativo);

                atendentes.add(atendente);
            }

            return atendentes;

        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void addAttendant(Atendente atendente) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            myConn = dataSource.getConnection();

            // Criando um SQL para inserir no banco
            String sql = "insert into atendente "
                    + "(nome, sobrenome, cpf, sexo, ativo)"
                    + "values (?, ?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // Atribuindo os parametros para o Paciente
            myStmt.setString(1, atendente.getNome());
            myStmt.setString(2, atendente.getSobrenome());
            myStmt.setString(3, atendente.getCpf());
            myStmt.setString(4, atendente.getSexo());
            myStmt.setBoolean(5, atendente.isAtivo());

            // Executando o comando SQL
            myStmt.execute();

        } finally {
            // Limpando os objetos JDBC
            close(myConn, myStmt, null);
        }
    }

    public Atendente getAtendente(String theAtendenteId) throws Exception {

        Atendente atendente = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int atendenteId;

        try {

            atendenteId = Integer.parseInt(theAtendenteId);

            myConn = dataSource.getConnection();

            String sql = "select * from atendente where idAtendente=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, atendenteId);

            System.out.println(atendenteId);

            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                String nome = myRs.getString("nome");
                String sobrenome = myRs.getString("sobrenome");
                String cpf = myRs.getString("cpf");
                String sexo = myRs.getString("sexo");
                boolean ativo = myRs.getBoolean("ativo");

                atendente = new Atendente(atendenteId, nome, sobrenome, cpf, sexo, ativo);

            } else {
                throw new Exception("Não pode achar o ID do médico: " + atendenteId);
            }
            return atendente;
        } finally {
            close(myConn, myStmt, myRs);
        }

    }

    public void updateAtendente(Atendente atendente) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "update atendente "
                    + "set nome=?, sobrenome=?, cpf=?, sexo=?, ativo=? "
                    + "where idAtendente=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, atendente.getNome());
            myStmt.setString(2, atendente.getSobrenome());
            myStmt.setString(3, atendente.getCpf());
            myStmt.setString(4, atendente.getSexo());
            myStmt.setBoolean(5, atendente.isAtivo());
            myStmt.setInt(6, atendente.getIdAtendente());

            myStmt.execute();

        } finally {

            close(myConn, myStmt, null);

        }

    }
    
    public void deleteAttendant(String theAtendenteId) throws Exception {
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        try {
         
            int atendentId = Integer.parseInt(theAtendenteId);
            
            myConn = dataSource.getConnection();

            String sql = "update atendente "
                    + "set ativo=false "
                    + "where idAtendente=?";
            
            myStmt = myConn.prepareStatement(sql);
            
            myStmt.setInt(1, atendentId);
            
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
