package puzzle8SistemasInteligentes;

import javax.swing.JFrame;

public class Main {

	public static int matrizObjetivo[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };

	public static int matriz[][] = { { 2, 1, 3 }, { 4, 6, 5 }, { 7, 8, 0 } };
	
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

	public static void main(String[] args) {
		Principal principal = new Principal();
		principal.resolverJogo(matriz, matrizObjetivo);
		
//		Window m = new Window(matriz);
//		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}