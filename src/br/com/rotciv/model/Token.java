package br.com.rotciv.model;

import br.com.rotciv.helper.Lexical;

public class Token extends Lexical{

    private String string;
    private Integer line;
    private String type;
    private String variableType = "";

    public Token() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getType() {
        return type;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public void setType() {
        if  ( isKeyword(this.string) ) {
            this.type = types.KEYWORD.getValue();
        } else if (isInteger(this.string)) {
            this.type = types.INTEGER_NUMBER.getValue();
        } else if (isFloat(this.string)) {
            this.type = types.FLOAT_NUMBER.getValue();
        } else if (isDelimiter(this.string)) {
            this.type = types.DELIMITER.getValue();
        } else if (isAssignment(this.string)) {
            this.type = types.ASSIGNMENT.getValue();
        } else if (isRelational(this.string)) {
            this.type = types.RELATIONAL_OP.getValue();
        } else if (isAdditive(this.string)){
            this.type = types.ADDING_OP.getValue();
        } else if (isMultiplicative(this.string)) {
            this.type = types.MULTIPLICATIVE_OP.getValue();
        } else if (isIdentifier(this.string)){
            this.type = types.IDENTIFIER.getValue();
        } else {
            this.type = types.INVALID.getValue();
        }
    }

    public void setTypeAs3DIdentifier () {
        this.type = types.ID3D.getValue();
    }
}
