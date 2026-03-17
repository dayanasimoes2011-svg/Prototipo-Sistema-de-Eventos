package com.eventos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private List<Integer> eventosConfirmados;

    public Usuario(int id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.eventosConfirmados = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public List<Integer> getEventosConfirmados() { return eventosConfirmados; }

    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public void confirmarParticipacao(int eventoId) {
        if (!eventosConfirmados.contains(eventoId)) {
            eventosConfirmados.add(eventoId);
        }
    }

    public void cancelarParticipacao(int eventoId) {
        eventosConfirmados.remove(Integer.valueOf(eventoId));
    }

    public boolean participaEvento(int eventoId) {
        return eventosConfirmados.contains(eventoId);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Email: %s | Telefone: %s",
                id, nome, email, telefone);
    }
}
