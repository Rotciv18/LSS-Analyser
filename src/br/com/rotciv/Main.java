package br.com.rotciv;

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
        setTokensAndTypes(fileLines, tokens);

        System.out.println("TOKEN         CLASSIFICAÇÃO         LINHA");
        for (int i = 0; i < tokens.size(); i++){
            Token token = tokens.get(i);
            System.out.println(token.getString() + "            " + token.getType() + "            " + token.getLine());
        }
    }

    public static void setTokensAndTypes(List<String> fileLines, List<Token> tokens){
        for (int i = 0; i < fileLines.size();i++){ //linha em linha
            List<String> tokenLine = new ArrayList<>();
            StringTokenizer stringTokenizer = new StringTokenizer(fileLines.get(i), ".:=;(),", true);

            while (stringTokenizer.hasMoreTokens()){
                tokenLine.add(stringTokenizer.nextToken().replaceAll("\\s", ""));
            }

            for (int j = 0; tokenLine.size() > j; j++){ //token a token
                Token newToken = new Token();
                newToken.setLine(i+1);

                String tokenString = (tokenLine.get(j));
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
            }
        }
    }
}
