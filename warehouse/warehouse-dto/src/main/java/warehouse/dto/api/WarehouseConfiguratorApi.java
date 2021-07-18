package warehouse.dto.api;

public interface WarehouseConfiguratorApi {
	String WAREHOUSE_STATE_BACK = "/warehouse-state-back";
	String CONTAINER_CREATE = "/container/create";
	String PRODUCT_CREATE = "/product/create";
	String OPERATOR_CREATE = "/operator/create";
	String ROLE_CREATE = "/role/create";
	String SET_OPERATOR_TO_ROLE ="/operator/role/set";
	String SET_PRODUCT_TO_CONTAINER ="/product/container/set";
	String CHANGE_CONTAINER_ADDRESS ="/container/address/change";
	String CHANGE_PRODUCT_NAME ="/product/name/change";
	String SET_TRANSPORT_SUPPLY_TO_PRODUCT ="/transport/supply/product/set";
	String SET_IRREDUCIBLE_BALANCE_TO_PRODUCT ="/irreducible/balance/product/set";
	String CHANGE_OPERATOR_NAME ="/operator/name/change";
	String CHANGE_OPERATOR_EMAIL ="/operator/email/change";
	String CHANGE_ROLE ="/role/change";
}
