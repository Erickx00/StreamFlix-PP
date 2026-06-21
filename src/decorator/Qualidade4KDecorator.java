package decorator;


import factory.Plano;

public class Qualidade4KDecorator extends PlanoDecorator {
    public Qualidade4KDecorator(Plano plano) {
        super(plano);
    }

    @Override
    public String getNome() {
        return planoDecorado.getNome() + " + 4K Ultra HD";
    }

    @Override
    public double getPreco() {
        return planoDecorado.getPreco() + 14.90;
    }

    @Override
    public boolean temQualidade4K() {
        return true;
    }
}
