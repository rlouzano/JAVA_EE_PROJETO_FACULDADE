package br.senac.sp.dao;

import br.senac.sp.jdbc.ConexaoDB;
import br.senac.sp.model.Relatorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO extends Relatorio{

    /**
     * Insere um venda concluida no banco de dados relatório
     *
     * @param nomeproduto
     * @param codigoproduto
     * @param categoria
     * @param qtd_vendido
     * @param preco_unitario
     * @param preco_total
     * @param usuario
     * @param nomeCliente
     * @param cpfCliente
     * @param nomeFilial
     * @param formapagamento
     * @return retorna verdadeiro se a operação foi realizada com sucesso
     */
    public boolean cadastrarVendas(String nomeproduto, String codigoproduto, String categoria, int qtd_vendido, double preco_unitario, double preco_total, String usuario, String nomeCliente, String cpfCliente, String nomeFilial, String formapagamento) {
        Relatorio re = new Relatorio();
        String sql = "insert into relatorio(nomeproduto, codigoproduto, categoria, qtd_vendido, preco_unitario, preco_total, data_venda, usuario, nomeCLiente, cpfCliente, nomeFilial, forma_pagamento, idfilial) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setString(1, nomeproduto);
            preparador.setString(2, codigoproduto);
            preparador.setString(3, categoria);
            preparador.setInt(4, qtd_vendido);
            preparador.setDouble(5, preco_unitario);
            preparador.setDouble(6, preco_total);
            preparador.setObject(7, re.getLocalDateTime());
            preparador.setString(8, usuario);
            preparador.setString(9, nomeCliente);
            preparador.setString(10, cpfCliente);
            preparador.setString(11, nomeFilial);
            preparador.setString(12, formapagamento);
            if (nomeFilial.equals("CDSP")) {
                preparador.setInt(13, 1);
            } else if (nomeFilial.equals("CDRJ")) {
                preparador.setInt(13, 2);
            }
            preparador.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO AO FINALIZAR A VENDA: " + e.getMessage());
        }
        return false;
    }

    /**
     * Devolve uma lista com todos os dados da tabela relatório apartir de duas
     * datas selecionadas
     *
     * @param datainicio
     * @param datafim
     * @return retorna os dados da tabela relatório
     */
    public List<Relatorio> pegaTodos(String datainicio, String datafim) {
        String sql = "select * from relatorio where data_venda >= ? and data_venda <= ?";
        List<Relatorio> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, datainicio);
                preparador.setString(2, datafim);
                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    Relatorio re = new Relatorio();
                    re.setIdrelatorio(resultado.getInt("idrelatorio"));
                    re.setNomeproduto(resultado.getString("nomeproduto"));
                    re.setCodigoproduto(resultado.getString("codigoproduto"));
                    re.setCategoria(resultado.getString("categoria"));
                    re.setQtd_vendido(resultado.getInt("qtd_vendido"));
                    re.setPreco_unitario(resultado.getDouble("preco_unitario"));
                    re.setPreco_total(resultado.getDouble("preco_total"));
                    re.setData(resultado.getString("data_venda"));
                    re.setUsuario(resultado.getString("usuario"));
                    re.setNomeCliente(resultado.getString("nomeCLiente"));
                    re.setCpfCliente(resultado.getString("cpfCliente"));
                    re.setNomefilial(resultado.getString("nomeFilial"));
                    re.setFormPagto(resultado.getString("forma_pagamento"));
                    lista.add(re);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR AS INFORMAÇÔES DO BANCO DE DADOS" + e.getMessage());
        }
        return lista;
    }

    /**
     * Devolve uma lista analitica da tabela relatório apartir de duas datas
     * determinadas
     *
     * @param datainicio
     * @param datafim
     * @return retorna uma lista de dados analitico da tabela relatório
     */
    public List<Relatorio> pegaTodosAnalitico(String datainicio, String datafim) {
        String sql = "select idrelatorio, nomeproduto, codigoproduto, categoria, qtd_vendido, sum(qtd_vendido) as Soma_Qtd, avg(qtd_vendido) as Media_Qtd, preco_unitario,  preco_total, sum(preco_total) as Soma_Total, avg(preco_total) as Media_Preco, data_venda, usuario, nomeCLiente, cpfCliente, nomeFilial, forma_pagamento from relatorio where data_venda >= ? and data_venda <= ? group by nomeproduto, forma_pagamento";
        List<Relatorio> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, datainicio);
                preparador.setString(2, datafim);
                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    Relatorio re = new Relatorio();
                    re.setIdrelatorio(resultado.getInt("idrelatorio"));
                    re.setNomeproduto(resultado.getString("nomeproduto"));
                    re.setCodigoproduto(resultado.getString("codigoproduto"));
                    re.setCategoria(resultado.getString("categoria"));
                    re.setQtd_vendido(resultado.getInt("qtd_vendido"));
                    re.setMedia_Qtd(resultado.getInt("Media_Qtd"));
                    re.setSoma_Qtd(resultado.getInt("Soma_Qtd"));
                    re.setPreco_unitario(resultado.getDouble("preco_unitario"));
                    re.setPreco_total(resultado.getDouble("preco_total"));
                    re.setSoma_Total(resultado.getDouble("Soma_Total"));
                    re.setMedia_preco(resultado.getDouble("Media_Preco"));
                    re.setData(resultado.getString("data_venda"));
                    re.setUsuario(resultado.getString("usuario"));
                    re.setNomeCliente(resultado.getString("nomeCLiente"));
                    re.setCpfCliente(resultado.getString("cpfCliente"));
                    re.setNomefilial(resultado.getString("nomeFilial"));
                    re.setFormPagto(resultado.getString("forma_pagamento"));
                    lista.add(re);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR AS INFORMAÇÔES DO BANCO DE DADOS -- errou" + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca todos por data e filial
     *
     * @param datainicio
     * @param datafim
     * @param filial
     * @return retorna uma lista do produtos vendidos
     */
    public List<Relatorio> pegaTodosAnaliticoFilial(String datainicio, String datafim, String filial) {
        String sql = "select idrelatorio, nomeproduto, codigoproduto, categoria, qtd_vendido, sum(qtd_vendido) as Soma_Qtd, avg(qtd_vendido) as Media_Qtd, preco_unitario,  preco_total, sum(preco_total) as Soma_Total, avg(preco_total) as Media_Preco, data_venda, usuario, nomeCLiente, cpfCliente, nomeFilial, forma_pagamento from relatorio where data_venda >= ? and data_venda <= ? and nomeFilial = ? group by nomeproduto, forma_pagamento";
        List<Relatorio> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, datainicio);
                preparador.setString(2, datafim);
                preparador.setString(3, filial);
                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    Relatorio re = new Relatorio();
                    re.setIdrelatorio(resultado.getInt("idrelatorio"));
                    re.setNomeproduto(resultado.getString("nomeproduto"));
                    re.setCodigoproduto(resultado.getString("codigoproduto"));
                    re.setCategoria(resultado.getString("categoria"));
                    re.setQtd_vendido(resultado.getInt("qtd_vendido"));
                    re.setMedia_Qtd(resultado.getInt("Media_Qtd"));
                    re.setSoma_Qtd(resultado.getInt("Soma_Qtd"));
                    re.setPreco_unitario(resultado.getDouble("preco_unitario"));
                    re.setPreco_total(resultado.getDouble("preco_total"));
                    re.setSoma_Total(resultado.getDouble("Soma_Total"));
                    re.setMedia_preco(resultado.getDouble("Media_Preco"));
                    re.setData(resultado.getString("data_venda"));
                    re.setUsuario(resultado.getString("usuario"));
                    re.setNomeCliente(resultado.getString("nomeCLiente"));
                    re.setCpfCliente(resultado.getString("cpfCliente"));
                    re.setNomefilial(resultado.getString("nomeFilial"));
                    re.setFormPagto(resultado.getString("forma_pagamento"));
                    lista.add(re);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR AS INFORMAÇÔES DO BANCO DE DADOS" + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca todos por data e categoria
     *
     * @param datainicio
     * @param datafim
     * @param categoria
     * @return retorna uma lista do produtos vendidos
     */
    public List<Relatorio> pegaTodosAnaliticoCategoria(String datainicio, String datafim, String categoria) {
        String sql = "select idrelatorio, nomeproduto, codigoproduto, categoria, qtd_vendido, sum(qtd_vendido) as Soma_Qtd, avg(qtd_vendido) as Media_Qtd, preco_unitario,  preco_total, sum(preco_total) as Soma_Total, avg(preco_total) as Media_Preco, data_venda, usuario, nomeCLiente, cpfCliente, nomeFilial, forma_pagamento from relatorio where data_venda >= ? and data_venda <= ? and categoria like ? group by nomeproduto, forma_pagamento";
        List<Relatorio> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, datainicio);
                preparador.setString(2, datafim);
                preparador.setString(3, categoria + "%");
                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    Relatorio re = new Relatorio();
                    re.setIdrelatorio(resultado.getInt("idrelatorio"));
                    re.setNomeproduto(resultado.getString("nomeproduto"));
                    re.setCodigoproduto(resultado.getString("codigoproduto"));
                    re.setCategoria(resultado.getString("categoria"));
                    re.setQtd_vendido(resultado.getInt("qtd_vendido"));
                    re.setMedia_Qtd(resultado.getInt("Media_Qtd"));
                    re.setSoma_Qtd(resultado.getInt("Soma_Qtd"));
                    re.setPreco_unitario(resultado.getDouble("preco_unitario"));
                    re.setPreco_total(resultado.getDouble("preco_total"));
                    re.setSoma_Total(resultado.getDouble("Soma_Total"));
                    re.setMedia_preco(resultado.getDouble("Media_Preco"));
                    re.setData(resultado.getString("data_venda"));
                    re.setUsuario(resultado.getString("usuario"));
                    re.setNomeCliente(resultado.getString("nomeCLiente"));
                    re.setCpfCliente(resultado.getString("cpfCliente"));
                    re.setNomefilial(resultado.getString("nomeFilial"));
                    re.setFormPagto(resultado.getString("forma_pagamento"));
                    lista.add(re);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR AS INFORMAÇÔES DO BANCO DE DADOS" + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca todos por data e cliente
     *
     * @param datainicio
     * @param datafim
     * @param cliente
     * @return retorna uma lista do produtos vendidos
     */
    public List<Relatorio> pegaTodosAnaliticoCliente(String datainicio, String datafim, String cliente) {
        String sql = "select idrelatorio, nomeproduto, codigoproduto, categoria, qtd_vendido, sum(qtd_vendido) as Soma_Qtd, avg(qtd_vendido) as Media_Qtd, preco_unitario,  preco_total, sum(preco_total) as Soma_Total, avg(preco_total) as Media_Preco, data_venda, usuario, nomeCLiente, cpfCliente, nomeFilial, forma_pagamento from relatorio where data_venda >= ? and data_venda <= ? and nomeCLiente like ? group by nomeproduto, forma_pagamento";
        List<Relatorio> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, datainicio);
                preparador.setString(2, datafim);
                preparador.setString(3, cliente + "%");
                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    Relatorio re = new Relatorio();
                    re.setIdrelatorio(resultado.getInt("idrelatorio"));
                    re.setNomeproduto(resultado.getString("nomeproduto"));
                    re.setCodigoproduto(resultado.getString("codigoproduto"));
                    re.setCategoria(resultado.getString("categoria"));
                    re.setQtd_vendido(resultado.getInt("qtd_vendido"));
                    re.setMedia_Qtd(resultado.getInt("Media_Qtd"));
                    re.setSoma_Qtd(resultado.getInt("Soma_Qtd"));
                    re.setPreco_unitario(resultado.getDouble("preco_unitario"));
                    re.setPreco_total(resultado.getDouble("preco_total"));
                    re.setSoma_Total(resultado.getDouble("Soma_Total"));
                    re.setMedia_preco(resultado.getDouble("Media_Preco"));
                    re.setData(resultado.getString("data_venda"));
                    re.setUsuario(resultado.getString("usuario"));
                    re.setNomeCliente(resultado.getString("nomeCLiente"));
                    re.setCpfCliente(resultado.getString("cpfCliente"));
                    re.setNomefilial(resultado.getString("nomeFilial"));
                    re.setFormPagto(resultado.getString("forma_pagamento"));
                    lista.add(re);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR AS INFORMAÇÔES DO BANCO DE DADOS" + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca todos por data e filial
     *
     * @param datainicio
     * @param datafim
     * @param filial
     * @return retorna uma lista do produtos vendidos
     */
    public List<Relatorio> pegaTodosSinteticoFilial(String datainicio, String datafim, String filial) {
        String sql = "select * from relatorio where data_venda >= ? and data_venda <= ? and nomeFilial like = ?";
        List<Relatorio> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, datainicio);
                preparador.setString(2, datafim);
                preparador.setString(3, filial);

                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    Relatorio re = new Relatorio();
                    re.setIdrelatorio(resultado.getInt("idrelatorio"));
                    re.setNomeproduto(resultado.getString("nomeproduto"));
                    re.setCodigoproduto(resultado.getString("codigoproduto"));
                    re.setCategoria(resultado.getString("categoria"));
                    re.setQtd_vendido(resultado.getInt("qtd_vendido"));
                    re.setPreco_unitario(resultado.getDouble("preco_unitario"));
                    re.setPreco_total(resultado.getDouble("preco_total"));
                    re.setData(resultado.getString("data_venda"));
                    re.setUsuario(resultado.getString("usuario"));
                    re.setNomeCliente(resultado.getString("nomeCLiente"));
                    re.setCpfCliente(resultado.getString("cpfCliente"));
                    re.setNomefilial(resultado.getString("nomeFilial"));
                    re.setFormPagto(resultado.getString("forma_pagamento"));
                    lista.add(re);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR AS INFORMAÇÔES DO BANCO DE DADOS" + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca todos por data e categoria
     *
     * @param datainicio
     * @param datafim
     * @param categoria
     * @return retorna uma lista do produtos vendidos
     */
    public List<Relatorio> pegaTodosSinteticoCategoria(String datainicio, String datafim, String categoria) {
        String sql = "select * from relatorio where data_venda >= ? and data_venda <= ? and nomeFilial like = ? and categoria like ?";
        List<Relatorio> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, datainicio);
                preparador.setString(2, datafim);
                preparador.setString(3, categoria + "%");

                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    Relatorio re = new Relatorio();
                    re.setIdrelatorio(resultado.getInt("idrelatorio"));
                    re.setNomeproduto(resultado.getString("nomeproduto"));
                    re.setCodigoproduto(resultado.getString("codigoproduto"));
                    re.setCategoria(resultado.getString("categoria"));
                    re.setQtd_vendido(resultado.getInt("qtd_vendido"));
                    re.setPreco_unitario(resultado.getDouble("preco_unitario"));
                    re.setPreco_total(resultado.getDouble("preco_total"));
                    re.setData(resultado.getString("data_venda"));
                    re.setUsuario(resultado.getString("usuario"));
                    re.setNomeCliente(resultado.getString("nomeCLiente"));
                    re.setCpfCliente(resultado.getString("cpfCliente"));
                    re.setNomefilial(resultado.getString("nomeFilial"));
                    re.setFormPagto(resultado.getString("forma_pagamento"));
                    lista.add(re);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR AS INFORMAÇÔES DO BANCO DE DADOS" + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca todos por data e cliente
     *
     * @param datainicio
     * @param datafim
     * @param cliente
     * @return retorna uma lista do produtos vendidos
     */
    public List<Relatorio> pegaTodosSinteticoCliente(String datainicio, String datafim, String cliente) {
        String sql = "select * from relatorio where data_venda >= ? and data_venda <= ? and nomeFilial like = ? and nomeCLiente like ?";
        List<Relatorio> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, datainicio);
                preparador.setString(2, datafim);
                preparador.setString(3, cliente + "%");

                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    Relatorio re = new Relatorio();
                    re.setIdrelatorio(resultado.getInt("idrelatorio"));
                    re.setNomeproduto(resultado.getString("nomeproduto"));
                    re.setCodigoproduto(resultado.getString("codigoproduto"));
                    re.setCategoria(resultado.getString("categoria"));
                    re.setQtd_vendido(resultado.getInt("qtd_vendido"));
                    re.setPreco_unitario(resultado.getDouble("preco_unitario"));
                    re.setPreco_total(resultado.getDouble("preco_total"));
                    re.setData(resultado.getString("data_venda"));
                    re.setUsuario(resultado.getString("usuario"));
                    re.setNomeCliente(resultado.getString("nomeCLiente"));
                    re.setCpfCliente(resultado.getString("cpfCliente"));
                    re.setNomefilial(resultado.getString("nomeFilial"));
                    re.setFormPagto(resultado.getString("forma_pagamento"));
                    lista.add(re);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR AS INFORMAÇÔES DO BANCO DE DADOS" + e.getMessage());
        }
        return lista;
    }

}
