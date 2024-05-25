package com.example.desarrollodeaplicaciones.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "session")
public class Session {
	@Id
	private String id; //Este es un hash que representa al token original.
	private String lastRefreshToken;
	private Date createdAt;


}
