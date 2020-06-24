<%-- 
    Document   : cadastroFunc.jsp
    Created on : 22/04/2020, 14:05:31
    Author     : rafae
--%>

<%@page import="br.senac.sp.dao.usuarioDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.senac.sp.model.Usuario"%>
<%    
    //Acessando a session e pegando objeto usuario
    Usuario usuario = (Usuario) request.getSession().getAttribute("loginController");
    usuarioDAO dao = new usuarioDAO();
    Usuario user = dao.buscaPoId(usuario.getId());
    if (!(user.getPerfil().equals("ADMIN"))) {
        out.print("<script>alert('Acesso negado, somente os administradores podem cadastrar funcionários');location.href = 'menuController?acao=menu';</script>");
    }
%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Merk | Funcionários</title>
        <link rel="stylesheet" href="css/cadUser.css">
    </head>
    <body>
        <section>
            <form action="UsuarioController" method="GET">
                <input type="hidden" name="acao" value="cadastrar"/>
                <h1>Crie a sua conta MerkExpress</h1>
                <label id="lblNome">Nome:</label> 
                <input type="text" name="nome" />
                <br></br>
                <hr>
                </hr>
                <label id="lblCpf">Cpf:</label> 
                <input type="text" placeholder="Seu CPF sem traço" maxlength="14" name="cpf"/>
                <br></br>
                <hr>
                </hr>
                <label id="perfil">Perfil:</label> 
                <select name="perfil">
                    <option>ADMIN</option>
                    <option>VENDEDOR</option>
                    <option>GESTOR</option>
                </select>
                <br></br>
                <hr>
                </hr>
                <label id="user">Usuário:</label> 
                <input type="text" placeholder="Qual será seu usuário no sistema?" name="login"/>
                <br></br>
                <hr>
                </hr>
                <label id="senha">Senha:</label> 
                <input type="password" name="senha"/>
                <br></br>
                <hr>
                </hr>
                <label id="adm">Senha admin:</label> 
                <input id="txtAdm" type="password" name="senhaAdmin"/>
                <br id="adm"></br>
                <hr id="hrAdm">
                </hr>
                <br></br>
                <label id="filial">Filial:</label> 
                <select name="filial">
                    <option >CDSP</option>
                    <option >CDRJ</option>
                </select>
                <br></br>

                <input type="submit" id="cadastrar" value="cadastrar">
                <br></br>
                <a href="UsuarioController?acao=listar">Listar Funcionários</a>
            </form>
        </section>
    </body>
</html>
