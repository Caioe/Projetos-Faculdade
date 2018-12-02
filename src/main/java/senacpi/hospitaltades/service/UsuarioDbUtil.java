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
import senacpi.hospitaltades.model.Usuario;

/**
 *
 * @author Yury Cavalcante
 */
public class UsuarioDbUtil {

    private DataSource dataSource;

    public UsuarioDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public Usuario handleLogin(String login, String senha) throws Exception {

        Usuario usuario = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int usuarioId;

        try {

            myConn = dataSource.getConnection();

            String sql = "select idUsuario, nome, cargo, codFilial from usuario where login=? AND senha=? AND ativo=true";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, login);
            myStmt.setString(2, senha);

            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                usuarioId = myRs.getInt("idUsuario");
                String nome = myRs.getString("nome");
                String cargo = myRs.getString("cargo");
                String codFilial = myRs.getString("codFilial");
                boolean ativo = true;

                usuario = new Usuario(usuarioId, login, senha, nome, cargo, codFilial, ativo);

            }
            return usuario;
        } finally {
            close(myConn, myStmt, myRs);
        }

    }

    public List<Usuario> getUsuarios() throws Exception {

        List<Usuario> usuarios = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            myConn = dataSource.getConnection();

            String sql = "select * from usuario where ativo is true";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {

                int idUsuario = myRs.getInt("idUsuario");
                String login = myRs.getString("login");
                String senha = myRs.getString("senha");
                String nome = myRs.getString("nome");
                String cargo = myRs.getString("cargo");
                boolean loginAtivo = myRs.getBoolean("loginAtivo");
                String codFilial = myRs.getString("codFilial");
                boolean ativo = myRs.getBoolean("ativo");

                Usuario usuario = new Usuario(idUsuario, login, senha, nome, cargo, loginAtivo, codFilial, ativo);

                usuarios.add(usuario);
            }

            return usuarios;

        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public Usuario getUsuario(String theUsuarioId) throws Exception {

        Usuario usuario = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int usuarioId;

        try {

            usuarioId = Integer.parseInt(theUsuarioId);

            myConn = dataSource.getConnection();

            String sql = "select * from usuario where idUsuario=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, usuarioId);

            System.out.println(usuarioId);

            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                String login = myRs.getString("login");
                String senha = myRs.getString("senha");
                String nome = myRs.getString("nome");
                String cargo = myRs.getString("cargo");
                boolean loginAtivo = myRs.getBoolean("loginAtivo");
                String codFilial = myRs.getString("codFilial");
                boolean ativo = myRs.getBoolean("ativo");

                usuario = new Usuario(usuarioId, login, senha, nome, cargo, loginAtivo, codFilial, ativo);

            } else {
                throw new Exception("Não pode achar o ID do médico: " + usuarioId);
            }
            return usuario;
        } finally {
            close(myConn, myStmt, myRs);
        }

    }

    public void updateUser(Usuario usuario) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "update usuario "
                    + "set login=?, senha=?, nome=?, codFilial=?, ativo=? "
                    + "where idUsuario=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, usuario.getLogin());
            myStmt.setString(2, usuario.getSenha());
            myStmt.setString(3, usuario.getNome());
            myStmt.setString(4, usuario.getCodFilial());
            myStmt.setBoolean(5, usuario.isAtivo());

            myStmt.execute();

        } finally {

            close(myConn, myStmt, null);

        }

    }

    public void updateUserLogin(int funcId, String cargo) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        if (cargo.equals("Medico")) {

            try {
                myConn = dataSource.getConnection();

                String sql = "update medico "
                        + "set idUsuario=? "
                        + "where idMedico=?";

                myStmt = myConn.prepareStatement(sql);

                myStmt.setInt(1, funcId);
                myStmt.setInt(2, funcId);

                myStmt.execute();

            } finally {

                close(myConn, myStmt, null);

            }
        }

        if (cargo.equals("Atendente")) {

            try {
                myConn = dataSource.getConnection();

                String sql = "update atendente "
                        + "set idUsuario=? "
                        + "where idAtendente=?";

                myStmt = myConn.prepareStatement(sql);

                myStmt.setInt(1, funcId);
                myStmt.setInt(2, funcId);

                myStmt.execute();

            } finally {

                close(myConn, myStmt, null);

            }
        }

    }

    public void addUser(Usuario usuario) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            myConn = dataSource.getConnection();

            // Criando um SQL para inserir no banco
            String sql = "insert into usuario"
                    + "(login, senha, nome, cargo, loginAtivo, codFilial, ativo)"
                    + "values (?, ?, ?, ?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // Atribuindo os parametros para o Paciente
            myStmt.setString(1, usuario.getLogin());
            myStmt.setString(2, usuario.getSenha());
            myStmt.setString(3, usuario.getNome());
            myStmt.setString(4, usuario.getCargo());
            myStmt.setBoolean(5, usuario.isLoginAtivo());
            myStmt.setString(6, usuario.getCodFilial());
            myStmt.setBoolean(7, usuario.isAtivo());

            // Executando o comando SQL
            myStmt.execute();

        } finally {
            // Limpando os objetos JDBC
            close(myConn, myStmt, null);
        }
    }

    public void deleteUser(String theUsuarioId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            int usuarioId = Integer.parseInt(theUsuarioId);

            myConn = dataSource.getConnection();

            String sql = "update usuario "
                    + "set ativo=false "
                    + "where idUsuario=?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, usuarioId);

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
