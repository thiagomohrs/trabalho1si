package trabalho1si;

public class Nodo implements Comparable<Nodo> {

	private int[][] estado = new int[3][3];

	private int custo;
	private int id;
	private int iddoPai;
	private int posicaoXvazia;
	private int posicaoYvazia;
	private int posicaoXvalor;
	private int posicaoYvalor;
	private int posicaoXvalorObjetivo;
	private int posicaoYvalorObjetivo;
	private int numeroMovido;
	
	public Nodo(int custo, int[][] estado, int id, int idNodoDoPai, int numeroMovido) {
		this.custo = custo;
		this.setEstado(estado);
		this.id = id;
		this.iddoPai = idNodoDoPai;
		this.numeroMovido = numeroMovido;
	}

	public int[][] getEstado() {
		return this.estado;
	}

	public int getNumeroMovido() {
		return numeroMovido;
	}

	public void setNumeroMovido(int numeroMovido) {
		this.numeroMovido = numeroMovido;
	}

	public void setEstado(int[][] estado) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.estado[i][j] = estado[i][j];
			}
		}
		this.calcularPosicaoVazia();
		this.calcularDistanciaPosicoes();
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

	public int getId() {
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
	
	public int getPosicaoXvalor() {
		return posicaoXvalor;
	}

	public void setPosicaoXvalor(int posicaoXvalor) {
		this.posicaoXvalor = posicaoXvalor;
	}

	public int getPosicaoYvalor() {
		return posicaoYvalor;
	}

	public void setPosicaoYvalor(int posicaoYvalor) {
		this.posicaoYvalor = posicaoYvalor;
	}

	public int getPosicaoXvalorObjetivo() {
		return posicaoXvalorObjetivo;
	}

	public void setPosicaoXvalorObjetivo(int posicaoXvalorObjetivo) {
		this.posicaoXvalorObjetivo = posicaoXvalorObjetivo;
	}

	public int getPosicaoYvalorObjetivo() {
		return posicaoYvalorObjetivo;
	}

	public void setPosicaoYvalorObjetivo(int posicaoYvalorObjetivo) {
		this.posicaoYvalorObjetivo = posicaoYvalorObjetivo;
	}

	public void setCusto(int custo) {
		this.custo = custo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIddoPai(int iddoPai) {
		this.iddoPai = iddoPai;
	}

	public void setPosicaoXvazia(int posicaoXvazia) {
		this.posicaoXvazia = posicaoXvazia;
	}

	public void setPosicaoYvazia(int posicaoYvazia) {
		this.posicaoYvazia = posicaoYvazia;
	}

	public void imprimirNodo() {
		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				System.out.printf("\t %d \t", this.estado[linha][coluna]);
			}
			System.out.println();
		}
		System.out.println("Custo Total: " + this.getCustoMaisHeuristica());
		System.out.println("------------------------------------------");
	}

	public int getCustoMaisHeuristica() {
		return this.getCusto() + this.calcularDistanciaPosicoes() + this.getQuantidadePosicoesInvertidas();
	}

	public int calcularDistanciaPosicoes() {	
		int[][] matriz = this.getEstado();

		int distanciaHeuristica = 0;
		for (int linhaObjetivo = 0; linhaObjetivo < 3; linhaObjetivo++) {
			for (int colunaObjetivo = 0; colunaObjetivo < 3; colunaObjetivo++) {
				int valorObjetivo = Puzzle.matrizObjetivo[linhaObjetivo][colunaObjetivo];
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
	
	public int getQuantidadePosicoesInvertidas(){
		int quantidadeDeDuplas = 0;

		int[][] matriz = this.getEstado();
		
		for(int valor = 1; valor < 9; valor++){
			if(verificaSeValorEstaDoLadoDaPosicaoObjetivo(valor)){
				
				getPosicaoDoValor(valor); 
				getPosicaoObjetivoDoValor(valor);
			
				int numeroDestino = Puzzle.matrizObjetivo[getPosicaoXvalor()][getPosicaoYvalor()];
				int numeroOrigem = matriz[getPosicaoXvalorObjetivo()][getPosicaoYvalorObjetivo()];
				
				if(numeroDestino == numeroOrigem)
					quantidadeDeDuplas++;
			}
		}
		return quantidadeDeDuplas;	
	}
	
	public boolean verificaSeValorEstaDoLadoDaPosicaoObjetivo(int valor){
		int [][] matriz = this.getEstado();
		int [][] matrizObjetivo = Puzzle.matrizObjetivo;
		
		for (int i = 0; i < matrizObjetivo.length; i++) {
			for (int j = 0; j < matrizObjetivo.length; j++) {
				if(matrizObjetivo[i][j] == valor){
					if (i > 0 && matriz[i-1][j] == valor) {
						return true;
					}
					if (i < 2 && matriz[i+1][j] == valor) {
						return true;
					}
					if (j > 0 && matriz[i][j-1] == valor) {
						return true;
					}
					if (j < 2 && matriz[i][j+1] == valor) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void getPosicaoObjetivoDoValor(int valor){
		int [][] objetivo = Puzzle.matrizObjetivo;
		for(int linha = 0; linha < objetivo.length; linha++){
			for(int coluna = 0; coluna < objetivo.length; coluna++){
				if(objetivo[linha][coluna] == valor){
					setPosicaoXvalorObjetivo(linha);
					setPosicaoYvalorObjetivo(coluna);
				}
			}
		}
	}

	public void getPosicaoDoValor(int valor){
		int [][] matriz = this.getEstado();
		for(int linha = 0; linha < matriz.length; linha++){
			for(int coluna = 0; coluna < matriz.length; coluna++){
				if(matriz[linha][coluna] == valor){
					setPosicaoXvalor(linha);
					setPosicaoYvalor(coluna);
				}
			}
		}
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
}
