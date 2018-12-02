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
    private int idRemedio;
    private String nomeRemedio;
    private String usuarioNome;
    private String codFilial;
    private boolean ativo;
    private String obsMedica;

    public Consulta(int idConsulta, Date data, String motivo, int idPaciente, String nomePaciente, int idMedico, String nomeMedico, int idRemedio, String nomeRemedio, String usuarioNome, String codFilial, boolean ativo, String obsMedica) {
        this.data = data;
        this.idConsulta = idConsulta;
        this.motivo = motivo;
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
        this.idMedico = idMedico;
        this.nomeMedico = nomeMedico;
        this.idRemedio = idRemedio;
        this.nomeRemedio = nomeRemedio;
        this.usuarioNome = usuarioNome;
        this.codFilial = codFilial;
        this.ativo = ativo;
        this.obsMedica = obsMedica;
    }

    public Consulta(Date data, String motivo, int idPaciente, String nomePaciente, int idMedico, String nomeMedico, int idRemedio, String nomeRemedio, String usuarioNome, String codFilial, boolean ativo, String obsMedica) {
        this.data = data;
        this.motivo = motivo;
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
        this.idMedico = idMedico;
        this.nomeMedico = nomeMedico;
        this.idRemedio = idRemedio;
        this.nomeRemedio = nomeRemedio;
        this.usuarioNome = usuarioNome;
        this.codFilial = codFilial;
        this.ativo = ativo;
        this.obsMedica = obsMedica;
    }

    public Consulta(int idConsulta, Date data, String motivo, int idPaciente, String nomePaciente, int idMedico, String nomeMedico, String usuarioNome, String codFilial, boolean ativo) {
        this.data = data;
        this.idConsulta = idConsulta;
        this.motivo = motivo;
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
        this.idMedico = idMedico;
        this.nomeMedico = nomeMedico;
        this.usuarioNome = usuarioNome;
        this.codFilial = codFilial;
        this.ativo = ativo;
    }

    public Consulta(Date data, String motivo, int idPaciente, String nomePaciente, int idMedico, String nomeMedico, String usuarioNome, String codFilial, boolean ativo) {
        this.data = data;
        this.motivo = motivo;
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
        this.idMedico = idMedico;
        this.nomeMedico = nomeMedico;
        this.usuarioNome = usuarioNome;
        this.codFilial = codFilial;
        this.ativo = ativo;
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

    public int getIdRemedio() {
        return idRemedio;
    }

    public void setIdRemedio(int idRemedio) {
        this.idRemedio = idRemedio;
    }

    public String getNomeRemedio() {
        return nomeRemedio;
    }

    public void setNomeRemedio(String nomeRemedio) {
        this.nomeRemedio = nomeRemedio;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
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

    public String getObsMedica() {
        return obsMedica;
    }

    public void setObsMedica(String obsMedica) {
        this.obsMedica = obsMedica;
    }

}
