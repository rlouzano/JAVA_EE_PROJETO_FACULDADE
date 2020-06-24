package br.senac.sp.model.beam;

import br.senac.sp.dao.ClienteDAO;
import br.senac.sp.model.Cliente;
import br.senac.sp.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class c_cliente extends Cliente {

    //Metodo para listar todos os cliente cadastrados na tela
    public void listarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente c = new Cliente();
        ClienteDAO cdao = new ClienteDAO();
        try {
            List<Cliente> cli = cdao.pegaTodos();
            request.setAttribute("cliente", cli);
            request.getRequestDispatcher("views/cliente/listarClientes.jsp").forward(request, response);
        } catch (ServletException e) {
            System.out.println("ERRO SERVLET AO ACESSAR TELA DE CLIENTE: " + e.getMessage());
        }
    }

    //Metodo para listar o cliente que o usuário deseja alterar na tela
    public void alterarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClienteDAO cdao = new ClienteDAO();
        Cliente c = new Cliente();
        PrintWriter out = response.getWriter();
        c.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
        Cliente clientes = cdao.buscaPorId(c.getIdCliente());
        if (clientes.getIdCliente() > 0) {
            request.setAttribute("c", clientes);
            request.getRequestDispatcher("views/cliente/editarCliente.jsp").forward(request, response);
        } else {
            out.print("<script>alert('Cliente não encontrado!');location.href='./listarClientes.jsp'</script>");
        }
    }

    //metodo para alterar um cliente 
    public void salvarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClienteDAO cdao = new ClienteDAO();
        Cliente c = new Cliente();
        PrintWriter out = response.getWriter();

        String mensagem;
        try {
            if (!request.getParameter("idCliente").isEmpty()) {
                c.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
            }
            c.setNome(request.getParameter("nome"));
            c.setSexo(request.getParameter("sexo"));
            c.setCpf(request.getParameter("CPF"));
            c.setRg(request.getParameter("rg"));
            c.setEstadoCivil(request.getParameter("estadoCivil"));
            c.setTelefone(request.getParameter("telefone"));
            c.setDataNascimento(request.getParameter("dataNascimento"));
            c.setEmail(request.getParameter("email"));
            c.setLogradouro(request.getParameter("logradouro"));
            c.setNumeroCasa(request.getParameter("numeroCasa"));
            c.setComplemento(request.getParameter("complemento"));
            c.setCEP(request.getParameter("CEP"));
            c.setCidade(request.getParameter("cidade"));
            c.setUf(request.getParameter("uf"));
            if (cdao.alterar(c.getNome(), c.getSexo(), c.getCpf(), c.getRg(), c.getEstadoCivil(), c.getTelefone(), c.getDataNascimento(), c.getEmail(), c.getLogradouro(), c.getNumeroCasa(), c.getComplemento(), c.getCEP(), c.getCidade(), c.getUf(), c.getIdCliente())) {
                mensagem = "Cliente Cadastrado com Sucesso";

            } else {
                mensagem = "Erro ao cadastrar Cliente";
            }
        } catch (NumberFormatException e) {
            mensagem = "erro ao cadastrar cliente!";
            System.out.println("erro ao cadastrar cliente:" + e.getMessage());
        }
        out.println("<script>alert('" + mensagem + "');location.href='ClienteController?acao=listar'</script>");

    }

    //Metodo para redirecionamento de pagina
    public void novoCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("views/cliente/cadastrarCliente.jsp").forward(request, response);
    }

    //Metodo para cadastrar um cliente novo no banco de dados
    public void cadastrarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClienteDAO cdao = new ClienteDAO();
        Cliente c = new Cliente();
        Usuario usuario = (Usuario) request.getSession().getAttribute("loginController");
        PrintWriter out = response.getWriter();
        String msg;
        try {
            c.setNome(request.getParameter("nome"));
            c.setSexo(request.getParameter("sexo"));
            c.setCpf(request.getParameter("CPF"));
            c.setRg(request.getParameter("rg"));
            c.setEstadoCivil(request.getParameter("estadoCivil"));
            c.setTelefone(request.getParameter("telefone"));
            c.setDataNascimento(request.getParameter("dataNascimento"));
            c.setEmail(request.getParameter("email"));
            c.setLogradouro(request.getParameter("logradouro"));
            c.setNumeroCasa(request.getParameter("numeroCasa"));
            c.setComplemento(request.getParameter("complemento"));
            c.setCEP(request.getParameter("CEP"));
            c.setCidade(request.getParameter("cidade"));
            c.setUf(request.getParameter("uf"));
            if (cdao.cadastrar(usuario.getId(), c.getNome(), c.getSexo(), c.getCpf(), c.getRg(), c.getEstadoCivil(), c.getTelefone(), c.getDataNascimento(), c.getEmail(), c.getLogradouro(), c.getNumeroCasa(), c.getComplemento(), c.getCEP(), c.getCidade(), c.getUf())) {
                msg = "Cliente Cadastrado com Sucesso";
            } else {
                msg = "Erro ao cadastrar Cliente";
            }
        } catch (Exception e) {
            msg = "erro ao cadastrar cliente!";
            System.out.println("erro ao cadastrar cliente:" + e.getMessage());
        }
        out.println("<script>alert('" + msg + "');location.href='ClienteController?acao=listar'</script>");

    }

    //Metodo para excluir um cliente do banco de dados
    public void excluirCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClienteDAO cdao = new ClienteDAO();
        Cliente c = new Cliente();
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("idCliente"));
        if (cdao.excluir(id)) {
            out.print("<script>alert('Cliente excluido com sucesso!');location.href='ClienteController?acao=listar'</script>");
        } else {
            out.print("<script>alert('Erro ao excluir cliente!');location.href='ClienteController?acao=listar'</script>");
        }
    }
}
