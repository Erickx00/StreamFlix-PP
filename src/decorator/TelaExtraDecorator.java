package decorator;


import factory.Plano;

public class TelaExtraDecorator extends PlanoDecorator {
    public TelaExtraDecorator(Plano plano) {
        super(plano);
    }

    @Override
    public String getNome() {
        return planoDecorado.getNome() + " + Tela Extra";
    }

    @Override
    public double getPreco() {
        return planoDecorado.getPreco() + 9.90;
    }

    @Override
    public int getTelasSimultaneas() {
        return planoDecorado.getTelasSimultaneas() + 1;
    }
}
