import java.util.List;

public class ClienteCorporativo extends Cliente {
    private String contacto;

    public ClienteCorporativo(String nit, String nombreEmpresa, String contacto, List<TipoLicencia> licencias) {
        super(nit, nombreEmpresa, licencias);
        this.contacto = contacto;
    }

    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * 0.10;
    }

    @Override
    public boolean puedeAlquilarMas() {
        return getAlquileresActivos() < 3;
    }

    @Override
    public void registrarAlquilerConfirmado() {
    }

    @Override
    public String getTipoClienteStr() { return "Corporativo (NIT: " + getIdentificador() + " - Contacto: " + contacto + ")"; }
}