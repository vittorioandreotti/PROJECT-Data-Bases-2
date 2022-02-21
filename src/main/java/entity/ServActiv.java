package entity;

import javax.persistence.Embeddable;
import java.sql.Date;

@Embeddable
public class ServActiv {
    private Date date_start_activ;
    private Date date_end_activ;
    private int rej_num;
    private Date date_last_rej;
}
