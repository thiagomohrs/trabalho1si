package teste;

public class Nodo implements Comparable<Nodo> {

	private int[][] estado = new int[3][3];

	private int custo;
	private int id;
	private int iddoPai;
	private int posicaoXvazia;
	private int posicaoYvazia;
	
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
		
		int[][] matriz = this.getEstado();
		
		int quantidadeDeDuplas = 0;
		int [] posicaoAtual  = new int[2];
		int [] posicaoObjetivo = new int[2];
		
		for(int valor = 1; valor < 9; valor++){
			if(verificaSeValorEstaEmPosicaoProximaAPosicaoObjetivo(valor)){
				
				posicaoAtual = this.getPosicaoDoValor(valor); 
				posicaoObjetivo = this.getPosicaoFinalDoValor(valor);
				
				int linaOrigem = posicaoAtual[0];
				int colunaOrigem = posicaoAtual[1];
				int numeroDestino = Puzzle.matrizObjetivo[linaOrigem][colunaOrigem];
				int linhaDestino = posicaoObjetivo[0];
				int colunaDestino = posicaoObjetivo[1];
				int numeroOrigem = matriz[linhaDestino][colunaDestino];
				
				if(numeroDestino == numeroOrigem)
					quantidadeDeDuplas++;
			}
		}
		return quantidadeDeDuplas;	
	}
	
	public boolean verificaSeValorEstaEmPosicaoProximaAPosicaoObjetivo(int valor){
		int [] coordenadas = new int [2];
		coordenadas = getPosicaoDoValor(valor);
		int linha = coordenadas[0];
		int coluna = coordenadas[1];
		
		if(valor==1){
			if((linha==0 && coluna==1) || (linha==1 && coluna==0))
				return true;
		}
		if(valor==2){
			if((linha==0 && coluna==0) || (linha==0 && coluna==2)|| (linha==1 && coluna==1))
				return true;
		}
		if(valor==3){
			if((linha==0 && coluna==1) || (linha==1 && coluna==2))
				return true;
		}
		if(valor==4){
			if((linha==0 && coluna==0) || (linha==2 && coluna==0)|| (linha==1 && coluna==1))
				return true;
		}
		if(valor==5){
			if((linha==0 && coluna==1) || (linha==1 && coluna==0)|| (linha==2 && coluna==1) || (linha==1 && coluna==2))
				return true;
		}
		if(valor==6){
			if((linha==0 && coluna==2) || (linha==1 && coluna==1))
				return true;
		}
		if(valor==7){
			if((linha==1 && coluna==0) || (linha==2 && coluna==1))
				return true;
		}
		if(valor==8){
			if((linha==1 && coluna==1) || (linha==2 && coluna==0))
				return true;
		}
	return false;
		
	}

	public int [] getPosicaoFinalDoValor(int valor){
		int [] posicao = new int [2];
		int [][] objetivo = new int[3][3];
		objetivo = Puzzle.matrizObjetivo;
		for(int linha = 0; linha < objetivo.length; linha++){
			for(int c = 0; c < objetivo.length; c++){
				if(objetivo[linha][c] == valor){
					posicao[0] = linha;
					posicao[1] = c;
				}
			}
		
		}
		return posicao;
		
	}

	public int[] getPosicaoDoValor(int valor){
		int [] posicao = new int [2];
		int[][] matriz = this.getEstado();
		for(int linha = 0; linha < matriz.length; linha++){
			for(int coluna = 0; coluna < matriz.length; coluna++){
				if(matriz[linha][coluna] == valor){
					posicao[0] = linha;
					posicao[1] = coluna;
				}
			}
		
		}
		return posicao;
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
