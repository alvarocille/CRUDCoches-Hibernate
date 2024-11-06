package acceso.dam.hibernatecrud_acilleruelosinovas.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "multa", indexes = {
        @Index(name = "idx_multa_id", columnList = "id_multa")
})
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_multa;
    private Double precio;
    private LocalDate fecha;
    @Column(insertable = false, updatable = false)
    private Integer id_coche;

    @ManyToOne
    @JoinColumn(name="id_coche", referencedColumnName="id")
    private Coche coche;

    public Multa() {
    }

    public Multa(Double precio, LocalDate fecha, int id_coche) {
        this.precio = precio;
        this.fecha = fecha;
        this.id_coche = id_coche;
    }

    public Integer getIdMulta() {
        return id_multa;
    }

    public void setIdMulta(Integer id_multa) {
        this.id_multa = id_multa;
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

    public Integer getCoche() {
        return id_coche;
    }

    public void setCoche(Integer id_coche) {
        this.id_coche = id_coche;
    }

    @Override
    public String toString() {
        return "Multa{" +
                "id_multa=" + id_multa +
                ", precio=" + precio +
                ", fecha=" + fecha +
                ", id_coche='" + id_coche + '\'' +
                '}';
    }
}

