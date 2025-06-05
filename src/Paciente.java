import java.util.Objects;    // para comparar objetos

public class Paciente {

    private String identificador;    // ID del paciente
    private String nombre;           // Nombre del paciente





    // Constructor, crea un paciente con ID y nombre
    public Paciente(String identificador, String nombre) {
        this.identificador = identificador;
        this.nombre = nombre;
    }




    //metodo para obtener informacion

    public String getIdentificador() {
        return identificador;
    }

    public String getNombre() {
        return nombre;
    }

    //metodo para cambiar el nombre

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Convierte este paciente a una línea para guardar en el archivo
    @Override
    public String toString() {
        return identificador + "," + nombre;
    }

    // Comprueba si dos pacientes tienen el mismo ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paciente)) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(identificador, paciente.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }
}
