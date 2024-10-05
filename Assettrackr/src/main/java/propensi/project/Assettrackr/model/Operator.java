package propensi.project.Assettrackr.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "operator")
@Getter
@Setter
public class Operator extends UserModel {
}
