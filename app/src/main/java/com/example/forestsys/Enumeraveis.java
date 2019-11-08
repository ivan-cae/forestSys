package com.example.forestsys;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

public class Enumeraveis {
    public enum nivelAcesso {
        Admin(1),
        Operador(2);


        public int numeral;

        nivelAcesso(int i) {
            numeral = i;
        }

        @TypeConverter
        public static nivelAcesso getNivelAcesso(int numeral){
            for(nivelAcesso nivel : values()){
                if(nivel.numeral == numeral){
                    return nivel;
                }
            }
            return null;
        }

        @TypeConverter

        public static int getNivelAcessoNumeral(nivelAcesso nivel){
            return nivel.numeral;
        }

        public void setNivelAcessoNumeral(int num){
            this.numeral = num;
        }
    }
}
