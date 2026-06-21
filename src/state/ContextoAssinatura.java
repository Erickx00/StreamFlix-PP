package state;

public class ContextoAssinatura {
    private EstadoAssinatura estado;

    public ContextoAssinatura() {
        this.estado = new EstadoAtivo();
    }

    public void setEstado(EstadoAssinatura estado) {
        System.out.println(" Estado alterado: " + this.estado.getNome() + " → " + estado.getNome());
        this.estado = estado;
    }

    public EstadoAssinatura getEstado() { return estado; }

    public void ativar()   { estado.ativar(this); }
    public void suspender(){ estado.suspender(this); }
    public void cancelar() { estado.cancelar(this); }

    public String getNomeEstado() { return estado.getNome(); }
}
