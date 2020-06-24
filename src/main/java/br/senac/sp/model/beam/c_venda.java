package br.senac.sp.model.beam;

import br.senac.sp.dao.ClienteDAO;
import br.senac.sp.dao.RelatorioDAO;
import br.senac.sp.dao.produtoDAO;
import br.senac.sp.dao.vendaDAO;
import br.senac.sp.model.Cliente;
import br.senac.sp.model.Relatorio;
import br.senac.sp.model.Usuario;
import br.senac.sp.model.Venda;
import br.senac.sp.model.produto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class c_venda extends Venda {

    //   public produtoDAO pdao = new produtoDAO();
    //  public Venda v = new Venda();
    //metodo para buscar um produto por um cpf ou o codigo do produto
    public void vendas(HttpServletRequest request, HttpServletResponse response, String pesquisa) throws ServletException, IOException {
        produtoDAO pdao = new produtoDAO();
        try {
       /*     response.setContentType("text/html;charset=UTF-8");
            System.out.println(pesquisa);
            List<produto> prod = pdao.getBuscaProduto(pesquisa);
            request.setAttribute("prod", prod);*/
            request.getRequestDispatcher("views/vendas/vendas.jsp").forward(request, response);
        } catch (ServletException e) {
            System.out.println("ERRO SERVLET AO ACESSAR TELA DE VENDAS: " + e.getMessage());
            response.sendRedirect("menuController?acao=menu");
        }
    }

    //metodo usado para incluir produto no carrinho
    public void incluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        vendaDAO vdao = new vendaDAO();
        Venda v = new Venda();
        try {
            response.setContentType("text/html;charset=UTF-8");
            v.setNomeproduto(request.getParameter("nome"));
            v.setCodigoproduto(request.getParameter("codigo"));
            v.setCategoria(request.getParameter("categoria"));
            v.setQtd(Integer.parseInt(request.getParameter("qtd")));
            v.setPreco_unitario(Double.parseDouble(request.getParameter("preco")));
            v.setUsuario(request.getParameter("login"));
            v.setNomecliente(request.getParameter("nomecliente"));
            v.setCpfcliente(request.getParameter("cpfcliente"));
            v.setNome_filial(request.getParameter("nomefilial"));
            vdao.getGravar(v, v.getUsuario());
            request.getRequestDispatcher("views/vendas/vendas.jsp").forward(request, response);
        } catch (ServletException e) {
            System.out.println("ERRO SERVLET AO INCLUIR PRODUTO: " + e.getMessage());
            response.sendRedirect("vendaController?acao=vendas");
        }
    }

    //metodo para listar todos produtos no carrinhho
    public void carrinho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Venda v = new Venda();
        vendaDAO vdao = new vendaDAO();
        try {
            response.setContentType("text/html;charset=UTF-8");
            String idcarrinho = request.getParameter("idcarrinho");
            v.setId(Integer.parseInt(idcarrinho));
            System.out.println("ENCONTREI ID: " + v.getId());
            List<Venda> lista = vdao.getPegaTudoPorId(v.getId());
            request.setAttribute("lista", lista);
            request.getRequestDispatcher("views/vendas/carrinho.jsp").forward(request, response);
        } catch (ServletException e) {
            System.out.println("ERRO SERVLET AO ACESSAR O CARRINHO: " + e.getMessage());
            response.sendRedirect("vendaController?acao=vendas");
        }
    }

// Se o usuario incluir um produto igual no mesmo carrinho, este metodo soma a quantidade total do mesmo produto
// É usado quando o usuário tenta alterar a quantidade do produto no carrinho e o valor é maior que do estoque
    public int calculaQtd(int id, String cod) {
        produtoDAO pdao = new produtoDAO();
        vendaDAO vdao = new vendaDAO();
        int soma = 0, qtd_prod = 0, qtd_venda = 0;
        List<produto> pd = pdao.pegaTodosPorCod(cod);
        for (produto pdq : pd) {
            qtd_prod += pdq.getQtd();
        }
        List<Venda> vd = super.getPegaTudo();
        for (Venda venda : vd) {
            if (id == venda.getId()) {
                qtd_venda += venda.getQtd();
            }
        }
        soma = (qtd_prod + qtd_venda);
        return soma;
    }

