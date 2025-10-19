import javax.swing.*;
import java.sql.*;

public class CadastroAluno extends JFrame {
    private JTextField txtRa, txtNome, txtEndereco, txtTelefone, txtEmail;
    private JComboBox<String> comboCurso;
    private JButton btnSalvar;

    public CadastroAluno() {
        setTitle("Cadastro de Aluno");
        setSize(400, 350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // para fechar só esta janela

        JLabel lblRa = new JLabel("RA:");
        JLabel lblNome = new JLabel("Nome:");
        JLabel lblEndereco = new JLabel("Endereço:");
        JLabel lblTelefone = new JLabel("Telefone:");
        JLabel lblEmail = new JLabel("E-mail:");
        JLabel lblCurso = new JLabel("Curso:");

        txtRa = new JTextField();
        txtNome = new JTextField();
        txtEndereco = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();
        comboCurso = new JComboBox<>();
        btnSalvar = new JButton("Salvar");

        lblRa.setBounds(30, 30, 100, 25);
        txtRa.setBounds(150, 30, 200, 25);

        lblNome.setBounds(30, 70, 100, 25);
        txtNome.setBounds(150, 70, 200, 25);

        lblEndereco.setBounds(30, 110, 100, 25);
        txtEndereco.setBounds(150, 110, 200, 25);

        lblTelefone.setBounds(30, 150, 100, 25);
        txtTelefone.setBounds(150, 150, 200, 25);

        lblEmail.setBounds(30, 190, 100, 25);
        txtEmail.setBounds(150, 190, 200, 25);

        lblCurso.setBounds(30, 230, 100, 25);
        comboCurso.setBounds(150, 230, 200, 25);

        btnSalvar.setBounds(150, 270, 100, 30);

        add(lblRa); add(txtRa);
        add(lblNome); add(txtNome);
        add(lblEndereco); add(txtEndereco);
        add(lblTelefone); add(txtTelefone);
        add(lblEmail); add(txtEmail);
        add(lblCurso); add(comboCurso);
        add(btnSalvar);

        carregarCursos();
        btnSalvar.addActionListener(e -> salvarAluno());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarCursos() {
        try (Connection con = Conexao.conectar()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nome FROM cursos");
            comboCurso.removeAllItems();
            while (rs.next()) {
                comboCurso.addItem(rs.getString("nome"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cursos!");
            e.printStackTrace();
        }
    }

    private void salvarAluno() {
        if (txtRa.getText().isEmpty() || txtNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!");
            return;
        }

        try (Connection con = Conexao.conectar()) {
            String sql = """
                INSERT INTO alunos (ra, nome, endereco, telefone, email, id_curso)
                VALUES (?, ?, ?, ?, ?, (SELECT id_curso FROM cursos WHERE nome = ?))
            """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, txtRa.getText());
            stmt.setString(2, txtNome.getText());
            stmt.setString(3, txtEndereco.getText());
            stmt.setString(4, txtTelefone.getText());
            stmt.setString(5, txtEmail.getText());
            stmt.setString(6, comboCurso.getSelectedItem().toString());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");

            // limpa os campos após salvar
            txtRa.setText("");
            txtNome.setText("");
            txtEndereco.setText("");
            txtTelefone.setText("");
            txtEmail.setText("");
            comboCurso.setSelectedIndex(0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar aluno!");
            e.printStackTrace();
        }
    }
}
