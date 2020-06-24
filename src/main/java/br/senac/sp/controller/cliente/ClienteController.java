package br.senac.sp.controller.cliente;

import br.senac.sp.model.beam.c_cliente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClienteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        c_cliente c = new c_cliente();
        String acao = request.getParameter("acao");
        try (PrintWriter out = response.getWriter()) {
            switch (acao) {
                case "listar":
                    c.listarCliente(request, response);
                    break;
                case "alterar":
                    c.alterarCliente(request, response);
                    break;
                case "Salvar":
                    c.salvarCliente(request, response);
                    break;
                case "novo":
                    c.novoCliente(request, response);
                    break;
                case "Cadastrar":
                    c.cadastrarCliente(request, response);
                    break;
                case "excluir":
                    c.excluirCliente(request, response);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
