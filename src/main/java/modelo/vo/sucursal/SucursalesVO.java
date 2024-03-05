package modelo.vo.sucursal;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "sucursales", schema = "bancovigo", catalog = "")
public class SucursalesVO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "suCodSucursal")
    private int suCodSucursal;
    @Basic
    @Column(name = "suCiudad")
    private String suCiudad;
    @Basic
    @Column(name = "suActivo")
    private BigDecimal suActivo;

    public int getSuCodSucursal() {
        return suCodSucursal;
    }

    public void setSuCodSucursal(int suCodSucursal) {
        this.suCodSucursal = suCodSucursal;
    }

    public String getSuCiudad() {
        return suCiudad;
    }

    public void setSuCiudad(String suCiudad) {
        this.suCiudad = suCiudad;
    }

    public BigDecimal getSuActivo() {
        return suActivo;
    }

    public void setSuActivo(BigDecimal suActivo) {
        this.suActivo = suActivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SucursalesVO that = (SucursalesVO) o;
        return suCodSucursal == that.suCodSucursal && Objects.equals(suCiudad, that.suCiudad) && Objects.equals(suActivo, that.suActivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suCodSucursal, suCiudad, suActivo);
    }
}
