package br.com.ucsal.olimpiadas;
import br.com.ucsal.olimpiadas.Service.ParticipanteService;
import br.com.ucsal.olimpiadas.Service.ProvaService;
import br.com.ucsal.olimpiadas.Service.QuestaoService;
import br.com.ucsal.olimpiadas.Service.TentativaService;
import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.model.Tentativa;

import java.util.List;
import java.util.Scanner;


public class Menu {

    private final Scanner in = new Scanner(System.in);

    private final ParticipanteService participanteService;
    private final ProvaService provaService;
    private final QuestaoService questaoService;
    private final TentativaService tentativaService;

    private long proximoParticipanteId = 1;
    private long proximaProvaId = 1;
    private long proximaQuestaoId = 1;

    public Menu(ParticipanteService participanteService, ProvaService provaService, QuestaoService questaoService, TentativaService tentativaService) {
        this.participanteService = participanteService;
        this.provaService = provaService;
        this.questaoService = questaoService;
        this.tentativaService = tentativaService;
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n=== OLIMPÍADA DE QUESTÕES (V1) ===");
            System.out.println("1) Cadastrar participante");
            System.out.println("2) Cadastrar prova");
            System.out.println("3) Cadastrar questão (A–E) em uma prova");
            System.out.println("4) Aplicar prova (selecionar participante + prova)");
            System.out.println("5) Listar tentativas (resumo)");
            System.out.println("0) Sair");
            System.out.print("> ");

            switch (in.nextLine()) {
                case "1" -> cadastrarParticipante();
                case "2" -> cadastrarProva();
                case "3" -> cadastrarQuestao();
                case "4" -> aplicarProva();
                case "5" -> listarTentativas();
                case "0" -> {
                    System.out.println("tchau");
                    return;
                }
                default -> System.out.println("opção inválida");
            }
        }
    }

    private void cadastrarParticipante() {
        System.out.print("Nome: ");
        var nome = in.nextLine();

        System.out.print("Email (opcional): ");
        var email = in.nextLine();

        try {
            var p = participanteService.cadastrarParticipante(nome, email, proximoParticipanteId++);
            System.out.println("Participante cadastrado: " + p.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void cadastrarProva() {
        System.out.print("Título da prova: ");
        var titulo = in.nextLine();

        try {
            var pp = provaService.cadastrarProva(titulo, proximaProvaId++);
            System.out.println("Prova criada: " + pp.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void cadastrarQuestao() {

        System.out.print("ID da prova: ");
        long provaId = Long.parseLong(in.nextLine());

        System.out.println("Enunciado:");
        var enunciado = in.nextLine();

        var alternativas = new String[5];
        for (int i = 0; i < 5; i++) {
            char letra = (char) ('A' + i);
            System.out.print("Alternativa " + letra + ": ");
            alternativas[i] = letra + ") " + in.nextLine();
        }

        System.out.print("Alternativa correta: ");
        char correta = Questao.normalizar(in.nextLine().charAt(0));

        var q = new Questao();
        q.setId(proximaQuestaoId++);
        q.setProvaId(provaId);
        q.setEnunciado(enunciado);
        q.setAlternativas(alternativas);
        q.setAlternativaCorreta(correta);

        questaoService.cadastrarQuestao(q);

        System.out.println("Questão cadastrada!");
    }

    private void aplicarProva() {

        if (participanteService.listar().isEmpty()) {
            System.out.println("cadastre participantes primeiro");
            return;
        }

        if (provaService.listar().isEmpty()) {
            System.out.println("cadastre provas primeiro");
            return;
        }

        Long participanteId = escolherParticipante();
        if (participanteId == null) return;

        Long provaId = escolherProva();
        if (provaId == null) return;

        var questoes = questaoService.IdentificadoPorId(provaId);

        if (questoes.isEmpty()) {
            System.out.println("esta prova não possui questões cadastradas");
            return;
        }

        System.out.println("\n--- Início da Prova ---");

        List<Character> respostas = new java.util.ArrayList<>();

        for (var q : questoes) {

            System.out.println("\nQuestão #" + q.getId());
            System.out.println(q.getEnunciado());

            System.out.println("Posição inicial:");
            imprimirTabuleiroFen(q.getFenInicial());

            for (var alt : q.getAlternativas()) {
                System.out.println(alt);
            }

            System.out.print("Sua resposta (A–E): ");

            char marcada;
            try {
                marcada = Questao.normalizar(in.nextLine().trim().charAt(0));
            } catch (Exception e) {
                System.out.println("resposta inválida (marcando como errada)");
                marcada = 'X';
            }

            respostas.add(marcada);
        }

        var tentativa = tentativaService.aplicarProva(participanteId, provaId, respostas);

        int nota = tentativaService.calcularNota(tentativa);

        System.out.println("\n--- Fim da Prova ---");
        System.out.println("Nota (acertos): " + nota + " / " + questoes.size());
    }

    private void listarTentativas() {

        List<Tentativa> lista = tentativaService.listar();

        for (var t : lista) {
            int nota = tentativaService.calcularNota(t);

            System.out.printf(
                    "#%d | participante=%d | prova=%d | nota=%d/%d%n",
                    t.getId(),
                    t.getParticipanteId(),
                    t.getProvaId(),
                    nota,
                    t.getRespostas().size()
            );
        }
    }

    private Long escolherParticipante() {
        System.out.println("\nParticipantes:");

        for (var p : participanteService.listar()) {
            System.out.printf("  %d) %s%n", p.getId(), p.getNome());
        }

        System.out.print("Escolha o id do participante: ");

        try {
            long id = Long.parseLong(in.nextLine());

            boolean existe = participanteService.listar()
                    .stream()
                    .anyMatch(p -> p.getId() == id);

            if (!existe) {
                System.out.println("id inválido");
                return null;
            }

            return id;

        } catch (Exception e) {
            System.out.println("entrada inválida");
            return null;
        }
    }

    private Long escolherProva() {
        System.out.println("\nProvas:");

        for (var p : provaService.listar()) {
            System.out.printf("  %d) %s%n", p.getId(), p.getTitulo());
        }

        System.out.print("Escolha o id da prova: ");

        try {
            long id = Long.parseLong(in.nextLine());

            boolean existe = provaService.listar()
                    .stream()
                    .anyMatch(p -> p.getId() == id);

            if (!existe) {
                System.out.println("id inválido");
                return null;
            }

            return id;

        } catch (Exception e) {
            System.out.println("entrada inválida");
            return null;
        }
    }

    private void imprimirTabuleiroFen(String fen) {

        String parteTabuleiro = fen.split(" ")[0];
        String[] ranks = parteTabuleiro.split("/");

        System.out.println();
        System.out.println("    a b c d e f g h");
        System.out.println("   -----------------");

        for (int r = 0; r < 8; r++) {

            String rank = ranks[r];
            System.out.print((8 - r) + " | ");

            for (char c : rank.toCharArray()) {

                if (Character.isDigit(c)) {
                    int vazios = c - '0';
                    for (int i = 0; i < vazios; i++) {
                        System.out.print(". ");
                    }
                } else {
                    System.out.print(c + " ");
                }
            }

            System.out.println("| " + (8 - r));
        }

        System.out.println("   -----------------");
        System.out.println("    a b c d e f g h");
        System.out.println();
    }
}

