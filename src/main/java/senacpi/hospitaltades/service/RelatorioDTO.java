/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senacpi.hospitaltades.service;

import java.sql.PreparedStatement;
import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import senacpi.hospitaltades.model.Relatorio;

/**
 *
 * @author Yury Cavalcante
 */
public class RelatorioDTO {

    private DataSource dataSource;

    public RelatorioDTO(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Relatorio> gerarRelatorio(Date inicio, Date fim) throws Exception {

        List<Relatorio> listaRelatorio = new ArrayList<>();

        Connection myConn = null;
        ResultSet myRs = null;
        PreparedStatement myStmt = null;

        try {

            myConn = dataSource.getConnection();

            String queryRelatorio = "SELECT concat(p.nome, ' ', p.sobrenome) as nome, "
                    + "c.idConsulta as idConsulta, c.data as dataConsulta, "
                    + "r.nome as nomeRemedio, r.quantidade as qtdRemedio, r.idRemedio as idRemedio, "
                    + "from consulta c "
                    + "INNER JOIN remedio r on r.idRemedio = c.idRemedio "
                    + "INNER JOIN paciente p on p.id = c.idPaciente "
                    + "WHERE c.data BETWEEN ? AND ? "
                    + "ORDER BY idConsulta";
            System.out.println("inicio: " + inicio + "fim: " + fim);

            myStmt = myConn.prepareStatement(queryRelatorio);

            myStmt.setObject(1, inicio);
            myStmt.setObject(2, fim);

            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                Relatorio relatorio = new Relatorio();
                relatorio.setNome(myRs.getString(1));
                relatorio.setIdConsulta(myRs.getInt(2));
                relatorio.setData(myRs.getDate(3));
                relatorio.setNomeRemedio(myRs.getString(4));
                relatorio.setQtdRemedio(myRs.getInt(5));
                relatorio.setIdRemedio(myRs.getInt(6));
                listaRelatorio.add(relatorio);

            }

            return listaRelatorio;

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
