/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senacpi.hospitaltades.dao;

import senacpi.hospitaltades.service.AtendenteDbUtil;
import java.io.IOException;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import senacpi.hospitaltades.model.Atendente;

/**
 *
 * @author Yury Cavalcante
 */
public class AtendenteControllerServlet extends HttpServlet {

    private AtendenteDbUtil atendenteDbUtil;

    @Resource(name = "jdbc/hospital_tades")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {

        super.init();

        try {
            atendenteDbUtil = new AtendenteDbUtil(dataSource);
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
                    listarAtendentes(request, response);
                    break;

                //Atualizar um paciente(U)
                case "LOAD ATTENDANT":
                    carregarAtendente(request, response);
                    break;

                // Deletar um paciente (D)
                case "DELETE ATTENDANT":
                    excluirAtendente(request, response);
                    break;

                default:
                    listarAtendentes(request, response);
            }

            listarAtendentes(request, response);
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
                case "CREATE ATTENDANT":
                    cadastrarAtendente(request, response);
                    break;

                case "UPDATE ATTENDANT":
                    editarAtendente(request, response);
                    break;
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    private void cadastrarAtendente(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo informação do FORMULÁRIO de Paciente
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String cpf = request.getParameter("cpf");
        String sexo = request.getParameter("sexo");
        Boolean ativo = true;

        // Criar um objeto do PACIENTE
        Atendente atendente = new Atendente(nome, sobrenome, cpf, sexo, ativo);

        // Adicionar esse PACIENTE no banco de Dados
        atendenteDbUtil.addAttendant(atendente);

        // Enviar este PACIENTE para lista de Pacientes (listarPacientes)
        response.sendRedirect(request.getContextPath() + "/AtendenteControllerServlet?command=READ");
    }

    private void listarAtendentes(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Buscando pacientes usando o pacienteDbUtil
        List<Atendente> atendentes = atendenteDbUtil.getAtendentes();

        // Settando o atributo PACIENTES com o valor que buscamos
        request.setAttribute("ATENDENTES", atendentes);

        // Atribuindo valor para o dispatcher com o endereço da página que queremos
        RequestDispatcher dispatcher = request.getRequestDispatcher("lista-atendentes.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void carregarAtendente(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Ler o ID do paciente
        String theAtendenteId = request.getParameter("atendenteId");

        // Achar o paciente pelo banco de dados
        Atendente atendente = atendenteDbUtil.getAtendente(theAtendenteId);

        // Colocar o paciente no atributo request
        request.setAttribute("ATENDENTE", atendente);

        // Enviar o paciente para a página JSP (paciente-editar.jsp)
        RequestDispatcher dispatcher = request.getRequestDispatcher("atendente-editar.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void editarAtendente(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo os dados do form
        int idAtendente = Integer.parseInt(request.getParameter("atendenteId"));
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String cpf = request.getParameter("cpf");
        String sexo = request.getParameter("sexo");
        Boolean ativo = true;

        Atendente atendente = new Atendente(idAtendente, nome, sobrenome, cpf, sexo, ativo);

        atendenteDbUtil.updateAtendente(atendente);

        response.sendRedirect(request.getContextPath() + "/AtendenteControllerServlet?command=READ");

    }

    private void excluirAtendente(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String theAtendenteId = request.getParameter("atendenteId");

        atendenteDbUtil.deleteAttendant(theAtendenteId);

        listarAtendentes(request, response);

    }
}
