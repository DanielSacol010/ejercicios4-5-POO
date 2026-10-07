import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControladorRentaMovil {
    private List<Vehiculo> vehiculos;
    private List<Cliente> clientes;
    private List<Alquiler> alquileres;
    private int correlativoActual;
    private double ingresosTotales;
    private double descuentosTotales;

    public ControladorRentaMovil() {
        this.vehiculos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.alquileres = new ArrayList<>();
        this.correlativoActual = 1;
        this.ingresosTotales = 0.0;
        this.descuentosTotales = 0.0;
    }

    public void iniciarSistema() {
        cargarDatosIniciales();
        VistaRentaMovil vista = new VistaRentaMovil(this);
        vista.mostrarMenu();
    }

    private void cargarDatosIniciales() {
        vehiculos.add(new Automovil("A-001", "Toyota", "Yaris", 200, 5, false));
        Vehiculo autoCasiUmbral = new Automovil("A-002", "Honda", "Civic", 250, 5, true);
        autoCasiUmbral.sumarDiasMantenimiento(28);
        vehiculos.add(autoCasiUmbral);

        vehiculos.add(new Motocicleta("M-001", "Yamaha", "MT-03", 100, 321));
        vehiculos.add(new Motocicleta("M-002", "Suzuki", "GN125", 80, 125));

        vehiculos.add(new CamionetaCarga("C-001", "Isuzu", "NQR", 350, 3.5));
        vehiculos.add(new CamionetaCarga("C-002", "Hino", "300", 400, 5.0));

        vehiculos.add(new Microbus("B-001", "Toyota", "Hiace", 500, 15, true));
        vehiculos.add(new Microbus("B-002", "Nissan", "Urvan", 300, 12, false));

        ClienteIndividual ind1 = new ClienteIndividual("1111111111111", "Juan Perez", Arrays.asList(TipoLicencia.B));
        Vehiculo vHistorial = vehiculos.get(0);
        
        Alquiler pasado1 = new Alquiler(901, ind1, vHistorial, 2, 400.0, 0.0);
        pasado1.finalizarAlquiler();
        ind1.agregarAlquiler(pasado1);
        
        Alquiler pasado2 = new Alquiler(902, ind1, vHistorial, 1, 200.0, 0.0);
        pasado2.finalizarAlquiler();
        ind1.agregarAlquiler(pasado2);
        
        Alquiler pasado3 = new Alquiler(903, ind1, vHistorial, 3, 600.0, 0.0);
        pasado3.finalizarAlquiler();
        ind1.agregarAlquiler(pasado3);
        
        ind1.setAlquileresConfirmados(3);
        
        clientes.add(ind1);
        clientes.add(new ClienteIndividual("2222222222222", "Maria Gomez", Arrays.asList(TipoLicencia.M)));

        clientes.add(new ClienteCorporativo("8888888", "Distribuidora SA", "Carlos Lopez", Arrays.asList(TipoLicencia.A, TipoLicencia.B)));
        clientes.add(new ClienteCorporativo("9999999", "Tech Solutions", "Ana Torres", Arrays.asList(TipoLicencia.C)));
    }

    public void registrarVehiculo(Vehiculo v) {
        if (buscarVehiculo(v.getPlaca()) != null) {
            throw new RentaMovilException("Error: La placa " + v.getPlaca() + " ya está registrada.");
        }
        vehiculos.add(v);
    }

    public void registrarCliente(Cliente c) {
        if (buscarCliente(c.getIdentificador()) != null) {
            throw new RentaMovilException("Error: El identificador " + c.getIdentificador() + " ya está registrado.");
        }
        clientes.add(c);
    }

    public Vehiculo buscarVehiculo(String placa) {
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) return v;
        }
        return null;
    }

    public Cliente buscarCliente(String id) {
        for (Cliente c : clientes) {
            if (c.getIdentificador().equals(id)) return c;
        }
        return null;
    }

    public List<Vehiculo> getVehiculos() { return vehiculos; }
    public List<Cliente> getClientes() { return clientes; }

    public String cotizarAlquiler(String placa, String idCliente, int dias) {
        Vehiculo v = buscarVehiculo(placa);
        Cliente c = buscarCliente(idCliente);

        if (v == null) return "Error: Vehículo no encontrado.";
        if (c == null) return "Error: Cliente no encontrado.";

        StringBuilder cotizacion = new StringBuilder();
        cotizacion.append("\n--- COTIZACIÓN ---\n");
        cotizacion.append("Vehículo: ").append(v.getDescripcion()).append("\n");
        cotizacion.append("Estado actual: ").append(v.getEstado()).append("\n");
        
        double tarifaBase = v.getTarifaDiaria() * dias;
        double recargo = v.calcularRecargo(dias);
        double subtotal = tarifaBase + recargo;
        double descuento = c.calcularDescuento(subtotal);
        double total = subtotal - descuento;

        cotizacion.append(String.format("Subtotal: Q%.2f (Tarifa base: Q%.2f + Recargo: Q%.2f)\n", subtotal, tarifaBase, recargo));
        cotizacion.append(String.format("Descuento: Q%.2f\n", descuento));
        cotizacion.append(String.format("TOTAL: Q%.2f\n", total));

        boolean puedeAlquilar = true;
        cotizacion.append("\nAnálisis de viabilidad:\n");

        if (v.getEstado() != EstadoVehiculo.DISPONIBLE) {
            cotizacion.append("- RECHAZADO: El vehículo no está disponible.\n");
            puedeAlquilar = false;
        }
        if (!v.validarLicencia(c.getLicencias())) {
            cotizacion.append("- RECHAZADO: El cliente no posee la licencia requerida para este vehículo.\n");
            puedeAlquilar = false;
        }
        if (!c.puedeAlquilarMas()) {
            cotizacion.append("- RECHAZADO: El cliente alcanzó su límite de alquileres activos.\n");
            puedeAlquilar = false;
        }

        if (puedeAlquilar) {
            cotizacion.append("+ APROBADO: El cliente puede alquilar este vehículo ahora mismo.\n");
        }
        
        return cotizacion.toString();
    }

    public void confirmarAlquiler(String placa, String idCliente, int dias) {
        if (dias <= 0) throw new IllegalArgumentException("Los días de alquiler deben ser mayores a cero.");

        Vehiculo v = buscarVehiculo(placa);
        if (v == null) throw new RentaMovilException("Error: Vehículo no existe.");
        
        Cliente c = buscarCliente(idCliente);
        if (c == null) throw new RentaMovilException("Error: Cliente no existe.");

        if (v.getEstado() != EstadoVehiculo.DISPONIBLE) {
            throw new RentaMovilException("RECHAZADO: El vehículo no está disponible.");
        }
        if (!v.validarLicencia(c.getLicencias())) {
            throw new RentaMovilException("RECHAZADO: El cliente no posee la licencia requerida para este vehículo.");
        }
        if (!c.puedeAlquilarMas()) {
            throw new RentaMovilException("RECHAZADO: El cliente alcanzó su límite de alquileres activos.");
        }

        double subtotal = (v.getTarifaDiaria() * dias) + v.calcularRecargo(dias);
        double descuento = c.calcularDescuento(subtotal);
        
        Alquiler nuevoAlquiler = new Alquiler(correlativoActual++, c, v, dias, subtotal, descuento);
        alquileres.add(nuevoAlquiler);
        c.agregarAlquiler(nuevoAlquiler);

        v.setEstado(EstadoVehiculo.ALQUILADO);
        c.setAlquileresActivos(c.getAlquileresActivos() + 1);
        c.registrarAlquilerConfirmado(); 

        ingresosTotales += nuevoAlquiler.getTotal();
        descuentosTotales += nuevoAlquiler.getDescuento();
    }

    public String registrarDevolucion(String placa) {
        Vehiculo v = buscarVehiculo(placa);
        if (v == null) throw new RentaMovilException("Vehículo no existe.");
        if (v.getEstado() != EstadoVehiculo.ALQUILADO) throw new RentaMovilException("Rechazado: El vehículo no está alquilado.");

        Alquiler alquilerActivo = null;
        for (Alquiler a : alquileres) {
            if (a.getVehiculo().getPlaca().equalsIgnoreCase(placa) && a.isActivo()) {
                alquilerActivo = a;
                break;
            }
        }

        if (alquilerActivo == null) throw new RentaMovilException("No se encontró registro de alquiler activo para esta placa.");

        alquilerActivo.finalizarAlquiler();
        alquilerActivo.getCliente().setAlquileresActivos(alquilerActivo.getCliente().getAlquileresActivos() - 1);
        
        v.sumarDiasMantenimiento(alquilerActivo.getDias());
        if (v.getDiasAcumulados() >= v.getUmbralMantenimiento()) {
            v.setEstado(EstadoVehiculo.EN_MANTENIMIENTO);
            return "Devolución exitosa. El vehículo alcanzó su umbral y pasó a MANTENIMIENTO.";
        } else {
            v.setEstado(EstadoVehiculo.DISPONIBLE);
            return "Devolución exitosa. El vehículo está DISPONIBLE nuevamente.";
        }
    }

    public String registrarFinMantenimiento(String placa) {
        Vehiculo v = buscarVehiculo(placa);
        if (v == null) throw new RentaMovilException("Vehículo no existe.");
        if (v.getEstado() != EstadoVehiculo.EN_MANTENIMIENTO) throw new RentaMovilException("Rechazado: El vehículo no está en mantenimiento.");

        v.setEstado(EstadoVehiculo.DISPONIBLE);
        v.reiniciarDiasAcumulados();
        return "Fin de mantenimiento registrado. Vehículo DISPONIBLE.";
    }

    public String generarReportesCierre() {
        StringBuilder rep = new StringBuilder();
        rep.append("\n=== REPORTES DE FINAL DE JORNADA ===\n");
        
        int dis = 0, alq = 0, man = 0;
        for (Vehiculo v : vehiculos) {
            if (v.getEstado() == EstadoVehiculo.DISPONIBLE) dis++;
            else if (v.getEstado() == EstadoVehiculo.ALQUILADO) alq++;
            else if (v.getEstado() == EstadoVehiculo.EN_MANTENIMIENTO) man++;
        }
        rep.append(String.format("Flota (Total %d): %d Disponibles, %d Alquilados, %d Mantenimiento\n", vehiculos.size(), dis, alq, man));
        rep.append(String.format("Ingresos Totales: Q%.2f\n", ingresosTotales));
        rep.append(String.format("Descuentos Otorgados: Q%.2f\n", descuentosTotales));
        
        rep.append("\nAlquileres Activos:\n");
        boolean hayActivos = false;
        for (Alquiler a : alquileres) {
            if (a.isActivo()) {
                rep.append(String.format("- Corr: %d | Placa: %s | Cliente: %s\n", a.getCorrelativo(), a.getVehiculo().getPlaca(), a.getCliente().getNombre()));
                hayActivos = true;
            }
        }
        if (!hayActivos) rep.append("No hay alquileres activos.\n");
        
        return rep.toString();
    }
    
    public String obtenerHistorialCliente(String id) {
        Cliente c = buscarCliente(id);
        if (c == null) return "Cliente no encontrado.";
        
        StringBuilder rep = new StringBuilder();
        rep.append("\nHistorial de: ").append(c.getNombre()).append("\n");
        double totalPagado = 0;
        
        if (c.getHistorialAlquileres().isEmpty()) return rep.append("No tiene alquileres registrados.\n").toString();
        
        for (Alquiler a : c.getHistorialAlquileres()) {
            rep.append(String.format("- %d días | %s | Pagado: Q%.2f\n", a.getDias(), a.getVehiculo().getPlaca(), a.getTotal()));
            totalPagado += a.getTotal();
        }
        rep.append(String.format("Total histórico pagado: Q%.2f\n", totalPagado));
        return rep.toString();
    }
}