package uce.edu.ec.dominio.consola;

import uce.edu.ec.dominio.modelo.Categoria;
import uce.edu.ec.dominio.modelo.Evidencia;
import uce.edu.ec.dominio.modelo.Incidente;
import uce.edu.ec.dominio.modelo.Notificacion;
import uce.edu.ec.dominio.modelo.Moderador;
import uce.edu.ec.dominio.modelo.RepositorioCRUD;
import uce.edu.ec.dominio.modelo.Usuario;
import uce.edu.ec.dominio.util.Validador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

//Submenus (nivel 1), uno por caso de uso. Los datos se guardan en memoria.
public class SubMenu {
    private final Lector lector;
    private final IngresoUsuario ingresoUsuario;
    private final IngresoIncidente ingresoIncidente;
    private final IngresoNotificacion ingresoNotificacion;

    // CRUD (crear, consultar, editar, eliminar + validacion de duplicados con equals) via RepositorioCRUD.
    private final RepositorioCRUD<Usuario> usuarios = new RepositorioCRUD<>();
    private final RepositorioCRUD<Categoria> categorias = new RepositorioCRUD<>();
    private final RepositorioCRUD<Incidente> incidentes = new RepositorioCRUD<>();
    private final RepositorioCRUD<Notificacion> notificaciones = new RepositorioCRUD<>();
    private final Set<Integer> usuariosDeshabilitados = new HashSet<>();
    private final List<String> facultades = new ArrayList<>(List.of(Validador.FACULTADES));
    private final Map<Integer, String> recomendaciones = new LinkedHashMap<>();
    // Cada categoria tiene un nivel asociado: no se vuelve a pedir al reportar.
    private final Map<Integer, String> nivelPorCategoria = new LinkedHashMap<>();

    private Usuario sesionActual;

    private int siguienteIdUsuario = 1;
    private int siguienteIdCategoria = 1;
    private int siguienteIdIncidente = 1;
    private int siguienteIdEvidencia = 1;
    private int siguienteIdNotificacion = 1;

    public SubMenu(Lector lector) {
        this.lector = lector;
        this.ingresoUsuario = new IngresoUsuario(lector);
        this.ingresoIncidente = new IngresoIncidente(lector);
        this.ingresoNotificacion = new IngresoNotificacion(lector);

        // Cuenta de moderacion predeterminada para iniciar sesion desde el primer uso.
        usuarios.crear(new Moderador(siguienteIdUsuario++, "Moderador", "moderador@uce.edu.ec",
                "Facu2026", 1, "Validar incidentes, cambiar estados y enviar notificaciones"));

        // Cuatro categorias disponibles desde el inicio para reportar incidentes.
        categorias.crear(new Categoria(siguienteIdCategoria++, "Bajo", "Incidente de riesgo bajo"));
        categorias.crear(new Categoria(siguienteIdCategoria++, "Medio", "Incidente de riesgo medio"));
        categorias.crear(new Categoria(siguienteIdCategoria++, "Alto", "Incidente de riesgo alto"));
        categorias.crear(new Categoria(siguienteIdCategoria++, "Critico", "Incidente de riesgo critico"));
        for (Categoria categoria : categorias.consultar()) {
            nivelPorCategoria.put(categoria.getIdCategoria(), categoria.getNombreCategoria().toUpperCase(java.util.Locale.ROOT));
        }
    }

    public List<Usuario> getUsuarios() {
        return usuarios.consultar();
    }

    public List<Categoria> getCategorias() {
        return categorias.consultar();
    }

    public List<Incidente> getIncidentes() {
        return incidentes.consultar();
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones.consultar();
    }

    // Iniciar/cerrar sesion y separar los permisos de cada rol.
    public Usuario getSesionActual() { return sesionActual; }

    public void iniciarSesion() {
        iniciarSesion(null);
    }

    public boolean iniciarSesion(String rolEsperado) {
        sesionActual = null;
        String correo = lector.leerCorreo("Correo institucional");
        String clave = lector.leerContrasena("Contrasena (letras y numeros)");
        for (Usuario usuario : usuarios.consultar()) {
            boolean rolCorrecto = rolEsperado == null || rolEsperado.equals(usuario.getTipoUsuario())
                    || ("USUARIO".equals(rolEsperado) && ("ESTUDIANTE".equals(usuario.getTipoUsuario())
                    || "TRABAJADOR".equals(usuario.getTipoUsuario())));
            if (rolCorrecto && !usuariosDeshabilitados.contains(usuario.getIdUsuario())
                    && usuario.getCorreo().equalsIgnoreCase(correo)
                    && usuario.getContrasenaHash().equals(clave)) {
                sesionActual = usuario;
                lector.mostrar("Credenciales validadas. Acceso concedido: "
                        + usuario.getNombreUsuario() + " (" + usuario.getTipoUsuario() + ")");
                return true;
            }
        }
        lector.mostrar("Credenciales incorrectas, rol no coincidente o usuario deshabilitado.");
        return false;
    }

