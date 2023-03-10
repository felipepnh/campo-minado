package br.com.carrara.cm.modelo;

import br.com.carrara.cm.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private int minas;

    private List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas){
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        generCampos();
        linkVizinhos();
        awayMinas();
    }

    public void opemCampo(int linhas, int colunas){
        try {
            campos.parallelStream().filter(p -> p.getLinha() == linhas && p.getColuna() == colunas).findFirst().ifPresent(Campo::abrir);
        } catch (ExplosaoException e){
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void markCampo (int linhas, int colunas){
        campos.parallelStream().filter(c -> c.getColuna() == colunas && c.getLinha() == linhas).findFirst().ifPresent(Campo::altMarca);
    }

    private void generCampos(){
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                campos.add(new Campo(i,j));
            }
        }
    }
    private void linkVizinhos(){
        for (Campo a: campos){
            for (Campo b: campos){
                a.addVizinho(b);
            }
        }
    }
    private void awayMinas(){
        long mountMinas=0;

        do {
            int randomCampo = (int) (Math.random() * campos.size());
            campos.get(randomCampo).minar();
            mountMinas = campos.stream().filter(c -> c.isMinado()).count();
        }while (mountMinas < minas);
    }

    public boolean objetivoAlcacado(){
        return campos.stream().allMatch(c -> c.objetiAlcancado());
    }

    public void reinicar(){
        campos.stream().forEach(c -> c.reiniciar());
        awayMinas();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("   ");
        for (int x = 1; x <= colunas; x++) {
            sb.append(" ");
            sb.append(x);
            sb.append(" ");
        }
        sb.append("\n");
        int i = 0;
        for (int l = 1; l <= linhas; l++) {
            sb.append(l);
            sb.append("  ");
            for (int c = 1; c <= colunas; c++) {
                sb.append(" ");
                sb.append(campos.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
