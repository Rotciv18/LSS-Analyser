package br.com.rotciv;

import br.com.rotciv.helper.Lexical;
import br.com.rotciv.helper.ReadTextAsString;
import br.com.rotciv.model.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) throws Exception {
        String st1 = ReadTextAsString.readFileAsString("file.txt");
        List<String> fileLines = asList(st1.split("\n"));
/*
        StringTokenizer stringTokenizer = new StringTokenizer(fileLines.get(1), ".:;(),", true);
        while (stringTokenizer.hasMoreTokens()){
            System.out.println(stringTokenizer.nextToken());
        }
*/

        List<Token> tokens = new ArrayList<>();
        if (!setTokensAndTypes(fileLines, tokens)) {

            System.out.println("TOKEN         CLASSIFICAÇÃO         LINHA");
            for (int i = 0; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                System.out.println(token.getString() + "            " + token.getType() + "            " + token.getLine());
            }
        } else {
            System.out.println("Erro encontrado nos seguintes comandos: ");
            for (int i = 0; i < tokens.size(); i++){
                if ( tokens.get(i).getType().equals(Lexical.types.INVALID.getValue()) ) {
                    System.out.println(tokens.get(i).getString() + ", ");
                }
            }
        }
    }

    public static boolean setTokensAndTypes(List<String> fileLines, List<Token> tokens){
        boolean isComment = false;
        boolean hasErrors = false;

        for (int i = 0; i < fileLines.size();i++){ //linha em linha
            List<String> tokenLine = new ArrayList<>();
            StringTokenizer stringTokenizer = new StringTokenizer(fileLines.get(i), ".:=;(),+-*/{} \r", true);

            while (stringTokenizer.hasMoreTokens()){
                //tokenLine.add(stringTokenizer.nextToken().replaceAll("\\s", ""));
                tokenLine.add(stringTokenizer.nextToken());
            }

            for (int j = 0; tokenLine.size() > j; j++){ //token a token
                Token newToken = new Token();
                newToken.setLine(i+1);

                String tokenString = (tokenLine.get(j));

                //Parte pro próximo se encontrar espaço em branco
                if (tokenString.isBlank())
                    continue;

                //Comentários
                if (tokenString.equals("{") || isComment){
                    isComment = true;

                    if (tokenString.equals("}")){
                        isComment = false;
                    }

                    continue;
                }

                if (!tokenString.equals(":"))
                    ;
                else if (tokenLine.get(j+1).equals("=")){ //atribuição, e não delimitadores!
                    tokenString = ":=";
                    j++;
                }
                newToken.setString(tokenString);
                if ( newToken.getString().equals("") )
                    continue;
                newToken.setType();
                tokens.add(newToken);

                if (newToken.getType().equals(Lexical.types.INVALID.getValue()))
                    hasErrors = true;
            }
        }

        return hasErrors;
    }
}
