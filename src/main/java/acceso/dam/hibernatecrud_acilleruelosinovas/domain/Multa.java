package acceso.dam.hibernatecrud_acilleruelosinovas.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "multa", indexes = {
        @Index(name = "idx_multa_id", columnList = "id")
})
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMulta;
    private Double precio;
    private LocalDate fecha;
    private String matricula;

    public Multa() {}

    public Multa(Double precio, LocalDate fecha, String matricula) {
        this.precio = precio;
        this.fecha = fecha;
        this.matricula = matricula;
    }

    public Integer getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(Integer idMulta) {
        this.idMulta = idMulta;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Multa{" +
                "idMulta=" + idMulta +
                ", precio=" + precio +
                ", fecha=" + fecha +
                ", matricula='" + matricula + '\'' +
                '}';
    }
}

