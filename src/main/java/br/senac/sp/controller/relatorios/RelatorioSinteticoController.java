package br.senac.sp.controller.relatorios;

import br.senac.sp.dao.RelatorioDAO;
import br.senac.sp.model.Relatorio;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rafae
 */
public class RelatorioSinteticoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RelatorioDAO rdao = new RelatorioDAO();
        String datainicio = null;
        String datafim = null;
        String acao = request.getParameter("acao");

        switch (acao) {
            case "relatorio":
                request.getRequestDispatcher("views/relatorioSintetico/relatoriosSinteticos.jsp").forward(request, response);
                break;
            case "simples":
                datainicio = request.getParameter("datainicio");
                datafim = request.getParameter("datafim");
                List<Relatorio> lista = rdao.getPegaTodosSintetico(datainicio, datafim);
                request.setAttribute("rlista", lista);
                request.getRequestDispatcher("views/relatorioSintetico/relatorioSinteticoSimples.jsp").forward(request, response);
                break;
            case "rfilial":
                datainicio = request.getParameter("datainicio");
                datafim = request.getParameter("datafim");
                String filial = request.getParameter("filial");
                List<Relatorio> lista1 = rdao.getPegaTodosSinteticoPorFilial(datainicio, datafim, filial);
                request.setAttribute("rlista", lista1);
                request.getRequestDispatcher("views/relatorioSintetico/relatorioSinteticoFilial.jsp").forward(request, response);
                break;
            case "rcategoria":
                datainicio = request.getParameter("datainicio");
                datafim = request.getParameter("datafim");
                String categoria = request.getParameter("categoria");
                List<Relatorio> lista2 = rdao.getPegaTodosSinteticoPorCategoria(datainicio, datafim, categoria);
                request.setAttribute("rlista", lista2);
                request.getRequestDispatcher("views/relatorioSintetico/relatorioSinteticoCategoria.jsp").forward(request, response);
                break;
            case "rcliente":
                datainicio = request.getParameter("datainicio");
                datafim = request.getParameter("datafim");
                String cliente = request.getParameter("cliente");
                List<Relatorio> lista3 = rdao.getPegaTodosSinteticoPorCliente(datainicio, datafim, cliente);
                request.setAttribute("rlista", lista3);
                request.getRequestDispatcher("views/relatorioSintetico/relatorioSinteticoCliente.jsp").forward(request, response);
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
    }// </editor-fold>

}
