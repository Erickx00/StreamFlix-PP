package factory;

public class PlanoStandard implements Plano{
    @Override
    public String getNome() {
        return "Standard";
    }

    @Override
    public double getPreco() {
        return 29.99;
    }

    @Override
    public int getTelasSimultaneas() {
        return 2;
    }

    @Override
    public boolean temQualidade4K() {
        return false;
    }

    @Override
    public boolean temDownload() {
        return true;
    }
}
