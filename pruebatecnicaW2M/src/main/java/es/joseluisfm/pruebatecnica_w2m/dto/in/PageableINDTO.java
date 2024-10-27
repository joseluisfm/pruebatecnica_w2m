package es.joseluisfm.pruebatecnica_w2m.dto.in;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableINDTO {
	private int num;
	private int size;
	private List<SortINDTO> sorts;

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

	public List<SortINDTO> getSorts() {
		return sorts;
	}

	public void setSorts(List<SortINDTO> sorts) {
		this.sorts = sorts;
	}

}
