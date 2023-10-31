package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;
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


   private String img;
    public Hero(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.isHero = true;
        this.img = "sNomeImagePNG";
        this.whatIsIt = "Hero";
    }

    public void voltaAUltimaPosicao(){
        this.pPosicao.volta();
    }
    private boolean arco = false;
    private int qttKeys = 0;
    private int qttLifes = 0;
    private int direcao = -1; // -1 significa que o herói não se moveu ainda
    private int attackDirection = 0;

    private boolean hasSword = false;

    private boolean isAnimating = false;
    private char lastDirection; // 'U' para cima, 'D' para baixo, 'L' para esquerda, 'R' para direita


    public boolean setPosicao(int linha, int coluna){
        if(this.pPosicao.setPosicao(linha, coluna)){
            if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
                this.voltaAUltimaPosicao();
            }
            return true;
        }
        return false;
    }

    public void copyAttributes(Hero other) {
        // Copie os atributos do 'other' para a instância atual
        this.img = other.img;
        this.isHero = other.isHero;
        this.whatIsIt = other.whatIsIt;
        Posicao pOther = other.getPosicao();
        int linha = pOther.getLinha();
        int coluna = pOther.getColuna();
        this.pPosicao = new Posicao(linha, coluna);
        this.arco = other.arco;
        this.qttKeys = other.qttKeys;
        this.qttLifes = other.qttLifes;
        this.direcao = other.direcao;
        this.attackDirection = other.attackDirection;
        this.hasSword = other.hasSword;
        this.isAnimating = other.isAnimating;
        this.lastDirection = other.lastDirection;
    }

    public int getAttackDirection() {
        return attackDirection;
    }

    public void setAttackDirection(int attack) {
        this.attackDirection = attack;

    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public void setAnimating(boolean animating) {
        isAnimating = animating;
    }

    public boolean hasSword() {
        return hasSword;
    }

    public void setHasSword(boolean hasSword) {
        this.hasSword = hasSword;
    }

    public boolean getArco(){
        return arco;
    }
    
    public void setArco(boolean equipado){
        this.arco = equipado;
    }
    
    public int getQttKeys() {
        return qttKeys;
    }

    public void setQttKeys(int qttKeys) {
        this.qttKeys = qttKeys;
    }

    public int getQttLifes() {
        return qttLifes;
    }

    public void setQttLifes(int qttLifes) {
        this.qttLifes = qttLifes;
    }

    /*TO-DO: este metodo pode ser interessante a todos os personagens que se movem*/
    private boolean validaPosicao(){
        if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
            this.voltaAUltimaPosicao();
            return false;
        }
        return true;       
    }
    public void resetaAllHero(){
        this.qttKeys = 0;
        this.qttLifes = 0;
        this.hasSword = false;
        this.arco = false;
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
            return  validaPosicao();
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
    
    public void shootRight(){
        Arrow arrow = new Arrow("arrowright.png", 3);
        arrow.setPosicao(pPosicao.getLinha(),pPosicao.getColuna()+1);
        Desenho.acessoATelaDoJogo().addPersonagem(arrow);
    }
    public void shootLeft(){
        Arrow arrow = new Arrow("arrowleft.png", 4);
        arrow.setPosicao(pPosicao.getLinha(),pPosicao.getColuna()-1);
        Desenho.acessoATelaDoJogo().addPersonagem(arrow);
    }
    public void shootUp(){
        Arrow arrow = new Arrow("arrowup.png", 1);
        arrow.setPosicao(pPosicao.getLinha() - 1,pPosicao.getColuna());
        Desenho.acessoATelaDoJogo().addPersonagem(arrow);
    }
    public void shootDown(){
        Arrow arrow = new Arrow("arrowdown.png", 2);
        arrow.setPosicao(pPosicao.getLinha() + 1,pPosicao.getColuna());
        Desenho.acessoATelaDoJogo().addPersonagem(arrow);
    }
}
