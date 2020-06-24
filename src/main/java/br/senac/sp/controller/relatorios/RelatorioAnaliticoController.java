package br.senac.sp.controller.relatorios;

import br.senac.sp.dao.RelatorioDAO;
import br.senac.sp.model.Relatorio;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RelatorioAnaliticoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RelatorioDAO rdao = new RelatorioDAO();
        String datainicio = null;
        String datafim = null;
        String acao = request.getParameter("acao");
        switch (acao) {
            case "relatorios":
                request.getRequestDispatcher("views/relatorioAnalitico/relatoriosAnaliticos.jsp").forward(request, response);
                break;
            case "ranalitico":
                datainicio = request.getParameter("datainicio");
                datafim = request.getParameter("datafim");
                List<Relatorio> lista1 = rdao.getPegaTodosAnalitico(datainicio, datafim);
                request.setAttribute("lista", lista1);
                request.getRequestDispatcher("views/relatorioAnalitico/relatorioAnaliticoSimples.jsp").forward(request, response);
                break;
            case "rfilial":
                datainicio = request.getParameter("datainicio");
                datafim = request.getParameter("datafim");
                String filial = request.getParameter("filial");
                List<Relatorio> lista2 = rdao.getPegaTodosAnaliticoPorFilial(datainicio, datafim, filial);
                request.setAttribute("lista", lista2);
                request.getRequestDispatcher("views/relatorioAnalitico/relatorioAnaliticoFilial.jsp").forward(request, response);
                break;
            case "rcategoria":
                datainicio = request.getParameter("datainicio");
                datafim = request.getParameter("datafim");
                String categoria = request.getParameter("categoria");
                List<Relatorio> lista = rdao.getPegaTodosAnaliticoPorCategoria(datainicio, datafim, categoria);
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("views/relatorioAnalitico/relatorioAnaliticoCategoria.jsp").forward(request, response);
                break;
            case "rcliente":
                datainicio = request.getParameter("datainicio");
                datafim = request.getParameter("datafim");
                String cliente = request.getParameter("cliente");
                List<Relatorio> lista3 = rdao.getPegaTodosAnaliticoPorCliente(datainicio, datafim, cliente);
                request.setAttribute("lista", lista3);
                request.getRequestDispatcher("views/relatorioAnalitico/relatorioAnaliticoCliente.jsp").forward(request, response);
                break;
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
