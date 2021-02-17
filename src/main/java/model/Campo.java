package model;

import excecoes.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private  final int linha;
    private  final int coluna;

    private  boolean minado = false;
    private  boolean aberto = false;
    private  boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();

    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public boolean getMinado() {
        return minado;
    }

    public void setMinado(boolean minado) {
        this.minado = minado;
    }

    public boolean getAberto() {
        return aberto;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public boolean getMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public List<Campo> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(List<Campo> vizinhos) {
        this.vizinhos = vizinhos;
    }


    public boolean isFechado() {
        return !aberto;
    }

    public boolean adicionarVizinho(Campo vizinho){
        boolean linhaDiferente = getLinha() != vizinho.linha;
        boolean colunaDiferente = getColuna() != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(getLinha() - vizinho.linha);
        int deltaColuna = Math.abs(getColuna() - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if(deltaGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;
        }else if(deltaGeral == 2 && diagonal){
            vizinhos.add(vizinho);
            return true;
        }
        return false;
    }

    public void alternarMarcacao(){
        if(!aberto){
            setMarcado(!getMarcado());
        }
    }

    public boolean abrir(){
        if(!aberto && !marcado){
            setAberto(true);

            if(minado){
                throw new ExplosaoException();
            }

            if(vizinhancaSegura()){
                vizinhos.forEach(v -> v.abrir());
            }

            return true;
        }
        return false;
    }

    boolean vizinhancaSegura(){
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    public void minar(){
        setMinado(true);
    }

    public boolean objetivoAlcancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;

        return desvendado || protegido;
    }

    public long minasVizinhanca(){
        return vizinhos.stream().filter(v -> v.minado).count();
    }

    public void reiniciar(){
        setAberto(false);
        setMinado(false);
        setMarcado(false);
    }

    public String toString(){
        if(marcado){
            return "x";
        }else if(aberto && minado){
            return "*";
        }else if(aberto && minasVizinhanca() > 0){
            return Long.toString(minasVizinhanca());
        } else if(aberto){
            return " ";
        }else {
            return "?";
        }
    }
}
