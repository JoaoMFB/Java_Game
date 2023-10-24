package Modelo;

import java.io.Serializable;
import auxiliar.Posicao;
public class Box extends Personagem implements Serializable {




    public Box(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.isBox = true;
        this.setbTransponivel(true);
    }
    public void autoDesenho(){
        super.autoDesenho();
    }





}
