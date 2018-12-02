/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senacpi.hospitaltades.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import senacpi.hospitaltades.model.Atendente;
import senacpi.hospitaltades.model.Medico;
import senacpi.hospitaltades.model.Usuario;
import senacpi.hospitaltades.service.AtendenteDbUtil;
import senacpi.hospitaltades.service.MedicoDbUtil;
import senacpi.hospitaltades.service.UsuarioDbUtil;

/**
 *
 * @author Yury Cavalcante
 */
public class UsuarioControllerServlet extends HttpServlet {

    private UsuarioDbUtil usuarioDbUtil;
    private MedicoDbUtil medicoDbUtil;
    private AtendenteDbUtil atendenteDbUtil;

    @Resource(name = "jdbc/hospital_tades")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {

        super.init();

        try {
            usuarioDbUtil = new UsuarioDbUtil(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // Ler o a varável "command"
            String theCommand = request.getParameter("command");

            // Caso não há comando, o default é listar os pacientes
            if (theCommand == null) {
                theCommand = "READ";
            }

            // Rotear para o método apropria com um SWITCH CASE 
            switch (theCommand) {

                // Lendo pacientes (R)
                case "READ":
                    listarUsuarios(request, response);
                    break;

                //Atualizar um paciente(U)
                case "EDIT USER":
                    carregarUsuario(request, response);
                    break;

                // Deletar um paciente (D)
                case "DELETE USER":
                    excluirUsuario(request, response);
                    break;

                default:
                    listarUsuarios(request, response);
            }

            listarUsuarios(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String theCommand = request.getParameter("command");

            if (theCommand == null) {
                theCommand = "LOGIN";
            }

            switch (theCommand) {

                case "CREATE USER":
                    cadastrarUsuario(request, response);
                    break;

                case "UPDATE USER":
                    editarUsuario(request, response);
                    break;

                case "LOGIN":
                    logarUsuario(request, response);
                    break;

                case "LOGOUT":
                    deslogarUsuario(request, response);
                    break;

                default:
                    logarUsuario(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void logarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if ("POST".equalsIgnoreCase(request.getMethod())) {

            if (request.getParameter("login") != null || request.getParameter("senha") != null) {

                if (request.getParameter("login").equals("Login")) {

                    String usuarioLogin = request.getParameter("usuario");
                    String senha = request.getParameter("senha");

                    Usuario usuario = usuarioDbUtil.handleLogin(usuarioLogin, senha);

                    if (usuario != null) {

                        if (usuario.getSenha().equals(senha) && usuario.getLogin().equals(usuarioLogin)) {

                            HttpSession session = request.getSession();
                            session.setAttribute("usuarioNome", usuario.getNome());
                            session.setAttribute("usuarioCargo", usuario.getCargo());
                            session.setAttribute("usuarioLogin", usuario.getLogin());

                            RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");

                            dispatcher.forward(request, response);
                        }
                    } else {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/loginErrado.jsp");

                        dispatcher.forward(request, response);
                    }
                }

            } 

        }
    }

    private void deslogarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("logout.jsp");

    }

    private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo informação do FORMULÁRIO de Paciente
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String nome = request.getParameter("nome");
        String cargo = request.getParameter("cargo");
        Boolean ativo = true;

        // Criar um objeto do PACIENTE
        Usuario usuario = new Usuario(login, senha, nome, cargo, ativo, ativo);

        // Adicionar esse PACIENTE no banco de Dados
        usuarioDbUtil.addUser(usuario);

        if (usuario.getCargo().equals("Medico")) {
            int funcId = Integer.parseInt(request.getParameter("theFuncId"));
            usuarioDbUtil.updateUserLogin(funcId, cargo);
            response.sendRedirect(request.getContextPath() + "/MedicoControllerServlet?command=READ");
        }

        if (usuario.getCargo().equals("Atendente")) {
            int funcId = Integer.parseInt(request.getParameter("theFuncId"));
            usuarioDbUtil.updateUserLogin(funcId, cargo);
            response.sendRedirect(request.getContextPath() + "/AtendenteControllerServlet?command=READ");
        }

    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Buscando pacientes usando o pacienteDbUtil
        List<Usuario> usuarios = usuarioDbUtil.getUsuarios();

        // Settando o atributo PACIENTES com o valor que buscamos
        request.setAttribute("USUARIOS", usuarios);

        // Atribuindo valor para o dispatcher com o endereço da página que queremos
        RequestDispatcher dispatcher = request.getRequestDispatcher("lista-usuarios.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void carregarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Ler o ID do paciente
        String theUsuarioId = request.getParameter("usuarioId");

        // Achar o paciente pelo banco de dados
        Usuario usuario = usuarioDbUtil.getUsuario(theUsuarioId);

        // Colocar o paciente no atributo request
        request.setAttribute("USUARIO", usuario);

        // Enviar o paciente para a página JSP (paciente-editar.jsp)
        RequestDispatcher dispatcher = request.getRequestDispatcher("usuario-editar.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void editarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo os dados do form
        int idUsuario = Integer.parseInt(request.getParameter("usuarioId"));
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String nome = request.getParameter("nome");
        String cargo = request.getParameter("cargo");
        Boolean loginAtivo = true;
        Boolean ativo = true;

        Usuario usuario = new Usuario(idUsuario, login, senha, nome, cargo, loginAtivo, ativo);

        usuarioDbUtil.updateUser(usuario);

        if (usuario.getCargo().equals("Médico")) {
            int funcId = Integer.parseInt(request.getParameter("theFuncId"));
            usuarioDbUtil.updateUserLogin(funcId, cargo);
            response.sendRedirect(request.getContextPath() + "/MedicoControllerServlet?command=READ");
        }

        if (usuario.getCargo().equals("Atendente")) {
            int funcId = Integer.parseInt(request.getParameter("theFuncId"));
            usuarioDbUtil.updateUserLogin(funcId, cargo);
            response.sendRedirect(request.getContextPath() + "/AtendenteControllerServlet?command=READ");
        }

    }

    private void excluirUsuario(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String theUsuarioId = request.getParameter("usuarioId");

        usuarioDbUtil.deleteUser(theUsuarioId);

        listarUsuarios(request, response);

    }

}
