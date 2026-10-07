public class Alquiler {
    private int correlativo;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private int dias;
    private double subtotal;
    private double descuento;
    private double total;
    private boolean activo;

    public Alquiler(int correlativo, Cliente cliente, Vehiculo vehiculo, int dias, double subtotal, double descuento) {
        this.correlativo = correlativo;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.dias = dias;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = subtotal - descuento;
        this.activo = true;
    }

    public int getCorrelativo() { return correlativo; }
    public Cliente getCliente() { return cliente; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public int getDias() { return dias; }
    public double getSubtotal() { return subtotal; }
    public double getDescuento() { return descuento; }
    public double getTotal() { return total; }
    public boolean isActivo() { return activo; }
    public void finalizarAlquiler() { this.activo = false; }
}