// Este metodo altera a quantidade de produto no carrinho se for menor ou igual ao que tem no estoque
    public void alterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            int i = Integer.parseInt(request.getParameter("id"));
            int q = Integer.parseInt(request.getParameter("qtd"));
            String cod = request.getParameter("codproduto");
            vendaDAO vlista = new vendaDAO();
            int soma;
            do {
                soma = calculaQtd(i, cod);
                if (q <= 0) {
                    out.println("<html><body><script>alert('Quantidade não pode ser zero ou menor');window.location.replace('vendaController?acao=vendas');</script></body></html>");
                } else if (soma > q) {
                    vlista.getEditar(q, i);
                    request.getRequestDispatcher("views/vendas/vendas.jsp").forward(request, response);
                } else {
                    out.println("<html><body><script>alert('Quantidade maior que o estoque');window.location.replace('vendaController?acao=vendas');</script></body></html>");
                }
            } while (soma > q);
        } catch (Exception e) {
            System.out.println("ERRO SERVLET AO ALTERAR QTD PRODUTO: " + e.getMessage());
            response.sendRedirect("vendaController?acao=vendas");
        }
    }

    //Este metodo excluir o item do carrinho
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        vendaDAO vdao = new vendaDAO();
        try {
            response.setContentType("text/html;charset=UTF-8");
            int idexcluir = Integer.parseInt(request.getParameter("id"));
            vdao.getExcluir(idexcluir);
            request.getRequestDispatcher("views/vendas/vendas.jsp").forward(request, response);
        } catch (ServletException e) {
            System.out.println("ERRO SERVLET AO EXCLUIR PRODUTO DO CARRINHO: " + e.getMessage());
            response.sendRedirect("vendaController?acao=vendas");
        }
    }

    //Este metodo é usado quando o cliente cancela a venda, então o usuário seleciona cancelar que todos os itens do carrinho são excluidos
    public void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        vendaDAO vdao = new vendaDAO();
        try {
            response.setContentType("text/html;charset=UTF-8");
            vdao.getExcluirTodos();
            request.getRequestDispatcher("views/vendas/vendas.jsp").forward(request, response);
        } catch (ServletException e) {
            System.out.println("ERRO SERVLET AO CANCELAR TODOS OS ITENS DO CARRINHO: " + e.getMessage());
            response.sendRedirect("vendaController?acao=vendas");
        }
    }

    //Este metodo é usado para incluir toda a venda na tabela relatório do banco de dados e excluir todos os dados da tabela venda
    public void finalizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        produtoDAO pdao = new produtoDAO();
        vendaDAO vdao = new vendaDAO();
        String mensagem = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            Relatorio r = new Relatorio();
            RelatorioDAO rd = new RelatorioDAO();
            Usuario usuario = (Usuario) request.getSession().getAttribute("loginController");
            List<Venda> listaVenda = vdao.pegaTodos();
            List<produto> listaProd = pdao.getPegaProdutos();
            int qtdprod = 0;
            for (produto prodqtd : listaProd) {
                qtdprod = prodqtd.getQtd();
            }
            for (Venda venda : listaVenda) {
                pdao.getAlteraProdutoVenda((qtdprod - venda.getQtd()), venda.getCodigoproduto());
                if (rd.getGravar(venda.getNomeproduto(), venda.getCodigoproduto(),
                    venda.getCategoria(), venda.getQtd(), venda.getPreco_unitario(),
                   (venda.getQtd() * venda.getPreco_unitario()), venda.getUsuario(),
                    venda.getNomecliente(), venda.getCpfcliente(), usuario.getFilial(), venda.getFormpagto())) {
                    mensagem = "Venda efetuado com sucesso!";
                    vdao.ExcluirTodos();
                    request.getRequestDispatcher("views/vendas/vendas.jsp").forward(request, response);
                } else {
                    mensagem = "Erro ao efetuar a venda!";
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(mensagem + e.getMessage());
            response.sendRedirect("vendaController?acao=vendas");
        }
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + mensagem + "');location.href='vendaController?acao=vendas'</script>");
    }

    // metodo para redirecionamento de pagina
    public void pagamento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("views/vendas/clientevenda.jsp");
        rd.forward(request, response);
    }

    // metodo usado para buscar os cliente por um cpf digitado
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Venda v = new Venda();
        String cpf = request.getParameter("cpf");
        v.setNomecliente(request.getParameter("nomecli"));
        v.setCpfcliente(request.getParameter("cpfcli"));
        ClienteDAO cdao = new ClienteDAO();
        List<Cliente> clista = cdao.BuscaCliente(cpf);
        System.out.println(clista);
        request.setAttribute("clista", clista);
        request.getRequestDispatcher("views/vendas/clientevenda.jsp").forward(request, response);
    }

    //metodo usado para incluir um cliente na tabela venda do banco de dados
    public void incluirCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Venda v = new Venda();
        vendaDAO vdao = new vendaDAO();
        Usuario usuario = (Usuario) request.getSession().getAttribute("loginController");
        v.setNomecliente(request.getParameter("nomecliente"));
        v.setCpfcliente(request.getParameter("cpfcliente"));
        vdao.getCadastroClienteVenda(v.getNomecliente(), v.getCpfcliente(), usuario.getFilial());
        request.getRequestDispatcher("views/vendas/clientevenda.jsp").forward(request, response);
    }

    // metodo para redirecionamento de pagina
    public void proximo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("views/vendas/formaPagto.jsp").forward(request, response);
    }

}
