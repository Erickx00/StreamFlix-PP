package prototype;



import model.Filme;

import java.util.ArrayList;
import java.util.List;

public class Playlist implements Cloneable {
    private static int contadorId = 1;

    private int id;
    private String nome;
    private List<Filme> filmes;

    public Playlist(String nome) {
        this.id = contadorId++;
        this.nome = nome;
        this.filmes = new ArrayList<>();
    }

    public void adicionarFilme(Filme f) { filmes.add(f); }
    public void removerFilme(int index)  { filmes.remove(index); }
    public List<Filme> getFilmes()       { return filmes; }
    public String getNome()              { return nome; }
    public void setNome(String nome)     { this.nome = nome; }
    public int getId()                   { return id; }

    /** Prototype: clona a playlist com cópias dos filmes */
    @Override
    public Playlist clone() {
        try {
            Playlist clone = (Playlist) super.clone();
            clone.id = contadorId++;
            clone.nome = "Cópia de " + this.nome;
            clone.filmes = new ArrayList<>();
            for (Filme f : this.filmes) {
                clone.filmes.add(f.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Playlist [%d] \"%s\" — %d filme(s)\n", id, nome, filmes.size()));
        for (int i = 0; i < filmes.size(); i++) {
            sb.append(String.format("  %d. %s\n", i + 1, filmes.get(i)));
        }
        return sb.toString();
    }
}
