import java.util.List;

public class Automovil extends Vehiculo {
    private int pasajeros;
    private boolean esAutomatico;

    public Automovil(String placa, String marca, String modelo, double tarifa, int pasajeros, boolean esAutomatico) {
        super(placa, marca, modelo, tarifa);
        this.pasajeros = pasajeros;
        this.esAutomatico = esAutomatico;
    }

    @Override
    public double calcularRecargo(int dias) {
        return esAutomatico ? 50.0 * dias : 0.0;
    }

    @Override
    public boolean validarLicencia(List<TipoLicencia> licencias) {
        return licencias.contains(TipoLicencia.A) || licencias.contains(TipoLicencia.B) || licencias.contains(TipoLicencia.C);
    }

    @Override
    public int getUmbralMantenimiento() { return 30; }

    @Override
    public String getDescripcion() {
        String trans = esAutomatico ? "Automática" : "Manual";
        return String.format("Automóvil - %s %s [%s] | %d pasajeros, Transmisión: %s | Tarifa: Q%.2f", 
                getMarca(), getModelo(), getPlaca(), pasajeros, trans, getTarifaDiaria());
    }
}