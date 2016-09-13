package puzzle8SistemasInteligentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Principal {

	private List<Nodo> jaVisitados = new ArrayList<Nodo>();
	private List<Nodo> fronteiras = new ArrayList<Nodo>();
	private List<Nodo> caminhoFinal = new ArrayList<Nodo>();
    private int[][] matriz = Main.matriz;
    private int[][] matrizObjetivo = Main.matrizObjetivo;
	private Nodo nodoAtual;
	private Nodo filho1;
	private Nodo filho2;
	private Nodo filho3;
	private Nodo filho4;
	private int distanciaTotal = 0;
    private int maiorFronteira = 0;
    
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

	public int[][] criarMatrizAleatoria(int[][] matriz) {
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
	
	public void resolverJogo(int[][] matriz, int[][] matrizObjetivo) {
        nodoAtual = new Nodo(0, matriz, -1);
		nodoAtual.imprimirNodo();
		nodoAtual.getCustoTotal();
//		while (matriz != matrizObjetivo) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.println("Valor: "+ matriz[i][j] +" | i: "+i+" | j: "+j);
					if(matriz[i][j] == 0){
					}
				}
			}
//		};
	}
	
    private void ordenarFronteiras() {
        Collections.sort(fronteiras);
    }
	
	void maiorFronteira() {
        if (fronteiras.size() > this.maiorFronteira) {
            maiorFronteira = fronteiras.size();
        }
    }
}
