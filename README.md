# Jogo de Sudoku em Java

## 📝 Descrição do Projeto

Este é um jogo clássico de Sudoku desenvolvido em Java, com interface gráfica construída utilizando Java Swing. O objetivo do projeto é oferecer uma experiência de jogo interativa, com geração dinâmica de quebra-cabeças, validação de jogadas em tempo real e funcionalidades de persistência de dados.

O projeto foi estruturado seguindo princípios de design de software como Model-View-Controller (MVC) para garantir modularidade, manutenibilidade e escalabilidade.

## ✨ Funcionalidades

* **Geração Dinâmica de Quebra-Cabeças:** Criação de novos jogos de Sudoku com soluções válidas.
* **Seleção de Dificuldade:** O usuário pode escolher entre os níveis Fácil, Médio e Difícil, que influenciam a quantidade de células preenchidas no início do jogo.
* **Validação de Jogadas em Tempo Real:**
    * Verificação instantânea de entrada para garantir que apenas números de 1 a 9 sejam digitados.
    * Feedback visual e mensagens de erro para números inválidos (duplicados na linha, coluna ou bloco 3x3).
    * Células fixas (do quebra-cabeça inicial) são não editáveis e visualmente distintas.
* **Condição de Vitória:** O jogo detecta automaticamente quando o Sudoku é resolvido corretamente e exibe uma mensagem de parabéns.
* **Persistência de Jogo:**
    * **Salvar Partida:** Permite ao jogador salvar o estado atual do jogo, incluindo as células fixas originais e as jogadas feitas pelo usuário.
    * **Carregar Partida:** Possibilita retomar um jogo salvo anteriormente, mantendo o progresso e o status das células fixas.

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java
* **Interface Gráfica:** Java Swing
* **Estrutura de Projeto:** Padrão MVC (Model-View-Controller)

## 📂 Estrutura do Projeto

O projeto segue uma estrutura de pacotes clara para separar as responsabilidades:
```
sudoku-game/
├── src/
│   └── main/
│       └── java/
│           ├── application/
│           │   └── MainScreen.java
│           ├── model/
│           │   ├── Board.java
│           │   ├── Cell.java
│           │   ├── Difficulty.java
│           │   └── SudokuGenerator.java
│           └── view/
│               └── BoardPanel.java
└── README.md
```

## ⚙️ Como Rodar o Projeto

Para executar o jogo de Sudoku em sua máquina local:

1.  **Clone o Repositório:**
    ```bash
    git clone [https://github.com/Gtvnv/sudoku_project.git](https://github.com/Gtvnv/sudoku_project.git)
    cd sudoku_project # O nome da pasta será o nome do repositório
    ```

2.  **Compile e Execute (Se usar IDE - Eclipse/IntelliJ IDEA):**
    * Importe o projeto para sua IDE favorita como um projeto Java existente.
    * Localize a classe `MainScreen.java` (no pacote `application`).
    * Execute o método `main` desta classe.

3.  **Compile e Execute:**
    * Certifique-se de ter o Java Development Kit (JDK) 8 ou superior instalado.
    * Navegue até a pasta `src/main/java`.
    * Compile as classes Java:
        ```bash
        javac application/*.java model/*.java view/*.java
        # Se os pacotes estiverem aninhados (ex: com/seuprojeto/sudoku/...), ajuste o caminho
        ```
    * Execute a aplicação:
        ```bash
        java application.MainScreen
        # ou, se o classpath for complexo:
        # java -cp . application.MainScreen
        ```

## 📈 Melhorias Futuras

* **Verificação de Unicidade da Solução:** Implementar um algoritmo para garantir que cada quebra-cabeça gerado tenha apenas uma solução possível.
* **Funcionalidades de Dica/Resolver:** Adicionar botões para dar dicas ao jogador ou resolver o Sudoku automaticamente.
* **Interface do Usuário (UI) Aprimorada:** Melhorar a estética com ícones, temas visuais, talvez animações.
* **Validação em Tempo Real (Mais Avançada):** Realçar números que *estão* incorretos após uma jogada, em vez de apenas limpar o campo.
* **Contador de Tempo:** Adicionar um cronômetro para medir o tempo de resolução do puzzle.
* **Níveis de Dificuldade Detalhados:** Refinar a lógica de dificuldade para incluir "very easy", "expert", etc.
* **Opções de Jogo:** Botões para reiniciar o jogo atual, começar um novo.

## ✉️ Contato

Para dúvidas ou sugestões, sinta-se à vontade para entrar em contato:

* **Seu Nome:** [Gustavo Ventura]
* **GitHub:** [https://github.com/Gtvnv]
* **LinkedIn:** [https://www.linkedin.com/in/gtvnv]
* **Email:** [gutsman1235@gmail.com]
---
