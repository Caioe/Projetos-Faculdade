/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senacpi.hospitaltades.dao;

import senacpi.hospitaltades.service.PacienteDbUtil;
import java.io.IOException;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import senacpi.hospitaltades.model.Paciente;

/**
 *
 * @author Yury Cavalcante
 */
public class PacienteControllerServlet extends HttpServlet {

    private PacienteDbUtil pacienteDbUtil;

    @Resource(name = "jdbc/hospital_tades")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {

        super.init();

        try {
            pacienteDbUtil = new PacienteDbUtil(dataSource);
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
                    listarPacientes(request, response);
                    break;

                //Atualizar um paciente(U)
                case "LOAD":
                    carregarPaciente(request, response);
                    break;

                // Deletar um paciente (D)
                case "DELETE":
                    excluirPaciente(request, response);
                    break;

                default:
                    listarPacientes(request, response);
            }

            listarPacientes(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // Ler o a varável "command"
            String theCommand = request.getParameter("command");

            // Rotear para o método apropria com um SWITCH CASE 
            switch (theCommand) {

                // Cadastrando paciente (C) 
                case "CREATE":
                    cadastrarPaciente(request, response);
                    break;

                case "UPDATE":
                    editarPaciente(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    private void cadastrarPaciente(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo informação do FORMULÁRIO de Paciente
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String dataNasc = request.getParameter("dataNasc");
        String cpf = request.getParameter("cpf");
        String sexo = request.getParameter("sexo");
        String contato = request.getParameter("contato");
        String email = request.getParameter("email");
        String codFilial = request.getParameter("codFilial");
        Boolean ativo = true;

        // Criar um objeto do PACIENTE
        Paciente paciente = new Paciente(nome, sobrenome, dataNasc, cpf, sexo, contato, email, codFilial, ativo);

        // Adicionar esse PACIENTE no banco de Dados
        pacienteDbUtil.addPatient(paciente);

        // Envia o usuário de volta a página de listagem de pacientes
        response.sendRedirect(request.getContextPath() + "/PacienteControllerServlet?command=READ");
    }

    private void listarPacientes(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        String usuarioCodFilial = (String) session.getAttribute("usuarioCodFilial");

        List<Paciente> pacientes = pacienteDbUtil.getPacientes(usuarioCodFilial);
        // Buscando pacientes usando o pacienteDbUtil
        // Settando o atributo PACIENTES com o valor que buscamos
        request.setAttribute("PACIENTES", pacientes);

        // Atribuindo valor para o dispatcher com o endereço da página que queremos
        RequestDispatcher dispatcher = request.getRequestDispatcher("lista-pacientes.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void carregarPaciente(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Ler o ID do paciente
        String thePacienteId = request.getParameter("pacienteId");

        // Achar o paciente pelo banco de dados
        Paciente paciente = pacienteDbUtil.getPaciente(thePacienteId);

        // Colocar o paciente no atributo request
        request.setAttribute("PACIENTE", paciente);

        // Enviar o paciente para a página JSP (paciente-editar.jsp)
        RequestDispatcher dispatcher = request.getRequestDispatcher("paciente-editar.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void editarPaciente(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo os dados do form
        int id = Integer.parseInt(request.getParameter("pacienteId"));
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String dataNasc = request.getParameter("dataNasc");
        String cpf = request.getParameter("cpf");
        String sexo = request.getParameter("sexo");
        String contato = request.getParameter("contato");
        String email = request.getParameter("email");
        String codFilial = request.getParameter("codFilial");
        Boolean ativo = true;

        Paciente paciente = new Paciente(id, nome, sobrenome, dataNasc, cpf, sexo, contato, email, codFilial, ativo);

        pacienteDbUtil.updatePaciente(paciente);

        response.sendRedirect(request.getContextPath() + "/PacienteControllerServlet?command=READ");

    }

    private void excluirPaciente(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String thePacienteId = request.getParameter("pacienteId");

        pacienteDbUtil.deletePatient(thePacienteId);

        listarPacientes(request, response);

    }
}
