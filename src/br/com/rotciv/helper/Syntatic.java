package br.com.rotciv.helper;

import br.com.rotciv.model.Token;

import java.util.List;

public class Syntatic {

    private static int index = 0;

    public Syntatic() {
    }

    public boolean isProgram (List<Token> tokens){
        if (tokens.get(index).getString().equals("program")) {
            next();

            if ( isId(tokens.get(index)) ) {
                next();

                if ( tokens.get(index).getString().equals(";") ) {
                    next();

                    if (variableDeclarations(tokens)) {
                        next();

                        if (subProgramDeclarations(tokens)){
                            next();

                            if (compoundCommand(tokens)){
                                next();

                                if (tokens.get(index).getString().equals(".")) {
                                    return true;
                                } else {
                                    System.out.println("Erro em isProgram(). Obtido " + tokens.get(index).getString() + " em vez de '.'");
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Erro em isProgram(). Obtido " + tokens.get(index).getString() + " em vez de ';'");
                }
            } else {
                System.out.println("Erro em isProgram(). Obtido " + tokens.get(index).getString() + " em vez de um identificador");
            }
        } else {
            System.out.println("Erro em isProgram(). Obtido " + tokens.get(index).getString() + " em vez de 'program'");
        }

        return false;
    }

    private boolean variableDeclarations (List<Token> tokens) {
        if ( tokens.get(index).getString().equals("var") ) {
            next();

            return variableDeclarationList(tokens);
        }
        return true;
    }

    private boolean variableDeclarationList (List<Token> tokens) {
        if ( idList(tokens) ) {
            next();

            if ( tokens.get(index).getString().equals(":") ) {
                next();

                if ( isType(tokens.get(index)) ) {
                    next();

                    if ( tokens.get(index).getString().equals(";") ) {
                        next();

                        if ( isId(tokens.get(index)) ) {
                            return variableDeclarationList(tokens);
                        } else {
                            --index;
                            return true;
                        }
                    } else {
                        System.out.println("Erro em variableDeclarationList(). Obtido " + tokens.get(index).getString() + " em vez de ';'");
                    }
                } else {
                    System.out.println("Erro em variableDeclarationList(). Obtido " + tokens.get(index).getString() + " em vez de um tipo.");
                }
            } else {
                System.out.println("Erro em variableDeclarationList(). Obtido " + tokens.get(index).getString() + " em vez de ':'");
            }
        }
        return false;
    }

    private boolean subProgramDeclarations (List<Token> tokens){
        if (subProgramDeclaration(tokens)) {
            next();

            if ( tokens.get(index).getString().equals(";") ) {
                next();

                return subProgramDeclarations(tokens);
            } else {
                index--;
                return true;
            }
        }
        index--;
        return true;
    }

    private boolean subProgramDeclaration (List<Token> tokens) {
        if ( tokens.get(index).getString().equals("procedure") ) {
            next();

            if ( isId(tokens.get(index)) ) {
                next();

                if ( arguments(tokens) ) {
                    next();

                    if ( tokens.get(index).getString().equals(";") ){
                        next();

                        if (variableDeclarations(tokens)){
                            next();

                            if (subProgramDeclarations(tokens)) {
                                next();

                                return compoundCommand(tokens);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean arguments (List<Token> tokens) {
        if ( tokens.get(index).getString().equals("(") ) {
            next();

            if ( parameterList(tokens) ) {
                next();

                if ( tokens.get(index).getString().equals(")") ) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    private boolean parameterList (List<Token> tokens) {
        if (idList(tokens)) {
            next();

            if ( tokens.get(index).getString().equals(":") ) {
                next();

                if (isType(tokens.get(index))) {
                    next();

                    if ( tokens.get(index).getString().equals(";") ) {
                        next();

                        return parameterList(tokens);
                    } else {
                        index--;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean idList (List<Token> tokens) {
        if (isId(tokens.get(index))) {
            next();

            if ( tokens.get(index).getString().equals(";") ) {
                next();

                return idList(tokens);
            }
            index--;
            return true;
        }
        return false;
    }

    private boolean compoundCommand(List<Token> tokens){
        if ( tokens.get(index).getString().equals("begin") ) {
            next();

            if (optionalCommands(tokens)) {
                next();

                if ( tokens.get(index).getString().equals("end") ) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean optionalCommands (List<Token> tokens) {
        if (commandList(tokens))
            return true;

        return true;
    }

    private boolean commandList (List<Token> tokens) {
        if (command(tokens)) {
            next();

            if ( tokens.get(index).getString().equals(";") ) {
                next();

                return commandList(tokens);
            }
            --index;
            return true;
        }
        return false;
    }

    private boolean command (List<Token> tokens) {
        if (variable(tokens)) {
            next();

            if ( tokens.get(index).getString().equals(":=") ) {
                next();

                return expression(tokens);
            }
        } else if ( procedureActivation(tokens) ) {
            return true;
        } else if ( compoundCommand(tokens) ) {
            return true;
        } else if ( tokens.get(index).getString().equals("if") ) {
            next();

            if (expression(tokens)) {
                next();

                if ( tokens.get(index).getString().equals("then") ) {
                    next();

                    if ( command(tokens) ) {
                        next();

                        return elsePart(tokens);
                    }
                }
            }
        } else if ( tokens.get(index).getString().equals("while") ) {
            next();

            if (expression(tokens)) {
                next();

                if ( tokens.get(index).getString().equals("do") ) {
                    next();

                    return command(tokens);
                }
            }
        }
        return false;
    }

    private boolean elsePart(List<Token> tokens) {
        if ( tokens.get(index).getString().equals("else") ) {
            next();

            return command(tokens);
        }
        --index;
        return true;
    }

    private boolean procedureActivation (List<Token> tokens) {
        if ( isId(tokens.get(index)) ) {
            next();

            if ( tokens.get(index).getString().equals("(") ) {
                next();

                if (expressionsList(tokens)) {
                    next();

                    if ( tokens.get(index).getString().equals(")") ) {
                        return true;
                    }
                }
            }
            index--;
            return true;
        }
        return false;
    }

    private boolean expressionsList (List<Token> tokens) {
        if ( expression(tokens) ) {
            next();

            if ( tokens.get(index).getString().equals(",") ) {
                next();

                return expressionsList(tokens);
            }
            index--;
            return true;
        }
        return false;
    }

    private boolean expression(List<Token> tokens) {
        if (simpleExpression(tokens)) {
            next();

            if (isRelationalOperator(tokens.get(index))) {
                next();

                return simpleExpression(tokens);
            }
            index--;
            return true;
        }
        return false;
    }

    private boolean simpleExpression (List<Token> tokens) {
        if (isSign(tokens.get(index))) {
            next();
        }

        if (term(tokens)){
            next();

            if (isAdditiveOperator(tokens.get(index))) {
                next();

                return simpleExpression(tokens);
            }
            --index;
            return true;
        }

        return false;
    }

    private boolean term (List<Token> tokens) {
        if (factor(tokens)){
            next();

            if ( isMultiplicativeOperator(tokens.get(index)) ) {
                next();

                return term(tokens);
            }

            --index;
            return true;
        }
        return false;
    }

    private boolean factor (List<Token> tokens) {
        if (isId(tokens.get(index))) {
            next();

            if (tokens.get(index).getString().equals("(")) {
                next();

                if (expressionsList(tokens)) {
                    next();

                    if (tokens.get(index).getString().equals(")")){
                        return true;
                    }
                }
            }
            --index;
            return true;
        } else if (tokens.get(index).getType().equals(Lexical.types.INTEGER_NUMBER.getValue())
                    || tokens.get(index).getType().equals(Lexical.types.FLOAT_NUMBER.getValue())
                    || tokens.get(index).getString().equals("true")
                    || tokens.get(index).getString().equals("false")) {
            return true;

        } else if ( tokens.get(index).getString().equals("(") ) {
            next();

            if ( expression(tokens) ) {
                next();

                if ( tokens.get(index).getString().equals(")") ) {
                    return true;
                }
            }
        } else if ( tokens.get(index).getString().equals("not") ) {
            next();

            return factor(tokens);
        }

        return false;
    }

    private boolean variable (List<Token> tokens) {
        return isId(tokens.get(index));
    }

    private boolean isId (Token token) {
        if (!token.getType().equals(Lexical.types.KEYWORD.getValue()))
            return (Lexical.isIdentifier(token.getString()));
        return false;
    }

    private boolean isType (Token token) {
        if (token.getType().equals(Lexical.types.KEYWORD.getValue()) ) {
            return (token.getString().equals("boolean") || token.getString().equals("integer")
            || token.getString().equals("real"));
        }
        return false;
    }

    private boolean isSign (Token token) {
        String string = token.getString();

        return (string.equals("+") || string.equals("-"));
    }

    private boolean isRelationalOperator (Token token) {
        return Lexical.isRelational(token.getString());
    }

    private boolean isAdditiveOperator (Token token) {
        return Lexical.isAdditive(token.getString());
    }

    private boolean isMultiplicativeOperator (Token token) {
        return Lexical.isMultiplicative(token.getString());
    }

    private void next () {
        index++;
    }
}
