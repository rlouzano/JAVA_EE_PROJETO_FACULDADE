
<%@page import="br.senac.sp.model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%    //Acessando a session e pegando objeto usuario
    Usuario usuario = (Usuario) request.getSession().getAttribute("loginController");
%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF8">
        <title>Merk | Cliente</title>
        <link rel="stylesheet" href="css/editarCliente.css">
    </head>

    <body>
        <form action="ClienteController" method="GET">
            <section id="fundoTabela">
                <input type="hidden" name="acao" value="Salvar"/>
                <input type="hidden" name="idCliente" value="${c.idCliente}"/>
                <label id="nome">Nome:</label>
                <input type="text" name="nome" value="${c.nome}"> <br/><br/>
                <hr>
                <label id="sexo">Sexo:</label>
                <input type="text" name="sexo" value="${c.sexo}"> <br/> 
                <br/>
                <hr>
                <label id="cpf">CPF:</label>
                <input type="text" name="CPF" value="${c.cpf}"> <br/> 
                <br/>
                <hr>
                <label id="rg">RG:</label>
                <input type="text" name="rg" value="${c.rg}"> <br/>  
                <br/>
                <hr>
                <label id="ec">Estado Civil:</label>
                <input type="text" name="estadoCivil" value="${c.estadoCivil}"> <br/> 
                <br/>
                <hr>
                <label id="tel">Telefone:</label>
                <input type="text" name="telefone" value="${c.telefone}"> <br/>  
                <br/>
                <hr>
                <label id="dn">Data de Nasc:</label>
                <input type="text" name="dataNascimento" value="${c.dataNascimento}"> <br/> 
                <br/>
                <hr>
                <label id="email">Email:</label>
                <input type="text" name="email" value="${c.email}"> <br/>           
                <br/>
                <hr>
                <label id="log">Logradouro:</label>
                <input type="text" name="logradouro" value="${c.logradouro}"> <br/>            
                <br/>
                <hr>
                <label id="n">Número Casa:</label>
                <input type="text" name="numeroCasa" value="${c.numeroCasa}"> <br/>             
                <br/>
                <hr>
                <label id=comp" style="margin-left: 45px">Complemento:</label>
                <input type="text" name="complemento" value="${c.complemento}"> <br/> 
                <br/>
                <hr>
                <label id="cep">CEP:</label>
                <input type="text" name="CEP" value="${c.CEP}"> <br/>         
                <br/>
                <hr>
                <label id="cid">Cidade:</label>
                <input type="text" name="cidade" value="${c.cidade}"> <br/>       
                <br/>
                <hr>
                <label id="uf">UF:</label>
                <input type="text" name="uf" value="${c.uf}"> <br/> 
                <br/>
                <hr>
                <input id="salvar" type="submit"  value="Salvar">
            </section>
        </form>
    </body>
</html>