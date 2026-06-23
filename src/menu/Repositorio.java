package menu;



import model.Assinatura;
import model.Filme;
import model.Usuario;
import prototype.Playlist;

import java.util.ArrayList;
import java.util.List;

/** Simula banco de dados em memória */
public class Repositorio {
    private static final Repositorio INSTANCE = new Repositorio();

    private final List<Usuario>    usuarios    = new ArrayList<>();
    private final List<Filme>      filmes      = new ArrayList<>();
    private final List<Assinatura> assinaturas = new ArrayList<>();
    private final List<Playlist>   playlists   = new ArrayList<>();

    private Repositorio() {
        // Dados iniciais
        filmes.add(new Filme("Stranger Things", "Ficção Científica", 2016));
        filmes.add(new Filme("The Crown",        "Drama",            2016));
        filmes.add(new Filme("Squid Game",       "Thriller",         2021));
        filmes.add(new Filme("Arcane",           "Animação",         2021));
        filmes.add(new Filme("Wednesday",        "Terror/Comédia",   2022));
    }

    public static Repositorio getInstance() { return INSTANCE; }

    public List<Usuario>    getUsuarios()    { return usuarios; }
    public List<Filme>      getFilmes()      { return filmes; }
    public List<Assinatura> getAssinaturas() { return assinaturas; }
    public List<Playlist>   getPlaylists()   { return playlists; }

    public Usuario buscarUsuarioPorId(int id) {
        return usuarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public Filme buscarFilmePorId(int id) {
        return filmes.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
    }

    public Assinatura buscarAssinaturaPorUsuario(int usuarioId) {
        return assinaturas.stream()
            .filter(a -> a.getUsuario().getId() == usuarioId)
            .findFirst().orElse(null);
    }

    public Playlist buscarPlaylistPorId(int id) {
        return playlists.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }
}
