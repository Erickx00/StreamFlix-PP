# 🎬 StreamFlix — Sistema de Streaming (Padrões de Projeto em Java)

Projeto final da disciplina de **Padrões de Projeto**, implementando um sistema de streaming simplificado (estilo Netflix) totalmente via **terminal**, com aplicação prática de 5 padrões de projeto GoF.

---

## 📚 Padrões de Projeto Aplicados

| Padrão       | Onde é usado                             | Intenção                                              |
|--------------|------------------------------------------|-------------------------------------------------------|
| **Factory**  | `PlanoFactory`                           | Criar objetos de plano sem expor a lógica de criação  |
| **State**    | `EstadoAtivo`, `EstadoSuspenso`, `EstadoCancelado` | Alterar comportamento conforme estado da assinatura |
| **Decorator**| `TelaExtraDecorator`, `Qualidade4KDecorator`, `ModoOfflineDecorator` | Adicionar recursos extras ao plano dinamicamente |
| **Observer** | `Filme` + `UsuarioObserver`              | Notificar usuários sobre novos episódios               |
| **Prototype**| `Playlist.clone()`                       | Clonar playlists com cópia independente dos filmes    |

---

## 🗂️ Estrutura de Pacotes

```
src/
├── Main.java
├── model/
│   ├── Usuario.java
│   ├── Filme.java          ← também atua como Subject (Observer)
│   ├── Observer.java       ← interface Observer
│   └── Assinatura.java     ← usa State e Factory
├── factory/
│   ├── Plano.java          ← interface produto
│   ├── PlanoBasico.java
│   ├── PlanoStandard.java
│   ├── PlanoPremium.java
│   └── PlanoFactory.java   ← Factory Method
├── state/
│   ├── EstadoAssinatura.java  ← interface State
│   ├── ContextoAssinatura.java
│   ├── EstadoAtivo.java
│   ├── EstadoSuspenso.java
│   └── EstadoCancelado.java
├── decorator/
│   ├── PlanoDecorator.java    ← Decorator abstrato
│   ├── TelaExtraDecorator.java
│   ├── Qualidade4KDecorator.java
│   └── ModoOfflineDecorator.java
├── observer/
│   └── UsuarioObserver.java   ← Observer concreto
├── prototype/
│   └── Playlist.java          ← Cloneable (Prototype)
└── menu/
    ├── Repositorio.java        ← banco em memória (Singleton)
    └── MenuPrincipal.java      ← interface de terminal
```

---

## ▶️ Como Compilar e Executar

### Pré-requisitos
- Java JDK 17 ou superior

### Compilar

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
```

### Executar

```bash
java -cp out src.Main
```

---

## 🧭 Funcionalidades do Menu

```
1. 👤 Usuários       → Cadastrar e listar usuários
2. 🎬 Filmes/Séries  → Cadastrar e listar conteúdo
3. 📋 Assinaturas    → Criar (Factory), mudar estado (State), adicionar extras (Decorator)
4. 🎵 Playlists      → Criar, adicionar filmes, clonar (Prototype)
5. 🔔 Notificações   → Inscrever em série, lançar episódio (Observer)
```

---

## 🧩 Detalhamento dos Padrões

### 1. Factory Method — Criação de Planos

**Problema:** Criar o plano correto (Básico, Standard, Premium) sem poluir o código do cliente com `new PlanoBasico()`, `new PlanoPremium()`, etc.

**Solução:** `PlanoFactory.criarPlano(tipo)` encapsula a criação.

```java
// Client code — não conhece as classes concretas
Plano plano = PlanoFactory.criarPlano("premium");
```

> 📖 **Referência:** Aula do Prof. Álvaro Magnus — *Factory Method: o criador de objetos sem comprometer o princípio aberto/fechado.*

---

### 2. State — Estado da Assinatura

**Problema:** O comportamento ao ativar/suspender/cancelar muda conforme o estado atual. Sem State, teríamos `if/else` gigante.

**Solução:** Cada estado (`EstadoAtivo`, `EstadoSuspenso`, `EstadoCancelado`) implementa `EstadoAssinatura` e delega as transições.

```
ATIVA   →  suspender()  →  SUSPENSA
SUSPENSA → ativar()     →  ATIVA
ATIVA   →  cancelar()   →  CANCELADA
CANCELADA → ativar()    →  ATIVA
```

> 📖 **Referência:** Aula do Prof. Álvaro Magnus — *State: elimine condicionais com polimorfismo de estado.*

---

### 3. Decorator — Recursos Extras no Plano

**Problema:** Adicionar combinações de recursos (4K, tela extra, offline) sem criar uma subclasse para cada combinação.

**Solução:** Cada Decorator envolve um `Plano` e ajusta preço/capacidades.

```java
Plano plano = PlanoFactory.criarPlano("basico");        // R$ 18,90
plano = new TelaExtraDecorator(plano);                  // + R$  9,90
plano = new Qualidade4KDecorator(plano);                // + R$ 14,90
// Resultado: Básico + Tela Extra + 4K Ultra HD — R$ 43,70
```

> 📖 **Referência:** Aula do Prof. Álvaro Magnus — *Decorator: adicione responsabilidades a objetos de forma dinâmica.*

---

### 4. Observer — Notificação de Novos Episódios

**Problema:** Quando uma série lança episódio, todos os usuários inscritos devem ser notificados sem acoplamento direto.

**Solução:** `Filme` mantém lista de `Observer`; `UsuarioObserver` implementa `atualizar()`.

```java
Filme serie = repo.buscarFilmePorId(1);
serie.adicionarObserver(new UsuarioObserver(usuario));
serie.lancarNovoEpisodio("S02E01 - O Retorno");
// → "🔔 Notificação para João: Novo episódio disponível: S02E01..."
```

> 📖 **Referência:** Aula do Prof. Álvaro Magnus — *Observer: defina dependências 1-N para propagação automática de eventos.*

---

### 5. Prototype — Clonagem de Playlists

**Problema:** Copiar uma playlist inteira (com todos os filmes) de forma eficiente, sem recriar manualmente cada item.

**Solução:** `Playlist` implementa `Cloneable` e sobrescreve `clone()` para cópia profunda.

```java
Playlist original = repo.buscarPlaylistPorId(1);
Playlist copia = original.clone();
// "Cópia de Minha Playlist" — independente da original
```

> 📖 **Referência:** Aula do Prof. Álvaro Magnus — *Prototype: clone objetos sem depender de suas classes concretas.*


## 📖 Referências

- **GAMMA, E. et al.** *Design Patterns: Elements of Reusable Object-Oriented Software.* Addison-Wesley, 1994. (Gang of Four)
- **FREEMAN, E.; ROBSON, E.** *Use a Cabeça! Padrões de Projetos.* O'Reilly / Alta Books, 2009.
- **Aulas do Prof. Álvaro Magnus — Padrões de Projeto:**
  - Aula: *Factory Method — criação de objetos com baixo acoplamento*
  - Aula: *State — polimorfismo no lugar de condicionais de estado*
  - Aula: *Decorator — extensão de comportamento sem herança*
  - Aula: *Observer — comunicação desacoplada entre objetos*
  - Aula: *Prototype — clonagem eficiente de objetos complexos*
- **Refactoring.Guru** — https://refactoring.guru/pt-br/design-patterns

---

## 👨‍💻 Equipe

| Nome | Função |
|------|--------|
|      | Desenvolvimento |
|      | Desenvolvimento |

---

*Projeto desenvolvido para a disciplina de Padrões de Projeto — Prof. Álvaro Magnus*
