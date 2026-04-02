package Service;

import Repositorio.*;
import model.*;

import java.util.List;

public class TentativaService {

    private final ParticipanteRepository participanteRepo;
    private final ProvaRepository provaRepo;
    private final QuestaoRepository questaoRepo;
    private final TentativaRepository tentativaRepo;

    private long proximoId = 1;

    public TentativaService(
            ParticipanteRepository participanteRepo,
            ProvaRepository provaRepo,
            QuestaoRepository questaoRepo,
            TentativaRepository tentativaRepo
    ) {
        this.participanteRepo = participanteRepo;
        this.provaRepo = provaRepo;
        this.questaoRepo = questaoRepo;
        this.tentativaRepo = tentativaRepo;
    }

    public Tentativa iniciarTentativa(long participanteId, long provaId, long id) {

        if (!participanteRepo.IdentificadoPorId(participanteId)) {
            throw new IllegalArgumentException("Participante inválido");
        }

        if (!provaRepo.IdentificaPorId(provaId)) {
            throw new IllegalArgumentException("Prova inválida");
        }

        var t = new Tentativa();
        t.setId(id);
        t.setParticipanteId(participanteId);
        t.setProvaId(provaId);

        return t;
    }

    public List<Questao> buscarQuestoesDaProva(long provaId) {
        return questaoRepo.IdentificadoPorId(provaId);
    }

    public void salvar(Tentativa t) {
        tentativaRepo.salvar(t);
    }

    public Tentativa aplicarProva(long participanteId, long provaId, List<Character> respostas) {

        var tentativa = iniciarTentativa(participanteId, provaId, proximoId++);

        var questoes = buscarQuestoesDaProva(provaId);

        for (int i = 0; i < questoes.size(); i++) {
            var q = questoes.get(i);

            char marcada = respostas.get(i);

            var r = new Resposta();
            r.setQuestaoId(q.getId());
            r.setAlternativaMarcada(marcada);
            r.setCorreta(q.isRespostaCorreta(marcada));

            tentativa.getRespostas().add(r);
        }

        salvar(tentativa);

        return tentativa;
    }

    public List<Tentativa> listar() {
        return tentativaRepo.listar();
    }

    public int calcularNota(Tentativa t) {
        int acertos = 0;

        for (var r : t.getRespostas()) {
            if (r.isCorreta()) {
                acertos++;
            }
        }

        return acertos;
    }
}