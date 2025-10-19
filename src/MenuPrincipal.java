import javax.swing.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("Menu Principal");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnCadastro = new JButton("Cadastrar Aluno");
        JButton btnLista = new JButton("Listar Alunos");
        JButton btnSair = new JButton("Sair");

        btnCadastro.setBounds(70, 30, 150, 30);
        btnLista.setBounds(70, 70, 150, 30);
        btnSair.setBounds(70, 110, 150, 30);

        add(btnCadastro);
        add(btnLista);
        add(btnSair);

        btnCadastro.addActionListener(e -> new CadastroAluno());
        btnLista.addActionListener(e -> new ListaAlunos());
        btnSair.addActionListener(e -> System.exit(0));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuPrincipal();
    }
}
