package br.com.ucsal.olimpiadas;

import br.com.ucsal.olimpiadas.Repositorio.ParticipanteRepository;
import br.com.ucsal.olimpiadas.Repositorio.ProvaRepository;
import br.com.ucsal.olimpiadas.Repositorio.QuestaoRepository;
import br.com.ucsal.olimpiadas.Repositorio.TentativaRepository;
import br.com.ucsal.olimpiadas.Service.ParticipanteService;
import br.com.ucsal.olimpiadas.Service.ProvaService;
import br.com.ucsal.olimpiadas.Service.QuestaoService;
import br.com.ucsal.olimpiadas.Service.TentativaService;

import java.util.Scanner;

public class App {


    private static final Scanner in = new Scanner(System.in);
    private static ParticipanteService ParticipanteService;
    private static ProvaService ProvaService;
    private static QuestaoService QuestaoService;
    private static TentativaService TentativaService;

    public static void main(String[] args) {
        var participanteRepo = new ParticipanteRepository();
        var provaRepo = new ProvaRepository();
        var questaoRepo = new QuestaoRepository();
        var tentativaRepo = new TentativaRepository();


        var participanteService = new ParticipanteService(participanteRepo);
        var provaService = new ProvaService(provaRepo);
        var questaoService = new QuestaoService(questaoRepo);
        var tentativaService = new TentativaService(participanteRepo, provaRepo, questaoRepo, tentativaRepo);

        var menu = new Menu(participanteService, provaService , questaoService, tentativaService);

        menu.iniciar();
    }
}