package model;

public class Usuario {
    private static int contadorId = 1;

    private int id;
    private String nome;
    private String email;
    private Assinatura assinatura;

    public Usuario(String nome, String email) {
        this.id = contadorId++;
        this.nome = nome;
        this.email = email;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public Assinatura getAssinatura() { return assinatura; }
    public void setAssinatura(Assinatura assinatura) { this.assinatura = assinatura; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s)", id, nome, email);
    }
}
