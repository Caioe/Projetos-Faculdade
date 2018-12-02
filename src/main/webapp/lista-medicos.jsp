<%-- 
    Document   : lista-pacientes
    Created on : 27/09/2018, 21:02:09
    Author     : Yury Cavalcante
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Médicos</title>

        <link type="text/css" rel="stylesheet" href="client/css/lista-pacientes.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/css?family=Kodchasan:400,600" rel="stylesheet">


    </head>
    <body>
        <div id="main-wrapper">
            <div id="sidebar-container">
                <div id="user-container">
                    <div class="profile-pic-circle">
                        <img src="client/imgs/profile.svg" alt="Ícone de Perfil" class="icon-profile-pic">
                    </div>
                    <h3>${usuarioNome}</h3>
                    <h4>${usuarioCargo}</h4>

                </div>


                <c:url var="patientsLink" value="PacienteControllerServlet">
                    <c:param name="command" value="READ" />
                </c:url>

                <c:url var="medicsLink" value="MedicoControllerServlet">
                    <c:param name="command" value="READ" />
                </c:url>

                <c:url var="attendantsLink" value="AtendenteControllerServlet">
                    <c:param name="command" value="READ" />
                </c:url>

                <c:url var="remediesLink" value="RemedioControllerServlet">
                    <c:param name="command" value="READ" />
                </c:url>

                <c:url var="usersLink" value="UsuarioControllerServlet">
                    <c:param name="command" value="READ" />
                </c:url>

                <c:url var="homeLink" value="home.jsp" />


                <div id="menu-container">
                    <hr style="margin: 8px;">
                    <c:if test="${usuarioCargo == 'Atendente'}">
                        <div class="menu-item"><i class="fas fa-stethoscope" style="padding-right: 6px; color: #006EA2; font-size: 1.3rem;"></i>
                            <a href="${createAppointment}" style="text-decoration: none; color: #4BB543; font-weight: bolder">Marcar Consulta</a>
                        </div>
                    </c:if>

                    <c:if test="${usuarioCargo == 'Medico'}">
                        <div class="menu-item"><i class="fas fa-stethoscope" style="padding-right: 6px; color: #006EA2; font-size: 1.3rem;"></i>
                            <a href="${createAppointment}" style="text-decoration: none; color: #4BB543; font-weight: bolder">Consultas</a>
                        </div>
                    </c:if>

                    <div class="menu-item"><i class="fas fa-user-injured" style="padding-right: 6px; color: #006EA2; font-size: 1.3rem;"></i>
                        <a href="${patientsLink}" style="text-decoration: none; color: inherit;">Pacientes</a>
                    </div>

                    <c:if test="${usuarioCargo == 'Medico' || usuarioCargo == 'Admin'}">
                        <div class="menu-item"><i class="fas fa-stethoscope" style="padding-right: 6px; color: #006EA2; font-size: 1.3rem;"></i>
                            <a href="${medicsLink}" style="text-decoration: none; color: inherit;">Médicos</a>
                        </div>
                    </c:if>

                    <c:if test="${usuarioCargo == 'Atendente' || usuarioCargo == 'Admin'}">
                        <div class="menu-item"><i class="fas fa-user-tie" style="padding-right: 6px; color: #006EA2; font-size: 1.3rem;"></i>
                            <a href="${attendantsLink}" style="text-decoration: none; color: inherit;">Atendentes</a>
                        </div>
                    </c:if>


                    <div class="menu-item"><i class="fas fa-box-open" style="padding-right: 6px; color: #006EA2; font-size: 1.3rem;"></i>
                        <a href="${remediesLink}" style="text-decoration: none; color: inherit;">Estoque</a>
                    </div>

                    <c:if test="${usuarioCargo == 'Admin'}">
                        <div class="menu-item"><i class="fas fa-users" style="padding-right: 6px; color: #006EA2; font-size: 1.3rem;"></i>
                            <a href="${usersLink}" style="text-decoration: none; color: inherit;">Usuários</a>
                        </div>
                    </c:if>

                    <c:if test="${usuarioCargo == 'Admin'}">
                        <div class="menu-item"><i class="fas fa-file-alt" style="padding-right: 6px; color: #006EA2; font-size: 1.3rem;"></i>
                            Relatórios
                        </div>
                    </c:if>

                    <div class="menu-item">
                        <form name="logoutform" action="UsuarioControllerServlet" method="POST">
                            <input type="hidden" name="command" value="LOGOUT">
                            <button type="submit" name="logout" class="logout-buton"><i class="fas fa-sign-out-alt" style="padding-right: 12px; color: #fff;"></i>Sair</button>
                        </form>
                    </div>
                </div>
            </div>
            <div id="content-wrapper">
                <div id="topbar-container">

                    <div class="main-title"><a href="${homeLink}"><i class="fas fa-hospital-alt" style="padding-right: 20px; color: #FA4860;"></i>hospital tades</a></div>
                    <div id="row"></div>
                </div>
                <div id="content-container">
                    <div class="add-patient">
                        <input type="button" value="Cadastrar" onclick="window.location.href = 'medico-cadastro.jsp'; return false;" class="btn-primary"/>
                    </div>
                    <div class="table-session">
                        <table class="tades-table" cellspacing="0">
                            <thead class="tades-thead">
                                <tr style="background-color: #006EA2;">
                                    <th scope="col">Nome</th>
                                    <th scope="col">Sobrenome</th>
                                    <th scope="col">CRM</th>
                                    <th scope="col">#</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="medico" items="${MEDICOS}">

                                    <c:url var="link" value="MedicoControllerServlet">
                                        <c:param name="command" value="LOAD MEDIC" />
                                        <c:param name="medicoId" value="${medico.idMedico}" />
                                    </c:url>

                                    <c:url var="deleteLink" value="MedicoControllerServlet">
                                        <c:param name="command" value="DELETE MEDIC" />
                                        <c:param name="medicoId" value="${medico.idMedico}" />
                                    </c:url>

                                    <c:url var="editLogin" value="UsuarioControllerServlet">
                                        <c:param name="command" value="EDIT USER" />
                                        <c:param name="usuarioId" value="${usuario.idUsuario}" />
                                    </c:url>

                                    <c:url var="createLogin" value="usuario-cadastro.jsp">
                                        <c:param name="funcId" value="${medico.idMedico}" />
                                        <c:param name="nomeFuncionario" value="${medico.nome}" />
                                        <c:param name="cargo" value="Medico" />
                                    </c:url>


                                    <tr>
                                        <th scope="row">${medico.nome}</th>
                                        <td scope="row">${medico.sobrenome}</td>
                                        <td scope="row">${medico.crm}</td>
                                        <td scope="row">
                                            <a href="${link}">Atualizar</a>
                                            |
                                            <a href="${deleteLink}" onclick="if (!(confirm('Você tem certeza que deseja excluir este funcionário?')))
                                                        return false">Deletar</a>

                                            <c:if test="${medico.idUsuario ne 0 || null}">
                                                |
                                                <a href="${editLogin}">Editar Login</a>
                                            </c:if>
                                            <c:if test="${medico.idUsuario eq 0 || null}">
                                                |
                                                <a href="${createLogin}">Criar Login</a>
                                            </c:if>




                                            </td>
                                        </tr>    
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>




