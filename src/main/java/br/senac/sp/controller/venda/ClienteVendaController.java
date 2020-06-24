package br.senac.sp.controller.venda;

import br.senac.sp.model.beam.c_venda;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClienteVendaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        c_venda c = new c_venda();
        String acao = request.getParameter("acao");
        switch (acao) {
            case "pagamento":
                c.pagamento(request, response);
                break;
            case "buscar":
                c.buscar(request, response);
                break;
            case "incluir":
                c.incluirCliente(request, response);
                break;
            case "proximo":
                c.proximo(request, response);
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
