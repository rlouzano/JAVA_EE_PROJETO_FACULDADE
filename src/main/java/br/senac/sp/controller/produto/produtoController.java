package br.senac.sp.controller.produto;

import br.senac.sp.model.beam.c_produto;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class produtoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        c_produto c = new c_produto();
        String acao = request.getParameter("acao");
        try (PrintWriter out = response.getWriter()) {
            switch (acao) {
                case "produto":
                    c.telaCadProduto(request, response);
                    break;
                case "Cadastrar":
                   c.cadastroProduto(request, response);
                    break;
                case "listar":
                    c.listarProduto(request, response);
                    break;
                case "alterar":
                    c.alterarProduto(request, response);
                    break;
                case "salvar":
                    c.salvarProduto(request, response);
                    break;
                case "excluir":
                    c.excluirProduto(request, response);
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
    }// </editor-fold>

}
