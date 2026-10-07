import java.util.List;

public class CamionetaCarga extends Vehiculo {
    private double capacidadToneladas;

    public CamionetaCarga(String placa, String marca, String modelo, double tarifa, double capacidadToneladas) {
        super(placa, marca, modelo, tarifa);
        this.capacidadToneladas = capacidadToneladas;
    }

    @Override
    public double calcularRecargo(int dias) {
        return 100.0 * capacidadToneladas * dias;
    }

    @Override
    public boolean validarLicencia(List<TipoLicencia> licencias) {
        return licencias.contains(TipoLicencia.A) || licencias.contains(TipoLicencia.B);
    }

    @Override
    public int getUmbralMantenimiento() { return 15; }

    @Override
    public String getDescripcion() {
        return String.format("Camioneta de Carga - %s %s [%s] | Capacidad: %.1f Toneladas | Tarifa: Q%.2f", 
                getMarca(), getModelo(), getPlaca(), capacidadToneladas, getTarifaDiaria());
    }
}