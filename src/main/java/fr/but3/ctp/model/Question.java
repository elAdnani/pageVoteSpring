package fr.but3.ctp.model;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "question")
@Entity
public class Question implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Integer id;

@Column(name = "libquest", nullable=false)
private String libquest;

@Column(name = "active", columnDefinition = "boolean default false")
private Boolean active = false;
 
@OneToMany(mappedBy="question", fetch= FetchType.LAZY)
private List<Choix> choix;

public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getLibquest() {
	return libquest;
}
public void setLibquest(String libquest) {
	this.libquest = libquest;
}
public boolean isActive() {
	return active==true;
}
public void setActive(Boolean active) {
	this.active = active;
}
public List<Choix> getChoix() {
	return choix;
}
public Choix addChoice(int id) {
	Choix element = null;
	for(Choix choix : this.choix) {
		if(choix.getId()==id) {
			choix.addNbChoix();
			element = choix;
		}
	}
	return element;
}

public void setQuestions(List<Choix> questions) {
	this.choix = questions;
}
@Override
public String toString() {
	return "Question [id=" + id + ", libquest=" + libquest + ", active=" + active + ", choix=" + choix + "]";
}



}