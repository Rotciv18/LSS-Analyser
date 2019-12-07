package br.com.rotciv.helper;

import br.com.rotciv.model.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Syntatic {

    private Stack<List<Token>> scope = new Stack<>();
    private Stack<Integer> indexIdList = new Stack<>();

    private int index = 0;
    private String expectingType = "";

    public Syntatic() {
        nextScope();
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
        } else {
            index--;
        }

        return true;
    }

    private boolean variableDeclarationList (List<Token> tokens) {
        if ( idList(tokens) ) {

            next();

            if ( tokens.get(index).getString().equals(":") ) {
                next();

                if ( isType(tokens.get(index)) ) {
                    //Seta os tipos da lista de variaveis
                    if ( !declareVariableAndTypes(tokens, tokens.get(index).getString()) ) {
                        //Programa deve abortar se já existir uma variavel no mesmo escopo
                        return false;
                    }

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
/* Função para alterações feitas em sala de aula
    private boolean subProgramDeclaration (List<Token> tokens) {
        if ( tokens.get(index).getString().equals("function") ) {
            //if ( tokens.get(index).getString().equals("procedure") ) {
            next();

            if ( isId(tokens.get(index)) ) {
                next();

                if ( arguments(tokens) ) {
                    next();

                    if ( tokens.get(index).getString().equals(":") ) {
                        next();

                        if ( isType(tokens.get(index)) ) {
                            next();

                            if (tokens.get(index).getString().equals(";")) {
                                next();

                                if (variableDeclarations(tokens)) {
                                    next();

                                    if (subProgramDeclarations(tokens)) {
                                        next();

                                        return compoundCommand(tokens);
                                    }
                                }
                            } else {
                                System.out.println("Erro em subProgramDeclaration(). Obtido " + tokens.get(index).getString() + " em vez de ';'");
                            }
                        }
                    }
                }
            } else {
                System.out.println("Erro em subProgramDeclaration(). Obtido " + tokens.get(index).getString() + " em vez de Identificador");
            }
        }
        return false;
    }

 */

    private boolean subProgramDeclaration (List<Token> tokens) {
        if ( tokens.get(index).getString().equals("procedure") ) {
            nextScope();
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
                    } else {
                        System.out.println("Erro em subProgramDeclaration(). Obtido " + tokens.get(index).getString() + " em vez de ';'");
                    }
                }
            } else {
                System.out.println("Erro em subProgramDeclaration(). Obtido " + tokens.get(index).getString() + " em vez de Identificador");
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
                } else {
                    System.out.println("Erro em arguments(). Obtido " + tokens.get(index).getString() + " em vez de ')'");
                }
                return false;
            }
            return false;
        }
        return true;
    }

    private boolean parameterList (List<Token> tokens) {
        if (idList(tokens)) {
            //declareVariable(token);

            next();

            if ( tokens.get(index).getString().equals(":") ) {
                next();

                if (isType(tokens.get(index))) {
                    //Seta os tipos da lista de variaveis
                    if ( !declareVariableAndTypes(tokens, tokens.get(index).getString()) ) {
                        //Programa deve abortar se já existir uma variavel no mesmo escopo
                        return false;
                    }

                    next();

                    if ( tokens.get(index).getString().equals(";") ) {
                        next();

                        return parameterList(tokens);
                    } else {
                        index--;
                        return true;
                    }
                } else {
                    System.out.println("Erro em parameterList(). Obtido " + tokens.get(index).getString() + " em vez de Tipo");
                }
            } else {
                System.out.println("Erro em parameterList(). Obtido " + tokens.get(index).getString() + " em vez de ':'");
            }
        }
        return false;
    }

    private boolean idList (List<Token> tokens) {
        if (isId(tokens.get(index))) {
            //Guarda indices de variaveis a serem declaradas
            indexIdList.push(index);

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
                    previousScope();
                    return true;
                } else {
                    System.out.println("Erro em compoundCommand(). Obtido " + tokens.get(index).getString() + " em vez de 'end'");
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
            //Checa se variavel foi declarada
            if ( !isVariableDeclared(tokens.get(index), tokens) ) {
                System.out.println("Variavel \"" + tokens.get(index).getString() + "\" nao foi declarada!");
                return false;
            }
            String variableType = tokens.get(index).getVariableType();
            next();

            if ( tokens.get(index).getString().equals(":=") ) {
                Semantic.setExpectedType(variableType);
                Semantic.setExpressionType("");
                next();

                if ( expression(tokens) ) {

                    if ( Semantic.isTypeMatch() ) {
                        return true;
                    } else {
                        System.out.println("Erro na linha " + tokens.get(index).getLine() + ":" +
                                           "Esperado uma expressão do tipo " + Semantic.getExpectedType() +
                                           ", mas foi obtido: " + Semantic.getExpressionType());
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else if ( procedureActivation(tokens) ) {
            return true;
        } else if ( compoundCommand(tokens) ) {
            return true;
        } else if ( tokens.get(index).getString().equals("if") ) {
            Semantic.setExpectedType(Lexical.types.BOOLEAN.getValue());
            Semantic.setExpressionType("");

            next();

            if (expression(tokens)) {
                if ( !Semantic.isTypeMatch() ) {
                    System.out.println("Erro na linha " + tokens.get(index).getLine() + ":" +
                            "Esperado uma expressão do tipo " + Semantic.getExpectedType() +
                            ", mas foi obtido: " + Semantic.getExpressionType());
                    return false;
                }

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
        } else {
            System.out.println("Erro em command(). Encontrado: " + tokens.get(index).getString());
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
                //Resultado da expressão deverá ser do tipo boolean
                Semantic.setExpressionType(Lexical.types.BOOLEAN.getValue());

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
                //Operador multiplicativo: Resultado deve ser real
                Semantic.updateExpressionType(Lexical.types.FLOAT_NUMBER.getValue());

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
            if ( !isVariableDeclared(tokens.get(index), tokens) ) {
                System.out.println("Variavel \"" + tokens.get(index).getString() + "\" nao foi declarada!");
            }

            //Atualizar tipo da expressão de acordo com a variável encontrada
            Semantic.updateExpressionType(tokens.get(index).getVariableType());

            next();

            if (tokens.get(index).getString().equals("(")) {
                next();

                if (expressionsList(tokens)) {
                    next();

                    if (tokens.get(index).getString().equals(")")){
                        return true;
                    } else {
                        System.out.println("Erro em factor(). Obtido " + tokens.get(index).getString() + " em vez de ')'");
                        return false;
                    }
                }
            }
            --index;
            return true;
        } else if (tokens.get(index).getType().equals(Lexical.types.INTEGER_NUMBER.getValue())
                    || tokens.get(index).getType().equals(Lexical.types.FLOAT_NUMBER.getValue())
                    || tokens.get(index).getString().equals("true")
                    || tokens.get(index).getString().equals("false")) {

            String type = tokens.get(index).getType();
            boolean isRelational = ( type.equals("true") || type.equals("false") );
            //Atualiza expressão para boolean se achar "true" ou "false". Se não, atualiza para int/real
            Semantic.updateExpressionType( isRelational ? Lexical.types.BOOLEAN.getValue()
                                                        : type) ;

            return true;

        } else if ( tokens.get(index).getString().equals("(") ) {
            next();

            if ( expression(tokens) ) {
                next();

                if ( tokens.get(index).getString().equals(")") ) {
                    return true;
                } else {
                    System.out.println("Erro em factor(). Obtido " + tokens.get(index).getString() + " em vez de ')'");
                }
            }
        } else if ( tokens.get(index).getString().equals("not") ) {
            Semantic.updateExpressionType(Lexical.types.BOOLEAN.getValue());

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

    private void previousScope () {
        scope.pop();
    }

    private void nextScope () {
        scope.push(new ArrayList<>());
    }

    private boolean isVariableDeclared (Token token, List<Token> tokens) {
        List<Token> tokensInScope = scope.peek();
        for (Token value : tokensInScope) {
            if (value.getString().equals(token.getString())) {
                tokens.get(index).setVariableType(value.getVariableType());
                return true;
            }
        }
        return false;
    }

    private boolean declareVariable (Token token) {
        if ( isVariableDeclared(token, new ArrayList<>()) ) {
            System.out.println("Variavel \"" + token.getString() + "\" ja declarada!");
            return false;
        }
        scope.peek().add(token);
        return true;
    }

    private boolean declareVariableAndTypes (List<Token> tokens, String type) {
        while ( !indexIdList.empty() ) {
            if ( !declareVariable(tokens.get(indexIdList.peek())) )
                return false;
            tokens.get(indexIdList.pop()).setVariableType(type);
        }
        return true;
    }
}
