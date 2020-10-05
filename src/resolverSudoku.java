package Sudoku;

import java.util.Scanner;

public class resolverSudoku {
    private Sudoku sud;
    private int tam;

    public resolverSudoku() {
        sud = new Sudoku();
        tam = 9;
    }

    public boolean resolver(int row, int col) {
        sud.printSudoku();
        if (col == tam) {
            // Si la columna es igual a 9 (size), nos devolvemos a 0 y aumentamos la fila
            col = 0;
            row += 1;
            if (row == tam) {
                return true;
                // Si la fila es igual a 9 (size), es nuestra ultima celda y ya se resolvió el
                // sudoku, devolvemos true
            }
        }

        for (int num = 1; num < tam + 1; num++)
        // Empezamos a iterar entre la fila 1 a la 9
        {
            if (sud.matriz[row][col] == 0)
            // Si el valor del sudoku[row][col] es igual a 0, entonces tenemos que buscar su
            // respectivo valor
            {
                if (legal(row, col, num))
                // Si el valor es legal en esa posición entonces lo ponemos y avanzamos
                {
                    sud.matriz[row][col] = num;
                    if (resolver(row, col + 1))
                    // Inducimos un metodo recursivo que busca rellenar cada celda con un valor
                    // valido hasta llegar al final.
                    {
                        return true;
                    } else
                    // Si no hay un valor para la celda que sea legal,
                    // retorna falso y hace que el llamado anterior coloque un cero y
                    // que se repita la función hasta encontrar un valor legal.
                    {
                        sud.matriz[row][col] = 0;
                    }
                }
                // Esto lo hacemos hasta llegar a la ultima celda donde si encuentra un valor
                // legal para esa celda, lo coloca y returna verdadero haciendo que se termine
                // la recursividad y colapsando todo con sus respetivos valores
            } else {
                // Si la celda tiene un valor asignado la saltamos
                return resolver(row, col + 1);
            }
        }
        return false;
        // No se encontró un valor legal/solución retornamos falso.
    }
    // Si pasamos por todas las celdas y las llenamos retornamos verdadero (aparece
    // arriba al llegar a la ultima celda)

    public boolean legal(int row, int col, int num) {
        for (int j = 0; j < tam; j++) // Revisa si está el mismo numero en la fila.
        {
            if (sud.matriz[row][j] == num) {
                return false;
            }
        }

        for (int r = 0; r < tam; r++) // Revisa si está el mismo numero en la columna.
        {
            if (sud.matriz[r][col] == num) {
                return false;
            }
        }
        // Revisa si está el mismo numero en la
        // caja.
        int filaArribaDer = (row / sud.getFilas_Caja()) * sud.getFilas_Caja();
        int colArribaDer = (col / sud.getCols_Caja()) * sud.getCols_Caja();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (sud.matriz[filaArribaDer + i][colArribaDer + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        resolverSudoku sp = new resolverSudoku();
        if (!sp.sud.cargarSusoku()) {
            return;
        }
        long inicio = System.currentTimeMillis();
        if (sp.resolver(0, 0)) {
            System.out.println("Solución encontrada\n");
            sp.sud.printSudoku();
            System.out.println("\nSe demoró " + (System.currentTimeMillis() - inicio) + "ms para resolver el sudoku.");
        } else {
            System.out.println("No Encontré una solución!");
        }

    }
}
