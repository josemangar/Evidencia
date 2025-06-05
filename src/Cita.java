import java.util.Objects;    // para comparar objetos

public class Cita {

    private String identificador;          // ID de la cita
    private String fecha;                  // Fecha de la cita
    private String hora;                   // Hora de la cita
    private String motivo;                 // Motivo de la cita
    private String identificadorDoctor;    // ID del doctor que atenderá
    private String identificadorPaciente;  // ID del paciente





    // Constructor: crea una cita con todos los datos
    public Cita(String identificador, String fecha, String hora, String motivo,
                String identificadorDoctor, String identificadorPaciente) {
        this.identificador = identificador;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.identificadorDoctor = identificadorDoctor;
        this.identificadorPaciente = identificadorPaciente;
    }





    //Metodo para obtener informacion

    public String getIdentificador() {
        return identificador;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getIdentificadorDoctor() {
        return identificadorDoctor;
    }

    public String getIdentificadorPaciente() {
        return identificadorPaciente;
    }

    //etodos opcionales para cambiar datos

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setIdentificadorDoctor(String identificadorDoctor) {
        this.identificadorDoctor = identificadorDoctor;
    }

    public void setIdentificadorPaciente(String identificadorPaciente) {
        this.identificadorPaciente = identificadorPaciente;
    }





    // Convierte esta cita a una linea para guardar en el archivo
    @Override
    public String toString() {
        return identificador + "," + fecha + "," + hora + "," + motivo + "," +
                identificadorDoctor + "," + identificadorPaciente;
    }





    // Comprueba si dos citas tienen el mismo ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cita)) return false;
        Cita cita = (Cita) o;
        return Objects.equals(identificador, cita.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }
}
