package com.roshka.sifen.addon;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* Clase encargada de mapear consulta de CDC
* @author eleon
*
*/

@SuppressWarnings("serial")
public class rEnviConsDeResponse implements Serializable
{
	private LocalDateTime dFecProc;
	private String dCodRes;
	private String dMsgRes;
	private xContenDE xContenDE;
	
}
