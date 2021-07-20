package br.com.bancoOriginal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Routes {

	private String source;
	private String target;
	private Integer distance;
}