    public void ejecutarMenuPorRol(String rol) {
        if ("USUARIO".equals(rol)) {
            lector.mostrar("\n--- ACCESO DE USUARIO (ESTUDIANTE O TRABAJADOR) ---");
            lector.mostrar("1. Iniciar sesion");
            lector.mostrar("2. Crear cuenta");
            lector.mostrar("0. Volver");
            int acceso = lector.leerOpcion("Opcion", 0, 2);
            if (acceso == 0) return;
            if (acceso == 2) {
                // Ambos pertenecen al mismo rol USUARIO; solo cambia el perfil de registro.
                lector.mostrar("1. Estudiante");
                lector.mostrar("2. Trabajador");
                int tipo = lector.leerOpcion("Perfil para el registro", 1, 2);
                Usuario nuevo = tipo == 1
                        ? ingresoUsuario.ingresarEstudiante(siguienteIdUsuario)
                        : ingresoUsuario.ingresarTrabajador(siguienteIdUsuario);
                registrarUsuario(nuevo);
                lector.mostrar("Inicie sesion con su cuenta para continuar.");
            }
        } else if (!"MODERADOR".equals(rol) && !"GESTOR_RIESGO".equals(rol)) {
            lector.mostrar("Rol no reconocido.");
            return;
        }
        if (!iniciarSesion(rol)) return;
        switch (rol) {
            case "USUARIO" -> menuUsuario();
            case "MODERADOR" -> menuModerador();
            case "GESTOR_RIESGO" -> menuGestorRiesgo();
            default -> { }
        }
        cerrarSesion();
    }

    public void cerrarSesion() {
        sesionActual = null;
        lector.mostrar("Sesion cerrada.");
    }

    private boolean autenticado() {
        if (sesionActual != null) return true;
        lector.mostrar("Primero debe iniciar sesion.");
        return false;
    }

    private boolean esModerador() {
        if (!autenticado()) return false;
        if ("MODERADOR".equals(sesionActual.getTipoUsuario())) return true;
        lector.mostrar("Operacion exclusiva del moderador.");
        return false;
    }

    public void menuUsuarios() {
        int opcion;
        do {
            boolean moderador = sesionActual != null && "MODERADOR".equals(sesionActual.getTipoUsuario());
            boolean inicio = usuarios.consultar().stream().noneMatch(u -> "MODERADOR".equals(u.getTipoUsuario()));
            lector.mostrar("\n--- GESTION DE USUARIOS ---");
            lector.mostrar("1. Registrar estudiante");
            lector.mostrar("2. Registrar trabajador");
            if (moderador || inicio) lector.mostrar("3. Registrar moderador" + (inicio ? " (configuracion inicial)" : ""));
            if (moderador) {
                lector.mostrar("4. Registrar gestor de riesgo");
                lector.mostrar("5. Listar usuarios");
            }
            lector.mostrar("0. Volver");
            opcion = lector.leerOpcion("Opcion", 0, 5);
            switch (opcion) {
                case 1 -> registrarUsuario(ingresoUsuario.ingresarEstudiante(siguienteIdUsuario));
                case 2 -> registrarUsuario(ingresoUsuario.ingresarTrabajador(siguienteIdUsuario));
                case 3 -> {
                    if (moderador || inicio) registrarUsuario(ingresoUsuario.ingresarModerador(siguienteIdUsuario));
                    else lector.mostrar("No tiene permisos para registrar moderadores.");
                }
                case 4 -> { if (esModerador()) registrarUsuario(ingresoUsuario.ingresarGestorDeRiesgo(siguienteIdUsuario)); }
                case 5 -> { if (esModerador()) listarUsuarios(); }
                default -> { }
            }
        } while (opcion != 0);
    }

    private void listarUsuarios() {
        if (usuarios.cantidad() == 0) { lector.mostrar("No hay usuarios registrados."); return; }
        for (Usuario usuario : usuarios.consultar()) lector.mostrar("  ID " + usuario.getIdUsuario() + " - " + usuario.getNombreUsuario() + " (" + usuario.getTipoUsuario() + ")");
    }

    private void registrarUsuario(Usuario usuario) {
        for (Usuario existente : usuarios.consultar()) {
            if (existente.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
                lector.mostrar("Ya existe un usuario con ese correo. No se registro.");
                return;
            }
        }
        if (!usuarios.crear(usuario)) {
            lector.mostrar("Ya existe un usuario con ese id. No se registro.");
            return;
        }
        siguienteIdUsuario++;
        lector.mostrar("Usuario registrado con id " + usuario.getIdUsuario() + ".");
    }

