# FacuAlerta – ajustes para la entrega de POO

## Herencia
`Estudiante`, `Trabajador`, `GestorDeRiesgo` y `Moderador` heredan de `Usuario`.
Los atributos se mantienen `private` y se accede a ellos mediante getters/setters.
Los constructores de las clases hijas invocan `super(...)`.

## toString, equals y hashCode
Las clases del modelo implementan `toString()`, `equals(Object)` y `hashCode()`.
`equals` usa `instanceof` y casting. La igualdad se define por el identificador de cada entidad
(en las clases hijas se consideran el id heredado y el id específico).

## Relaciones
- Asociación: `Incidente` mantiene referencias a `Usuario` y `Categoria`.
- Uno a muchos: `Incidente` contiene evidencias; se conserva la lista para compatibilidad
  y se añaden `getEvidenciasComoArreglo()` y `setEvidencias(Evidencia[])`.
- Asociación uno a uno: `Notificacion` mantiene una referencia a `Incidente`.
- Composición: las evidencias se gestionan desde el incidente como parte de su registro.

## CRUD
`RepositorioCRUD<T>` proporciona crear, consultar, editar y eliminar.
Tiene sobrecargas que reciben referencias de objetos y evita duplicados mediante `equals`.

## Nota sobre constructores
Los constructores sin argumentos inicializan atributos con valores predeterminados.
Los constructores con argumentos asignan los valores recibidos y los constructores de las
subclases llaman a `super(...)`.

## Actualización final (rúbrica)
- Herencia: atributos de `Usuario` ahora `protected`; hijas con `super()` / `this()`, `toString` sobreescrito, `equals` (instanceof + casting) y `hashCode`.
- Uno a muchos con **arreglo de objetos**: `Incidente.evidencias` es `Evidencia[]` (composición), con sobrecargas `agregarEvidencia(Object|Evidencia)`, `buscarEvidencia(Evidencia|int)`, `eliminarEvidencia(Evidencia)` y validación de duplicados.
- Uno a uno con un objeto: `Notificacion` → `Incidente`.
- Asociación (`Incidente`–`Categoria`), agregación (`Incidente`–`Usuario`), composición (`Incidente`–`Evidencia`). Ver `docs/diagrama_clases.puml`.
- CRUD: `SubMenu` usa `RepositorioCRUD<T>` (crear, consultar, editar, eliminar) con validación de duplicados vía `equals`.
- Menús: añadidas opciones **Listar** (incidentes, usuarios, categorías, facultades, recomendaciones, notificaciones) y **Editar categoría**.
- Tests: 120 pruebas JUnit 4 pasan (incluye `RepositorioCRUDTest` y `RelacionesYEqualsTest`).