package es.joseluisfm.pruebatecnica_w2m.dto.out;

import org.springframework.data.domain.Page;

public class PageableOUTDTO {
	private int totalPages;
	private long totalElements;

	public PageableOUTDTO(Page<?> page) {
		this.totalPages = page.getTotalPages();
		this.totalElements = page.getTotalElements();
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

}
