package com.eventos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SistemaEventos sistema = new SistemaEventos();
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   SISTEMA DE CADASTRO DE EVENTOS DA CIDADE");
        System.out.println("=================================================");

        boolean executando = true;
        while (executando) {
            exibirMenuPrincipal();
            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> menuEventos();
                case 2 -> menuUsuarios();
                case 3 -> menuParticipacao();
                case 0 -> {
                    System.out.println("\nEncerrando o sistema. Até logo!");
                    executando = false;
                }
                default -> System.out.println("\nOpção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n----- MENU PRINCIPAL -----");
        System.out.println("1. Gerenciar Eventos");
        System.out.println("2. Gerenciar Usuários");
        System.out.println("3. Participação em Eventos");
        System.out.println("0. Sair");
        System.out.println("--------------------------");
    }

    private static void menuEventos() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n----- MENU EVENTOS -----");
            System.out.println("1. Cadastrar novo evento");
            System.out.println("2. Listar todos os eventos");
            System.out.println("3. Listar eventos futuros (ativos)");
            System.out.println("4. Listar eventos passados");
            System.out.println("5. Listar eventos por categoria");
            System.out.println("6. Ver detalhes de um evento");
            System.out.println("0. Voltar");
            System.out.println("------------------------");

            int opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> cadastrarEvento();
                case 2 -> listarEventos(sistema.listarTodosEventos(), "Todos os Eventos");
                case 3 -> listarEventos(sistema.listarEventosAtivos(), "Eventos Futuros");
                case 4 -> listarEventos(sistema.listarEventosPassados(), "Eventos Passados");
                case 5 -> listarPorCategoria();
                case 6 -> verDetalhesEvento();
                case 0 -> voltar = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuUsuarios() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n----- MENU USUÁRIOS -----");
            System.out.println("1. Cadastrar novo usuário");
            System.out.println("2. Listar todos os usuários");
            System.out.println("3. Ver eventos de um usuário");
            System.out.println("0. Voltar");
            System.out.println("-------------------------");

            int opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> listarUsuarios();
                case 3 -> verEventosDoUsuario();
                case 0 -> voltar = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuParticipacao() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n----- MENU PARTICIPAÇÃO -----");
            System.out.println("1. Confirmar participação em evento");
            System.out.println("2. Cancelar participação em evento");
            System.out.println("0. Voltar");
            System.out.println("-----------------------------");

            int opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> confirmarParticipacao();
                case 2 -> cancelarParticipacao();
                case 0 -> voltar = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarEvento() {
        System.out.println("\n--- Cadastrar Novo Evento ---");
        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine().trim();

        Categoria categoria = selecionarCategoria();
        if (categoria == null) return;

        LocalDateTime horario = null;
        while (horario == null) {
            System.out.print("Horário (dd/MM/yyyy HH:mm): ");
            String entrada = scanner.nextLine().trim();
            try {
                horario = LocalDateTime.parse(entrada, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use dd/MM/yyyy HH:mm");
            }
        }

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine().trim();

        Evento evento = sistema.cadastrarEvento(nome, endereco, categoria, horario, descricao);
        System.out.println("\nEvento cadastrado com sucesso!");
        System.out.println(evento);
    }

    private static void cadastrarUsuario() {
        System.out.println("\n--- Cadastrar Novo Usuário ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();

        Usuario usuario = sistema.cadastrarUsuario(nome, email, telefone);
        System.out.println("\nUsuário cadastrado com sucesso!");
        System.out.println(usuario);
    }

    private static void confirmarParticipacao() {
        System.out.println("\n--- Confirmar Participação ---");
        listarUsuarios();
        int usuarioId = lerInteiro("ID do usuário: ");

        listarEventos(sistema.listarEventosAtivos(), "Eventos Disponíveis");
        int eventoId = lerInteiro("ID do evento: ");

        if (sistema.confirmarParticipacao(usuarioId, eventoId)) {
            System.out.println("Participação confirmada com sucesso!");
        } else {
            System.out.println("Erro: usuário ou evento não encontrado.");
        }
    }

    private static void cancelarParticipacao() {
        System.out.println("\n--- Cancelar Participação ---");
        int usuarioId = lerInteiro("ID do usuário: ");
        int eventoId = lerInteiro("ID do evento: ");

        if (sistema.cancelarParticipacao(usuarioId, eventoId)) {
            System.out.println("Participação cancelada com sucesso!");
        } else {
            System.out.println("Erro: usuário ou evento não encontrado.");
        }
    }

    private static void listarEventos(List<Evento> eventos, String titulo) {
        System.out.println("\n--- " + titulo + " ---");
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento encontrado.");
            return;
        }
        for (Evento e : eventos) {
            System.out.println(e);
        }
        System.out.println("Total: " + eventos.size() + " evento(s).");
    }

    private static void listarUsuarios() {
        List<Usuario> usuarios = sistema.listarUsuarios();
        System.out.println("\n--- Usuários Cadastrados ---");
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    private static void listarPorCategoria() {
        Categoria categoria = selecionarCategoria();
        if (categoria == null) return;
        listarEventos(sistema.listarEventosPorCategoria(categoria),
                "Eventos - " + categoria.getDescricao());
    }

    private static void verDetalhesEvento() {
        listarEventos(sistema.listarTodosEventos(), "Todos os Eventos");
        int id = lerInteiro("ID do evento: ");
        Evento evento = sistema.buscarEventoPorId(id);
        if (evento == null) {
            System.out.println("Evento não encontrado.");
        } else {
            System.out.println("\n" + evento.toDetalhes());
        }
    }

    private static void verEventosDoUsuario() {
        listarUsuarios();
        int id = lerInteiro("ID do usuário: ");
        List<Evento> eventos = sistema.listarEventosDoUsuario(id);
        listarEventos(eventos, "Eventos do Usuário");
    }

    private static Categoria selecionarCategoria() {
        System.out.println("\nCategorias disponíveis:");
        Categoria[] categorias = Categoria.values();
        for (int i = 0; i < categorias.length; i++) {
            System.out.printf("%d. %s%n", i + 1, categorias[i].getDescricao());
        }
        int escolha = lerInteiro("Selecione a categoria: ");
        if (escolha < 1 || escolha > categorias.length) {
            System.out.println("Categoria inválida.");
            return null;
        }
        return categorias[escolha - 1];
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                String linha = scanner.nextLine().trim();
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, insira um número válido.");
            }
        }
    }
}
