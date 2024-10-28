package es.joseluisfm.pruebatecnica_w2m.dto.in;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PageableINDTO {
	@NotNull
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private int num;

	@NotNull
	@Min(1)
	@Max(Integer.MAX_VALUE)
	private int size;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Pageable toPageable() {
		return PageRequest.of(num, size);
	}

}
