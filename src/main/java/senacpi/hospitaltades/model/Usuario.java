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
public class Usuario {

    private int idUsuario;
    private String login;
    private String senha;
    private String nome;
    private String cargo;
    private boolean loginAtivo;
    private String codFilial;
    private boolean ativo;

    public Usuario(int idUsuario, String login, String senha, String nome, String cargo, boolean loginAtivo, String codFilial, boolean ativo) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cargo = cargo;
        this.codFilial = codFilial;
        this.ativo = ativo;
    }

    public Usuario(String login, String senha, String nome, String cargo, boolean loginAtivo, String codFilial, boolean ativo) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cargo = cargo;
        this.codFilial = codFilial;
        this.ativo = ativo;
    }

    public Usuario(int idUsuario, String login, String senha, String nome, String cargo, String codFilial, boolean ativo) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cargo = cargo;
        this.codFilial = codFilial;
        this.ativo = ativo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public boolean isLoginAtivo() {
        return loginAtivo;
    }

    public void setLoginAtivo(boolean loginAtivo) {
        this.loginAtivo = loginAtivo;
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
