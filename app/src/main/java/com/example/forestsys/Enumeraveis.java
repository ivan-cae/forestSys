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
        public static nivelAcesso getNivelAcesso(int numeral) {
            for (nivelAcesso nivel : values()) {
                if (nivel.numeral == numeral) {
                    return nivel;
                }
            }
            return null;
        }

        @TypeConverter

        public static int getNivelAcessoNumeral(nivelAcesso nivel) {
            return nivel.numeral;
        }
    }


    public enum manejo {
        Alto_Fuste (1),
        Talhadia(2);


        public int numeral;

        manejo(int i) {
            numeral = i;
        }

        @TypeConverter
        public static manejo getManejo(int numeral) {
            for (manejo manj : values()) {
                if (manj.numeral == numeral) {
                    return manj;
                }
            }
            return null;
        }

        @TypeConverter

        public static int getManejoNumeral(manejo manj) {
            return manj.numeral;
        }
    }


    public enum status {
        Andamento (1),
        Encerrado(2);


        public int numeral;

        status(int i) {
            numeral = i;
        }

        @TypeConverter
        public static status getStatus(int numeral) {
            for (status stts : values()) {
                if (stts.numeral == numeral) {
                    return stts;
                }
            }
            return null;
        }

        @TypeConverter

        public static int getStatusNumeral(status stts) {
            return stts.numeral;
        }
    }
}