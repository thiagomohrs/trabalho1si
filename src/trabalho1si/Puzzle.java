package trabalho1si;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Puzzle {

	// private int[][] matriz = { { 2, 1, 3 }, { 5, 0, 7 }, { 8, 4, 6 } }; // 18 passos teste professor
	// private int[][] matriz = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } }; // 1 passo
	// private int[][] matriz = { { 3, 2, 1 }, { 6, 5, 4 }, { 7, 0, 8 } };
	// private int[][] matriz = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } };
	// private int[][] matriz = { { 1, 0, 3 }, { 7, 4, 6 }, { 5, 8, 2 } };
	// private int[][] matriz = { { 7, 1, 3 }, { 0, 4, 6 }, { 5, 8, 2 } };
	private int[][] matriz = { { 4, 7, 5 }, { 0, 2, 1 }, { 3, 6, 8 } };
	static int[][] matrizObjetivo = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
	// private int[][] matriz = criarMatrizAleatoria(new int[3][3]);

	private List<Nodo> nodosJaVisitados = new ArrayList<>();
	private List<Nodo> nodosFronteiras = new ArrayList<>();
	private List<Nodo> nodosCaminhoFinal = new ArrayList<>();
	private List<Integer> numerosMovidos = new ArrayList<>();
	private Nodo nodoAtual, nodoFilho1, nodoFilho2, nodoFilho3, nodoFilho4;
	private int contadorID = 0;
	private int maiorFronteira = 0;
	private long start;

	Puzzle() {
		this.nodosJaVisitados.clear();
		this.nodosFronteiras.clear();
		this.nodoAtual = new Nodo(0, this.matriz, this.contadorID, -1, -1);
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

	void resolverJogo() {
		this.start = System.currentTimeMillis();
		this.nodosFronteiras.add(this.nodoAtual);
		while (!this.nodosFronteiras.isEmpty()) {
			this.ordenarFronteira();
			this.nodoAtual = this.nodosFronteiras.get(0);
			// this.nodoAtual.imprimirNodo();
			if (this.verificaSeJaFoiVisitado(this.nodoAtual)) {
				this.nodosFronteiras.remove(0);
			} else {
				if (this.ehEstadoFinal(this.nodoAtual.getEstado())) {
					this.finalDeJogo();
					break;
				} else {
					this.nodoAtual.calcularPosicaoVazia();
					this.matriz = this.nodoAtual.getEstado();
					if (this.nodoAtual.getPosicaoXvazia() > 0) {
						this.nodoFilho1 = this.moveCima(this.nodoAtual);
						this.nodosFronteiras.add(this.nodoFilho1);
					}
					if (this.nodoAtual.getPosicaoXvazia() < 2) {
						this.nodoFilho2 = this.moveBaixo(this.nodoAtual);
						this.nodosFronteiras.add(this.nodoFilho2);
					}
					if (this.nodoAtual.getPosicaoYvazia() > 0) {
						this.nodoFilho3 = this.moveEsquerda(this.nodoAtual);
						this.nodosFronteiras.add(this.nodoFilho3);
					}
					if (this.nodoAtual.getPosicaoYvazia() < 2) {
						this.nodoFilho4 = this.moveDireita(this.nodoAtual);
						this.nodosFronteiras.add(this.nodoFilho4);
					}
					this.nodosJaVisitados.add(this.nodoAtual);
					this.maiorFronteira();
					this.nodosFronteiras.remove(this.nodoAtual);
				}
			}
		}
	}

	private void finalDeJogo() {
		long finish = System.currentTimeMillis();
		while (this.nodoAtual.getId() != 0) {
			int idPai = this.nodoAtual.getIddoPai();

			for (int i = 0; i < this.nodosJaVisitados.size(); i++) {
				if (this.nodosJaVisitados.get(i).getId() == idPai) {
					Nodo pai = this.nodosJaVisitados.get(i);
					this.nodosCaminhoFinal.add(this.nodoAtual);
					this.nodoAtual = pai;
					break;
				}
			}
		}
		this.nodosCaminhoFinal.add(this.nodoAtual);
		for (int i = this.nodosCaminhoFinal.size(); i > 0; i--) {
			this.matriz = this.nodosCaminhoFinal.get(i - 1).getEstado();
			this.numerosMovidos.add(this.nodosCaminhoFinal.get(i - 1).getNumeroMovido());
		}
		System.out.println("Quantidade de nodos percorridos: " + this.nodosCaminhoFinal.size());
		System.out.println("Maior Fronteira: " + this.maiorFronteira + " nodos.");
		System.out.println("Números movidos: " + this.numerosMovidos);
		long total = finish - this.start;
		System.out.println("Tempo total execução: " + TimeUnit.MILLISECONDS.toSeconds(total) + " segundos");
	}

	public boolean ehEstadoFinal(int[][] matrizA) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (matrizA[i][j] != Puzzle.matrizObjetivo[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean ehMesmoEstado(int[][] matrizA, int[][] matrizB) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (matrizA[i][j] != matrizB[i][j]) {
					return false;
				}
			}
		}
		return true;

	}

	private Nodo moveBaixo(Nodo atual) {
		int[][] estado = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				estado[i][j] = atual.getEstado()[i][j];
			}
		}
		int x = atual.getPosicaoXvazia();
		int y = atual.getPosicaoYvazia();

		int tmp = estado[x][y];

		estado[x][y] = estado[x + 1][y];
		int numeroMovido = estado[x + 1][y];
		estado[x + 1][y] = tmp;

		return new Nodo(atual.getCusto() + 1, estado, ++this.contadorID, atual.getId(), numeroMovido);
	}

	private Nodo moveDireita(Nodo atual) {
		int[][] estado = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				estado[i][j] = atual.getEstado()[i][j];
			}
		}

		int x = atual.getPosicaoXvazia();
		int y = atual.getPosicaoYvazia();

		int tmp = estado[x][y];

		estado[x][y] = estado[x][y + 1];
		int numeroMovido = estado[x][y + 1];
		estado[x][y + 1] = tmp;

		return new Nodo(atual.getCusto() + 1, estado, ++this.contadorID, atual.getId(), numeroMovido);
	}

	private Nodo moveCima(Nodo atual) {
		int[][] estado = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				estado[i][j] = atual.getEstado()[i][j];
			}
		}

		int x = atual.getPosicaoXvazia();
		int y = atual.getPosicaoYvazia();

		int tmp = estado[x][y];

		estado[x][y] = estado[x - 1][y];
		int numeroMovido = estado[x - 1][y];
		estado[x - 1][y] = tmp;

		return new Nodo(atual.getCusto() + 1, estado, ++this.contadorID, atual.getId(), numeroMovido);
	}

	private Nodo moveEsquerda(Nodo atual) {
		int[][] estado = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				estado[i][j] = atual.getEstado()[i][j];
			}
		}
		int x = atual.getPosicaoXvazia();
		int y = atual.getPosicaoYvazia();

		int tmp = estado[x][y];

		estado[x][y] = estado[x][y - 1];
		int numeroMovido = estado[x][y - 1];
		estado[x][y - 1] = tmp;

		return new Nodo(atual.getCusto() + 1, estado, ++this.contadorID, atual.getId(), numeroMovido);
	}

	private void ordenarFronteira() {
		Collections.sort(this.nodosFronteiras);
	}

	private boolean verificaSeJaFoiVisitado(Nodo nodoAtual) {
		if (!this.nodosJaVisitados.isEmpty()) {
			for (int i = 0; i < this.nodosJaVisitados.size(); i++) {
				if (this.ehMesmoEstado(nodoAtual.getEstado(), this.nodosJaVisitados.get(i).getEstado())) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	void maiorFronteira() {
		if (this.nodosFronteiras.size() > this.maiorFronteira) {
			this.maiorFronteira = this.nodosFronteiras.size();
		}
	}
}
