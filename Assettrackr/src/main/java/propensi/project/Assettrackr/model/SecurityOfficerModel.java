package propensi.project.Assettrackr.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "security_officer")
@Getter
@Setter
public class SecurityOfficerModel extends UserModel{
}
