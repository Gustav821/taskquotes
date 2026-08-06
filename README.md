# TaskQuotes

Aplicación Android de demostración que integra **Kotlin**, **Jetpack Compose**,
**Firebase** (Authentication + Firestore), **Room Database**, una **API REST**
pública (Retrofit) y control de versiones con **Git/GitHub**.

## ¿Qué hace la app?

1. **Login** (Firebase Authentication: correo/contraseña) o "Continuar sin cuenta"
   para probarla sin configurar Firebase.
2. **Mis Tareas**: lista de tareas persistida localmente con **Room** (funciona
   sin internet). Un botón "Sincronizar" sube las tareas pendientes a
   **Cloud Firestore** cuando hay sesión iniciada.
3. **Trivia**: consume la API REST pública `https://opentdb.com/api.php`
   (Open Trivia Database) mediante **Retrofit**, y guarda el resultado en Room
   como caché offline (patrón offline-first: la UI siempre observa la base de
   datos local).

## Tecnologías y dónde están

| Requisito        | Dónde se implementa |
|-------------------|----------------------|
| Kotlin            | Todo el código fuente (`app/src/main/java`) |
| Jetpack Compose   | `ui/screens/*`, `ui/theme/*`, `MainActivity.kt` |
| Firebase          | `data/repository/AuthRepository.kt`, `data/repository/TaskRepository.kt` |
| Room Database     | `data/local/*` (Entity, DAO, Database) |
| API REST          | `data/remote/*` (Retrofit) |
| GitHub            | Este repositorio Git (ver sección "Subir a GitHub") |

## Cómo compilar (Android Studio)

1. Descomprime el proyecto y ábrelo con **Android Studio** (`File > Open`,
   selecciona la carpeta `TaskQuotes`).
2. El proyecto no incluye el Gradle Wrapper. Android Studio lo detectará y
   ofrecerá crearlo automáticamente ("Create Gradle Wrapper") — acepta. Si no
   aparece el aviso, ve a `File > Sync Project with Gradle Files`.
3. Espera a que termine el "Gradle Sync" (descarga dependencias, requiere
   internet la primera vez).
4. Presiona **Run ▶** con un emulador o dispositivo conectado.

La app compila y corre en modo local/invitado **sin necesidad de Firebase**.

## Cómo activar Firebase (opcional, para login y sincronización reales)

1. Ve a [https://console.firebase.google.com](https://console.firebase.google.com)
   y crea un proyecto.
2. Agrega una app Android con el `applicationId`: `com.example.taskquotes`.
3. Descarga el archivo `google-services.json` y colócalo en la carpeta `app/`
   (junto a `app/build.gradle.kts`).
4. En la consola de Firebase, habilita **Authentication > Correo/contraseña**
   y crea una base de datos de **Cloud Firestore** (modo prueba).
5. Vuelve a Android Studio y haz `Sync Project with Gradle Files`.

`google-services.json` está en `.gitignore` a propósito (contiene claves de tu
proyecto): cada quien usa el suyo, no se comparte en el repositorio.

## Subir el repositorio a GitHub

El proyecto ya incluye un repositorio Git local (carpeta `.git`) con el
commit inicial. Para publicarlo en tu cuenta de GitHub:

```bash
cd TaskQuotes
git remote add origin https://github.com/Gustav821/taskquotes.git
git branch -M main
git push -u origin main
```

(Antes crea el repositorio vacío en GitHub, sin README ni licencia, para
evitar conflictos con el commit local.)

## Estructura del proyecto

```
app/src/main/java/com/example/taskquotes/
├── data/
│   ├── local/         Room: Entity, DAO, Database
│   ├── remote/         Retrofit: modelos y servicio de la API REST
│   └── repository/     Repositorios (Auth, Task, Trivia)
├── navigation/          Rutas y NavHost de Jetpack Compose
├── ui/
│   ├── screens/         Pantallas Compose (Login, Tareas, Agregar, Trivia)
│   └── theme/           Tema Material3
├── viewmodel/           ViewModels (MVVM)
├── util/                Utilidades (Resource<T>)
├── MainActivity.kt
└── TaskQuotesApp.kt     Application: inicializa Firebase de forma segura
```

## Reporte técnico

Ver `Reporte_Tecnico.docx` en la raíz del proyecto para el detalle de la
arquitectura, decisiones técnicas y cómo se cumple cada requisito.
