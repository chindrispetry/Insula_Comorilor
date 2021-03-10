package com.company;

import java.util.Arrays;
import java.util.List;

public enum Ocupatie {
    pirat,
    agricultor,
    disparut;
    private static  List<Ocupatie> list = Arrays.asList(values());
    public static int getOcupatie(Ocupatie ocupatie){
        return list.indexOf(ocupatie);
    }
}
