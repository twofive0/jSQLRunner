/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsqlrunner;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Devin
 */
@Entity
@Table(name = "comptype", catalog = "EvolutionLDAR", schema = "")
@NamedQueries({
    @NamedQuery(name = "Comptype.findAll", query = "SELECT c FROM Comptype c"),
    @NamedQuery(name = "Comptype.findByIdCompType", query = "SELECT c FROM Comptype c WHERE c.idCompType = :idCompType"),
    @NamedQuery(name = "Comptype.findByTypeName", query = "SELECT c FROM Comptype c WHERE c.typeName = :typeName"),
    @NamedQuery(name = "Comptype.findByTypeAbbr", query = "SELECT c FROM Comptype c WHERE c.typeAbbr = :typeAbbr")})
public class Comptype implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCompType")
    private Integer idCompType;
    @Basic(optional = false)
    @Column(name = "TypeName")
    private String typeName;
    @Column(name = "TypeAbbr")
    private String typeAbbr;

    public Comptype() {
    }

    public Comptype(Integer idCompType) {
        this.idCompType = idCompType;
    }

    public Comptype(Integer idCompType, String typeName) {
        this.idCompType = idCompType;
        this.typeName = typeName;
    }

    public Integer getIdCompType() {
        return idCompType;
    }

    public void setIdCompType(Integer idCompType) {
        Integer oldIdCompType = this.idCompType;
        this.idCompType = idCompType;
        changeSupport.firePropertyChange("idCompType", oldIdCompType, idCompType);
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        String oldTypeName = this.typeName;
        this.typeName = typeName;
        changeSupport.firePropertyChange("typeName", oldTypeName, typeName);
    }

    public String getTypeAbbr() {
        return typeAbbr;
    }

    public void setTypeAbbr(String typeAbbr) {
        String oldTypeAbbr = this.typeAbbr;
        this.typeAbbr = typeAbbr;
        changeSupport.firePropertyChange("typeAbbr", oldTypeAbbr, typeAbbr);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompType != null ? idCompType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comptype)) {
            return false;
        }
        Comptype other = (Comptype) object;
        if ((this.idCompType == null && other.idCompType != null) || (this.idCompType != null && !this.idCompType.equals(other.idCompType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsqlrunner.Comptype[ idCompType=" + idCompType + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
