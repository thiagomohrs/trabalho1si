package puzzle8SistemasInteligentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

	public static int matrizObjetivo[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };

//	public static int matriz[][] = { { 2, 1, 3 }, { 4, 7, 5 }, { 6, 8, 0 } };
//	public static int matriz[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } };
	public static int matriz[][] = { { 1, 7, 2 }, { 0, 6, 7 }, { 5, 4, 3 } };
//	public static int matriz[][] = { { 2, 1, 3 }, { 5, 0, 7 }, { 8, 4, 6 } };
	
	
//	public static int matriz[][] = criarMatrizAleatoria(new int[3][3]);
	
	public int[][] getMatrizObjetivo() {
		return matrizObjetivo;
	}

	public void setMatrizObjetivo(int[][] matrizObjetivo) {
		Main.matrizObjetivo = matrizObjetivo;
	}

	public int[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(int[][] matriz) {
		Main.matriz = matriz;
	}

	public static int[][] criarMatrizAleatoria(int[][] matriz) {
		List<Integer> numeros = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			numeros.add(i);
		}
		Collections.shuffle(numeros);
		int index = 0;
		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				matriz[linha][coluna] = numeros.get(index);
				index++;
			}
		}
		return matriz;
	}
	
	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle();
		puzzle.resolverJogo(matriz, matrizObjetivo);
		
//		Window m = new Window(matriz);
//		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}