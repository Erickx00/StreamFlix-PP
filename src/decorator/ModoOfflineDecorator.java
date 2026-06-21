package decorator;


import factory.Plano;

public class ModoOfflineDecorator extends PlanoDecorator {
    public ModoOfflineDecorator(Plano plano) {
        super(plano);
    }

    @Override
    public String getNome() {
        return planoDecorado.getNome() + " + Modo Offline";
    }

    @Override
    public double getPreco() {
        return planoDecorado.getPreco() + 7.90;
    }

    @Override
    public boolean temDownload() {
        return true;
    }
}
