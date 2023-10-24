package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.ControleDeJogo;
import Controler.Tela;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Hero extends Personagem implements Serializable{
    public Hero(String sNomeImagePNG) {
        super(sNomeImagePNG);
    }

    public void voltaAUltimaPosicao(){
        this.pPosicao.volta();
    }

    public int qttKeys = 0;
    private int direcao = -1; // -1 significa que o herói não se moveu ainda

    public boolean setPosicao(int linha, int coluna){
        if(this.pPosicao.setPosicao(linha, coluna)){
            if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
                this.voltaAUltimaPosicao();
            }
            return true;
        }
        return false;       
    }

    /*TO-DO: este metodo pode ser interessante a todos os personagens que se movem*/
    private boolean validaPosicao(){
        if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
            this.voltaAUltimaPosicao();
            return false;
        }
        return true;       
    }
    public int getDirecao() {
        return direcao;
    }


    public boolean moveUp() {
        if(super.moveUp()) {
            direcao = 1;
            return validaPosicao();
        }
        return false;
    }

    public boolean moveDown() {
        if(super.moveDown()) {
            direcao = 2;
            return validaPosicao();
        }
        return false;
    }

    public boolean moveRight() {
        if(super.moveRight()) {
            direcao = 3;
            return validaPosicao();
        }
        return false;
    }

    public boolean moveLeft() {
        if(super.moveLeft()) {
            direcao = 4;
            return validaPosicao();
        }
        return false;
    }    
    
}
