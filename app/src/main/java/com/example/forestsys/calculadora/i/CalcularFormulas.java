package com.example.forestsys.calculadora.i;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.forestsys.R;

public class CalcularFormulas extends AppCompatActivity {

        private TextView e1, e2;
        private String expressao;
        private Double resultado1 = 0.0;
        private Double resultado2 = 0.0;
        private String dez = "6519";
        private String tres = "3";
        private String dois = "2";
        private String trinta = "30";
        private String[] expressaoAux = {"sqrt", "((", dez, "*", tres, "/", dois, ")^2+",trinta,")"};

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.teste_formulas);
            e1 =  findViewById(R.id.resultado_formula1);
            e2 = findViewById(R.id.resultado_formula2);
            expressao="sqrt((10*3/2)^2+30)";
            resultado1 = new ExtendedDoubleEvaluator().evaluate(expressao);
            e1.setText(resultado1.toString());

            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < expressaoAux.length; i++) {
                sb.append(expressaoAux[i]);
            }
            String str = sb.toString().trim();
            resultado2 = new ExtendedDoubleEvaluator().evaluate(str);
            e2.setText(resultado2.toString());
    }
}
