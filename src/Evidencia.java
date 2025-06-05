import java.io.BufferedReader;    // para leer lineas del archivo
import java.io.BufferedWriter;    // para escribir lineas en el archivo
import java.io.File;              // para manejar archivos y carpetas
import java.io.FileReader;        // para abrir un archivo para leer
import java.io.FileWriter;        // para abrir un archivo para escribir
import java.io.IOException;       // para manejar errores de entrada y salida
import java.nio.file.Files;       // para crear carpetas si no existen
import java.nio.file.Paths;       // para verificar rutas
import java.util.ArrayList;       // para usar listas dinamicas
import java.util.List;            // para declarar variables de tipo lista
import java.util.Scanner;         // para leer datos del usuario
import java.util.function.Consumer; // para procesar lineas cuando leemos el archivo

public class Evidencia {



    // listas en memoria ──────────
    private List<Doctor> listaDoctores = new ArrayList<>();     // aqui guardamos todos los doctores
    private List<Paciente> listaPacientes = new ArrayList<>();   // aqui guardamos todos los pacientes
    private List<Cita> listaCitas = new ArrayList<>();           // aqui guardamos todas las citas




    // rutas de carpeta y archivos
    private final String CARPETA_DB        = "db";
    private final String ARCHIVO_DOCTORES  = CARPETA_DB + File.separator + "doctors.csv";
    private final String ARCHIVO_PACIENTES = CARPETA_DB + File.separator + "patients.csv";
    private final String ARCHIVO_CITAS     = CARPETA_DB + File.separator + "appointments.csv";




    // para leer datos que escribe el usuario
    private Scanner entrada = new Scanner(System.in);

    // Constructor: crea carpeta, archivos y carga los datos
    public Evidencia() {
        crearCarpetaYArchivos();    // si no existen, los crea
        cargarTodosLosDatos();      // carga los datos de los archivos a las listas
    }




    // crea carpeta db y archivos si no existen
    private void crearCarpetaYArchivos() {
        // crear carpeta si falta
        if (!Files.exists(Paths.get(CARPETA_DB))) {
            try {
                Files.createDirectory(Paths.get(CARPETA_DB));
                System.out.println("Carpeta db creada.");
            } catch (IOException e) {
                System.out.println("No se pudo crear carpeta db: " + e.getMessage());
            }
        }




        // Crear cada archivo si falta
        crearArchivoSiFalta(ARCHIVO_DOCTORES);
        crearArchivoSiFalta(ARCHIVO_PACIENTES);
        crearArchivoSiFalta(ARCHIVO_CITAS);
    }

