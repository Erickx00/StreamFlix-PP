package model;



import factory.Plano;
import state.ContextoAssinatura;

import java.time.LocalDate;

public class Assinatura {
    private static int contadorId = 1;

    private int id;
    private Usuario usuario;
    private Plano plano;
    private LocalDate dataInicio;
    private ContextoAssinatura contexto;

    public Assinatura(Usuario usuario, Plano plano) {
        this.id = contadorId++;
        this.usuario = usuario;
        this.plano = plano;
        this.dataInicio = LocalDate.now();
        this.contexto = new ContextoAssinatura();
    }

    public void ativar()    { contexto.ativar(); }
    public void suspender() { contexto.suspender(); }
    public void cancelar()  { contexto.cancelar(); }

    public String getEstado() { return contexto.getNomeEstado(); }
    public int getId()        { return id; }
    public Usuario getUsuario() { return usuario; }
    public Plano getPlano()   { return plano; }
    public void setPlano(Plano plano) { this.plano = plano; }
    public LocalDate getDataInicio() { return dataInicio; }

    @Override
    public String toString() {
        return String.format(
            "Assinatura [%d] | Usuário: %s | Plano: %s | Estado: %s | Início: %s",
            id, usuario.getNome(), plano.getNome(), getEstado(), dataInicio
        );
    }
}
