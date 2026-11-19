# FacuAlerta - aplicación de consola corregida

## Menú principal (roles)

Al abrir el programa se elige uno de estos tres roles:

1. **Usuario**: comprende tanto a **Estudiante** como a **Trabajador**. Ambos comparten el mismo menú. El tipo de perfil se pide únicamente al crear la cuenta.
2. **Gestor de riesgo**: inicia sesión con una cuenta creada por el moderador.
3. **Moderador**: inicia sesión con sus credenciales.

Cada rol debe iniciar sesión con su correo `@uce.edu.ec` y contraseña. Se comprueba que la cuenta pertenezca al rol elegido y esté habilitada. No se aceptan cuentas de otros dominios.

### Menú Usuario
- **Gestionar incidentes**: añadir, eliminar, modificar y buscar reportes; adjuntar fotografía o vídeo; buscar evidencia.
- **Consultar recomendaciones**.

Los usuarios comunes solo pueden consultar y modificar sus propios incidentes.

### Menú Moderador
- **Gestionar usuarios**: añadir, deshabilitar, buscar y modificar.
- **Gestionar incidentes**: añadir, eliminar, modificar y buscar; adjuntar evidencias.
- **Enviar alerta del incidente**.
- **Buscar facultad**.
- **Gestionar recomendaciones**: añadir, modificar, eliminar y buscar por contenido.

### Menú Gestor de riesgo
- **Gestionar incidentes**: añadir, eliminar, modificar y buscar; adjuntar evidencias y actualizar la facultad/zona.
- **Gestionar categorías**: crear, eliminar y buscar.
- **Gestionar facultades**: añadir y buscar.
- **Buscar recomendaciones** por contenido.

## Corrección de categoría duplicada

Al registrar un incidente se elige **una sola vez** la categoría. Las categorías iniciales son **Bajo, Medio, Alto y Crítico**; esa elección determina automáticamente el nivel de gravedad almacenado en el incidente, sin solicitarlo otra vez.

Si el gestor crea una categoría distinta, le asigna un nivel de gravedad **al crear la categoría**. En futuros reportes, el usuario solo elegirá dicha categoría. No se puede borrar una categoría que ya esté asociada a un incidente.

## Credenciales iniciales para probar

**Moderador**

- Correo: `moderador@uce.edu.ec`
- Contraseña: `Facu2026`

El moderador puede crear cuentas del Gestor de Riesgo. Los Estudiantes y Trabajadores pueden crear sus propias cuentas en el acceso a Usuario. Las contraseñas del ejemplo son **solo para demostración**, no para un sistema real en producción.

## Ejecución

Es un proyecto **Java 21 + Maven**, pensado para consola (incluye `pom.xml`, código en `src/main/java` y pruebas en `src/test/java`).

Desde el directorio del proyecto:

```bash
mvn test
mvn compile
java -cp target/classes uce.edu.ec.dominio.FacuAlerta
```

También puede abrirse como proyecto Maven en NetBeans.

## Notas

- Los datos se guardan **en memoria** durante la ejecución: cuentas nuevas, reportes, alertas, categorías y recomendaciones se pierden cuando se cierra el programa.
- Se conservó la estructura original de paquetes y las clases de dominio.
- El código Java se compiló con Java 21 y las 104 pruebas incluidas se ejecutaron satisfactoriamente en una comprobación local. El comando `mvn test` no se ejecutó en ese entorno porque Maven no estaba instalado.