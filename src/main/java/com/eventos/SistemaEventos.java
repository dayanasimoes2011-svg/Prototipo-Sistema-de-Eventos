package com.eventos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaEventos {
    private List<Evento> eventos;
    private List<Usuario> usuarios;
    private int proximoEventoId;
    private int proximoUsuarioId;

    public SistemaEventos() {
        this.eventos = Persistencia.carregarEventos();
        this.usuarios = Persistencia.carregarUsuarios();
        this.proximoEventoId = eventos.stream()
                .mapToInt(Evento::getId).max().orElse(0) + 1;
        this.proximoUsuarioId = usuarios.stream()
                .mapToInt(Usuario::getId).max().orElse(0) + 1;
    }

    public Evento cadastrarEvento(String nome, String endereco, Categoria categoria,
                                   LocalDateTime horario, String descricao) {
        Evento evento = new Evento(proximoEventoId++, nome, endereco, categoria, horario, descricao);
        eventos.add(evento);
        salvar();
        return evento;
    }

    public Usuario cadastrarUsuario(String nome, String email, String telefone) {
        Usuario usuario = new Usuario(proximoUsuarioId++, nome, email, telefone);
        usuarios.add(usuario);
        salvar();
        return usuario;
    }

    public boolean confirmarParticipacao(int usuarioId, int eventoId) {
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        Evento evento = buscarEventoPorId(eventoId);
        if (usuario == null || evento == null) return false;
        usuario.confirmarParticipacao(eventoId);
        evento.adicionarParticipante(usuarioId);
        salvar();
        return true;
    }

    public boolean cancelarParticipacao(int usuarioId, int eventoId) {
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        Evento evento = buscarEventoPorId(eventoId);
        if (usuario == null || evento == null) return false;
        usuario.cancelarParticipacao(eventoId);
        evento.removerParticipante(usuarioId);
        salvar();
        return true;
    }

    public List<Evento> listarTodosEventos() {
        return eventos.stream()
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }

    public List<Evento> listarEventosAtivos() {
        return eventos.stream()
                .filter(Evento::isAtivo)
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }

    public List<Evento> listarEventosFuturos() {
        return listarEventosAtivos();
    }

    public List<Evento> listarEventosPassados() {
        return eventos.stream()
                .filter(Evento::isPassado)
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }

    public List<Evento> listarEventosPorCategoria(Categoria categoria) {
        return eventos.stream()
                .filter(e -> e.getCategoria() == categoria)
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }

    public Evento buscarEventoPorId(int id) {
        return eventos.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public Usuario buscarUsuarioPorId(int id) {
        return usuarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public List<Evento> listarEventosDoUsuario(int usuarioId) {
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        if (usuario == null) return new ArrayList<>();
        return usuario.getEventosConfirmados().stream()
                .map(this::buscarEventoPorId)
                .filter(e -> e != null)
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }

    private void salvar() {
        Persistencia.salvarEventos(eventos);
        Persistencia.salvarUsuarios(usuarios);
    }
}
