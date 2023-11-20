package Controler;

import Modelo.*;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.Save;
import auxiliar.Posicao;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener {
    private Hero hero;
    private LifeCounter lifeCounter;
    private Diamond diamond;
    private Ghast ghast;
    private Arco arco;
    private Espada espada;
    private ArrayList<Personagem> faseAtual;
    private ControleDeJogo cj = new ControleDeJogo();
    private Graphics g2;
    private int faseAt;

    public Tela() {
        Desenho.setCenario(this);
        initComponents();
        this.addMouseListener(this); /*mouse*/

        this.addKeyListener(this);   /*teclado*/
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);


        faseAtual = new ArrayList<Personagem>();
        this.faseAt = 0;


        /*Cria faseAtual adiciona personagens*/
        System.out.format("----INSTRUCOES BASICAS---- \n");
        System.out.format("-->SUA QUANTIDADE MÁXIMA DE VIDAS É 3. ENQUANTO VOCÊ POSSUIR VIDAS VOCÊ VOLTARÁ AO PONTO INICIAL DA FASE, SEU PROGRESSO É MANTIDO \n");
        System.out.format("-->CASO VOCE MORRA E NAO TENHA MAIS VIDAS OU QUEIRA RECOMMECAR VOCE DEVE APERTAR 'R' PARA RETORNAR A 1a FASE \n");
        System.out.format("-->QUANDO COLETAR O ITEM FINAL, A PROXIMA FASE EH LIBERADA, BASTA APERTAR 'P' PARA ACESSA-LA\n\n");
        System.out.format("----INSTRUCOES DE ATAQUE---- \n");
        System.out.format("-->AO ENCOSTAR EM UM MONSTRO, VOCÊ PERDE UMA VIDA, EH NECESSARIO COLETAR UMA ARMA PARA PODER ATACA-LOS\n");
        System.out.format("--->PARA USAR A ESPADA, BASTA CHEGAR PERTO DE UM MONSTRO E APERTAR ESPACO\n");
        System.out.format("--->PARA USAR O ARCO, UTILIZE AS TECLAS W-A-S-D PARA ESCOLHER A DIRECAO DE ATAQUE\n");

        hero = new Hero("downSteve.png");
        if(!load("savegame.ser")){
            setFase(hero);
        }

    }
    public boolean save(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            Save salvarJogo = new Save(faseAtual, faseAt);
            out.writeObject(salvarJogo);
            System.out.format("Jogo salvo com sucesso na fase: %d", faseAt);
        } catch (FileNotFoundException ex) {
            logAndPrintError("Erro ao salvar o jogo: arquivo não encontrado", ex);
            return false;
        } catch (IOException ex) {
            logAndPrintError("Erro ao salvar o jogo", ex);
            return false;
        }
        return true;
    }

    public boolean load(String filename) {
        File saveGameFile = new File(filename);

        if (saveGameFile.exists()) {
            try (FileInputStream fileIn = new FileInputStream(saveGameFile);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {

                Save jogoSalvo = (Save) in.readObject();
                this.faseAtual = jogoSalvo.getPersonagens();
                this.faseAt = jogoSalvo.getContador();
                System.out.format("Jogo carregado: Você está na fase %d%n", this.faseAt);

            } catch (IOException | ClassNotFoundException ex) {
                logAndPrintError("Erro ao carregar o jogo", ex);
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private void logAndPrintError(String message, Exception ex) {
        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, message, ex);
        System.out.println("Erro: " + message);
    }


    public void setFase(Hero hero){
            if(this.faseAt == 0){
                setAllChar(hero);
            }
            if(this.faseAt == 1){
                setAllChar2(hero);
            }
            if(this.faseAt == 2){
                setAllChar3(hero);
            }
            if(this.faseAt == 3){
                setAllChar4(hero);
            }
    }
    public void setAllChar(Hero hero){
        int[][] matriz = {
                {0,     0,      -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -2},
                {-1,    0,      0,      4,      0,      11,     1,      14,      1,      8,     0,      0,      -1},
                {-1,    0,      0,      1,      0,      0,      1,      0,      1,       0,      1,      0,      -1},
                {-1,    0,      0,      1,      0,      0,      1,      0,      1,       0,      1,      0,      -1},
                {-1,    0,      0,      1,      0,      0,      1,      0,      1,       0,      1,      0,      -1},
                {-1,    0,      0,      1,      0,      0,      1,      4,      1,       0,      1,      0,      -1},
                {-1,    0,      5,      1,      0,      0,      0,      0,      0,       0,      1,      15,      -1},
                {-1,    0,      1,      1,      1,      1,      1,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      0,      0,      0,      0,      1,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      0,      12,     0,      0,      1,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      0,      0,      0,      0,      1,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      0,      0,      0,      2,      1,      0,      0,       2,      1,      6,      -1},
                {-1,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1}
        };

        Wall Par[] = new Wall[121]; //index 1
        Wall Stone[] = new Wall[121]; //index -1
        Key Key[] = new Key[12]; //index 2
        Pig Pig[] = new Pig[12]; //index 3
        Door Door[] = new Door[12]; //index 4
        Monster Monster[] = new Monster[12]; //index 5
        Life Life[] = new Life[12]; //index 6
        Skeleton Skeleton[] = new Skeleton[12]; //index 11, 12, 13, 14 ()
        Zombie Zombie[] = new Zombie[12]; //index 15, 16, 17 (vertical, horizontal, aleatorio)

        hero.setPosicao(0, 0);
        this.addPersonagem(hero);
        //Heroi --> index 9, diamond --> index 7
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 13; j++){

                if(matriz[i][j] == -2){
                    lifeCounter = new LifeCounter("stone.png");
                    lifeCounter.setPosicao(i, j);
                    this.addPersonagem(lifeCounter);
                }
                if(matriz[i][j] == -1){
                    Stone[i] = new Wall("stone.png");
                    Stone[i].setPosicao(i,j);
                    this.addPersonagem(Stone[i]);
                }
                if(matriz[i][j] == 1){
                    Par[i] = new Wall("bricks.png");
                    Par[i].setPosicao(i,j);
                    this.addPersonagem(Par[i]);
                }
                if(matriz[i][j] == 2){
                    Key[i] = new Key("axe.png");
                    Key[i].setPosicao(i, j);
                    this.addPersonagem(Key[i]);
                }
                if(matriz[i][j] == 3){
                    Pig[i] = new Pig("pig.png");
                    Pig[i].setPosicao(i, j);
                    this.addPersonagem(Pig[i]);
                }
                if(matriz[i][j] == 4){
                    Door[i] = new Door("door.png");
                    Door[i].setPosicao(i, j);
                    this.addPersonagem(Door[i]);
                }

                if(matriz[i][j] == 5){
                    Life[i] = new Life("coracao.png");
                    Life[i].setPosicao(i, j);
                    this.addPersonagem(Life[i]);
                }
                if(matriz[i][j] == 6){
                    diamond = new Diamond("Diamond.png");
                    diamond.setPosicao(i, j);
                    this.addPersonagem(diamond);
                }

                if(matriz[i][j] == 7){
                    Skeleton[i] = new Skeleton("skeleton.png", 1);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 8){
                    Skeleton[i] = new Skeleton("skeleton.png", 2);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 9){
                    Skeleton[i] = new Skeleton("skeleton.png", 3);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 10){
                    Skeleton[i] = new Skeleton("skeleton.png", 4);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 11){
                    Zombie[i] = new Zombie("zombie.png", 'v');
                    Zombie[i].setPosicao(i, j);
                    this.addPersonagem(Zombie[i]);
                }
                if(matriz[i][j] == 12){
                    Zombie[i] = new Zombie("zombie.png", 'h');
                    Zombie[i].setPosicao(i, j);
                    this.addPersonagem(Zombie[i]);
                }
                if(matriz[i][j] == 13){
                    Zombie[i] = new Zombie("zombie.png", 'a');
                    Zombie[i].setPosicao(i, j);
                    this.addPersonagem(Zombie[i]);
                }
                if(matriz[i][j] == 14){
                    arco = new Arco("arco.png");
                    arco.setPosicao(i, j);
                    this.addPersonagem(arco);
                }
                if(matriz[i][j] == 15){
                    espada = new Espada("sword.png");
                    espada.setPosicao(i, j);
                    this.addPersonagem(espada);
                }
            }
        }

    }

    public void setAllChar2(Hero hero){
        int[][] matriz = {
                {0,     0,      -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -2},
                {-1,    0,      0,      0,      1,      8,      1,       0,      0,       0,     0,      0,      -1},
                {-1,    0,      1,      0,      1,      0,      1,      1,      4,       1,      1,      0,      -1},
                {-1,    0,      1,      0,      1,      0,      1,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      1,      0,      1,      0,      1,      0,      12,       0,      1,     0,      -1},
                {-1,    0,      1,      0,      1,      0,      1,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      0,      0,      0,      0,      1,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      1,      0,      0,      0,      1,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      1,      0,      1,      0,      1,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      1,      0,      1,      0,      1,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      0,      3,      1,      0,      0,      0,      2,       0,      1,      0,      -1},
                {-1,    0,      0,      0,      1,      1,      1,      0,      0,       0,      1,      6,      -1},
                {-1,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1}
        };

        Wall Par[] = new Wall[121]; //index 1
        Wall Stone[] = new Wall[121]; //index -1
        Key Key[] = new Key[12]; //index 2
        Pig Pig[] = new Pig[12]; //index 3
        Door Door[] = new Door[12]; //index 4
        Monster Monster[] = new Monster[12]; //index 5
        Life Life[] = new Life[12]; //index 6
        Skeleton Skeleton[] = new Skeleton[12]; //index 7, 8, ,9 ,10  ()
        Zombie Zombie[] = new Zombie[12]; //index 15, 16, 17 (vertical, horizontal, aleatorio)

        hero.setPosicao(0, 0);
        this.addPersonagem(hero);
        //Heroi --> index 9, diamond --> index 7
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 13; j++){

                if(matriz[i][j] == -2){
                    lifeCounter = new LifeCounter("stone.png");
                    lifeCounter.setPosicao(i, j);
                    this.addPersonagem(lifeCounter);
                }
                if(matriz[i][j] == -1){
                    Stone[i] = new Wall("stone.png");
                    Stone[i].setPosicao(i,j);
                    this.addPersonagem(Stone[i]);
                }
                if(matriz[i][j] == 1){
                    Par[i] = new Wall("bricks.png");
                    Par[i].setPosicao(i,j);
                    this.addPersonagem(Par[i]);
                }
                if(matriz[i][j] == 2){
                    Key[i] = new Key("axe.png");
                    Key[i].setPosicao(i, j);
                    this.addPersonagem(Key[i]);
                }
                if(matriz[i][j] == 3){
                    Pig[i] = new Pig("pig.png");
                    Pig[i].setPosicao(i, j);
                    this.addPersonagem(Pig[i]);
                }
                if(matriz[i][j] == 4){
                    Door[i] = new Door("door.png");
                    Door[i].setPosicao(i, j);
                    this.addPersonagem(Door[i]);
                }

                if(matriz[i][j] == 5){
                    Life[i] = new Life("coracao.png");
                    Life[i].setPosicao(i, j);
                    this.addPersonagem(Life[i]);
                }
                if(matriz[i][j] == 6){
                    diamond = new Diamond("Diamond.png");
                    diamond.setPosicao(i, j);
                    this.addPersonagem(diamond);
                }

                if(matriz[i][j] == 7){
                    Skeleton[i] = new Skeleton("skeleton.png", 1);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 8){
                    Skeleton[i] = new Skeleton("skeleton.png", 2);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 9){
                    Skeleton[i] = new Skeleton("skeleton.png", 3);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 10){
                    Skeleton[i] = new Skeleton("skeleton.png", 4);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 11){
                    Zombie[i] = new Zombie("zombie.png", 'v');
                    Zombie[i].setPosicao(i, j);
                    this.addPersonagem(Zombie[i]);
                }
                if(matriz[i][j] == 12){
                    Zombie[i] = new Zombie("zombie.png", 'h');
                    Zombie[i].setPosicao(i, j);
                    this.addPersonagem(Zombie[i]);
                }
                if(matriz[i][j] == 13){
                    Zombie[i] = new Zombie("zombie.png", 'a');
                    Zombie[i].setPosicao(i, j);
                    this.addPersonagem(Zombie[i]);
                }
                if(matriz[i][j] == 14){
                    arco = new Arco("arco.png");
                    arco.setPosicao(i, j);
                    this.addPersonagem(arco);
                }
                if(matriz[i][j] == 15){
                    espada = new Espada("sword.png");
                    espada.setPosicao(i, j);
                    this.addPersonagem(espada);
                }
            }
        }

    }
    public void setAllChar3(Hero hero){
        int[][] matriz = {
                {0,     0,      -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -2},
                {-1,    0,      0,      0,      0,      0,      0,      0,      0,       0,      0,      0,      -1},
                {-1,    0,      0,      0,      0,      0,      0,      0,      0,       0,      0,      0,      -1},
                {-1,    0,      0,      0,      0,      3,      0,      0,      0,       0,      5,      0,      -1},
                {-1,    0,      0,      9,      0,      0,      0,      8,      0,       0,      0,      0,      -1},
                {-1,    0,      0,      0,      0,      0,      0,      0,      0,       0,      0,      0,      -1},
                {-1,    0,      0,      0,      0,      2,      0,      0,      0,       0,      0,      0,      -1},
                {-1,    0,      0,      0,      0,      0,      0,      0,      0,       0,      0,      0,      -1},
                {-1,    0,      0,      7,      0,      0,      0,      10,      0,       0,      1,      4,      -1},
                {-1,    0,      0,      0,      0,      0,      0,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      0,      0,      0,      0,      0,      0,      0,       0,      1,      0,      -1},
                {-1,    0,      0,      0,      0,      0,      0,      0,      0,       0,      1,      6,      -1},
                {-1,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1}
        };

        Wall Par[] = new Wall[121]; //index 1
        Wall Stone[] = new Wall[121]; //index -1
        Key Key[] = new Key[12]; //index 2
        Pig Pig[] = new Pig[12]; //index 3
        Door Door[] = new Door[12]; //index 4
        Monster Monster[] = new Monster[12]; //index 5
        Life Life[] = new Life[12]; //index 6
        Skeleton Skeleton[] = new Skeleton[12]; //index 11, 12, 13, 14 ()
        Zombie Zombie[] = new Zombie[12]; //index 15, 16, 17 (vertical, horizontal, aleatorio)

        hero.setPosicao(0, 0);
        this.addPersonagem(hero);
        //Heroi --> index 9, diamond --> index 7
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 13; j++){

                if(matriz[i][j] == -2){
                    lifeCounter = new LifeCounter("stone.png");
                    lifeCounter.setPosicao(i, j);
                    this.addPersonagem(lifeCounter);
                }
                if(matriz[i][j] == -1){
                    Stone[i] = new Wall("stone.png");
                    Stone[i].setPosicao(i,j);
                    this.addPersonagem(Stone[i]);
                }
                if(matriz[i][j] == 1){
                    Par[i] = new Wall("bricks.png");
                    Par[i].setPosicao(i,j);
                    this.addPersonagem(Par[i]);
                }
                if(matriz[i][j] == 2){
                    Key[i] = new Key("axe.png");
                    Key[i].setPosicao(i, j);
                    this.addPersonagem(Key[i]);
                }
                if(matriz[i][j] == 3){
                    Pig[i] = new Pig("pig.png");
                    Pig[i].setPosicao(i, j);
                    this.addPersonagem(Pig[i]);
                }
                if(matriz[i][j] == 4){
                    Door[i] = new Door("door.png");
                    Door[i].setPosicao(i, j);
                    this.addPersonagem(Door[i]);
                }

                if(matriz[i][j] == 5){
                    Life[i] = new Life("coracao.png");
                    Life[i].setPosicao(i, j);
                    this.addPersonagem(Life[i]);
                }
                if(matriz[i][j] == 6){
                    diamond = new Diamond("Diamond.png");
                    diamond.setPosicao(i, j);
                    this.addPersonagem(diamond);
                }

                if(matriz[i][j] == 7){
                    Skeleton[i] = new Skeleton("skeleton.png", 1);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 8){
                    Skeleton[i] = new Skeleton("skeleton.png", 2);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 9){
                    Skeleton[i] = new Skeleton("skeleton.png", 3);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 10){
                    Skeleton[i] = new Skeleton("skeleton.png", 4);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 11){
                    Zombie[i] = new Zombie("zombie.png", 'v');
                    Zombie[i].setPosicao(i, j);
                    this.addPersonagem(Zombie[i]);
                }
                if(matriz[i][j] == 12){
                    Zombie[i] = new Zombie("zombie.png", 'h');
                    Zombie[i].setPosicao(i, j);
                    this.addPersonagem(Zombie[i]);
                }
                if(matriz[i][j] == 13){
                    Zombie[i] = new Zombie("zombie.png", 'a');
                    Zombie[i].setPosicao(i, j);
                    this.addPersonagem(Zombie[i]);
                }
                if(matriz[i][j] == 14){
                    arco = new Arco("arco.png");
                    arco.setPosicao(i, j);
                    this.addPersonagem(arco);
                }
                if(matriz[i][j] == 15){
                    espada = new Espada("sword.png");
                    espada.setPosicao(i, j);
                    this.addPersonagem(espada);
                }
            }
        }

    }
    public void setAllChar4(Hero hero){
        int[][] matriz = {
                { 0,  9, -1, -1, -1, -1, -1, -1, -1, -1, -1, 66, -2},
                {-1,  0,  1,  1,  1, 12,  1,  0,  0,  0,  1,  0, -1},
                {-1,  0,  0,  1,  1,  0, 33,  0,  0,  0,  1,  0, -1},
                {-1,  1,  0,  1,  1,  0,  0,  0,  0,  0,  1,  0, -1},
                {-1,  0,  0,  1,  0,  0,  0,  1,  0,  0,  1,  0, -1},
                {-1,  0,  1,  1,  0,  0,  1,  1,  0,  0,  1,  0, -1},
                {-1,  0,  0,  1,  0,  0,  1,  1,  0,  0,  1,  0, -1},
                {-1,  1,  6,  1,  0,  0,  0,  1,  0,  0,  1,  0, -1},
                {-1,  0,  0,  1,  1,  0,  0,  1,  0,  0,  1,  0, -1},
                {-1,  0,  1,  0, 15,  0,  0,  1,  0,  0,  1,  0, -1},
                {-1,  0,  0,  0,  0,  0,  0,  1,  0,  0,  1,  0, -1},
                {-1,  0,  0,  0,  0,  0,  0,  1,  0,  6,  1,  0, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
        };

        Wall Par[] = new Wall[121]; //index 1
        Key Key[] = new Key[12]; //index 2
        Pig Pig[] = new Pig[12]; //index 3
        Door Door[] = new Door[12]; //index 4
        Monster Monster[] = new Monster[12]; //index 5
        Life Life[] = new Life[12]; //index 6
        Wall Stone[] = new Wall[121]; //index 7
        Zombie Zombie[] = new Zombie[12];
        Skeleton Skeleton[] = new Skeleton[12];
        hero.setPosicao(0, 0);
        this.addPersonagem(hero);
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 13; j++){

                if(matriz[i][j] == -1){
                    Stone[i] = new Wall("stone.png");
                    Stone[i].setPosicao(i,j);
                    this.addPersonagem(Stone[i]);
                }
                if(matriz[i][j] == -2){
                    lifeCounter = new LifeCounter("stone.png");
                    lifeCounter.setPosicao(i, j);
                    this.addPersonagem(lifeCounter);
                }
                if(matriz[i][j] == 1){
                    Par[i] = new Wall("bricks.png");
                    Par[i].setPosicao(i,j);
                    this.addPersonagem(Par[i]);
                }
                if(matriz[i][j] == 2){
                    Key[i] = new Key("axe.png");
                    Key[i].setPosicao(i, j);
                    this.addPersonagem(Key[i]);
                }
                if(matriz[i][j] == 3){
                    Pig[i] = new Pig("coracao.png");
                    Pig[i].setPosicao(i, j);
                    this.addPersonagem(Pig[i]);
                }
                if(matriz[i][j] == 4){
                    Door[i] = new Door("door.png");
                    Door[i].setPosicao(i, j);
                    this.addPersonagem(Door[i]);
                }
                if(matriz[i][j] == 5){
                    Monster[i] = new Monster("caveira.png");
                    Monster[i].setPosicao(i, j);
                    this.addPersonagem(Monster[i]);
                }
                if(matriz[i][j] == 6){
                    Life[i] = new Life("coracao.png");
                    Life[i].setPosicao(i, j);
                    this.addPersonagem(Life[i]);
                }
                if(matriz[i][j] == 7){
                    diamond = new Diamond("Diamond.png");
                    diamond.setPosicao(i, j);
                    this.addPersonagem(diamond);
                }
                if(matriz[i][j] == 12){
                    Skeleton[i] = new Skeleton("skeleton.png", 2);
                    Skeleton[i].setPosicao(i, j);
                    this.addPersonagem(Skeleton[i]);
                }
                if(matriz[i][j] == 15){
                    Zombie[i] = new Zombie("zombie.png", 'v');
                    Zombie[i].setPosicao(i, j);
                    this.addPersonagem(Zombie[i]);
                }
                if(matriz[i][j] == 66){
                    ghast = new Ghast("ghast.png");
                    ghast.setPosicao(i,j);
                    this.addPersonagem(ghast);
                    ghast.setQttLifes(3);
                }
                if(matriz[i][j] == 33){
                    arco = new Arco("arco.png");
                    arco.setPosicao(i, j);
                    this.addPersonagem(arco);
                }
                
            }
        }
    }
    
    
    public boolean ehPosicaoValida(Posicao p){
        return cj.ehPosicaoValida(this.faseAtual, p);
    }
    public void addPersonagem(Personagem umPersonagem ) {
        faseAtual.add(umPersonagem);
    }

    public void removePersonagem(Personagem umPersonagem) {
        faseAtual.remove(umPersonagem);
    }

    public Graphics getGraphicsBuffer(){
        return g2;
    }
    public void paint(Graphics gOld) {
        Graphics g = this.getBufferStrategy().getDrawGraphics();
        /*Criamos um contexto gráfico*/
        g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);
        /*************Desenha cenário de fundo**************/
        for (int i = 0; i < Consts.RES; i++) {
            for (int j = 0; j < Consts.RES; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "background.png");
                    g2.drawImage(newImage,
                            j * Consts.CELL_SIDE, i * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);

                } catch (IOException ex) {
                    Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
            if (!this.faseAtual.isEmpty()) {
                this.cj.desenhaTudo(faseAtual);
                int aux = this.cj.processaTudo(faseAtual);
                if(aux == 1){
                    this.faseAt++;
                    this.cj.setFase(faseAt);
                    this.faseAtual.clear();
                    setFase(hero);
                }
                else if(aux == -1){
                    this.cj.setFase(faseAt);
                    this.faseAtual.clear();
                    hero.resetaAllHero();
                    setFase(hero);
                }
            }


        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    public void go() {
        TimerTask task = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.PERIOD);
    }

    public void keyPressed(KeyEvent e) {
        Hero hero = (Hero) this.faseAtual.get(0);
        if (e.getKeyCode() == KeyEvent.VK_M) {
            save("savegame.ser");
        }
        if (e.getKeyCode() == KeyEvent.VK_C) {
            this.faseAtual.clear();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            hero.moveUp();
            if (hero.hasSword()) {
                hero.changeImg("upSteveSword.png");
            } else {
                hero.changeImg("upSteve.png");
            }

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            hero.moveDown();
            hero.changeImg("downSteve.png");
            if (hero.hasSword()) {
                hero.changeImg("downSteveSword.png");
            } else {
                hero.changeImg("downSteve.png");
            }

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            hero.changeImg("leftSteve.png");
            if (hero.hasSword()) {
                hero.changeImg("leftSteveSword.png");
            } else {
                hero.changeImg("leftSteve.png");
            }
            hero.moveLeft();

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (hero.hasSword()) {
                hero.changeImg("rightSteveSword.png");
            } else {
                hero.changeImg("rightSteve.png");
            }

            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            if(hero.getArco()){
                hero.changeImg("upSteve.png");
                hero.shootUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            if(hero.getArco()){
                hero.changeImg("leftSteve.png");
                hero.shootLeft();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            if(hero.getArco()){
                hero.changeImg("downSteve.png");
                hero.shootDown();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            if(hero.getArco()){
                hero.changeImg("rightSteve.png");
                hero.shootRight();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(hero.hasSword()){
                switch(hero.getAttackDirection()){
                    case (1):
                        hero.changeImg("leftSteveSwordAttack.png");
                        hero.setKilled(true);
                        break;
                    case (2):
                        hero.changeImg("rightSteveSwordAttack.png");
                        hero.setKilled(true);
                        break;
                    case (3):
                        hero.changeImg("upSteveSwordAttack.png");
                        hero.setKilled(true);
                        break;
                    case (4):
                        hero.changeImg("downSteveSwordAttack.png");
                        hero.setKilled(true);
                        break;
                    default:
                        break;
                }
            }

        }else if (e.getKeyCode() == KeyEvent.VK_R){
            this.faseAtual.clear();
            setAllChar(hero);
        }
        else if(e.getKeyCode() == KeyEvent.VK_Z){
            this.faseAtual.clear();
            this.cj.setFase(3);
            faseAt = 3;
            setAllChar4(hero);
        }


        this.setTitle("-> Cell: " + (hero.getPosicao().getColuna()) + ", "
                + (hero.getPosicao().getLinha()));

        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
    }

    public void mousePressed(MouseEvent e) {
        Hero hero = (Hero) this.faseAtual.get(0);

        /* Clique do mouse desligado*/
         int x = e.getX();
         int y = e.getY();
     
         this.setTitle("X: "+ x + ", Y: " + y +
         " -> Cell: " + (y/Consts.CELL_SIDE) + ", " + (x/Consts.CELL_SIDE));
        
         hero.getPosicao().setPosicao(y/Consts.CELL_SIDE, x/Consts.CELL_SIDE);
         
        repaint();
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2023-1 - Skooter");
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
