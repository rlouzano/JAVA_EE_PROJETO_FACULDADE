package br.senac.sp.model.beam;

import br.senac.sp.dao.usuarioDAO;
import br.senac.sp.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class c_usuario extends Usuario {

    public Usuario u = new Usuario();

    //Metodo para redirecionamento de pagina
    public void telaCadUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("views/funcionario/cadastroFunc.jsp").forward(request, response);
    }

    //metodo para cadastrar um funcionário para acesso ao sistema
    public void cadastroUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        usuarioDAO udao = new usuarioDAO();
        String msg;
        try {
            u.setNome(request.getParameter("nome"));
            u.setCpf(request.getParameter("cpf"));
            u.setPerfil(request.getParameter("perfil"));
            u.setLogin(request.getParameter("login"));
            u.setSenha(request.getParameter("senha"));
            u.setSenhaAdmin(request.getParameter("senhaAdmin"));
            u.setFilial(request.getParameter("filial"));
            if (u.getGravar(u)) {
                msg = "Usuario Cadastrado com Sucesso";
            } else {
                msg = "Erro ao cadastrar o Usuario";
            }
        } catch (NumberFormatException e) {
            msg = "erro ao cadastrar usuario!";
            System.out.println("erro ao cadastrar usuario:" + e.getMessage());
        }
        out.println("<script>alert('" + msg + "');location.href='UsuarioController?acao=listar'</script>");
    }

    //Metodo para listar todos os funcionários cadastrados no sistemas
    public void listarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> lista = u.getPegaTodos();
        request.setAttribute("user", lista);
        request.getRequestDispatcher("views/funcionario/listarFuncionario.jsp").forward(request, response);
    }

    //Metodo para buscar os dados de um usuário para alteração
    public void alterarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        usuarioDAO udao = new usuarioDAO();
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
        Usuario user = udao.buscaPoId(id);
        if (user.getId() > 0) {
            request.setAttribute("u", user);
            request.getRequestDispatcher("views/funcionario/editarFuncionario.jsp").forward(request, response);
        } else {
            out.print("<script>alert('Funcionário não encontrado!');location.href='UsuarioController?acao=listar'</script>");
        }
    }

    //metodo para alterar os dados do usuário
    public void salvarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        usuarioDAO udao = new usuarioDAO();
        PrintWriter out = response.getWriter();
        String mensagem;
        try {
            if (!request.getParameter("id").isEmpty()) {
                u.setId(Integer.parseInt(request.getParameter("id")));
            }
            u.setNome(request.getParameter("nome"));
            u.setCpf(request.getParameter("cpf"));
            u.setPerfil(request.getParameter("perfil"));
            u.setLogin(request.getParameter("login"));
            u.setSenha(request.getParameter("senha"));
            u.setSenhaAdmin(request.getParameter("senhaAdmin"));
            u.setFilial(request.getParameter("filial"));
            if (u.getEditar(u)) {
                mensagem = "Funcionário editado com sucesso!";
            } else {
                mensagem = "Erro ao editar o Funcionário";
            }
        } catch (NumberFormatException e) {
            mensagem = "Erro ao editar o Funcionário";
            System.out.println("Erro ao editar o Funcionário:" + e.getMessage());
        }
        out.println("<script>alert('" + mensagem + "');location.href='UsuarioController?acao=listar'</script>");

    }

    //metodo para excluir um funcionário do sistema
    public void excluirUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        usuarioDAO udao = new usuarioDAO();
        PrintWriter out = response.getWriter();
        int idfunc = Integer.parseInt(request.getParameter("id"));
        if (u.getExcluir(idfunc)) {
            out.print("<script>alert('funcionário excluido com sucesso!');location.href='UsuarioController?acao=listar'</script>");
        } else {
            out.print("<script>alert('Erro ao excluir funcionário!');location.href='UsuarioController?acao=listar'</script>");
        }
    }

}
