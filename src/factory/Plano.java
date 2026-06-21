package factory;


public interface Plano {
    String getNome();
    double getPreco();
    int getTelasSimultaneas();
    boolean temQualidade4K();
    boolean temDownload();

    default String descricao() {
        return String.format(
                "%-12s | R$ %5.2f/mes | %d tela(s) | 4K: %-3s | Download: %-3s",
                getNome(), getPreco(), getTelasSimultaneas(),
                temQualidade4K() ? "Sim" : "Nao",
                temDownload() ? "Sim" : "Nao"
        );
    }
}

