package Modelo;

import java.io.Serializable;
import auxiliar.Posicao;
public class Door extends Personagem implements Serializable {


    public Door(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.setbTransponivel(false);
        this.isDoor = true;

    }



    public void autoDesenho(){
        super.autoDesenho();
    }

}
