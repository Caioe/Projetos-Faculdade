<%-- 
    Document   : login
    Created on : 24/10/2018, 23:30:24
    Author     : Yury Cavalcante
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Hospital Tades</title>

        <link type="text/css" rel="stylesheet" href="client/css/login.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/css?family=Kodchasan:400,600" rel="stylesheet">
        
    </head>
    <body>
        <div class="login-content">
            <div class="login-container">
                <span style="color:#006EA2; font-size: 3rem; font-weight: 800; padding-bottom:50px;">Ops! :(</span>
                <form name="loginForm" action="UsuarioControllerServlet" method="POST">
                    
                    <input type="hidden" name="command" value="LOGIN">
                    
                    <div class="form-inline">
                         <label for="usuario">Usuário</label>
                         <input type="text" name="usuario" class="form-control">
                    </div>
                    <br/>
                    <div class="form-inline">
                        <label for="senha">Senha</label>
                        <input type="password" name="senha" class="form-control">
                    </div>
                    <br />
                    <button type="submit" name="login" value="Login" class="btn-primary">Login</button>
                </form>
                <span style="color:red; font-size: 1.3rem; font-weight: 800; padding-top:50px;">Login ou senha estão incorretos. Por favor, insira novamente.</span>
            </div>
            <div class="logo-container">
                <div class="main-title"><i class="fas fa-hospital-alt" style="padding-right: 20px; color: #FA4860;"></i>hospital tades</div>
            </div>
        </div>
    </body>
</html>
