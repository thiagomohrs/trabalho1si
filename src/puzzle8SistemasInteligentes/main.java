package puzzle8SistemasInteligentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void imprimir(int[][] matriz) {
		System.out.println("\nA Matriz ficou: \n");
		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				System.out.printf("\t %d \t", matriz[linha][coluna]);
			}
			System.out.println();
		}
	}

	public static int[][] criarMatriz(int[][] matriz) {
		System.out.println("Matriz M[3][3]\n");

		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				System.out.printf("Insira o elemento M[%d][%d]: ", linha + 1, coluna + 1);
				matriz[linha][coluna] = new Scanner(System.in).nextInt();
			}
		}
		return matriz;
	}

	public static void calculaDistancia(int[][] matriz, int objetivo[][], List<Integer> distancia) {
		int disttotal = 0;
		for (int linhaob = 0; linhaob < 3; linhaob++) {
			for (int colunaob = 0; colunaob < 3; colunaob++) {
				int valorob = objetivo[linhaob][colunaob];
				for (int linhama = 0; linhama < 3; linhama++) {
					for (int colunama = 0; colunama < 3; colunama++) {
						int valorma = matriz[linhama][colunama];
						int distvalor = 0;
						if (valorob == valorma) {
							int distcol = colunaob - colunama;
							int distlin = linhaob - linhama;
							distvalor = Math.abs(distcol) + Math.abs(distlin);
							// adiciona a distancia para aquela peça no arraylist
							distancia.add(valorob, distvalor);
							// imprime a distancia para cada peça(numero)
							System.out.println("Peça nº: " + valorob + " | Distancia de posição objetivo: " + distvalor);
						}
						disttotal += distvalor;
					}
				}
			}
		}
		// imprime somatorio de todas as distancias
		System.out.println("disttotal: " + disttotal);
	}

	public static int[][] criarMatrizAleatoria(int[][] matriz) {
		List<Integer> numeros = new ArrayList<>();
		for (int i = 0; i < 9; i++) { // Sequencia da mega sena
			numeros.add(i);
		}
		Collections.shuffle(numeros);
		int index = 0;
		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				matriz[linha][coluna] = numeros.get(index) + 1;
				index++;
			}
		}
		return matriz;
	}

	private static void imprimirPeloArrayList(List<Integer> distancia) {
		for (int i = 1; i < distancia.size(); i++) {
			System.out.println("Peça: " + i + " | Distancia: " + distancia.get(i));
		}

	}

	public static int calculaDistanciaTotal(List<Integer> distancia) {
		int distanciaTotal = 0;
		for (int i = 1; i < distancia.size(); i++) {
			distanciaTotal += distancia.get(i);
		}
		return distanciaTotal;
	}

	public static int verificarSePosicaoObjetivoEstaProxima(int[][] matriz, int objetivo[][], int peca) {
		int valor = 0;
		// TODO metodo para mudar ditancia dependendo da posicao objetivo
		return valor;
	}

	public static void main(String[] args) {

		// matriz objetivo
		int objetivo[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

		// matriz criada
		int matriz[][] = new int[3][3];

		// ArrayList para salvar a peça e sua distancia
		// index é o valor da peça
		List<Integer> distancia = new ArrayList<>();
		// inicia index 0 com dist -1
		distancia.add(-1);

		// pede para usuario criar matriz
		// criarMatriz(matriz);

		// cria matriz aleatória
		criarMatrizAleatoria(matriz);

		// imprime matriz criada
		imprimir(matriz);

		calculaDistancia(matriz, objetivo, distancia);

		System.out.println(distancia);
		imprimirPeloArrayList(distancia);

		System.out.println("Distancia Total: " + calculaDistanciaTotal(distancia));

		verificarSePosicaoObjetivoEstaProxima(matriz, objetivo, 1);

	}

}
