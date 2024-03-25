package fr.but3.ctp.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "choix")
@Entity
public class Choix implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "libchoix", nullable = false, length = 20)
	private String libChoix;

	@Column(name = "statut", nullable = false, columnDefinition = "boolean default false")
	private Boolean statut;

	@Column(name = "nbchoix", nullable = false, columnDefinition = "int default 0")
	private int nbChoix;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "qno")
	private Question question;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getLibChoix() {
		return libChoix;
	}

	public void setLibChoix(String libChoix) {
		this.libChoix = libChoix;
	}

	public boolean isStatut() {
		return statut == true;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	public int getNbChoix() {
		return nbChoix;
	}

	public void addNbChoix() {
		this.nbChoix = this.nbChoix + 1;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "Choix [id=" + id + ", libChoix=" + libChoix + ", statut=" + statut + ", nbChoix=" + nbChoix+ "]";
	}

}
