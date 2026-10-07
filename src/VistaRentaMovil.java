import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VistaRentaMovil {
    private ControladorRentaMovil controlador;
    private Scanner scanner;

    public VistaRentaMovil(ControladorRentaMovil controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n===== RENTAMOVIL =====");
            System.out.println("1. Registrar Vehículo");
            System.out.println("2. Registrar Cliente");
            System.out.println("3. Consultar Flota y Clientes");
            System.out.println("4. Cotizar Alquiler");
            System.out.println("5. Confirmar Alquiler");
            System.out.println("6. Registrar Devolución");
            System.out.println("7. Finalizar Mantenimiento");
            System.out.println("8. Reportes de Fin de Jornada");
            System.out.println("9. Historial de Cliente");
            System.out.println("10. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: menuRegistrarVehiculo(); break;
                case 2: menuRegistrarCliente(); break;
                case 3: consultarCatalogos(); break;
                case 4: menuCotizar(); break;
                case 5: menuConfirmar(); break;
                case 6: menuDevolucion(); break;
                case 7: menuFinMantenimiento(); break;
                case 8: System.out.println(controlador.generarReportesCierre()); break;
                case 9: 
                    String id = leerTexto("Ingrese ID del cliente: ");
                    System.out.println(controlador.obtenerHistorialCliente(id));
                    break;
                case 10: System.out.println("Saliendo..."); break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 10);
    }

    private void menuRegistrarVehiculo() {
        System.out.println("\nCategorías: 1.Automóvil  2.Motocicleta  3.Camioneta  4.Microbús");
        int cat = leerEntero("Seleccione categoría (1-4): ");
        if (cat < 1 || cat > 4) { System.out.println("Categoría inválida."); return; }

        String placa = leerTexto("Placa: ");
        if (controlador.buscarVehiculo(placa) != null) { System.out.println("Error: Placa ya existe."); return; }

        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");
        double tarifa = leerDoublePositivo("Tarifa diaria (Q): ");

        Vehiculo v = null;
        switch (cat) {
            case 1:
                int pasAuto = leerEnteroPositivo("Cantidad pasajeros: ");
                boolean auto = leerTexto("Es automático (s/n): ").equalsIgnoreCase("s");
                v = new Automovil(placa, marca, modelo, tarifa, pasAuto, auto);
                break;
            case 2:
                int cc = leerEnteroPositivo("Cilindraje (cc): ");
                v = new Motocicleta(placa, marca, modelo, tarifa, cc);
                break;
            case 3:
                double ton = leerDoublePositivo("Capacidad (Toneladas): ");
                v = new CamionetaCarga(placa, marca, modelo, tarifa, ton);
                break;
            case 4:
                int pasMicro = leerEnteroPositivo("Cantidad pasajeros: ");
                boolean piloto = leerTexto("Incluye piloto (s/n): ").equalsIgnoreCase("s");
                v = new Microbus(placa, marca, modelo, tarifa, pasMicro, piloto);
                break;
        }
        if (v != null) {
            controlador.registrarVehiculo(v);
            System.out.println("Vehículo registrado exitosamente.");
        }
    }

    private void menuRegistrarCliente() {
        System.out.println("\nTipos: 1.Individual  2.Corporativo");
        int tipo = leerEntero("Seleccione tipo (1-2): ");
        if (tipo < 1 || tipo > 2) { System.out.println("Tipo inválido."); return; }

        String id = leerTexto(tipo == 1 ? "DPI (13 dígitos): " : "NIT: ");
        if (tipo == 1 && id.length() != 13) { System.out.println("Error: DPI debe tener 13 dígitos."); return; }
        if (controlador.buscarCliente(id) != null) { System.out.println("Error: Identificador ya existe."); return; }

        String nombre = leerTexto("Nombre " + (tipo == 1 ? "del cliente: " : "de la empresa: "));
        List<TipoLicencia> lics = pedirLicencias();

        Cliente c = null;
        if (tipo == 1) {
            c = new ClienteIndividual(id, nombre, lics);
        } else {
            String contacto = leerTexto("Nombre del contacto: ");
            c = new ClienteCorporativo(id, nombre, contacto, lics);
        }
        if (c != null) {
            controlador.registrarCliente(c);
            System.out.println("Cliente registrado exitosamente.");
        }
    }

    private void consultarCatalogos() {
        System.out.println("\n--- FLOTA REGISTRADA ---");
        for (Vehiculo v : controlador.getVehiculos()) {
            System.out.println(v.getDescripcion() + " | Estado: " + v.getEstado() + " | Días acum: " + v.getDiasAcumulados());
        }
        System.out.println("\n--- CLIENTES REGISTRADOS ---");
        for (Cliente c : controlador.getClientes()) {
            System.out.println(c.getTipoClienteStr() + 
                               " | Nombre: " + c.getNombre() + 
                               " | Licencias: " + c.getLicencias() + 
                               " | Activos: " + c.getAlquileresActivos());
        }
    }

    private void menuCotizar() {
        String placa = leerTexto("Placa del vehículo: ");
        String id = leerTexto("ID del cliente (DPI/NIT): ");
        int dias = leerEnteroPositivo("Cantidad de días: ");
        System.out.println(controlador.cotizarAlquiler(placa, id, dias));
    }

    private void menuConfirmar() {
        System.out.println("Nota: Para confirmar debe estar seguro del alquiler.");
        String placa = leerTexto("Placa del vehículo: ");
        String id = leerTexto("ID del cliente (DPI/NIT): ");
        int dias = leerEnteroPositivo("Cantidad de días: ");
        
        try {
            controlador.confirmarAlquiler(placa, id, dias);
            System.out.println("¡Alquiler CONFIRMADO y registrado con éxito!");
        } catch (RentaMovilException | IllegalArgumentException e) {
            System.out.println(e.getMessage()); 
        }
    }

private void menuDevolucion() {
        String placa = leerTexto("Placa del vehículo a devolver: ");
        try {
            System.out.println(controlador.registrarDevolucion(placa));
        } catch (RentaMovilException e) {
            System.out.println(e.getMessage());
        }
    }
    private void menuFinMantenimiento() {
        String placa = leerTexto("Placa del vehículo reparado: ");
        try {
            System.out.println(controlador.registrarFinMantenimiento(placa));
        } catch (RentaMovilException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<TipoLicencia> pedirLicencias() {
        List<TipoLicencia> lics = new ArrayList<>();
        while (lics.isEmpty()) {
            String input = leerTexto("Ingrese tipos de licencia separados por coma (A, B, C, M): ").toUpperCase();
            for (String s : input.split(",")) {
                try { lics.add(TipoLicencia.valueOf(s.trim())); } 
                catch (IllegalArgumentException ignored) {}
            }
            if (lics.isEmpty()) System.out.println("Debe ingresar al menos una licencia válida (A, B, C o M).");
        }
        return lics;
    }

    private String leerTexto(String msj) {
        System.out.print(msj);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String msj) {
        while (true) {
            try {
                System.out.print(msj);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número entero válido.");
            }
        }
    }

    private int leerEnteroPositivo(String msj) {
        while (true) {
            int val = leerEntero(msj);
            if (val > 0) return val;
            System.out.println("Error: Debe ser un número mayor a cero.");
        }
    }

    private double leerDoublePositivo(String msj) {
        while (true) {
            try {
                System.out.print(msj);
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val > 0) return val;
                System.out.println("Error: Debe ser un valor mayor a cero.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número decimal válido.");
            }
        }
    }
}