import java.util.Scanner;

public class MinMax_vs_Player {
	
	
	 public static final int MAX_WINNING_SCORE = 999999;
	 public static final int MIN_WINNING_SCORE = -999999;

	 static int turno = 1;
	 static char[][] tablero = new char[6][7];
	 static int maxDepth=4;
	
	public static void main(String[] args) {
		Scanner teclado_introducir_columna = new Scanner(System.in);

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

			tablero=introducirFicha(player1, tablero, columnaEscogida);
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
			char [][] tableroaux=new char[7][6];
			for(int col = 0; col < 7; col++) {
				for(int fil=0;fil<6;fil++) {
tableroaux[col][fil]=tablero[fil][col];
				}
			}
			
			do {
				System.out.println("Turno de MINMAX5: ");
				columnaEscogida=maxPlay(tableroaux, 0, Integer.MIN_VALUE, Integer.MAX_VALUE)[0];
				columnaValida = validarColumna(columnaEscogida, tablero);

			} while (columnaValida == false);

			tablero=introducirFicha(player2, tablero, columnaEscogida);
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
	 public static boolean isDone(int depth, char[][] board, int score)
	    {
	        return depth >= maxDepth || turno > 42 || score >= MAX_WINNING_SCORE || score <= MIN_WINNING_SCORE;
	    }
	private static int[] maxPlay(char[][] board, int depth, int alpha, int beta)
    {
        int score = calcScore('0',board);

        if (isDone(depth, board, score))
            return new int[]{-1, score};

        int[] max = new int[]{-1, 0};

        for (int column = 0; column < 7; column++) {

			char [][] new_board=new char[7][6];
			for(int col = 0; col < 7; col++) {
				for(int fil=0;fil<6;fil++) {
					new_board[col][fil]=board[col][fil];
				}
			}
            if (place(column, true,new_board)) {
                int[] next = minPlay(new_board, depth + 1, alpha, beta);

                if (max[0] == -1 || next[1] > max[1]) {
                    max[0] = column;
                    max[1] = next[1];
                    alpha = next[1];
                }

                if(beta <= alpha)
                    return max;
            }
        }

        return max;
    }
	
	 private static int[] minPlay(char[][] board, int depth, int alpha, int beta)
	    {
	        int score = calcScore('+',board);

	        if (isDone(depth, board, score))
	            return new int[]{-1, score};

	        int[] min = new int[]{-1, 0};

	        for (int column = 0; column < 7; column++) {

				char [][] new_board=new char[7][6];
				for(int col = 0; col < 7; col++) {
					for(int fil=0;fil<6;fil++) {
						new_board[col][fil]=board[col][fil];
					}
				}
	            if (place(column, false,new_board)) {
	                int[] next = maxPlay(new_board, depth + 1, alpha, beta);

	                if (min[0] == -1 || next[1] < min[1]) {
	                    min[0] = column;
	                    min[1] = next[1];
	                    beta = next[1];
	                }

	                if(beta <= alpha)
	                    return min;
	            }
	        }

	        return min;
	    }
	 
	    public static boolean place(int col, boolean isMax, char[][] board)
	    {
	        if (board[col][0] == ' ' && col >= 0 && col < 7) {
	            for (int y = 6 - 1; y >= 0; y--) {
	                if (board[col][y] == ' ') {
	                    board[col][y] = isMax ? '0' : '+';
	                    break;
	                }
	            }
	            return true;
	        } else {
	            return false;
	        }
	    }
	 
	 public static int calcScore(char minMax, char[][] board)
	    {
	        int vertical_points=0, horizontal_points=0, descDiagonal_points=0, ascDiagonal_points=0, total_points=0;

	        for (int row = 0; row < 6 - 3; row++) {
	            for (int column = 0; column < 7; column++) {
	                int tempScore = calcScorePosition(row, column, 1, 0,minMax,board);
	                vertical_points += tempScore;
	                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
	                    return tempScore;
	            }
	        }

	        for (int row = 0; row < 6 ; row++) {
	            for (int column = 0; column < 7 - 3; column++) {
	                int tempScore = calcScorePosition(row, column, 0, 1,minMax,board);
	                horizontal_points += tempScore;
	                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
	                    return tempScore;
	            }
	        }

	        for (int row = 0; row < 6 - 3 ; row++) {
	            for (int column = 0; column < 7 - 3; column++) {
	                int tempScore = calcScorePosition(row, column, 1, 1,minMax,board);
	                descDiagonal_points += tempScore;
	                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
	                    return tempScore;
	            }
	        }

	        for (int row = 3; row < 6  ; row++) {
	            for (int column = 0; column < 7 - 4; column++) {
	                int tempScore = calcScorePosition(row, column, -1, 1,minMax,board);
	                ascDiagonal_points += tempScore;
	                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
	                    return tempScore;
	            }
	        }

	        total_points = vertical_points + horizontal_points + descDiagonal_points + ascDiagonal_points;
	        return total_points;
	    }


	    private static int calcScorePosition(int row, int column, int increment_row, int increment_col, char minMax,char[][] board)
	    {
	        int ai_points = 0, player_points = 0;

	        for (int i = 0; i < 4; i++) //connect "4"
	        {
	            if(board[column][row] == '0')
	            {
	                ai_points++;
	            }
	            else if (board[column][row] == '+')
	            {
	                player_points++;
	            }

	            row += increment_row;
	            column += increment_col;
	        }

	        if(player_points == 4)
	            return MIN_WINNING_SCORE;
	        else if(ai_points == 4)
	            return MAX_WINNING_SCORE;
	        else
	            return ai_points;
	    }

	 
	
	// Introduce Ficha en la Columna Elegida comprobando cual es la posicion libre
	// más baja de la columna
	public static char[][] introducirFicha(char jugador, char[][] tablero, int columnaEscogida) {
		for (int fila = tablero.length - 1; fila >= 0; fila--) {
			if (tablero[fila][columnaEscogida] == ' ') {
				tablero[fila][columnaEscogida] = jugador;
				return tablero;
			}
		}
		return tablero;
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