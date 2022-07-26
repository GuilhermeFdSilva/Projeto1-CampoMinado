package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Campo {
	private final int linha;
	private final int coluna;
	private boolean minado;
	private boolean marcado;
	private boolean aberto;
	private List<Campo> vizinhos = new ArrayList<>();
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	boolean adicionarVizinho(Campo vizinho) {
		int d1 = Math.abs(vizinho.coluna - this.coluna);
		int d2 = Math.abs(vizinho.linha - this.linha);
		if (d1 == 0 && d2 == 1) {
			this.vizinhos.add(vizinho);
			return true;
		} else if (d1 == 1 && d2 == 0) {
			this.vizinhos.add(vizinho);
			return true;
		} else if (d1 == 1 && d2 == 1) {
			this.vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	void alternarMarcacao() {
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
			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
			return false;
		}
	}
	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	public boolean isMarcado() {
		return marcado;
	}
	void minar() {
		minado = true;
	}
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}
	public boolean isAberto() {
		return aberto;
	}
	public boolean isMinado() {
		return minado;
	}
	public int getLinha() {
		return linha;
	}
	public int getColuna() {
		return coluna;
	}
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	void reiniciar() {
		minado = false;
		marcado = false;
		aberto = false;
	}
	public String toString() {
		if(marcado) {
			return "x";
		}else if(aberto && minado) {
			return "*";
		}else if(aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		}else if(aberto) {
			return " ";
		}else {
			return "?";
		}
	}
}