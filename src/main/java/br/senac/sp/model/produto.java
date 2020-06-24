package br.senac.sp.model;

import br.senac.sp.dao.produtoDAO;
import java.util.List;

public class produto extends Venda {

    private int idproduto;
    private String nome;
    private String codigo;
    private String categoria;
    private int qtd;
    private double preco;
    private String pesquisa;
    private int id;
    private Filial filial;

    public boolean getGravarProduto(produto p, String filial) {
        produtoDAO pdao = new produtoDAO();
        if (pdao.cadastrar(p, filial)) {
            return true;
        }
        return false;
    }

    public boolean getAlterarProduto(produto p) {
        produtoDAO pdao = new produtoDAO();
        if (pdao.alterar(p)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean getExcluir(int id) {
        produtoDAO pdao = new produtoDAO();
        if (pdao.excluir(id)) {
            return true;
        }
        return false;
    }

    public List<produto> getPegaProdutos() {
        produtoDAO pdao = new produtoDAO();
        return pdao.pegaTodos();
    }

    public List<produto> getBuscaProduto(String pesquisa) {
        produtoDAO pdao = new produtoDAO();
        return pdao.buscaPorPesquisa(pesquisa);
    }

    public produto getBuscaProdutoPorId(int id) {
        produtoDAO pdao = new produtoDAO();
        return pdao.buscaPorId(id);
    }

    public boolean getAlteraProdutoVenda(int qtd, String codigoproduto) {
        produtoDAO pdao = new produtoDAO();
        if (pdao.alteraVenda(qtd, codigoproduto)) {
            return true;
        }
        return false;
    }

    public int getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(int idproduto) {
        this.idproduto = idproduto;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getCategoria() {
        return categoria;
    }

    @Override
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public int getQtd() {
        return qtd;
    }

    @Override
    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    @Override
    public String toString() {
        return "[" + this.idproduto + " | " + this.getNome() + " | " + this.codigo + " | " + this.categoria + " | " + this.qtd + " | " + this.preco + " | " + this.id + " " + this.filial + "]";
    }

}
