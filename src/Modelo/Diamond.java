package Modelo;

import java.io.Serializable;

public class Diamond extends Personagem implements Serializable {


    public Diamond(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.isDiamond = true;
        this.whatIsIt = "Diamond";
    }
    public void autoDesenho(){
        super.autoDesenho();
    }


}
