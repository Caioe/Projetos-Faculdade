/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senacpi.hospitaltades.dao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import senacpi.hospitaltades.model.Relatorio;
import senacpi.hospitaltades.service.RelatorioDTO;

/**
 *
 * @author Yury Cavalcante
 */
public class RelatorioControllerServlet extends HttpServlet {

    private RelatorioDTO relatorioDbUtil;

    @Resource(name = "jdbc/hospital_tades")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {

        super.init();

        try {
            relatorioDbUtil = new RelatorioDTO(dataSource);
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
                theCommand = "REPORT";
            }

            // Rotear para o método apropria com um SWITCH CASE 
            switch (theCommand) {

                // Lendo pacientes (R)
                case "REPORT":
                    relatorioForm(request, response);
                    break;

                case "GENERATE REPORT":
                    gerarRelatorio(request, response);
                    break;
            }

            relatorioForm(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    private void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lendo informação do FORMULÁRIO de Paciente
        Date inicio = formatStringToDate(request.getParameter("inicio"));
        Date fim = formatStringToDate(request.getParameter("fim"));

        List<Relatorio> relatorio = relatorioDbUtil.gerarRelatorio(inicio, fim);

        // Settando o atributo PACIENTES com o valor que buscamos
        request.setAttribute("RELATORIO", relatorio);
        // Enviar o paciente para a página JSP (paciente-editar.jsp)
        RequestDispatcher dispatcher = request.getRequestDispatcher("relatorio-realizado.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }
    
    private void relatorioForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        

        RequestDispatcher dispatcher = request.getRequestDispatcher("relatorio.jsp");

        // Executando o dispatcher
        dispatcher.forward(request, response);
    }

    public static Date formatStringToDate(String data) throws Exception {
        if (data == null || data.equals("")) {
            return null;
        }
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = formatter.parse(data);
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public static String formatDateToString(Date data) throws Exception {
        if (data == null) {
            return null;
        }

        String date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date = formatter.format(data);

        return date;
    }
}
