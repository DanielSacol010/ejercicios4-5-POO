import java.util.ArrayList;
import java.util.List;

public abstract class Cliente {
    private String identificador;
    private String nombre;
    private List<TipoLicencia> licencias;
    private int alquileresActivos;
    private List<Alquiler> historialAlquileres;

    public Cliente(String identificador, String nombre, List<TipoLicencia> licencias) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.licencias = new ArrayList<>(licencias);
        this.alquileresActivos = 0;
        this.historialAlquileres = new ArrayList<>();
    }

    public String getIdentificador() { return identificador; }
    public String getNombre() { return nombre; }
    public List<TipoLicencia> getLicencias() { return licencias; }
    public int getAlquileresActivos() { return alquileresActivos; }
    public void setAlquileresActivos(int activos) { this.alquileresActivos = activos; }
    
    public void agregarAlquiler(Alquiler alquiler) {
        historialAlquileres.add(alquiler);
    }
    public List<Alquiler> getHistorialAlquileres() { return historialAlquileres; }

    public abstract double calcularDescuento(double subtotal);
    public abstract boolean puedeAlquilarMas();
    public abstract void registrarAlquilerConfirmado();
    public abstract String getTipoClienteStr();
}