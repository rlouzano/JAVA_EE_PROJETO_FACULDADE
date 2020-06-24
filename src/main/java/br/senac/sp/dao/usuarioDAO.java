package br.senac.sp.dao;

import br.senac.sp.jdbc.ConexaoDB;
import br.senac.sp.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class usuarioDAO extends Usuario {

    /**
     * Cadastra os dados na tabela usuario
     *
     * @param u
     * @return retorna se verdadeiro se os dados foram inseridos com sucesso.
     */
    public boolean cadastrar(Usuario u) {
        String sql = "insert into usuario(nome, cpf, perfil, login, senha, senha_adm, filial, idfilial) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setString(1, u.getNome());
            preparador.setString(2, u.getCpf());
            preparador.setString(3, u.getPerfil());
            preparador.setString(4, u.getLogin());
            preparador.setString(5, u.getSenha());
            preparador.setString(6, u.getSenhaAdmin());
            preparador.setString(7, u.getFilial());
            if (u.getFilial().equals("CDSP")) {
                preparador.setInt(8, 1);
            } else if (u.getFilial().equals("CDRJ")) {
                preparador.setInt(8, 2);
            }
            preparador.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO AO CADASTRAR USUARIO: " + e.getMessage());
        }
        return false;
    }

    /**
     * Altera os dados na tabela usuario
     * @param u
     * @return retorna se verdadeiro se os dados foram alterados com sucesso.
     */
    public boolean alterar(Usuario u) {
        String sql = "update usuario set nome=?, cpf=?, perfil=?, login=?, senha=? ,senha_adm=? ,filial=? ,idfilial=? where id=?";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, u.getNome());
                preparador.setString(2, u.getCpf());
                preparador.setString(3, u.getPerfil());
                preparador.setString(4, u.getLogin());
                preparador.setString(5, u.getSenha());
                preparador.setString(6, u.getSenhaAdmin());
                preparador.setString(7, u.getFilial());
                if (u.getFilial().equals("CDSP")) {
                    preparador.setInt(8, 1);
                } else if (u.getFilial().equals("CDRJ")) {
                    preparador.setInt(8, 2);
                }
                preparador.setInt(9, u.getId());
                preparador.execute();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO EDITAR: " + e.getMessage());
        }
        return false;
    }

    /**
     * Exclui os dados na tabela usuario
     *
     * @param id
     * @return retorna verdadeiro se os dados foram excluidos com sucesso.
     */
    public boolean excluir(int id) {
        try {
            String sql = "delete from usuario where id=?";
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, id);
            preparador.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro de sql" + e.getMessage());
            return false;
        }
    }

    /**
     * Busca uma lista de usuário por um id especifico
     *
     * @param id
     * @return retorna a lista de usuário por id especifico
     */
    public Usuario buscaPoId(int id) {
        Usuario user = new Usuario();
        String sql = "SELECT * FROM usuario WHERE id=?";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            PreparedStatement preparador = con.prepareStatement(sql);
            preparador.setInt(1, id);
            ResultSet resultado = preparador.executeQuery();
            if (resultado.next()) {
                user.setId(resultado.getInt("id"));
                user.setNome(resultado.getString("nome"));
                user.setCpf(resultado.getString("cpf"));
                user.setPerfil(resultado.getString("perfil"));
                user.setLogin(resultado.getString("login"));
                user.setSenha(resultado.getString("senha"));
                user.setSenhaAdmin(resultado.getString("senha_adm"));
                user.setFilial(resultado.getString("filial"));
                user.setIdfilial(resultado.getInt("idfilial"));

            }
            preparador.execute();
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR USUARIOS POR ID " + e.getMessage());
        }
        return user;
    }

    /**
     * Realiza a busca de todos egistros da tabela de usuarios
     *
     * @return Uma lista de objetos usuario contendo 0 elementos quando tiver
     * registro ou n elementos quando tiver resultado
     */
    public List<Usuario> buscaTodos() {
        String sql = "Select * from usuario";
        List<Usuario> lista = new ArrayList<>();
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                ResultSet resultado = preparador.executeQuery();
                while (resultado.next()) {
                    Usuario user = new Usuario();
                    user.setId(resultado.getInt("id"));
                    user.setNome(resultado.getString("nome"));
                    user.setCpf(resultado.getString("cpf"));
                    user.setPerfil(resultado.getString("perfil"));
                    user.setLogin(resultado.getString("login"));
                    user.setSenha(resultado.getString("senha"));
                    user.setSenhaAdmin(resultado.getString("senha_adm"));
                    user.setFilial(resultado.getString("filial"));
                    user.setIdfilial(resultado.getInt("idfilial"));
                    lista.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR TODOS USUARIOS " + e.getMessage());
        }
        return lista;
    }

    public Usuario PegaTodos(String login) {
        Usuario user = new Usuario();
        String sql = "Select * from usuario where login = ?";
        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, login);
                ResultSet resultado = preparador.executeQuery();
                if (resultado.next()) {
                    user.setId(resultado.getInt("id"));
                    user.setNome(resultado.getString("nome"));
                    user.setCpf(resultado.getString("cpf"));
                    user.setPerfil(resultado.getString("perfil"));
                    user.setLogin(resultado.getString("login"));
                    user.setSenha(resultado.getString("senha"));
                    user.setSenhaAdmin(resultado.getString("senha_adm"));
                    user.setFilial(resultado.getString("filial"));
                    user.setIdfilial(resultado.getInt("idfilial"));
                }
                resultado.close();
                preparador.close();
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO BUSCAR TODOS USUARIOS " + e.getMessage());
        }
        return user;
    }

    /**
     * Busca os dados de autenticação do usuário no banco de dados
     *
     * @param usuConsulta
     * @return retorna os dados do usuário autenticado
     */
    public Usuario autenticar(Usuario usuConsulta) {
        String sql = "Select * from usuario where login=? and senha=?";

        try {
            ConexaoDB connection = new ConexaoDB();
            Connection con = connection.recuperarConexao();
            try (PreparedStatement preparador = con.prepareStatement(sql)) {
                preparador.setString(1, usuConsulta.getLogin());
                preparador.setString(2, usuConsulta.getSenha());
                ResultSet resultado = preparador.executeQuery();
                if (resultado.next()) {
                    Usuario user = new Usuario();
                    user.setId(resultado.getInt("id"));
                    user.setNome(resultado.getString("nome"));
                    user.setFilial(resultado.getString("filial"));
                    user.setPerfil(resultado.getString("perfil"));
                    user.setLogin(resultado.getString("login"));
                    user.setSenha(resultado.getString("senha"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO AUTENTICAR USUARIO " + e.getMessage());
        }
        return null;

    }
}
