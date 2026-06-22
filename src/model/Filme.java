package model;

import java.util.ArrayList;
import java.util.List;

public class Filme implements Cloneable {
    private static int contadorId = 1;

    private int id;
    private String titulo;
    private String genero;
    private int anoLancamento;
    private List<Observer> observers = new ArrayList<>();

    public Filme(String titulo, String genero, int anoLancamento) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
    }

    // Observer support
    public void adicionarObserver(Observer o) { observers.add(o); }
    public void removerObserver(Observer o) { observers.remove(o); }
    public void notificarObservers(String mensagem) {
        for (Observer o : observers) o.atualizar(mensagem);
    }

    public void lancarNovoEpisodio(String episodio) {
        System.out.println("\n Novo episódio lançado: \"" + episodio + "\" de \"" + titulo + "\"");
        notificarObservers("Novo episódio disponível: " + episodio + " - " + titulo);
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public int getAnoLancamento() { return anoLancamento; }

    @Override
    public Filme clone() {
        try {
            Filme clone = (Filme) super.clone();
            clone.observers = new ArrayList<>();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%d) - %s", id, titulo, anoLancamento, genero);
    }
}
