package factory;

public class PlanoBasico implements Plano{
    @Override
    public String getNome() {
        return "Basico";
    }

    @Override
    public double getPreco() {
        return 18.99;
    }

    @Override
    public int getTelasSimultaneas() {
        return 1;
    }

    @Override
    public boolean temQualidade4K() {
        return false;
    }

    @Override
    public boolean temDownload() {
        return false;
    }
}
