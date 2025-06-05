import java.util.Objects;    // para comparar objetos

public class Doctor {

    private String identificador;    // ID del doctor
    private String nombre;           // Nombre del doctor
    private String especialidad;     // Especialidad del doctor

    // Constructor, crea un doctor con ID, nombre y especialidad
    public Doctor(String identificador, String nombre, String especialidad) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    //Metodos para obtener información

    public String getIdentificador() {
        return identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    //Opcionales para cambiar datos

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // Convierte este doctor a una línea para guardar en el archivo
    @Override
    public String toString() {
        return identificador + "," + nombre + "," + especialidad;
    }

    // Comprueba si dos doctores tienen el mismo ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(identificador, doctor.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }
}

