import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ListaAlunos extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;

    public ListaAlunos() {
        setTitle("Lista de Alunos");
        setSize(700, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel();
        modelo.addColumn("RA");
        modelo.addColumn("Nome");
        modelo.addColumn("Endereço");
        modelo.addColumn("Telefone");
        modelo.addColumn("E-mail");
        modelo.addColumn("Curso");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 20, 640, 300);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(290, 330, 120, 25);

        add(scroll);
        add(btnAtualizar);

        btnAtualizar.addActionListener(e -> carregarAlunos());
        carregarAlunos();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarAlunos() {
        modelo.setRowCount(0);
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT a.ra, a.nome, a.endereco, a.telefone, a.email, c.nome AS curso " +
                 "FROM alunos a JOIN cursos c ON a.id_curso = c.id_curso ORDER BY a.nome")) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("ra"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("curso")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alunos!");
        }
    }
}
