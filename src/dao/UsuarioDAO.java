package dao;

import dto.UsuarioDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    ArrayList<UsuarioDTO> lista = new ArrayList<>();

    public void cadastrarUsuario(UsuarioDTO objUsuarioDTO) {
        String sql = "INSERT INTO usuarios (nome, cpf, email) VALUES (?, ?, ?)";

        conn = new ConexaoDAO().conectaBD();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, objUsuarioDTO.getNome());
            pstm.setString(2, objUsuarioDTO.getCpf());
            pstm.setString(3, objUsuarioDTO.getEmail());

            pstm.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO (cadastrar): " + e.getMessage());
        }
    }

    public ArrayList<UsuarioDTO> listarUsuarios() {
        String sql = "SELECT * FROM usuarios";

        conn = new ConexaoDAO().conectaBD();

        try (PreparedStatement pstm = conn.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                UsuarioDTO objUsuarioDTO = new UsuarioDTO();
                objUsuarioDTO.setId(rs.getInt("id"));
                objUsuarioDTO.setNome(rs.getString("nome"));
                objUsuarioDTO.setCpf(rs.getString("cpf"));
                objUsuarioDTO.setEmail(rs.getString("email"));

                lista.add(objUsuarioDTO);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO (listar): " + e.getMessage());
        }
        return lista;
    }

    public void alterarUsuario(UsuarioDTO objUsuarioDTO) {
        String sql = "UPDATE usuarios SET nome = ?, cpf = ?, email = ? WHERE id = ?";

        conn = new ConexaoDAO().conectaBD();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, objUsuarioDTO.getNome());
            pstm.setString(2, objUsuarioDTO.getCpf());
            pstm.setString(3, objUsuarioDTO.getEmail());
            pstm.setInt(4, objUsuarioDTO.getId());

            pstm.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO (alterar): " + e.getMessage());
        }
    }

    public void excluirUsuario(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        conn = new ConexaoDAO().conectaBD();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, idUsuario);
            pstm.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO (excluir): " + e.getMessage());
        }
    }

    public void criarTabelaSeNaoExistir() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "nome VARCHAR(100) NOT NULL, "
                + "cpf VARCHAR(20) NOT NULL UNIQUE, "
                + "email VARCHAR(100)"
                + ")";

        conn = new ConexaoDAO().conectaBD();

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            if (!e.getMessage().contains("Table 'usuarios' already exists")) {
                JOptionPane.showMessageDialog(null, "UsuarioDAO (criarTabela): " + e.getMessage());
            }
        }
    }
}
