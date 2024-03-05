package modelo.vo.cliente;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cliente", schema = "bancovigo", catalog = "")
public class ClienteVO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "clDni")
    private String clDni;
    @Basic
    @Column(name = "clNombre")
    private String clNombre;
    @Basic
    @Column(name = "clApellido")
    private String clApellido;
    @Basic
    @Column(name = "clTelefono")
    private Integer clTelefono;

    public String getClDni() {
        return clDni;
    }

    public void setClDni(String clDni) {
        this.clDni = clDni;
    }

    public String getClNombre() {
        return clNombre;
    }

    public void setClNombre(String clNombre) {
        this.clNombre = clNombre;
    }

    public String getClApellido() {
        return clApellido;
    }

    public void setClApellido(String clApellido) {
        this.clApellido = clApellido;
    }

    public Integer getClTelefono() {
        return clTelefono;
    }

    public void setClTelefono(Integer clTelefono) {
        this.clTelefono = clTelefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteVO clienteVO = (ClienteVO) o;
        return Objects.equals(clDni, clienteVO.clDni) && Objects.equals(clNombre, clienteVO.clNombre) && Objects.equals(clApellido, clienteVO.clApellido) && Objects.equals(clTelefono, clienteVO.clTelefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clDni, clNombre, clApellido, clTelefono);
    }
    
    public String toPrettyString() {
    	return getClNombre() + " " + getClApellido() + " ["+getClDni()+"]";
    }
}
