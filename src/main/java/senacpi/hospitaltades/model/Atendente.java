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
public class Atendente {

    private int idAtendente;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String sexo;
    private boolean temLogin;
    private boolean ativo;

    public Atendente(int idAtendente, String nome, String sobrenome, String cpf, String sexo, boolean ativo) {
        this.idAtendente = idAtendente;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.ativo = ativo;
    }

    public Atendente(int idAtendente, String nome, String sobrenome, String cpf, String sexo, boolean temLogin, boolean ativo) {
        this.idAtendente = idAtendente;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.temLogin = temLogin;
        this.ativo = ativo;
    }

    public Atendente(String nome, String sobrenome, String cpf, String sexo, boolean ativo) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.ativo = ativo;
    }

    public int getIdAtendente() {
        return idAtendente;
    }

    public void setIdAtendente(int idAtendente) {
        this.idAtendente = idAtendente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public boolean isTemLogin() {
        return temLogin;
    }

    public void setTemLogin(boolean temLogin) {
        this.temLogin = temLogin;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
