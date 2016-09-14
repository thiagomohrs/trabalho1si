package puzzle8SistemasInteligentes;

import java.util.ArrayList;
import java.util.List;

public class Nodo implements Comparable<Nodo> {
	 protected int[][] estado = new int[3][3];
	 private int id;
	 private int idDoNodoPai;
	 private int custoTotal;
	 protected int posicaoXvazia;
	 protected int posicaoYvazia;
	 protected int posicaoXpeca;
	 protected int posicaoYpeca;
	 private int pecaMovida;
	 List<Integer> distanciaDeCadaValor = new ArrayList<>();

	 public Nodo(int id, int[][] estado, int idDoNodoPai, int custoTotal) {
	        setEstado(estado);
	        this.id = id;
	        this.idDoNodoPai = idDoNodoPai;
	        this.custoTotal = custoTotal;
	    }
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getDistanciaDeCadaValor() {
		return distanciaDeCadaValor;
	}

	public void setDistanciaDeCadaValor(List<Integer> distanciaDeCadaValor) {
		this.distanciaDeCadaValor = distanciaDeCadaValor;
	}

	public void setIdDoNodoPai(int idDoNodoPai) {
		this.idDoNodoPai = idDoNodoPai;
	}

	public void setCustoTotal(int custoTotal) {
		this.custoTotal = custoTotal;
	}

	public int[][] getEstado() {
        return estado;
    }
	
	public int getCustoTotal(){
		return calculaDistanciaTotalDoNodo(this.estado, Main.matrizObjetivo)+calculaPecasComObjetivosInvertidos(this.estado, Main.matrizObjetivo);
	}

    public void setEstado(int[][] estado) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.estado[i][j] = estado[i][j];
            }
        }
    }
    public int getID() {
        return id;
    }

    public int getIdDoNodoPai() {
        return idDoNodoPai;
    }
    
    public int getPosicaoXvazia() {
		return posicaoXvazia;
	}

	public void setPosicaoXvazia(int posicaoXvazia) {
		this.posicaoXvazia = posicaoXvazia;
	}

	public int getPosicaoYvazia() {
		return posicaoYvazia;
	}

	public void setPosicaoYvazia(int posicaoYvazia) {
		this.posicaoYvazia = posicaoYvazia;
	}
	
	public int getPecaMovida() {
		return pecaMovida;
	}

	public void setPecaMovida(int pecaMovida) {
		this.pecaMovida = pecaMovida;
	}

	public int getPosicaoXpeca() {
		return posicaoXpeca;
	}

	public void setPosicaoXpeca(int posicaoXpeca) {
		this.posicaoXpeca = posicaoXpeca;
	}

	public int getPosicaoYpeca() {
		return posicaoYpeca;
	}

	public void setPosicaoYpeca(int posicaoYpeca) {
		this.posicaoYpeca = posicaoYpeca;
	}

	public void calcularPosicaoVazia() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (estado[i][j] == 0) {
                    posicaoXvazia = i;
                    posicaoYvazia = j;
                    break;
                }
            }
        }
    }
	 
	public void calcularPosicaoPeca(int peca) {
	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 3; j++) {
	            if (estado[i][j] == peca) {
	                posicaoXpeca = i;
	                posicaoYpeca = j;
	                break;
	            }
	        }
	    }
	}
	 
	public int calculaDistanciaTotalDoNodo(int[][] matriz, int[][] matrizObjetivo){
		distanciaDeCadaValor.clear();
    	int distanciaTotal = 0;
		for (int linhaObjetivo = 0; linhaObjetivo < 3; linhaObjetivo++) {
			for (int colunaObjetivo = 0; colunaObjetivo < 3; colunaObjetivo++) {
				int valorObjetivo = matrizObjetivo[linhaObjetivo][colunaObjetivo];
				for (int linhaMatriz = 0; linhaMatriz < 3; linhaMatriz++) {
					for (int colunaMatriz = 0; colunaMatriz < 3; colunaMatriz++) {
						int valorMatriz = matriz[linhaMatriz][colunaMatriz];
						int distanciaDoValor = 0;
						if (valorObjetivo == valorMatriz) {
							int distanciaColulas = colunaObjetivo - colunaMatriz;
							int distanciaLinhas = linhaObjetivo - linhaMatriz;
							distanciaDoValor = Math.abs(distanciaColulas) + Math.abs(distanciaLinhas);
							distanciaDeCadaValor.add(distanciaDoValor);
							distanciaTotal += distanciaDoValor;
						}
					}
				}
			}
		}
		System.out.println("Distancia total do nodo: " + distanciaTotal);
    	return distanciaTotal;
    }
	
	public void imprimirNodo(){
		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				System.out.printf("\t %d \t", estado[linha][coluna]);
			}
			System.out.println();
		}
	}
	
	public int calculaQuantidadeDePecasForaDaPosicao(){
		int numeroDePecasForaDaPosicao = 0;
		for (int i = 0; i < distanciaDeCadaValor.size(); i++) {
			if (distanciaDeCadaValor.get(i) > 0) {
				numeroDePecasForaDaPosicao++;
			}
		}
		System.out.println("Numero de peças fora da posição: " + numeroDePecasForaDaPosicao);
		return numeroDePecasForaDaPosicao;
	}
	
	public int calculaPecasComObjetivosInvertidos(int[][] matriz, int[][] objetivo) {
		int valorTotal = 0;
		for (int i = 0; i < distanciaDeCadaValor.size(); i++) {
			int pecaNaPosicaoObjetivo = verificarPecaNaPosicaoObjetivoByPeca(matriz, objetivo, i);
			if (pecaNaPosicaoObjetivo != i) {
				if (pecaNaPosicaoObjetivo == verificarPecaNaPosicaoObjetivoByPeca(matriz, objetivo, pecaNaPosicaoObjetivo)) {
					int valor = distanciaDeCadaValor.get(i);
					distanciaDeCadaValor.set(i+1, valor + 1);
					System.out.println("Distancia do valor: " + i + " aumentada");
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
