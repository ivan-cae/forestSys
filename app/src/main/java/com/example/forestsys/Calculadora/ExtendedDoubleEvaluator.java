package com.example.forestsys.Calculadora;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Parameters;
import java.util.Iterator;

/*
 * Classe auxiliar responsável por calcular a raiz quadrada de um numero
 */
public class ExtendedDoubleEvaluator extends DoubleEvaluator {
    private static final Function SQRT = new Function("sqrt", 1);
    private static final Parameters PARAMS;

    static {
        PARAMS = DoubleEvaluator.getDefaultParameters();
        PARAMS.add(SQRT);
    }

    public ExtendedDoubleEvaluator() {
        super(PARAMS);
    }

    //retorna a raiz quadrada de um número
    @Override
    public Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
        if (function == SQRT) return Math.sqrt(arguments.next());

        return super.evaluate(function, arguments, evaluationContext);
    }
}
