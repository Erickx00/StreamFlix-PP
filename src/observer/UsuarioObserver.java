package observer;


import model.Observer;
import model.Usuario;

public class UsuarioObserver implements Observer {
    private Usuario usuario;

    public UsuarioObserver(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void atualizar(String mensagem) {
        System.out.println(" Notificação para " + usuario.getNome() + ": " + mensagem);
    }

    public Usuario getUsuario() { return usuario; }
}
