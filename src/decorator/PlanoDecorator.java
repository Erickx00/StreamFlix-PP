package decorator;


import factory.Plano;

public abstract class PlanoDecorator implements Plano {
    protected Plano planoDecorado;

    public PlanoDecorator(Plano plano) {
        this.planoDecorado = plano;
    }

    @Override
    public String getNome() {
        return planoDecorado.getNome();
    }

    @Override
    public double getPreco() {
        return planoDecorado.getPreco();
    }

    @Override
    public int getTelasSimultaneas() {
        return planoDecorado.getTelasSimultaneas();
    }

    @Override
    public boolean temQualidade4K() {
        return planoDecorado.temQualidade4K();
    }

    @Override
    public boolean temDownload() {
        return planoDecorado.temDownload();
    }
}
