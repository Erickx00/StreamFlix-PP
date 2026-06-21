package state;

public class EstadoSuspenso implements EstadoAssinatura {

    @Override
    public void ativar(ContextoAssinatura ctx) {
        System.out.println(" Assinatura reativada com sucesso.");
        ctx.setEstado(new EstadoAtivo());
    }

    @Override
    public void suspender(ContextoAssinatura ctx) {
        System.out.println(" Assinatura já está SUSPENSA.");
    }

    @Override
    public void cancelar(ContextoAssinatura ctx) {
        System.out.println(" Assinatura cancelada a partir de suspensão.");
        ctx.setEstado(new EstadoCancelado());
    }

    @Override
    public String getNome() { return "SUSPENSA"; }
}
