package br.senac.sp.dao;

import br.senac.sp.jdbc.ConexaoDB;
import br.senac.sp.model.Filial;
import br.senac.sp.model.produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafae
 */
public class produtoDAO extends produto {

    /**
     * Cadastra dados na tabela produto
     * @param prod
     * @param filial
     * @return valida se o cadastro foi realizado ou não
     */
    public boolean cadastrar(produto prod, String filial) {
        Filial f = new Filial();
        String sql = "insert into produto(nome, codigoproduto, categoria, qtd, preco, filial, idfilial) value (?, ?, ?, ?, ?, ?, ?)";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, prod.getNome());
            p.setString(2, prod.getCodigo());
            p.setString(3, prod.getCategoria());
            p.setInt(4, prod.getQtd());
            p.setDouble(5, prod.getPreco());
            p.setString(6, filial);
            if (filial.equals("CDSP")) {
                p.setInt(7, 1);
            } else if (filial.equals("CDRJ")) {
                p.setInt(7, 2);
            }
            p.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO AO CADASTRAR O PRODUTO " + e.getMessage());
        }
        return false;
    }

    /**
     * Altera os dados na tabela produto
     * @param p
     * @return valida se os dados foram alterados
     */
    public boolean alterar(produto p) {
        String sql = "update produto set nome = ?,codigoproduto = ?,categoria = ?,qtd = ?,preco = ?, idfilial= ?  where idproduto=?";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setString(1, p.getNome());
            preparador.setString(2, p.getCodigoproduto());
            preparador.setString(3, p.getCategoria());
            preparador.setInt(4, p.getQtd());
            preparador.setDouble(5, p.getPreco());
            preparador.setInt(6, p.getIdfilial());
            preparador.setInt(7, p.getIdproduto());
            preparador.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO AO EDITAR PRODUTO: " + e.getMessage());
        }
        return false;
    }

    /**
     * Exclui um dado especificado pelo usuario na tabela produto
     *
     * @param idproduto
     * @return valida se os dados foram excluidos
     */
    public boolean excluir(int idproduto) {
        try {
            String sql = "delete from produto where idproduto=?";
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, idproduto);
            preparador.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro de sql" + e.getMessage());
            return false;
        }
    }

    /**
     * Devolve uma lista de dados da tabela produto
     *
     * @return retorna um lista de dados
     */
    public List<produto> pegaTodos() {
        String sql = "SELECT p.idproduto, p.nome, p.codigoproduto, p.categoria, p.qtd, p.preco, f.nome FROM produto AS p inner join filial AS f on p.idfilial = f.idfilial";
        List<produto> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    produto prod = new produto();
                    prod.setIdproduto(resultado.getInt("p.idproduto"));
                    prod.setNome(resultado.getString("p.nome"));
                    prod.setCodigo(resultado.getString("p.codigoproduto"));
                    prod.setCategoria(resultado.getString("p.categoria"));
                    prod.setQtd(resultado.getInt("p.qtd"));
                    prod.setPreco(resultado.getDouble("p.preco"));
                    Filial f = new Filial();
                    f.getNomeFilial(resultado.getString("f.nome"));
                    prod.setFilial(f);
                    lista.add(prod);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO PEGAR TODOS: " + e.getMessage());
        }
        return lista;
    }
    
        public List<produto> pegaTodosPorCod(String codigo) {
        String sql = "SELECT p.idproduto, p.nome, p.codigoproduto, p.categoria, p.qtd, p.preco, f.nome FROM produto AS p inner join filial AS f on p.idfilial = f.idfilial where p.codigoproduto like ?";
        List<produto> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, codigo);
                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    produto prod = new produto();
                    prod.setIdproduto(resultado.getInt("p.idproduto"));
                    prod.setNome(resultado.getString("p.nome"));
                    prod.setCodigo(resultado.getString("p.codigoproduto"));
                    prod.setCategoria(resultado.getString("p.categoria"));
                    prod.setQtd(resultado.getInt("p.qtd"));
                    prod.setPreco(resultado.getDouble("p.preco"));
                    Filial f = new Filial();
                    f.getNomeFilial(resultado.getString("f.nome"));
                    prod.setFilial(f);
                    lista.add(prod);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO PEGAR TODOS: " + e.getMessage());
        }
        return lista;
    }
    
    
    


    /**
     * Devolve uma lista com de dados pesquisa apartir de um cpf ou codigo
     * produto
     *
     * @param pesquisa
     * @return retorna uma lista de informações da tabela
     */
    public List<produto> buscaPorPesquisa(String pesquisa) {
        String sql = "select p.idproduto, p.nome, p.codigoproduto, p.categoria, p.qtd, p.preco, f.nome, f.idfilial from produto as p inner join filial as f on p.idfilial = f.idfilial where p.nome like ? or p.codigoproduto like ?";
        List<produto> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, "%" + pesquisa + "%");
                preparador.setString(2, "%" + pesquisa + "%");
                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    produto prod = new produto();
                    prod.setId(resultado.getInt("p.idproduto"));
                    prod.setNome(resultado.getString("p.nome"));
                    prod.setCodigo(resultado.getString("p.codigoproduto"));
                    prod.setCategoria(resultado.getString("p.categoria"));
                    prod.setQtd(resultado.getInt("p.qtd"));
                    prod.setPreco(resultado.getDouble("p.preco"));
                    Filial f = new Filial();
                    f.setIdfilial(resultado.getInt("f.idfilial"));
                    f.getNomeFilial(resultado.getString("f.nome"));
                    prod.setFilial(f);
                    lista.add(prod);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO EXECUTAR A PESQUISA NO BANCO" + e.getMessage());
        }
        return lista;
    }

    /**
     * Devolve uma lista buscada apartir do idproduto da tabela produto
     *
     * @param idproduto
     * @return Retorna os dados da tabela produto
     */
    public produto buscaPorId(int idproduto) {
        produto prod = new produto();
        try {
            String sql = "SELECT * FROM produto WHERE idproduto=?";
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, idproduto);
            ResultSet resultado = preparador.executeQuery();
            while (resultado.next()) {
                prod.setIdproduto(resultado.getInt("idproduto"));
                prod.setNome(resultado.getString("nome"));
                prod.setCodigo(resultado.getString("codigoproduto"));
                prod.setCategoria(resultado.getString("categoria"));
                prod.setQtd(resultado.getInt("qtd"));
                prod.setPreco(resultado.getDouble("preco"));
                prod.setId(resultado.getInt("idfilial"));
                preparador.execute();
            }
        } catch (SQLException e) {
            System.out.println("Erro de sql" + e.getMessage());
        }
        return prod;
    }

    /**
     * Altera a quantidade de produto quando uma venda é realizada
     *
     * @param qtd
     * @param codigoproduto
     * @return valida se os dados foram alterados
     */
    public boolean alteraVenda(int qtd, String codigoproduto) {
        String sql = "update produto set qtd=? where codigoproduto = ?";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, qtd);
            preparador.setString(2, codigoproduto);
            preparador.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO AO CADASTRAR VENDA " + e.getMessage());
        }
        return false;
    }
}
