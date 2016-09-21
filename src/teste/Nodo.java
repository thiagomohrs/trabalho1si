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
		return this.getCusto() + this.calcularHeuristica()+this.getQuantidadePosicoesInvertidas();
	}

	public int calcularHeuristica() {
		
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
		
		int quantDuplas = 0;
		int posAtual [] = new int[2];
		int [] posObjetivo = new int[2];
		
		for(int valor = 1; valor < 9; valor++){
			if(valorEstaEmPosicaoInversaoDireta(valor,matriz)){
				
				posAtual = this.getPosicaoDoValor(valor, matriz); 
				posObjetivo = this.getPosicaoFinalDoValor(valor);
				
				int lOrigN = posAtual[0];
				int cOrigN = posAtual[1];
				int nDest = Puzzle.matrizObjetivo[lOrigN][cOrigN];
				int lDestN = posObjetivo[0];
				int cDestN = posObjetivo[1];
				int nOrigN = matriz[lDestN][cDestN];
				
				if(nDest == nOrigN)
					quantDuplas++;
			}
		}
		return quantDuplas;	
	}
	
	//Retorna true se o valor esta em posicao de inversao direta
	public boolean valorEstaEmPosicaoInversaoDireta(int valor, int[][]matriz){
		
		int [] coordenadas = new int [2];
		coordenadas = getPosicaoDoValor(valor, matriz);
		int l = coordenadas[0];
		int c = coordenadas[1];
		
		if(valor==1){
			if((l==0 && c==1) || (l==1 && c==0))
				return true;
		}
		if(valor==2){
			if((l==0 && c==0) || (l==0 && c==2)|| (l==1 && c==1))
				return true;
		}
		if(valor==3){
			if((l==0 && c==1) || (l==1 && c==2))
				return true;
		}
		if(valor==4){
			if((l==0 && c==0) || (l==2 && c==0)|| (l==1 && c==1))
				return true;
		}
		if(valor==5){
			if((l==0 && c==1) || (l==1 && c==0)|| (l==2 && c==1) || (l==1 && c==2))
				return true;
		}
		if(valor==6){
			if((l==0 && c==2) || (l==1 && c==1))
				return true;
		}
		if(valor==7){
			if((l==1 && c==0) || (l==2 && c==1))
				return true;
		}
		if(valor==8){
			if((l==1 && c==1) || (l==2 && c==0))
				return true;
		}
	return false;
		
	}
	//Retorna o par [x,y] da posição final do valor recebido por parâmetro
	public int [] getPosicaoFinalDoValor(int valor){
		int [] posicao = new int [2];
		int [][] objetivo = new int[3][3];
		objetivo = Puzzle.matrizObjetivo;
		for(int l = 0; l < objetivo.length; l++){
			for(int c = 0; c < objetivo.length; c++){
				if(objetivo[l][c] == valor){
					posicao[0] = l;
					posicao[1] = c;
				}
			}
		
		}
		return posicao;
		
	}
	//Retorna o par [x,y] da posição do valor recebido por parâmetro
	public int[] getPosicaoDoValor(int valor, int [][] tabuleiro){
		int [] posicao = new int [2];
		for(int l = 0; l < tabuleiro.length; l++){
			for(int c = 0; c < tabuleiro.length; c++){
				if(tabuleiro[l][c] == valor){
					posicao[0] = l;
					posicao[1] = c;
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
