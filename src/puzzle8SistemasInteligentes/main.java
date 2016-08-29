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
		int distanciaTotal = 0;
		// intera na matriz objetivo
		for (int linhaobjetivo = 0; linhaobjetivo < 3; linhaobjetivo++) {
			for (int colunaobjetivo = 0; colunaobjetivo < 3; colunaobjetivo++) {
				int valorob = objetivo[linhaobjetivo][colunaobjetivo];
				// intera na matriz criada
				for (int linhamatriz = 0; linhamatriz < 3; linhamatriz++) {
					for (int colunamatriz = 0; colunamatriz < 3; colunamatriz++) {
						int valorma = matriz[linhamatriz][colunamatriz];
						int distanciaDoValor = 0;
						// quando o valor da matriz criada é igual a da matriz objetivo entra no if
						if (valorob == valorma) {
							int distanciaColulas = colunaobjetivo - colunamatriz;
							int distanciaLinhas = linhaobjetivo - linhamatriz;
							distanciaDoValor = Math.abs(distanciaColulas) + Math.abs(distanciaLinhas);
							// adiciona a distancia para aquela peça no arraylist
							distancia.set(valorob, distanciaDoValor);
							// imprime a distancia para cada peça(numero)
							System.out.println("Peça nº: " + valorob + " | Distancia de posição objetivo: " + distanciaDoValor);
						}
						distanciaTotal += distanciaDoValor;
					}
				}
			}
		}
		// imprime somatorio de todas as distancias
		System.out.println("disttotal: " + distanciaTotal);
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

	// acho melhor dividir em varios metodos: verificarPosicaoObjetivoByPeca
	public static int verificarPecaNaPosicaoObjetivoByPeca(int[][] matriz, int objetivo[][], int peca) {
		int valor = 0;
		// TODO metodo para mudar ditancia dependendo da posicao objetivo
		// verificar qual peça está na posição objetivo da peça que veio por parametro, e verificar se a posição objetivo da peça que está no objetivo é a que a peça que veio por
		// parametro está localizada
		for (int linhaobjetivo = 0; linhaobjetivo < 3; linhaobjetivo++) {
			for (int colunaobjetivo = 0; colunaobjetivo < 3; colunaobjetivo++) {
				int valorObjetivo = objetivo[linhaobjetivo][colunaobjetivo];
				if (peca == valorObjetivo) {
					valor = matriz[linhaobjetivo][colunaobjetivo];
				}
			}
		}
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
		// inicia arraylist com -1
		distancia.add(-1);
		distancia.add(-1);
		distancia.add(-1);
		distancia.add(-1);
		distancia.add(-1);
		distancia.add(-1);
		distancia.add(-1);
		distancia.add(-1);
		distancia.add(-1);
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

		int distanciaTotal = calculaDistanciaTotal(distancia);

		// Aqui verifica se o objetivo da peca que esta na posição objetivo é a propria peça e adiciona + 1 a distancia total
		// FIXME ta errado :) mas ta meio certo
		for (int i = 1; i < distancia.size(); i++) {
			if (verificarPecaNaPosicaoObjetivoByPeca(matriz, objetivo, i) == verificarPecaNaPosicaoObjetivoByPeca(matriz, objetivo,
					verificarPecaNaPosicaoObjetivoByPeca(matriz, objetivo, i))) {
				int valor = distancia.get(i);
				distancia.set(i, valor + 1);
				distanciaTotal += 1;
			}
		}

		System.out.println(distancia);
		imprimirPeloArrayList(distancia);
		System.out.println("Distancia Total: " + distanciaTotal);
	}
}
