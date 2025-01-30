import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Db database = new Db();

        // Авторизация игрока 1
        System.out.println("Введите данные для игрока 1.");
        System.out.print("Логин: ");
        String playerOneUsername = input.nextLine();
        System.out.print("Пароль: ");
        String playerOnePassword = input.nextLine();

        if (!database.isUserExists(playerOneUsername, playerOnePassword)) {
            System.out.println("Ошибка авторизации для игрока 1. Завершение программы.");
            database.close();
            return;
        }

        // Авторизация игрока 2
        System.out.println("Введите данные для игрока 2.");
        System.out.print("Логин: ");
        String playerTwoUsername = input.nextLine();
        System.out.print("Пароль: ");
        String playerTwoPassword = input.nextLine();

        if (!database.isUserExists(playerTwoUsername, playerTwoPassword)) {
            System.out.println("Ошибка авторизации для игрока 2. Завершение программы.");
            database.close();
            return;
        }

        System.out.println("Оба игрока успешно авторизованы! Начинаем игру.");

        // Логика игры
        char[][] gameBoard = new char[3][3];
        initializeGameBoard(gameBoard);
        printGameBoard(gameBoard);

        char activePlayer = 'X';
        boolean isWinner = false;

        while (!isWinner) {
            System.out.println("Ход игрока " + activePlayer);
            int row, col;

            // Проверка ввода и обработка хода
            while (true) {
                System.out.print("Введите номер строки (1-3): ");
                row = input.nextInt() - 1;
                System.out.print("Введите номер столбца (1-3): ");
                col = input.nextInt() - 1;

                if (isValidMove(gameBoard, row, col)) {
                    break;
                } else {
                    System.out.println("Некорректный ход! Попробуйте снова.");
                }
            }

            // Обновляем доску
            gameBoard[row][col] = activePlayer;
            printGameBoard(gameBoard);

            // Проверка на победу
            isWinner = checkForWinner(gameBoard, activePlayer);
            if (isWinner) {
                System.out.println("Поздравляем! Игрок " + activePlayer + " победил!");
            } else if (isBoardFull(gameBoard)) {
                System.out.println("Игра завершилась вничью.");
                break;
            }

            // Смена активного игрока
            activePlayer = (activePlayer == 'X') ? 'O' : 'X';
        }

        // Завершение работы
        database.close();
        input.close();
    }

    // Инициализация доски
    public static void initializeGameBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Печать доски
    public static void printGameBoard(char[][] board) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    // Проверка на валидность хода
    public static boolean isValidMove(char[][] board, int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    // Проверка на победу
    public static boolean checkForWinner(char[][] board, char player) {
        // Проверка строк
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        // Проверка столбцов
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }

        // Проверка диагоналей
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    // Проверка на заполненность доски
    public static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
