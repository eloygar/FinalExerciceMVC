package modelo.vo.transaccion;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "transacciones", schema = "bancovigo", catalog = "")
public class TransaccionesVO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int id;
    @Basic
    @Column(name = "trCodCuenta")
    private Integer trCodCuenta;
    @Basic
    @Column(name = "trFechaTransaccion")
    private Date trFechaTransaccion;
    @Basic
    @Column(name = "trTipo")
    private String trTipo;
    @Basic
    @Column(name = "trCantidad")
    private Integer trCantidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTrCodCuenta() {
        return trCodCuenta;
    }

    public void setTrCodCuenta(Integer trCodCuenta) {
        this.trCodCuenta = trCodCuenta;
    }

    public Date getTrFechaTransaccion() {
        return trFechaTransaccion;
    }

    public void setTrFechaTransaccion(Date trFechaTransaccion) {
        this.trFechaTransaccion = trFechaTransaccion;
    }

    public String getTrTipo() {
        return trTipo;
    }

    public void setTrTipo(String trTipo) {
        this.trTipo = trTipo;
    }

    public Integer getTrCantidad() {
        return trCantidad;
    }

    public void setTrCantidad(Integer trCantidad) {
        this.trCantidad = trCantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransaccionesVO that = (TransaccionesVO) o;
        return id == that.id && Objects.equals(trCodCuenta, that.trCodCuenta) && Objects.equals(trFechaTransaccion, that.trFechaTransaccion) && Objects.equals(trTipo, that.trTipo) && Objects.equals(trCantidad, that.trCantidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trCodCuenta, trFechaTransaccion, trTipo, trCantidad);
    }
}
