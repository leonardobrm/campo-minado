package view;

import excecoes.ExplosaoException;
import excecoes.SairException;
import model.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {
    private Tabuleiro tabuleiro;
    private Scanner sc = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;

        executarGame();
    }

    private void executarGame() {
        try {
            boolean continuar = true;
            while (continuar){

                cicloGame();

                System.out.println("Outra partida? (S/n)");
                String resp = sc.nextLine();
                if("n".equalsIgnoreCase(resp)){
                    continuar = false;
                }else {
                    tabuleiro.reiniciar();
                }
            }
        }catch (SairException e){
            System.out.println("Tchau!!");
        }finally {
            sc.close();
        }
    }

    private void cicloGame() {
        try {

            while (!tabuleiro.objetivoAlcancado()){
                System.out.println(tabuleiro.toString());

                String digitado = capturarValorDigitado("Digite (x, y): ");
                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                digitado = capturarValorDigitado("1 - Abrir ou 2(Des)Marcar: ");
                if("1".equals(digitado)){
                    tabuleiro.abrir(xy.next(), xy.next());
                }else if("2".equals(digitado)){
                    tabuleiro.alternarMarcacao(xy.next(), xy.next());
                }
            }

            System.out.println(tabuleiro);
            System.out.println("Voce ganhou!!!");
        }catch (ExplosaoException e){
            System.out.println(tabuleiro);
            System.out.println("Voce perdeu!");
        }
    }

    private String capturarValorDigitado(String texto){
        System.out.print(texto);
        String digitado = sc.nextLine();

        if("sair".equalsIgnoreCase(digitado)){
            throw new SairException();
        }

        return digitado;
    }
}
