package modelo.vo.cuenta;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "cuenta", schema = "bancovigo", catalog = "")
public class CuentaVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cuCodCuenta")
    private int cuCodCuenta;
    @Basic
    @Column(name = "cuCodSucursal")
    private Integer cuCodSucursal;
    @Basic
    @Column(name = "cuFechaCreacion")
    private Date cuFechaCreacion;
    @Basic
    @Column(name = "CuSaldo")
    private Integer cuSaldo;

    public int getCuCodCuenta() {
        return cuCodCuenta;
    }

    public void setCuCodCuenta(int cuCodCuenta) {
        this.cuCodCuenta = cuCodCuenta;
    }

    public Integer getCuCodSucursal() {
        return cuCodSucursal;
    }

    public void setCuCodSucursal(Integer cuCodSucursal) {
        this.cuCodSucursal = cuCodSucursal;
    }

    public Date getCuFechaCreacion() {
        return cuFechaCreacion;
    }

    public void setCuFechaCreacion(Date cuFechaCreacion) {
        this.cuFechaCreacion = cuFechaCreacion;
    }

    public Integer getCuSaldo() {
        return cuSaldo;
    }

    public void setCuSaldo(Integer cuSaldo) {
        this.cuSaldo = cuSaldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuentaVO cuentaVO = (CuentaVO) o;
        return cuCodCuenta == cuentaVO.cuCodCuenta && Objects.equals(cuCodSucursal, cuentaVO.cuCodSucursal) && Objects.equals(cuFechaCreacion, cuentaVO.cuFechaCreacion) && Objects.equals(cuSaldo, cuentaVO.cuSaldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cuCodCuenta, cuCodSucursal, cuFechaCreacion, cuSaldo);
    }
}
