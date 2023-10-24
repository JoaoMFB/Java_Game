package Controler;

import Modelo.*;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.swing.JButton;


public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener {

    private Hero hero;
    private ArrayList<Personagem> faseAtual;
    private ControleDeJogo cj = new ControleDeJogo();
    private Graphics g2;

    public Tela() {
        Desenho.setCenario(this);
        initComponents();
        this.addMouseListener(this); /*mouse*/

        this.addKeyListener(this);   /*teclado*/
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);


        faseAtual = new ArrayList<Personagem>();

        /*Cria faseAtual adiciona personagens*/

        setAllChar();
    }
    public void setAllChar(){
        int[][] matriz = {
                {9, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 6, 1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 6, 1, 0, 1, 0, 1, 0},
                {0, 1, 3, 1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 4, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 0 , 1, 5, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0},
                {2, 0, 0, 1, 5, 0, 0, 0, 0, 0, 0}
        };

        Wall Par[] = new Wall[121]; //index 1
        Key Key[] = new Key[12]; //index 2
        Box Box[] = new Box[12]; //index 3
        Door Door[] = new Door[12]; //index 4
        Monster Monster[] = new Monster[12]; //index 5
        Life Life[] = new Life[12]; //index 4


        for (int i = 0; i < 11; i++){
            for (int j = 0; j < 11; j++){
                if(matriz[i][j] == 9){
                    hero = new Hero("skoot.png");
                    hero.setPosicao(0, 0);
                    this.addPersonagem(hero);
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
                    Box[i] = new Box("coracao.png");
                    Box[i].setPosicao(i, j);
                    this.addPersonagem(Box[i]);
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

            }
        }

    }
    public boolean ehPosicaoValida(Posicao p){
        return cj.ehPosicaoValida(this.faseAtual, p);
    }
    public void addPersonagem(Personagem umPersonagem) {
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
            this.cj.processaTudo(faseAtual);
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
        if (e.getKeyCode() == KeyEvent.VK_C) {
            this.faseAtual.clear();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            hero.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_R){
        this.faseAtual.clear();
        setAllChar();

    }


        this.setTitle("-> Cell: " + (hero.getPosicao().getColuna()) + ", "
                + (hero.getPosicao().getLinha()));

        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
    }

    public void mousePressed(MouseEvent e) {
        /* Clique do mouse desligado*/
         int x = e.getX();
         int y = e.getY();
     
         this.setTitle("X: "+ x + ", Y: " + y +
         " -> Cell: " + (y/Consts.CELL_SIDE) + ", " + (x/Consts.CELL_SIDE));
        
         this.hero.getPosicao().setPosicao(y/Consts.CELL_SIDE, x/Consts.CELL_SIDE);
         
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
