package br.senac.sp.model.beam;

import br.senac.sp.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class c_login extends Usuario {

    public void AutenticaUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        Usuario autenticado = super.getBuscaUsuario(login);
        ValidaLoginSenha(autenticado, senha, request, response);
    }

    public void ValidaLoginSenha(Usuario autenticado, String senha, HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        if (autenticado == null) {
            out.print("<script>alert('Usuário/senha invalido(s).');location.href='loginController?acao=login';</script>");
        } else {
                if (autenticado.autentica(senha)) {
                    HttpSession sessao = request.getSession();
                    sessao.setMaxInactiveInterval(60 * 3);
                    sessao.setAttribute("loginController", autenticado);
                    response.sendRedirect("menuController?acao=menu");
                } else {
                    out.print("<script>alert('Usuário/senha invalido(s).');location.href='loginController?acao=login';</script>");
                }
        }
    }

    public void RedirecionaLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        if (acao.equals("login")) {
            HttpSession sessao = request.getSession();
            sessao.removeAttribute("usuario");
            request.getRequestDispatcher("views/login/login.jsp").forward(request, response);
        }
    }

}
