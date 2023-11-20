package Modelo;

import java.io.Serializable;

public class Pig extends Personagem implements Serializable {




    public Pig(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.setbTransponivel(true);
    }
    public void autoDesenho(){
        super.autoDesenho();
    }
    public void voltaAUltimaPosicao(){
        this.pPosicao.volta();
    }





}
