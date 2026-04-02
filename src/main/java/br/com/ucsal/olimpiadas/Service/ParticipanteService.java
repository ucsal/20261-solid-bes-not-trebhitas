package Service;

import Repositorio.ParticipanteRepository;
import model.Participante;
import java.util.List;

public class ParticipanteService {

    private final ParticipanteRepository repo;

    public ParticipanteService(ParticipanteRepository repo) {

        this.repo = repo;
    }

    public Participante cadastrarParticipante(String nome, String email, long id) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }

        var p = new Participante();
        p.setId(id);
        p.setNome(nome);
        p.setEmail(email);

        repo.salvar(p);

        return p;
    }

    public List<Participante> listar() {

        return repo.listar();
    }

    public boolean IdentificadoPorId(long id) {

        return repo.IdentificadoPorId(id);
    }

}

