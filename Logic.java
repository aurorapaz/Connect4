import java.util.Scanner;

public class Logic {

	public static void main(String[] args) {
		Scanner teclado_introducir_columna = new Scanner(System.in);

		char[][] tablero = new char[6][7];
		int turno = 1;
		char player1 = '+';
		char player2 = '0';
		boolean winner = false;

		// Ponemos la matriz a blancos
		for (int fila = 0; fila < tablero.length; fila++) {
			for (int columna = 0; columna < tablero[0].length; columna++) {
				tablero[fila][columna] = ' ';
			}
		}

		actualizarTablero(tablero); // Imprime el tablero en blanco al inicio

		// JUGAR hasta que alguien gane o hasta que se llene el tablero
		while (winner == false && turno <= 42) {
			boolean columnaValida;
			int columnaEscogida;

			// Juega JUGADOR 1
			do {

				System.out.print("Turno Jugador1 (+)... Elige la columna: ");
				columnaEscogida = teclado_introducir_columna.nextInt() - 1;
				columnaValida = validarColumna(columnaEscogida, tablero);

			} while (columnaValida == false);

			introducirFicha(player1, tablero, columnaEscogida);
			actualizarTablero(tablero);

			// Comprueba si ha ganado el Jugador 1
			winner = ganador(player1, tablero);

			// Si ya gano salimos
			if (winner) {
				System.out.println("Gano el jugador 1");
				break;
			}

			turno++;

			// Juega JUGADOR 2
			do {

				System.out.print("Turno Jugador2 (0)... Elige la columna: ");
				columnaEscogida = teclado_introducir_columna.nextInt() - 1;
				columnaValida = validarColumna(columnaEscogida, tablero);

			} while (columnaValida == false);

			introducirFicha(player2, tablero, columnaEscogida);
			actualizarTablero(tablero);

			// Comprueba si ha ganado el Jugador 2
			winner = ganador(player2, tablero);

			// Si ya gano salimos
			if (winner) {
				System.out.println("Gano el jugador 2");
				break;
			}

			turno++;
		}

		if (turno > 42) {
			System.out.println("¡ El tablero se lleno !");
			System.out.println("RESULTADO= EMPATE");
		}

		teclado_introducir_columna.close();

	}

	// Introduce Ficha en la Columna Elegida comprobando cual es la posicion libre
	// más baja de la columna
	public static void introducirFicha(char jugador, char[][] tablero, int columnaEscogida) {
		for (int fila = tablero.length - 1; fila >= 0; fila--) {
			if (tablero[fila][columnaEscogida] == ' ') {
				tablero[fila][columnaEscogida] = jugador;
				break;
			}
		}
	}

	public static void actualizarTablero(char[][] tablero) {
		System.out.println(" 1 2 3 4 5 6 7");
		System.out.println("###############");
		for (int fila = 0; fila < tablero.length; fila++) {
			System.out.print("|");
			for (int col = 0; col < tablero[0].length; col++) {
				System.out.print(tablero[fila][col]);
				System.out.print("|");
			}
			System.out.println();
			System.out.println("###############");
		}
		System.out.println();
	}

	public static boolean validarColumna(int columna_seleccionada, char[][] tablero) {
		// Comprobamos que la columna esta en el tablero
		if (columna_seleccionada < 0 || columna_seleccionada > 6) {
			System.out.println("¡ Vuelve a intentarlo porque la columna no es valida ! ");
			return false;
		}

		// La columna esta llena si el hueco superior no esta vacio
		if (tablero[0][columna_seleccionada] != ' ') {
			System.out.println("¡ Vuelve a intentarlo porque la columna esta llena ! ");
			return false;
		}

		return true;
	}

	public static boolean ganador(char jugador, char[][] tablero) {

		// Comprueba si 4 fichas en horizontal
		for (int fila = 0; fila < 6; fila++) {
			for (int columna = 0; columna < 4; columna++) {
				if (tablero[fila][columna] == jugador && tablero[fila][columna + 1] == jugador
						&& tablero[fila][columna + 2] == jugador && tablero[fila][columna + 3] == jugador) {
					return true;
				}
			}
		}

		// Comprueba si 4 fichas en vertical
		for (int fila = 0; fila < 3; fila++) {
			for (int columna = 0; columna < 7; columna++) {
				if (tablero[fila][columna] == jugador && tablero[fila + 1][columna] == jugador
						&& tablero[fila + 2][columna] == jugador && tablero[fila + 3][columna] == jugador) {
					return true;
				}
			}
		}

		// Comprueba si 4 fichas en diagonal ascendente hacia la derecha (equivale a
		// diagonal descendente hacia la izquierda)
		for (int fila = 3; fila < 6; fila++) {
			for (int columna = 0; columna < 4; columna++) {
				if (tablero[fila][columna] == jugador && tablero[fila - 1][columna + 1] == jugador
						&& tablero[fila - 2][columna + 2] == jugador && tablero[fila - 3][columna + 3] == jugador) {
					return true;
				}
			}
		}

		// Comprueba si 4 fichas en diagonal descendente hacia la derecha (equivale a
		// diagonal ascendente hacia la izquierda)
		for (int fila = 0; fila < 3; fila++) {
			for (int columna = 0; columna < 4; columna++) {
				if (tablero[fila][columna] == jugador && tablero[fila + 1][columna + 1] == jugador
						&& tablero[fila + 2][columna + 2] == jugador && tablero[fila + 3][columna + 3] == jugador) {
					return true;
				}
			}
		}
		return false;
	}
}