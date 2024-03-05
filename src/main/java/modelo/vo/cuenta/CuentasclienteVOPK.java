package modelo.vo.cuenta;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class CuentasclienteVOPK implements Serializable {
    @Column(name = "ccDNI")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ccDni;
    @Column(name = "ccCodCuenta")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        CuentasclienteVOPK that = (CuentasclienteVOPK) o;
        return ccCodCuenta == that.ccCodCuenta && Objects.equals(ccDni, that.ccDni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ccDni, ccCodCuenta);
    }
}
