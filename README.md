# Coffee Machine 2.0

Simulador de máquina de café com API REST em Quarkus e interface em Angular. O projeto adapta as regras de uma aplicação Java de terminal para uma interface web: é possível comprar bebidas, abastecer ingredientes, consultar o estoque e retirar o dinheiro do caixa.

## Tecnologias

- Java 21 e Quarkus 3.27 no backend
- Angular 22, TypeScript e CSS no frontend
- Maven e npm para desenvolvimento e build

## Estrutura

```text
coffee-backend/
  src/main/java/machine/      Regras da máquina e endpoints REST
  src/main/resources/          Configuração da porta e CORS
  src/test/java/machine/       Testes das regras da máquina
coffee-frontend/
  src/app/                    Interface e chamadas à API
```

O estoque fica em memória no backend. Ao reiniciar o Quarkus, ele volta aos valores iniciais: 400 ml de água, 540 ml de leite, 120 g de grãos, 9 copos e $550 no caixa.

## Como executar

Tenha um JDK 21 ou superior, Maven, Node.js e npm instalados. Abra dois terminais na raiz do projeto.

**Terminal 1 — backend:**

```powershell
cd coffee-backend
mvn quarkus:dev
```

A API ficará disponível em `http://localhost:8080`. Este projeto usa o Maven instalado; não inclui `mvnw`.

**Terminal 2 — frontend:**

```powershell
cd coffee-frontend
npm ci
npm start
```

Abra `http://localhost:4200`. O frontend chama a API em `http://localhost:8080/api/machine`, e o backend permite essa origem via CORS.

## Bebidas

| Tipo | Bebida | Água | Leite | Grãos | Preço |
| --- | --- | ---: | ---: | ---: | ---: |
| 1 | Espresso | 250 ml | 0 ml | 16 g | $4 |
| 2 | Latte | 350 ml | 75 ml | 20 g | $7 |
| 3 | Cappuccino | 200 ml | 100 ml | 12 g | $6 |

Cada compra também consome um copo. Se faltar algum ingrediente, a compra não altera o estoque nem o caixa.

## API

| Método | Rota | Ação |
| --- | --- | --- |
| `GET` | `/api/machine/remaining` | Retorna o estoque e o dinheiro |
| `POST` | `/api/machine/buy/{type}` | Compra a bebida 1, 2 ou 3 |
| `POST` | `/api/machine/fill` | Adiciona água, leite, grãos e copos |
| `POST` | `/api/machine/take` | Retira todo o dinheiro do caixa |

O corpo de `fill` é um JSON com `water`, `milk`, `coffeeBeans` e `cups`, por exemplo:

```json
{"water": 100, "milk": 50, "coffeeBeans": 20, "cups": 2}
```

As ações `buy`, `fill` e `take` respondem com uma mensagem e o estoque atualizado. `remaining` retorna somente o estoque.

## Verificação

```powershell
cd coffee-backend
mvn test
```

```powershell
cd coffee-frontend
npm run build
```
