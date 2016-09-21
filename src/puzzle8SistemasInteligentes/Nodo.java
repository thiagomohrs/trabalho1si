package puzzle8SistemasInteligentes;

import java.util.ArrayList;
import java.util.List;

public class Nodo implements Comparable<Nodo> {
	protected int[][] estadoPuzzle = new int[3][3];
	private int id;
	private int idDoNodoPai;
	private int custo;
	protected int posicaoXvazia;
	protected int posicaoYvazia;
	protected int posicaoXpeca;
	protected int posicaoYpeca;
	private int pecaMovida;
	List<Integer> distanciaDeCadaValor = new ArrayList<>();

	public Nodo(int id, int[][] estado, int idDoNodoPai, int custo) {
		this.setEstado(estado);
		this.id = id;
		this.idDoNodoPai = idDoNodoPai;
		this.custo = custo;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getDistanciaDeCadaValor() {
		return this.distanciaDeCadaValor;
	}

	public void setDistanciaDeCadaValor(List<Integer> distanciaDeCadaValor) {
		this.distanciaDeCadaValor = distanciaDeCadaValor;
	}

	public void setIdDoNodoPai(int idDoNodoPai) {
		this.idDoNodoPai = idDoNodoPai;
	}

	public void setCusto(int custoTotal) {
		this.custo = custoTotal;
	}

	public int[][] getEstado() {
		return this.estadoPuzzle;
	}

	public int getCusto() {
		return this.custo;
	}

	public void setEstado(int[][] estado) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.estadoPuzzle[i][j] = estado[i][j];
			}
		}
		this.calcularPosicaoVazia();
	}

	public int getIdDoNodoPai() {
		return this.idDoNodoPai;
	}

	public int getPosicaoXvazia() {
		return this.posicaoXvazia;
	}

	public void setPosicaoXvazia(int posicaoXvazia) {
		this.posicaoXvazia = posicaoXvazia;
	}

	public int getPosicaoYvazia() {
		return this.posicaoYvazia;
	}

	public void setPosicaoYvazia(int posicaoYvazia) {
		this.posicaoYvazia = posicaoYvazia;
	}

	public int getPecaMovida() {
		return this.pecaMovida;
	}

	public void setPecaMovida(int pecaMovida) {
		this.pecaMovida = pecaMovida;
	}

	public int getPosicaoXpeca() {
		return this.posicaoXpeca;
	}

	public void setPosicaoXpeca(int posicaoXpeca) {
		this.posicaoXpeca = posicaoXpeca;
	}

	public int getPosicaoYpeca() {
		return this.posicaoYpeca;
	}

	public void setPosicaoYpeca(int posicaoYpeca) {
		this.posicaoYpeca = posicaoYpeca;
	}

	public int getCustoTotal() {
		return this.getCusto() + this.calculaHeuristica(this.estadoPuzzle);
	}

	public void calcularPosicaoVazia() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.estadoPuzzle[i][j] == 0) {
					this.posicaoXvazia = i;
					this.posicaoYvazia = j;
					break;
				}
			}
		}
	}

	public void calcularPosicaoPeca(int peca) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.estadoPuzzle[i][j] == peca) {
					this.posicaoXpeca = i;
					this.posicaoYpeca = j;
					break;
				}
			}
		}
	}

	public int calculaHeuristica(int[][] matriz) {
		this.distanciaDeCadaValor.clear();
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
							this.distanciaDeCadaValor.add(distanciaDoValor);
							distanciaHeuristica += distanciaDoValor;
						}
					}
				}
			}
		}
		return distanciaHeuristica;
	}

	public void imprimirNodo() {
		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				System.out.printf("\t %d \t", this.estadoPuzzle[linha][coluna]);
			}
			System.out.println();
		}
		System.out.println("------------------------------------------");
	}

	public int calculaQuantidadeDePecasForaDaPosicao() {
		int numeroDePecasForaDaPosicao = 0;
		for (int i = 0; i < this.distanciaDeCadaValor.size(); i++) {
			if (this.distanciaDeCadaValor.get(i) > 0) {
				numeroDePecasForaDaPosicao++;
			}
		}
		return numeroDePecasForaDaPosicao;
	}

	public int calculaPecasComObjetivosInvertidos(int[][] matriz, int[][] objetivo) {
		int valorTotal = 0;
		for (int i = 0; i < this.distanciaDeCadaValor.size(); i++) {
			int pecaNaPosicaoObjetivo = this.verificarPecaNaPosicaoObjetivoByPeca(matriz, objetivo, i);
			if (pecaNaPosicaoObjetivo != i) {
				if (pecaNaPosicaoObjetivo == this.verificarPecaNaPosicaoObjetivoByPeca(matriz, objetivo, pecaNaPosicaoObjetivo)) {
					int valor = this.distanciaDeCadaValor.get(i);
					this.distanciaDeCadaValor.set(i + 1, valor + 1);
					valorTotal += 1;
				}
			}
		}
		return valorTotal;
	}

	public int verificarPecaNaPosicaoObjetivoByPeca(int[][] matriz, int[][] objetivo, int peca) {
		int valor = 0;
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

	@Override
	public int compareTo(Nodo outroNodo) {
		if (this.getCustoTotal() < outroNodo.getCustoTotal()) {
			return -1;
		}
		if (this.getCustoTotal() > outroNodo.getCustoTotal()) {
			return +1;
		}
		return 0;
	}
}
