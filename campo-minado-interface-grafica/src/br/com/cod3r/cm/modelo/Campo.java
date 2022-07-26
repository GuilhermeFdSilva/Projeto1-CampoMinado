package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {
	private final int linha;
	private final int coluna;
	private boolean minado;
	private boolean marcado;
	private boolean aberto;
	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observadores = new ArrayList<>();
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	public void registrarObservador(CampoObservador observador) {
		observadores.add(observador);
	}
	private void notificarObservadores(CampoEvento evento) {
		observadores.stream().forEach(o -> o.eventoOcorreu(this, evento));
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
	public void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
			if(marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			}else {
				notificarObservadores(CampoEvento.DESMARCAR);				
			}
		}
	}
	public boolean abrir() {
		if (!aberto && !marcado) {
			if (minado) {
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}
			setAberto(true);
			notificarObservadores(CampoEvento.ABRIR);
			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
			return false;
		}
	}
	public boolean vizinhancaSegura() {
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
		if(aberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
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
	public int minasNaVizinhanca() {
		return (int) vizinhos.stream().filter(v -> v.minado).count();
	}
	void reiniciar() {
		minado = false;
		marcado = false;
		aberto = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}
}