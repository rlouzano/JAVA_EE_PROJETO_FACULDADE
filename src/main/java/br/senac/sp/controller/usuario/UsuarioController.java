package br.senac.sp.controller.usuario;

import br.senac.sp.model.beam.c_usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rafae
 */
public class UsuarioController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        c_usuario c = new c_usuario();
        String acao = request.getParameter("acao");
        switch (acao) {
            case "usuario":
                c.telaCadUsuario(request, response);
                break;
            case "cadastrar":
                c.cadastroUsuario(request, response);
                break;
            case "listar":
                c.listarUsuario(request, response);
                break;
            case "alterar":
                c.alterarUsuario(request, response);
                break;
            case "salvar":
                c.salvarUsuario(request, response);
                break;
            case "excluir":
                c.excluirUsuario(request, response);
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
