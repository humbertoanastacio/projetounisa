import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/projeto_db";
    private static final String USUARIO = "root";
    private static final String SENHA = "123456"; // sua senha do MySQL

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("✅ Conectado ao banco de dados com sucesso!");
            return conexao;
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver JDBC não encontrado.");
            return null;
        }
    }
}
