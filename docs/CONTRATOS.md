# Contratos compartilhados

Estes modelos e interfaces são a base comum do app. Quem usa um contrato deve seguir esta forma, e mudanças precisam ser combinadas com o grupo antes de entrar em `main`.

Pacotes: `data/model` (modelos) e `data/repository` (interfaces e mocks).

## Modelos

| Classe | Campos principais | Observações |
|---|---|---|
| `Projeto` | `id`, `nome`, `tipo` (`MUDA` ou `PSA`) | Já existia |
| `Propriedade` | `id`, `projetoId`, `nome`, `municipio` | AM-02 |
| `Area` | `id`, `propriedadeId`, `nome`, `hectares` | AM-02 |
| `Etapa` | `id`, `projetoId`, `nome`, `inicioPrevisto`, `fimPrevisto` | Datas em texto `AAAA-MM-DD` (evita `java.time` com minSdk 24) |
| `PontoColeta` | `id`, `areaId`, `nome`, `latitude`, `longitude`, `fotoPath` | `fotoPath` pode ser `null` (AM-05) |
| `RegistroCampo` | `uuidLocal`, `pontoId`, `campos`, `latitude`, `longitude`, `fotoPath`, `criadoEm`, `status`, `atualizadoEm`, `sincronizadoEm` | Registro de formulário (AM-03, AM-04) |

### `RegistroCampo`

- `uuidLocal`: chave de idempotência. A API usa para não duplicar o registro, então precisa ser gerado uma vez e nunca trocado.
- `campos`: mapa `identificadorDoCampo -> valor (texto)`. Imutável depois de criado.
- `status`: `PENDENTE` (criado, ainda não enviado), `ENVIANDO`, `SINCRONIZADO`, `CONFLITO`.
- `sincronizadoEm`: `null` até o status virar `SINCRONIZADO`. Quem muda o status usa `mudarStatus(novoStatus, agora)`.

## Interfaces de repositório

| Interface | Métodos | Quem implementa |
|---|---|---|
| `ProjetoRepository` | `listarProjetos`, `listarPropriedades(projetoId)`, `listarAreas(propriedadeId)`, `listarEtapas(projetoId)` | Mock hoje (`MockProjetoRepository`); API depois |
| `PontoColetaRepository` | `listarPontos(areaId)` | Mock (a criar) |
| `RegistroCampoRepository` | `salvar`, `listarTodos`, `listarPorStatus` | Mock em memória hoje; Room e sincronização na Parte 1 |

Todos os métodos recebem um `Callback<T>` (`onSucesso` e `onErro`). Os mocks de projetos usam `Handler` (latência simulada), então não rodam em testes JVM. Os mocks novos de `RegistroCampo` respondem de forma síncrona, para os testes rodarem sem Android.

## Regras

- Não criar modelos paralelos: se precisar de um campo novo, altere este arquivo e avise o grupo.
- Não importar tipos de persistência (Room) ou de rede (Retrofit) nos modelos. Eles ficam em `data/model`, em Java puro.
- Campos que vêm da API seguem o formato dela. Enquanto a API não estiver disponível, os mocks seguem este contrato.
