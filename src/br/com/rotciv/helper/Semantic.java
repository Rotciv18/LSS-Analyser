package br.com.rotciv.helper;

import br.com.rotciv.model.Token;

public class Semantic {
    private static String expectedType = "";
    private static String expressionType = "";

    public static String getExpectedType() {
        return expectedType;
    }

    public static void setExpectedType(String expectedType) {
        Semantic.expectedType = expectedType;
    }

    public static String getExpressionType() {
        return expressionType;
    }

    public static void setExpressionType(String expressionType) {
        Semantic.expressionType = expressionType;
    }

    public static void updateExpressionType(String type) {
        //Primeiro termo da expressão
        if ( expressionType.equals("") ) {
            setExpressionType(type);
        } else //Para uma expressão de inteiros, espere apenas por inteiros
            if ( expressionType.equals(Lexical.types.INTEGER_NUMBER.getValue()) ) {
                if ( !type.equals(Lexical.types.BOOLEAN.getValue()) ) {
                    setExpressionType(type);
                }
            } else //Para uma expressão de reais, espere inteiros ou reais
            if ( expressionType.equals(Lexical.types.FLOAT_NUMBER.getValue()) ) {
                if ( type.equals(Lexical.types.INTEGER_NUMBER.getValue()) ||
                     type.equals(Lexical.types.FLOAT_NUMBER.getValue())) {
                    setExpressionType(Lexical.types.FLOAT_NUMBER.getValue());
                }
            } else //para uma expressão que tornou-se booleana, segue o baile.
            if ( expressionType.equals(Lexical.types.BOOLEAN.getValue()) ) {
                //faz nada
            }

    }

    public static boolean isTypeMatch () {
        //Int esperado
        if ( expectedType.equals(Lexical.types.INTEGER_NUMBER.getValue()) ) {
            return ( expressionType.equals(expectedType) );
        }

        //Real esperado
        if ( expectedType.equals(Lexical.types.FLOAT_NUMBER.getValue()) ) {
            return ( expressionType.equals(Lexical.types.INTEGER_NUMBER.getValue()) ||
                     expressionType.equals(expectedType));
        }

        //Boolean esperado
        if ( expectedType.equals(Lexical.types.BOOLEAN.getValue()) ) {
            return ( expressionType.equals(expectedType) );
        }

        return false;
    }
}