    // ------------------------------------------------------------------
    // Caso de uso: Gestionar categorias
    // ------------------------------------------------------------------
    public void menuCategorias() {
        int opcion;
        do {
            lector.mostrar("\n--- CATEGORIAS ---");
            lector.mostrar("1. Registrar categoria");
            lector.mostrar("2. Listar categorias");
            lector.mostrar("0. Volver");
            opcion = lector.leerOpcion("Opcion", 0, 2);
            if (opcion == 1) {
                crearCategoria();
            } else if (opcion == 2) {
                listar(categorias.consultar(), "No hay categorias registradas.");
            }
        } while (opcion != 0);
    }

    // ------------------------------------------------------------------
    // Caso de uso: Reportar y gestionar incidentes
    // ------------------------------------------------------------------
    public void menuIncidentes() {
        int opcion;
        do {
            lector.mostrar("\n--- INCIDENTES ---");
            lector.mostrar("1. Reportar incidente");
            lector.mostrar("2. Agregar evidencia a un incidente");
            lector.mostrar("3. Cambiar estado de un incidente");
            lector.mostrar("4. Listar incidentes");
            lector.mostrar("0. Volver");
            opcion = lector.leerOpcion("Opcion", 0, 4);
            switch (opcion) {
                case 1 -> reportarIncidente();
                case 2 -> agregarEvidencia();
                case 3 -> { if (esModerador()) cambiarEstado(); }
                case 4 -> listarIncidentesVisibles();
                default -> {
                }
            }
        } while (opcion != 0);
    }

    private void reportarIncidente() {
        if (!autenticado()) return;
        if (categorias.cantidad() == 0) {
            lector.mostrar("No hay categorias disponibles. Solicite al gestor de riesgo que registre una.");
            return;
        }
        Categoria categoria = elegirCategoria();
        String facultad = elegirFacultadRegistrada("Facultad donde ocurrio el incidente");
        String nivelGravedad = nivelPorCategoria.get(categoria.getIdCategoria());
        if (nivelGravedad == null) {
            lector.mostrar("La categoria no tiene nivel de gravedad configurado.");
            return;
        }
        Incidente incidente = ingresoIncidente.ingresarIncidente(
                siguienteIdIncidente, sesionActual, categoria, facultad, nivelGravedad);
        Evidencia evidencia = ingresoIncidente.ingresarEvidencia(siguienteIdEvidencia);
        incidente.agregarEvidencia(evidencia);
        siguienteIdEvidencia++;
        siguienteIdIncidente++;
        if (!incidentes.crear(incidente)) {
            lector.mostrar("Ya existe un incidente con ese id. No se registro.");
            return;
        }
        lector.mostrar("Incidente reportado con id " + incidente.getIdIncidente() + ".");
    }

    private void agregarEvidencia() {
        if (!autenticado()) return;
        Incidente incidente = elegirIncidente();
        if (incidente == null) {
            return;
        }
        Evidencia evidencia = ingresoIncidente.ingresarEvidencia(siguienteIdEvidencia++);
        incidente.agregarEvidencia(evidencia);
        lector.mostrar("Evidencia agregada al incidente " + incidente.getIdIncidente() + ".");
    }

