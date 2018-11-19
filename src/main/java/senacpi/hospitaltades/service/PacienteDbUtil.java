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
import senacpi.hospitaltades.model.Paciente;

/**
 *
 * @author Yury Cavalcante
 */
public class PacienteDbUtil {

    private DataSource dataSource;

    public PacienteDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Paciente> getPacientes() throws Exception {

        List<Paciente> pacientes = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            myConn = dataSource.getConnection();

            String sql = "select * from paciente where ativo is true";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {

                int id = myRs.getInt("id");
                String nome = myRs.getString("nome");
                String sobrenome = myRs.getString("sobrenome");
                String dataNasc = myRs.getString("dataNasc");
                String cpf = myRs.getString("cpf");
                String sexo = myRs.getString("sexo");
                String contato = myRs.getString("contato");
                String email = myRs.getString("email");
                boolean ativo = myRs.getBoolean("ativo");

                Paciente paciente = new Paciente(id, nome, sobrenome, dataNasc, cpf, sexo, contato, email, ativo);

                pacientes.add(paciente);
            }

            return pacientes;

        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void addPatient(Paciente paciente) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            myConn = dataSource.getConnection();

            // Criando um SQL para inserir no banco
            String sql = "insert into paciente"
                    + "(nome, sobrenome, dataNasc, cpf, sexo, contato, email, ativo)"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // Atribuindo os parametros para o Paciente
            myStmt.setString(1, paciente.getNome());
            myStmt.setString(2, paciente.getSobrenome());
            myStmt.setString(3, paciente.getDataNasc());
            myStmt.setString(4, paciente.getCpf());
            myStmt.setString(5, paciente.getSexo());
            myStmt.setString(6, paciente.getContato());
            myStmt.setString(7, paciente.getEmail());
            myStmt.setBoolean(8, paciente.isAtivo());

            // Executando o comando SQL
            myStmt.execute();

        } finally {
            // Limpando os objetos JDBC
            close(myConn, myStmt, null);
        }
    }

    public Paciente getPaciente(String thePacienteId) throws Exception {

        Paciente paciente = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int pacienteId;

        try {

            pacienteId = Integer.parseInt(thePacienteId);

            myConn = dataSource.getConnection();

            String sql = "select * from paciente where id=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, pacienteId);

            System.out.println(pacienteId);

            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                String nome = myRs.getString("nome");
                String sobrenome = myRs.getString("sobrenome");
                String dataNasc = myRs.getString("dataNasc");
                String cpf = myRs.getString("cpf");
                String sexo = myRs.getString("sexo");
                String contato = myRs.getString("contato");
                String email = myRs.getString("email");
                boolean ativo = myRs.getBoolean("ativo");

                paciente = new Paciente(pacienteId, nome, sobrenome, dataNasc, cpf, sexo, contato, email, ativo);

            } else {
                throw new Exception("Não pode achar o ID do estudante: " + pacienteId);
            }
            return paciente;
        } finally {
            close(myConn, myStmt, myRs);
        }

    }

    public void updatePaciente(Paciente paciente) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "update paciente "
                    + "set nome=?, sobrenome=?, dataNasc=?, cpf=?, sexo=?, contato=?, email=?, ativo=? "
                    + "where id=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, paciente.getNome());
            myStmt.setString(2, paciente.getSobrenome());
            myStmt.setString(3, paciente.getDataNasc());
            myStmt.setString(4, paciente.getCpf());
            myStmt.setString(5, paciente.getSexo());
            myStmt.setString(6, paciente.getContato());
            myStmt.setString(7, paciente.getEmail());
            myStmt.setBoolean(8, paciente.isAtivo());
            myStmt.setInt(9, paciente.getId());

            myStmt.execute();

        } finally {

            close(myConn, myStmt, null);

        }

    }
    
    public void deletePatient(String thePacienteId) throws Exception {
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        try {
         
            int pacienteId = Integer.parseInt(thePacienteId);
            
            myConn = dataSource.getConnection();

            String sql = "update paciente "
                    + "set ativo=false "
                    + "where id=?";
            
            myStmt = myConn.prepareStatement(sql);
            
            myStmt.setInt(1, pacienteId);
            
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
