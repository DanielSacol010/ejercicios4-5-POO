import java.util.List;

public class Microbus extends Vehiculo {
    private int pasajeros;
    private boolean conPiloto;

    public Microbus(String placa, String marca, String modelo, double tarifa, int pasajeros, boolean conPiloto) {
        super(placa, marca, modelo, tarifa);
        this.pasajeros = pasajeros;
        this.conPiloto = conPiloto;
    }

    @Override
    public double calcularRecargo(int dias) {
        return conPiloto ? 250.0 * dias : 0.0;
    }

    @Override
    public boolean validarLicencia(List<TipoLicencia> licencias) {
        if (conPiloto) return true;
        return licencias.contains(TipoLicencia.A) || licencias.contains(TipoLicencia.B);
    }

    @Override
    public int getUmbralMantenimiento() { return 25; }

    @Override
    public String getDescripcion() {
        String pilotoInfo = conPiloto ? "Con Piloto" : "Sin Piloto";
        return String.format("Microbús - %s %s [%s] | %d pasajeros, %s | Tarifa: Q%.2f", 
                getMarca(), getModelo(), getPlaca(), pasajeros, pilotoInfo, getTarifaDiaria());
    }
}