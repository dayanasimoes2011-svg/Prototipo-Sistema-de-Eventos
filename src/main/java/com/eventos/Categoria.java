package com.eventos;

public enum Categoria {
    FESTA("Festa"),
    ESPORTIVO("Esportivo"),
    SHOW("Show"),
    CULTURAL("Cultural"),
    EDUCACIONAL("Educacional"),
    GASTRONOMICO("Gastronômico"),
    OUTRO("Outro");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
