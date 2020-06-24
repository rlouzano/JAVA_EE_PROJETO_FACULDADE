<%-- 
    Document   : relatoriosSinteticos
    Created on : 22/05/2020, 18:19:03
    Author     : rafae
--%>

<%@page import="br.senac.sp.dao.usuarioDAO"%>
<%@page import="br.senac.sp.model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%    //Acessando a session e pegando objeto usuario
    Usuario usuario = (Usuario) request.getSession().getAttribute("loginController");
    usuarioDAO dao = new usuarioDAO();
    Usuario user = dao.buscaPoId(usuario.getId());
    if (!(user.getPerfil().equals("ADMIN")|| user.getPerfil().equals("GESTOR"))) {
        out.print("<script>alert('Acesso negado, somente os administradores e gestores podem acessar os relatórios');location.href = 'menuController?acao=menu';</script>");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/relatorioSintetico.css">
    </head>
    <body>
        <section id="sec">
        <a href="RelatorioSinteticoController?acao=simples">Sintético - Simples</a></br>
        <a href="RelatorioSinteticoController?acao=rfilial">Sintético - Por Filial</a></br>
        <a href="RelatorioSinteticoController?acao=rcategoria">Sintético - Por Categoria de Produto</a></br>
        <a href="RelatorioSinteticoController?acao=rcliente">Sintético - Por Cliente</a></br>
        </section>>
    </body>
</html>
