/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senacpi.hospitaltades.model;

import java.util.Date;

/**
 *
 * @author Yury Cavalcante
 */
public class Relatorio {
    private String nome;
    private int idConsulta;
    private Date data;
    private String nomeRemedio;
    private int qtdRemedio;
    private int idRemedio;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNomeRemedio() {
        return nomeRemedio;
    }

    public void setNomeRemedio(String nomeRemedio) {
        this.nomeRemedio = nomeRemedio;
    }

    public int getQtdRemedio() {
        return qtdRemedio;
    }

    public void setQtdRemedio(int qtdRemedio) {
        this.qtdRemedio = qtdRemedio;
    }

    public int getIdRemedio() {
        return idRemedio;
    }

    public void setIdRemedio(int idRemedio) {
        this.idRemedio = idRemedio;
    }


    
}
