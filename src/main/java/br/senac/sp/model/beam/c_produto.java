package br.senac.sp.model.beam;

import br.senac.sp.dao.produtoDAO;
import br.senac.sp.model.Filial;
import br.senac.sp.model.produto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class c_produto extends produto {

    public produto p = new produto();

    //Metodo para redirecionamento de pagina
    public void telaCadProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("views/produto/cadastroProd.jsp").forward(request, response);
    }

    //metodo para cadastrar um produto no banco de dados
    public void cadastroProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Filial f = new Filial();
        produtoDAO pdao = new produtoDAO();
        PrintWriter out = response.getWriter();
        String msg;
        try {
            p.setNome(request.getParameter("nome"));
            p.setCodigo(request.getParameter("codigoproduto"));
            p.setCategoria(request.getParameter("categoria"));
            p.setQtd(Integer.parseInt(request.getParameter("qtd")));
            p.setPreco(Double.parseDouble(request.getParameter("preco")));
            f.getNomeFilial(request.getParameter("filial"));
            if (pdao.getGravarProduto(p, f.getNomeFilial())) {
                msg = "Produto Cadastrado com Sucesso";
            } else {
                msg = "Erro ao cadastrar o produto";
            }
        } catch (NumberFormatException e) {
            msg = "erro ao cadastrar cliente!";
            System.out.println("erro ao cadastrar cliente:" + e.getMessage());
        }
        out.println("<script>alert('" + msg + "');location.href='produtoController?acao=listar'</script>");
    }

    //Metodo para listar os produto cadastrados na tela para o usuário
    public void listarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        produtoDAO pdao = new produtoDAO();
        PrintWriter out = response.getWriter();
        try {
            List<produto> pegatudo = pdao.getPegaProdutos();
            request.setAttribute("pegatudo", pegatudo);
            request.getRequestDispatcher("views/produto/listarProduto.jsp").forward(request, response);
        } catch (ServletException e) {
            System.out.println("ERRO AO LISTA OS PRODUTOS: " + e.getMessage());
        }
    }

    //Metodo para lista o produto que o usuário deseja alterar
    public void alterarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        produtoDAO pdao = new produtoDAO();
        PrintWriter out = response.getWriter();
        int idproduto = Integer.parseInt(request.getParameter("idproduto"));
        produto prod = pdao.getBuscaProdutoPorId(idproduto);
        if (prod.getIdproduto() > 0) {
            request.setAttribute("p", prod);
            request.getRequestDispatcher("views/produto/editarProduto.jsp").forward(request, response);
        } else {
            out.print("<script>alert('Produto não encontrado!');location.href='./listarProduto.jsp'</script>");
        }
    }

    //metodo para alterar os dados do produto
    public void salvarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        produtoDAO pdao = new produtoDAO();
        PrintWriter out = response.getWriter();
        String mensagem;
        try {
            if (!request.getParameter("id").isEmpty()) {
                p.setIdproduto(Integer.parseInt(request.getParameter("id")));
            }
            p.setNome(request.getParameter("nome"));
            p.setCodigo(request.getParameter("codigoproduto"));
            p.setCategoria(request.getParameter("categoria"));
            p.setQtd(Integer.parseInt(request.getParameter("qtd")));
            p.setPreco(Double.parseDouble(request.getParameter("preco")));
            p.setId(Integer.parseInt(request.getParameter("idfilial")));
            if (pdao.getAlterarProduto(p)) {
                mensagem = "Produto editado com sucesso!";
            } else {
                mensagem = "Erro ao editar o produto";
            }
        } catch (NumberFormatException e) {
            mensagem = "Erro ao editar o produto";
            System.out.println("Erro ao editar o produto:" + e.getMessage());
        }
        out.println("<script>alert('" + mensagem + "');location.href='produtoController?acao=listar'</script>");
    }

    //Metodo para excluir um produto do estoque
    public void excluirProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        produtoDAO pdao = new produtoDAO();
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("idproduto"));
        if (pdao.getExcluir(id)) {
            out.print("<script>alert('Produto excluido com sucesso!');location.href='produtoController?acao=listar'</script>");
        } else {
            out.print("<script>alert('Erro ao excluir produto!');location.href='produtoController?acao=listar'</script>");
        }
    }

}
