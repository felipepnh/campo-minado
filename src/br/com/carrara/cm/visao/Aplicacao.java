package br.com.carrara.cm.visao;

import br.com.carrara.cm.modelo.Tabuleiro;

public class Aplicacao {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10, 6);
        new TabuleiroTerminal(tabuleiro);
    }
}
