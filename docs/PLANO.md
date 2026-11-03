# Plano — Opção 2: Aplicativo de monitoramento ambiental (Projetos Muda e PSA)

**Grupo:** Luis Otávio, Ariel Almeida e Artur Becker
**Tecnologia escolhida:** Java (Android nativo), Android Studio
**Base:** `Requisitos_Tres_Opcoes_Apps_Android.pdf`, requisitos AM-01 a AM-12 e critérios de aceite

---

## 1. Escopo

O app é o cliente Android. O backend, o banco central, o portal web, a geração central de relatórios e a infraestrutura de nuvem ficam fora deste trabalho.

Entregas: código-fonte compilável, instruções de build, APK para testes (e AAB, se for o caso), documentação de telas, fluxos, endpoints e permissões, e testes básicos.

## 2. Requisitos → implementação (Java)

| Req. | Tema | Solução sugerida |
|---|---|---|
| AM-01 | Login e perfis (admin, equipe técnica, empresa) | Login com JWT; token guardado em `EncryptedSharedPreferences`; perfil define menus e dados visíveis |
| AM-02 | Projetos Muda e PSA, propriedades, áreas, etapas | Telas de lista e detalhe consumindo a API |
| AM-03 | Formulários de campo configuráveis | Renderização dinâmica de campos (texto, número, data, seleção) a partir do JSON do formulário; validação de obrigatórios |
| AM-04 | Coleta offline e sincronização | Room (SQLite) como fila local com status (`PENDENTE`, `ENVIANDO`, `SINCRONIZADO`, `CONFLITO`); `WorkManager` para sincronizar com a rede; UUID local por registro para evitar duplicação |
| AM-05 | GPS e fotos georreferenciadas | `FusedLocationProviderClient`; captura de foto com `CameraX` ou Intent de câmera; foto ligada ao ponto e ao projeto |
| AM-06 | Mapa com propriedades, áreas, pontos e croquis | Google Maps SDK (ou osmdroid, se evitar chave de API); camadas vindas da API |
| AM-07 | Parâmetros e laudos de água, comparação com CONAMA | Lista de análises por ponto; limites recebidos da API (o app só compara e exibe) |
| AM-08 | Dashboards e indicadores | Cards com números e gráficos simples (ex.: MPAndroidChart) |
| AM-09 | Relatórios PDF e Excel | Download do arquivo disponibilizado pela API e compartilhamento via `Intent.ACTION_SEND` |
| AM-10 | Conservação de solo (se confirmado) | Módulo opcional, só se o cliente confirmar |
| AM-11 | Histórico e autoria | Tela de histórico com data de alteração e data de sincronização |
| AM-12 | Falhas de rede, permissões, dados locais | Estados de erro e carregamento; pedido de permissão em tempo de execução; dados locais criptografados quando sensíveis |

**Transversais (premissas):** HTTPS, JWT, tratamento de erros, indicadores de carregamento e proteção de credenciais (sem chaves no código; usar `local.properties` ou `BuildConfig`).

## 3. Arquitetura

- **Padrão:** MVVM com `ViewModel` e `LiveData`/`Flow`.
- **Rede:** Retrofit + OkHttp + Gson, com interceptor que adiciona o JWT.
- **Camadas:** `ui` (Activities/Fragments) → `viewmodel` → `repository` (decide entre API e Room) → `data` (`remote` e `local`).
- **Pacote base:** `br.unisc.monitoramento` (ajustar se o grupo preferir).
- **Versão mínima do Android:** a definir (sugestão: API 24).

## 4. Riscos e pontos em aberto

1. **A API da plataforma ambiental não tem URL, documentação ou contratos no PDF.** Sem isso, a integração não pode ser testada de verdade. Solução provisória: camada de dados com interface `ProjetoRepository` e implementação *mock* (JSON local). Trocar pela API real quando o responsável liberar o acesso.
2. **Contrato de sincronização** (identificadores, versionamento, conflitos): o PDF pede que a API ofereça isso. Confirmar com o responsável e, enquanto isso, assumir UUID local + `updatedAt` + regra "servidor vence" e registrar como premissa.
3. **Credenciais de homologação**, perfis de teste e disponibilidade do ambiente de testes.
4. **Chave do Google Maps** (se usar Google Maps SDK).
5. **Identidade visual** e política de privacidade (dados de propriedades e localização são sensíveis).
6. **Dados sigilosos:** definir quais campos são ocultados por perfil.

## 5. Marcos sugeridos (trabalho em grupo de 3)

| Marco | Conteúdo | Responsável sugerido |
|---|---|---|
| M1 | Projeto Android Studio, navegação, tela de login com mock, perfis | Luis |
| M2 | Projetos, áreas, pontos e formulários (lista e detalhe), Retrofit + mock | Ariel |
| M3 | Formulário dinâmico com validação, GPS, câmera | Artur |
| M4 | Room + WorkManager (fila offline e sincronização sem duplicar) | Luis |
| M5 | Mapa, análises de água, dashboards, relatórios (download/compartilhar) | Ariel e Artur |
| M6 | Testes (login, offline, sincronização, falha de rede), documentação e APK | Todos |

Os responsáveis são sugestões, para ajustar entre vocês.

## 6. Critérios de aceite (do PDF)

- [ ] Registrar um formulário com GPS e foto sem conexão e sincronizar após reconectar, sem duplicar.
- [ ] Exibir projetos, áreas, pontos, croquis e análises conforme o perfil do usuário autenticado.
- [ ] Mostrar indicadores por projeto e consultar ou compartilhar relatórios, sem portal web.

## 7. Decisões pendentes (do PDF)

- Versão mínima do Android.
- Política de acesso e privacidade.
- Prazo e orçamento (se houver).
- Responsabilidade por homologação e suporte.
- Quem é o responsável pela API e onde está a documentação.
