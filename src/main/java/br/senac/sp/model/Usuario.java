package br.senac.sp.model;

import br.senac.sp.dao.usuarioDAO;
import java.util.List;

public class Usuario extends Filial {

    private int id;
    private String nome;
    private String cpf;
    private String perfil;
    private String login;
    private String senha;
    private String senhaAdmin;
    private String filial;

    public boolean autentica(String senha) {
        if (this.senha != null && this.senha.equals(senha)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getGravar(Usuario u) {
        usuarioDAO udao = new usuarioDAO();
        if (udao.cadastrar(u)) {
            return true;
        }
        return false;
    }

    public boolean getEditar(Usuario u) {
        usuarioDAO udao = new usuarioDAO();
        if (udao.alterar(u)) {
            return true;
        }
        return false;
    }

    public boolean getExcluir(int id) {
        usuarioDAO udao = new usuarioDAO();
        if (udao.excluir(id)) {
            return true;
        }
        return false;
    }

    public List<Usuario> getPegaTodos() {
        usuarioDAO udao = new usuarioDAO();
        return udao.buscaTodos();
    }

    public Usuario getBuscaUsuario(String login) {
        usuarioDAO udao = new usuarioDAO();
        return udao.PegaTodos(login);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaAdmin() {
        return senhaAdmin;
    }

    public void setSenhaAdmin(String senhaAdmin) {
        this.senhaAdmin = senhaAdmin;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    @Override
    public String toString() {
        return "[" + this.id + " " + this.nome + " " + this.cpf + " " + this.perfil + " " + this.login + " " + this.senha + " " + this.senhaAdmin + " " + this.filial + "]";
    }

}
