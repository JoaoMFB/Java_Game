package Modelo;

import java.io.Serializable;
import auxiliar.Posicao;
public class LifeCounter extends Personagem implements Serializable {

    public LifeCounter(String sNomeImagePNG) {
        super(sNomeImagePNG);
    }

    public void autoDesenho(){
        super.autoDesenho();
    }
}
