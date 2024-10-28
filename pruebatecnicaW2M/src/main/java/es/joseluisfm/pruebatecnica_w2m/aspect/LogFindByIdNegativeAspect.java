package es.joseluisfm.pruebatecnica_w2m.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogFindByIdNegativeAspect {

	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(LogFindByIdNegativeAspect.class);

	@Before("execution(* es.joseluisfm.pruebatecnica_w2m.controller.SpaceShipsController.*(..)) && args(id,..)")
	public void logNegativeIdRequest(JoinPoint joinPoint, Long id) {
		if (id != null && id < 0) {
			LOGGER.warn("Solicitud realizada con ID negativo: {} en el método {}", id, joinPoint.getSignature().getName());
		}
	}
}
