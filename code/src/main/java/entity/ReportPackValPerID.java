package entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReportPackValPerID implements Serializable {

    private int id;
    private int num_month;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportPackValPerID)) return false;
        ReportPackValPerID that = (ReportPackValPerID) o;
        return id == that.id && num_month == that.num_month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, num_month);
    }
}
