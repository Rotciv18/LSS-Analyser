package br.com.rotciv.helper;

public class Lexical {
    public enum types {
        KEYWORD("keyword"), IDENTIFIER("identifier"), INTEGER_NUMBER("integer"), FLOAT_NUMBER("float"),
        DELIMITER("delimiter"), ASSIGNMENT("assignment operator"), RELATIONAL_OP("relational operator"),
        ADDING_OP("adding operator"), MULTIPLICATIVE_OP("multiplicative operator"), COMMENT("comment");
        private final String value;

        types(String i) {
            value = i;
        }

        public String getValue() {
            return value;
        }
    }

    public static boolean isKeyword(String token) {
        return (token.equals("program") || token.equals("var") || token.equals("integer")
                || token.equals("real") || token.equals("boolean") || token.equals("procedure")
                || token.equals("begin") || token.equals("end") || token.equals("if")
                || token.equals("then") || token.equals("else") || token.equals("while")
                || token.equals("do") || token.equals("not") );
    }

    public static boolean isIdentifier(String token) {
        return token.charAt(0) != '0' && token.charAt(0) != '1' && token.charAt(0) != '2'
                && token.charAt(0) != '3' && token.charAt(0) != '4' && token.charAt(0) != '5'
                && token.charAt(0) != '6' && token.charAt(0) != '7' && token.charAt(0) != '8'
                && token.charAt(0) != '9' && !(isDelimiter(token)) && token.charAt(0) != ':';
    }

    //retorna false se em algum momento encontrar um caracter não inteiro
    public static boolean isInteger(String token) {
        for (int i = 0; i < token.length(); i++) {
            if (token.charAt(i) == '0' || token.charAt(i) == '1' || token.charAt(i) == '2'
                    || token.charAt(i) == '3' || token.charAt(i) == '4' || token.charAt(i) == '5'
                    || token.charAt(i) == '6' || token.charAt(i) == '7' || token.charAt(i) == '8'
                    || token.charAt(i) == '9' || token.charAt(0) == '-') // Para números negativos
                return true;
        }
        return false;
    }

    public static boolean isFloat (String token) {
        boolean isThereFloat = false;

        for (int i = 0; i < token.length(); i++){
            if (token.charAt(i) == '.')
                isThereFloat = true;

            if (token.charAt(i) != '0' || token.charAt(i) != '1' || token.charAt(i) != '2'
                    || token.charAt(i) != '3' || token.charAt(i) != '4' || token.charAt(i) != '5'
                    || token.charAt(i) != '6' || token.charAt(i) != '7' || token.charAt(i) != '8'
                    || token.charAt(i) != '9' || token.charAt(0) != '-' || token.charAt(i) != '.')
                return false;
        }
        if (isThereFloat)
            return true;
        return false;
    }

    public static boolean isDelimiter (String token){
        return token.equals(";") || token.equals(":") || token.equals(".") ||
                token.equals("(") || token.equals(")") || token.equals(",");
    }

    public static boolean isAssignment (String token){
        return token.equals(":=");
    }

    public static boolean isRelational (String token) {
        return token.equals(">") || token.equals("<") || token.equals(">=") ||
                token.equals("<=") || token.equals("=") || token.equals("<>");
    }

    public static boolean isAdditive (String token) {
        return token.equals("+") || token.equals("-") || token.equals("or");
    }

    public static boolean isMultiplicative (String token) {
        return (token.equals("*") || token.equals("/") || token.equals("and"));
    }

    public static boolean isComment (String token){
        return ( token.equals("{") || token.equals("}"));
    }

}
