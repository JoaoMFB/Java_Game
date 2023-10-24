package Modelo;

import java.io.Serializable;

public class Life extends Personagem implements Serializable {


    public Life(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.isLife = true;
    }
    public void autoDesenho(){
        super.autoDesenho();
    }


}