    private void cambiarEstado() {
        Incidente incidente = elegirIncidente();
        if (incidente == null) {
            return;
        }
        String nuevoEstado = lector.leerDeLista("Nuevo estado", Validador.ESTADOS_INCIDENTE);
        try {
            incidente.setEstado(nuevoEstado);
            incidente.setFechaHoraActualizacion(
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            lector.mostrar("Estado actualizado.");
        } catch (IllegalArgumentException e) {
            lector.mostrar("No se pudo cambiar el estado: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // Caso de uso: Notificaciones
    // ------------------------------------------------------------------
    public void menuNotificaciones() {
        if (!autenticado()) return;
        int opcion;
        do {
            lector.mostrar("\n--- NOTIFICACIONES ---");
            if ("MODERADOR".equals(sesionActual.getTipoUsuario())) lector.mostrar("1. Enviar notificacion");
            lector.mostrar("2. Listar notificaciones");
            lector.mostrar("0. Volver");
            opcion = lector.leerOpcion("Opcion", 0, 2);
            switch (opcion) {
                case 1 -> { if (esModerador()) enviarNotificacion(); }
                case 2 -> listarNotificacionesVisibles();
                default -> { }
            }
        } while (opcion != 0);
    }

    private void enviarNotificacion() {
        if (!esModerador()) return;
        Incidente incidente = elegirIncidente();
        if (incidente == null) return;
        Notificacion notificacion = ingresoNotificacion.ingresarNotificacion("N" + siguienteIdNotificacion, incidente);
        // No enviar a destinatarios inexistentes, ni exponer datos privados.
        String destinos = notificacion.getDestinatarioCorreo();
        if (!"@Todos".equalsIgnoreCase(destinos)) {
            for (String correo : destinos.split(",")) {
                boolean existe = usuarios.consultar().stream().anyMatch(u -> u.getCorreo().equalsIgnoreCase(correo.trim()));
                if (!existe) { lector.mostrar("Destinatario no registrado: " + correo.trim()); return; }
            }
        }
        siguienteIdNotificacion++;
        if (!notificaciones.crear(notificacion)) { lector.mostrar("Notificacion duplicada."); return; }
        lector.mostrar("Notificacion " + notificacion.getIdNotificacion() + " registrada para " + destinos + ".");
    }

    private void listarNotificacionesVisibles() {
        boolean moderador = "MODERADOR".equals(sesionActual.getTipoUsuario());
        int vistas = 0;
        for (Notificacion n : notificaciones.consultar()) {
            boolean destinatario = "@Todos".equalsIgnoreCase(n.getDestinatarioCorreo());
            for (String correo : n.getDestinatarioCorreo().split(","))
                if (correo.trim().equalsIgnoreCase(sesionActual.getCorreo())) destinatario = true;
            if (moderador || destinatario) {
                lector.mostrar("  " + n.getIdNotificacion() + " - " + n.getTitulo()
                        + " - Incidente #" + n.getIncidente().getIdIncidente()
                        + " (" + n.getIncidente().getTitulo() + ")");
                vistas++;
            }
        }
        if (vistas == 0) lector.mostrar("No hay notificaciones.");
    }

    // ------------------------------------------------------------------
    // Utilitarios
    // ------------------------------------------------------------------
    private Categoria elegirCategoria() {
        for (Categoria c : categorias.consultar()) lector.mostrar("  ID " + c.getIdCategoria() + " - " + c.getNombreCategoria());
        while (true) {
            int id = lector.leerEntero("Id de la categoria");
            for (Categoria categoria : categorias.consultar()) {
                if (categoria.getIdCategoria() == id) {
                    return categoria;
                }
            }
            lector.mostrar("No existe una categoria con ese id.");
        }
    }

    private void listarIncidentesVisibles() {
        if (!autenticado()) return;
        int vistos = 0;
        for (Incidente i : incidentes.consultar()) {
            if (puedeVerTodosLosIncidentes() ||
                    i.getUsuarioReporta().getIdUsuario() == sesionActual.getIdUsuario()) {
                lector.mostrar("  ID " + i.getIdIncidente() + " - " + i.getTitulo() + " - " + i.getEstado()
                        + " - " + i.getFacultad() + " - evidencias: " + i.getTotalEvidencias());
                vistos++;
            }
        }
        if (vistos == 0) lector.mostrar("No hay incidentes visibles.");
    }

    private Incidente elegirIncidente() {
        if (!autenticado()) return null;
        if (incidentes.consultar().stream().noneMatch(i -> puedeVerTodosLosIncidentes()
                || i.getUsuarioReporta().getIdUsuario() == sesionActual.getIdUsuario())) return null;
        int id = lector.leerEntero("Id del incidente");
        for (Incidente incidente : incidentes.consultar()) {
            if (incidente.getIdIncidente() == id && (puedeVerTodosLosIncidentes()
                    || incidente.getUsuarioReporta().getIdUsuario() == sesionActual.getIdUsuario())) return incidente;
        }
        lector.mostrar("No existe un incidente accesible con ese id.");
        return null;
    }

    private void listar(List<?> lista, String mensajeVacio) {
        if (lista.isEmpty() && !mensajeVacio.isEmpty()) {
            lector.mostrar(mensajeVacio);
            return;
        }
        for (Object elemento : lista) {
            lector.mostrar("  " + elemento);
        }
    }

    // ======================== MENUS POR ROL ========================
    private boolean esGestorRiesgo() {
        if (!autenticado()) return false;
        if ("GESTOR_RIESGO".equals(sesionActual.getTipoUsuario())) return true;
        lector.mostrar("Operacion exclusiva del gestor de riesgo.");
        return false;
    }

    private boolean puedeVerTodosLosIncidentes() {
        return sesionActual != null && ("MODERADOR".equals(sesionActual.getTipoUsuario())
                || "GESTOR_RIESGO".equals(sesionActual.getTipoUsuario()));
    }

    private boolean puedeAdministrarIncidentes() {
        if (!autenticado()) return false;
        if (puedeVerTodosLosIncidentes()) return true;
        lector.mostrar("Operacion exclusiva del moderador o gestor de riesgo.");
        return false;
    }

    // Los tres menus principales agrupan acciones para no sobrecargar la pantalla.
    private void menuUsuario() {
        int op;
        do {
            lector.mostrar("\n===== MENU USUARIO =====");
            lector.mostrar("1. Gestionar incidentes");
            lector.mostrar("2. Consultar recomendaciones");
            lector.mostrar("3. Listar mis notificaciones");
            lector.mostrar("0. Cerrar sesion");
            op = lector.leerOpcion("Opcion", 0, 3);
            switch (op) {
                case 1 -> gestionarIncidentes(false);
                case 2 -> listarRecomendaciones();
                case 3 -> listarNotificacionesVisibles();
                default -> { }
            }
        } while (op != 0);
    }

    private void menuModerador() {
        int op;
        do {
            lector.mostrar("\n===== MENU MODERADOR =====");
            lector.mostrar("1. Gestionar usuarios");
            lector.mostrar("2. Gestionar incidentes");
            lector.mostrar("3. Enviar alerta del incidente");
            lector.mostrar("4. Buscar facultad");
            lector.mostrar("5. Gestionar recomendaciones");
            lector.mostrar("6. Listar notificaciones enviadas");
            lector.mostrar("0. Cerrar sesion");
            op = lector.leerOpcion("Opcion", 0, 6);
            switch (op) {
                case 1 -> gestionarUsuariosModerador();
                case 2 -> gestionarIncidentes(false);
                case 3 -> enviarNotificacion();
                case 4 -> buscarFacultad();
                case 5 -> gestionarRecomendacionesModerador();
                case 6 -> listarNotificacionesVisibles();
                default -> { }
            }
        } while (op != 0);
    }

    private void menuGestorRiesgo() {
        int op;
        do {
            lector.mostrar("\n===== MENU GESTOR DE RIESGO =====");
            lector.mostrar("1. Gestionar incidentes");
            lector.mostrar("2. Gestionar categorias");
            lector.mostrar("3. Gestionar facultades");
            lector.mostrar("4. Buscar recomendaciones");
            lector.mostrar("5. Listar recomendaciones");
            lector.mostrar("0. Cerrar sesion");
            op = lector.leerOpcion("Opcion", 0, 5);
            switch (op) {
                case 1 -> gestionarIncidentes(true);
                case 2 -> gestionarCategoriasGestor();
                case 3 -> gestionarFacultadesGestor();
                case 4 -> buscarRecomendaciones();
                case 5 -> listarRecomendaciones();
                default -> { }
            }
        } while (op != 0);
    }

    private void gestionarIncidentes(boolean incluirZona) {
        if (!autenticado()) return;
        boolean usuarioComun = "ESTUDIANTE".equals(sesionActual.getTipoUsuario())
                || "TRABAJADOR".equals(sesionActual.getTipoUsuario());
        int op;
        do {
            lector.mostrar("\n--- GESTIONAR INCIDENTES ---");
            lector.mostrar("1. Anadir reporte de incidente");
            lector.mostrar("2. Eliminar reporte de incidente");
            lector.mostrar("3. Modificar incidente");
            lector.mostrar("4. Buscar incidentes");
            lector.mostrar("5. Listar incidentes");
            lector.mostrar("6. Adjuntar fotografias del incidente");
            if (usuarioComun) {
                lector.mostrar("7. Buscar evidencia");
            } else if (incluirZona) {
                lector.mostrar("7. Actualizar zona del incidente");
            }
            lector.mostrar("0. Volver");
            op = lector.leerOpcion("Opcion", 0, usuarioComun || incluirZona ? 7 : 6);
            switch (op) {
                case 1 -> reportarIncidente();
                case 2 -> eliminarIncidente();
                case 3 -> modificarIncidente(puedeVerTodosLosIncidentes());
                case 4 -> buscarIncidente();
                case 5 -> listarIncidentesVisibles();
                case 6 -> agregarEvidencia();
                case 7 -> {
                    if (usuarioComun) buscarEvidencia();
                    else if (incluirZona && "GESTOR_RIESGO".equals(sesionActual.getTipoUsuario()))
                        actualizarZonaIncidente();
                    else lector.mostrar("Opcion no disponible para este rol.");
                }
                default -> { }
            }
        } while (op != 0);
    }

    private void gestionarUsuariosModerador() {
        if (!esModerador()) return;
        int op;
        do {
            lector.mostrar("\n--- GESTIONAR USUARIOS ---");
            lector.mostrar("1. Anadir usuario");
            lector.mostrar("2. Deshabilitar usuario");
            lector.mostrar("3. Buscar usuario");
            lector.mostrar("4. Modificar usuario");
            lector.mostrar("5. Listar usuarios");
            lector.mostrar("0. Volver");
            op = lector.leerOpcion("Opcion", 0, 5);
            switch (op) {
                case 1 -> anadirUsuario();
                case 2 -> deshabilitarUsuario();
                case 3 -> buscarUsuario();
                case 4 -> modificarUsuario();
                case 5 -> listarUsuarios();
                default -> { }
            }
        } while (op != 0);
    }

    private void gestionarCategoriasGestor() {
        if (!esGestorRiesgo()) return;
        int op;
        do {
            lector.mostrar("\n--- GESTIONAR CATEGORIAS ---");
            lector.mostrar("1. Crear categoria");
            lector.mostrar("2. Eliminar categoria");
            lector.mostrar("3. Buscar categoria");
            lector.mostrar("4. Editar categoria");
            lector.mostrar("5. Listar categorias");
            lector.mostrar("0. Volver");
            op = lector.leerOpcion("Opcion", 0, 5);
            switch (op) {
                case 1 -> crearCategoria();
                case 2 -> eliminarCategoria();
                case 3 -> buscarCategoria();
                case 4 -> editarCategoria();
                case 5 -> listar(categorias.consultar(), "No hay categorias registradas.");
                default -> { }
            }
        } while (op != 0);
    }

    private void gestionarFacultadesGestor() {
        if (!esGestorRiesgo()) return;
        int op;
        do {
            lector.mostrar("\n--- GESTIONAR FACULTADES ---");
            lector.mostrar("1. Anadir facultad");
            lector.mostrar("2. Buscar facultad");
            lector.mostrar("3. Listar facultades");
            lector.mostrar("0. Volver");
            op = lector.leerOpcion("Opcion", 0, 3);
            switch (op) {
                case 1 -> anadirFacultad();
                case 2 -> buscarFacultad();
                case 3 -> listar(facultades, "No hay facultades registradas.");
                default -> { }
            }
        } while (op != 0);
    }

    private void gestionarRecomendacionesModerador() {
        if (!esModerador()) return;
        int op;
        do {
            lector.mostrar("\n--- GESTIONAR RECOMENDACIONES ---");
            lector.mostrar("1. Anadir recomendacion");
            lector.mostrar("2. Modificar recomendacion");
            lector.mostrar("3. Eliminar recomendacion");
            lector.mostrar("4. Buscar recomendaciones");
            lector.mostrar("5. Listar recomendaciones");
            lector.mostrar("0. Volver");
            op = lector.leerOpcion("Opcion", 0, 5);
            switch (op) {
                case 1 -> anadirRecomendacion();
                case 2 -> modificarRecomendacion();
                case 3 -> eliminarRecomendacion();
                case 4 -> buscarRecomendaciones();
                case 5 -> listarRecomendaciones();
                default -> { }
            }
        } while (op != 0);
    }

    // ------------------------ USUARIOS ------------------------
    private void anadirUsuario() {
        if (!esModerador()) return;
        lector.mostrar("1. Estudiante  2. Trabajador  3. Gestor de riesgo  4. Moderador");
        int tipo = lector.leerOpcion("Tipo de usuario", 1, 4);
        Usuario nuevo = switch (tipo) {
            case 1 -> ingresoUsuario.ingresarEstudiante(siguienteIdUsuario);
            case 2 -> ingresoUsuario.ingresarTrabajador(siguienteIdUsuario);
            case 3 -> ingresoUsuario.ingresarGestorDeRiesgo(siguienteIdUsuario);
            default -> ingresoUsuario.ingresarModerador(siguienteIdUsuario);
        };
        registrarUsuario(nuevo);
    }

    private void deshabilitarUsuario() {
        if (!esModerador()) return;
        listarUsuarios();
        int id = lector.leerEntero("ID del usuario a deshabilitar");
        for (Usuario u : usuarios.consultar()) {
            if (u.getIdUsuario() == id) {
                if (u.getIdUsuario() == sesionActual.getIdUsuario()) {
                    lector.mostrar("No puede deshabilitar su propia cuenta.");
                    return;
                }
                usuariosDeshabilitados.add(id);
                lector.mostrar("Usuario deshabilitado.");
                return;
            }
        }
        lector.mostrar("No existe ese usuario.");
    }

    private void buscarUsuario() {
        if (!esModerador()) return;
        String correo = lector.leerCorreo("Correo institucional del usuario");
        for (Usuario u : usuarios.consultar()) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                lector.mostrar(u + (usuariosDeshabilitados.contains(u.getIdUsuario()) ? " [DESHABILITADO]" : " [ACTIVO]"));
                return;
            }
        }
        lector.mostrar("Usuario no encontrado.");
    }

    private void modificarUsuario() {
        if (!esModerador()) return;
        int id = lector.leerEntero("ID del usuario a modificar");
        for (Usuario u : usuarios.consultar()) {
            if (u.getIdUsuario() == id) {
                u.setNombreUsuario(lector.leerLetras("Nuevo nombre"));
                u.setContrasenaHash(lector.leerContrasena("Nueva contrasena"));
                lector.mostrar("Usuario actualizado.");
                return;
            }
        }
        lector.mostrar("Usuario no encontrado.");
    }

    // ------------------------ INCIDENTES ------------------------
    private void eliminarIncidente() {
        if (!autenticado()) return;
        Incidente i = elegirIncidente();
        if (i == null) return;
        if (!puedeVerTodosLosIncidentes() && i.getUsuarioReporta().getIdUsuario() != sesionActual.getIdUsuario()) {
            lector.mostrar("Solo puede eliminar sus propios reportes.");
            return;
        }
        incidentes.eliminar(i);
        lector.mostrar("Reporte eliminado.");
    }

    private void modificarIncidente(boolean gestionGlobal) {
        if (!autenticado()) return;
        if (gestionGlobal && !puedeAdministrarIncidentes()) return;
        Incidente i = elegirIncidente();
        if (i == null) return;
        i.setTitulo(lector.leerTitulo("Nuevo titulo"));
        i.setDescripcion(lector.leerTitulo("Nueva descripcion"));
        i.setNivelGravedad(lector.leerDeLista("Nuevo nivel de gravedad", Validador.NIVELES_GRAVEDAD));
        i.setFechaHoraActualizacion(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        lector.mostrar("Incidente actualizado.");
    }

    private void buscarIncidente() {
        if (!autenticado()) return;
        int id = lector.leerEntero("ID del incidente");
        for (Incidente i : incidentes.consultar()) {
            if (i.getIdIncidente() == id && (puedeVerTodosLosIncidentes()
                    || i.getUsuarioReporta().getIdUsuario() == sesionActual.getIdUsuario())) {
                lector.mostrar(i.toString());
                return;
            }
        }
        lector.mostrar("Incidente no encontrado o sin permisos para consultarlo.");
    }

    private void buscarEvidencia() {
        if (!autenticado()) return;
        int id = lector.leerEntero("ID de evidencia");
        for (Incidente i : incidentes.consultar()) {
            if (puedeVerTodosLosIncidentes() || i.getUsuarioReporta().getIdUsuario() == sesionActual.getIdUsuario()) {
                for (Evidencia e : i.getEvidencias()) {
                    if (e.getIdEvidencia() == id) {
                        lector.mostrar("Evidencia encontrada en incidente " + i.getIdIncidente() + ": " + e);
                        return;
                    }
                }
            }
        }
        lector.mostrar("Evidencia no encontrada.");
    }

    private void actualizarZonaIncidente() {
        if (!esGestorRiesgo()) return;
        Incidente i = elegirIncidente();
        if (i == null) return;
        i.setFacultad(elegirFacultadRegistrada("Nueva facultad/zona"));
        i.setFechaHoraActualizacion(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        lector.mostrar("Zona del incidente actualizada.");
    }

    // ------------------------ CATEGORIAS ------------------------
    private void crearCategoria() {
        if (!esGestorRiesgo()) return;
        Categoria c = ingresoIncidente.ingresarCategoria(siguienteIdCategoria);
        if (categorias.consultar().stream().anyMatch(existente ->
                existente.getNombreCategoria().equalsIgnoreCase(c.getNombreCategoria()))) {
            lector.mostrar("Ya existe una categoria con ese nombre.");
            return;
        }
        String nivel = Validador.esDeLista(c.getNombreCategoria(), Validador.NIVELES_GRAVEDAD)
                ? c.getNombreCategoria().toUpperCase(java.util.Locale.ROOT)
                : lector.leerDeLista("Nivel de gravedad asignado a esta categoria", Validador.NIVELES_GRAVEDAD);
        if (!categorias.crear(c)) { lector.mostrar("Ya existe una categoria con ese id."); return; }
        nivelPorCategoria.put(c.getIdCategoria(), nivel);
        siguienteIdCategoria++;
        lector.mostrar("Categoria creada con ID " + c.getIdCategoria());
    }

    private void eliminarCategoria() {
        if (!esGestorRiesgo()) return;
        Categoria c = seleccionarCategoriaExistente();
        if (c == null) return;
        boolean utilizada = incidentes.consultar().stream().anyMatch(i -> i.getCategoria().getIdCategoria() == c.getIdCategoria());
        if (utilizada) { lector.mostrar("No se puede eliminar: la categoria esta asociada a incidentes."); return; }
        categorias.eliminar(c);
        nivelPorCategoria.remove(c.getIdCategoria());
        lector.mostrar("Categoria eliminada.");
    }

    private void buscarCategoria() {
        if (!autenticado()) return;
        String nombre = lector.leerLetras("Nombre de la categoria");
        for (Categoria c : categorias.consultar()) {
            if (c.getNombreCategoria().equalsIgnoreCase(nombre)) { lector.mostrar(c.toString()); return; }
        }
        lector.mostrar("Categoria no encontrada.");
    }

    // EDITAR: crea el objeto actualizado y lo reemplaza con RepositorioCRUD.editar (rechaza duplicados).
    private void editarCategoria() {
        if (!esGestorRiesgo()) return;
        Categoria original = seleccionarCategoriaExistente();
        if (original == null) return;
        String nombre = lector.leerLetras("Nuevo nombre de la categoria");
        String descripcion = lector.leerTexto("Nueva descripcion");
        Categoria actualizada;
        try {
            actualizada = new Categoria(original.getIdCategoria(), nombre, descripcion);
        } catch (IllegalArgumentException e) {
            lector.mostrar("No se pudo editar: " + e.getMessage());
            return;
        }
        for (Categoria otra : categorias.consultar()) {
            if (otra.getIdCategoria() != original.getIdCategoria()
                    && otra.getNombreCategoria().equalsIgnoreCase(nombre)) {
                lector.mostrar("Ya existe una categoria con ese nombre.");
                return;
            }
        }
        if (categorias.editar(original, actualizada)) lector.mostrar("Categoria actualizada.");
        else lector.mostrar("No se pudo actualizar la categoria.");
    }

    private Categoria seleccionarCategoriaExistente() {
        if (categorias.cantidad() == 0) { lector.mostrar("No hay categorias."); return null; }
        for (Categoria c : categorias.consultar()) lector.mostrar(c.toString());
        int id = lector.leerEntero("ID de categoria");
        for (Categoria c : categorias.consultar()) if (c.getIdCategoria() == id) return c;
        lector.mostrar("Categoria no encontrada.");
        return null;
    }

    // ------------------------ FACULTADES ------------------------
    private void anadirFacultad() {
        if (!esGestorRiesgo()) return;
        String nombre = lector.leerLetras("Nombre de la facultad");
        if (facultades.stream().anyMatch(f -> f.equalsIgnoreCase(nombre))) {
            lector.mostrar("La facultad ya existe."); return;
        }
        try {
            Validador.registrarFacultad(nombre);
            facultades.add(nombre);
            lector.mostrar("Facultad agregada.");
        } catch (IllegalArgumentException e) {
            lector.mostrar("No se pudo agregar la facultad: " + e.getMessage());
        }
    }

    private void buscarFacultad() {
        if (!autenticado()) return;
        String texto = lector.leerTexto("Nombre o parte del nombre de la facultad");
        int encontrados = 0;
        for (String f : facultades) if (f.toLowerCase().contains(texto.toLowerCase())) {
            lector.mostrar("- " + f); encontrados++;
        }
        if (encontrados == 0) lector.mostrar("No se encontraron facultades.");
    }

    private String elegirFacultadRegistrada(String etiqueta) {
        for (int n = 0; n < facultades.size(); n++) lector.mostrar((n + 1) + ". " + facultades.get(n));
        int indice = lector.leerOpcion(etiqueta, 1, facultades.size());
        return facultades.get(indice - 1);
    }

    // ------------------------ RECOMENDACIONES ------------------------
    private void anadirRecomendacion() {
        if (!esModerador()) return;
        String texto = lector.leerTexto("Contenido de la recomendacion");
        int id = recomendaciones.isEmpty() ? 1 : recomendaciones.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
        recomendaciones.put(id, texto);
        lector.mostrar("Recomendacion agregada con ID " + id);
    }

    private void modificarRecomendacion() {
        if (!esModerador()) return;
        listarRecomendaciones();
        int id = lector.leerEntero("ID de recomendacion");
        if (!recomendaciones.containsKey(id)) { lector.mostrar("Recomendacion no encontrada."); return; }
        recomendaciones.put(id, lector.leerTexto("Nuevo contenido"));
        lector.mostrar("Recomendacion modificada.");
    }

    private void eliminarRecomendacion() {
        if (!esModerador()) return;
        listarRecomendaciones();
        int id = lector.leerEntero("ID de recomendacion");
        if (recomendaciones.remove(id) != null) lector.mostrar("Recomendacion eliminada.");
        else lector.mostrar("Recomendacion no encontrada.");
    }

    private void buscarRecomendaciones() {
        if (!autenticado()) return;
        if (recomendaciones.isEmpty()) {
            lector.mostrar("No hay recomendaciones registradas.");
            return;
        }
        String criterio = lector.leerTexto("Palabra o frase para buscar recomendacion").toLowerCase(java.util.Locale.ROOT);
        int encontradas = 0;
        for (Map.Entry<Integer, String> entrada : recomendaciones.entrySet()) {
            if (entrada.getValue().toLowerCase(java.util.Locale.ROOT).contains(criterio)) {
                lector.mostrar("ID " + entrada.getKey() + " - " + entrada.getValue());
                encontradas++;
            }
        }
        if (encontradas == 0) lector.mostrar("No se encontraron recomendaciones.");
    }

    private void listarRecomendaciones() {
        if (!autenticado()) return;
        if (recomendaciones.isEmpty()) { lector.mostrar("No hay recomendaciones registradas."); return; }
        recomendaciones.forEach((id, texto) -> lector.mostrar("ID " + id + " - " + texto));
    }

}