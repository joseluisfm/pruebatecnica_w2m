package es.joseluisfm.pruebatecnica_w2m.dto.in;

public class SortINDTO {
	private String field;
	private SortOrderEnum order;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public SortOrderEnum getOrder() {
		return order;
	}

	public void setOrder(SortOrderEnum order) {
		this.order = order;
	}

}
