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
import senacpi.hospitaltades.model.Medico;
import senacpi.hospitaltades.model.Paciente;

/**
 *
 * @author Yury Cavalcante
 */
public class MedicoDbUtil {

    private DataSource dataSource;

    public MedicoDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Medico> getMedicos(String theCodFilial) throws Exception {

        List<Medico> medicos = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            myConn = dataSource.getConnection();

            String sql = "select * from medico where ativo is true and codFilial='" + theCodFilial + "'";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {

                int idMedico = myRs.getInt("idMedico");
                String nome = myRs.getString("nome");
                String sobrenome = myRs.getString("sobrenome");
                String cpf = myRs.getString("cpf");
                String sexo = myRs.getString("sexo");
                String crm = myRs.getString("crm");
                String codFilial = myRs.getString("codFilial");
                boolean ativo = myRs.getBoolean("ativo");

                Medico medico = new Medico(idMedico, nome, sobrenome, cpf, sexo, crm, codFilial, ativo);

                medicos.add(medico);
            }

            return medicos;

        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void addMedic(Medico medico) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            myConn = dataSource.getConnection();

            // Criando um SQL para inserir no banco
            String sql = "insert into medico"
                    + "(nome, sobrenome, cpf, sexo, crm, codFilial, ativo)"
                    + "values (?, ?, ?, ?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // Atribuindo os parametros para o Paciente
            myStmt.setString(1, medico.getNome());
            myStmt.setString(2, medico.getSobrenome());
            myStmt.setString(3, medico.getCpf());
            myStmt.setString(4, medico.getSexo());
            myStmt.setString(5, medico.getCrm());
            myStmt.setString(6, medico.getCodFilial());
            myStmt.setBoolean(7, medico.isAtivo());

            // Executando o comando SQL
            myStmt.execute();

        } finally {
            // Limpando os objetos JDBC
            close(myConn, myStmt, null);
        }
    }

    public Medico getMedico(String theMedicoId) throws Exception {

        Medico medico = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int medicoId;

        try {

            medicoId = Integer.parseInt(theMedicoId);

            myConn = dataSource.getConnection();

            String sql = "select * from medico where idMedico=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, medicoId);

            System.out.println(medicoId);

            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                String nome = myRs.getString("nome");
                String sobrenome = myRs.getString("sobrenome");
                String cpf = myRs.getString("cpf");
                String sexo = myRs.getString("sexo");
                String crm = myRs.getString("crm");
                String codFilial = myRs.getString("codFilial");
                boolean ativo = myRs.getBoolean("ativo");

                medico = new Medico(medicoId, nome, sobrenome, cpf, sexo, crm, codFilial, ativo);

            } else {
                throw new Exception("Não pode achar o ID do médico: " + medicoId);
            }
            return medico;
        } finally {
            close(myConn, myStmt, myRs);
        }

    }

    public void updateMedico(Medico medico) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "update medico "
                    + "set nome=?, sobrenome=?, cpf=?, sexo=?, crm=?, codFilial=?, ativo=? "
                    + "where idMedico=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, medico.getNome());
            myStmt.setString(2, medico.getSobrenome());
            myStmt.setString(3, medico.getCpf());
            myStmt.setString(4, medico.getSexo());
            myStmt.setString(5, medico.getCrm());
            myStmt.setBoolean(6, medico.isAtivo());
            myStmt.setString(7, medico.getCodFilial());
            myStmt.setInt(8, medico.getIdMedico());

            myStmt.execute();

        } finally {

            close(myConn, myStmt, null);

        }

    }

    public void deleteMedic(String theMedicoId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            int medicoId = Integer.parseInt(theMedicoId);

            myConn = dataSource.getConnection();

            String sql = "update medico "
                    + "set ativo=false "
                    + "where idMedico=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, medicoId);

            myStmt.execute();
        } finally {
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
