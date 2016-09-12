package puzzle8SistemasInteligentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

public class Main {

	int distanciaTotal = 0;

	public static void imprimirMatriz3x3(int[][] matriz) {
		System.out.println("\nA Matriz ficou: \n");
		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				System.out.printf("\t %d \t", matriz[linha][coluna]);
			}
			System.out.println();
		}
	}

	public static int[][] usuarioCriarMatriz(int[][] matriz) {
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
						}
					}
				}
			}
		}
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

	public static int adicionarBonusDeDistanciaParaPecasComObjetivosInversos(int[][] matriz, int objetivo[][], List<Integer> distancia, int distanciaTotal) {
		// Aqui verifica se o objetivo da peca que esta na posição objetivo é a propria peça e adiciona + 1 a distancia total
		for (int i = 1; i < distancia.size(); i++) {
			int pecaNaPosicaoObjetivo = verificarPecaNaPosicaoObjetivoByPeca(matriz, objetivo, i);
			if (pecaNaPosicaoObjetivo != i) {
				if (pecaNaPosicaoObjetivo == verificarPecaNaPosicaoObjetivoByPeca(matriz, objetivo, pecaNaPosicaoObjetivo)) {
					int valor = distancia.get(i);
					distancia.set(i, valor + 1);
					distanciaTotal += 1;
				}
			}
		}
		return distanciaTotal;
	}

	// acho melhor dividir em varios metodos: verificarPosicaoObjetivoByPeca
	public static int verificarPecaNaPosicaoObjetivoByPeca(int[][] matriz, int objetivo[][], int peca) {
		int valor = 0;
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

	public static void iniciarArrayList(List<Integer> array) {
		for (int i = 0; i < 10; i++) {
			array.add(-1);
		}
	}

	public static int calculaPecasForaDaPosicao(List<Integer> distancia) {
		int numeroDePecasForaDaPosicao = 0;
		for (int i = 1; i < distancia.size(); i++) {
			if (distancia.get(i) > 0) {
				numeroDePecasForaDaPosicao++;
			}
		}
		return numeroDePecasForaDaPosicao;
	}

	public static boolean isMatrizIgualMatrizObjetivo(int matriz[][], int objetivo[][]) {
		if (matriz == objetivo) {
			return true;
		}
		return false;
	}

	public static int[][] movimento(int matriz[][], List<Integer> distancia) {
		int maior = 0;
		ArrayList<Integer> ultimaPecaTrocada = new ArrayList<>();
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if (matriz[i][j] == 9) {
					List<Integer> valoresProximos = new ArrayList();
					if (i - 1 >= 0 && i - 1 <= 2) {
						valoresProximos.add(matriz[i - 1][j]);
						System.out.println("i-1: " + matriz[i - 1][j] + " | Distancia da pocicao objetivo: " + distancia.get(matriz[i - 1][j]));
					}
					if (i + 1 >= 0 && i + 1 <= 2) {
						valoresProximos.add(matriz[i + 1][j]);
						System.out.println("i+1: " + matriz[i + 1][j] + " | Distancia da pocicao objetivo: " + distancia.get(matriz[i + 1][j]));
					}
					if (j - 1 >= 0 && j - 1 <= 2) {
						valoresProximos.add(matriz[i][j - 1]);
						System.out.println("j-1: " + matriz[i][j - 1] + " | Distancia da pocicao objetivo: " + distancia.get(matriz[i][j - 1]));
					}
					if (j + 1 >= 0 && j + 1 <= 2) {
						valoresProximos.add(matriz[i][j + 1]);
						System.out.println("j+1: " + matriz[i][j + 1] + " | Distancia da pocicao objetivo: " + distancia.get(matriz[i][j + 1]));
					}
					// FIXME Funciona a primara vez, depois ele fica trocando de lugar com o mesmo
					// problema parece com o do leandro, vai ter que verificar e pegar outro que não seja o ultimo
					for (int j1 = 0; j1 < valoresProximos.size(); j1++) {
						if (distancia.get(valoresProximos.get(j1)) > distancia.get(valoresProximos.get(maior))) {
							maior = valoresProximos.get(j1);
						}
					}
					System.out.println("Valor proximo com maior distancia: " + maior);
					for (int i2 = 0; i2 < 3; i2++) {
						for (int j2 = 0; j2 < 3; j2++) {
							if (matriz[i2][j2] == maior) {
								matriz[i][j] = maior;
								matriz[i2][j2] = 9;
								imprimirMatriz3x3(matriz);
							}
						}
					}
					ultimaPecaTrocada.add(maior);
				}
			}

		}
		return matriz;
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
		iniciarArrayList(distancia);

		// pede para usuario criar matriz
		// criarMatriz(matriz);

		// cria matriz aleatória
		criarMatrizAleatoria(matriz);

		Window m = new Window(matriz);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// imprime matriz criada
		imprimirMatriz3x3(matriz);

		calculaDistancia(matriz, objetivo, distancia);

		int distanciaTotal = calculaDistanciaTotal(distancia);

		distanciaTotal = adicionarBonusDeDistanciaParaPecasComObjetivosInversos(matriz, objetivo, distancia, distanciaTotal);

		imprimirPeloArrayList(distancia);
		System.out.println("Distancia Total: " + distanciaTotal);

		System.out.println("Numero de peças fora da posição: " + calculaPecasForaDaPosicao(distancia));

		do {
			movimento(matriz, distancia);
			calculaDistancia(matriz, objetivo, distancia);
		} while (objetivo != matriz);
		imprimirMatriz3x3(matriz);
	}

}
