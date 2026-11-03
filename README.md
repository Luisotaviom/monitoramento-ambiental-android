# Monitoramento Ambiental (Android)

Aplicativo Android nativo em **Java** para registro de atividades e coleta de informações ambientais em campo, com acesso aos projetos **Muda** e **PSA**: propriedades, áreas, pontos de coleta, análises de água e indicadores.

> Trabalho da disciplina Programação para Dispositivos Móveis (UNISC).
> Integrantes: Luis Otávio, Ariel Almeida e Artur Becker.

## Status

🚧 Em desenvolvimento. Hoje o projeto tem:

- [x] Estrutura Gradle (sem Android Studio), com wrapper
- [x] Tela de login com validação e perfis (administrador, equipe técnica, empresa)
- [x] Lista de projetos Muda e PSA (dados mock)
- [x] Teste unitário de validação
- [ ] Formulários de campo dinâmicos com validação
- [ ] GPS e fotos georreferenciadas
- [ ] Persistência offline (Room) e sincronização (WorkManager)
- [ ] Mapa, análises de água, dashboards e relatórios
- [ ] Integração com a API real

O plano completo, com requisitos e marcos, está em [docs/PLANO.md](docs/PLANO.md).

## Tecnologias

- Java 17 (código) · JDK 17 ou superior para o build
- Android: minSdk 24 (Android 7.0), targetSdk 35
- AndroidX, Material Components, RecyclerView, ViewModel/LiveData
- Gradle 8.14.3 (wrapper) com Android Gradle Plugin 8.7.3

## Pré-requisitos

1. **JDK 17+** (testado com Temurin 21)
2. **Android SDK** com:
   - `platform-tools`
   - `platforms;android-35`
   - `build-tools;35.0.0`
3. Variável `ANDROID_HOME` apontando para o SDK (ou o arquivo `local.properties`)

Sem Android Studio, o SDK pode ser instalado pela linha de comando com `sdkmanager` (vem com o pacote *Command line tools*):

```bash
sdkmanager --licenses
sdkmanager "platform-tools" "platforms;android-35" "build-tools;35.0.0"
```

## Como compilar e testar

Na raiz do projeto:

```bash
# Testes unitários
./gradlew test

# APK de depuração (app/build/outputs/apk/debug/app-debug.apk)
./gradlew assembleDebug

# Instalar em um aparelho ou emulador conectado
./gradlew installDebug
```

Para o emulador, crie um AVD pelo `avdmanager` ou use um aparelho físico com depuração USB ativada.

## Estrutura

```
.
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/br/unisc/monitoramento/
│       │   │   ├── data/              # Modelos, interfaces de repositório e implementações mock
│       │   │   ├── ui/login/          # Tela de login
│       │   │   ├── ui/home/           # Lista de projetos
│       │   │   └── util/              # Validações
│       │   └── res/                   # Layouts, strings, cores e temas
│       └── test/                      # Testes unitários
├── docs/
│   └── PLANO.md                       # Escopo, requisitos e marcos
├── gradle/wrapper/                    # Gradle wrapper
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── gradlew / gradlew.bat
```

## Arquitetura

- **Camadas:** `ui` → `data.repository` → (`data.remote` e `data.local`, a implementar).
- **Repositórios** são interfaces (`AuthRepository`, `ProjetoRepository`). Hoje as implementações são *mock*, com a mesma assinatura que a versão com Retrofit e Room terá.
- **Retorno assíncrono** por `Callback<T>`, sem coroutines, para manter o projeto simples.

## Segurança

- Tráfego apenas por HTTPS.
- Autenticação por JWT (a ser ligada à API).
- Nenhuma credencial no código. A URL da API fica em `API_BASE_URL` no `app/build.gradle.kts` e será configurada quando o acesso for liberado.
- `allowBackup` desativado no manifest.

## Pendências

- Definir a API da plataforma ambiental (URL, contratos de autenticação e sincronização).
- Definir a política de privacidade e os campos sigilosos por perfil.
- Chave do Google Maps, se o mapa usar o SDK do Google.
