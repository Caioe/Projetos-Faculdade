/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senacpi.hospitaltades.model;

/**
 *
 * @author Yury Cavalcante
 */
public class Remedio {
    
    private int idRemedio;
    private String nome;
    private String quantidade;
    private boolean ativo;

    public Remedio(int idRemedio, String nome, String quantidade, boolean ativo) {
        this.idRemedio = idRemedio;
        this.nome = nome;
        this.quantidade = quantidade;
        this.ativo = ativo;
    }

    public Remedio(String nome, String quantidade, boolean ativo) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.ativo = ativo;
    }

    public int getIdRemedio() {
        return idRemedio;
    }

    public void setIdRemedio(int idRemedio) {
        this.idRemedio = idRemedio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    
}