    // Si no existe el archivo lo crea vacío
    private void crearArchivoSiFalta(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("No se pudo crear el archivo " + ruta + ": " + e.getMessage());
            }
        }
    }




    // lee los archivos y llena las listas
    private void cargarTodosLosDatos() {
        listaDoctores.clear();
        listaPacientes.clear();
        listaCitas.clear();



        // Cargar doctores
        readCSV(ARCHIVO_DOCTORES, partes -> {
            if (partes.length >= 3) {
                String id = partes[0].trim();
                String nombre = partes[1].trim();
                String esp = partes[2].trim();
                if (!id.isEmpty() && !nombre.isEmpty() && !esp.isEmpty()) {
                    listaDoctores.add(new Doctor(id, nombre, esp));
                }
            }
        });




        // Cargar pacientes
        readCSV(ARCHIVO_PACIENTES, partes -> {
            if (partes.length >= 2) {
                String id = partes[0].trim();
                String nombre = partes[1].trim();
                if (!id.isEmpty() && !nombre.isEmpty()) {
                    listaPacientes.add(new Paciente(id, nombre));
                }
            }
        });




        // Cargar citas
        readCSV(ARCHIVO_CITAS, partes -> {
            if (partes.length >= 6) {
                String id = partes[0].trim();
                String fecha = partes[1].trim();
                String hora = partes[2].trim();
                String motivo = partes[3].trim();
                String idDoc = partes[4].trim();
                String idPac = partes[5].trim();
                if (!id.isEmpty() && !fecha.isEmpty() && !hora.isEmpty() &&
                        !motivo.isEmpty() && !idDoc.isEmpty() && !idPac.isEmpty()) {
                    listaCitas.add(new Cita(id, fecha, hora, motivo, idDoc, idPac));
                }
            }
        });
    }




    // lee cada línea del archivo y aplica la función para llenar los datos
    private void readCSV(String rutaArchivo, Consumer<String[]> accion) {
        File archivo = new File(rutaArchivo);
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.isBlank()) continue;               // omite líneas vacías
                String[] partes = linea.split(",", -1);      // separa por coma
                accion.accept(partes);                       // llama a la función con esos datos
            }
        } catch (IOException e) {
            System.out.println("Error al leer " + rutaArchivo + ": " + e.getMessage());
        }
    }




    // muestra el menu
    public void menu() {
        String opcion;
        do {
            System.out.println("\nMenu");
            System.out.println("1. Crear nuevo doctor");
            System.out.println("2. Crear nuevo paciente");
            System.out.println("3. Lista de citas");
            System.out.println("4. Crear nueva cita");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = entrada.nextLine().trim();

            switch (opcion) {
                case "1":
                    crearDoctor();
                    break;
                case "2":
                    crearPaciente();
                    break;
                case "3":
                    listarCitas();
                    break;
                case "4":
                    crearCita();
                    break;
                case "5":
                    guardarTodosLosDatos();
                    System.out.println("Datos guardados. Hasta luego :)");
                    break;
                default:
                    System.out.println("!ERROR! Solo escriba 1, 2, 3, 4 ó 5.");
            }
        } while (!opcion.equals("5"));

        entrada.close();   // cerrar el lector
    }




    // Pide datos para crear un doctor y lo agrega
    private void crearDoctor() {
        System.out.print("\nEscriba ID del doctor: ");
        String id = entrada.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Debe escribir un ID.");
            return;
        }




        // Verificar si ya existe
        for (Doctor d : listaDoctores) {
            if (d.getIdentificador().equalsIgnoreCase(id)) {
                System.out.println("Ese ID ya existe para: " + d.getNombre());
                return;
            }
        }
        System.out.print("Escriba nombre del doctor: ");
        String nombre = entrada.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Debe escribir un nombre.");
            return;
        }
        System.out.print("Escriba especialidad: ");
        String esp = entrada.nextLine().trim();
        if (esp.isEmpty()) {
            System.out.println("Debe escribir una especialidad.");
            return;
        }
        listaDoctores.add(new Doctor(id, nombre, esp));
        System.out.println("Doctor agregado: " + id + " : " + nombre + " (" + esp + ")");
    }




    // Pide datos para crear un paciente y lo agrega
    private void crearPaciente() {
        System.out.print("\nEscriba ID del paciente: ");
        String id = entrada.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Debe escribir un ID.");
            return;
        }




        // Verificar si ya existe
        for (Paciente p : listaPacientes) {
            if (p.getIdentificador().equalsIgnoreCase(id)) {
                System.out.println("Ese ID ya existe para: " + p.getNombre());
                return;
            }
        }
        System.out.print("Escriba nombre del paciente: ");
        String nombre = entrada.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Debe escribir un nombre.");
            return;
        }
        listaPacientes.add(new Paciente(id, nombre));
        System.out.println("Paciente agregado: " + id + " : " + nombre);
    }




    // Muestra todas las citas que existen
    private void listarCitas() {
        System.out.println("\nCitas registradas:");
        if (listaCitas.isEmpty()) {
            System.out.println("No hay citas.");
            return;
        }
        System.out.println("ID : Fecha Hora : Doctor -> Paciente");
        for (Cita c : listaCitas) {
            System.out.println(c.getIdentificador() + " : " +
                    c.getFecha() + " " + c.getHora() + " : " +
                    c.getIdentificadorDoctor() + " -> " +
                    c.getIdentificadorPaciente());
        }
    }





    // Pide datos para crear una cita, verifica y la agrega
    private void crearCita() {
        System.out.print("\nEscriba ID de la cita: ");
        String id = entrada.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Debe escribir un ID.");
            return;
        }




        // Verificar si ya existe
        for (Cita c : listaCitas) {
            if (c.getIdentificador().equalsIgnoreCase(id)) {
                System.out.println("Ese ID ya existe para otra cita.");
                return;
            }
        }
        System.out.print("Escriba fecha (AAAA/MM/DD): ");
        String fecha = entrada.nextLine().trim();
        if (fecha.isEmpty()) {
            System.out.println("Debe escribir una fecha.");
            return;
        }
        System.out.print("Escriba hora (HH:MM): ");
        String hora = entrada.nextLine().trim();
        if (hora.isEmpty()) {
            System.out.println("Debe escribir una hora.");
            return;
        }
        System.out.print("Escriba motivo de la cita: ");
        String motivo = entrada.nextLine().trim();
        if (motivo.isEmpty()) {
            System.out.println("Debe escribir un motivo.");
            return;
        }




        // Elegir doctor
        if (listaDoctores.isEmpty()) {
            System.out.println("No hay doctores registrados.");
            return;
        }
        System.out.println("\nDoctores disponibles:");
        for (Doctor d : listaDoctores) {
            System.out.println(d.getIdentificador() + " : " + d.getNombre() +
                    " (" + d.getEspecialidad() + ")");
        }
        System.out.print("Escriba ID del doctor: ");
        String idDoc = entrada.nextLine().trim();
        if (idDoc.isEmpty()) {
            System.out.println("Debe escribir un ID de doctor.");
            return;
        }
        Doctor doctorSel = null;
        for (Doctor d : listaDoctores) {
            if (d.getIdentificador().equalsIgnoreCase(idDoc)) {
                doctorSel = d;
                break;
            }
        }
        if (doctorSel == null) {
            System.out.println("No se encontró ese doctor.");
            return;
        }




        // Elegir paciente
        if (listaPacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }
        System.out.println("\nPacientes disponibles:");
        for (Paciente p : listaPacientes) {
            System.out.println(p.getIdentificador() + " : " + p.getNombre());
        }
        System.out.print("Escriba ID del paciente: ");
        String idPac = entrada.nextLine().trim();
        if (idPac.isEmpty()) {
            System.out.println("Debe escribir un ID de paciente.");
            return;
        }
        Paciente pacienteSel = null;
        for (Paciente p : listaPacientes) {
            if (p.getIdentificador().equalsIgnoreCase(idPac)) {
                pacienteSel = p;
                break;
            }
        }
        if (pacienteSel == null) {
            System.out.println("No se encontró ese paciente.");
            return;
        }




        // Agregar la nueva cita
        listaCitas.add(new Cita(id, fecha, hora, motivo,
                doctorSel.getIdentificador(),
                pacienteSel.getIdentificador()));
        System.out.println("Cita creada: " + id + " : " + fecha + " " + hora +
                " : " + doctorSel.getIdentificador() + " -> " +
                pacienteSel.getIdentificador());
    }




    // Guarda todas las listas en sus respectivos archivos
    private void guardarTodosLosDatos() {
        escribirCSV(ARCHIVO_DOCTORES, listaDoctores, d -> d.toString());
        escribirCSV(ARCHIVO_PACIENTES, listaPacientes, p -> p.toString());
        escribirCSV(ARCHIVO_CITAS, listaCitas, c -> c.toString());
    }




    // Escribe la lista completa en el archivo, linea por linea
    private <T> void escribirCSV(String rutaArchivo, List<T> datos,
                                 java.util.function.Function<T, String> convertir) {
        File archivo = new File(rutaArchivo);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
            for (T obj : datos) {
                escritor.write(convertir.apply(obj));   // escribe la linea
                escritor.newLine();                     // salto de linea
            }
        } catch (IOException e) {
            System.out.println("Error al guardar " + rutaArchivo + ": " + e.getMessage());
        }
    }




    // muestra el menu
    public static void main(String[] args) {
        Evidencia programa = new Evidencia();  // crear el programa
        programa.menu();                       // mostrar menú principal
    }
}
