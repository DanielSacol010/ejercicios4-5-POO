import java.util.List;

public class Motocicleta extends Vehiculo {
    private int cilindraje;

    public Motocicleta(String placa, String marca, String modelo, double tarifa, int cilindraje) {
        super(placa, marca, modelo, tarifa);
        this.cilindraje = cilindraje;
    }

    @Override
    public double calcularRecargo(int dias) {
        return cilindraje > 250 ? 75.0 : 0.0;
    }

    @Override
    public boolean validarLicencia(List<TipoLicencia> licencias) {
        return licencias.contains(TipoLicencia.M);
    }

    @Override
    public int getUmbralMantenimiento() { return 20; }

    @Override
    public String getDescripcion() {
        return String.format("Motocicleta - %s %s [%s] | %d cc | Tarifa: Q%.2f", 
                getMarca(), getModelo(), getPlaca(), cilindraje, getTarifaDiaria());
    }
}