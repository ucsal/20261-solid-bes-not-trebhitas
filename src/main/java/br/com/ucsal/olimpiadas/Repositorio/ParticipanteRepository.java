package br.com.ucsal.olimpiadas.Repositorio;

import br.com.ucsal.olimpiadas.model.Participante;

import java.util.ArrayList;
import java.util.List;

public class ParticipanteRepository {
    private final List<Participante> participantes = new ArrayList<>();

    public void salvar(Participante p) {

        participantes.add(p);
    }

    public List<Participante> listar() {

        return participantes;
    }

    public boolean IdentificadoPorId(long id) {
        return participantes.stream().anyMatch(p -> p.getId() == id);
    }
}
