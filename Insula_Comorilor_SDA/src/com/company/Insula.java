package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Insula {
    private List<Observer> observers = new LinkedList<>();
    private Map<Integer, List<Pirat>> luptePirati = new LinkedHashMap<>();
    private List<Pirat> piratList = new LinkedList<>();

    private int[][] suprafata;
    private int timp;

    public Insula(String numeFisier){
        try {
            File file = new File(numeFisier);
            Scanner myScan = new Scanner(file);

            String str = myScan.nextLine();
            String[] info = str.split(" ");

            suprafata = new int[Integer.parseInt(info[0])][Integer.parseInt(info[1])];
            timp = Integer.parseInt(info[2]);

            while (myScan.hasNext()) {
                str = myScan.nextLine();
                info = str.split(" ");

                if (info.length == 5){
                    Pirat p = new Pirat(info[0],Integer.parseInt(info[3]),Ocupatie.pirat,
                            Integer.parseInt(info[4]),
                            new Coordonate(Integer.parseInt(info[1]),Integer.parseInt(info[2])));
                    piratList.add(p);
                    observers.add(p);
                }
                if(info.length == 4){
                    suprafata[Integer.parseInt(info[1])][Integer.parseInt(info[2])] = Integer.parseInt(info[3]);
                }
            }
        }
        catch (FileNotFoundException f){
            System.out.println("Fisier " + numeFisier + "nu s-a gasit");
        }
    }
    public void Begin() {
        for(int i = 0;i<timp ;i++) {
            verificaPozitie();
            cautaComoara();
            partajarePirati();
            lupte();
            notifyAllObservers();
        }
    }
    public void cautaComoara(){
        for(Pirat p:piratList){
            if(p.getOcupatie() == Ocupatie.pirat) {
                p.setResureseTemerare(p.getResureseTemerare() - 1);
                if(p.getResureseTemerare()==0)
                    p.setOcupatie(Ocupatie.agricultor);
                int comoara = suprafata[p.getCoordonate().getX()][p.getCoordonate().getY()];
                if (comoara != 0) {
                    p.setComoriGasite(p.getComoriGasite() + comoara);
                    suprafata[p.getCoordonate().getX()][p.getCoordonate().getY()] = 0;
                }
            }
        }
    }
    public void verificaPozitie(){
        for(Pirat p:piratList){
            if(p.getOcupatie() == Ocupatie.pirat) {
                if (p.getCoordonate().getX() < 0 || p.getCoordonate().getY() < 0 ||
                        p.getCoordonate().getX() > suprafata.length-1 || p.getCoordonate().getY() > suprafata.length-1)
                    p.setOcupatie(Ocupatie.disparut);
            }
        }
    }
    public void lupte(){
        Pirat pirat;
        for (Integer nr :luptePirati.keySet()) {
            if (luptePirati.get(nr).size() >= 2) {
                int dim = luptePirati.get(nr).size();
                for (int j = 0; j < dim - 1; j += 2) {
                    pirat = luptePirati.get(nr).get(j + 1);
                    luptePirati.get(nr).get(j).setResureseTemerare(luptePirati.get(nr).get(j).getResureseTemerare() -
                            pirat.getResureseTemerare());
                    if(luptePirati.get(nr).get(j).getResureseTemerare()==0)
                        luptePirati.get(nr).get(j).setOcupatie(Ocupatie.agricultor);
                    pirat.setResureseTemerare(0);
                    pirat.setOcupatie(Ocupatie.agricultor);
                    pirat.setComoriGasite(0);
                    if (dim % 2 == 1) {
                        pirat = luptePirati.get(nr).get(dim-1);
                        pirat.setComoriGasite(pirat.getComoriGasite() +
                                suprafata[pirat.getCoordonate().getX()][pirat.getCoordonate().getY()]);
                        suprafata[pirat.getCoordonate().getX()][pirat.getCoordonate().getY()] = 0;
                    }
                }
            }
        }
    }
    public void partajarePirati(){
        List<Coordonate> coordDiferite = new LinkedList<>();
        luptePirati.clear();
        for(Pirat p : piratList){
            boolean este = false;
            for (Coordonate coord :coordDiferite){
                if (p.getCoordonate().getX()==coord.getX() &&
                        p.getCoordonate().getY()==coord.getY()) {
                    este = true;
                    break;
                }
            }
            if(!este)
                coordDiferite.add(p.getCoordonate());
        }
        int i = 0 ;
        for(Coordonate coord:coordDiferite) {
            List<Pirat> pirats  = new LinkedList<>();
            for (Pirat p : piratList) {
                if(p.getCoordonate().getX()==coord.getX() &&
                        p.getCoordonate().getY()==coord.getY() && p.getOcupatie()==Ocupatie.pirat)
                    pirats.add(p);
            }
            if(pirats.size()>=2) {
                pirats.sort((o1, o2) ->
                {
                    return Integer.compare(o2.getResureseTemerare(), o1.getResureseTemerare());
                });
                luptePirati.put(i++, pirats);
            }
        }
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
    public void sortPirati(){
        piratList.sort((o2,o1)->{
            if(o1.getComoriGasite()> o2.getComoriGasite())
                return 1;
            if (o1.getComoriGasite() == o2.getComoriGasite()){
                if(Ocupatie.getOcupatie(o1.getOcupatie()) == Ocupatie.getOcupatie(o2.getOcupatie())){
                    if(o1.getResureseTemerare()  > o2.getResureseTemerare())
                        return 1;
                    if(o1.getResureseTemerare()  < o2.getResureseTemerare())
                        return -1;
                    if(o1.getResureseTemerare()  == o2.getResureseTemerare()) {
                        if (o1.getNume().compareTo(o2.getNume()) > 0)
                            return -1;
                        if (o1.getNume().compareTo(o2.getNume()) < 0)
                            return 1;
                        if (o1.getNume().compareTo(o2.getNume()) == 0)
                            return 0;
                    }
                }
                if (Ocupatie.getOcupatie(o1.getOcupatie()) < Ocupatie.getOcupatie(o2.getOcupatie())) {
                    return 1;
                }
                return -1;
            }
            return -1;
        });
    }
    public String afisareSituatiePirati(boolean sortare){
        StringBuilder str = new StringBuilder();
        str.append("Lista piratilor ").append("\n");
        if(sortare)
            sortPirati();
        for(Pirat p : piratList){
            str.append(p).append("\n");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        Insula ins = new Insula("Insula.txt");
        ins.Begin();
        System.out.println(ins.afisareSituatiePirati(false));
        System.out.println(ins.afisareSituatiePirati(true));
    }
}
