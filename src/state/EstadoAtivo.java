package state;

public class EstadoAtivo implements EstadoAssinatura {

    @Override
    public void ativar(ContextoAssinatura ctx) {
        System.out.println(" Assinatura já está ATIVA.");
    }

    @Override
    public void suspender(ContextoAssinatura ctx) {
        System.out.println(" Assinatura suspensa com sucesso.");
        ctx.setEstado(new EstadoSuspenso());
    }

    @Override
    public void cancelar(ContextoAssinatura ctx) {
        System.out.println(" Assinatura cancelada.");
        ctx.setEstado(new EstadoCancelado());
    }

    @Override
    public String getNome() { return "ATIVA"; }
}
