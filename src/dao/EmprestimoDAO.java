/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.EmprestimoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author bsale
 */
public class EmprestimoDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    public void registrarEmprestimo(EmprestimoDTO objDTO) {
        String sqlVerifica = "SELECT disponivel FROM livros WHERE id = ?";
        String sqlInserir = "INSERT INTO emprestimos (id_usuario, id_livro, data_emprestimo) VALUES (?, ?, CURRENT_DATE)";
        String sqlAtualizarLivro = "UPDATE livros SET disponivel = false WHERE id = ?";

        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sqlVerifica);
            pstm.setInt(1, objDTO.getIdLivro());
            rs = pstm.executeQuery();

            if (rs.next()) {
                boolean disponivel = rs.getBoolean("disponivel");
                if (!disponivel) {
                    JOptionPane.showMessageDialog(null, "Este livro já está emprestado!");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Livro não encontrado (ID inválido)!");
                return;
            }

            pstm = conn.prepareStatement(sqlInserir);
            pstm.setInt(1, objDTO.getIdUsuario());
            pstm.setInt(2, objDTO.getIdLivro());
            pstm.execute();

            pstm = conn.prepareStatement(sqlAtualizarLivro);
            pstm.setInt(1, objDTO.getIdLivro());
            pstm.execute();

            JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao emprestar: " + e.getMessage());
        }
    }

    public void registrarDevolucao(int idEmprestimo, int idLivro) {
        String sqlDevolucao = "UPDATE emprestimos SET data_devolucao = CURRENT_DATE WHERE id = ?";
        String sqlLiberarLivro = "UPDATE livros SET disponivel = true WHERE id = ?";

        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sqlDevolucao);
            pstm.setInt(1, idEmprestimo);
            pstm.execute();

            pstm = conn.prepareStatement(sqlLiberarLivro);
            pstm.setInt(1, idLivro);
            pstm.execute();

            JOptionPane.showMessageDialog(null, "Devolução registrada com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro na devolução: " + e.getMessage());
        }
    }

    public ArrayList<EmprestimoDTO> listarHistorico() {
        String sql = "SELECT e.id, e.id_livro, u.nome, l.titulo, e.data_emprestimo, e.data_devolucao "
                + "FROM emprestimos e "
                + "JOIN usuarios u ON e.id_usuario = u.id "
                + "JOIN livros l ON e.id_livro = l.id "
                + "ORDER BY e.id DESC";

        conn = new ConexaoDAO().conectaBD();
        ArrayList<EmprestimoDTO> lista = new ArrayList<>();

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                EmprestimoDTO obj = new EmprestimoDTO();
                obj.setId(rs.getInt("id"));
                obj.setIdLivro(rs.getInt("id_livro"));
                obj.setNomeUsuario(rs.getString("nome"));
                obj.setTituloLivro(rs.getString("titulo"));
                obj.setDataEmprestimo(rs.getString("data_emprestimo"));
                obj.setDataDevolucao(rs.getString("data_devolucao"));

                lista.add(obj);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar histórico: " + e.getMessage());
        }
        return lista;
    }
}
