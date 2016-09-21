package teste;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Jogo {

	private List<Nodo> nodosJaVisitados = new ArrayList<>();
	private List<Nodo> nodosFronteiras = new ArrayList<>();
	private List<Nodo> nodosCaminhoFinal = new ArrayList<>();
	private Nodo nodoAtual, nodoFilho1, nodoFilho2, nodoFilho3, nodoFilho4;
	private int[][] matriz = { { 2, 1, 3 }, { 5, 0, 7 }, { 8, 4, 6 } }; // 18 passos
	// private int[][] matriz = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } }; // 1 passos
	private int[][] matrizObjetivo = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
	private int contadorID = 0;
	private int maiorFronteira = 0;

	Jogo() {
		this.nodosJaVisitados.clear();
		this.nodosFronteiras.clear();
		this.nodoAtual = new Nodo(0, this.matriz, this.contadorID, -1);
	}

	void resolver8puzzle() {

		this.nodosFronteiras.add(this.nodoAtual);
		while (!this.nodosFronteiras.isEmpty()) {
			this.ordenarFronteira();

			this.nodoAtual = this.nodosFronteiras.get(0);
			if (this.checarJaVisitados(this.nodoAtual)) {
				this.nodosFronteiras.remove(0);
			} else {

				if (this.ehEstadoFinal(this.nodoAtual.getEstado())) {
					this.fimDeJogo();
					break;
				} else { // Continua Pesquisa
					this.nodoAtual.calcularPosicaoVazia();
					this.matriz = this.nodoAtual.getEstado();

					// FAZ AS TROCAS E CRIA OS NODOS FILHOS
					// 0 NA POSICAO 00
					if (this.nodoAtual.getPosicaoXvazia() > 0) {
						// this.nodoFilho1 = this.mover(this.nodoAtual, 0);
						this.nodoFilho1 = this.moveCima(this.nodoAtual);
						this.nodosFronteiras.add(this.nodoFilho1);
					}
					if (this.nodoAtual.getPosicaoXvazia() < 2) {
						// this.nodoFilho1 = this.mover(this.nodoAtual, 1);
						this.nodoFilho2 = this.moveBaixo(this.nodoAtual);
						this.nodosFronteiras.add(this.nodoFilho2);
					}
					if (this.nodoAtual.getPosicaoYvazia() > 0) {
						// this.nodoFilho1 = this.mover(this.nodoAtual, 2);
						this.nodoFilho3 = this.moveEsquerda(this.nodoAtual);
						this.nodosFronteiras.add(this.nodoFilho3);
					}
					if (this.nodoAtual.getPosicaoYvazia() < 2) {
						// this.nodoFilho1 = this.mover(this.nodoAtual, 3);
						this.nodoFilho4 = this.moveDireita(this.nodoAtual);
						this.nodosFronteiras.add(this.nodoFilho4);
					}
					/*
					 * if (this.nodoAtual.getPosicaoXvazia() == 0 && this.nodoAtual.getPosicaoYvazia() == 0) {
					 * this.nodoFilho1 = this.moveBaixo(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho1);
					 * this.nodoFilho2 = this.moveDireita(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho2);
					 * } else {
					 * // 0 NA POSICAO 01
					 * if (this.nodoAtual.getPosicaoXvazia() == 0 && this.nodoAtual.getPosicaoYvazia() == 1) {
					 * this.nodoFilho1 = this.moveEsquerda(this.nodoAtual);
					 * this.nodoFilho2 = this.moveDireita(this.nodoAtual);
					 * this.nodoFilho3 = this.moveBaixo(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho1);
					 * this.nodosFronteiras.add(this.nodoFilho2);
					 * this.nodosFronteiras.add(this.nodoFilho3);
					 * } else {
					 * // 0 NA POSICAO 02
					 * if (this.nodoAtual.getPosicaoXvazia() == 0 && this.nodoAtual.getPosicaoYvazia() == 2) {
					 * this.nodoFilho1 = this.moveBaixo(this.nodoAtual);
					 * this.nodoFilho2 = this.moveEsquerda(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho1);
					 * this.nodosFronteiras.add(this.nodoFilho2);
					 *
					 * } else {
					 * // 0 NA POSICAO 10
					 * if (this.nodoAtual.getPosicaoXvazia() == 1 && this.nodoAtual.getPosicaoYvazia() == 0) {
					 * this.nodoFilho1 = this.moveBaixo(this.nodoAtual);
					 * this.nodoFilho2 = this.moveDireita(this.nodoAtual);
					 * this.nodoFilho3 = this.moveCima(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho1);
					 * this.nodosFronteiras.add(this.nodoFilho2);
					 * this.nodosFronteiras.add(this.nodoFilho3);
					 * } else {
					 * // 0 NA POSICAO 11
					 * if (this.nodoAtual.getPosicaoXvazia() == 1 && this.nodoAtual.getPosicaoYvazia() == 1) {
					 * this.nodoFilho1 = this.moveBaixo(this.nodoAtual);
					 * this.nodoFilho2 = this.moveDireita(this.nodoAtual);
					 * this.nodoFilho3 = this.moveEsquerda(this.nodoAtual);
					 * this.nodoFilho4 = this.moveCima(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho1);
					 * this.nodosFronteiras.add(this.nodoFilho2);
					 * this.nodosFronteiras.add(this.nodoFilho3);
					 * this.nodosFronteiras.add(this.nodoFilho4);
					 * } else {
					 * // 0 NA POSICAO 12
					 * if (this.nodoAtual.getPosicaoXvazia() == 1 && this.nodoAtual.getPosicaoYvazia() == 2) {
					 *
					 * this.nodoFilho1 = this.moveBaixo(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho1);
					 * this.nodoFilho2 = this.moveEsquerda(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho2);
					 * this.nodoFilho3 = this.moveCima(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho3);
					 * } else {
					 * // 0 NA POSICAO 20
					 * if (this.nodoAtual.getPosicaoXvazia() == 2 && this.nodoAtual.getPosicaoYvazia() == 0) {
					 * this.nodoFilho1 = this.moveCima(this.nodoAtual);
					 * this.nodoFilho2 = this.moveDireita(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho1);
					 * this.nodosFronteiras.add(this.nodoFilho2);
					 *
					 * } else {
					 *
					 * // 0 NA POSICAO 21
					 * if (this.nodoAtual.getPosicaoXvazia() == 2 && this.nodoAtual.getPosicaoYvazia() == 1) {
					 * this.nodoFilho1 = this.moveDireita(this.nodoAtual);
					 * this.nodoFilho2 = this.moveEsquerda(this.nodoAtual);
					 * this.nodoFilho3 = this.moveCima(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho1);
					 * this.nodosFronteiras.add(this.nodoFilho2);
					 * this.nodosFronteiras.add(this.nodoFilho3);
					 *
					 * } else {
					 *
					 * // 0 NA POSICAO 22
					 * if (this.nodoAtual.getPosicaoXvazia() == 2 && this.nodoAtual.getPosicaoYvazia() == 2) {
					 * this.nodoFilho1 = this.moveCima(this.nodoAtual);
					 * this.nodoFilho2 = this.moveEsquerda(this.nodoAtual);
					 * this.nodosFronteiras.add(this.nodoFilho1);
					 * this.nodosFronteiras.add(this.nodoFilho2);
					 *
					 * }
					 * }
					 * }
					 *
					 * }
					 * }
					 * }
					 * }
					 *
					 * }
					 * }
					 */

					this.nodosJaVisitados.add(this.nodoAtual);
					this.maiorFronteira();
					this.nodosFronteiras.remove(this.nodoAtual);

				}

			}
		}
	}

	private void fimDeJogo() {
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
			this.nodoAtual.imprimirNodo();
		}
		System.out.println("Caminho final - " + this.nodosCaminhoFinal.size() + " passos.");
		System.out.println("Ultimo nodo - ID " + this.nodosCaminhoFinal.get(0).getID());
		System.out.println("Maior Fronteira - " + this.maiorFronteira + " nodos.");
	}

	public boolean ehEstadoFinal(int[][] A) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (A[i][j] != this.matrizObjetivo[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean ehMesmoEstado(int[][] A, int[][] B) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (A[i][j] != B[i][j]) {
					return false;
				}
			}
		}
		return true;

	}

	private Nodo mover(Nodo atual, int direcao) {
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
		switch (direcao) {
		case 0:
			estadoTemp[x][y] = estadoTemp[x + 1][y];
			estadoTemp[x - 1][y] = tmp;
			break;
		case 1:
			estadoTemp[x][y] = estadoTemp[x + 1][y];
			estadoTemp[x + 1][y] = tmp;
			break;
		case 2:
			estadoTemp[x][y] = estadoTemp[x + 1][y];
			estadoTemp[x][y - 1] = tmp;
			break;
		case 3:
			estadoTemp[x][y] = estadoTemp[x + 1][y];
			estadoTemp[x][y + 1] = tmp;
			break;
		default:
			break;
		}

		return new Nodo(atual.getCusto() + 1, estadoTemp, ++this.contadorID, atual.getID());
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

	private boolean checarJaVisitados(Nodo nodoAtual) {
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
