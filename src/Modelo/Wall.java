package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.Tela;
import java.awt.Graphics;
import java.io.Serializable;

public class Wall extends Personagem implements Serializable{
    private int iContaIntervalos;

    public Wall(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.setbTransponivel(false);
        this.iContaIntervalos = 0;
        this.whatIsIt = "Wall";
    }

    public void autoDesenho() {
        super.autoDesenho();

        this.iContaIntervalos++;
        if(this.iContaIntervalos == Consts.TIMER){
            this.iContaIntervalos = 0;
        }
    }
}
