package teste;

import puzzle8SistemasInteligentes.Main;

public class Nodo implements Comparable<Nodo> {

	// Array bidimensional com as posicoes

	private int[][] estado = new int[3][3];

	private int custo;
	private int id;
	private int iddoPai;
	private int posicaoXvazia;
	private int posicaoYvazia;
	private int heuristica;

	public Nodo(int custo, int[][] estado, int id, int iddoPai) {
		this.custo = custo;
		this.setEstado(estado);
		this.id = id;
		this.iddoPai = iddoPai;
	}

	public int[][] getEstado() {
		return this.estado;
	}

	public void setEstado(int[][] estado) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.estado[i][j] = estado[i][j];
			}
		}
		this.calcularPosicaoVazia();
		this.calcularHeuristica();
	}

	public void calcularPosicaoVazia() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.estado[i][j] == 0) {
					this.posicaoXvazia = i;
					this.posicaoYvazia = j;
					break;
				}
			}
		}
	}

	public int getID() {
		return this.id;
	}

	public int getIddoPai() {
		return this.iddoPai;
	}

	public int getCusto() {
		return this.custo;
	}

	public int getPosicaoXvazia() {
		return this.posicaoXvazia;
	}

	public int getPosicaoYvazia() {
		return this.posicaoYvazia;
	}

	public int getCustoMaisHeuristica() {
		return this.getCusto() + this.calcularHeuristica();
	}

	public int calcularHeuristica() {
		int[][] matriz = this.getEstado();

		int distanciaHeuristica = 0;
		for (int linhaObjetivo = 0; linhaObjetivo < 3; linhaObjetivo++) {
			for (int colunaObjetivo = 0; colunaObjetivo < 3; colunaObjetivo++) {
				int valorObjetivo = Main.matrizObjetivo[linhaObjetivo][colunaObjetivo];
				for (int linhaMatriz = 0; linhaMatriz < 3; linhaMatriz++) {
					for (int colunaMatriz = 0; colunaMatriz < 3; colunaMatriz++) {
						int valorMatriz = matriz[linhaMatriz][colunaMatriz];
						int distanciaDoValor = 0;
						if (valorObjetivo == valorMatriz) {
							int distanciaColulas = colunaObjetivo - colunaMatriz;
							int distanciaLinhas = linhaObjetivo - linhaMatriz;
							distanciaDoValor = Math.abs(distanciaColulas) + Math.abs(distanciaLinhas);
							distanciaHeuristica += distanciaDoValor;
						}
					}
				}
			}
		}
		return distanciaHeuristica;
	}

	@Override
	public int compareTo(Nodo outroNodo) {
		if (this.getCustoMaisHeuristica() < outroNodo.getCustoMaisHeuristica()) {
			return -1;
		}
		if (this.getCustoMaisHeuristica() > outroNodo.getCustoMaisHeuristica()) {
			return +1;
		}
		return 0;
	}

	public void imprimirNodo() {
		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				System.out.printf("\t %d \t", this.estado[linha][coluna]);
			}
			System.out.println();
		}
		System.out.println("------------------------------------------");
	}

}
