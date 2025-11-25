package dao;

import dto.LivroDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class LivroDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<LivroDTO> lista = new ArrayList<>();

    public void cadastrarLivro(LivroDTO objLivroDTO) {
        String sql = "INSERT INTO livros (titulo, autor, disponivel) VALUES (?, ?, ?)";

        conn = new ConexaoDAO().conectaBD();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, objLivroDTO.getTitulo());
            pstm.setString(2, objLivroDTO.getAutor());
            pstm.setBoolean(3, objLivroDTO.isDisponivel());

            pstm.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "LivroDAO (cadastrar): " + e.getMessage());
        }
    }

    public ArrayList<LivroDTO> listarLivros() {
        String sql = "SELECT * FROM livros";

        conn = new ConexaoDAO().conectaBD();

        try (PreparedStatement pstm = conn.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                LivroDTO objLivroDTO = new LivroDTO();
                objLivroDTO.setId(rs.getInt("id"));
                objLivroDTO.setTitulo(rs.getString("titulo"));
                objLivroDTO.setAutor(rs.getString("autor"));
                objLivroDTO.setDisponivel(rs.getBoolean("disponivel"));

                lista.add(objLivroDTO);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "LivroDAO (listar): " + e.getMessage());
        }

        return lista;
    }

    public void alterarLivro(LivroDTO objLivroDTO) {
        String sql = "UPDATE livros SET titulo = ?, autor = ?, disponivel = ? WHERE id = ?";

        conn = new ConexaoDAO().conectaBD();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, objLivroDTO.getTitulo());
            pstm.setString(2, objLivroDTO.getAutor());
            pstm.setBoolean(3, objLivroDTO.isDisponivel());
            pstm.setInt(4, objLivroDTO.getId());

            pstm.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "LivroDAO (alterar): " + e.getMessage());
        }
    }

    public void excluirLivro(int idLivro) {
        String sql = "DELETE FROM livros WHERE id = ?";

        conn = new ConexaoDAO().conectaBD();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, idLivro);
            pstm.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "LivroDAO (excluir): " + e.getMessage());
        }
    }

    public void criarTabelaSeNaoExistir() {
        String sql = "CREATE TABLE IF NOT EXISTS livros ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "titulo VARCHAR(200) NOT NULL, "
                + "autor VARCHAR(100), "
                + "disponivel BOOLEAN DEFAULT TRUE"
                + ")";

        conn = new ConexaoDAO().conectaBD();

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            if (!e.getMessage().contains("Table 'livros' already exists")) {
                JOptionPane.showMessageDialog(null, "LivroDAO (criarTabela): " + e.getMessage());
            }
        }
    }
// Método para buscar um livro específico pelo ID
    public LivroDTO buscarPorId(int id) {
        String sql = "SELECT * FROM livros WHERE id = ?";
        conn = new ConexaoDAO().conectaBD();
        LivroDTO livro = null;
        
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, id);
            // Executa a consulta
            ResultSet rs = pstm.executeQuery();
            
            // Se encontrou algum registro
            if (rs.next()) {
                livro = new LivroDTO();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setDisponivel(rs.getBoolean("disponivel"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "LivroDAO (buscarPorId): " + e.getMessage());
        }
        
        // Retorna o objeto LivroDTO (ou null se não achou)
        return livro;
    }
    // Método ATUALIZADO para limpar a tabela (usado apenas em testes)
    public void limparTabela() {
        conn = new ConexaoDAO().conectaBD();
        try (java.sql.Statement stmt = conn.createStatement()) {
            // 1. Desativa a verificação de chave estrangeira
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            
            // 2. Agora sim, apaga tudo e reseta o ID para 1
            stmt.execute("TRUNCATE TABLE livros");
            
            // 3. Reativa a segurança
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
