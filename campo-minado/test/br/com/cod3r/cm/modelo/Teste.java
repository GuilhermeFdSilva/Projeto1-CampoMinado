package br.com.cod3r.cm.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Teste {
	private Campo campo = new Campo(3, 3);
	
	@Test
	void testeVizinho1() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinho2() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinho3() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinho4() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinho5() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinho6() {
		Campo vizinho = new Campo(4, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinho7() {
		Campo vizinho = new Campo(4, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinho8() {
		Campo vizinho = new Campo(2, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(8, 7);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	@Test
	void testeValorMarcacao() {
		assertFalse(campo.isMarcado());
	}
	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	@Test
	void testeAlternarMarcacao2() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	@Test
	void abrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	@Test
	void abrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	@Test
	void abrirMinadoMarcado() {
		campo.minar();
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	@Test
	void abrirMinadoNaoMarcado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
	}
	@Test
	void testeAbrirComVizinhos1() {
		Campo vizinho1 = new Campo(2, 2);		
		Campo vizinhoDoVizinho1 = new Campo(1, 1);
		vizinho1.adicionarVizinho(vizinhoDoVizinho1);
		campo.adicionarVizinho(vizinho1);
		campo.abrir();
		assertTrue(vizinho1.isAberto() && vizinhoDoVizinho1.isAberto());
	}
	@Test
	void testeAbrirComVizinhos2() {
		Campo vizinho1 = new Campo(2, 2);		
		Campo vizinhoDoVizinho1 = new Campo(1, 1);
		vizinhoDoVizinho1.minar();
		vizinho1.adicionarVizinho(vizinhoDoVizinho1);
		campo.adicionarVizinho(vizinho1);
		campo.abrir();
		assertTrue(vizinho1.isAberto() && !vizinhoDoVizinho1.isAberto());
	}
	@Test
	void testeReiniciar() {
		campo.reiniciar();
		assertFalse(campo.isAberto() && campo.isMarcado() && campo.isMinado());
	}
	@Test
	void testeObjetivoAlcancado1(){
		campo.abrir();
		assertTrue(campo.objetivoAlcancado());
	}
	@Test
	void testeObjetivoAlcancado2() {
		campo.minar();
		campo.alternarMarcacao();
		assertTrue(campo.objetivoAlcancado());
	}
	@Test
	void testeObjetivoAlcancado3() {
		campo.minar();
		assertFalse(campo.objetivoAlcancado());
	}
	@Test
	void testeMinasNaVizinhanca1() {
		Campo vizinho1 = new Campo(2, 2);
		vizinho1.minar();
		campo.adicionarVizinho(vizinho1);
		assertEquals(1, campo.minasNaVizinhanca());
	}
	@Test
	void testeMinasNaVizinhanca2() {
		Campo vizinho1 = new Campo(2, 2);
		vizinho1.minar();
		Campo vizinho2 = new Campo(2, 3);
		vizinho2.minar();
		campo.adicionarVizinho(vizinho1);
		campo.adicionarVizinho(vizinho2);
		assertEquals(2, campo.minasNaVizinhanca());
	}
	@Test
	void testeToString1() {
		assertEquals('?', campo.toString().charAt(0));
	}
	@Test
	void testeToString2() {
		campo.alternarMarcacao();
		assertEquals('x', campo.toString().charAt(0));
	}
	@Test
	void testeToString4() {
		campo.abrir();
		assertEquals(' ', campo.toString().charAt(0));
	}
	@Test
	void testeToString5() {
		Campo vizinho1 = new Campo(2, 2);
		vizinho1.minar();
		campo.adicionarVizinho(vizinho1);
		campo.abrir();
		assertEquals('1', campo.toString().charAt(0));
	}
}
