package blog.in.action.table;

import lombok.Getter;

import javax.persistence.*;

@Getter
@TableGenerator(
        name = "TABLE_SEQ_GENERATOR",
        table = "SEQ_TABLE",
        allocationSize = 1
)
@Entity
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_SEQ_GENERATOR")
    private Long id;
}
