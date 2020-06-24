package br.senac.sp.dao;

import br.senac.sp.jdbc.ConexaoDB;
import br.senac.sp.model.Cliente;
import br.senac.sp.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends Cliente {

    /**
     * Cadastra os dados do cliente no banco de dados
     *
     * @param nome
     * @param sexo
     * @param cpf
     * @param rg
     * @param estadoCivil
     * @param telefone
     * @param nascimento
     * @param email
     * @param logradouro
     * @param numero
     * @param complemento
     * @param cep
     * @param cidade
     * @param uf
     * @return verdadeiro se os dados foram cadastrado com sucesso
     */
    public boolean cadastrar(int id, String nome, String sexo, String cpf, String rg, String estadoCivil, String telefone, String nascimento, String email, String logradouro, String numero, String complemento, String cep, String cidade, String uf) {
        String sql = "insert into cliente(idCliente, nomecli, sexo, CPF, rg, estadoCivil, telefone, dataNascimento, email, logradouro, numero, complemento, cep, cidade, UF) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, id);
            preparador.setString(2, nome);
            preparador.setString(3, sexo);
            preparador.setString(4, cpf);
            preparador.setString(5, rg);
            preparador.setString(6, estadoCivil);
            preparador.setString(7, telefone);
            preparador.setString(8, nascimento);
            preparador.setString(9, email);
            preparador.setString(10, logradouro);
            preparador.setString(11, numero);
            preparador.setString(12, complemento);
            preparador.setString(13, cep);
            preparador.setString(14, cidade);
            preparador.setString(15, uf);
            preparador.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO AO CADASTRAR: " + e.getMessage());
        }
        return false;
    }

    /**
     * Altera os dados do cliente no banco de dados
     *
     * @param nome
     * @param sexo
     * @param cpf
     * @param rg
     * @param estadoCivil
     * @param telefone
     * @param nascimento
     * @param email
     * @param logradouro
     * @param numero
     * @param complemento
     * @param cep
     * @param cidade
     * @param uf
     * @param id
     * @return verdadeiro se os dados foram alterados com sucesso
     */
    public boolean alterar(String nome, String sexo, String cpf, String rg, String estadoCivil, String telefone, String nascimento, String email, String logradouro, String numero, String complemento, String cep, String cidade, String uf, int id) {
        String sql = "update cliente set nomecli = ?,sexo = ?,CPF = ?,rg = ?,estadoCivil = ?,telefone = ?,dataNascimento = ?,email = ?,logradouro = ?,numero = ?,complemento = ?,cep = ?,cidade=?, UF=? where idCliente=?";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setString(1, nome);
            preparador.setString(2, sexo);
            preparador.setString(3, cpf);
            preparador.setString(4, rg);
            preparador.setString(5, estadoCivil);
            preparador.setString(6, telefone);
            preparador.setString(7, nascimento);
            preparador.setString(8, email);
            preparador.setString(9, logradouro);
            preparador.setString(10, numero);
            preparador.setString(11, complemento);
            preparador.setString(12, cep);
            preparador.setString(13, cidade);
            preparador.setString(14, uf);
            preparador.setInt(15, id);
            preparador.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro de sql: " + e.getMessage());
        }
        return false;
    }

    /**
     * Exclui um cliente do banco de dados
     *
     * @param idCliente
     * @return verdadeiro se os dados foram excluidos com sucesso
     */
    public boolean excluir(int idCliente) {
        try {
            String sql = "delete from cliente where idCliente=?";
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, idCliente);
            preparador.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro de sql" + e.getMessage());
            return false;
        }
    }

    /**
     * Busca um lista de cliente no banco de dados por um cpf
     *
     * @param cpf Recebe como parametro um CPF para buscar os dados da tabela
     * cliente
     * @return uma lista de dados do cliente
     */
    public List<Cliente> BuscaCliente(String cpf) {
        String sql = "select * from cliente where CPF like ?";
        List<Cliente> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, cpf + "%");
                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    Cliente c = new Cliente();
                    c.setIdCliente(resultado.getInt("idCliente"));
                    c.setNome(resultado.getString("nomecli"));
                    c.setCpf(resultado.getString("CPF"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR AS INFORMAÇÔES DO BANCO DE DADOS" + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca uma lista de informações do banco de dados da tabela cliente
     *
     * @return retorna uma lista do dados do cliente
     */
    public List<Cliente> pegaTodos() {
        String sql = "SELECT * FROM cliente";
        List<Cliente> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    Cliente cli = new Cliente();
                    cli.setIdCliente(resultado.getInt("idCliente"));
                    cli.setNome(resultado.getString("nomecli"));
                    cli.setSexo(resultado.getString("sexo"));
                    cli.setCpf(resultado.getString("CPF"));
                    cli.setRg(resultado.getString("rg"));
                    cli.setEstadoCivil(resultado.getString("estadoCivil"));
                    cli.setTelefone(resultado.getString("telefone"));
                    cli.setDataNascimento(resultado.getString("dataNascimento"));
                    cli.setEmail(resultado.getString("email"));
                    cli.setLogradouro(resultado.getString("logradouro"));
                    cli.setNumeroCasa(resultado.getString("numero"));
                    cli.setComplemento(resultado.getString("complemento"));
                    cli.setCEP(resultado.getString("cep"));
                    cli.setCidade(resultado.getString("cidade"));
                    cli.setUf(resultado.getString("UF"));
                    lista.add(cli);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO PEGAR TODOS: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca os dados do cliente pelo id
     *
     * @param idCliente
     * @return retorna os dados do cliente
     */
    public Cliente buscaPorId(int idCliente) {

        Cliente cliente = new Cliente();
        try {
            String sql = "Select * from cliente where idCliente=?";
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, idCliente);
            ResultSet resultado = preparador.executeQuery();

            if (resultado.next()) {
                cliente.setIdCliente(idCliente);
                cliente.setNome(resultado.getString("nomecli"));
                cliente.setSexo(resultado.getString("sexo"));
                cliente.setCpf(resultado.getString("CPF"));
                cliente.setRg(resultado.getString("rg"));
                cliente.setEstadoCivil(resultado.getString("estadoCivil"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setDataNascimento(resultado.getString("dataNascimento"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setLogradouro(resultado.getString("logradouro"));
                cliente.setNumeroCasa(resultado.getString("numero"));
                cliente.setComplemento(resultado.getString("complemento"));
                cliente.setCEP(resultado.getString("cep"));
                cliente.setCidade(resultado.getString("cidade"));
                cliente.setUf(resultado.getString("UF"));
            }
        } catch (SQLException e) {
            System.out.println("Erro de sql" + e.getMessage());
        }
        return cliente;
    }

}
