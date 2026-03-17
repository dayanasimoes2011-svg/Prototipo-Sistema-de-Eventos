package com.eventos;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {
    private static final String EVENTOS_FILE = "events.data";
    private static final String USUARIOS_FILE = "users.data";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static void salvarEventos(List<Evento> eventos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EVENTOS_FILE))) {
            for (Evento e : eventos) {
                StringBuilder participantes = new StringBuilder();
                for (int i = 0; i < e.getParticipantes().size(); i++) {
                    if (i > 0) participantes.append(",");
                    participantes.append(e.getParticipantes().get(i));
                }
                writer.printf("%d|%s|%s|%s|%s|%s|%s%n",
                        e.getId(),
                        escapar(e.getNome()),
                        escapar(e.getEndereco()),
                        e.getCategoria().name(),
                        e.getHorario().format(FORMATTER),
                        escapar(e.getDescricao()),
                        participantes.toString());
            }
        } catch (IOException ex) {
            System.err.println("Erro ao salvar eventos: " + ex.getMessage());
        }
    }

    public static List<Evento> carregarEventos() {
        List<Evento> eventos = new ArrayList<>();
        File file = new File(EVENTOS_FILE);
        if (!file.exists()) return eventos;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 6) continue;
                try {
                    int id = Integer.parseInt(parts[0]);
                    String nome = desescapar(parts[1]);
                    String endereco = desescapar(parts[2]);
                    Categoria categoria = Categoria.valueOf(parts[3]);
                    LocalDateTime horario = LocalDateTime.parse(parts[4], FORMATTER);
                    String descricao = desescapar(parts[5]);

                    Evento evento = new Evento(id, nome, endereco, categoria, horario, descricao);

                    if (parts.length > 6 && !parts[6].isEmpty()) {
                        String[] pIds = parts[6].split(",");
                        for (String pid : pIds) {
                            if (!pid.trim().isEmpty()) {
                                evento.adicionarParticipante(Integer.parseInt(pid.trim()));
                            }
                        }
                    }
                    eventos.add(evento);
                } catch (Exception ex) {
                    System.err.println("Linha inválida no arquivo de eventos: " + line);
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao carregar eventos: " + ex.getMessage());
        }
        return eventos;
    }

    public static void salvarUsuarios(List<Usuario> usuarios) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USUARIOS_FILE))) {
            for (Usuario u : usuarios) {
                StringBuilder eventos = new StringBuilder();
                for (int i = 0; i < u.getEventosConfirmados().size(); i++) {
                    if (i > 0) eventos.append(",");
                    eventos.append(u.getEventosConfirmados().get(i));
                }
                writer.printf("%d|%s|%s|%s|%s%n",
                        u.getId(),
                        escapar(u.getNome()),
                        escapar(u.getEmail()),
                        escapar(u.getTelefone()),
                        eventos.toString());
            }
        } catch (IOException ex) {
            System.err.println("Erro ao salvar usuários: " + ex.getMessage());
        }
    }

    public static List<Usuario> carregarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        File file = new File(USUARIOS_FILE);
        if (!file.exists()) return usuarios;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 4) continue;
                try {
                    int id = Integer.parseInt(parts[0]);
                    String nome = desescapar(parts[1]);
                    String email = desescapar(parts[2]);
                    String telefone = desescapar(parts[3]);

                    Usuario usuario = new Usuario(id, nome, email, telefone);

                    if (parts.length > 4 && !parts[4].isEmpty()) {
                        String[] eIds = parts[4].split(",");
                        for (String eid : eIds) {
                            if (!eid.trim().isEmpty()) {
                                usuario.confirmarParticipacao(Integer.parseInt(eid.trim()));
                            }
                        }
                    }
                    usuarios.add(usuario);
                } catch (Exception ex) {
                    System.err.println("Linha inválida no arquivo de usuários: " + line);
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao carregar usuários: " + ex.getMessage());
        }
        return usuarios;
    }

    private static String escapar(String s) {
        return s.replace("|", "\\pipe").replace("\n", "\\n");
    }

    private static String desescapar(String s) {
        return s.replace("\\pipe", "|").replace("\\n", "\n");
    }
}
