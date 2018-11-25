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
import senacpi.hospitaltades.model.Consulta;

/**
 *
 * @author Yury Cavalcante
 */
public class ConsultaDbUtil {

    private DataSource dataSource;

    public ConsultaDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Consulta> getConsultas() throws Exception {

        List<Consulta> consultas = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            myConn = dataSource.getConnection();

            String sql = "select * from consultas where ativo is true";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {

                int idConsulta = myRs.getInt("idConsulta");
                String motivo = myRs.getString("motivo");
                int idPaciente = myRs.getInt("idPaciente");
                String nomePaciente = myRs.getString("nomePaciente");
                int idMedico = myRs.getInt("idMedico");
                String nomeMedico = myRs.getString("nomeMedico");
                String usuarioNome = myRs.getString("usuarioNome");
                boolean ativo = myRs.getBoolean("ativo");

                Consulta consulta = new Consulta(idConsulta, motivo, idPaciente, nomePaciente, idMedico, nomeMedico, usuarioNome, ativo);

                consultas.add(consulta);
            }

            return consultas;

        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void addAppointment(Consulta consulta) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            myConn = dataSource.getConnection();

            // Criando um SQL para inserir no banco
            String sql = "insert into consultas "
                    + "(motivo, idPaciente, nomePaciente, idMedico, nomeMedico, usuarioNome, ativo)"
                    + "values (?, ?, ?, ?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // Atribuindo os parametros para o Paciente
            myStmt.setString(1, consulta.getMotivo());
            myStmt.setInt(2, consulta.getIdPaciente());
            myStmt.setString(3, consulta.getNomePaciente());
            myStmt.setInt(4, consulta.getIdMedico());
            myStmt.setString(5, consulta.getNomeMedico());
            myStmt.setString(6, consulta.getUsuarioNome());
            myStmt.setBoolean(7, consulta.isAtivo());

            // Executando o comando SQL
            myStmt.execute();

        } finally {
            // Limpando os objetos JDBC
            close(myConn, myStmt, null);
        }
    }

    public Consulta getConsulta(String theConsultaId) throws Exception {

        Consulta consulta = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int consultaId;

        try {

            consultaId = Integer.parseInt(theConsultaId);

            myConn = dataSource.getConnection();

            String sql = "select * from consultas where idConsulta=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, consultaId);

            System.out.println(consultaId);

            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                String motivo = myRs.getString("motivo");
                int idPaciente = myRs.getInt("idPaciente");
                String nomePaciente = myRs.getString("nomePaciente");
                int idMedico = myRs.getInt("idMedico");
                String nomeMedico = myRs.getString("nomeMedico");
                String usuarioNome = myRs.getString("usuarioNome");
                boolean ativo = myRs.getBoolean("ativo");

                consulta = new Consulta(consultaId, motivo, idPaciente, nomePaciente, idMedico, nomeMedico, usuarioNome, ativo);

            } else {
                throw new Exception("Não pode achar o ID do médico: " + consultaId);
            }
            return consulta;
        } finally {
            close(myConn, myStmt, myRs);
        }

    }

    public void updateConsulta(Consulta consulta) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "update consultas "
                    + "motivo=?, idPaciente=?, nomePaciente=?, idMedico=?, nomeMedico=?, usuarioNome=?, ativo=? "
                    + "where idConsulta=?";

            myStmt = myConn.prepareStatement(sql);
            
            myStmt.setString(1, consulta.getMotivo());
            myStmt.setInt(2, consulta.getIdPaciente());
            myStmt.setString(3, consulta.getNomePaciente());
            myStmt.setInt(4, consulta.getIdMedico());
            myStmt.setString(5, consulta.getNomeMedico());
            myStmt.setString(6, consulta.getUsuarioNome());
            myStmt.setBoolean(7, consulta.isAtivo());
            
            myStmt.execute();

        } finally {

            close(myConn, myStmt, null);

        }

    }
    
    public void deleteAppointment(String theConsultaId) throws Exception {
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        try {
         
            int consultaId = Integer.parseInt(theConsultaId);
            
            myConn = dataSource.getConnection();

            String sql = "update consultas "
                    + "set ativo=false "
                    + "where idConsulta=?";
            
            myStmt = myConn.prepareStatement(sql);
            
            myStmt.setInt(1, consultaId);
            
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
