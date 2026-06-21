package state;

public interface EstadoAssinatura {
    void ativar(ContextoAssinatura ctx);
    void suspender(ContextoAssinatura ctx);
    void cancelar(ContextoAssinatura ctx);
    String getNome();
}
