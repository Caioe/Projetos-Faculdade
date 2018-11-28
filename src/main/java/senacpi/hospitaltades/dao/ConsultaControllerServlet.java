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
import senacpi.hospitaltades.model.Consulta;
import senacpi.hospitaltades.model.Medico;
import senacpi.hospitaltades.model.Paciente;
import senacpi.hospitaltades.service.ConsultaDbUtil;
import senacpi.hospitaltades.service.MedicoDbUtil;
import senacpi.hospitaltades.service.PacienteDbUtil;

/**
 *
 * @author Yury Cavalcante
 */
public class ConsultaControllerServlet extends HttpServlet {

    private ConsultaDbUtil consultaDbUtil;
    private PacienteDbUtil pacienteDbUtil;
    private MedicoDbUtil medicoDbUtil;

    @Resource(name = "jdbc/hospital_tades")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {

        super.init();

        try {
            consultaDbUtil = new ConsultaDbUtil(dataSource);
            pacienteDbUtil = new PacienteDbUtil(dataSource);
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
                    listarConsultas(request, response);
                    break;

                //Atualizar um paciente(U)
                case "LOAD APPOINTMENT":
                    carregarConsulta(request, response);
                    break;

                // Deletar um paciente (D)
                case "DELETE APPOINTMENT":
                    excluirConsulta(request, response);
                    break;

                default:
                    listarConsultas(request, response);
            }

            listarConsultas(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Paciente> pacientes = pacienteDbUtil.getPacientes();

            request.setAttribute("PACIENTES", pacientes);

            List<Medico> medicos = medicoDbUtil.getMedicos();

            request.setAttribute("MEDICOS", medicos);

            // Ler o a varável "command"
            String theCommand = request.getParameter("command");

            // Rotear para o método apropria com um SWITCH CASE 
            switch (theCommand) {

                // Cadastrando paciente (C) 
                case "CREATE APPOINTMENT":
                    cadastrarConsulta(request, response);
                    break;
                    //AEEEEE
                case "UPDATE APPOINTMENT":
                    editarConsulta(request, response);
                    break;
                default:
                    // Enviar este PACIENTE para lista de Pacientes (listarPacientes)
                    RequestDispatcher dispatcher = request.getRequestDispatcher("consulta-cadastro.jsp");

                    // Executando o dispatcher
                    dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    private void cadastrarConsulta(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        int idPaciente = 0;
        int idMedico = 0;
        String nomePaciente = null;
        String nomeMedico = null;
        // Lendo informação do FORMULÁRIO de Paciente

        String motivo = request.getParameter("motivo");
        String idPacienteString = request.getParameter("idPaciente");
        String idMedicoString = request.getParameter("idMedico");

        if (idPacienteString != null) {
            idPaciente = Integer.parseInt(idPacienteString);
        }

        if (idMedicoString != null) {
            idMedico = Integer.parseInt(idMedicoString);
        }

        if (idPaciente != 0) {
            Paciente paciente = pacienteDbUtil.getPaciente(idPacienteString);
            nomePaciente = paciente.getNome();
        }

        if (idMedico != 0) {
            Medico medico = medicoDbUtil.getMedico(idMedicoString);
            nomeMedico = medico.getNome();
        }

        String usuarioNome = request.getParameter("usuarioNome");
        Boolean ativo = true;

        // Criar um objeto do PACIENTE
        Consulta consulta = new Consulta(motivo, idPaciente, nomePaciente, idMedico, nomeMedico, usuarioNome, ativo);

        consultaDbUtil.addAppointment(consulta);

        response.sendRedirect(request.getContextPath() + "/ConsultaControllerServlet?command=READ");
    }

    private void listarConsultas(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        List<Paciente> pacientes = pacienteDbUtil.getPacientes();

        request.setAttribute("PACIENTES", pacientes);

        // Buscando pacientes usando o pacienteDbUtil
        List<Consulta> consultas = consultaDbUtil.getConsultas();

        // Settando o atributo PACIENTES com o valor que buscamos
        request.setAttribute("CONSULTAS", consultas);

        // Atribuindo valor para o dispatcher com o endereço da página que queremos
        RequestDispatcher dispatcher = request.getRequestDispatcher("lista-consultas.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void carregarConsulta(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Ler o ID do paciente
        String theConsultaId = request.getParameter("consultaId");

        // Achar o paciente pelo banco de dados
        Consulta consulta = consultaDbUtil.getConsulta(theConsultaId);

        // Colocar o paciente no atributo request
        request.setAttribute("CONSULTA", consulta);

        // Enviar o paciente para a página JSP (paciente-editar.jsp)
        RequestDispatcher dispatcher = request.getRequestDispatcher("consulta-editar.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    private void editarConsulta(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo informação do FORMULÁRIO de Paciente
        List<Paciente> pacientes = pacienteDbUtil.getPacientes();

        request.setAttribute("PACIENTES", pacientes);

        List<Medico> medicos = medicoDbUtil.getMedicos();

        request.setAttribute("MEDICOS", medicos);

        int idConsulta = Integer.parseInt(request.getParameter("consultaId"));
        String motivo = request.getParameter("motivo");
        int idPaciente = Integer.parseInt(request.getParameter("idPaciente"));
        Paciente paciente = pacienteDbUtil.getPaciente("idPaciente");
        String nomePaciente = paciente.getNome();
        int idMedico = Integer.parseInt(request.getParameter("idMedico"));
        Medico medico = medicoDbUtil.getMedico("idMedico");
        String nomeMedico = medico.getNome();
        String usuarioNome = request.getParameter("usuarioNome");
        Boolean ativo = true;

        // Criar um objeto do PACIENTE
        Consulta consulta = new Consulta(idConsulta, motivo, idPaciente, nomePaciente, idMedico, nomeMedico, usuarioNome, ativo);

        // Adicionar esse PACIENTE no banco de Dados
        consultaDbUtil.updateConsulta(consulta);

        // Enviar este PACIENTE para lista de Pacientes (listarPacientes)
        response.sendRedirect(request.getContextPath() + "/ConsultaControllerServlet?command=READ");

    }

    private void excluirConsulta(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String theConsultaId = request.getParameter("consultaId");

        consultaDbUtil.deleteAppointment(theConsultaId);

        listarConsultas(request, response);

    }
}
