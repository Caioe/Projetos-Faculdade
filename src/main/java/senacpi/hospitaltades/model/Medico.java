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
public class Medico {

    private int idMedico;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String sexo;
    private String crm;
    private int idUsuario;
    private String codFilial;
    private boolean ativo;

    public Medico(int idMedico, String nome, String sobrenome, String cpf, String sexo, String crm, int idUsuario, String codFilial, boolean ativo) {
        this.idMedico = idMedico;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.crm = crm;
        this.idUsuario = idUsuario;
        this.codFilial = codFilial;
        this.ativo = ativo;
    }

    public Medico(int idMedico, String nome, String sobrenome, String cpf, String sexo, String crm, String codFilial, boolean ativo) {
        this.idMedico = idMedico;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.crm = crm;
        this.codFilial = codFilial;
        this.ativo = ativo;
    }

    public Medico(String nome, String sobrenome, String cpf, String sexo, String crm, String codFilial, boolean ativo) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.crm = crm;
        this.codFilial = codFilial;
        this.ativo = ativo;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
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

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setTemLogin(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodFilial() {
        return codFilial;
    }

    public void setCodFilial(String codFilial) {
        this.codFilial = codFilial;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
