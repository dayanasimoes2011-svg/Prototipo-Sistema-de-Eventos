package com.eventos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private int id;
    private String nome;
    private String endereco;
    private Categoria categoria;
    private LocalDateTime horario;
    private String descricao;
    private List<Integer> participantes;

    public Evento(int id, String nome, String endereco, Categoria categoria,
                  LocalDateTime horario, String descricao) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
        this.participantes = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public Categoria getCategoria() { return categoria; }
    public LocalDateTime getHorario() { return horario; }
    public String getDescricao() { return descricao; }
    public List<Integer> getParticipantes() { return participantes; }

    public void setNome(String nome) { this.nome = nome; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public void adicionarParticipante(int usuarioId) {
        if (!participantes.contains(usuarioId)) {
            participantes.add(usuarioId);
        }
    }

    public void removerParticipante(int usuarioId) {
        participantes.remove(Integer.valueOf(usuarioId));
    }

    public boolean isAtivo() {
        return horario.isAfter(LocalDateTime.now());
    }

    public boolean isPassado() {
        return horario.isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Nome: %s | Categoria: %s | Horário: %s | Endereço: %s | Participantes: %d",
                id, nome, categoria, horario.format(FORMATTER), endereco, participantes.size());
    }

    public String toDetalhes() {
        return String.format(
                "=== %s ===\n" +
                "ID: %d\n" +
                "Categoria: %s\n" +
                "Horário: %s\n" +
                "Endereço: %s\n" +
                "Descrição: %s\n" +
                "Participantes confirmados: %d",
                nome, id, categoria, horario.format(FORMATTER),
                endereco, descricao, participantes.size());
    }
}
