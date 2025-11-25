/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author bsale
 */
public class ConexaoDAO {

    public Connection conectaBD() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/sistema_biblioteca";
            String user = "root";
            // Configure sua senha do banco aqui
            String password = "SUA_SENHA";

            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ConexaoDAO: Erro ao conectar com o Banco de Dados", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }

}
