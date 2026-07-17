# Desafio de Projeto DIO: Explorando Padrões de Projeto com Spring Boot

Este repositório foi desenvolvido para o **Desafio de Projeto** da [Digital Innovation One (DIO)](https://www.dio.me/). O objetivo principal é aplicar na prática os conceitos de **Padrões de Projeto (Design Patterns)** utilizando o ecossistema Spring Framework.

O projeto consiste em uma API RESTful voltada para o gerenciamento de uma clínica veterinária ou pet shop, estabelecendo uma relação bidirecional estável e bem modelada entre as entidades de **Clientes** e seus respectivos **Animais (Pets)**.

---

## 🛠️ Tecnologias e Ferramentas Utilizadas

- **Java 17+**
- **Spring Boot 3+** (Web, Data JPA)
- **Banco de Dados H2 / PostgreSQL** (via Hibernate/JPA)
- **Lombok** (para redução de código boilerplate)
- **Jakarta Persistence** (anotações de mapeamento objeto-relacional)
- **Swagger/OpenAPI** (para documentação interativa da API)

---

## 📂 Estrutura do Projeto

O projeto segue a arquitetura em camadas padrão do Spring Boot, facilitando a legibilidade e a manutenção de padrões de projeto bem consolidados, como o **Service** (para regras de negócio) e o **Repository** (padrão *Data Access Object / Repository*):

```text
src/main/java/one/digitalinnovation
├── controllers     # Camada de exposição dos Endpoints RESTful
├── entities        # Modelagem de dados e mapeamento O.R.M.
│   ├── Animal.java
│   └── Cliente.java
├── enums           # Enumeradores do sistema
│   └── TipoAnimal.java
├── exceptions      # Tratamento global de erros da API
├── repositories    # Interfaces de comunicação com o Banco de Dados (Spring Data)
└── services        # Camada de lógica de negócio (Implementações de padrões de serviço)
    ├── AnimalService.java
    └── ClienteService.java
```

---

## 🧩 Soluções de Design Patterns & Boas Práticas

### 1. Evitando Recursão Infinita (Ciclos JSON)
Ao trabalhar com relacionamentos bidirecionais (`@OneToMany` e `@ManyToOne`) no Spring Boot, o conversor de JSON padrão (**Jackson**) pode entrar em loops indefinidos que causam falhas do tipo `StackOverflowError`. 

Para mitigar este problema, foi adotado o uso estratégico da anotação `@JsonIgnore` diretamente na entidade raiz ou dependente, garantindo que as requisições via Swagger consigam mapear a árvore de objetos de forma linear e limpa.

### 2. Mapeamento das Entidades

#### **Classe Cliente (Dono)**
Gerencia os dados cadastrais do cliente e possui um ciclo em cascata (`CascadeType.ALL`) para automatizar ações na lista de animais dependentes:

```java
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    private String endereco;
    private String cidade;
    private String cep;

    @JsonIgnore // Evita loops infinitos na serialização bi-direcional
    @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animais = new ArrayList<>();
}
```

#### **Classe Animal (Pet)**
Contém as propriedades do animal e utiliza um relacionamento obrigatório que aponta para o seu respectivo tutor:

```java
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String raca;
    private TipoAnimal tipoAnimal;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente dono;
}
```

---

## 🚀 Como Executar o Projeto

1. Clone este repositório para a sua máquina local:
   ```bash
   git clone https://github.com/juniorn06/desafio-projeto-dio.git
   ```
2. Abra a pasta do projeto no seu editor ou IDE de preferência (IntelliJ IDEA, Eclipse, VS Code).
3. Aguarde o download das dependências do Maven descritas no arquivo `pom.xml`.
4. Execute o arquivo `PadroesProjetoSpringApplication.java`.
5. Acesse a interface do Swagger no navegador para testar os endpoints expostos:
   ```text
   http://localhost:8080/swagger-ui/index.html
   ```

---

## 🎓 Aprendizados Obtidos

Através deste desafio prático proposto pela DIO, foi possível consolidar habilidades fundamentais de desenvolvimento corporativo em Java:
- Configuração de ciclo de vida e inversão de controle gerenciado pelo Spring Container.
- Manipulação correta e tratamentos de dependências cíclicas em bancos relacionais com o Hibernate.
- Criação de APIs robustas usando boas práticas arquiteturais contemporâneas.
