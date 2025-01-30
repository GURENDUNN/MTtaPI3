import java.sql.*;

public class Db {
    String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=lab4;encrypt=false;trustServerCertificate=true;";
    String user = "kinnats";
    String password = "password"; 
    
    
    
    

    Connection con;
    public Db() {
        try {
            // Загружаем драйвер для SQL Server
            System.out.println("Попытка подключения...");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.con = DriverManager.getConnection(dbUrl,user,password);  // Подключение через локальный сервер
            if (this.con != null) {
                System.out.println("Соединение с базой данных успешно установлено!");
            } else {
                System.out.println("Не удалось установить соединение с базой данных.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка SQL: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка загрузки драйвера: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка подключения: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Соединение закрыто.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка закрытия соединения: " + e.getMessage());
        }
    }

    // Метод для проверки существования пользователя
    public boolean isUserExists(String username, String password) {
        if (con == null) {
            System.out.println("Соединение не установлено!");
            return false;
        }

        try {
            String query = "SELECT count(*) FROM users WHERE username=? AND password=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 1) {
                System.out.println("Пользователь найден!");
                return true;
            } else {
                System.out.println("Пользователь не найден!");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка запроса: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        Db db = new Db();
        
        // Пример использования метода для проверки пользователя
        boolean exists = db.isUserExists("Player1", "Pass1");
        if (exists) {
            System.out.println("Успешная авторизация.");
        } else {
            System.out.println("Неверные данные пользователя.");
        }

        // Закрытие соединения
        db.close();
    }
}
