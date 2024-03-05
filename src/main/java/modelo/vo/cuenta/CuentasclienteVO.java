package modelo.vo.cuenta;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cuentascliente", schema = "bancovigo", catalog = "")
@IdClass(CuentasclienteVOPK.class)
public class CuentasclienteVO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ccDNI")
    private String ccDni;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ccCodCuenta")
    private int ccCodCuenta;

    public String getCcDni() {
        return ccDni;
    }

    public void setCcDni(String ccDni) {
        this.ccDni = ccDni;
    }

    public int getCcCodCuenta() {
        return ccCodCuenta;
    }

    public void setCcCodCuenta(int ccCodCuenta) {
        this.ccCodCuenta = ccCodCuenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuentasclienteVO that = (CuentasclienteVO) o;
        return ccCodCuenta == that.ccCodCuenta && Objects.equals(ccDni, that.ccDni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ccDni, ccCodCuenta);
    }
}
