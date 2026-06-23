package menu;



import decorator.ModoOfflineDecorator;
import decorator.Qualidade4KDecorator;
import decorator.TelaExtraDecorator;
import factory.Plano;
import factory.PlanoFactory;
import model.Assinatura;
import model.Filme;
import model.Usuario;
import observer.UsuarioObserver;
import prototype.Playlist;

import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner sc = new Scanner(System.in);
    private final Repositorio repo = Repositorio.getInstance();

    // ─────────────────────────────────────────────
    public void iniciar() {
        banner();
        int op;
        do {
            menuPrincipalOpcoes();
            op = lerInt();
            switch (op) {
                case 1 -> menuUsuarios();
                case 2 -> menuFilmes();
                case 3 -> menuAssinaturas();
                case 4 -> menuPlaylists();
                case 5 -> menuNotificacoes();
                case 0 -> System.out.println("\n Encerrando StreamFlix. Até logo!");
                default -> System.out.println("⚠ Opção inválida.");
            }
        } while (op != 0);
    }

    // ══════════════════════════════════════════════
    //  USUÁRIOS
    // ══════════════════════════════════════════════
    private void menuUsuarios() {
        int op;
        do {
            titulo("👤 USUÁRIOS");
            System.out.println(" 1. Cadastrar usuário");
            System.out.println(" 2. Listar usuários");
            System.out.println(" 0. Voltar");
            op = lerInt();
            switch (op) {
                case 1 -> cadastrarUsuario();
                case 2 -> listarUsuarios();
                case 0 -> {}
                default -> System.out.println("⚠ Opção inválida.");
            }
        } while (op != 0);
    }

    private void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("E-mail: ");
        String email = sc.nextLine();
        Usuario u = new Usuario(nome, email);
        repo.getUsuarios().add(u);
        System.out.println(" Usuário criado: " + u);
    }

    private void listarUsuarios() {
        if (repo.getUsuarios().isEmpty()) { System.out.println("Nenhum usuário cadastrado."); return; }
        titulo("Lista de Usuários");
        repo.getUsuarios().forEach(u -> {
            Assinatura a = repo.buscarAssinaturaPorUsuario(u.getId());
            String plano = a == null ? "Sem assinatura" : a.getPlano().getNome() + " [" + a.getEstado() + "]";
            System.out.printf("  %s | %s%n", u, plano);
        });
    }

    // ══════════════════════════════════════════════
    //  FILMES
    // ══════════════════════════════════════════════
    private void menuFilmes() {
        int op;
        do {
            titulo("🎬 FILMES/SÉRIES");
            System.out.println(" 1. Cadastrar filme/série");
            System.out.println(" 2. Listar filmes/séries");
            System.out.println(" 0. Voltar");
            op = lerInt();
            switch (op) {
                case 1 -> cadastrarFilme();
                case 2 -> listarFilmes();
                case 0 -> {}
                default -> System.out.println("⚠ Opção inválida.");
            }
        } while (op != 0);
    }

    private void cadastrarFilme() {
        System.out.print("Título: ");
        String titulo = sc.nextLine();
        System.out.print("Gênero: ");
        String genero = sc.nextLine();
        System.out.print("Ano de lançamento: ");
        int ano = lerInt();
        Filme f = new Filme(titulo, genero, ano);
        repo.getFilmes().add(f);
        System.out.println(" Filme cadastrado: " + f);
    }

    private void listarFilmes() {
        titulo("Lista de Filmes/Séries");
        repo.getFilmes().forEach(f -> System.out.println("  " + f));
    }

    // ══════════════════════════════════════════════
    //  ASSINATURAS  (Factory + State + Decorator)
    // ══════════════════════════════════════════════
    private void menuAssinaturas() {
        int op;
        do {
            titulo(" ASSINATURAS");
            System.out.println(" 1. Criar assinatura        [Factory]");
            System.out.println(" 2. Listar assinaturas");
            System.out.println(" 3. Alterar estado           [State]");
            System.out.println(" 4. Adicionar recurso extra  [Decorator]");
            System.out.println(" 0. Voltar");
            op = lerInt();
            switch (op) {
                case 1 -> criarAssinatura();
                case 2 -> listarAssinaturas();
                case 3 -> alterarEstado();
                case 4 -> adicionarRecursoExtra();
                case 0 -> {}
                default -> System.out.println("⚠ Opção inválida.");
            }
        } while (op != 0);
    }

    private void criarAssinatura() {
        if (repo.getUsuarios().isEmpty()) { System.out.println("⚠ Nenhum usuário cadastrado."); return; }
        listarUsuarios();
        System.out.print("ID do usuário: ");
        int uid = lerInt();
        Usuario u = repo.buscarUsuarioPorId(uid);
        if (u == null) { System.out.println("⚠ Usuário não encontrado."); return; }
        if (repo.buscarAssinaturaPorUsuario(uid) != null) {
            System.out.println("⚠ Usuário já possui assinatura."); return;
        }

        titulo("Escolha o Plano  [Factory]");
        System.out.println(" 1. Básico    — R$ 18,90");
        System.out.println(" 2. Standard  — R$ 34,90");
        System.out.println(" 3. Premium   — R$ 55,90");
        System.out.print("Plano: ");
        String tipo = sc.nextLine().trim();

        try {
            Plano plano = PlanoFactory.criarPlano(tipo);
            Assinatura a = new Assinatura(u, plano);
            repo.getAssinaturas().add(a);
            u.setAssinatura(a);
            System.out.println(" Assinatura criada!\n  " + a);
        } catch (IllegalArgumentException e) {
            System.out.println("⚠ " + e.getMessage());
        }
    }

    private void listarAssinaturas() {
        if (repo.getAssinaturas().isEmpty()) { System.out.println("Nenhuma assinatura."); return; }
        titulo("Lista de Assinaturas");
        repo.getAssinaturas().forEach(a -> {
            System.out.println("  " + a);
            System.out.println("    Plano: " + a.getPlano().descricao());
        });
    }

    private void alterarEstado() {
        if (repo.getAssinaturas().isEmpty()) { System.out.println("Nenhuma assinatura."); return; }
        listarAssinaturas();
        System.out.print("ID do usuário: ");
        int uid = lerInt();
        Assinatura a = repo.buscarAssinaturaPorUsuario(uid);
        if (a == null) { System.out.println("⚠ Assinatura não encontrada."); return; }

        titulo("Alterar Estado  [State] — atual: " + a.getEstado());
        System.out.println(" 1. Ativar");
        System.out.println(" 2. Suspender");
        System.out.println(" 3. Cancelar");
        int op = lerInt();
        switch (op) {
            case 1 -> a.ativar();
            case 2 -> a.suspender();
            case 3 -> a.cancelar();
            default -> System.out.println("⚠ Opção inválida.");
        }
    }

    private void adicionarRecursoExtra() {
        if (repo.getAssinaturas().isEmpty()) { System.out.println("Nenhuma assinatura."); return; }
        listarAssinaturas();
        System.out.print("ID do usuário: ");
        int uid = lerInt();
        Assinatura a = repo.buscarAssinaturaPorUsuario(uid);
        if (a == null) { System.out.println("⚠ Assinatura não encontrada."); return; }

        titulo("Adicionar Recurso Extra  [Decorator]");
        System.out.println(" 1. Tela Extra       +R$ 9,90");
        System.out.println(" 2. 4K Ultra HD      +R$ 14,90");
        System.out.println(" 3. Modo Offline     +R$ 7,90");
        int op = lerInt();
        Plano planoAtual = a.getPlano();
        Plano novoPlano = switch (op) {
            case 1 -> new TelaExtraDecorator(planoAtual);
            case 2 -> new Qualidade4KDecorator(planoAtual);
            case 3 -> new ModoOfflineDecorator(planoAtual);
            default -> null;
        };
        if (novoPlano == null) { System.out.println("⚠ Opção inválida."); return; }
        a.setPlano(novoPlano);
        System.out.println(" Recurso adicionado!\n  Novo plano: " + novoPlano.descricao());
    }

    // ══════════════════════════════════════════════
    //  PLAYLISTS  (Prototype)
    // ══════════════════════════════════════════════
    private void menuPlaylists() {
        int op;
        do {
            titulo("🎵 PLAYLISTS");
            System.out.println(" 1. Criar playlist");
            System.out.println(" 2. Listar playlists");
            System.out.println(" 3. Adicionar filme à playlist");
            System.out.println(" 4. Clonar playlist       [Prototype]");
            System.out.println(" 0. Voltar");
            op = lerInt();
            switch (op) {
                case 1 -> criarPlaylist();
                case 2 -> listarPlaylists();
                case 3 -> adicionarFilmePlaylist();
                case 4 -> clonarPlaylist();
                case 0 -> {}
                default -> System.out.println("⚠ Opção inválida.");
            }
        } while (op != 0);
    }

    private void criarPlaylist() {
        System.out.print("Nome da playlist: ");
        String nome = sc.nextLine();
        Playlist p = new Playlist(nome);
        repo.getPlaylists().add(p);
        System.out.println(" Playlist criada: " + p.getNome());
    }

    private void listarPlaylists() {
        if (repo.getPlaylists().isEmpty()) { System.out.println("Nenhuma playlist."); return; }
        repo.getPlaylists().forEach(System.out::println);
    }

    private void adicionarFilmePlaylist() {
        if (repo.getPlaylists().isEmpty()) { System.out.println("Nenhuma playlist."); return; }
        listarPlaylists();
        System.out.print("ID da playlist: ");
        int pid = lerInt();
        Playlist p = repo.buscarPlaylistPorId(pid);
        if (p == null) { System.out.println("⚠ Playlist não encontrada."); return; }

        listarFilmes();
        System.out.print("ID do filme: ");
        int fid = lerInt();
        Filme f = repo.buscarFilmePorId(fid);
        if (f == null) { System.out.println("⚠ Filme não encontrado."); return; }

        p.adicionarFilme(f);
        System.out.println(" Filme \"" + f.getTitulo() + "\" adicionado à playlist \"" + p.getNome() + "\".");
    }

    private void clonarPlaylist() {
        if (repo.getPlaylists().isEmpty()) { System.out.println("Nenhuma playlist."); return; }
        listarPlaylists();
        System.out.print("ID da playlist a clonar: ");
        int pid = lerInt();
        Playlist original = repo.buscarPlaylistPorId(pid);
        if (original == null) { System.out.println("⚠ Playlist não encontrada."); return; }

        Playlist clone = original.clone();
        repo.getPlaylists().add(clone);
        System.out.println(" Playlist clonada com sucesso!");
        System.out.println("  Original: [" + original.getId() + "] " + original.getNome());
        System.out.println("  Clone:    [" + clone.getId() + "] " + clone.getNome());
    }

    // ══════════════════════════════════════════════
    //  NOTIFICAÇÕES  (Observer)
    // ══════════════════════════════════════════════
    private void menuNotificacoes() {
        int op;
        do {
            titulo(" NOTIFICAÇÕES  [Observer]");
            System.out.println(" 1. Inscrever usuário em série");
            System.out.println(" 2. Lançar novo episódio (notifica inscritos)");
            System.out.println(" 0. Voltar");
            op = lerInt();
            switch (op) {
                case 1 -> inscreverUsuario();
                case 2 -> lancarEpisodio();
                case 0 -> {}
                default -> System.out.println("⚠ Opção inválida.");
            }
        } while (op != 0);
    }

    private void inscreverUsuario() {
        if (repo.getUsuarios().isEmpty()) { System.out.println("⚠ Nenhum usuário."); return; }
        if (repo.getFilmes().isEmpty())   { System.out.println("⚠ Nenhuma série."); return; }
        listarUsuarios();
        System.out.print("ID do usuário: ");
        int uid = lerInt();
        Usuario u = repo.buscarUsuarioPorId(uid);
        if (u == null) { System.out.println("⚠ Usuário não encontrado."); return; }

        listarFilmes();
        System.out.print("ID da série: ");
        int fid = lerInt();
        Filme f = repo.buscarFilmePorId(fid);
        if (f == null) { System.out.println("⚠ Série não encontrada."); return; }

        f.adicionarObserver(new UsuarioObserver(u));
        System.out.println("✅ " + u.getNome() + " inscrito(a) em \"" + f.getTitulo() + "\".");
    }

    private void lancarEpisodio() {
        if (repo.getFilmes().isEmpty()) { System.out.println("⚠ Nenhuma série."); return; }
        listarFilmes();
        System.out.print("ID da série: ");
        int fid = lerInt();
        Filme f = repo.buscarFilmePorId(fid);
        if (f == null) { System.out.println("⚠ Série não encontrada."); return; }

        System.out.print("Nome do episódio (ex: S02E01 - O Retorno): ");
        String ep = sc.nextLine();
        f.lancarNovoEpisodio(ep);
    }

    // ══════════════════════════════════════════════
    //  HELPERS
    // ══════════════════════════════════════════════
    private void banner() {
        System.out.println("""
            ╔══════════════════════════════════════════╗
            ║     StreamFlix — Sistema de Streaming  ║
            ║      Padrões de Projeto em Java          ║
            ╚══════════════════════════════════════════╝
            """);
    }

    private void menuPrincipalOpcoes() {
        System.out.println("""
            ═══════════════════════════════════════════
             Menu Principal
            ═══════════════════════════════════════════
             1.  Usuários
             2.  Filmes / Séries
             3.  Assinaturas  (Factory | State | Decorator)
             4.  Playlists    (Prototype)
             5.  Notificações (Observer)
             0. Sair
            ═══════════════════════════════════════════""");
        System.out.print("Opção: ");
    }

    private void titulo(String t) {
        System.out.println("\n─── " + t + " ─────────────────────────────");
    }

    private int lerInt() {
        while (true) {
            String linha = sc.nextLine().trim();
            try { return Integer.parseInt(linha); }
            catch (NumberFormatException e) { System.out.print("Digite um número: "); }
        }
    }
}
