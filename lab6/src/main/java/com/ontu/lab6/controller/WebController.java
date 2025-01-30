package com.ontu.lab6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
public class WebController {

    @RequestMapping(value = "/index")
    public String index(Model model) {
        // Масив для ігрового поля
        String[][] gameBoard = new String[3][3];

        // Ініціалізація генератора випадкових чисел
        Random random = new Random();

        // Заповнення поля випадковими значеннями ("X", "O", або " ")
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int rand = random.nextInt(3); // Випадкове число між 0 і 2
                if (rand == 0) {
                    gameBoard[i][j] = "X";
                } else if (rand == 1) {
                    gameBoard[i][j] = "O";
                } else {
                    gameBoard[i][j] = " "; // Порожнє місце
                }
            }
        }

        // Перевірка на переможця
        String winner = checkWinner(gameBoard);

        // Додаємо масив і результат у модель
        model.addAttribute("gameBoard", gameBoard);
        model.addAttribute("winner", winner);

        return "index";
    }

    /**
     * Метод для перевірки переможця.
     *
     * @param board Ігрове поле
     * @return Символ переможця ("X", "O") або "Нічия"/"Немає переможця"
     */
    private String checkWinner(String[][] board) {
        // Перевірка рядків
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].equals(" ") && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
                return board[i][0] + " виграв!";
            }
        }

        // Перевірка стовпців
        for (int j = 0; j < 3; j++) {
            if (!board[0][j].equals(" ") && board[0][j].equals(board[1][j]) && board[1][j].equals(board[2][j])) {
                return board[0][j] + " виграв!";
            }
        }

        // Перевірка діагоналей
        if (!board[0][0].equals(" ") && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            return board[0][0] + " виграв!";
        }
        if (!board[0][2].equals(" ") && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
            return board[0][2] + " виграв!";
        }

        // Якщо переможця немає
        return "Немає переможця";
    }
}
