import java.util.List;

public class ClienteIndividual extends Cliente {
    private int alquileresConfirmados;

    public ClienteIndividual(String dpi, String nombre, List<TipoLicencia> licencias) {
        super(dpi, nombre, licencias);
        this.alquileresConfirmados = 0;
    }

    public void setAlquileresConfirmados(int confirmados) {
        this.alquileresConfirmados = confirmados;
    }

    @Override
    public double calcularDescuento(double subtotal) {
        return alquileresConfirmados >= 3 ? subtotal * 0.05 : 0.0;
    }

    @Override
    public boolean puedeAlquilarMas() {
        return getAlquileresActivos() < 1;
    }

    @Override
    public void registrarAlquilerConfirmado() {
        alquileresConfirmados++;
    }

    @Override
    public String getTipoClienteStr() { return "Individual (DPI: " + getIdentificador() + ")"; }
}