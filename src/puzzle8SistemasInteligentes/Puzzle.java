package puzzle8SistemasInteligentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Puzzle {
	
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
        System.out.println("Nodo inicial: ");
		nodoAtual.imprimirNodo();
		nodoAtual.getCustoTotal();
		while (!isFinalDeJogo(nodoAtual.getEstado())) {
			if (fronteiras.size() > 0) {
				this.ordenarFronteiras();
	            nodoAtual = fronteiras.get(0);
	            nodoAtual.getCustoTotal();
	            caminhoFinal.add(nodoAtual);
	            System.out.println("Nodo Selecionado: ");
	            nodoAtual.imprimirNodo();
			}
			if(isFinalDeJogo(nodoAtual.getEstado())){
				System.out.println("Total de nodos: " + caminhoFinal.size());
				break;
			}
			nodoAtual.calcularPosicaoVazia();
	        matriz = nodoAtual.getEstado();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
				if(matriz[i][j] == 0){
					if (i - 1 >= 0 && i - 1 <= 2) {
						int peca = matriz[i - 1][j];
						Nodo nodoAux = nodoAtual;
						nodoFilho1 = movimento(nodoAux, peca);
						fronteiras.add(nodoFilho1);
					}
					if (i + 1 >= 0 && i + 1 <= 2) {
						int peca = matriz[i + 1][j];
						Nodo nodoAux = nodoAtual;
						nodoFilho2 = movimento(nodoAux, peca);
						fronteiras.add(nodoFilho2);
					}
					if (j - 1 >= 0 && j - 1 <= 2) {
						int peca = matriz[i][j - 1];
						Nodo nodoAux = nodoAtual;
						nodoFilho3 = movimento(nodoAux, peca);
						fronteiras.add(nodoFilho3);
					}
					if (j + 1 >= 0 && j + 1 <= 2) {
						int peca = matriz[i][j + 1];
						Nodo nodoAux = nodoAtual;
						nodoFilho4 = movimento(nodoAux, peca);
						fronteiras.add(nodoFilho4);
					}
				}
			}
		}
	        maiorFronteira();
	        fronteiras.remove(nodoAtual);
		}
		System.out.println("Nodo Final: ");
		nodoAtual.imprimirNodo();
	}
	
	public Nodo movimento(Nodo nodo, int peca){
		Nodo novoNodo = new Nodo(contadorId++, nodo.estadoPuzzle , nodo.getId(), nodo.getCustoTotal());
		novoNodo.calcularPosicaoVazia();
		novoNodo.calcularPosicaoPeca(peca);
		novoNodo.estadoPuzzle[novoNodo.getPosicaoXvazia()][novoNodo.getPosicaoYvazia()] = peca;
		novoNodo.estadoPuzzle[novoNodo.getPosicaoXpeca()][novoNodo.getPosicaoYpeca()] = 0;
		novoNodo.getCustoTotal();
		System.out.println("Nodo Filho: ");
		novoNodo.imprimirNodo();
		return novoNodo;
	}
	
    public boolean isFinalDeJogo(int[][] matriz) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matriz[i][j] != Main.matrizObjetivo[i][j]) {
                    return false;
                }
            }
        }
        return true;
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
