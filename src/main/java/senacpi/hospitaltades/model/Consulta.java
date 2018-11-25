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
public class Consulta {
    private int idConsulta;
    private Date data;
    private String motivo;
    private int idPaciente;
    private String nomePaciente;
    private int idMedico;
    private String nomeMedico;
    private String usuarioNome;
    private boolean ativo;

    public Consulta(int idConsulta, String motivo, int idPaciente, String nomePaciente, int idMedico, String nomeMedico, String usuarioNome, boolean ativo) {
        this.idConsulta = idConsulta;
        this.motivo = motivo;
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
        this.idMedico = idMedico;
        this.nomeMedico = nomeMedico;
        this.usuarioNome = usuarioNome;
        this.ativo = ativo;
    }

    public Consulta(String motivo, int idPaciente, String nomePaciente, int idMedico, String nomeMedico, String usuarioNome, boolean ativo) {
        this.motivo = motivo;
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
        this.idMedico = idMedico;
        this.nomeMedico = nomeMedico;
        this.usuarioNome = usuarioNome;
        this.ativo = ativo;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }


}
