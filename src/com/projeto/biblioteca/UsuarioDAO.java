package com.projeto.biblioteca;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

//conectar no Banco
public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO() {

        this.connection = new ConnectionFactory().conectaBD();
    }

    //Metodo para Criar usuario
    public void criarUsuario(Usuario usuario) throws SQLException {

        String sql = "INSERT INTO usuario(nome, email, telefone, tipo_usuario) VALUES(?,?,?,?)";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getTipo_usuario());
            //Comando que cria o usuario
            stmt.executeUpdate();
            System.out.println("Usuario Criado com Sucesso");
        } catch (SQLException erro) {
            System.out.println("Erro ao criar o usuario: " + erro.getMessage());
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    //Metodo para Listar todos os usuarios
    public List<Usuario> listarUsuarios() throws SQLException {
        String sql = "SELECT * FROM usuario";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        try {
            stmt = connection.prepareStatement(sql);
            //Comando de busca e listagem dos usuarios
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setTipo_usuario(rs.getString("tipo_usuario"));
                usuarios.add(usuario);

            }
        } catch (SQLException erro) {
            System.out.println("Erro ao listar " + erro.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return usuarios;
    }

    //Metodo para buscar usuarios por id
    public Usuario buscarUsuarioPorId(int id) throws SQLException {
        String sql = "SELECT*FROM usuario WHERE id=?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setTipo_usuario(rs.getString("tipo_usuario"));
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao buscar usuário: " + erro.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return usuario;
    }

    //Metodo para atualizar um usuario
    public void atualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome=?, email=?, telefone=?, tipo_usuario=? WHERE id=?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getTipo_usuario());
            stmt.setInt(5, usuario.getId());

            //Comando que executa a atualização
            stmt.executeUpdate();
            System.out.println("Usuário atualizado com sucesso!");

        } catch (SQLException erro) {
            System.out.println("Erro ao atualizar usuário: " + erro.getMessage());
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    //Método para deletar um usuário
    public void deletarUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id=?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Usuário deletado com sucesso!");

        } catch (SQLException erro) {
            System.out.println("Erro ao deletar usuário: " + erro.getMessage());
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    //Metodo para fechar a conexão(caso precise ser fechado manualmente)
    public void fecharConexao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
