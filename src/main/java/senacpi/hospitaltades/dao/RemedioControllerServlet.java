/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senacpi.hospitaltades.dao;

import java.io.IOException;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import senacpi.hospitaltades.model.Remedio;
import senacpi.hospitaltades.service.RemedioDbUtil;

/**
 *
 * @author Yury Cavalcante
 */
public class RemedioControllerServlet extends HttpServlet {

    private RemedioDbUtil remedioDbUtil;

    @Resource(name = "jdbc/hospital_tades")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {

        super.init();

        try {
            remedioDbUtil = new RemedioDbUtil(dataSource);
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
                    listarRemedios(request, response);
                    break;

                //Atualizar um paciente(U)
                case "LOAD":
                    carregarRemedio(request, response);
                    break;

                // Deletar um paciente (D)
                case "DELETE":
                    excluirRemedio(request, response);
                    break;

                default:
                    listarRemedios(request, response);
            }

            listarRemedios(request, response);
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

            switch (theCommand) {
                // Cadastrando paciente (C) 
                case "CREATE":
                    cadastrarRemedio(request, response);
                    break;

                case "UPDATE":
                    editarRemedio(request, response);
                    break;
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void cadastrarRemedio(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo informação do FORMULÁRIO de Paciente
        String nome = request.getParameter("nome");
        String quantidade = request.getParameter("quantidade");
        Boolean ativo = true;

        // Criar um objeto do PACIENTE
        Remedio remedio = new Remedio(nome, quantidade, ativo);

        // Adicionar esse PACIENTE no banco de Dados
        remedioDbUtil.addRemedy(remedio);

        // Enviar este PACIENTE para lista de Pacientes (listarPacientes)
        listarRemedios(request, response);
    }

    private void listarRemedios(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Buscando pacientes usando o pacienteDbUtil
        List<Remedio> remedios = remedioDbUtil.getRemedios();

        // Settando o atributo PACIENTES com o valor que buscamos
        request.setAttribute("REMEDIOS", remedios);

        // Atribuindo valor para o dispatcher com o endereço da página que queremos
        RequestDispatcher dispatcher = request.getRequestDispatcher("lista-remedios.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void carregarRemedio(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Ler o ID do paciente
        String theRemedioId = request.getParameter("remedioId");

        // Achar o paciente pelo banco de dados
        Remedio remedio = remedioDbUtil.getRemedio(theRemedioId);

        // Colocar o paciente no atributo request
        request.setAttribute("REMEDIO", remedio);

        // Enviar o paciente para a página JSP (paciente-editar.jsp)
        RequestDispatcher dispatcher = request.getRequestDispatcher("remedio-editar.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void editarRemedio(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo os dados do form
        int id = Integer.parseInt(request.getParameter("remedioId"));
        String nome = request.getParameter("nome");
        String quantidade = request.getParameter("quantidade");
        Boolean ativo = true;

        Remedio remedio = new Remedio(id, nome, quantidade, ativo);

        remedioDbUtil.updateRemedio(remedio);

        listarRemedios(request, response);

    }

    private void excluirRemedio(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String theRemedioId = request.getParameter("remedioId");

        remedioDbUtil.deleteRemedy(theRemedioId);

        listarRemedios(request, response);

    }
}
