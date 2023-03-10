package br.com.carrara.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.carrara.cm.excecao.ExplosaoException;

public class Campo {
	private final int linha;
	private final int coluna;

	private boolean aberto;
	private boolean marcado;
	private boolean minado;

	List<Campo> vizinhos = new ArrayList<>();

	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	boolean addVizinho(Campo vizinho) {
		boolean difLinha = linha != vizinho.linha;
		boolean difColuna = coluna != vizinho.coluna;
		boolean diagonal = difLinha && difColuna;

		int delColuna= Math.abs(coluna - vizinho.coluna);
		int delLinha= Math.abs(linha - vizinho.linha);
		int delAll = delLinha + delColuna;

		if (delAll == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (delAll == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	void altMarca() {
		if (!aberto) {
			marcado = !marcado;
		}
	}

	boolean abrir() {

		if (!aberto && !marcado) {
			aberto = true;
			
			if (minado) {
				throw new ExplosaoException();
			}

			if (safeViziinhaca()) {
				vizinhos.forEach(Campo::abrir);
			}
			return true;

		} else {

			return false;
		}
	}

	boolean safeViziinhaca() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	void minar() {
		minado = true;
	}
	
	public boolean isMarcado() {
		return marcado;
	}

	void setAberto(boolean aberto){
		this.aberto = aberto;
	}
	public boolean isAberto() {
		return aberto;
	}
	public int getLinha() {
		return linha;
	}
	public boolean isMinado(){
		return minado;
	}
	public int getColuna() {
		return coluna;
	}

	boolean objetiAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		
		return desvendado || protegido;
	}
	
	long minasVizinhas() {
		return vizinhos.stream().filter(v->v.minado).count();
	}
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "X";
		}else if (aberto && minado) {
			return"*";
		} else if(aberto && minasVizinhas()  > 0) {
			return Long.toString(minasVizinhas());
		}else if(aberto) {
			return " ";
		} else  {
			return "?";
		}
	}
}
