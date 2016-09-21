package teste;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Puzzle {
	
	private int[][] matriz = { { 2, 1, 3 }, { 5, 0, 7 }, { 8, 4, 6 } }; // 18 passos teste professor
	// private int[][] matriz = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } }; // 1 passo
	
	static int[][] matrizObjetivo = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };

	private List<Nodo> nodosJaVisitados = new ArrayList<>();
	private List<Nodo> nodosFronteiras = new ArrayList<>();
	private List<Nodo> nodosCaminhoFinal = new ArrayList<>();
	private Nodo nodoAtual, nodoFilho1, nodoFilho2, nodoFilho3, nodoFilho4;
	private int contadorID = 0;
	private int maiorFronteira = 0;

	Puzzle() {
		this.nodosJaVisitados.clear();
		this.nodosFronteiras.clear();
		this.nodoAtual = new Nodo(0, this.matriz, this.contadorID, -1);
	}

	void resolverJogo() {
		this.nodosFronteiras.add(this.nodoAtual);
		while (!this.nodosFronteiras.isEmpty()) {
			this.ordenarFronteira();
			this.nodoAtual = this.nodosFronteiras.get(0);
			this.nodoAtual.imprimirNodo();
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
		while (this.nodoAtual.getID() != 0) {
			int idPai = this.nodoAtual.getIddoPai();

			for (int i = 0; i < this.nodosJaVisitados.size(); i++) {
				if (this.nodosJaVisitados.get(i).getID() == idPai) {
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
		}
		System.out.println("Caminho final: " + (this.nodosCaminhoFinal.size()-1) + " passos.");
		System.out.println("Id do Ultimo nodo: " + this.nodosCaminhoFinal.get(0).getID());
		System.out.println("Maior Fronteira: " + this.maiorFronteira + " nodos.");
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
		int[][] estadoTemp = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				estadoTemp[i][j] = atual.getEstado()[i][j];
			}
		}
		int x = atual.getPosicaoXvazia();
		int y = atual.getPosicaoYvazia();

		int tmp = estadoTemp[x][y];
		estadoTemp[x][y] = estadoTemp[x + 1][y];
		estadoTemp[x + 1][y] = tmp;

		return new Nodo(atual.getCusto() + 1, estadoTemp, ++this.contadorID, atual.getID());
	}

	private Nodo moveDireita(Nodo atual) {
		int[][] estadoTemp = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				estadoTemp[i][j] = atual.getEstado()[i][j];
			}
		}

		int x = atual.getPosicaoXvazia();
		int y = atual.getPosicaoYvazia();

		int tmp = estadoTemp[x][y];
		estadoTemp[x][y] = estadoTemp[x][y + 1];
		estadoTemp[x][y + 1] = tmp;

		return new Nodo(atual.getCusto() + 1, estadoTemp, ++this.contadorID, atual.getID());
	}

	private Nodo moveCima(Nodo atual) {
		int[][] estadoTemp = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				estadoTemp[i][j] = atual.getEstado()[i][j];
			}
		}

		int x = atual.getPosicaoXvazia();
		int y = atual.getPosicaoYvazia();

		int tmp = estadoTemp[x][y];
		estadoTemp[x][y] = estadoTemp[x - 1][y];
		estadoTemp[x - 1][y] = tmp;

		return new Nodo(atual.getCusto() + 1, estadoTemp, ++this.contadorID, atual.getID());
	}

	private Nodo moveEsquerda(Nodo atual) {
		int[][] estadoTemp = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				estadoTemp[i][j] = atual.getEstado()[i][j];
			}
		}
		int x = atual.getPosicaoXvazia();
		int y = atual.getPosicaoYvazia();

		int tmp = estadoTemp[x][y];
		estadoTemp[x][y] = estadoTemp[x][y - 1];
		estadoTemp[x][y - 1] = tmp;

		return new Nodo(atual.getCusto() + 1, estadoTemp, ++this.contadorID, atual.getID());
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
