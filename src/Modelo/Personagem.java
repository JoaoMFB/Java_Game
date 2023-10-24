package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;

public abstract class Personagem implements Serializable {

    protected ImageIcon iImage;
    protected Posicao pPosicao;
    protected boolean bTransponivel; /*Pode passar por cima?*/
    protected boolean bMortal;       /*Se encostar, morre?*/
    protected boolean isBox;
    protected boolean isMonster;

    protected boolean isKey;
    protected boolean isLife;

    protected boolean isDoor;

    protected boolean isDiamond;



    protected Personagem(String sNomeImagePNG) {
        this.pPosicao = new Posicao(1, 1);
        this.bTransponivel = true;
        this.bMortal = false;
        this.isBox = false;
        this.isKey = false;
        this.isDoor = false;
        this.isMonster = false;
        this.isLife = false;
        this.isDiamond = false;

        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Posicao getPosicao() {
        /*TODO: Retirar este método para que objetos externos nao possam operar
         diretamente sobre a posição do Personagem*/
        return pPosicao;
    }

    public boolean isbTransponivel() {
        return bTransponivel;
    }

    public void changeImg(String sNomeImagePNG){
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setbTransponivel(boolean bTransponivel) {
        this.bTransponivel = bTransponivel;
    }


    public boolean isbKey(){
        return isKey;
    }
    public boolean isbLife(){
        return isLife;
    }

    public boolean isbBox(){
        return isBox;
    }

    public boolean isbDoor() { return isDoor; }

    public boolean isbMonster(){return isMonster;}

    public boolean isbDiamond(){return isDiamond;}

    public void moveBox(int direcao) {
        Posicao novaPosicao = this.getPosicao();
        int novaLinha = novaPosicao.getLinha();
        int novaColuna = novaPosicao.getColuna();

        // Dependendo da direção em que o herói estava indo, atualize a nova posição da caixa
        if (direcao == 1) { // Herói estava indo para cima
            novaPosicao.moveUp();
        } else if (direcao == 2) { // Herói estava indo para baixo
            novaPosicao.moveDown();
        } else if (direcao == 3) { // Herói estava indo para a direita
            novaPosicao.moveRight();
        } else if (direcao == 4) { // Herói estava indo para a esquerda
            novaPosicao.moveLeft();
        }

        // Verifique se a nova posição da caixa é válida e atualize-a
        if (Desenho.acessoATelaDoJogo().ehPosicaoValida(novaPosicao)) {
            this.setPosicao(novaPosicao.getLinha(), novaPosicao.getColuna());
        }
    }

    public void openDoor(int qttKeys){
        if(qttKeys >= 1){
            this.setbTransponivel(true);
        }
    }

    public void autoDesenho(){
        Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());        
    }

    public boolean setPosicao(int linha, int coluna) {
        return pPosicao.setPosicao(linha, coluna);
    }

    public boolean moveUp() {
        return this.pPosicao.moveUp();
    }

    public boolean moveDown() {
        return this.pPosicao.moveDown();
    }

    public boolean moveRight() {
        return this.pPosicao.moveRight();
    }

    public boolean moveLeft() {
        return this.pPosicao.moveLeft();
    }


}
