/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senacpi.hospitaltades.dao;

import senacpi.hospitaltades.service.MedicoDbUtil;
import java.io.IOException;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import senacpi.hospitaltades.model.Medico;

/**
 *
 * @author Yury Cavalcante
 */
public class MedicoControllerServlet extends HttpServlet {

    private MedicoDbUtil medicoDbUtil;
    ;

    @Resource(name = "jdbc/hospital_tades")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {

        super.init();

        try {
            medicoDbUtil = new MedicoDbUtil(dataSource);
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
                    listarMedicos(request, response);
                    break;

                //Atualizar um paciente(U)
                case "LOAD MEDIC":
                    carregarMedico(request, response);
                    break;

                // Deletar um paciente (D)
                case "DELETE MEDIC":
                    excluirMedico(request, response);
                    break;

                default:
                    listarMedicos(request, response);
            }

            listarMedicos(request, response);
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
                case "CREATE MEDIC":
                    cadastrarMedico(request, response);
                    break;

                case "UPDATE MEDIC":
                    editarMedico(request, response);
                    break;
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    private void cadastrarMedico(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo informação do FORMULÁRIO de Paciente
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String cpf = request.getParameter("cpf");
        String sexo = request.getParameter("sexo");
        String crm = request.getParameter("crm");
        String codFilial = request.getParameter("codFilial");
        Boolean ativo = true;

        // Criar um objeto do PACIENTE
        Medico medico = new Medico(nome, sobrenome, cpf, sexo, crm, codFilial, ativo);

        // Adicionar esse PACIENTE no banco de Dados
        medicoDbUtil.addMedic(medico);

        // Enviar este PACIENTE para lista de Pacientes (listarPacientes)
        response.sendRedirect(request.getContextPath() + "/MedicoControllerServlet?command=READ");
    }

    private void listarMedicos(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Buscando pacientes usando o pacienteDbUtil
        List<Medico> medicos = medicoDbUtil.getMedicos();

        // Settando o atributo PACIENTES com o valor que buscamos
        request.setAttribute("MEDICOS", medicos);

        // Atribuindo valor para o dispatcher com o endereço da página que queremos
        RequestDispatcher dispatcher = request.getRequestDispatcher("lista-medicos.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void carregarMedico(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Ler o ID do paciente
        String theMedicoId = request.getParameter("medicoId");

        // Achar o paciente pelo banco de dados
        Medico medico = medicoDbUtil.getMedico(theMedicoId);

        // Colocar o paciente no atributo request
        request.setAttribute("MEDICO", medico);

        // Enviar o paciente para a página JSP (paciente-editar.jsp)
        RequestDispatcher dispatcher = request.getRequestDispatcher("medico-editar.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void editarMedico(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo os dados do form
        int idMedico = Integer.parseInt(request.getParameter("medicoId"));
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String cpf = request.getParameter("cpf");
        String sexo = request.getParameter("sexo");
        String crm = request.getParameter("crm");
        String codFilial = request.getParameter("codFilial");
        Boolean ativo = true;

        Medico medico = new Medico(idMedico, nome, sobrenome, cpf, sexo, crm, codFilial, ativo);

        medicoDbUtil.updateMedico(medico);

        response.sendRedirect(request.getContextPath() + "/MedicoControllerServlet?command=READ");

    }

    private void excluirMedico(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String theMedicoId = request.getParameter("medicoId");

        medicoDbUtil.deleteMedic(theMedicoId);

        listarMedicos(request, response);

    }
}
