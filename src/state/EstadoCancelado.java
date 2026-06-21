package state;

public class EstadoCancelado implements EstadoAssinatura {

    @Override
    public void ativar(ContextoAssinatura ctx) {
        System.out.println(" Assinatura reativada após cancelamento.");
        ctx.setEstado(new EstadoAtivo());
    }

    @Override
    public void suspender(ContextoAssinatura ctx) {
        System.out.println(" Não é possível suspender uma assinatura CANCELADA.");
    }

    @Override
    public void cancelar(ContextoAssinatura ctx) {
        System.out.println(" Assinatura já está CANCELADA.");
    }

    @Override
    public String getNome() { return "CANCELADA"; }
}
