package Sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sudoku {
    public int[][] matriz;
    private int tam; // Tamaño del susoku 9x9
    private int fila_caja; // filas en la caja 3
    private int columna_caja; // columnas en la caja 3

    public Sudoku() {
        tam = 9;
        fila_caja = 3;
        columna_caja = 3;
        matriz = new int[tam][tam];
    }

    public int largo() {
        return matriz.length;
    }

    public int getFilas_Caja() {
        return fila_caja;
    }

    public int getCols_Caja() {
        return columna_caja;
    }

    public boolean cargarSusoku() {
        try {
            Scanner scan = new Scanner(new File("puzzle.txt"));

            if (!scan.hasNextLine()) {
                System.out.println("Archivo vacío.");
                return false;
            }

            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    matriz[i][j] = scan.nextInt();
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo. crear un puzzle.txt en la misma carpeta.");
        }
        System.out.println("Se cargó el sudoku");
        return true;
    }

    public void printSudoku() {
        int f = 0;
        int c = 0;
        String dibujarCaja = new String(new char[4 * matriz.length + 4]).replace('\0', '-');
        for (int[] i : matriz) {
            if (f % fila_caja == 0)
                System.out.print(dibujarCaja + '\n');
            for (int j : i) {
                if (c % columna_caja == 0)
                    System.out.print('|');
                if (j < 10)
                    System.out.print("  " + j + " ");
                else
                    System.out.print(" " + j + " ");
                c++;
            }
            f++;
            System.out.print("|");
            System.out.println();
        }
        System.out.print(dibujarCaja + '\n');
    }
}
