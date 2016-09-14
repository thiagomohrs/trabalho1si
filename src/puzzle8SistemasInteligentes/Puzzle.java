package puzzle8SistemasInteligentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Puzzle {

	private List<Nodo> jaVisitados = new ArrayList<Nodo>();
	private List<Nodo> fronteiras = new ArrayList<Nodo>();
	private List<Nodo> caminhoFinal = new ArrayList<Nodo>();
    private Nodo nodoAtual;
	private Nodo nodoFilho1, nodoFilho2, nodoFilho3, nodoFilho4;
	private int maiorFronteira = 0;
    private int contadorId = 0;
    
	public int[][] usuarioCriarMatriz(int[][] matriz) {
		System.out.println("Matriz M[3][3]\n");

		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				System.out.printf("Insira o elemento M[%d][%d]: ", linha + 1, coluna + 1);
				matriz[linha][coluna] = new Scanner(System.in).nextInt();
			}
		}
		return matriz;
	}
	
	public void resolverJogo(int[][] matriz, int[][] matrizObjetivo) {
        nodoAtual = new Nodo(0, matriz, -1, 0);
		nodoAtual.imprimirNodo();
		nodoAtual.getCustoTotal();
		while (nodoAtual.getEstado() != matrizObjetivo) {
			if (jaVisitados.contains(nodoAtual)) {
	            fronteiras.remove(nodoAtual);
	        } 
			if (fronteiras.size() > 0) {
				this.ordenarFronteiras();
	            nodoAtual = fronteiras.get(0);
	            caminhoFinal.add(nodoAtual);
			}
			nodoAtual.calcularPosicaoVazia();
	        matriz = nodoAtual.getEstado();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
				if(matriz[i][j] == 0){
					if (i - 1 >= 0 && i - 1 <= 2) {
						int peca = matriz[i - 1][j];
						nodoFilho1 = movimento(nodoAtual, peca);
						fronteiras.add(nodoFilho1);
					}
					if (i + 1 >= 0 && i + 1 <= 2) {
						int peca = matriz[i + 1][j];
						nodoFilho2 = movimento(nodoAtual, peca);
						fronteiras.add(nodoFilho2);
					}
					if (j - 1 >= 0 && j - 1 <= 2) {
						int peca = matriz[i][j - 1];
						nodoFilho3 = movimento(nodoAtual, peca);
						fronteiras.add(nodoFilho3);
					}
					if (j - 1 >= 0 && j + 1 <= 2) {
						int peca = matriz[i][j + 1];
						nodoFilho4 = movimento(nodoAtual, peca);
						fronteiras.add(nodoFilho4);
					}
				}
			}
		}
			jaVisitados.add(nodoAtual);
	        maiorFronteira();
	        fronteiras.remove(nodoAtual);
		}
		nodoAtual.imprimirNodo();
		System.out.println("Caminho Final" + caminhoFinal);
	}
	
	public Nodo movimento(Nodo nodo, int peca){
		Nodo novoNodo = new Nodo(nodo.getID()+1, nodo.estado , ++contadorId, nodo.getCustoTotal() + 1);
		novoNodo.calcularPosicaoVazia();
		novoNodo.calcularPosicaoPeca(peca);
		novoNodo.estado[novoNodo.getPosicaoXvazia()][novoNodo.getPosicaoYvazia()] = peca;
		novoNodo.estado[novoNodo.getPosicaoXpeca()][novoNodo.getPosicaoYpeca()] = 0;
		novoNodo.imprimirNodo();
		return novoNodo;
	}
	
    private void ordenarFronteiras() {
        Collections.sort(fronteiras);
    }
	
	void maiorFronteira() {
        if (fronteiras.size() > this.maiorFronteira) {
            maiorFronteira = fronteiras.size();
        }
        System.out.println("Maior Fronteira até agora: " + maiorFronteira);
    }
}
