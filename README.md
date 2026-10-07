# Sistema de renta de vehículos

Aplicación desarrollada en **Java** para gestionar el alquiler de distintos tipos de vehículos mediante los principios de **Programación Orientada a Objetos (POO)**.

El proyecto implementa **herencia**, **polimorfismo** y una separación de responsabilidades basada en el patrón arquitectónico **MVC (Modelo–Vista–Controlador)**.

## 👤 Información del estudiante

- **Nombre:** Daniel Eduardo Sacol Cojón
- **Carné:** 26870

## ✨ Características principales

- Gestión de alquiler de diferentes tipos de vehículos:
  - Vehículos
  - Motocicletas
  - Camionetas
  - Buses
- Uso de herencia para representar las características comunes y específicas de cada vehículo.
- Aplicación de polimorfismo mediante referencias del tipo base.
- Organización del código siguiendo el patrón MVC.
- Ejecución desde la terminal utilizando el compilador de Java.

## 🧱 Conceptos de POO aplicados

- **Herencia:** permite reutilizar atributos y comportamientos entre las clases relacionadas.
- **Polimorfismo:** permite trabajar con diferentes tipos de vehículos a través de una misma abstracción.
- **Encapsulamiento:** protege el estado interno de los objetos mediante atributos y métodos de acceso.
- **Abstracción:** modela los elementos esenciales del sistema y oculta detalles innecesarios.

## 📁 Estructura del proyecto

```text
.
├── src/                         # Código fuente Java
├── docs/
│   ├── Analisis ejercicios 4 y 5.pdf
│   ├── UML.png                  # Diagrama UML del sistema
│   └── pruebas.pdf              # Evidencia de pruebas
└── README.md
```

## ⚙️ Requisitos

- **Java JDK 8 o superior**
- Terminal o consola de comandos

Puedes comprobar que Java está instalado con:

```bash
java -version
javac -version
```

## ▶️ Cómo ejecutar el proyecto

Desde la carpeta raíz del repositorio, compila los archivos fuente:

```bash
javac -d bin src/*.java
```

Después, ejecuta la aplicación:

```bash
java -cp bin Main
```

La carpeta `bin/` se crea automáticamente al compilar y contiene los archivos `.class` generados.

## 📚 Documentación

- [Análisis de los ejercicios 4 y 5](docs/Analisis%20ejercicios%204%20y%205.pdf)
- [Diagrama UML](docs/UML.png)
- [Pruebas del sistema](docs/pruebas.pdf)

## 🎯 Objetivo académico

El objetivo del proyecto es aplicar los fundamentos de la Programación Orientada a Objetos en un sistema práctico de gestión de alquiler de vehículos, demostrando el uso de jerarquías de clases, reutilización de código, polimorfismo y organización mediante MVC.

## 📄 Licencia

Este proyecto fue desarrollado con fines académicos.
