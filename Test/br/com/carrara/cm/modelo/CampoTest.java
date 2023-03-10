package br.com.carrara.cm.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.carrara.cm.excecao.ExplosaoException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CampoTest {
    private Campo campo;
    @BeforeEach
    void initCampo(){
        campo = new Campo(3,3);
    }

    @Test
    void testVizinhoCima(){
        Campo vizinho = new Campo(3,4);
        boolean result = campo.addVizinho(vizinho);
        assertTrue(result);
    }
    @Test
    void testVizinhoBaixo(){
        Campo vizinho = new Campo(3,2);
        boolean result = campo.addVizinho(vizinho);
        assertTrue(result);
    }
    @Test
    void testVizinhoEsquerda(){
        Campo vizinho = new Campo(2,3);
        boolean result = campo.addVizinho(vizinho);
        assertTrue(result);
    }
    @Test
    void testVizinhoDireita(){
        Campo vizinho = new Campo(4,3);
        boolean result = campo.addVizinho(vizinho);
        assertTrue(result);
    }

    @Test
    void testVizinhoDiagonal(){
        Campo vizinho = new Campo(2,2);
        boolean result = campo.addVizinho(vizinho);
        assertTrue(result);
    }

    @Test
    void testNaoVizinho(){
        Campo vizinho = new Campo(5,5);
        boolean result = campo.addVizinho(vizinho);
        assertFalse(result);
    }
    @Test
    void testMarcacao() {
    	assertFalse(campo.isMarcado());
    }
    @Test
    void testAlternMarcacao() {
    	campo.altMarca();
    	assertTrue(campo.isMarcado());
    }
    @Test
    void testDoubleAlternMarcacao() {
    	campo.altMarca();
    	campo.altMarca();
    	assertFalse(campo.isMarcado());
    }
    
    @Test
    void testAbrirCampo() {
    	assertTrue(campo.abrir());
    }
    
    @Test
    void testAbrirCampoMinado() {
    	campo.altMarca();
    	assertFalse(campo.abrir());
    }
    
    @Test 
    void testAbrirCampoMinAndMarca(){
    	campo.altMarca();
    	campo.minar();
    	assertFalse(campo.abrir());
    }
    @Test
    void testAbrirMinado() {
    	campo.minar();
    	assertThrows(ExplosaoException.class, () -> campo.abrir());
    	}
    @Test
    void testAbrirComVizinhos() {
    	Campo  campo1 = new Campo(1,1);
    	Campo  campo2 = new Campo(2,2);   	
    	campo2.addVizinho(campo1);
    	
    	campo.addVizinho(campo2);
    	campo.abrir();
    	
    	assertTrue(campo2.isAberto() && campo1.isAberto());
    }
    @Test
    void testAbrirComVizinhos2() {
    	Campo  campo1 = new Campo(1,1);
    	Campo  campo12 = new Campo(1,2);
    	campo12.minar();

    	
    	Campo  campo2 = new Campo(2,2);   	
    	campo2.addVizinho(campo1);
    	campo2.addVizinho(campo12);
    	
    	campo.addVizinho(campo2);
    	campo.abrir();
    	
    	assertTrue(campo2.isAberto() && !campo1.isAberto());
    }
}
