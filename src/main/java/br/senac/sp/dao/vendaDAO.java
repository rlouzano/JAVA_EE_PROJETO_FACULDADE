package br.senac.sp.dao;

import br.senac.sp.jdbc.ConexaoDB;
import br.senac.sp.model.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class vendaDAO extends Venda {

    /**
     * Cadastra os produtos que o cliente deseja comprar na tabela venda
     *
     * @param v
     * @param usuario
     * @return valida se os produtos foram cadastrados
     */
    public boolean cadastrar(Venda v, String usuario) {
        String sql = "insert into venda(nomeproduto, codigoproduto, categoria, qtd, preco_unitario, usuario, nomeFilial)values (?, ?, ?, ?, ?,?,?)";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, v.getNomeproduto());
                preparador.setString(2, v.getCodigoproduto());
                preparador.setString(3, v.getCategoria());
                preparador.setInt(4, v.getQtd());
                preparador.setDouble(5, v.getPreco_unitario());
                preparador.setString(6, usuario);
                preparador.setString(7, v.getNomeFilial());
                preparador.execute();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO AO CADASTRAR VENDA " + e.getMessage());
        }
        return false;
    }

    /**
     * Exclui o dado especificado pelo usuario na tabela venda
     *
     * @param id
     * @return
     */
    public boolean Excluir(int id) {
        String sql = "delete from venda where idvenda=?";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setInt(1, id);
                preparador.execute();
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO EXCLUIR VENDA " + e.getMessage());
        }
        return false;
    }

    /**
     * Altera a quantidade de produto da tabela venda
     *
     * @param qtd
     * @param id
     * @return valida se os dados foram alterados
     */
    public boolean Alterar(int qtd, int id) {
        String sql = "update venda set qtd=? where idvenda=?";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, qtd);
            preparador.setInt(2, id);
            preparador.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO AO ALTERAR " + e.getMessage());
        }
        return false;
    }

    /**
     * Devolve uma lista de dados da tabela venda
     *
     * @return retorna uma lista com os dados
     */
    public List<Venda> pegaTodos() {
        String sql = "select * from venda";
        List<Venda> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                ResultSet resultado = preparador.executeQuery();

                while (resultado.next()) {
                    Venda vd = new Venda();
                    vd.setId(resultado.getInt("idvenda"));
                    vd.setNomeproduto(resultado.getString("nomeproduto"));
                    vd.setCodigoproduto(resultado.getString("codigoproduto"));
                    vd.setCategoria(resultado.getString("categoria"));
                    vd.setQtd(resultado.getInt("qtd"));
                    vd.setPreco_unitario(resultado.getDouble("preco_unitario"));
                    vd.setPreco_total(resultado.getDouble("preco_total"));
                    vd.setUsuario(resultado.getString("usuario"));
                    vd.setNomecliente(resultado.getString("nomeCLiente"));
                    vd.setCpfcliente(resultado.getString("cpfCliente"));
                    vd.setNome_filial(resultado.getString("nomeFilial"));
                    vd.setFormpagto(resultado.getString("tipoPagto"));
                    lista.add(vd);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR AS INFORMAÇÔES DO BANCO DE DADOS" + e.getMessage());
        }
        return lista;
    }

    /**
     * Devolve um lista de dados da tabela venda apartir de um id selecionado
     *
     * @param id
     * @return retorna uma lista de dados
     */
    public List<Venda> pegaTudo(int id) {
        String sql = "select * from venda where idvenda = ?";
        List<Venda> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setInt(1, id);
                ResultSet resultado = preparador.executeQuery();

                while (resultado.next()) {
                    Venda vd = new Venda();
                    vd.setId(resultado.getInt("idvenda"));
                    vd.setNomeproduto(resultado.getString("nomeproduto"));
                    vd.setCodigoproduto(resultado.getString("codigoproduto"));
                    vd.setCategoria(resultado.getString("categoria"));
                    vd.setQtd(resultado.getInt("qtd"));
                    vd.setPreco_unitario(resultado.getDouble("preco_unitario"));
                    vd.setPreco_total(resultado.getDouble("preco_total"));
                    vd.setUsuario(resultado.getString("usuario"));
                    vd.setNomecliente(resultado.getString("nomeCliente"));
                    vd.setNome_filial(resultado.getString("nomeFilial"));
                    lista.add(vd);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR AS INFORMAÇÔES DO BANCO DE DADOS" + e.getMessage());
        }
        return lista;
    }

    /**
     * Exclui todos os dados da tabela venda, quando uma venda é finalizada
     * @return 
     */
    public boolean ExcluirTodos() {
        String sql = "delete from venda where idvenda >=1";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.execute();
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO EXCLUIR VENDA " + e.getMessage());
        }
        return false;
    }

    /**
     * Cadastra os dados da forma de pagamento na tabela formapagamento
     *
     * @param pagto
     * @return valida se foi cadastrado
     */
    public boolean inserirPagto(String pagto) {
        Venda v = new Venda();
        String sql = "update venda set tipoPagto=? where idvenda >0";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, pagto);
                preparador.execute();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO AO CADASTRAR VENDA " + e.getMessage());
        }
        return false;
    }

    /**
     * Cadastra os dados do cliente na tabela venda
     *
     * @param nomeCliente
     * @param cpfcli
     * @param filial
     * @return valida se os dados foram cadastrados
     */
    public boolean cadastrarCliente(String nomeCliente, String cpfcli, String filial) {
        String sql = "update venda set nomeCliente=?, cpfCliente=?, nomeFilial=? where idvenda > 0";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setString(1, nomeCliente);
            preparador.setString(2, cpfcli);
            preparador.setString(3, filial);
            preparador.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO AO CADASTRAR VENDA " + e.getMessage());
        }
        return false;
    }
}
