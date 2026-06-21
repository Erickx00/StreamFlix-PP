package factory;

public class PlanoPremium implements Plano{
    @Override
    public String getNome() {
        return "Premium";
    }

    @Override
    public double getPreco() {
        return 49.99;
    }

    @Override
    public int getTelasSimultaneas() {
        return 4;
    }

    @Override
    public boolean temQualidade4K() {
        return true;
    }

    @Override
    public boolean temDownload() {
        return true;
    }
}
