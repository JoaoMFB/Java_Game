package Modelo;

import java.io.Serializable;
import auxiliar.Posicao;
public class Box extends Personagem implements Serializable {


    public Box(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.isBox = true;
        this.setbTransponivel(false);
    }
    public void autoDesenho(){
        super.autoDesenho();
    }

    public void moveBox(Posicao pposicao){
        if (pposicao.moveUp()){
            this.pPosicao.moveUp();
        }
        else if (pposicao.moveDown()){
            this.pPosicao.moveDown();
        }
        else if (pposicao.moveRight()){
            this.pPosicao.moveRight();
        }
        else if (pposicao.moveLeft()){
            this.pPosicao.moveLeft();
        }
    }



}
