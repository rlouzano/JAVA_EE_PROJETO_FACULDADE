package br.senac.sp.controller.usuario;

import br.senac.sp.model.beam.c_login;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rafae
 */
@WebServlet("/loginController")
public class loginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        c_login login = new c_login();
        login.RedirecionaLogin(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        c_login login = new c_login();
        login.AutenticaUsuario(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
