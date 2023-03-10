package br.com.carrara.cm.visao;

import br.com.carrara.cm.excecao.ExplosaoException;
import br.com.carrara.cm.excecao.SairException;
import br.com.carrara.cm.modelo.Tabuleiro;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.Function;

public class TabuleiroTerminal {
    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public TabuleiroTerminal(Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro;

        executeJogo();
    }

    private void executeJogo() {
        try {
            boolean continuar = true;

            while (continuar){
                cicloDeJogo();

                System.out.println("Tentar novamente? (S/n): ");
                String resposta = entrada.nextLine();
                if ("n".equalsIgnoreCase(resposta)){
                    continuar = false;
                } else {
                    tabuleiro.reinicar();
                }
            }

        }catch (SairException e){
            System.out.println("Fim do Jogo!");
        } finally {
            entrada.close();
        }
    }

    private void cicloDeJogo() {
        try {

            while (!tabuleiro.objetivoAlcacado()){
                System.out.println(tabuleiro);

                String desejo = catValorDigitado("Digite 1 para Abrir - 2 para (Des)Marcar: ");

                String valueDigitado = catValorDigitado("Digite a Posição da Casa (Linha, Coluna): ");

                Iterator<Integer> lc = Arrays.stream(valueDigitado.split(",")).map(e -> Integer.parseInt(e.trim())).iterator();

                int linha = lc.next();
                int coluna = lc.next();

                if (desejo.equalsIgnoreCase("1")){
                    tabuleiro.opemCampo(linha, coluna);
                } else if (desejo.equalsIgnoreCase("2")){
                    tabuleiro.markCampo(linha, coluna);
                }

            }

            System.out.println(tabuleiro);
            String win = "you win!!!";
            System.out.println(win.toUpperCase());
        }catch (ExplosaoException e){
            String fail = "you have failed!!!";
            System.out.println(tabuleiro);
            System.out.println(fail.toUpperCase());
        }
    }

    private String catValorDigitado(String s) {
        System.out.print(s);
        String digitado = entrada.nextLine();

        if ("sair".equalsIgnoreCase(digitado)){
            throw new SairException();
        }
        return digitado;
    }


}
