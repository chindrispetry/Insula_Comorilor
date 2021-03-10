package com.company;


public class Pirat implements Observer{
    private final String nume;
    private int resureseTemerare;
    private int comoriGasite;
    private Ocupatie ocupatie;
    private int indDepl;
    private Coordonate coordonate;


    public String getNume() {
        return nume;
    }

    private  int directie;
    private boolean inainte = false;


    public Pirat(String nume, int resureseTemerare, Ocupatie ocupatie, int indDepl, Coordonate coordonate) {
        this.nume = nume;
        this.resureseTemerare = resureseTemerare;
        this.ocupatie = ocupatie;
        this.indDepl = indDepl;
        this.coordonate = coordonate;
    }

    @Override
    public void update(){
        if(ocupatie == Ocupatie.pirat) {
            if (indDepl == 0) {
                coordonate.setX(coordonate.getX() + 1);
                return;
            }
            if (indDepl == 1) {
                coordonate.setY(coordonate.getY() + 1);
                return;
            }
            if (indDepl == 2) {
                coordonate.setX(coordonate.getX() - 1);
                coordonate.setY(coordonate.getY() + 1);
                return;
            }
            if (indDepl == 3) {
                coordonate.setX(coordonate.getX() + 1);
                coordonate.setY(coordonate.getY() + 1);
                return;
            }
            if (indDepl == 4) {
                if (directie == 0) {
                    coordonate.setX(coordonate.getX() - 1);
                    coordonate.setY(coordonate.getY() + 1);
                    directie = 1;
                } else {
                    coordonate.setX(coordonate.getX() + 1);
                    coordonate.setY(coordonate.getY() + 1);
                    directie = 0;
                }
                return;
            }
            if (indDepl == 5) {
                if (directie == 0) {
                    coordonate.setX(coordonate.getX() - 1);
                    coordonate.setY(coordonate.getY() + 1);
                    directie = 1;
                } else {
                    coordonate.setX(coordonate.getX() + 1);
                    coordonate.setY(coordonate.getY() - 1);
                    directie = 0;
                }
                return;
            }
            if (indDepl == 6) {
                if (inainte) {
                    coordonate.setX(coordonate.getX() + 1);
                    inainte = false;
                } else if (directie == 0) {
                    coordonate.setY(coordonate.getY() + 1);
                    directie = 1;
                    inainte = true;
                } else {
                    coordonate.setY(coordonate.getY() - 1);
                    inainte = true;
                }
                return;
            }
            if (indDepl == 7) {
                coordonate.setX(coordonate.getX() - 1);
                return;
            }
            if (indDepl == 8) {
                coordonate.setY(coordonate.getY() - 1);
            }
        }
    }

    public Coordonate getCoordonate() {
        return coordonate;
    }

    public int getResureseTemerare() {
        return resureseTemerare;
    }

    public void setResureseTemerare(int resureseTemerare) {
        this.resureseTemerare = resureseTemerare;
    }

    public int getComoriGasite() {
        return comoriGasite;
    }

    public void setComoriGasite(int comoriGasite) {
        this.comoriGasite = comoriGasite;
    }

    public Ocupatie getOcupatie() {
        return ocupatie;
    }

    public void setOcupatie(Ocupatie ocupatie) {
        this.ocupatie = ocupatie;
    }
    public String stringOcupatie(){
        return switch (ocupatie) {
            case pirat -> " pleaca spre corabie din " + coordonate;
            case agricultor -> "a renuntat la piraterie, e agricultor in" + coordonate;
            default -> " este disparut ";
        };
    }
    @Override
    public String toString() {
        return "Pirat " + nume +
                ", resurese temerare = " + resureseTemerare +
                " comori gasite = " + comoriGasite + " "+
                 stringOcupatie();
    }
}
