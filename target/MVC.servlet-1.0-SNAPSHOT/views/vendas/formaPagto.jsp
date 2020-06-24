<%-- 
    Document   : formapagamento
    Created on : 14/05/2020, 17:39:43
    Author     : rafae
--%>
<%@page import="br.senac.sp.model.FormaPagamento"%>
<%@page import="br.senac.sp.dao.FormaPagamentoDAO"%>
<%@page import="br.senac.sp.model.Usuario"%>
<%@page import="br.senac.sp.model.Venda"%>
<%@page import="br.senac.sp.dao.vendaDAO"%>
<%@page import="java.util.List"%>
<%@page import="br.senac.sp.model.Cliente"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    //Acessando a session e pegando objeto usuario
    Usuario usuario = (Usuario) request.getSession().getAttribute("loginController");
    //Bem vindo <%= usuario.getLogin()
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forma de pagamento</title>
        <link rel="stylesheet" href="css/finalizarPgto.css">
    </head>
    <body>
        <form action="formaPagtoController" method="GET">
            <input type="hidden" name="acao" value="pagto"/>
            <div class="titulo">
                <h1>FORMA DE PAGAMENTO</h1>
            </div>
            <div class="forma-pagamento">
                <label for="pagto">Forma de Pagamento:</label>
                <select name="pagto" var="${param.pagto}" style="margin-left: 10px; width: 150px">
                    <jsp:useBean id="fdao" class="br.senac.sp.dao.FormaPagamentoDAO"/> 
                    <c:forEach var="f" items="${fdao.pegaTodos()}">
                        <option name="pagto" var="${param.Inserir}">${f.credito}</option>
                        <option name="pagto" var="${param.Inserir}">${f.debito}</option>
                        <option name="pagto" var="${param.Inserir}">${f.dinheiro}</option>
                        <option name="pagto" var="${param.Inserir}">${f.alimentacao}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="Inserir"/>
                <div style="margin-left: -14px; margin-top: 20px;">
                    <label id="confimPgt">O pagamento será em:</label>
                    <input type="text" value="${param.pagto }" style="margin-left: 10px; width: 198px"/>

                </div>
                <a href="javascript:confirmaVenda()">Finalizar Venda</a>

            </div>
        </form>
        <script type="text/javascript">
            function confirmaVenda() {
                if (window.confirm('Tem certeza que deseja finalizar a venda com pagamento em ${param.pagto }? ')) {
                    location.href = "vendaController?acao=finalizar";
                }
            }
        </script>
    </body>

</html>