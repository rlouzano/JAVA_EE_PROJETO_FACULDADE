<%-- 
    Document   : relatorios
    Created on : 22/05/2020, 17:36:27
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
        <title>Relatórios</title>
        <link rel="stylesheet" href="css/relatorioAnalitico.css">
    </head>
    <body>
        <section id="sec">
        <a href="RelatorioAnaliticoController?acao=ranalitico">Analitico - Simples</a></br>
        <a href="RelatorioAnaliticoController?acao=rfilial">Analitico - Por Filial</a></br>
        <a href="RelatorioAnaliticoController?acao=rcategoria">Analitico - Por Categoria de Produto</a></br>
        <a href="RelatorioAnaliticoController?acao=rcliente">Analitico - Por Cliente</a></br>
        </section>
    </body>
</html>
