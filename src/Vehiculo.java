import java.util.List;

public abstract class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private EstadoVehiculo estado;
    private int diasAcumulados;

    public Vehiculo(String placa, String marca, String modelo, double tarifaDiaria) {
        if (placa == null || placa.trim().isEmpty()) throw new IllegalArgumentException("La placa no puede estar vacía.");
        if (tarifaDiaria <= 0) throw new IllegalArgumentException("La tarifa diaria debe ser mayor a cero.");
        
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.tarifaDiaria = tarifaDiaria;
        this.estado = EstadoVehiculo.DISPONIBLE; 
        this.diasAcumulados = 0;
    }

    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getTarifaDiaria() { return tarifaDiaria; }
    public EstadoVehiculo getEstado() { return estado; }
    public void setEstado(EstadoVehiculo estado) { this.estado = estado; }
    public int getDiasAcumulados() { return diasAcumulados; }
    
    public void reiniciarDiasAcumulados() { this.diasAcumulados = 0; }
    public void sumarDiasMantenimiento(int dias) { this.diasAcumulados += dias; }

    public abstract double calcularRecargo(int dias);
    public abstract boolean validarLicencia(List<TipoLicencia> licencias);
    public abstract int getUmbralMantenimiento();
    public abstract String getDescripcion();
}