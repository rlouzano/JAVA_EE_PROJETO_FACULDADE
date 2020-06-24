package br.senac.sp.controller.venda;

import br.senac.sp.dao.vendaDAO;
import br.senac.sp.model.Venda;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rafae
 */
public class formaPagtoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Venda v = new Venda();
        vendaDAO vdao = new vendaDAO();
        String tipo = request.getParameter("pagto");
        System.out.println(tipo);
        vdao.getInserirPagto(tipo);
        request.getRequestDispatcher("views/vendas/formaPagto.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
